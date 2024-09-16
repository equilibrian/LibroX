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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import su.librox.R
import su.librox.model.Book
import su.librox.ui.components.BookPreviewCard
import su.librox.ui.components.Collection
import su.librox.ui.components.ExpandableText
import su.librox.ui.components.LayoutType
import su.librox.ui.components.LoadingPlaceholder
import su.librox.ui.components.rememberImageLoader
import su.librox.viewmodel.BookScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    isTitleVisible: Boolean,
    title: String,
    navController: NavController
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isTitleVisible)
            MaterialTheme.colorScheme.surfaceContainer
        else
            MaterialTheme.colorScheme.background,
        label = "Animated color"
    )
    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = isTitleVisible,
                enter = fadeIn(animationSpec = tween()),
                exit = fadeOut(animationSpec = tween())
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
    )
}

@Composable
private fun RelatedBooks(
    modifier: Modifier = Modifier,
    books: List<Book>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(164.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.st_other_books_in_series),
                style = MaterialTheme.typography.titleSmall
            )

            TextButton(
                onClick = {},
                contentPadding = PaddingValues(horizontal = 0.dp),
                modifier = Modifier.height(24.dp)
            ) {
                Text(text = stringResource(id = R.string.st_show_all))
            }
        }

        Collection(
            items = books,
            showPlaceholder = false,
            layoutType = LayoutType.Row,
            contentPadding = PaddingValues(horizontal = 12.dp),
            navController = navController
        )
    }
}

@Composable
fun BookDetails(book: Book) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(id = R.string.st_about_file),
            style = MaterialTheme.typography.titleSmall
        )

        @Composable
        fun DetailItem(label: String, value: String?) {
            value?.let {
                Text(
                    text = "$label: $value",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        DetailItem(stringResource(R.string.st_series), book.series)
        DetailItem(stringResource(R.string.st_volume), book.volumeNumber)
        DetailItem(stringResource(R.string.st_publisher), book.publisher)
        DetailItem(stringResource(R.string.st_year), book.year)
        DetailItem(stringResource(R.string.st_lang), book.lang)
        DetailItem(stringResource(R.string.st_translator), book.translator)
        DetailItem(stringResource(R.string.st_isbn), book.isbn)
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: LazyListState,
    book: Book,
    relatedBooks: List<Book>?,
    navController: NavController
) {
    LazyColumn(
        state = state,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(bottom = 8.dp)
    ) {
        item {
            BookPreviewCard(
                modifier = Modifier.padding(horizontal = 12.dp),
                coverPageUri = book.coverPath?.toUri(),
                title = book.title,
                authorName = book.authorName ?: stringResource(R.string.st_unknown),
                progress = 0f,
                imageLoader = rememberImageLoader()
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 12.dp),
                thickness = (0.5).dp
            )
        }

        item {
            ExpandableText(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .animateContentSize(),
                text = book.annotation ?: stringResource(R.string.st_no_descr),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (!relatedBooks.isNullOrEmpty()) {
            item {
                RelatedBooks(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    books = relatedBooks,
                    navController = navController
                )
            }
        }

        item { BookDetails(book) }
    }
}

@Composable
fun BookScreen(
    navController: NavController,
    viewModel: BookScreenViewModel = hiltViewModel(),
    bookId: Long
) {
    LaunchedEffect(Unit) { viewModel.getBook(bookId) }

    val contentState = rememberLazyListState()
    val inProgress by viewModel.inProgress.observeAsState(true)
    val book: Book? by viewModel.book.observeAsState(null)
    val relatedBooks: List<Book>? by viewModel.relatedBooks.observeAsState(null)

    Scaffold(
        topBar = {
            book?.title?.let {
                AppBar(
                    isTitleVisible = contentState.firstVisibleItemIndex != 0,
                    title = it, navController
                )
            }
        },
        content = { paddingValues ->
            Crossfade(targetState = !inProgress, label = "") { ts ->
                when {
                    ts -> {
                        book?.let {
                            Content(
                                modifier = Modifier.padding(paddingValues),
                                state = contentState,
                                book = it,
                                relatedBooks = relatedBooks,
                                navController = navController
                            )
                        }
                    }
                    else -> LoadingPlaceholder()
                }
            }
        }
    )
}