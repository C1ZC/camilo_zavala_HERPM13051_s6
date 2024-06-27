package com.cizc.camilo_zavala_herpm13051_s6.model

import android.net.Uri
import android.provider.BaseColumns

object InfraccionContract {
    // Definición de la autoridad del Content Provider
    const val AUTHORITY = "com.cizc.camilo_zavala_herpm13051_s6.provider"

    // Definición de la tabla de infracciones
    object InfraccionEntry : BaseColumns {
        const val TABLE_NAME = "infracciones"
        const val COLUMN_RUT_INSPECTOR = "rut_inspector"
        const val COLUMN_NOMBRE_LOCAL = "nombre_local"
        const val COLUMN_DIRECCION = "direccion"
        const val COLUMN_INFRACCION = "infraccion"

        // Definición de la URI del contenido para la tabla de infracciones
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

        // Definición de la columna especial _ID
        const val _ID = BaseColumns._ID

        // Tipos MIME
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$TABLE_NAME"
        const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$TABLE_NAME"
    }
}

