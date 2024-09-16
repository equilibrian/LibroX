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

package su.librox.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.ImageLoader
import coil.request.CachePolicy
import su.librox.R
import su.librox.model.Book

enum class LayoutType {
    Grid, Row
}

@Composable
private fun <T> CollectionItem(
    item: T,
    showLabel: Boolean,
    imageLoader: ImageLoader,
    navController: NavController
) {
    when (item) {
        is Book -> Book(
            title = if (showLabel) item.title else null,
            cover = item.coverPath?.toUri(),
            onClick = { navController.navigate("book/${item.id}") },
            imageLoader = imageLoader
        )
        else -> throw IllegalArgumentException("Unsupported item type: ${item!!::class.simpleName}")
    }
}

/**
 * Displays a collection of items.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param items The list of items to be displayed in the collection.
 * @param showItemsLabel A flag to enable/disable collection items label. Only used if `layoutType` is `LayoutType.Grid`.
 * @param showPlaceholder Whether to show a placeholder when the collection is empty.
 * @param isScanning A flag to indicate whether a loading placeholder should be displayed.
 * @param layoutType Defines whether the collection should be displayed in a grid or a row layout.
 * @param columns The number of columns to use in the grid layout. Only used if `layoutType` is `LayoutType.Grid`.
 * @param contentPadding Padding around the collection items.
 * @param imageLoader An instance of `ImageLoader` used to load images for the items.
 * @param navController Navigation controller to handle navigation when an item is clicked.
 * @throws IllegalArgumentException if an unsupported item type is provided.
 */
@Composable
fun <T> Collection(
    modifier: Modifier = Modifier,
    items: List<T>,
    showItemsLabel: Boolean = true,
    showPlaceholder: Boolean = true,
    isScanning: Boolean = false,
    layoutType: LayoutType = LayoutType.Grid,
    columns: GridCells = GridCells.Adaptive(minSize = 114.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    imageLoader: ImageLoader = rememberImageLoader(),
    navController: NavController
) {
    if (isScanning && showPlaceholder) LoadingPlaceholder()
    else if (items.isEmpty() && showPlaceholder)
        Placeholder(label = R.string.st_empty_collection, icon = R.drawable.open_book)
    else {
        when (layoutType) {
            LayoutType.Grid -> {
                LazyVerticalGrid(
                    columns = columns,
                    modifier = modifier,
                    contentPadding = contentPadding,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items.size) { idx ->
                        CollectionItem(items[idx], showItemsLabel, imageLoader, navController)
                    }
                }
            }
            LayoutType.Row -> {
                LazyRow(
                    modifier = modifier,
                    contentPadding = contentPadding,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(items.size) { idx ->
                        CollectionItem(items[idx], false, imageLoader, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun rememberImageLoader(): ImageLoader {
    val ctx = LocalContext.current
    return remember {
        ImageLoader.Builder(ctx)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .crossfade(true)
            .allowRgb565(true)
            .addLastModifiedToFileCacheKey(false)
            .build()
    }
}