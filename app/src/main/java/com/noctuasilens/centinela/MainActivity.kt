package com.noctuasilens.centinela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.noctuasilens.centinela.ui.dashboard.DashboardScreen
import com.noctuasilens.centinela.ui.dashboard.DashboardViewModel
import com.noctuasilens.centinela.ui.theme.CentinelaTheme

class MainActivity : ComponentActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentinelaTheme {
                DashboardScreen(viewModel = dashboardViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-audita automáticamente al regresar de las pantallas de ajustes del SO
        dashboardViewModel.runAudit()
    }
}
