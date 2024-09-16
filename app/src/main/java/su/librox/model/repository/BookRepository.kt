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

package su.librox.model.repository

import su.librox.model.Book
import su.librox.model.dao.BookDao
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookDao: BookDao) {
    suspend fun getAll(): List<Book> = bookDao.getAll()

    suspend fun getAllPreview(): List<Book> = bookDao.getAllPreview()

    suspend fun getAllHashes(): List<String> = bookDao.getAllHashes()

    suspend fun getById(id: Long): Book = bookDao.getById(id)

    suspend fun getRelated(bookId: Long): List<Book> = bookDao.getRelated(bookId)

    suspend fun getAllBySeries(series: String, ignoreId: Long): List<Book> =
        bookDao.getBySeries(series, ignoreId)

    suspend fun findByHash(hash: String): Book = bookDao.getByHash(hash)

    suspend fun upsert(vararg books: Book) = bookDao.upsert(*books)

    suspend fun delete(book: Book) = bookDao.delete(book)
}