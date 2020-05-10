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

package me.argraur.railgun.command.impls.`fun`

import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import kotlin.random.Random


class EightBall: Command {
    private val name = "8ball"
    private val usage = "$name <question>"
    private val desc = "Get answer to all of your questions!"
    private val replies = arrayOf(
        "It is certain", "It is decidedly so", "Without a doubt", "Yes - definitely",
        "You may rely on it", "As I see it, yes", "Most likely", "Outlook good", "Signs point to yes", "Yes",
        "Reply hazy, try again", "Ask again later", "Better not tell you now", "Cannot predict now",
        "Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
        "Outlook not so good", "Very doubtful"
    )

    override fun exec(message: Message) {
        message.channel.sendMessage(message.author.asMention).queue { response ->
            response.editMessage(
                Embed()
                    .setTitle(replies[Random(System.currentTimeMillis()).nextInt(replies.size)])
                    .setDesc(Format.stripCommand(message))
                    .create()
            ).queue()
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