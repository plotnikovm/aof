package io.aof.db

import android.util.Log
import com.google.gson.Gson
import java.util.zip.GZIPInputStream
import kotlin.text.Charsets.UTF_8


class Import {
    companion object {
        private fun importJson(s: String): List<Fap>? {
            val gson = Gson()
            val list = gson.fromJson(s, ListFap::class.java).list
            Log.println(Log.INFO, "import", list.toString())
            return list
        }

        fun importQrJson(s: String): List<Fap>? {
            val json =
                GZIPInputStream(s.byteInputStream()).bufferedReader(UTF_8).use { it.readText() }
            return importJson(json)
        }
    }
}
