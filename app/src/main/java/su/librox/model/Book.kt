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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["hash"], unique = true)])
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "cover_path") val coverPath: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author_name") val authorName: String?,
    @ColumnInfo(name = "annotation") val annotation: String?,
    @ColumnInfo(name = "series") val series: String?,
    @ColumnInfo(name = "volume_number") val volumeNumber: String?,
    @ColumnInfo(name = "publisher") val publisher: String?,
    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "lang") val lang: String?,
    @ColumnInfo(name = "translator") val translator: String?,
    @ColumnInfo(name = "keywords") val keywords: String?,
    @ColumnInfo(name = "isbn") val isbn: String?,
    @ColumnInfo(name = "file_path") val filePath: String?,
    @ColumnInfo(name = "hash") val hash: String?,
    @ColumnInfo(name = "is_favourite") val isFavourite: Boolean? = false
)