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

import me.argraur.railgun.Railgun
import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import net.dv8tion.jda.api.entities.Message

class Sauce: Command {
    private val name = "sauce"
    private val usage = "$name <imgurl> or attach image"
    private val desc = "Finds image's source"

    override fun exec(message: Message) {
        val url = Format.imageUrl(message)
        message.channel.sendMessage(
            Embed()
                .setTitle("Processing image...")
                .setDesc(message.author.asMention)
                .setImage(url)
                .create()
        ).queue { response ->
            try {
                val result = Railgun.sauceNAO.embed(Railgun.sauceNAO.search(message)!!)
                message.delete().queue()
                response.editMessage(result).queue()
            } catch (e: Exception) {
                e.printStackTrace()
                response.editMessage(Embed().setTitle("Processing failed. No source links were found.").create()).queue()
            }
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