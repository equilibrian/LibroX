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

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Represents a person involved with the book.
 *
 * @property id The ID of the person.
 * @property firstName The first name of the person.
 * @property middleName The middle name of the person.
 * @property lastName The last name of the person.
 * @property nickname The nickname of the person.
 * @property homePages The home pages of the person.
 * @property emails The emails of the person.
 */
@Root(strict = false)
data class Person @JvmOverloads constructor(
    @param:Element(name = "id", required = false)
    @field:Element(name = "id", required = false)
    val id: String? = null,

    @param:Element(name = "first-name", required = false)
    @field:Element(name = "first-name", required = false)
    val firstName: String? = null,

    @param:Element(name = "middle-name", required = false)
    @field:Element(name = "middle-name", required = false)
    val middleName: String? = null,

    @param:Element(name = "last-name", required = false)
    @field:Element(name = "last-name", required = false)
    val lastName: String? = null,

    @param:Element(name = "nickname", required = false)
    @field:Element(name = "nickname", required = false)
    val nickname: String? = null,

    @param:ElementList(name = "home-page", inline = true, required = false, entry = "home-page")
    @field:ElementList(name = "home-page", inline = true, required = false, entry = "home-page")
    val homePages: List<String>? = null,

    @param:ElementList(name = "email", inline = true, required = false, entry = "email")
    @field:ElementList(name = "email", inline = true, required = false, entry = "email")
    val emails: List<String>? = null
) {
    fun getFullName(): String = when {
        !firstName.isNullOrBlank() || !middleName.isNullOrBlank() || !lastName.isNullOrBlank() -> {
            listOfNotNull(firstName, middleName, lastName).joinToString(" ")
        }
        else -> nickname ?: ""
    }
}