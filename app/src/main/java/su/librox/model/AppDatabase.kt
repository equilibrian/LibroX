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

import androidx.room.Database
import androidx.room.RoomDatabase
import su.librox.model.dao.BookDao

/**
 * The Room database that holds the application data.
 */
@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the data access object (DAO) for books.
     *
     * @return The [BookDao] instance.
     */
    abstract fun bookDao() : BookDao
}