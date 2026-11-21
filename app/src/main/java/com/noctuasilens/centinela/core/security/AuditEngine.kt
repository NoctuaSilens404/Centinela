package com.noctuasilens.centinela.core.security

import android.content.Context
import com.noctuasilens.centinela.core.security.auditors.AdbAuditor
import com.noctuasilens.centinela.core.security.auditors.ScreenLockAuditor
import com.noctuasilens.centinela.core.security.auditors.SecurityPatchAuditor
import com.noctuasilens.centinela.core.security.auditors.StorageEncryptionAuditor
import com.noctuasilens.centinela.domain.model.AuditReport
import com.noctuasilens.centinela.domain.model.UserRank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * Motor central de auditoría on-device.
 * Orquesta la ejecución concurrente de los chequeos definidos para cada nivel.
 */
class AuditEngine(
    private val auditors: List<SecurityAuditor> = listOf(
        ScreenLockAuditor(),
        StorageEncryptionAuditor(),
        AdbAuditor(),
        SecurityPatchAuditor()
    )
) {
    /**
     * Ejecuta la auditoría completa de forma asíncrona en Dispatchers.IO.
     */
    suspend fun audit(context: Context, rank: UserRank = UserRank.POLLUELO): AuditReport =
        withContext(Dispatchers.IO) {
            val checkDeferreds = auditors.map { auditor ->
                async { auditor.audit(context) }
            }
            val checks = checkDeferreds.awaitAll()

            AuditReport(
                rank = rank,
                checks = checks
            )
        }
}
