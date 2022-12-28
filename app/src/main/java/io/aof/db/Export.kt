package io.aof.db

import android.content.Context
import android.graphics.Bitmap
import android.provider.BaseColumns
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream
import kotlin.text.Charsets.UTF_8

class Export {
    companion object {
        fun getItems(context: Context): List<Fap> {
            val dbHelper = Db.Fap.FapReaderDbHelper(context)
            val db = dbHelper.readableDatabase
            val cursor = db.query(
                Db.Fap.FapEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            val items = mutableListOf<Fap>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val timestamp =
                        getLong(getColumnIndexOrThrow(Db.Fap.FapEntry.COLUMN_NAME_TIMESTAMP))
                    val rating = getLong(getColumnIndexOrThrow(Db.Fap.FapEntry.COLUMN_NAME_RATING))
                    val time = getLong(getColumnIndexOrThrow(Db.Fap.FapEntry.COLUMN_NAME_TIME))
                    items.add(Fap(id, timestamp, rating, time))
                }
            }
            cursor.close()
            db.close()
            dbHelper.close()
            return items
        }

        private fun exportJson(items: List<Fap>): String {
            val gson = Gson()
            return gson.toJson(items)
        }

        fun exportQr(items: List<Fap>): Bitmap? {
            val json = exportJson(items)

            val bos = ByteArrayOutputStream()
            GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(json) }

            val renderOption = RenderOption()
            renderOption.content = bos.toString() // content to encode
            renderOption.size = 450 // size of the final QR code image
            renderOption.borderWidth = 6 // width of the empty space around the QR code
            renderOption.patternScale = 1f // (optional) specify a scale for patterns
            renderOption.clearBorder =
                true // if set to true, the background will NOT be drawn on the border area
            renderOption.color = Color(
                background = 0xFFFFFFFF.toInt(),
                light = 0xFFFFFFFF.toInt(),
                dark = 0xFF000000.toInt()
            )

            val result = AwesomeQrRenderer.render(renderOption)
            return result.bitmap
        }
    }
}