package com.cizc.camilo_zavala_herpm13051_s6.model

import android.content.ContentValues
import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cizc.camilo_zavala_herpm13051_s6.R
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionContract.InfraccionEntry

class EditarInfraccionActivity : AppCompatActivity() {

    private lateinit var editTextRutInspector: EditText
    private lateinit var editTextNombreLocal: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextInfraccion: EditText
    private lateinit var textViewFolio: TextView
    private lateinit var buttonActualizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_infraccion)

        editTextRutInspector = findViewById(R.id.editTextRutInspector)
        editTextNombreLocal = findViewById(R.id.editTextNombreLocal)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextInfraccion = findViewById(R.id.editTextInfraccion)
        textViewFolio = findViewById(R.id.textViewFolio)
        buttonActualizar = findViewById(R.id.buttonActualizar)


        // Cambio importante aquí: Obtener el ID de la infracción como un Long
        val infraccionId = intent.getLongExtra("INFRACCION_ID", -1L)
        if (infraccionId == -1L) {
            Toast.makeText(this, "Error: ID de infracción no encontrado.", Toast.LENGTH_LONG).show()
            finish() // Finaliza la actividad si no hay un ID válido.
            return
        }
        val infraccionUri = ContentUris.withAppendedId(InfraccionEntry.CONTENT_URI, infraccionId)

        val projection = arrayOf(
            InfraccionEntry.COLUMN_RUT_INSPECTOR,
            InfraccionEntry.COLUMN_NOMBRE_LOCAL,
            InfraccionEntry.COLUMN_DIRECCION,
            InfraccionEntry.COLUMN_INFRACCION
        )

        contentResolver.query(infraccionUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val rutInspector = cursor.getString(cursor.getColumnIndex(InfraccionEntry.COLUMN_RUT_INSPECTOR))
                val nombreLocal = cursor.getString(cursor.getColumnIndex(InfraccionEntry.COLUMN_NOMBRE_LOCAL))
                val direccion = cursor.getString(cursor.getColumnIndex(InfraccionEntry.COLUMN_DIRECCION))
                val infraccion = cursor.getString(cursor.getColumnIndex(InfraccionEntry.COLUMN_INFRACCION))
                val folio = cursor.getString(cursor.getColumnIndex(InfraccionEntry._ID))

                editTextRutInspector.setText(rutInspector)
                editTextNombreLocal.setText(nombreLocal)
                editTextDireccion.setText(direccion)
                editTextInfraccion.setText(infraccion)
                textViewFolio.text = "Folio: $folio"
            }
        }

        buttonActualizar.setOnClickListener {
            guardarCambiosInfraccion(infraccionUri)
        }
    }

    private fun guardarCambiosInfraccion(infraccionUri: Uri) {
        val rutInspector = editTextRutInspector.text.toString().trim()
        val nombreLocal = editTextNombreLocal.text.toString().trim()
        val direccion = editTextDireccion.text.toString().trim()
        val infraccion = editTextInfraccion.text.toString().trim()

        if (rutInspector.isNotEmpty() && nombreLocal.isNotEmpty() && direccion.isNotEmpty() && infraccion.isNotEmpty()) {
            val values = ContentValues().apply {
                put(InfraccionEntry.COLUMN_RUT_INSPECTOR, rutInspector)
                put(InfraccionEntry.COLUMN_NOMBRE_LOCAL, nombreLocal)
                put(InfraccionEntry.COLUMN_DIRECCION, direccion)
                put(InfraccionEntry.COLUMN_INFRACCION, infraccion)
            }

            val rowsAffected = contentResolver.update(infraccionUri, values, null, null)
            if (rowsAffected > 0) {
                Toast.makeText(this, "Los cambios se guardaron correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}

