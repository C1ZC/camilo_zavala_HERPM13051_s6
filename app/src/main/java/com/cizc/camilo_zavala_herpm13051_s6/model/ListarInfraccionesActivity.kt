package com.cizc.camilo_zavala_herpm13051_s6.model

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cizc.camilo_zavala_herpm13051_s6.ListAdapter
import com.cizc.camilo_zavala_herpm13051_s6.R
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionContract.InfraccionEntry
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListarInfraccionesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_infracciones)

        // Añadir manejador de evento clic al FloatingActionButton
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        fab.setOnClickListener {
            val intent = Intent(this, AgregarInfraccionActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        actualizarListaInfracciones()
    }

    private fun actualizarListaInfracciones() {
        val cursor = contentResolver.query(
            InfraccionEntry.CONTENT_URI,
            null, // Proyección: null para retornar todas las columnas
            null, // Sin cláusula de selección
            null, // Sin argumentos de selección
            null  // Orden por defecto
        )

        cursor?.let {
            val adapter = ListAdapter(this, it)
            adapter.setOnItemClickListener { idInfraccion ->
                // Intent para iniciar DetalleInfraccionActivity con el ID de infracción
                val intent = Intent(this, DetalleInfraccionActivity::class.java).apply {
                    putExtra("INFRACCION_ID", idInfraccion)
                }
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }
    }
}