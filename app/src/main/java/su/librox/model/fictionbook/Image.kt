/*
 * LibroX allows users to conveniently read e-books in various formats.
 * Copyright (C) 2024 Denis Levshchanov
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
 * Email: equilibrian@yandex.ru
 */

package su.librox.model.fictionbook

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
@Namespace(reference = "http://www.w3.org/1999/xlink")
data class Image(
    @field:Attribute(name = "type", required = false)
    @param:Attribute(name = "type", required = false)
    var type: String? = null,

    @field:Attribute(name = "href", required = true)
    @param:Attribute(name = "href", required = true)
    var imageId: String,

    @field:Attribute(name = "alt", required = false)
    @param:Attribute(name = "alt", required = false)
    var alt: String? = null,

    @field:Attribute(name = "title", required = false)
    @param:Attribute(name = "title", required = false)
    var title: String? = null,

    @field:Attribute(name = "id", required = false)
    @param:Attribute(name = "id", required = false)
    var id: String? = null
) {
    init {
        imageId = imageId.removePrefix("#")
    }
}