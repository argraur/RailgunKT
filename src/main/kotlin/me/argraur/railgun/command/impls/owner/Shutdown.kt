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

package me.argraur.railgun.command.impls.owner

import me.argraur.railgun.Railgun
import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Level
import net.dv8tion.jda.api.entities.Message

class Shutdown: Command {
    private val name = "shutdown"
    private val usage = name
    private val desc = "Shuts down the bot. Only for owner."
    private val level = Level.OWNER

    override fun exec(message: Message) {
        message.channel.sendMessage("Shutting down the bot...").queue()
        message.jda.shutdown()
        Railgun.stop()
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