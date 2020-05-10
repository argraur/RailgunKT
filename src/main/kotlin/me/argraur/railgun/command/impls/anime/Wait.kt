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

package me.argraur.railgun.command.impls.anime

import me.argraur.railgun.api.TraceMoeAPI
import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import me.argraur.railgun.utils.ImageColor

import net.dv8tion.jda.api.entities.Message

import java.awt.Color

class Wait: Command {
    private val name = "wait"
    private val usage = "$name <url> or attachment"
    private val desc = "Searches anime by given screenshot from it"

    override fun exec(message: Message) {
        val image = Format.imageUrl(message)
        message.channel.sendMessage(Embed(Color.decode("#${ImageColor.getColor(image)}")).setTitle("What Anime Is This?").setDesc("Processing screenshot for you, ${message.author.asMention} <3").setImage(image).create()).queue {response ->
            response.editMessage(TraceMoeAPI.embed(TraceMoeAPI.search(image)).setThumbnail(image).create()).queue()
        }
    }

    override fun getName(): String {
        return name
    }

    override fun getUsage(): String {
        return usage
    }

    override fun getDesc(): String {
        return desc
    }
}