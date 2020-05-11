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

import net.dv8tion.jda.api.entities.MessageEmbed

import okhttp3.OkHttpClient
import okhttp3.Request

import org.json.JSONObject

import java.awt.Color

import kotlin.random.Random

class KitsuAPI {
    companion object {
        private const val url = "https://kitsu.io/api/edge"
        private val okHttp = OkHttpClient()

        fun searchGenre(genre: String): JSONObject {
            return getRandAnime(JSONObject(getResponse("$url/anime?filter[genres]=$genre&sort=-favoritesCount")))
        }

        fun search(query: String): JSONObject {
            return getAnime(JSONObject(getResponse("$url/anime?filter[text]=${query.replace(" ", "%20")}")))
        }

        fun getResponse(url: String): String {
            return okHttp.newCall(
                Request.Builder()
                    .addHeader("Content-Type", "application/vnd.api+json")
                    .addHeader("Accept", "application/vnd.api+json")
                    .url(url)
                    .build()
            ).execute().body!!.string()
        }

        fun getAnime(anime: JSONObject): JSONObject {
            return anime
                .getJSONArray("data")
                .getJSONObject(0)
        }

        fun getRandAnime(anime: JSONObject): JSONObject {
            return anime
                .getJSONArray("data")
                .getJSONObject(Random(System.currentTimeMillis()).nextInt(anime.length()))
        }

        fun getStatus(anime: JSONObject): String {
            return anime.getJSONObject("attributes").getString("status")
        }

        fun getYTUrl(anime: JSONObject): String {
            return "https://youtu.be/${anime.getJSONObject("attributes").getString("youtubeVideoId")}"
        }

        fun getImage(anime: JSONObject): String {
            return anime.getJSONObject("attributes").getJSONObject("posterImage").getString("small")
        }

        fun getTitle(anime: JSONObject): String {
            return anime.getJSONObject("attributes").getJSONObject("titles").getString("en_jp")
        }

        fun getUrl(anime: JSONObject): String {
            return "https://kitsu.io/anime/${anime.getJSONObject("attributes").getString("slug")}"
        }

        fun getSource(anime: JSONObject): String {
            return anime.getJSONObject("attributes").getString("subtype")
        }

        fun getRating(anime: JSONObject): String {
            return anime.getJSONObject("attributes").getString("averageRating")
        }

        fun getSynopsis(anime: JSONObject): String {
            return anime.getJSONObject("attributes").getString("synopsis")
        }

        fun getGenres(animeObject: JSONObject): String {
            val genresBuilder = StringBuilder()
            val genresResponse = getResponse(
                animeObject
                    .getJSONObject("relationships")
                    .getJSONObject("genres")
                    .getJSONObject("links")
                    .getString("related")
            )
            val genresArray = JSONObject(genresResponse).getJSONArray("data")
            for (i in 0 until genresArray.length()) {
                genresBuilder.append(
                    genresArray
                        .getJSONObject(i)
                        .getJSONObject("attributes")
                        .getString("name")
                )
                if (i != genresArray.length() - 1)
                    genresBuilder.append(", ")
            }
            return genresBuilder.toString()
        }

        fun embed(anime: JSONObject): MessageEmbed {
            return Embed(Color.decode("#${ImageColor.getColor(getImage(anime))}"))
                .setTitle(getTitle(anime), getUrl(anime))
                .addField("Status", Format.bold(getStatus(anime)))
                .addField("Source", Format.bold(getSource(anime)))
                .addField("Rating", Format.bold(getRating(anime)))
                .addField("Genres", if (getGenres(anime) != "") getGenres(anime) else "N/A")
                .setDesc("Synopsis\n${Format.shellYellow(getSynopsis(anime))}")
                .addField("YouTube PV", getYTUrl(anime))
                .setThumbnail(getImage(anime))
                .create()
        }
    }
}