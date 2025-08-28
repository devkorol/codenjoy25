import Game from '../games';

const protocol = process.env.REACT_APP_GAME_SCHEMA;

export const getGameConnectionString = (server, code, email) =>
    `${protocol}://${server}/codenjoy-contest/board/player/${email}?code=${code}`;

export const getIFrameLink = (server, id) =>
    `${protocol}://${server}/codenjoy-contest/board/player/${id}?only=true`;

export const getJsClient = server =>
    `${protocol}://${server}/codenjoy-contest/resources/user/${Game.name}-servers.zip`;

export const getJavaClient = server =>
    `${protocol}://${server}/codenjoy-contest/resources/user/${Game.name}-servers.zip`;
