package com.noctuasilens.centinela.core.security.auditors

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.noctuasilens.centinela.core.security.SecurityAuditor
import com.noctuasilens.centinela.domain.model.CheckCategory
import com.noctuasilens.centinela.domain.model.CheckStatus
import com.noctuasilens.centinela.domain.model.RemedyAction
import com.noctuasilens.centinela.domain.model.SecurityCheck
import com.noctuasilens.centinela.domain.model.UserRank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Diagnóstico: Parches de Seguridad
 * Componente SDK: Build.VERSION.SECURITY_PATCH
 * Criterio: Comprueba que la fecha del parche no supere los 90 días de antigüedad.
 */
class SecurityPatchAuditor : SecurityAuditor {
    override val checkId: String = "security_patch"

    override suspend fun audit(context: Context): SecurityCheck {
        val patchString = Build.VERSION.SECURITY_PATCH

        if (patchString.isNullOrBlank()) {
            return SecurityCheck(
                id = checkId,
                title = "Parches de Seguridad",
                shortDescription = "No se pudo leer la fecha del parche.",
                status = CheckStatus.UNKNOWN,
                technicalDetails = "El fabricante del dispositivo no reportó una fecha de parche estándar en Build.VERSION.SECURITY_PATCH.",
                category = CheckCategory.SYSTEM_INTEGRITY,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.OpenSettings(
                    intentAction = Settings.ACTION_DEVICE_INFO_SETTINGS,
                    label = "Ajustes del Dispositivo"
                )
            )
        }

        return try {
            val patchDate = LocalDate.parse(patchString, DateTimeFormatter.ISO_LOCAL_DATE)
            val today = LocalDate.now()
            val daysDiff = ChronoUnit.DAYS.between(patchDate, today)

            if (daysDiff <= 90) {
                SecurityCheck(
                    id = checkId,
                    title = "Parches de Seguridad",
                    shortDescription = "Nivel de parche reciente ($patchString).",
                    status = CheckStatus.SECURE,
                    technicalDetails = "Tu dispositivo cuenta con actualizaciones de seguridad recientes (hace $daysDiff días), protegiéndote contra vulnerabilidades CVE conocidas.",
                    category = CheckCategory.SYSTEM_INTEGRITY,
                    rank = UserRank.POLLUELO,
                    remedyAction = RemedyAction.None
                )
            } else {
                SecurityCheck(
                    id = checkId,
                    title = "Parches de Seguridad",
                    shortDescription = "Parche desactualizado ($daysDiff días de antigüedad).",
                    status = CheckStatus.WARNING,
                    technicalDetails = "La última actualización de seguridad data del $patchString. Dispositivos sin parches recientes pueden ser vulnerables a exploits documentados.",
                    category = CheckCategory.SYSTEM_INTEGRITY,
                    rank = UserRank.POLLUELO,
                    remedyAction = RemedyAction.OpenSettings(
                        intentAction = Settings.ACTION_DEVICE_INFO_SETTINGS,
                        label = "Buscar Actualizaciones"
                    )
                )
            }
        } catch (_: Exception) {
            SecurityCheck(
                id = checkId,
                title = "Parches de Seguridad",
                shortDescription = "Parche reportado: $patchString",
                status = CheckStatus.UNKNOWN,
                technicalDetails = "Formato de fecha no reconocido para el parche de seguridad.",
                category = CheckCategory.SYSTEM_INTEGRITY,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.None
            )
        }
    }
}
