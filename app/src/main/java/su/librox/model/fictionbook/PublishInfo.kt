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

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false)
data class PublishInfo(
    @field:Element(name = "book-name", required = false)
    @param:Element(name = "book-name", required = false)
    val bookName: String? = null,

    @field:Element(name = "publisher", required = false)
    @param:Element(name = "publisher", required = false)
    val publisher: String? = null,

    @field:Element(name = "city", required = false)
    @param:Element(name = "city", required = false)
    val city: String? = null,

    @field:Element(name = "year", required = false)
    @param:Element(name = "year", required = false)
    val year: String? = null,

    @field:Element(name = "isbn", required = false)
    @param:Element(name = "isbn", required = false)
    val isbn: String? = null,

    @field:ElementList(name = "sequence", inline = true, required = false, entry = "sequence")
    @param:ElementList(name = "sequence", inline = true, required = false, entry = "sequence")
    val sequence: List<Sequence>? = null
)
