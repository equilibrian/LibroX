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

@file:OptIn(ExperimentalPermissionsApi::class)

package su.librox.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import su.librox.BuildConfig
import su.librox.R

private fun handlePermissions(
    isDialogVisible: MutableState<Boolean>,
    externalStoragePermissionState: PermissionState
) {
    val permissionsGranted = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        Environment.isExternalStorageManager()
    } else {
        externalStoragePermissionState.status.isGranted
    }

    if (!permissionsGranted) {
        isDialogVisible.value = true
    }
}

private fun requestPermissions(ctx: Context, externalStoragePermissionState: PermissionState) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
        val intent = Intent(
            Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
            uri
        )
        startActivity(ctx, intent, null)
    } else {
        externalStoragePermissionState.launchPermissionRequest()
    }
}

@Composable
fun PermissionsRequestDialog() {
    val externalStoragePermissionState = rememberPermissionState(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val isVisible = remember { mutableStateOf(false) }
    val ctx = LocalContext.current

    LaunchedEffect(Unit) { handlePermissions(isVisible, externalStoragePermissionState) }

    if (isVisible.value) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.st_title_request_permissions)) },
            text = { Text(text = stringResource(R.string.st_request_permissions)) },
            onDismissRequest = { isVisible.value = false },
            confirmButton = {
                TextButton(onClick = {
                    isVisible.value = false
                    requestPermissions(ctx, externalStoragePermissionState)
                }) { Text(text = stringResource(R.string.st_continue)) }
            },
            dismissButton = {
                TextButton(onClick = { isVisible.value = false }) {
                    Text(text = stringResource(R.string.st_cancel))
                }
            }
        )
    }
}