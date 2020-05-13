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

package me.argraur.railgun.api

import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import me.argraur.railgun.utils.ImageColor

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

import okhttp3.OkHttpClient
import okhttp3.Request

import org.json.JSONException
import org.json.JSONObject
import java.awt.Color

class SauceNAO {
    companion object {
        private const val url = "https://saucenao.com/search.php?"
        private const val contentType = "application/vnd.api+json"
        private const val accept = "application/vnd.api+json"
        private const val db = 999
        private const val outputType = 2
        private const val testMode = 0
        private const val numRes = 2
        private val okHttp = OkHttpClient()

        private fun getResponse(url: String): String {
            val request = Request.Builder()
                .addHeader("Content-Type", contentType)
                .addHeader("Accept", accept)
                .url(url)
                .build()
            return okHttp.newCall(request).execute().body!!.string()
        }

        fun search(message: Message): JSONObject? {
            val imageUrl = Format.imageUrl(message)
            var index = 0
            val url = StringBuilder(this.url)
                .append("db=$db")
                .append("&output_type=$outputType")
                .append("&testmode=$testMode")
                .append("&numres=$numRes")
                .append("&url=${imageUrl.replace(":", "%3A").replace("/", "%2F")}")
            val response = getResponse(url.toString())
            println(response)
            val json = JSONObject(response)
            val resultsFound = json.getJSONObject("header").getInt("results_returned")
            return if (resultsFound != 0) {
                try {
                    json.getJSONArray("results").getJSONObject(index).getJSONObject("data").getJSONArray("ext_urls")
                } catch (e: JSONException) {
                    index++
                }
                json.getJSONArray("results").getJSONObject(index)
            } else null
        }

        fun embed(result: JSONObject): MessageEmbed {
            return Embed(Color.decode("#${ImageColor.getColor(result.getJSONObject("header").getString("thumbnail"))}"))
                    .setAuthor(
                            try {
                                "Authored by: ${result.getJSONObject("data").getString("member_name")}"
                            } catch (e: Exception) {
                                "Authored by: N/A"
                            }
                    )
                    .setDesc("${Format.italic("Similarity")} ${Format.bold("${result.getJSONObject("header").getString("similarity")}%")}")
                    .setImage(result.getJSONObject("header").getString("thumbnail"))
                    .setTitle(
                        try {
                            result.getJSONObject("data").getString("title")
                        } catch (e: Exception) {
                            "Title unavailable"
                        }, result.getJSONObject("data").getJSONArray("ext_urls").getString(0)
                    ).create()
        }
    }
}