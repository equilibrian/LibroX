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
import android.database.Cursor
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import su.librox.exceptions.BookCreationFailed
import su.librox.exceptions.UnsupportedBookException
import su.librox.model.fictionbook.FictionBook
import timber.log.Timber
import java.io.File

object BookManager {
    private const val OCTET_STREAM = "application/octet-stream"

    /**
     * Creates a `Book` instance from the provided file.
     *
     * @param file The file to create the book from.
     * @return A `Book` instance.
     * @throws UnsupportedBookException If a book format does not supported.
     * @throws BookCreationFailed If failed to parse book.
     */
    @Throws(UnsupportedBookException::class, BookCreationFailed::class)
    suspend fun createBook(file: File): IBook = withContext(Dispatchers.IO) {
        val format = Format.from(file)

        when (format) {
            Format.FB2 -> createFictionBook(file) ?: throw BookCreationFailed()
            null -> throw UnsupportedBookException(format = file.extension)
        }
    }

    /**
     * Creates a `FictionBook` instance from the provided file.
     *
     * @param file The file to create the FictionBook from.
     * @return A `FictionBook` instance parsed from the file.
     */
    private suspend fun createFictionBook(file: File): IBook? = FictionBook.from(file)

    /**
     * Scans the device for books and returns a list of found books.
     *
     * Performs a query on the device's external storage
     * to find files that match specific MIME types.
     *
     * @param ctx The context used to access the content resolver.
     * @return A list of books found on the device.
     * @throws IllegalArgumentException If a column does not exist in the cursor.
     */
    suspend fun scan(ctx: Context): List<File> = withContext(Dispatchers.IO) {
        Timber.d("Scanning device for books")
        val booksFiles: MutableList<File> = mutableListOf()
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE
        )
        val selection = MediaStore.Files.FileColumns.MIME_TYPE + " IN (?, ?)"
        val selectionArgs = arrayOf(
            "application/x-fictionbook+xml",
            "application/octet-stream"
        )
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
        val queryUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val cursor: Cursor? = ctx.contentResolver
            .query(queryUri, projection, selection, selectionArgs, sortOrder)

        cursor?.use {
            val dataIdx = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val mimeTypeIdx = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)

            while (it.moveToNext()) {
                val file = File(it.getString(dataIdx))
                val mimeType = it.getString(mimeTypeIdx)

                if (mimeType.equals(OCTET_STREAM) && !file.extension.equals("fb2", true)) {
                    continue
                }

                booksFiles.add(file)
            }
        }

        booksFiles
    }
}