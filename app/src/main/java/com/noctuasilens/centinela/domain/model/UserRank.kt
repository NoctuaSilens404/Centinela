package com.noctuasilens.centinela.domain.model

/**
 * Representa los 5 rangos progresivos de aprendizaje y hardening en Centinela.
 */
enum class UserRank(
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val focusDescription: String
) {
    POLLUELO(
        levelNumber = 1,
        title = "Polluelo",
        subtitle = "Primeros Pasos",
        focusDescription = "Higiene digital básica y protección del dispositivo físico."
    ),
    APRENDIZ(
        levelNumber = 2,
        title = "Aprendiz",
        subtitle = "Navegación y Privacidad",
        focusDescription = "Reducción de la huella digital y protección contra rastreadores."
    ),
    VIGILANTE(
        levelNumber = 3,
        title = "Vigilante",
        subtitle = "Datos y Redes",
        focusDescription = "Cifrado en tránsito, respaldos y robustez en redes domésticas."
    ),
    CENTINELA(
        levelNumber = 4,
        title = "Centinela",
        subtitle = "Hardening Avanzado",
        focusDescription = "Minimización de la superficie de ataque y soberanía digital."
    ),
    GRAN_BUHO(
        levelNumber = 5,
        title = "Gran Búho",
        subtitle = "Cultura y Mentoría",
        focusDescription = "Concienciación comunitaria y respuesta ante incidentes."
    );

    companion object {
        fun fromLevel(level: Int): UserRank = entries.find { it.levelNumber == level } ?: POLLUELO
    }
}
