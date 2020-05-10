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

import me.argraur.railgun.command.Command
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import me.argraur.railgun.utils.ImageColor
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class Color: Command {
    private var name = "color"
    private var usage = "$name <url> or attachment"
    private var desc = "Retrieves dominating color of the given image"

    override fun exec(message: Message) {
        val hex = ImageColor.getColor(Format.imageUrl(message))
        message.channel.sendMessage(
            Embed(Color.decode("#$hex"))
                .setTitle("#$hex")
                .setImage("https://dummyimage.com/100x100/$hex/$hex.jpg")
                .create()
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