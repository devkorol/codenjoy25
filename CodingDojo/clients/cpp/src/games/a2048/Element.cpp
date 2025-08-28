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

//include "Element.h"

Element::Element(Char el) {
    elem.first = valueOf(el);
    elem.second = el;
}

Element::Element(String name) {
    elem.second = Elements.at(name);
    elem.first = name;
}

Char Element::ch() {
    return elem.second;
}

String Element::name() {
    return elem.first;
}

Char Element::getChar() const {
    return elem.second;
}

String Element::valueOf(Char ch) const {
    for (auto i : Elements) {
        if (i.second == ch) return i.first;
    }
    throw std::invalid_argument("Element::valueOf(Char ch): No such Element for " + ch);
}

bool Element::operator==(const Element& el) const {
    return elem == el.elem;
}

ElementMap Element::initialiseElements() {
    ElementMap result;

        // An obstacle through which the chips do not pass.

    result[LL("_x")] = LL('x');

        // Number 2. Will unite on the field only with the same chips,
        // the result will be a chip with number 4, so 2+2=4.

    result[LL("_2")] = LL('2');

        // Number 4. 4+4=8.

    result[LL("_4")] = LL('4');

        // Number 8. 8+8=16.

    result[LL("_8")] = LL('8');

        // Number 16. 16+16=32.

    result[LL("_16")] = LL('A');

        // Number 32. 32+36=64.

    result[LL("_32")] = LL('B');

        // Number 64. 64+64=128.

    result[LL("_64")] = LL('C');

        // Number 128. 128+128=256.

    result[LL("_128")] = LL('D');

        // Number 256. 256+256=512.

    result[LL("_256")] = LL('E');

        // Number 512. 512+512=1,024.

    result[LL("_512")] = LL('F');

        // Number 1,024. 1k+1k=2k.

    result[LL("_1024")] = LL('G');

        // Number 2,048. 2k+2k=4k.

    result[LL("_2048")] = LL('H');

        // Number 4,096. 4k+4k=8k.

    result[LL("_4096")] = LL('I');

        // Number 8,192. 8k+8k=16k.

    result[LL("_8192")] = LL('J');

        // Number 16,384. 16k+16k=32k.

    result[LL("_16384")] = LL('K');

        // Number 32,768. 32k+32k=64k.

    result[LL("_32768")] = LL('L');

        // Number 65,536. 64k+64k=128k.

    result[LL("_65536")] = LL('M');

        // Number 131,072. 128k+128k=256k.

    result[LL("_131072")] = LL('N');

        // Number 262,144. 256k+256k=512k.

    result[LL("_262144")] = LL('O');

        // Number 524,288. 512k+512k=1M.

    result[LL("_524288")] = LL('P');

        // Number 1,048,576. 1M+1M=2M.

    result[LL("_1048576")] = LL('Q');

        // Number 2,097,152. 2M+2M=4M.

    result[LL("_2097152")] = LL('R');

        // Number 4,194,304. 4M+4M=8M.

    result[LL("_4194304")] = LL('S');

        // Empty space.

    result[LL("NONE")] = LL(' ');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
