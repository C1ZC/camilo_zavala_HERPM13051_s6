package com.cizc.camilo_zavala_herpm13051_s6

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cizc.camilo_zavala_herpm13051_s6.model.InfraccionContract

class ListAdapter(private val context: Context, private var cursor: Cursor?) :
    RecyclerView.Adapter<ListAdapter.InfraccionViewHolder>() {

    // Definición del ViewHolder interno
    class InfraccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombreLocal: TextView = itemView.findViewById(R.id.nombreLocalTextView)
        val textViewDireccion: TextView = itemView.findViewById(R.id.direccionTextView)
        val textViewInfraccion: TextView = itemView.findViewById(R.id.infraccionTextView)
    }

    // Variable para el listener de clics en los elementos
    private var onItemClickListener: ((Long) -> Unit)? = null

    // Método público para establecer el listener
    fun setOnItemClickListener(listener: (Long) -> Unit) {
        onItemClickListener = listener
    }

    // Creación de nuevas vistas (invocada por el layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfraccionViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_infraccion, parent, false)
        return InfraccionViewHolder(itemView)
    }

    // Reemplazo del contenido de las vistas (invocado por el layout manager)
    override fun onBindViewHolder(holder: InfraccionViewHolder, position: Int) {
        cursor?.apply {
            moveToPosition(position)
            val id = getLong(getColumnIndexOrThrow(InfraccionContract.InfraccionEntry._ID))
            val nombreLocal = getString(getColumnIndexOrThrow(InfraccionContract.InfraccionEntry.COLUMN_NOMBRE_LOCAL))
            val direccion = getString(getColumnIndexOrThrow(InfraccionContract.InfraccionEntry.COLUMN_DIRECCION))
            val infraccion = getString(getColumnIndexOrThrow(InfraccionContract.InfraccionEntry.COLUMN_INFRACCION))

            holder.textViewNombreLocal.text = nombreLocal
            holder.textViewDireccion.text = direccion
            holder.textViewInfraccion.text = infraccion

            // Configuración del listener de clic para cada elemento de la lista
            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(id) // Invoca el listener pasando el ID de la infracción
            }
        }
    }

    // Retorna el tamaño del dataset (invocado por el layout manager)
    override fun getItemCount(): Int = cursor?.count ?: 0

    // Método para intercambiar el cursor cuando los datos cambian
    fun swapCursor(newCursor: Cursor?) {
        if (cursor != null) cursor?.close()
        cursor = newCursor
        notifyDataSetChanged()
    }

    // Limpieza del cursor cuando el adaptador se desasocia del RecyclerView
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        cursor?.close()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}
