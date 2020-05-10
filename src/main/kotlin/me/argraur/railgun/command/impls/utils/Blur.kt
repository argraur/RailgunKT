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
import me.argraur.railgun.utils.ImageBlur
import me.argraur.railgun.utils.ImageColor

import net.dv8tion.jda.api.entities.Message

import java.awt.Color
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class Blur: Command {
    private val name = "blur"
    private val usage = "$name <url> or attachment"
    private val desc = "Blurs given image."

    override fun exec(message: Message) {
        var radius = 10
        try {
            radius = Format.stripCommand(message).split(" ")[0].toInt()
        } catch (e: Exception) {
            try {
                radius = Format.stripCommand(message).split(" ")[1].toInt()
            } catch (ignored: Exception) {}
        }
        message.channel.sendMessage(
            Embed(Color.decode("#${ImageColor.getColor(Format.imageUrl(message))}"))
                .setTitle("Blur")
                .setDesc("Blurring your image, ${message.author.asMention}...")
                .setImage(Format.imageUrl(message))
                .create()
        ).queue { response ->
            val blur = ImageBlur.blur(Format.imageUrl(message), radius, "jpeg")!!
            val blurArr = blur.readAllBytes()
            if (blurArr.isEmpty()) {
                response.channel.sendFile(ImageBlur.blur(Format.imageUrl(message), radius, "png")!!, "blurred.png").queue()
            } else {
                response.channel.sendFile(blurArr, "blurred.jpg").queue()
            }
            response.delete().queue()
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
}