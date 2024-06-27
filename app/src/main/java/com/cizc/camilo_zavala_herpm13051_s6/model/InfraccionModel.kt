package com.cizc.camilo_zavala_herpm13051_s6.model
import java.io.Serializable

data class InfraccionModel(
    var id: Int? = null,
    var rutInspector: String,
    var nombreLocal: String,
    var direccion: String,
    var infraccion: String
) : Serializable

