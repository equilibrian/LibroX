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

package su.librox.model

import android.content.Context
import android.graphics.Bitmap
import su.librox.model.fictionbook.PublishInfo
import su.librox.model.fictionbook.Sequence

/**
 * Interface representing a book with various metadata and content-related methods.
 */
interface IBook {

    /**
     * Retrieves the cover page of the book as a [Bitmap].
     *
     * @param ctx The [Context] in which the cover page should be retrieved. This is typically used for
     *            resource access or other context-related operations.
     * @return A [Bitmap] representing the cover page of the book, or `null` if the cover page is not
     *         available.
     */
    fun getCoverPage(ctx: Context): Bitmap?

    /**
     * Retrieves the title of the book.
     *
     * @return A [String] representing the title of the book.
     */
    fun getTitle(): String

    /**
     * Retrieves the full name of the book's author.
     *
     * @return A [String] representing the full name of the author, or `null` if the author's name is not
     *         available.
     */
    fun getAuthorFullName(): String?

    /**
     * Retrieves the sequence or series information of the book.
     *
     * @return A [String] representing the sequence or series information of the book, or `null` if the
     *         information is not available.
     */
    fun getSequence(): Sequence?

    fun getPublishInfo(): PublishInfo?

    /**
     * Retrieves the language in which the book is written.
     *
     * @return A [String] representing the language of the book, or `null` if the language information is
     *         not available.
     */
    fun getLanguage(): String?

    /**
     * Retrieves a list of translators who have worked on the book, if applicable.
     *
     * @return A [List] of [String] containing the names of translators, or `null` if no translators are listed.
     */
    fun getTranslators(): String?

    /**
     * Retrieves an annotation or summary of the book.
     *
     * @return A [String] representing the annotation of the book, or `null` if the annotation is not
     *         available.
     */
    fun getAnnotation(): String?

    fun getGenres(): List<String>?

    fun getKeywords(): String?
}
