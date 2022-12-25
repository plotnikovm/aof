package io.aof.db

import com.google.gson.Gson
import java.util.zip.GZIPInputStream
import kotlin.text.Charsets.UTF_8


class Import {
    private class Data {
        val list: List<Fap>? = null
    }

    companion object {
        fun fromJson(s: String): List<Fap>? {
            val gson = Gson()
            return gson.fromJson(s, Data::class.java).list
        }

        fun fromQrJson(s: String): List<Fap>? {
            val json =
                GZIPInputStream(s.byteInputStream()).bufferedReader(UTF_8).use { it.readText() }
            return fromJson(json)
        }
    }
}
