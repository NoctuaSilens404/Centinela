Centinela 
"La sabiduría es tu mejor defensa"

Centinela es una aplicación móvil nativa (Android) de seguridad digital y privacidad diseñada para auditar la configuración del dispositivo de forma automática, educar al usuario y fomentar hábitos de protección mediante un sistema de progresión gamificado.

A diferencia de las herramientas pasivas de seguridad o las listas de autoevaluación manuales, Centinela utiliza APIs nativas del sistema operativo para verificar el cumplimiento real de configuraciones de seguridad, garantizando que el usuario progrese al adquirir y demostrar verdaderos hábitos de higiene digital.

Misión y Filosofía
Misión
Ayudar a las personas a comprender los riesgos digitales, fortalecer la configuración de sus dispositivos y desarrollar hábitos defensivos consistentes a través de la educación práctica y la verificación técnica.

Filosofía de Diseño
La seguridad es un hábito, no un producto: Ninguna herramienta sustituye la comprensión de los riesgos y la adopción de buenas prácticas.
Verificación real, no declaraciones: Centinela no confía en autoevaluaciones manuales; el motor de la app audita el sistema operativo directamente.
Privacidad desde la base: La aplicación no recopila datos personales. Toda auditoría se ejecuta de manera local (on-device).
Sistema de Niveles y Progresión
El camino de aprendizaje en Centinela está estructurado en cinco rangos progresivos:

Nivel	Rango	Enfoque Principal	Misiones y Verificaciones
1. Polluelo 	Primeros Pasos	Higiene digital básica y protección del dispositivo físico.	Bloqueo de pantalla, cifrado de disco (FBE), estado de depuración USB y parches de seguridad.
2. Aprendiz 	Navegación y Privacidad	Reducción de la huella digital y protección contra rastreadores.	Configuración de DNS privado con filtrado, auditoría de permisos de apps y bóvedas de contraseñas.
3. Vigilante 	Datos y Redes	Cifrado en tránsito, respaldos y robustez en redes domésticas.	Estrategia de respaldos 3-2-1, eliminación de 2FA por SMS y aislamiento de redes Wi-Fi.
4. Centinela 	Hardening Avanzado	Minimización de la superficie de ataque y soberanía digital.	Cifrado completo de almacenamiento secundario, entornos aislados y eliminación de metadatos.
5. Gran Búho 	Cultura y Mentoría	Concienciación comunitaria y respuesta ante incidentes.	Protocolos de contingencia familiar, mentoría digital y mantenimiento continuo de rachas.
Arquitectura Técnica y Motor de Auditoría (Android)
La versión inicial de Centinela está desarrollada como una aplicación nativa para Android en Kotlin, aprovechando los marcos de seguridad del SDK de Android para realizar verificaciones directas sobre el hardware y el sistema operativo.

Comprobaciones Automáticas (Nivel Polluelo)
El motor de auditoría ejecuta diagnósticos en segundo plano utilizando las APIs nativas del sistema operativo:

El siguiente cuadro detalla las comprobaciones automáticas ejecutadas por el módulo de auditoría:

Diagnóstico	Componente del SDK Utilizado	Criterio de Verificación	Acción de Remedio
Bloqueo de Pantalla	KeyguardManager.isDeviceSecure	Verifica presencia de PIN, patrón, contraseña o biometría activa.	Redirección a Ajustes de Seguridad.
Cifrado de Almacenamiento	StorageManager.isEncrypted	Confirma que el almacenamiento interno cuenta con cifrado activo (FBE).	Guía de actualización o configuración del SO.
Depuración USB (ADB)	Settings.Global.ADB_ENABLED	Retorna 0 (desactivado) para evitar accesos no autorizados por puerto físico.	Redirección a Opciones de Desarrollador.
Parches de Seguridad	Build.VERSION.SECURITY_PATCH	Comprueba que la fecha del parche no supere los 90 días de antigüedad.	Redirección a Actualizaciones del Sistema.
Roadmap del Proyecto
Fase 1: Especificación y Filosofía — Definición de misiones, arquitectura y sistema de niveles. (Completado)
Fase 2: MVP en Android (Kotlin) — Implementación del motor de auditoría de seguridad para el nivel Polluelo.
Fase 3: Interfaz Gamificada — Integración del Dashboard interactivo, mapa de progreso y sistema de insignias.
Fase 4: Módulos de Aprendiz y Vigilante — Escáner de permisos e inspección de DNS.
Fase 5: Multiplataforma — Expansión a sistemas operativos de escritorio (Windows, Linux) e iOS.
Requisitos y Desarrollo Local
Para compilar e iterar sobre el código de Centinela localmente, se requieren las siguientes herramientas:

Android Studio (versión reciente).
JDK 17 o superior.
Android SDK (API Level 26 / Android 8.0 Oreo como mínimo; API Level 34 como objetivo).
cd Centinela
Licencia
Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo LICENSE para obtener más detalles.

Autor y Mantenimiento
Desarrollado y mantenido por René O. Acosta (NoctuaSilens404).