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

class Hug: Command {
    private val name = "hug"
    private val usage = "$name <mention"
    private val desc = "Hugs a user <3"
    companion object {
        private val hugs = arrayOf(
            "https://i.imgur.com/GuADSLm.gif",
            "https://i.imgur.com/XEs1SWQ.gif",
            "https://i.imgur.com/snm8S1B.gif",
            "https://i.imgur.com/IoSM9JM.gif",
            "https://i.imgur.com/RPYNm9o.gif",
            "https://i.imgur.com/hgXcoiU.gif",
            "https://i.imgur.com/hA9ZNoR.gif",
            "https://i.imgur.com/t8Ghkkm.gif",
            "https://i.imgur.com/TYkINci.gif",
            "https://i.imgur.com/kNHDZBI.gif",
            "https://i.imgur.com/14FwOef.gif",
            "https://i.imgur.com/KFZNUZl.gif",
            "https://i.imgur.com/hm9aSze.gif",
            "https://i.imgur.com/ReFdPgW.gif",
            "https://i.imgur.com/V4n129V.gif",
            "https://i.imgur.com/bT766fG.gif",
            "https://i.imgur.com/4lkCPC7.gif",
            "https://i.imgur.com/i5vwbos.gif",
            "https://i.imgur.com/7BTlA2F.gif",
            "https://i.imgur.com/gI5qiWQ.gif",
            "https://i.imgur.com/FtWNVPU.gif"
        )
    }

    override fun exec(message: Message) {
        message.channel.sendMessage(
            Embed()
                .setDesc("${message.author.asMention} (つ✧ω✧)つ")
                .setImage(hugs[Random(System.currentTimeMillis()).nextInt(0, hugs.size)])
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