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

package su.librox.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.File
import java.io.FileOutputStream

object BitmapUtils {
    /**
     * Converts a Base64 encoded string to a `Bitmap`
     *
     * @param base64String The Base64 encoded string
     * @return `Bitmap`
     */
    fun base64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    /**
     * Saves a bitmap image to the device's internal storage.
     *
     * @param ctx The context used to access the file system.
     * @param bitmap The bitmap image to save.
     * @param fileName The name of the file to save the bitmap as.
     * @return The absolute path of the saved image, or null if an error occurred.
     */
    fun saveBitmap(ctx: Context, bitmap: Bitmap?, fileName: String): String? {
        return if (bitmap != null) {
            val dir = File(ctx.filesDir, "cover_pages")
            if (!dir.exists()) dir.mkdirs()

            val file = File(dir, "$fileName.png")
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            }

            file.absolutePath
        } else null
    }
}