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

package su.librox.exceptions

/**
 * Exception thrown when the application encounters a book or file format that is not supported.
 *
 *
 * @param format The unsupported format or file type that triggered the exception. This can help in
 *                providing a more specific error message.
 * @param message A custom message that provides additional details about the exception. If not
 *                provided, a default message is used.
 *
 * Example usage:
 * ```
 * throw UnsupportedBookException(format = "EPUB", message = "The EPUB format is not supported.")
 * ```
 *
 * This would result in an exception with the message: "The EPUB format is not supported. Format: EPUB"
 */
class UnsupportedBookException(
    val format: String,
    message: String? = "The specified book format is not supported."
) : Exception(message) {
    private val text: String? = message

    override val message: String?
        get() = text?.let { "$it Format: $format" } ?: super.message
}
