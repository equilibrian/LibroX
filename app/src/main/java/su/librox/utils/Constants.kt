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

package su.librox.utils

import su.librox.R
import su.librox.model.TabItem
import su.librox.navigation.Screen

object Constants {
    val BOTTOM_NAVIGATION_DESTINATIONS = listOf(
        Screen.Library,
        Screen.Search,
        Screen.Settings
    )

    val LIBRARY_TABS = listOf(
        TabItem(R.string.st_books),
        TabItem(R.string.st_title_series),
        TabItem(R.string.st_authors),
    )
}