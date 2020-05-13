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

import com.twelvemonkeys.image.ConvolveWithEdgeOp
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.image.Kernel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO

class ImageBlur {
    companion object {
        private val okHttp = OkHttpClient()
        fun blur(url: String, radius: Int = 10, format: String = "png"): InputStream? {
            val original = ImageIO.read(
                okHttp.newCall(Request.Builder().url(url).build())
                    .execute()
                    .body!!
                    .byteStream()
            )
            val size = radius * 2 + 1
            val weight = 1.0f / (size * size)
            val data = FloatArray(size * size)
            for (i in data.indices)
                data[i] = weight
            val op = ConvolveWithEdgeOp(
                Kernel(size, size, data),
                ConvolveWithEdgeOp.EDGE_REFLECT,
                null
            )
            val os = ByteArrayOutputStream()
            ImageIO.write(op.filter(original, null), format, os)
            return ByteArrayInputStream(os.toByteArray())
        }
    }
}