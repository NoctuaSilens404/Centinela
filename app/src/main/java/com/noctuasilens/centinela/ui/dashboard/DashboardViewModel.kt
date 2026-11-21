package com.noctuasilens.centinela.ui.dashboard

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.noctuasilens.centinela.core.security.AuditEngine
import com.noctuasilens.centinela.domain.model.AuditReport
import com.noctuasilens.centinela.domain.model.RemedyAction
import com.noctuasilens.centinela.domain.model.UserRank
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(val report: AuditReport) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}

class DashboardViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val auditEngine = AuditEngine()

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        runAudit()
    }

    fun runAudit() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            try {
                val report = auditEngine.audit(getApplication(), UserRank.POLLUELO)
                _uiState.value = DashboardUiState.Success(report)
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.localizedMessage ?: "Error inesperado durante la auditoría")
            }
        }
    }

    fun executeRemedy(context: Context, remedyAction: RemedyAction.OpenSettings) {
        try {
            val intent = Intent(remedyAction.intentAction).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(
                context,
                "No se pudo abrir la pantalla de ajustes solicitada.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
