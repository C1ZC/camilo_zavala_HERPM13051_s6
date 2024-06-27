package com.cizc.camilo_zavala_herpm13051_s6

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.cizc.camilo_zavala_herpm13051_s6.model.AgregarInfraccionActivity
import com.cizc.camilo_zavala_herpm13051_s6.model.ListarInfraccionesActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el botón para agregar infracciones
        val btnAgregarInfraccion = findViewById<Button>(R.id.btnAgregarInfraccion)
        btnAgregarInfraccion.setOnClickListener {
            // Navegar a la actividad de agregar infracción
            val intent = Intent(this, AgregarInfraccionActivity::class.java)
            startActivity(intent)
        }

        // Inicializar el botón para ver el listado de infracciones
        val btnVerInfracciones = findViewById<Button>(R.id.btnVerInfracciones)
        btnVerInfracciones.setOnClickListener {
            // Navegar a la actividad que muestra el listado de infracciones
            val intent = Intent(this, ListarInfraccionesActivity::class.java)
            startActivity(intent)
        }
    }
}