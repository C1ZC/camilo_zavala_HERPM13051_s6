package com.cizc.camilo_zavala_herpm13051_s6.data

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionModel

class InfraccionDAO(context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    // Método para insertar una infracción a través del Content Provider
    fun insertInfraccion(infraccion: InfraccionModel): Uri? {
        val uri = Uri.parse("content://com.cizc.camilo_zavala_herpm13051_s6.provider/infracciones")
        val values = ContentValues().apply {
            put("rut_inspector", infraccion.rutInspector)
            put("nombre_local", infraccion.nombreLocal)
            put("direccion", infraccion.direccion)
            put("infraccion", infraccion.infraccion)
        }
        return contentResolver.insert(uri, values)
    }

    // Método para actualizar una infracción a través del Content Provider
    fun updateInfraccion(infraccion: InfraccionModel): Int {
        val uri = Uri.parse("content://com.cizc.camilo_zavala_herpm13051_s6.provider/infracciones/${infraccion.id}")
        val values = ContentValues().apply {
            put("rut_inspector", infraccion.rutInspector)
            put("nombre_local", infraccion.nombreLocal)
            put("direccion", infraccion.direccion)
            put("infraccion", infraccion.infraccion)
        }
        return contentResolver.update(uri, values, null, null)
    }

    // Método para eliminar una infracción a través del Content Provider
    fun deleteInfraccion(id: Int): Int {
        val uri = Uri.parse("content://com.cizc.camilo_zavala_herpm13051_s6.provider/infracciones/$id")
        return contentResolver.delete(uri, null, null)
    }

    // Método para obtener todas las infracciones a través del Content Provider
    fun getAllInfracciones(): List<InfraccionModel> {
        val uri = Uri.parse("content://com.cizc.camilo_zavala_herpm13051_s6.provider/infracciones")
        val projection = arrayOf("id", "rut_inspector", "nombre_local", "direccion", "infraccion")
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        val infraccionesList = mutableListOf<InfraccionModel>()
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val rutInspector = it.getString(it.getColumnIndex("rut_inspector"))
                val nombreLocal = it.getString(it.getColumnIndex("nombre_local"))
                val direccion = it.getString(it.getColumnIndex("direccion"))
                val infraccion = it.getString(it.getColumnIndex("infraccion"))
                val infraccionModel = InfraccionModel(id, rutInspector, nombreLocal, direccion, infraccion)
                infraccionesList.add(infraccionModel)
            }
        }
        cursor?.close()
        return infraccionesList
    }
}
