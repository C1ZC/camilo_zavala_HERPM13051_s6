package com.cizc.camilo_zavala_herpm13051_s6.model

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cizc.camilo_zavala_herpm13051_s6.R
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionContract.InfraccionEntry

class DetalleInfraccionActivity : AppCompatActivity() {

    private lateinit var textViewRutInspector: TextView
    private lateinit var textViewNombreLocal: TextView
    private lateinit var textViewDireccion: TextView
    private lateinit var textViewInfraccion: TextView
    private lateinit var textViewFolio: TextView // TextView para el folio

    private var idInfraccion: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_infraccion)

        textViewRutInspector = findViewById(R.id.textViewRutInspector)
        textViewNombreLocal = findViewById(R.id.textViewNombreLocal)
        textViewDireccion = findViewById(R.id.textViewDireccion)
        textViewInfraccion = findViewById(R.id.textViewInfraccion)
        textViewFolio = findViewById(R.id.textViewFolio) // Inicialización del TextView del folio

        idInfraccion = intent.getLongExtra("INFRACCION_ID", -1L)

        configureButtons()
    }

    override fun onResume() {
        super.onResume()
        cargarDatosDeInfraccion()
    }

    private fun cargarDatosDeInfraccion() {
        if (idInfraccion != -1L) {
            val projection = arrayOf(
                InfraccionEntry.COLUMN_RUT_INSPECTOR,
                InfraccionEntry.COLUMN_NOMBRE_LOCAL,
                InfraccionEntry.COLUMN_DIRECCION,
                InfraccionEntry.COLUMN_INFRACCION
            )
            val selection = "${InfraccionEntry._ID} = ?"
            val selectionArgs = arrayOf(idInfraccion.toString())

            val cursor = contentResolver.query(
                InfraccionEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )

            cursor?.use {
                if (it.moveToFirst()) {
                    val rutInspector = it.getString(it.getColumnIndex(InfraccionEntry.COLUMN_RUT_INSPECTOR))
                    val nombreLocal = it.getString(it.getColumnIndex(InfraccionEntry.COLUMN_NOMBRE_LOCAL))
                    val direccion = it.getString(it.getColumnIndex(InfraccionEntry.COLUMN_DIRECCION))
                    val infraccion = it.getString(it.getColumnIndex(InfraccionEntry.COLUMN_INFRACCION))

                    textViewRutInspector.text = getString(R.string.detalle_rut_inspector, rutInspector)
                    textViewNombreLocal.text = getString(R.string.detalle_nombre_local, nombreLocal)
                    textViewDireccion.text = getString(R.string.detalle_direccion, direccion)
                    textViewInfraccion.text = getString(R.string.detalle_infraccion, infraccion)
                    textViewFolio.text = getString(R.string.detalle_folio, idInfraccion) // Mostrar el folio
                } else {
                    Log.d("DetalleInfraccion", "No se encontraron datos para el ID: $idInfraccion")
                }
            }
        } else {
            Log.d("DetalleInfraccion", "ID de Infracción no proporcionado o inválido")
        }
    }

    private fun configureButtons() {
        findViewById<Button>(R.id.buttonEditar).setOnClickListener {
            val intentEditar = Intent(this, EditarInfraccionActivity::class.java).apply {
                putExtra("INFRACCION_ID", idInfraccion)
            }
            startActivity(intentEditar)
        }

        findViewById<Button>(R.id.buttonVolver).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.buttonCompartir).setOnClickListener {
            val textoParaCompartir = StringBuilder(getString(R.string.compartir_encabezado))
            textoParaCompartir.append(getString(R.string.compartir_rut_inspector, textViewRutInspector.text))
            textoParaCompartir.append(getString(R.string.compartir_nombre_local, textViewNombreLocal.text))
            textoParaCompartir.append(getString(R.string.compartir_direccion, textViewDireccion.text))
            textoParaCompartir.append(getString(R.string.compartir_infraccion, textViewInfraccion.text))
            // Añadir el folio al texto para compartir
            textoParaCompartir.append("\n").append(getString(R.string.detalle_folio, idInfraccion))

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, textoParaCompartir.toString())
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.compartir)))
        }
    }
}
