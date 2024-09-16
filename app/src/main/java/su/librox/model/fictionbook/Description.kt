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
import org.simpleframework.xml.Root

/**
 * Represents the description of a book.
 *
 * @property titleInfo The title information of the book.
 * @property publishInfo The publishing information of the book.
 */
@Root(strict = false)
data class Description(
    @field:Element(name = "title-info")
    @param:Element(name = "title-info")
    val titleInfo: TitleInfo,

    @field:Element(name = "publish-info", required = false)
    @param:Element(name = "publish-info", required = false)
    val publishInfo: PublishInfo?,
)