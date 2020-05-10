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

import me.argraur.railgun.Railgun
import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import me.argraur.railgun.utils.Level
import net.dv8tion.jda.api.entities.Message

class Prefix: Command {
    private val name = "prefix"
    private val usage = "$name <prefix>"
    private val desc = "Sets new command prefix for this guild"
    private val level = Level.ADMIN

    override fun exec(message: Message) {
        val content = Format.stripCommand(message)
        when {
            (content == "") -> message.channel.sendMessage(Embed().fastCreate("Current prefix", Format.shellYellow(Railgun.prefix.getPrefix(message)))).queue()
            else -> {
                Railgun.prefix.setPrefix(message, content)
                message.channel.sendMessage(Embed().fastCreate("Set new prefix", Format.shellYellow(Railgun.prefix.getPrefix(message)))).queue()
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

    override fun getLevel(): Level {
        return level
    }
}