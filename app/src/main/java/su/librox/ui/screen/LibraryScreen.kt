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

package su.librox.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import su.librox.model.Book
import su.librox.ui.components.Collection
import su.librox.ui.components.PermissionsRequestDialog
import su.librox.utils.Constants
import su.librox.viewmodel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    rootNavController: NavController,
    viewModel: MainScreenViewModel
) {
    val ctx = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        Constants.LIBRARY_TABS.size
    }
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    LaunchedEffect(Unit) {
        viewModel.scanDevice(ctx)
    }

    val books: List<Book> by viewModel.books.observeAsState(emptyList())
    val isScanning: Boolean by viewModel.isScanning.observeAsState(false)

    Column(modifier = Modifier.fillMaxSize()) {
        PrimaryTabRow(selectedTabIndex = selectedTab) {
            Constants.LIBRARY_TABS.forEachIndexed { index, item ->
                Tab(
                    text = { Text(stringResource(item.title)) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { index ->
            when (index) {
                0 -> Collection(
                    items = books,
                    isScanning = isScanning && books.isEmpty(),
                    navController = rootNavController
                )
                else -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(Constants.LIBRARY_TABS[index].title),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    PermissionsRequestDialog()
}