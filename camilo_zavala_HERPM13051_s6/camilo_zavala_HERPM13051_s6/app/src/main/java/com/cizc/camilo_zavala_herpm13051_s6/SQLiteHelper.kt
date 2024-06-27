package com.cizc.camilo_zavala_herpm13051_s6

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionModel

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "InfraccionesDB"
        const val TABLE_INFRACCIONES = "infracciones"
        const val KEY_ID = "_id"
        const val KEY_RUT_INSPECTOR = "rut_inspector"
        const val KEY_NOMBRE_LOCAL = "nombre_local"
        const val KEY_DIRECCION = "direccion"
        const val KEY_INFRACCION = "infraccion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_INFRACCIONES_TABLE = ("CREATE TABLE " + TABLE_INFRACCIONES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RUT_INSPECTOR + " TEXT,"
                + KEY_NOMBRE_LOCAL + " TEXT," + KEY_DIRECCION + " TEXT,"
                + KEY_INFRACCION + " TEXT" + ")")
        db.execSQL(CREATE_INFRACCIONES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INFRACCIONES")
        onCreate(db)
    }

    fun addInfraccion(infraccion: InfraccionModel): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_RUT_INSPECTOR, infraccion.rutInspector)
        values.put(KEY_NOMBRE_LOCAL, infraccion.nombreLocal)
        values.put(KEY_DIRECCION, infraccion.direccion)
        values.put(KEY_INFRACCION, infraccion.infraccion)

        val success = db.insert(TABLE_INFRACCIONES, null, values)
        db.close()
        return success
    }

    fun updateInfraccion(infraccion: InfraccionModel): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_RUT_INSPECTOR, infraccion.rutInspector)
        values.put(KEY_NOMBRE_LOCAL, infraccion.nombreLocal)
        values.put(KEY_DIRECCION, infraccion.direccion)
        values.put(KEY_INFRACCION, infraccion.infraccion)

        val success = db.update(TABLE_INFRACCIONES, values, "$KEY_ID = ?", arrayOf(infraccion.id.toString()))
        db.close()
        return success
    }

    fun deleteInfraccion(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_INFRACCIONES, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun getAllInfracciones(): List<InfraccionModel> {
        val infraccionesList = ArrayList<InfraccionModel>()
        val selectQuery = "SELECT * FROM $TABLE_INFRACCIONES"
        this.readableDatabase.use { db ->
            db.rawQuery(selectQuery, null).use { cursor ->
                if (cursor.moveToFirst()) {
                    do {
                        val infraccion = InfraccionModel(
                            id = cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            rutInspector = cursor.getString(cursor.getColumnIndex(KEY_RUT_INSPECTOR)),
                            nombreLocal = cursor.getString(cursor.getColumnIndex(KEY_NOMBRE_LOCAL)),
                            direccion = cursor.getString(cursor.getColumnIndex(KEY_DIRECCION)),
                            infraccion = cursor.getString(cursor.getColumnIndex(KEY_INFRACCION))
                        )
                        infraccionesList.add(infraccion)
                    } while (cursor.moveToNext())
                }
            }
        }
        return infraccionesList
    }
}
