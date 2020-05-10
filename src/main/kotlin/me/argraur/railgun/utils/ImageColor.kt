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

import de.androidpit.colorthief.ColorThief

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class ImageColor {
    companion object {
        private val okHttp = OkHttpClient()
        fun getColor(url: String): String {
            val buff: BufferedImage
            val response: Response
            try {
                response = okHttp.newCall(Request.Builder().url(url).build()).execute()
            } catch (e: Exception) {
                return "Failed to load image"
            }
            val inputStream = response.body!!.byteStream()
            buff = ImageIO.read(inputStream)
            val rgb = ColorThief.getColor(buff)
            return String.format("%02x%02x%02x", rgb[0], rgb[1], rgb[2])
        }
    }
}