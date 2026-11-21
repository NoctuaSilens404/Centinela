package com.noctuasilens.centinela.ui.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.noctuasilens.centinela.domain.model.CheckStatus
import com.noctuasilens.centinela.domain.model.RemedyAction
import com.noctuasilens.centinela.domain.model.SecurityCheck
import com.noctuasilens.centinela.ui.theme.SecurityAmber
import com.noctuasilens.centinela.ui.theme.SecurityGreen
import com.noctuasilens.centinela.ui.theme.SecurityRed
import com.noctuasilens.centinela.ui.theme.SurfaceCard
import com.noctuasilens.centinela.ui.theme.SurfaceCardBorder
import com.noctuasilens.centinela.ui.theme.TextMuted
import com.noctuasilens.centinela.ui.theme.TextPrimary
import com.noctuasilens.centinela.ui.theme.TextSecondary

@Composable
fun SecurityCheckCard(
    check: SecurityCheck,
    onRemedyClick: (RemedyAction.OpenSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val (statusColor, statusIcon, statusText) = when (check.status) {
        CheckStatus.SECURE -> Triple(SecurityGreen, Icons.Default.CheckCircle, "Protegido")
        CheckStatus.WARNING -> Triple(SecurityAmber, Icons.Default.Warning, "Atención")
        CheckStatus.DANGER -> Triple(SecurityRed, Icons.Default.Error, "Riesgo")
        CheckStatus.UNKNOWN -> Triple(TextMuted, Icons.Default.Help, "Desconocido")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, SurfaceCardBorder, RoundedCornerShape(16.dp))
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(statusColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = statusText,
                    tint = statusColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = check.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )

                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = check.shortDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }

        // Sección expandible con detalle técnico
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceCardBorder.copy(alpha = 0.4f))
                        .padding(12.dp)
                ) {
                    Text(
                        text = check.technicalDetails,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                }
            }
        }

        // Botón de acción de remedio si aplica
        if (check.status != CheckStatus.SECURE && check.remedyAction is RemedyAction.OpenSettings) {
            val action = check.remedyAction
            Spacer(modifier = Modifier.height(12.dp))

            FilledTonalButton(
                onClick = { onRemedyClick(action) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = statusColor.copy(alpha = 0.15f),
                    contentColor = statusColor
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = action.label,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
