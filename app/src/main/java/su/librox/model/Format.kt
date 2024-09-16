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

import java.io.File

/**
 * Enum class representing supported e-book formats.
 *
 * @property extension The file extension associated with the format.
 * @property mimeTypes The list of MIME types associated with the format.
 */
enum class Format(val extension: String, val mimeTypes: List<String>) {
    FB2("fb2", listOf("application/x-fictionbook+xml", "application/octet-stream"));

    companion object {
        /**
         * Determines the supported format of a given file.
         *
         * @param file The file to check.
         * @return The [Format] if the file matches a supported format, or null otherwise.
         */
        fun from(file: File): Format? {
            val ext = file.extension.lowercase()
            val mimeType = getMimeType(ext)

            return entries.find { format ->
                format.extension == ext && format.mimeTypes.contains(mimeType)
            }
        }

        /**
         * Retrieves the MIME type associated with a given file extension.
         *
         * @param extension The file extension.
         * @return The MIME type as a [String], or null if the extension is not recognized.
         */
        private fun getMimeType(extension: String): String? {
            return when (extension) {
                "fb2" -> "application/x-fictionbook+xml"
                "epub" -> "application/epub+zip"
                "mobi" -> "application/x-mobipocket-ebook"
                else -> null
            }
        }
    }
}