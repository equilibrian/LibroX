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

/**
 * Represents the title information of a book.
 *
 * @property genre The genres of the book.
 * @property authors The authors of the book.
 * @property bookTitle The title of the book.
 * @property annotation The annotation of the book.
 * @property keywords The keywords associated with the book.
 * @property lang The language of the book.
 * @property coverPage The cover page of the book.
 * @property translators The translators of the book.
 * @property sequence The sequence information of the book.
 */
@Root(name = "title-info", strict = false)
data class TitleInfo(
    @field:ElementList(name = "genre", inline = true, required = false, entry = "genre")
    @param:ElementList(name = "genre", inline = true, required = false, entry = "genre")
    var genre: MutableList<String>? = mutableListOf(),

    @field:ElementList(name = "author", inline = true, entry = "author")
    @param:ElementList(name = "author", inline = true, entry = "author")
    val authors: List<Person>,

    @field:Element(name = "book-title")
    @param:Element(name = "book-title")
    val bookTitle: String,

    /*@field:Element(name = "annotation", required = false)
    @param:Element(name = "annotation", required = false)
    val annotation: Annotation?,*/

    @field:Element(name = "keywords", required = false)
    @param:Element(name = "keywords", required = false)
    val keywords: String?,

    @field:Element(name = "lang", required = false)
    @param:Element(name = "lang", required = false)
    val lang: String?,

    @field:Element(name = "coverpage", required = false)
    @param:Element(name = "coverpage", required = false)
    val coverPage: CoverPage?,

    @field:ElementList(name = "translator", inline = true, required = false, entry = "translator")
    @param:ElementList(name = "translator", inline = true, required = false, entry = "translator")
    val translators: List<Person>?,

    @field:ElementList(name = "sequence", inline = true, required = false, entry = "sequence")
    @param:ElementList(name = "sequence", inline = true, required = false, entry = "sequence")
    val sequence: List<Sequence>?,
) {

    init { clearGenres() }

    /**
     * Removes empty strings from the [genre] list.
     */
    private fun clearGenres() {
        if (genre.isNullOrEmpty()) return

        genre!!.forEachIndexed { idx, item ->
            if (item.isEmpty()) genre!!.removeAt(idx)

            genre!![idx].replace("-", "_")
        }
    }
}