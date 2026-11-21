package com.noctuasilens.centinela

import android.app.Application

/**
 * Punto de entrada principal de la aplicación Centinela.
 */
class CentinelaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicialización de telemetría local / configuración on-device si es requerida
    }
}
