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
import net.dv8tion.jda.api.entities.Message
import java.lang.IndexOutOfBoundsException

class Format {
    companion object {
        fun addPrefix(message: Message, str: String): String {
            return "${Railgun.prefix.getPrefix(message)}$str"
        }

        fun bold(str: String): String {
            return "**${str}**"
        }

        fun italic(str: String): String {
            return "*${str}*"
        }

        fun boldItalic(str: String): String {
            return "***${str}***"
        }

        fun shellOneLine(str: String): String {
            return "`${str}`"
        }

        fun shellMultipleLines(str: String): String {
            return "```\n${str}```"
        }

        fun shellYellow(str: String): String {
            return "```fix\n${str}\n```"
        }

        fun bashShell(str: String): String {
            return "```bash\n${str}\n```"
        }

        fun stripCommand(message: Message): String {
            val whitespace = when {
                message.contentRaw.split(" ").size > 1 -> " "
                else -> ""
            }
            return message.contentRaw.replaceFirst("${message.contentDisplay.split(" ")[0]}${whitespace}","")
        }

        fun imageUrl(message: Message): String {
            var url: String
            url = try {
                message.attachments[0].url
            } catch (e: IndexOutOfBoundsException) {
                stripCommand(message)
            }
            if (url.contains("?"))
                url = url.substring(0, url.indexOf("?"))
            return url
        }

        fun imageUrl(str: String): String {
            var url = str
            if (url.contains("?"))
                url = url.substring(0, url.indexOf("?"))
            return url
        }
    }
}