/*
 * LibroX allows users to conveniently read e-books in various formats.
 * Copyright (C) 2024 Denis Levshchanov
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
 * Email: equilibrian@yandex.ru
 */

package su.librox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import su.librox.ui.screen.BookScreen
import su.librox.ui.screen.MainScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {
        composable(route = Graph.MAIN) {
            MainScreen(rootNavController = navController)
        }

        val args = listOf(navArgument("bookId") { type = NavType.LongType })
        composable(route = Screen.Book.route, arguments = args) { backStackEntry ->
            backStackEntry.arguments?.getLong("bookId")?.let { id ->
                BookScreen(navController = navController, bookId = id)
            }
        }
        /*composable(route = Screen.Edit.route, arguments = args) { backStackEntry ->
            backStackEntry.arguments?.getInt("bookId")?.let {
                EditScreen(navController = navController, bookId = it)
            }
        }*/
    }
}