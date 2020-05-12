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

import me.argraur.railgun.Railgun
import java.io.*
import java.nio.Buffer
import java.text.SimpleDateFormat
import java.util.*

class Log {
    val file: String = "logs/log-${SimpleDateFormat("yyyyMMdd-HHmm").format(Date())}"
    private val writer: BufferedWriter

    init {
        writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
    }

    private fun showAndWrite(log: String) {
        println(log)
        writer.write(log)
        writer.newLine()
    }

    fun closeWriter() {
        writer.close()
    }

    fun I(message: String, caller: String) {
        showAndWrite("I ${SimpleDateFormat("yyyy.MM.dd|HH:mm:ss").format(Date())} $caller: $message")
    }
    fun D(message: String, caller: String) {
        if (Railgun.DEBUG) {
            showAndWrite("D ${SimpleDateFormat("yyyy.MM.dd|HH:mm:ss").format(Date())} $caller: $message")
        }
    }
    fun W(message: String, caller: String) {
        showAndWrite("W ${SimpleDateFormat("yyyy.MM.dd|HH:mm:ss").format(Date())} $caller: $message")
    }
    fun E(message: String, caller: String) {
        showAndWrite("E ${SimpleDateFormat("yyyy.MM.dd|HH:mm:ss").format(Date())} $caller: $message")
    }
    fun F(message: String, caller: String) {
        showAndWrite("FATAL ${SimpleDateFormat("yyyy.MM.dd|HH:mm:ss").format(Date())} $caller: $message")
        throw Exception("FATAL")
    }
}