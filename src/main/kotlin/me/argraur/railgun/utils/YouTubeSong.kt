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

import com.github.kiulian.downloader.YoutubeDownloader
import com.github.kiulian.downloader.model.formats.AudioFormat
import com.github.kiulian.downloader.model.quality.AudioQuality

import net.dv8tion.jda.api.EmbedBuilder

import net.dv8tion.jda.api.entities.Message

import java.awt.Color
import java.io.File

class YouTubeSong(private val message: Message): Runnable {
    override fun run() {
        val video = YoutubeDownloader().getVideo(Format.stripCommand(message))
        val details = video.details()
        val thumbnail = details.thumbnails()[0]
        val first = message.channel.sendMessage(
                Embed(Color.decode("#${ImageColor.getColor(Format.imageUrl(thumbnail))}"))
                        .setAuthor(details.author())
                        .setTitle(details.title())
                        .setDesc("Downloading audio track...")
                        .setThumbnail(thumbnail)
                        .create()
        )
        var audioFormat: List<AudioFormat>? = null
        for (i in arrayOf(AudioQuality.high, AudioQuality.medium, AudioQuality.low, AudioQuality.unknown))
            if (video.findAudioWithQuality(i).size > 0) {
                audioFormat = video.findAudioWithQuality(i)
                break
            }
        val output = File("downloads")
        var second: Message? = null
        first.queue { second = it }
        val file = video.download(audioFormat?.get(0), output)
        val filename = "${details.videoId()}.mp3"
        second?.editMessage(
                EmbedBuilder(second!!.embeds[0])
                        .setDescription("")
                        .addField("Name", Format.shellYellow(filename), false)
                        .addField("Size", Format.shellYellow("${file.length() / 1024 / 1024} MB"), false)
                        .addField("Format", Format.shellYellow("mp3"), false)
                        .build()
        )?.queue()
        second?.channel?.sendFile(file, filename)?.queue()
    }
}