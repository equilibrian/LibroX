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

package su.librox.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import su.librox.exceptions.BookCreationFailed
import su.librox.exceptions.UnsupportedBookException
import su.librox.model.Book
import su.librox.model.BookManager
import su.librox.model.fictionbook.FictionBook
import su.librox.model.fictionbook.PublishInfo
import su.librox.model.fictionbook.Sequence
import su.librox.model.repository.BookRepository
import su.librox.utils.BitmapUtils
import su.librox.utils.FileUtils.calculateSHA256Hash
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _isScanning = MutableLiveData<Boolean>()
    val isScanning: LiveData<Boolean> get() = _isScanning

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val tmpBooks: MutableList<Book> = mutableListOf()

    private suspend fun addFictionBook(ctx: Context, file: File, hash: String) = try {
        val book = BookManager.createBook(file)
        val coverPage: Bitmap? = book.getCoverPage(ctx)
        val publishInfo: PublishInfo? = book.getPublishInfo()
        val series: Sequence? = book.getSequence()
        val newBook = Book(
            coverPath = BitmapUtils.saveBitmap(ctx, coverPage, hash),
            title = book.getTitle(),
            authorName = book.getAuthorFullName(),
            annotation = FictionBook.extractAnnotation(file),
            series = series?.name,
            volumeNumber = series?.number,
            publisher = publishInfo?.publisher,
            year = publishInfo?.year,
            lang = book.getLanguage(),
            translator = book.getTranslators(),
            isbn = publishInfo?.isbn,
            keywords = book.getKeywords(),
            filePath = file.path,
            hash = hash
        )
        tmpBooks.add(newBook)
        _books.postValue(tmpBooks.toList()) // TODO: так нельзя, т.к при первом запуске невозможно открыть детали
    } catch (ex: BookCreationFailed) {
        Timber.d(ex)
    }

    suspend fun scanDevice(ctx: Context) = withContext(Dispatchers.IO) {
        _isScanning.postValue(true)
        BookManager.scan(ctx).takeIf { it.isNotEmpty() }?.let { files ->
            val hashes: List<String> = bookRepository.getAllHashes()
            Timber.d("Found ${files.size} files and ${hashes.size} hashes")

            files.forEach { file ->
                try {
                    val hash = file.calculateSHA256Hash()
                    if (hash !in hashes)
                        addFictionBook(ctx, file, hash)
                } catch (ex: UnsupportedBookException) {
                    Timber.e(ex)
                }
            }

            if (tmpBooks.isNotEmpty()) bookRepository.upsert(*tmpBooks.toTypedArray())

            _books.postValue(
                if (tmpBooks.isNotEmpty())
                    tmpBooks.toList()
                else
                    bookRepository.getAllPreview()
            )
        }

        _isScanning.postValue(false)

        return@withContext
    }
}