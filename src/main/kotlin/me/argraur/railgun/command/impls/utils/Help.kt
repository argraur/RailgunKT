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

package me.argraur.railgun.command.impls.utils

import me.argraur.railgun.Railgun
import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import net.dv8tion.jda.api.entities.Message

class Help: Command {
    private val name = "help"
    private val usage = "$name <command>"
    private val desc = "Returns help for given command"

    override fun exec(message: Message) {
        val commandName = Format.stripCommand(message)
        val embed = Embed()
        message.channel.sendMessage(
            if (commandName == "") {
                var avCommands = ""
                for (command: Command in Railgun.listener.commands.values) {
                    avCommands += "${command.getName()} "
                }
                embed
                    .setTitle(name)
                    .addField("Usage", Format.shellYellow(Format.addPrefix(message, usage)))
                    .addField("Description", Format.shellYellow(desc))
                    .addField("Available commands", Format.shellYellow(avCommands))
                    .create()
            } else {
                val command = Railgun.listener.commands[commandName]
                embed
                    .setTitle(command!!.getName())
                    .addField("Usage", Format.shellYellow(Format.addPrefix(message, command.getUsage())))
                    .addField("Description", Format.shellYellow(command.getDesc()))
                    .create()
            }
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