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

package su.librox.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import su.librox.R

@Composable
fun BookPreviewCard(
    modifier: Modifier = Modifier,
    coverPageUri: Uri?,
    title: String,
    authorName: String,
    progress: Float = 0f,
    imageLoader: ImageLoader
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .requiredHeight(136.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = coverPageUri,
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier.aspectRatio(0.75f),
                placeholder = painterResource(id = R.drawable.cover_placeholder),
                error = painterResource(id = R.drawable.cover_placeholder),
                contentScale = ContentScale.FillBounds
            )

            Content(
                title = title,
                authorName = authorName,
                progress = progress
            )
        }
    }
}

@Composable
private fun Content(
    title: String,
    authorName: String,
    progress: Float
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(
            text = authorName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .width(128.dp)
                .padding(top = 6.dp),
            strokeCap = StrokeCap.Round
        )
        TextButton(
            onClick = {  },
            contentPadding = PaddingValues(horizontal = 0.dp),
            modifier = Modifier.height(24.dp)
        ) {
            Text(text = stringResource(id = R.string.st_read))
            Icon(
                imageVector = ImageVector
                    .vectorResource(id = R.drawable.ic_right_small),
                contentDescription = null,
            )
        }
    }
}