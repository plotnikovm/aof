package io.aof.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class Db {
    object Fap {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FapReader.db"

        // Table contents are grouped together in an anonymous object.
        object FapEntry : BaseColumns {
            const val TABLE_NAME = "entry"
            const val COLUMN_NAME_TIMESTAMP = "timestamp"
            const val COLUMN_NAME_RATING = "rating" // in stars
            const val COLUMN_NAME_TIME = "time" // fap time in seconds
        }

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FapEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${FapEntry.COLUMN_NAME_TIMESTAMP} INTEGER," +
                    "${FapEntry.COLUMN_NAME_RATING} INTEGER," +
                    "${FapEntry.COLUMN_NAME_TIME} INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FapEntry.TABLE_NAME}"

        class FapReaderDbHelper(context: Context) :
            SQLiteOpenHelper(
                context,
                DATABASE_NAME, null,
                DATABASE_VERSION
            ) {
            override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(SQL_CREATE_ENTRIES)
            }

            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                db.execSQL(SQL_DELETE_ENTRIES)
                onCreate(db)
            }

            override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                onUpgrade(db, oldVersion, newVersion)
            }
        }
    }
}