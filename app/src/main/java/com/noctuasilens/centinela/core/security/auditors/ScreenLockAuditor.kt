package com.noctuasilens.centinela.core.security.auditors

import android.app.KeyguardManager
import android.content.Context
import android.provider.Settings
import com.noctuasilens.centinela.core.security.SecurityAuditor
import com.noctuasilens.centinela.domain.model.CheckCategory
import com.noctuasilens.centinela.domain.model.CheckStatus
import com.noctuasilens.centinela.domain.model.RemedyAction
import com.noctuasilens.centinela.domain.model.SecurityCheck
import com.noctuasilens.centinela.domain.model.UserRank

/**
 * Diagnóstico: Bloqueo de Pantalla
 * Componente SDK: KeyguardManager.isDeviceSecure
 * Criterio: Verifica presencia de PIN, patrón, contraseña o biometría activa.
 */
class ScreenLockAuditor : SecurityAuditor {
    override val checkId: String = "screen_lock"

    override suspend fun audit(context: Context): SecurityCheck {
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
        val isSecure = keyguardManager?.isDeviceSecure == true

        return if (isSecure) {
            SecurityCheck(
                id = checkId,
                title = "Bloqueo de Pantalla",
                shortDescription = "Protección física con PIN, patrón, clave o biometría.",
                status = CheckStatus.SECURE,
                technicalDetails = "El dispositivo cuenta con un mecanismo de bloqueo criptográfico y autenticación activa.",
                category = CheckCategory.PHYSICAL_DEVICE,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.None
            )
        } else {
            SecurityCheck(
                id = checkId,
                title = "Bloqueo de Pantalla",
                shortDescription = "Sin bloqueo seguro activo.",
                status = CheckStatus.DANGER,
                technicalDetails = "Cualquier persona con acceso físico al dispositivo puede acceder a tus fotos, chats y aplicaciones.",
                category = CheckCategory.PHYSICAL_DEVICE,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.OpenSettings(
                    intentAction = Settings.ACTION_SECURITY_SETTINGS,
                    label = "Configurar Bloqueo"
                )
            )
        }
    }
}
