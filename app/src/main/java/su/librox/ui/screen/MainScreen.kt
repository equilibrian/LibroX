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

package su.librox.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import su.librox.navigation.MainNavGraph
import su.librox.navigation.Screen
import su.librox.utils.Constants
import su.librox.viewmodel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: Screen
) {
    TopAppBar(
        title = {
            Crossfade(targetState = currentScreen.label, label = "") { target ->
                Text(text = stringResource(target))
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}

@Composable
private fun BottomNavigation(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar {
        Constants.BOTTOM_NAVIGATION_DESTINATIONS.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        selectedItem = index
                        navController.navigate(screen.route)
                    }
                },
                label = { Text(text = stringResource(screen.label)) },
                icon = {
                    Crossfade(targetState = selectedItem == index, label = "") { ts ->
                        when {
                            ts -> Icon(
                                imageVector = ImageVector.vectorResource(screen.iconFill!!),
                                contentDescription = null
                            )
                            else -> Icon(
                                imageVector = ImageVector.vectorResource(screen.iconLine!!),
                                contentDescription = null
                            )
                        }

                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen by remember {
        derivedStateOf { Screen.getDestinationByRoute(navBackStackEntry?.destination?.route) }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AppBar(scrollBehavior = scrollBehavior, currentScreen = currentScreen) },
        content = { paddingValues ->
            MainNavGraph(
                navController = navController,
                rootNavController = rootNavController,
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        },
        bottomBar = { BottomNavigation(navController = navController) }
    )
}