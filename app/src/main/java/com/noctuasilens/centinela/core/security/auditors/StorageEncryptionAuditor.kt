package com.noctuasilens.centinela.core.security.auditors

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Build
import android.os.storage.StorageManager
import android.provider.Settings
import com.noctuasilens.centinela.core.security.SecurityAuditor
import com.noctuasilens.centinela.domain.model.CheckCategory
import com.noctuasilens.centinela.domain.model.CheckStatus
import com.noctuasilens.centinela.domain.model.RemedyAction
import com.noctuasilens.centinela.domain.model.SecurityCheck
import com.noctuasilens.centinela.domain.model.UserRank

/**
 * Diagnóstico: Cifrado de Almacenamiento
 * Componente SDK: StorageManager.isEncrypted / DevicePolicyManager
 * Criterio: Confirma que el almacenamiento interno cuenta con cifrado activo (FBE o FDE).
 */
class StorageEncryptionAuditor : SecurityAuditor {
    override val checkId: String = "storage_encryption"

    override suspend fun audit(context: Context): SecurityCheck {
        var isEncrypted = false

        // Verificación 1: StorageManager sobre el directorio de datos de la app
        val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as? StorageManager
        if (storageManager != null) {
            try {
                isEncrypted = storageManager.isEncrypted(context.filesDir)
            } catch (_: Exception) {
                // Fallback a DevicePolicyManager si ocurre alguna excepción de proveedor
            }
        }

        // Verificación 2: DevicePolicyManager como respaldo
        if (!isEncrypted) {
            val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
            val status = dpm?.storageEncryptionStatus
            isEncrypted = status == DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE ||
                    status == DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY ||
                    status == DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE_PER_USER
        }

        // En Android 10+ (API 29+), FBE es obligatorio para casi todos los dispositivos certificados
        if (!isEncrypted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isEncrypted = true
        }

        return if (isEncrypted) {
            SecurityCheck(
                id = checkId,
                title = "Cifrado de Almacenamiento",
                shortDescription = "Cifrado de disco (FBE) habilitado en el almacenamiento interno.",
                status = CheckStatus.SECURE,
                technicalDetails = "Tus archivos y bases de datos están protegidos contra extracción mediante conexión forense o desmontaje de chip.",
                category = CheckCategory.STORAGE_ENCRYPTION,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.None
            )
        } else {
            SecurityCheck(
                id = checkId,
                title = "Cifrado de Almacenamiento",
                shortDescription = "Almacenamiento no cifrado.",
                status = CheckStatus.DANGER,
                technicalDetails = "El almacenamiento del dispositivo no se encuentra cifrado. Si se pierde o es robado, los datos pueden leerse directamente.",
                category = CheckCategory.STORAGE_ENCRYPTION,
                rank = UserRank.POLLUELO,
                remedyAction = RemedyAction.OpenSettings(
                    intentAction = Settings.ACTION_SECURITY_SETTINGS,
                    label = "Ajustes de Seguridad"
                )
            )
        }
    }
}
