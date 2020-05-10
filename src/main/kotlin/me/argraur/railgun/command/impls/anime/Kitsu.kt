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

import me.argraur.railgun.api.KitsuAPI
import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import net.dv8tion.jda.api.entities.Message

class Kitsu: Command {
    private val name = "kitsu"
    private val usage = "$name genre <genre> or $name search <query>"
    private val desc = "Search anime by query or genre"

    override fun exec(message: Message) {
        val api = KitsuAPI()
        message.channel.sendMessage(Embed().fastCreate("Kitsu", "Processing your request ${message.author.asMention}...")).queue { response ->
            val request = Format.stripCommand(message)
            when (val subCommand = request.split(" ")[0]) {
                "genre" -> {
                    response.editMessage(api.embed(api.searchGenre(request.split(" ")[1]))).queue()
                }
                "search" -> {
                    response.editMessage(api.embed(api.search(request.replace("$subCommand ", "")))).queue()
                }
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