/*
 * <LibroX allows users to conveniently read e-books in various formats.>
 * Copyright (C) <2024> <Denis Levshchanov>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact information:
 * Email: <equilibrian@yandex.ru>
 */

package su.librox.model.fictionbook

import org.simpleframework.xml.Attribute

/**
 * Represents a sequence associated with the book.
 *
 * @property name The name of the sequence.
 * @property number The number in the sequence.
 */
data class Sequence(
    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,

    @field:Attribute(name = "number", required = false)
    @param:Attribute(name = "number", required = false)
    val number: String? = null
)