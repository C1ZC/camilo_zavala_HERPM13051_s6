package com.cizc.camilo_zavala_herpm13051_s6

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.cizc.camilo_zavala_herpm13051_s6.SQLiteHelper.Companion.KEY_ID


class MyContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.cizc.camilo_zavala_herpm13051_s6.provider"
        val URI_INFRACCION = Uri.parse("content://$AUTHORITY/infracciones")

        const val INFRACCIONES = 1
        const val INFRACCION_ID = 2

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "infracciones", INFRACCIONES)
            addURI(AUTHORITY, "infracciones/#", INFRACCION_ID)
        }
    }

    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(): Boolean {
        dbHelper = SQLiteHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        val match = uriMatcher.match(uri)

        // Ajusta la proyección para incluir 'id AS _id' si no está presente
        val modifiedProjection = projection?.toMutableList() ?: mutableListOf<String>()
        if (projection != null && !projection.contains("_id")) {
            modifiedProjection.add(0, "${SQLiteHelper.KEY_ID} AS _id")
        }

        return when (match) {
            INFRACCIONES -> db.query(
                SQLiteHelper.TABLE_INFRACCIONES,
                modifiedProjection.toTypedArray(), // Usa la proyección modificada aquí
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )
            INFRACCION_ID -> {
                val id = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid URI")
                db.query(
                    SQLiteHelper.TABLE_INFRACCIONES,
                    modifiedProjection.toTypedArray(), // Y también aquí
                    "$KEY_ID = ?",
                    arrayOf(id),
                    null,
                    null,
                    sortOrder
                )
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }



    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            INFRACCIONES -> ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + SQLiteHelper.TABLE_INFRACCIONES
            INFRACCION_ID -> ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "." + SQLiteHelper.TABLE_INFRACCIONES
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        when (uriMatcher.match(uri)) {
            INFRACCIONES -> {
                val id = db.insert(SQLiteHelper.TABLE_INFRACCIONES, null, values)
                context?.contentResolver?.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            else -> throw IllegalArgumentException("Invalid URI for insert: $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = dbHelper.writableDatabase
        val match = uriMatcher.match(uri)
        val updatedRows = when (match) {
            INFRACCION_ID -> {
                val id = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid URI")
                db.update(SQLiteHelper.TABLE_INFRACCIONES, values, "_id=?", arrayOf(id))
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return updatedRows
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val match = uriMatcher.match(uri)
        val deletedRows = when (match) {
            INFRACCION_ID -> {
                val id = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid URI")
                db.delete(SQLiteHelper.TABLE_INFRACCIONES, "id=?", arrayOf(id))
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return deletedRows
    }

    // Otras implementaciones necesarias según las necesidades de tu aplicación
}