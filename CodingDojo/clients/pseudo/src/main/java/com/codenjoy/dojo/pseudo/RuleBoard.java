package com.codenjoy.dojo.pseudo;

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

import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.client.ElementsMap;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.CharElement;

public class RuleBoard extends AbstractBoard<CharElement> {

    public static final char ANY_CHAR = '?';
    public static final CharElement OUT_OF_FIELD = new CharElement() {
        @Override
        public char ch() {
            return '\uffff';
        }

        @Override
        public String name() {
            return null;
        }
    };

    private ElementsMap<CharElement> map;
    private ElementReader elements;

    public RuleBoard(ElementReader elements) {
        this.elements = elements;
        this.map = new ElementsMap<>(elements.values());
    }

    @Override
    public CharElement[] elements() {
        return elements.values();
    }

    @Override
    public CharElement getAt(int x, int y) {
        if (isOutOf(x, y)) {
            return OUT_OF_FIELD;
        }
        return super.getAt(x, y);
    }

    @Override
    public String toString() {
        return boardAsString();
    }

    @Override
    public CharElement valueOf(char ch) {
        try {
            return super.valueOf(ch);
        } catch (IllegalArgumentException e) {
            // пропускаем все неизвестные игре символы, типа '?'
            return null;
        }
    }

    // TODO refactor me
    public boolean isNearHero(Pattern pattern) {
        Point meAtMap = this.getFirst(elements.heroElements());

        RuleBoard part = this.clone(pattern.pattern());
        Point meAtPart = part.getFirst(elements.heroElements());

        Point corner = meAtMap.relative(meAtPart);

        for (int dx = 0; dx < part.size; dx++) {
            for (int dy = 0; dy < part.size; dy++) {
                CharElement real = this.getAt(corner.getX() + dx, corner.getY() + dy);
                Character mask = part.field(dx, dy).get(0);

                if (mask == ANY_CHAR){
                    continue;
                }

                if (mask == real.ch()) {
                    continue;
                }

                if (pattern.synonyms().match(mask, real.ch())) {
                    continue;
                }

                return false;
            }
        }
        return true;
    }

    private RuleBoard clone(String pattern1) {
        return (RuleBoard) new RuleBoard(elements).forString(pattern1);
    }

    public boolean isGameOver() {
        return getFirst(elements.heroElements()) == null;
    }
}
