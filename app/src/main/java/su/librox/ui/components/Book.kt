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

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import su.librox.R

/**
 * A composable that displays a book item with a cover image and an optional title.
 *
 * @param title The optional title of the book. If not provided, the title section will be omitted.
 * @param cover A [Uri] representing the cover image of the book. It can be `null` if no cover is available.
 * @param aspectRatio The aspect ratio of the cover image. Default is 0.7f (width / height).
 * @param onClick will be called when user clicks on the element.
 */
@Composable
fun Book(
    title: String? = null,
    cover: Uri?,
    aspectRatio: Float = 0.7f,
    onClick: () -> Unit,
    imageLoader: ImageLoader
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = cover,
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(4.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(4.dp)),
            placeholder = painterResource(id = R.drawable.cover_placeholder),
            error = painterResource(id = R.drawable.cover_placeholder),
            contentScale = ContentScale.FillBounds,
            filterQuality = FilterQuality.Medium
        )

        title?.let {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}