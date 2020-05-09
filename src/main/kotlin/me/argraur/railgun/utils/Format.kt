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

import net.dv8tion.jda.api.entities.Message

class Format {
    companion object {
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
            return message.contentDisplay.replaceFirst("${message.contentDisplay.split(" ")[0]} ","")
        }
    }
}