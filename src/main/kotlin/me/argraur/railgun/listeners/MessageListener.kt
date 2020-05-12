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

package me.argraur.railgun.listeners

import me.argraur.railgun.Railgun
import me.argraur.railgun.command.Command
import me.argraur.railgun.command.impls.`fun`.*
import me.argraur.railgun.command.impls.admin.Ban
import me.argraur.railgun.command.impls.admin.Kick
import me.argraur.railgun.command.impls.admin.Prefix
import me.argraur.railgun.command.impls.admin.Purge
import me.argraur.railgun.command.impls.anime.Kitsu
import me.argraur.railgun.command.impls.anime.Sauce
import me.argraur.railgun.command.impls.anime.Wait
import me.argraur.railgun.command.impls.owner.Shell
import me.argraur.railgun.command.impls.owner.Shutdown
import me.argraur.railgun.command.impls.utils.Blur
import me.argraur.railgun.command.impls.utils.Color
import me.argraur.railgun.command.impls.utils.Help
import me.argraur.railgun.command.impls.utils.Ping
import me.argraur.railgun.jenkins.commands.Build
import me.argraur.railgun.utils.checkLevel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {
    val commands: LinkedHashMap<String, Command> = LinkedHashMap()

    private fun registerCommand(command: Command) {
        commands[command.getName()] = command
        Railgun.logger.D("Registered command ${command.getName()}", this.javaClass.simpleName)
    }

    private fun commandExists(command: String): Boolean {
        Railgun.logger.D("Checking if command $command exists...", this.javaClass.simpleName)
        if (commands[command] != null) {
            Railgun.logger.D("Command $command exists.", this.javaClass.simpleName)
            return true
        }
        Railgun.logger.E("Command $command doesn't exist.", this.javaClass.simpleName)
        return false
    }

    init {
        Railgun.logger.I("Registering commands", this.javaClass.simpleName)
        registerCommand(Ban())
        registerCommand(Blur())
        registerCommand(Build())
        registerCommand(CoinFlip())
        registerCommand(Color())
        registerCommand(EightBall())
        registerCommand(Help())
        registerCommand(Hug())
        registerCommand(Juggle())
        registerCommand(Kick())
        registerCommand(Kitsu())
        registerCommand(Mirror())
        registerCommand(Ping())
        registerCommand(Prefix())
        registerCommand(Purge())
        registerCommand(Sauce())
        registerCommand(Shell())
        registerCommand(Shutdown())
        registerCommand(Slap())
        registerCommand(Wait())
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        if (message.author.isBot) {
            Railgun.logger.D("Bot's message. Do nothing.", this.javaClass.simpleName)
            return
        }
        Railgun.logger.D("Received message from guild ${message.guild.name}/${message.guild.id}", this.javaClass.simpleName)
        Railgun.logger.D("                 from member ${message.author.name}/${message.author.asTag}", this.javaClass.simpleName)
        Railgun.logger.D("                 with content: ${message.contentRaw}", this.javaClass.simpleName)
        if (!message.contentRaw.startsWith(Railgun.prefix.getPrefix(message))) {
            Railgun.logger.D("Normal message. Do nothing.", this.javaClass.simpleName)
            return
        }
        val command = Railgun.prefix.filterPrefix(message).split(" ")[0]
        if (commandExists(command)) {
            Railgun.logger.I("Command received: $command", this.javaClass.simpleName)
            if (checkLevel(message, commands[command]!!)) {
                Railgun.logger.I("Executing command $command", this.javaClass.simpleName)
                commands[command]?.exec(message)
            }
            else return
        }
    }
}