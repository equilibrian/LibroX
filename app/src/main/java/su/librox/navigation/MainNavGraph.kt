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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import su.librox.ui.screen.LibraryScreen
import su.librox.ui.screen.SearchScreen
import su.librox.ui.screen.SettingsScreen
import su.librox.viewmodel.MainScreenViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    viewModel: MainScreenViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Screen.Library.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Library.route) {
            LibraryScreen(rootNavController = rootNavController, viewModel = viewModel)
        }
        composable(Screen.Search.route) {
            SearchScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}