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
import me.argraur.railgun.utils.Level
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.VoiceChannel
import kotlin.random.Random

class Juggle: Command {
    private val name = "juggle"
    private val usage = name
    private val desc = "DANGEROUS! Juggle with voice chat members!"
    private val level = Level.ADMIN

    override fun exec(message: Message) {
        val vcList: List<VoiceChannel> = message.guild.voiceChannels
        for (i in 0 until vcList.size - 1) {
            for (vc in vcList) {
                for (member in vc.members) {
                    member.guild.moveVoiceMember(member, vcList[Random(System.currentTimeMillis()).nextInt(vcList.size)]).queue()
                }
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