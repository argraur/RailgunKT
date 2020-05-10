/*
 * Copyright (C) 2020 Arseniy Graur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.argraur.railgun.utils

import me.argraur.railgun.Railgun
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

class Embed {
    private val defaultColor = Color.PINK
    private val builder: EmbedBuilder

    constructor() {
        builder = EmbedBuilder().setColor(defaultColor)
    }

    constructor(color: Color) {
        builder = EmbedBuilder().setColor(color)
    }

    fun addField(field: String, content: String): Embed {
        builder.addField(field, content, false)
        return this
    }

    fun setThumbnail(url: String): Embed {
        builder.setThumbnail(url)
        return this
    }

    fun setDefaultThumbnail(): Embed {
        return setThumbnail(Railgun.picture)
    }

    fun setDesc(description: String): Embed {
        builder.setDescription(description)
        return this
    }

    fun setTitle(title: String): Embed {
        builder.setTitle(title)
        return this
    }

    fun setTitle(title: String, url: String): Embed {
        builder.setTitle(title, url)
        return this
    }

    fun setAuthor(author: String): Embed {
        builder.setAuthor(author)
        return this
    }

    fun setImage(url: String): Embed {
        builder.setImage(url)
        return this
    }

    fun create(): MessageEmbed {
        return builder.build()
    }

    fun fastCreate(title: String, description: String): MessageEmbed {
        builder.setTitle(title)
        builder.setDescription(description)
        return builder.build()
    }
}