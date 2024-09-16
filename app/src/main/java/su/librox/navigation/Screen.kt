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

package su.librox.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import su.librox.R

/**
 * Sealed class representing different screens in the application's navigation graph.
 *
 * @property route The unique route string used for navigating to the screen.
 * @property label The resource ID for the string resource that represents the screen's label.
 * @property iconLine The optional resource ID for the drawable resource that represents the screen's icon.
 */
sealed class Screen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val iconLine: Int? = null,
    @DrawableRes val iconFill: Int? = null
) {
    data object Library : Screen(
        route = "library",
        label = R.string.st_library,
        iconLine = R.drawable.ic_home,
        iconFill = R.drawable.ic_home_fill
    )
    data object Search : Screen(
        route = "search",
        label = R.string.st_search,
        iconLine = R.drawable.ic_search,
        iconFill = R.drawable.ic_search_fill
    )
    data object Settings : Screen(
        route = "settings",
        label = R.string.st_settings,
        iconLine = R.drawable.ic_settings,
        iconFill = R.drawable.ic_settings_fill
    )
    data object Book : Screen("book/{bookId}", R.string.st_book)
    data object Edit : Screen("edit/{bookId}", R.string.st_edit)

    companion object {
        /**
         * Retrieves a [Screen] instance based on the given route string.
         *
         * @param route The route string used to determine which [Screen] instance to return.
         * @return The [Screen] instance that matches the route, or [Library] if no match is found.
         */
        fun getDestinationByRoute(route: String?): Screen {
            return when (route) {
                Library.route -> Library
                Search.route -> Search
                Settings.route -> Settings
                Book.route -> Book
                Edit.route -> Edit
                else -> Library
            }
        }
    }
}