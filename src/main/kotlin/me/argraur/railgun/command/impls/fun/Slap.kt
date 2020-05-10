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
import net.dv8tion.jda.api.entities.Message
import kotlin.random.Random

class Slap: Command {
    private val name = "slap"
    private val usage = "$name <mention>"
    private val desc = "Slap mentioned user!"
    companion object {
        val slaps = arrayOf(
            "https://cdn.discordapp.com/attachments/356835182818623489/699024624121282661/Slap_9.gif",
            "https://media.discordapp.net/attachments/698965374317625345/699023483266662461/Slap_1.gif",
            "https://media.giphy.com/media/AlsIdbTgxX0LC/giphy.gif",
            "https://media2.giphy.com/media/tX29X2Dx3sAXS/giphy.gif",
            "https://media2.giphy.com/media/a7HKjDb3UJ0kM/giphy.gif",
            "https://media3.giphy.com/media/xUNd9HZq1itMkiK652/giphy.gif",
            "https://i.imgur.com/4MQkDKm.gif",
            "https://i.imgur.com/o2SJYUS.gif",
            "https://i.imgur.com/Agwwaj6.gif",
            "https://i.imgur.com/YA7g7h7.gif",
            "https://i.imgur.com/CwbYjBX.gif",
            "https://i.imgur.com/0B7O5Zi.gif",
            "https://i.imgur.com/HcTCdJ1.gif",
            "https://i.imgur.com/ESD4TJ2.gif",
            "https://i.imgur.com/ZDiDDdc.gif"
        )
    }

    override fun exec(message: Message) {
        message.channel.sendMessage(
            Embed()
                .setTitle("Slap!")
                .setDesc("${message.author.asMention} slapped ${message.mentionedMembers[0].asMention}")
                .setImage(slaps[Random(System.currentTimeMillis()).nextInt(0, slaps.size)])
                .create()
        ).queue()
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