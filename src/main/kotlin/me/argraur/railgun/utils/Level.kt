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

package me.argraur.railgun.utils

import me.argraur.railgun.Railgun
import me.argraur.railgun.command.Command
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message

enum class Level {
    OWNER,
    ADMIN,
    MESSAGE,
    BAN,
    KICK,
    NORMAL
}

fun checkLevel(message: Message, command: Command): Boolean {
    if (message.author.id == Railgun.config.getValue("goshujinsama"))
        return true
    return when (command.getLevel()) {
        Level.OWNER -> false
        Level.ADMIN -> message.member!!.hasPermission(Permission.ADMINISTRATOR)
        Level.MESSAGE -> message.member!!.hasPermission(Permission.MESSAGE_MANAGE)
        Level.BAN -> message.member!!.hasPermission(Permission.BAN_MEMBERS)
        Level.KICK -> message.member!!.hasPermission(Permission.KICK_MEMBERS)
        Level.NORMAL -> true
    }
}