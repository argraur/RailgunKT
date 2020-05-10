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

class CoinFlip: Command {
    private val name = "coinflip"
    private val usage = name
    private val desc = "Flip a coin!"

    companion object {
        const val heads = "https://media.discordapp.net/attachments/698965374317625345/699883972842487879/fMemdeFmPeYAAAAAASUVORK5CYII.png"
        const val tails = "https://cdn.discordapp.com/attachments/698965374317625345/699884341219557437/Y5vgPrzC7Iazmd78B6LVfyzD7HfwBKvIh3eeptpAAAAABJRU5ErkJggg.png"
    }

    override fun exec(message: Message) {
        val side = Random(System.currentTimeMillis()).nextInt(1000) % 2
        message.channel.sendMessage(
            Embed()
                .setTitle(if (side == 0) "It's heads!" else "It's tails!")
                .setImage(if (side == 0) heads else tails)
                .setDesc(message.author.asMention)
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