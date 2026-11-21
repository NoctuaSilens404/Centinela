package com.noctuasilens.centinela.ui.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.noctuasilens.centinela.domain.model.AuditReport
import com.noctuasilens.centinela.ui.theme.OwlGold
import com.noctuasilens.centinela.ui.theme.SecurityAmber
import com.noctuasilens.centinela.ui.theme.SecurityGreen
import com.noctuasilens.centinela.ui.theme.SecurityRed
import com.noctuasilens.centinela.ui.theme.SurfaceCard
import com.noctuasilens.centinela.ui.theme.SurfaceCardBorder
import com.noctuasilens.centinela.ui.theme.TextMuted
import com.noctuasilens.centinela.ui.theme.TextPrimary
import com.noctuasilens.centinela.ui.theme.TextSecondary

@Composable
fun RankHeader(
    report: AuditReport,
    modifier: Modifier = Modifier
) {
    val progress = if (report.totalChecks > 0) {
        report.passedChecks.toFloat() / report.totalChecks.toFloat()
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "progressAnimation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceCard)
            .border(1.dp, SurfaceCardBorder, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        // Encabezado con Icono y Rango
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Rango ${report.rank.levelNumber}: ${report.rank.title}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = OwlGold
                )
                Text(
                    text = report.rank.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(OwlGold.copy(alpha = 0.15f))
                    .border(1.dp, OwlGold.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = OwlGold,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = report.rank.focusDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de progreso y score
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Higiene Digital: ${report.passedChecks}/${report.totalChecks} misiones",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            Text(
                text = "${report.securityScore}%",
                style = MaterialTheme.typography.titleLarge,
                color = if (report.isRankCompleted) SecurityGreen else OwlGold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = if (report.isRankCompleted) SecurityGreen else OwlGold,
            trackColor = SurfaceCardBorder,
            strokeCap = StrokeCap.Round
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contadores de estado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusBadge(
                label = "${report.passedChecks} Seguros",
                color = SecurityGreen,
                modifier = Modifier.weight(1f)
            )
            if (report.warningChecks > 0) {
                StatusBadge(
                    label = "${report.warningChecks} Avisos",
                    color = SecurityAmber,
                    modifier = Modifier.weight(1f)
                )
            }
            if (report.dangerChecks > 0) {
                StatusBadge(
                    label = "${report.dangerChecks} Riesgos",
                    color = SecurityRed,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatusBadge(
    label: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}
