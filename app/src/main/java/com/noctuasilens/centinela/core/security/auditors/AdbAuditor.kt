package com.noctuasilens.centinela.core.security.auditors

import android.content.Context
import android.provider.Settings
import com.noctuasilens.centinela.core.security.SecurityAuditor
import com.noctuasilens.centinela.domain.model.CheckCategory
import com.noctuasilens.centinela.domain.model.CheckStatus
import com.noctuasilens.centinela.domain.model.RemedyAction
import com.noctuasilens.centinela.domain.model.SecurityCheck
import com.noctuasilens.centinela.domain.model.UserRank

/**
 * Diagnóstico: Depuración USB (ADB)
 * Componente SDK: Settings.Global.ADB_ENABLED
 * Criterio: Retorna 0 (desactivado) para evitar accesos no autorizados por puerto físico.
 */
class AdbAuditor : SecurityAuditor {
    override val checkId: String = "adb_debugging"

    override suspend fun audit(context: Context): SecurityCheck {
        val adbEnabled = try {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.ADB_ENABLED,
                0
            ) == 1
        } catch (_: Exception) {
            false
        }

        return if (!adbEnabled) {
            SecurityCheck(
                id = checkId,
                title = "Depuración USB (ADB)",
                shortDescription = "Interfaz de depuración desactivada.",
                status = CheckStatus.SECURE,
                technicalDetails = "El puerto USB no expone una shell de comandos sin autorización, previniendo ataques de tipo Juice Jacking o extracción no supervisada.",
                category = CheckCategory.DEVELOPMENT_INTERFACE,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.None
            )
        } else {
            SecurityCheck(
                id = checkId,
                title = "Depuración USB (ADB)",
                shortDescription = "Depuración USB activada.",
                status = CheckStatus.WARNING,
                technicalDetails = "Cualquier equipo al que conectes el dispositivo por cable podría ejecutar comandos, instalar apps o acceder a memoria si se autoriza la clave.",
                category = CheckCategory.DEVELOPMENT_INTERFACE,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.OpenSettings(
                    intentAction = Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS,
                    label = "Opciones de Desarrollador"
                )
            )
        }
    }
}
