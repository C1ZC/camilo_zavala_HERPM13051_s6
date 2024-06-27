package com.cizc.camilo_zavala_herpm13051_s6.model

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionContract.InfraccionEntry.CONTENT_URI
import com.cizc.camilo_zavala_herpm13051_s6.R

class AgregarInfraccionActivity : AppCompatActivity() {
    private lateinit var editTextRutInspector: EditText
    private lateinit var editTextNombreLocal: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextInfraccion: EditText
    private lateinit var buttonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_infraccion)

        // Inicialización de los componentes de la UI
        editTextRutInspector = findViewById(R.id.editTextRutInspector)
        editTextNombreLocal = findViewById(R.id.editTextNombreLocal)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextInfraccion = findViewById(R.id.editTextInfraccion)
        buttonGuardar = findViewById(R.id.buttonGuardar)

        buttonGuardar.setOnClickListener {
            guardarInfraccion()
        }
    }

    private fun guardarInfraccion() {
        val rutInspector = editTextRutInspector.text.toString().trim()
        val nombreLocal = editTextNombreLocal.text.toString().trim()
        val direccion = editTextDireccion.text.toString().trim()
        val infraccion = editTextInfraccion.text.toString().trim()

        if (rutInspector.isNotEmpty() && nombreLocal.isNotEmpty() && direccion.isNotEmpty() && infraccion.isNotEmpty()) {
            // Crear un objeto ContentValues con los valores de la nueva infracción
            val values = ContentValues().apply {
                put(InfraccionContract.InfraccionEntry.COLUMN_RUT_INSPECTOR, rutInspector)
                put(InfraccionContract.InfraccionEntry.COLUMN_NOMBRE_LOCAL, nombreLocal)
                put(InfraccionContract.InfraccionEntry.COLUMN_DIRECCION, direccion)
                put(InfraccionContract.InfraccionEntry.COLUMN_INFRACCION, infraccion)
            }

            // Insertar la nueva infracción utilizando el Content Resolver y el Content Provider
            val uri: Uri? = contentResolver.insert(CONTENT_URI, values)

            // Verificar si la inserción fue exitosa y mostrar un mensaje adecuado
            if (uri != null) {
                // Obtener el ID (folio) de la infracción creada a partir del URI
                val folio = uri.lastPathSegment

                // Construir y mostrar un AlertDialog con el folio mostrado en el mensaje
                AlertDialog.Builder(this).apply {
                    setTitle("Infracción Registrada")
                    setMessage("La infracción ha sido guardada con éxito. Folio: $folio")
                    setPositiveButton("OK") { dialog, which ->
                        // Acción opcional cuando el usuario presiona el botón "OK"
                        limpiarCampos()
                    }
                    setCancelable(false) // Esto evita que el usuario cierre el diálogo sin seleccionar una opción
                    show()
                }
            } else {
                Toast.makeText(this, "Error al guardar la infracción", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }


    private fun limpiarCampos() {
        editTextRutInspector.text.clear()
        editTextNombreLocal.text.clear()
        editTextDireccion.text.clear()
        editTextInfraccion.text.clear()
    }
}
