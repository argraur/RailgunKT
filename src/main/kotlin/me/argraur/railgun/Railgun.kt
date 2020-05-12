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

package me.argraur.railgun

import me.argraur.railgun.api.SauceNAO
import me.argraur.railgun.jenkins.Jenkins
import me.argraur.railgun.listeners.MessageListener
import me.argraur.railgun.utils.Config
import me.argraur.railgun.utils.Log
import me.argraur.railgun.utils.Prefix
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.Compression
import kotlin.system.exitProcess

class Railgun {
    private val token = "token"
    private val activity = "activity"
    private val discord: JDA
    companion object {
        val DEBUG = true
        val logger = Log()
        val config = Config()
        val listener = MessageListener()
        val picture = "https://media.discordapp.net/attachments/698965374317625345/699022743542169691/oof.jpg"
        val jenkins = Jenkins(
            config.getValue("jenkins.url"),
            config.getValue("jenkins.user"),
            config.getValue("jenkins.pass")
        )
        val prefix = Prefix()

        @JvmStatic fun main(args: Array<String>) {
            logger.I("Initializing Railgun object...", this.javaClass.simpleName)
            Railgun()
        }

        fun stop() {
            logger.I("Closing log writer...Bye-bye!", this.javaClass.simpleName)
            logger.closeWriter()
            exitProcess(0)
        }

        fun restart() {
            Thread.sleep(1000 * 10)
            logger.I("Restarting bot...", this.javaClass.simpleName)
            main(arrayOf())
        }
    }

    init {
        logger.I("Building Discord bot...", this.javaClass.simpleName)
        discord = JDABuilder
                .createDefault(config.getValue(token))
                .addEventListeners(listener)
                .setCompression(Compression.NONE)
                .setActivity(Activity.playing(config.getValue(activity)))
                .build()
    }
}