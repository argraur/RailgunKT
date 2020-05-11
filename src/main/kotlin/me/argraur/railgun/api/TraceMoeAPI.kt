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
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class TraceMoeAPI {
    companion object {
        private const val url = "https://trace.moe/api/search?url="
        private val okHttp = OkHttpClient()

        private fun getResponse(url: String): String {
            return okHttp.newCall(
                Request.Builder()
                    .addHeader("Content-Type", "application/vnd.api+json")
                    .addHeader("Accept", "application/vnd.api+json")
                    .url(url)
                    .build()
            ).execute().body!!.string()
        }

        fun search(url: String): JSONObject {
            return JSONObject(getResponse(this.url + url))
                .getJSONArray("docs")
                .getJSONObject(0)
        }

        private fun getTitle(wait: JSONObject): String {
            return wait.getString("title")
        }

        fun embed(wait: JSONObject): Embed {
            val anime = KitsuAPI.search(getTitle(wait))
            var s = wait.getFloat("at").toInt()
            val timestamp = ((s - 60.also { s %= it }) / 60).toString() + (if (s > 9) ":" else ":0") + s
            return Embed()
                .setTitle(getTitle(wait), KitsuAPI.getUrl(anime))
                .addField("Similarity", Format.shellOneLine(String.format("%.2f%%", wait.getFloat("similarity") * 100)))
                .addField("Episode",
                    Format.bold(
                        try {
                            wait.getInt("episode").toString()
                        } catch (ignored: Exception) {
                            wait.getString("episode")
                        }
                    )
                )
                .addField("Timestamp", Format.shellOneLine(timestamp))
                .addField("Status", Format.bold(KitsuAPI.getStatus(anime)))
                .addField("Source", Format.bold(KitsuAPI.getSource(anime)))
                .addField("Rating", Format.shellOneLine(KitsuAPI.getRating(anime)))
                .addField("Genres", if (KitsuAPI.getGenres(anime) != "") KitsuAPI.getGenres(anime) else "N/A")
                .setDesc("Synopsis\n${Format.shellYellow(KitsuAPI.getSynopsis(anime))}")
                .addField("YouTube PV", KitsuAPI.getYTUrl(anime))
        }
    }
}