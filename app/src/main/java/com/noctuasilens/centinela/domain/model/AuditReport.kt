package com.noctuasilens.centinela.domain.model

/**
 * Reporte global de la auditoría de seguridad para un rango determinado.
 */
data class AuditReport(
    val rank: UserRank,
    val checks: List<SecurityCheck>,
    val timestamp: Long = System.currentTimeMillis()
) {
    val totalChecks: Int
        get() = checks.size

    val passedChecks: Int
        get() = checks.count { it.status == CheckStatus.SECURE }

    val warningChecks: Int
        get() = checks.count { it.status == CheckStatus.WARNING }

    val dangerChecks: Int
        get() = checks.count { it.status == CheckStatus.DANGER }

    /**
     * Puntuación de seguridad de 0 a 100 basada en las misiones cumplidas.
     */
    val securityScore: Int
        get() = if (totalChecks == 0) 0 else ((passedChecks.toDouble() / totalChecks) * 100).toInt()

    /**
     * Indica si todas las verificaciones del nivel actual fueron aprobadas.
     */
    val isRankCompleted: Boolean
        get() = totalChecks > 0 && passedChecks == totalChecks
}
