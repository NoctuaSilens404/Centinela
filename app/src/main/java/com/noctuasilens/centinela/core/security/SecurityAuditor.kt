package com.noctuasilens.centinela.core.security

import android.content.Context
import com.noctuasilens.centinela.domain.model.SecurityCheck

/**
 * Interfaz base para cualquier módulo auditor de seguridad del SDK.
 * Cada auditor interactúa de manera local con las APIs del sistema operativo.
 */
interface SecurityAuditor {
    val checkId: String
    suspend fun audit(context: Context): SecurityCheck
}
