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

import org.apache.commons.io.IOUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.IllegalStateException

class Shell(private val message: Message): Runnable {
    private val command: String = Format.stripCommand(message)

    private fun reader(stream: InputStream): StringBuilder {
        val res = StringBuilder()
        val reader = BufferedReader(InputStreamReader(stream))
        var line: String
        while (true) {
            try {
                line = reader.readLine()
                res.append(line + "\n")
            } catch (e: IllegalStateException) {
                break
            }
        }
        return res
    }

    override fun run() {
        val processBuilder = ProcessBuilder()
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
            processBuilder.command("cmd.exe", "/c", command)
        else
            processBuilder.command("bash", "-c", command)
        try {
            val time = System.currentTimeMillis()
            val process = processBuilder.start()
            val out = reader(process.inputStream)
            val err = reader(process.errorStream)
            process.waitFor()
            if (err.toString() == "")
                err.append("Empty")
            if (out.toString().length < 1024)
                message.channel.sendMessage(
                    Embed()
                        .setTitle(command)
                        .addField("Input Stream", Format.shellYellow(if (out.toString() == "") "Empty" else out.toString()))
                        .addField("Error Stream", Format.shellYellow(err.toString()))
                        .addField("Time Elapsed", Format.shellYellow("${System.currentTimeMillis() - time} ms"))
                        .create()
                ).queue()
            else {
                out.append("\nTime Elapsed: ${System.currentTimeMillis() - time}ms\n")
                message.channel.sendFile(IOUtils.toInputStream(out.toString(), "UTF-8"), "output.txt").queue()
                if (err.toString() != "Empty") {
                    message.channel.sendFile(IOUtils.toInputStream(err.toString(), "UTF-8"), "err.txt").queue()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}