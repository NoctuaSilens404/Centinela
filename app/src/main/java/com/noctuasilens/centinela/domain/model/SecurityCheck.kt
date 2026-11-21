package com.noctuasilens.centinela.domain.model

/**
 * Estado del diagnóstico de seguridad.
 */
enum class CheckStatus {
    SECURE,    // Cumple con el criterio de seguridad
    WARNING,   // Advertencia: configuración no óptima o desactualizada
    DANGER,    // Riesgo inminente: vulnerabilidad física o lógica
    UNKNOWN    // No se pudo determinar el estado en el dispositivo
}

/**
 * Categorías de seguridad para organizar misiones.
 */
enum class CheckCategory {
    PHYSICAL_DEVICE,
    STORAGE_ENCRYPTION,
    DEVELOPMENT_INTERFACE,
    SYSTEM_INTEGRITY
}

/**
 * Acción de remedio interactiva para redirigir al usuario a la pantalla del SO correspondiente.
 */
sealed class RemedyAction {
    data class OpenSettings(
        val intentAction: String,
        val label: String
    ) : RemedyAction()

    data object None : RemedyAction()
}

/**
 * Representa una misión de verificación de seguridad individual.
 */
data class SecurityCheck(
    val id: String,
    val title: String,
    val shortDescription: String,
    val status: CheckStatus,
    val technicalDetails: String,
    val category: CheckCategory,
    val rank: UserRank = UserRank.POLLUELO,
    val remedyAction: RemedyAction = RemedyAction.None
)
