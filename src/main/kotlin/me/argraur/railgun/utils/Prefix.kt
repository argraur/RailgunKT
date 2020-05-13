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

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.argraur.railgun.Railgun
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Message
import java.io.*

class Prefix {
    private val key = "prefix"
    private val file = "data/prefix.rg"
    private val default = Railgun.config.getValue(key)
    private lateinit var prefixes: HashMap<String, String>

    private fun readObject(): Any {
        val fileInputStream = FileInputStream(file)
        val objectInputStream = ObjectInputStream(fileInputStream)
        val any = objectInputStream.readObject()
        objectInputStream.close()
        return any
    }

    private fun writeObject() {
        GlobalScope.launch {
            val fileOutputStream = FileOutputStream(file)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            try {
                objectOutputStream.writeObject(prefixes)
                println("${javaClass.name}: Successfully saved object!")
            } catch (e: Exception) {
                println("${javaClass.name}: Failed to save object!")
            }
        }
    }

    private fun isSet(message: Message): Boolean {
        return prefixes[message.guild.id] != null
    }

    fun getPrefix(message: Message): String {
        when {
            (message.channelType == ChannelType.PRIVATE) -> return default
            (isSet(message)) -> return prefixes[message.guild.id]!!
        }
        return default
    }

    fun setPrefix(message: Message, prefix: String) {
        prefixes[message.guild.id] = prefix
        writeObject()
    }

    fun filterPrefix(message: Message): String {
        return message.contentDisplay.replaceFirst(getPrefix(message), "")
    }

    init {
        try {
            val any = readObject()
            if (any is HashMap<*, *>)
                prefixes = any as HashMap<String, String>
        } catch (e: FileNotFoundException) {
            prefixes = HashMap()
            writeObject()
        }
    }
}