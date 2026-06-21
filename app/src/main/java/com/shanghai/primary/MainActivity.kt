package com.shanghai.primary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shanghai.primary.ui.game.GameScreen
import com.shanghai.primary.ui.game.GameViewModel
import com.shanghai.primary.ui.home.HomeScreen
import com.shanghai.primary.ui.home.HomeViewModel
import com.shanghai.primary.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                vm = homeViewModel,
                onSelectSubject = { subject ->
                    navController.navigate("game/${subject.name}")
                }
            )
        }
        composable("game/{subject}") { backStackEntry ->
            val subjectName = backStackEntry.arguments?.getString("subject") ?: "MATH"
            val subject = Subject.valueOf(subjectName)
            val gameViewModel: GameViewModel = viewModel(
                factory = GameViewModelFactory(subject, grade = 1)
            )
            GameScreen(
                vm = gameViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
