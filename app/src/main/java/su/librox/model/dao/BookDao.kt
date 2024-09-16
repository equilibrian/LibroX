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

package su.librox.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import su.librox.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    suspend fun getAll(): List<Book>

    @Query("SELECT id, title, cover_path FROM book")
    suspend fun getAllPreview(): List<Book>

    @Query("SELECT hash FROM book")
    suspend fun getAllHashes(): List<String>

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getById(id: Long): Book

    @Query("""
        SELECT * FROM Book
        WHERE series LIKE (
            SELECT series FROM Book WHERE id = :bookId
        )
        AND id != :bookId
    """)
    suspend fun getRelated(bookId: Long): List<Book>

    @Query("SELECT * FROM book WHERE series LIKE :series AND id != :ignoreId")
    suspend fun getBySeries(series: String, ignoreId: Long? = -1): List<Book>

    @Query("SELECT * FROM book WHERE hash LIKE :hash")
    suspend fun getByHash(hash: String): Book

    @Upsert
    suspend fun upsert(vararg books: Book)

    @Delete
    suspend fun delete(book: Book)
}