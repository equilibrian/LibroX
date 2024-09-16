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

package su.librox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import su.librox.model.Book
import su.librox.model.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> get() = _inProgress

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val _relatedBooks = MutableLiveData<List<Book>>()
    val relatedBooks: LiveData<List<Book>> get() = _relatedBooks

    /**
     * Fetches a book with the specified ID from the repository and updates the LiveData.
     *
     * @param id The ID of the book to retrieve.
     */
    suspend fun getBook(id: Long) = withContext(Dispatchers.IO) {
        _book.postValue(bookRepository.getById(id))
        _relatedBooks.postValue(bookRepository.getRelated(id))
        _inProgress.postValue(false)
    }
}