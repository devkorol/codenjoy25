/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
var currentBoardSize = null;

function initCanvases(contextPath, players, allPlayersScreen,
                multiplayerType, boardSize, game,
                enablePlayerInfo, enablePlayerInfoLevel,
                sprites, alphabet, spritesAlphabet, spriteElements,
                drawBoard, onLoad)
{
    var canvases = {};
    var infoPools = {};
    currentBoardSize = boardSize;
    var plots = {};
    var plotsUrls = {};
    var plotSize = 0;
    var canvasSize = 0;
    var images = {};
    loadCanvasesData(alphabet, spriteElements, onLoad);
    var reloading = false;
    var readableNames = {};
    var previousTickData = null;

    function toName(id) {
        return readableNames[id];
    }

    function goToHomePage() {
        window.location.href = contextPath;
    }

    function reloadCanvasesData() {
        reloading = true;
        loadPlayers(rebuildCanvasesForPlayers);
    }

    function notIn(playersWhere, playersWhat) {
        var result = [];
        var ids = getNames(playersWhere);
        playersWhat.forEach(function (player) {
            if ($.inArray(player.id, ids) == -1) {
                result.push(player);
            }
        });
        return result;
    }

    function rebuildCanvasesForPlayers(newPlayers) {
        var create = notIn(players, newPlayers);
        var remove = notIn(newPlayers, players);

        players = newPlayers;

        removeHtml(remove);
        removeCanvases(remove);

        buildHtml(create);
        buildCanvases(create);

        if (players.length == 0) {
            goToHomePage();
        }
        reloading = false;
    }

    function justGame(room) {
        var ROOMS_SEPARATOR = '-';
        return room.split(ROOMS_SEPARATOR).shift();
    }

    function loadSpriteImages(elements, alphabet, onImageLoad) {
        for (var index in elements) {
            var ch = alphabet[index];
            var color = elements[index];
            plots[ch] = color;
            var subFolder = (!!sprites) ? sprites + '/' : '';
            plotsUrls[color] = contextPath + '/resources/' + justGame(game) + '/sprite/' + subFolder + color + '.png';

            var image = new Image();
            image.onload = function() {
                if (plotSize == 0) {
                    plotSize = this.width;
                    canvasSize = plotSize * boardSize;
                    if (!!onImageLoad) {
                        onImageLoad();
                    }
                }
            }
            image.src = plotsUrls[color];
            images[color] = image;
        }
    }

    function loadCanvasesData(alphabet, elements, onLoadSprites) {
        loadSpriteImages(elements, alphabet, function() {
            buildHtml(players);
            buildCanvases(players);
            if (!!onLoadSprites) {
                onLoadSprites();
            }

            $('body').on('board-updated', function(events, data) {
                if (!reloading) {
                    drawUsersCanvas(data);
                }
            });
        });
    }

    function removeHtml(playersList) {
        playersList.forEach(function (player) {
            $('#div_' + player.id).remove();
        });
    }

    function buildHtml(playersList) {
        var templateData = [];
        playersList.forEach(function (player) {
            var id = player.id;
            var name = player.readableName;
            var levelVisible = (allPlayersScreen || !enablePlayerInfoLevel) ? 'none' : 'block';
            var playerVisible  = (!enablePlayerInfo) ? 'none' : 'block';
            templateData.push({
                name : name,
                id : id,
                levelVisible : levelVisible,
                playerVisible : playerVisible
            });
        });
        $('#players_container script').tmpl(templateData).appendTo('#players_container');
        if (!!setup.canvasCursor) {
            $('#players_container canvas').css('cursor', setup.canvasCursor);
        }
    }

    function removeCanvases(playersList) {
        playersList.forEach(function (player) {
            delete canvases[player.id];
            delete infoPools[player.id];
        });
    }

    function buildCanvases(playersList) {
        playersList.forEach(function (player) {
            canvases[player.id] = createCanvas(player.id);
            infoPools[player.id] = [];
        });
    }

    function decode(ch){
        return plots[ch];
    }

    function plotsContains(color) {
        for (var ch in plots) {
            if (plots[ch] == color) {
                return true;
            }
        }
        return false;
    }

    var getBoardDrawer = function(canvas, playerId, playerData, allPlayersScreen) {

        var getBoard = function() {
            return playerData.board;
        }

        var getPrevBoard = function() {
            return playerData.prevBoard;
        }

        var getLayers = function(board) {
            return (!board.layers) ? [board] : board.layers;
        }

        var getHeroesData = function() {
            return playerData.coordinates;
        }

        var drawAllLayers = function(layers, prevLayers, onDrawItem) {
            var isDrawByOrder = setup.isDrawByOrder;

            var isChanged = function(ch) {
                if (!prevLayers) {
                    return true;
                }
                for (var layer = 0; layer < layers.length; layer++) {
                    var color = layers[layer][ch];
                    var prevColor = prevLayers[layer][ch];
                    if (color != prevColor) {
                        return true;
                    }
                }
                return false;
            }

            var drawChar = function(plotIndex) {
                var x = 0;
                var y = boardSize - 1;
                for (var ch = 0; ch < layers[0].length; ch++) {
                    if (isChanged(ch)) {
                        for (var layer = 0; layer < layers.length; layer++) {
                            var color = layers[layer][ch];
                            if (!isDrawByOrder || plotIndex == color) {
                                canvas.drawPlot(decode(color), x, y);
                                if (!!onDrawItem) {
                                    onDrawItem(layers, layer, ch, x, y);
                                }
                            }
                        }
                    }
                    x++;
                    if (x == boardSize) {
                        x = 0;
                        y--;
                    }
                }
            }

            if (isDrawByOrder) {
                for (var ch in plots) {
                    var plot = plots[ch];
                    drawChar(ch);
                }
            } else {
                drawChar();
            }
        }

        var drawBackground = function(name) {
            if (plotsContains(name)) {
                if (setup.isFillOrStrechBackground) {
                    canvas.fillImage(name);
                } else {
                    canvas.stretchImage(name);
                }
            }
        }

        var drawBack = function() {
            drawBackground('background');
        }

        var drawFog = function() {
            drawBackground('fog');
        }

        var clear = function() {
            canvas.clear();
        }

        var logLayers = function(layers) {
            if (!setup.isPrintBoardToConsole) return;

            var parseLayer = function(layer) {
                var result = [];
                var line = '';
                for (var ch = 0; ch < layer.length; ch++) {
                    var color = layer[ch];
                    line += spritesAlphabet[alphabet.indexOf(color)];
                    if (ch % boardSize == boardSize - 1) {
                        result.push(line);
                        line = '';
                    }
                }
                return result;
            }

            var all = [];
            for (var index in layers) {
                all.push(parseLayer(layers[index]))
            }

            var result = '';
            for (var line in all[0]) {
                for (var layer in layers) {
                    result += all[layer][line] + ' ';
                }
                result += '\n';
            }

            console.log(result);

        }

        var drawLayers = function(onDrawItem) {
            var onlyChanges = setup.isDrawOnlyChanges;

            var layers = getLayers(getBoard());
            logLayers(layers);

            var prevBoard = getPrevBoard();
            var prevLayers = null;
            if (onlyChanges && !!prevBoard) {
                prevLayers = getLayers(prevBoard);
            }

            try {
                if (onlyChanges) {
                    canvas.restoreState();
                }
                drawAllLayers(layers, prevLayers, onDrawItem);
                if (onlyChanges) {
                    canvas.saveState();
                }
            } catch (err) {
                console.log(err);
            }
        }

        var drawPlayerNames = function(font, beforeDraw) {
            try {
                var drawName = function(id, point, font, heroData) {
                    var name = toName(id);
                    var data = {
                        'name':name,
                        'point':point,
                        'font':font,
                        'heroData':heroData
                    }
                    if (!!beforeDraw) data = beforeDraw(data);
                    canvas.drawText(data.name, data.point, data.font);
                }

                var board = getBoard();
                if (typeof board.showName == 'undefined' || board.showName) {
                    var currentPoint = null;
                    var currentHeroData = null;
                    var heroesData = getHeroesData();
                    var currentIsDrawName = true;
                    for (var id in heroesData) {
                        var heroData = heroesData[id];
                        var point = heroData.coordinate;
                        if (!point) return; // TODO why this can happen?
                        if (point.x == -1 || point.y == -1) {
                            if (playerId == id) {
                                currentIsDrawName = false;
                            }
                            continue;
                        }
                        if (!!board.offset) {
                            point.x -= board.offset.x;
                            point.y -= board.offset.y;
                        }

                        // TODO это тоже относится к TRAINING типу игры
                        var isPlayerOnSingleBoard = function(board) {
                            var progress = board.levelProgress;
                            if (!progress) {
                                return false;
                            }
                            return progress.current < progress.total;
                        }
                        var isDrawName = !!heroData.multiplayer && !isPlayerOnSingleBoard(board);
                        if (playerId == id) {
                            currentPoint = point;
                            currentHeroData = heroData;
                            currentIsDrawName = isDrawName;
                            continue;
                        }
                        if (isDrawName) {
                            drawName(id, point, font, heroData);
                        }
                    }
                    if (currentIsDrawName) {
                        if (canvas.isHighlighted()) {
                            var mark = Object.assign({}, font);
                            mark.font = "80px 'Verdana, sans-serif'";
                            mark.fillStyle = '#FF0';
                            mark.shadowBlur = 0;
                            // эти магические числа подогнаны под разные игры с разными размерами спрайтов
                            mark.dx = 0.5*plotSize - 40;
                            mark.dy = -0.5*plotSize + 27;
                            var markPoint = { x : currentPoint.x - 1, y : currentPoint.y - 1 };
                            canvas.drawText('⌾', markPoint, mark);
                        }
                        drawName(playerId, currentPoint, font, currentHeroData);
                    }
                }
            } catch (err) {
                console.log(err);
            }
        }

        return {
            clear : clear,
            drawBack : drawBack,
            drawLayers : drawLayers,
            drawPlayerNames : drawPlayerNames,
            drawFog : drawFog,
            canvas : canvas,
            playerId : playerId,
            playerData : playerData,
            allPlayersScreen : allPlayersScreen
        };
    };

    function defaultFont() {
        return {
            font: "15px 'Verdana, sans-serif'",
            fillStyle: '#0FF',
            textAlign: 'left',
            // TODO 5f2 придумать как-то фон, чтобы шрифт читался
            // shadowColor: '#000',
            // shadowOffsetX: 0,
            // shadowOffsetY: 0,
            // shadowBlur: 7
        }
    }

    function defaultDrawBoard(drawer) {
        drawer.clear();
        drawer.drawBack();
        drawer.drawLayers();
        drawer.drawPlayerNames(defaultFont(), null);
        drawer.drawFog();
    }

    drawBoard = (!!drawBoard) ? drawBoard : defaultDrawBoard;

    function calculateTextSize(text) {
        var div = $("#width_calculator_container");
        div.html(text);
        div.css('display', 'block');
        return div[0];
    }

    function showScoreInformation(playerId, information) {
        var infoPool = infoPools[playerId];

        // TODO это костыль, а возникает оно в момент переходов с поле на поле для игры http://127.0.0.1:8080/codenjoy-contest/board/game/namdreab
        if (typeof infoPool == 'undefined') {
            infoPools[playerId] = [];
            infoPool = infoPools[playerId];
        }

        if (information != '') {
            var arr = information.split(', ');
            for (var i in arr) {
                if (arr[i] == '0') {
                    continue;
                }
                infoPool.push(arr[i]);
            }
        }
        if (infoPool.length == 0) return;

        var score = $("#score_info_" + playerId);
        if (score.is(':visible')) {
            return;
        }

        var text = '<center>' + infoPool.join('<br>') + '</center>';
        infoPool.splice(0, infoPool.length);

        var canvas = $("#" + playerId);
        var size = calculateTextSize(text);
        score.css({
            position: "absolute",
            marginLeft: 0,
            marginTop: 0,
            left: canvas.position().left + canvas.width()/2 - size.clientWidth/2,
            top: canvas.position().top + canvas.height()/2 - size.clientHeight/2
        });

        score.html(text);

        score.show().delay(700).fadeOut(200, function() {
            score.hide();

            showScoreInformation(playerId, '');
        });
    }

    function createCanvas(canvasName) {
        var canvas = $("#" + canvasName);

        if (canvas[0].width != canvasSize || canvas[0].height != canvasSize) {
            canvas[0].width = canvasSize;
            canvas[0].height = canvasSize;
        }

        var ctx = canvas[0].getContext("2d");

        var saveState = function() {
            if (!canvas.saved) {
                canvas.saved = document.createElement('canvas');
                canvas.saved.width = canvas[0].width;
                canvas.saved.height = canvas[0].height;
                canvas.saved.ctx = canvas.saved.getContext('2d', { alpha: false });
            }
            canvas.saved.ctx.drawImage(canvas[0], 0, 0);
        }

        var restoreState = function() {
            if (!!canvas.saved) {
                ctx.drawImage(canvas.saved, 0, 0);
            }
        }

        var drawPlot = function(color, x, y) {
            var image = images[color];
            drawImage(image, x, y, 0, 0);
        }

        var drawImage = function(image, x, y, dx, dy) {
            ctx.drawImage(
                image,
                x * plotSize - (image.width - plotSize)/2 + dx,
                (boardSize - 1 - y) * plotSize - (image.height - plotSize) + dy
            );
        }

        var stretchImage = function(color) {
            var image = images[color];
            ctx.drawImage(
                image,
                0,
                0,
                canvas[0].width,
                canvas[0].height
            );
        }

        var fillImage = function(color) {
            var image = images[color];
            var pattern = ctx.createPattern(image, "repeat");
            ctx.rect(0,
                0,
                canvas[0].width,
                canvas[0].height);
            ctx.fillStyle = pattern;
            ctx.fill();
        }

        var highlighted = false;

        canvas.click(function(event) {
            if (event.shiftKey) {
                highlighted = !highlighted;
            }
        });

        var isHighlighted = function() {
            return highlighted;
        }

        var drawText = function(text, pt, font) {
            if (pt.x == -1 || pt.y == -1) return;

            if (!font) {
                font = defaultFont();
            }
            ctx.font = font.font;
            ctx.fillStyle =  font.fillStyle;
            ctx.textAlign = font.textAlign;
            // TODO придумать как-то фон, чтобы шрифт читался
            // ctx.shadowColor = font.shadowColor;
            // ctx.shadowOffsetX = font.shadowOffsetX;
            // ctx.shadowOffsetY = font.shadowOffsetY;
            // ctx.shadowBlur = font.shadowBlur;

            var x = (pt.x + 1) * plotSize;
            var y = (boardSize - pt.y - 1) * plotSize - 5;
            if (!!font.dx) {
                x += font.dx;
            }
            if (!!font.dy) {
                y += font.dy;
            }
            ctx.fillText(text, x, y);
            //ctx.shadowBlur = 0;
        }

        var clear = function() {
            canvas.clearCanvas();
        }

        var getCanvasSize = function() {
            return canvasSize;
        }

        var getPlotSize = function() {
            return plotSize;
        }

        return {
            fillImage : fillImage,
            stretchImage : stretchImage,
            drawImage : drawImage,
            drawPlot : drawPlot,
            drawText: drawText,
            clear : clear,
            restoreState : restoreState,
            saveState : saveState,
            getCanvasSize : getCanvasSize,
            getPlotSize : getPlotSize,
            isHighlighted : isHighlighted
        };
    }

    function isPlayerListEmpty(data) {
        return Object.keys(data).length == 0;
    }

    function getNames(playerList) {
        var result = [];
        playerList.forEach(function (player) {
            result.push(player.id);
        });
        return result;
    }

    function getPlayers(data) {
        return Object.keys(data);
    }

    function getAllPlayersFromGroups(data) {
        var result = [];
        var keys = getPlayers(data);
        for (var player in keys) {
            result = result.concat(data[keys[player]].group);
        }
        return result;
    }

    function isPlayersInGroups(data) {
        var playersOnTop = getPlayers(data);
        var playersInGroups = getAllPlayersFromGroups(data);
        return playersOnTop != playersInGroups;
    }

    function isPlayersListChanged(data) {
        var newPlayers = getPlayers(data);
        var oldPlayers = getNames(players);

        if (newPlayers.length != oldPlayers.length) {
            return true;
        }

        var hasNew = false;
        newPlayers.forEach(function (newPlayer) {
            if ($.inArray(newPlayer, oldPlayers) == -1) {
                hasNew = true;
            }
        });

        return hasNew;
    }

    function drawUsersCanvas(data) {
        if (data == null) {
            $("#showdata").text("There is NO data for player available!");
            return;
        }
        $("#showdata").text('');

        if (isPlayerListEmpty(data)) {
            goToHomePage();
            return;
        }

        if (allPlayersScreen && isPlayersInGroups(data)) {
            var playersOnTop = [];
            var ids = getPlayers(data);
            for (var index in ids) {
                var id = ids[index];
                playersOnTop.push({
                    'id':id,
                    'readableName':data[id].readableNames[id]
                });
            }

            reloading = true;
            rebuildCanvasesForPlayers(playersOnTop);
        }

        if (allPlayersScreen && isPlayersListChanged(data)) {
            reloadCanvasesData();
            return;
        }

        for (var player in data) {
            data[player].prevBoard = (!!previousTickData && !!previousTickData[player])
                                            ? previousTickData[player].board : null;
        }

        if (allPlayersScreen) {
            for (var player in data) {
                drawUserCanvas(player, data[player], true);
            }
        } else {
            for (var i in players) {
                var player = players[i].id;
                drawUserCanvas(player, data[player], false);
            }
        }
        previousTickData = data;
    }

    Number.prototype.padLeft = function(base,chr){
        var  len = (String(base || 10).length - String(this).length)+1;
        return len > 0? new Array(len).join(chr || '0')+this : this;
    }

    function drawUserCanvas(playerId, data, allPlayersScreen) {
        if (currentBoardSize != data.boardSize) {    // TODO так себе решение... Почему у разных юзеров передается размер борды а не всем сразу?
            reloadCanvasesData();
        }

        var canvas = canvases[playerId];
        canvas.boardSize = boardSize;
        readableNames = data.readableNames;

        drawBoard(getBoardDrawer(canvas, playerId, data, allPlayersScreen));

        if (!!data.tickTime) {
            $("#score_" + playerId).html(
                data.score + '<br>' +
                'Message : ' + data.message + '<br>' +
                'Time : ' + getTickDateTime(data.tickTime, false) +  '<br>' +
                'Mills : ' + data.tickTime +
                '<br>' +
                'Answer : ' + data.command + '<br>'
            );
        } else {
            $("#score_" + playerId).text(data.score);
        }

        showScoreInformation(playerId, data.info);

        if (!allPlayersScreen) {
            $("#level_" + playerId).text(data.coordinates[playerId].level + 1);
        }
    }

}
