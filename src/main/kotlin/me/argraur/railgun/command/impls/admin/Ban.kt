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

package me.argraur.railgun.command.impls.admin

import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Level
import net.dv8tion.jda.api.entities.Message

class Ban: Command {
    private val name = "ban"
    private val usage = "$name <mention>"
    private val desc = "Bans mentioned user"
    private val level = Level.BAN
    
    override fun exec(message: Message) {
        message.guild.ban(message.mentionedMembers[0], 0)
        message.channel.sendMessage(Embed().fastCreate("Banned!", "${message.mentionedMembers[0].asMention} was banned by ${message.author.asMention}")).queue()
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

    override fun getLevel(): Level {
        return level
    }
}