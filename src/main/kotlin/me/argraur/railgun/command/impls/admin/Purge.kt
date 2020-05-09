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
import me.argraur.railgun.utils.Format
import me.argraur.railgun.utils.Level
import net.dv8tion.jda.api.entities.Message
import java.lang.NumberFormatException

class Purge: Command {
    private val name = "purge"
    private val level = Level.MESSAGE

    override fun exec(message: Message) {
        val content = Format.stripCommand(message)
        try {
            val amount: Int = content.toInt()
            message.channel.purgeMessages(message.channel.history.retrievePast(amount + 1).complete())
        } catch (e: NumberFormatException) {
            val id = content.split("/")[6]
            val history = message.channel.getHistoryAfter(id, 100).complete().retrievedHistory
            message.channel.deleteMessageById(id).queue()
            message.channel.purgeMessages(history)
        }
    }

    override fun getName(): String {
        return name
    }

    override fun getLevel(): Level {
        return level
    }
}