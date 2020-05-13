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

package me.argraur.railgun.jenkins

import com.offbytwo.jenkins.JenkinsServer
import com.offbytwo.jenkins.JenkinsTriggerHelper
import com.offbytwo.jenkins.client.JenkinsHttpClient
import com.offbytwo.jenkins.model.BuildResult
import me.argraur.railgun.Railgun
import me.argraur.railgun.utils.Embed
import me.argraur.railgun.utils.Format
import net.dv8tion.jda.api.entities.Message
import org.apache.http.client.HttpResponseException
import java.awt.Color
import java.net.URI
import java.util.*

class Jenkins(url: String, user: String, pass: String) {
    private val jenkins = JenkinsServer(
        JenkinsHttpClient(
            URI(url),
            user,
            pass
        )
    )
    private val jenkinsTriggerHelper = JenkinsTriggerHelper(
        jenkins
    )

    fun build(jobName: String, message: Message) {
        try {
            val job = jenkins.getJob(jobName)
            message.channel.sendMessage(
                    Embed()
                            .setTitle("Building $jobName #${job.nextBuildNumber}")
                            .addField("URL", "${job.url}${job.nextBuildNumber}")
                            .addField("Console", "${job.url}${job.nextBuildNumber}/console")
                            .setDesc("${message.author.asMention}, build will be started in a couple of seconds!")
                            .create()
            ).queue { response ->
                message.delete().queue()
                val build = jenkinsTriggerHelper.triggerJobAndWaitUntilFinished(jobName)
                val status: String
                val embed = Embed(
                        when {
                            (build.result == BuildResult.SUCCESS) -> {
                                status = "Successful"; Color.GREEN
                            }
                            (build.result == BuildResult.FAILURE) -> {
                                status = "Failed"; Color.RED
                            }
                            (build.result == BuildResult.ABORTED) -> {
                                status = "Aborted"; Color.GRAY
                            }
                            else -> {
                                status = "Unknown"; Color.ORANGE
                            }
                        }
                )
                val out = Scanner(build.consoleOutputText)
                while (out.hasNextLine()) {
                    val line = out.nextLine()
                    if (line.startsWith("__RAILGUN__")) {
                        val wline = line.replaceFirst("__RAILGUN__", "")
                        when {
                            (wline.startsWith("url=")) -> embed.addField("URL", wline.replace("url=", ""))
                            (wline.startsWith("name=")) -> embed.addField("Filename", Format.shellYellow(wline.replace("name=", "")))
                            (wline.startsWith("size=")) -> embed.addField("Size", Format.shellYellow(wline.replace("size=", "")))
                            (wline.startsWith("md5sum")) -> embed.addField("MD5 Hash", Format.shellYellow(wline.replace("md5sum=", "")))
                            else -> embed.addField("Output message", Format.shellYellow(wline))
                        }
                    }
                }
                response.editMessage(
                        embed
                                .setTitle("${build.fullDisplayName} $status", build.url)
                                .addField("Duration", "${(System.currentTimeMillis() - build.timestamp) / 1000 / 60} minutes")
                                .create()
                ).queue()
            }
        } catch (e: HttpResponseException) {
            message.channel.sendMessage(Embed().fastCreate("Launch failed.", "Failed to run the job, due to connection error.")).queue()
        }
    }
}