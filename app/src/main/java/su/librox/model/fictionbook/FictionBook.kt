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

package su.librox.model.fictionbook

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister
import org.w3c.dom.Document
import su.librox.model.IBook
import su.librox.utils.BitmapUtils
import timber.log.Timber
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Represents a FictionBook.
 *
 * @property description The description of the FictionBook.
 * @property binaries The list of binaries associated with the FictionBook.
 */
@Root(name = "FictionBook", strict = false)
data class FictionBook(
    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: Description,

    @field:ElementList(name = "binary", inline = true, required = false, entry = "binary")
    @param:ElementList(name = "binary", inline = true, required = false, entry = "binary")
    val binaries: List<Binary>?
) : IBook {
    override fun getTitle(): String = description.titleInfo.bookTitle

    override fun getAuthorFullName(): String? {
        val persons = description.titleInfo.authors
        return persons.map { it.getFullName() }
            .takeIf { it.isNotEmpty() }
            ?.joinToString(", ")
    }

    override fun getSequence(): Sequence? = description.titleInfo.sequence?.last()

    override fun getPublishInfo(): PublishInfo? = description.publishInfo

    override fun getLanguage(): String? = description.titleInfo.lang

    override fun getTranslators(): String? =
        description.titleInfo.translators?.joinToString(", ") { it.getFullName() }

    override fun getAnnotation(): String? {
        return null
    }

    override fun getGenres(): List<String>? = description.titleInfo.genre

    override fun getKeywords(): String? = description.titleInfo.keywords

    override fun getCoverPage(ctx: Context): Bitmap? {
        val coverPageId: String? = description.titleInfo.coverPage?.image?.imageId

        val binary = binaries?.find { it.id == coverPageId }
        if (binary?.id == coverPageId) return binary?.value?.let { BitmapUtils.base64ToBitmap(it) }

        return null
    }

    companion object {
        private val serializer: Persister = Persister()

        suspend fun extractAnnotation(file: File): String? = withContext(Dispatchers.IO) {
            try {
                val factory = DocumentBuilderFactory.newInstance()
                val builder = factory.newDocumentBuilder()
                val document: Document = builder.parse(file)
                document.documentElement.normalize()

                val annotationNode = document.getElementsByTagName("annotation").item(0)
                val rawText = annotationNode?.textContent?.trim()
                val cleanedText = rawText?.replace(Regex("\\s+"), " ")
                return@withContext cleanedText
            } catch (ex: Exception) {
                Timber.e(ex, "file: ${file.path}")
                return@withContext null
            }
        }


        /**
         * Constructs a [FictionBook] instance from XML content in a file.
         *
         * @param file The file containing XML data representing the fiction book.
         * @return A [FictionBook] instance parsed from the XML file.
         */
        suspend fun from(file: File): FictionBook? = withContext(Dispatchers.IO) {
            try {
                serializer.read(FictionBook::class.java, file)
            } catch (ex: Exception) {
                Timber.e(ex, "file: ${file.path}")
                null
            }
        }
    }
}