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

import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

class Config() {
    private val filename: String = "config.properties"
    private val config: Properties = Properties()

    init {
        val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(filename) ?: throw FileNotFoundException("$filename resource not found!")
        config.load(inputStream)
        println("${javaClass.name}: Ready!")
    }

    fun getValue(key: String): String {
        return config.getProperty(key)
    }
}