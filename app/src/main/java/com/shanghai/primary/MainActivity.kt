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
import com.shanghai.primary.data.model.Subject
import com.shanghai.primary.ui.game.FlashCardViewModelFactory
import com.shanghai.primary.ui.game.DragMatchViewModelFactory
import com.shanghai.primary.ui.game.WordScrambleViewModelFactory
import com.shanghai.primary.ui.game.TimedChallengeViewModelFactory
import com.shanghai.primary.ui.game.GameViewModelFactory
import com.shanghai.primary.ui.theme.AppTheme
import com.shanghai.primary.ui.game.FlashCardScreen
import com.shanghai.primary.ui.game.FlashCardViewModel
import com.shanghai.primary.ui.game.GameScreen
import com.shanghai.primary.ui.game.GameViewModel
import com.shanghai.primary.ui.game.DragMatchScreen
import com.shanghai.primary.ui.game.DragMatchViewModel
import com.shanghai.primary.ui.game.WordScrambleScreen
import com.shanghai.primary.ui.game.WordScrambleViewModel
import com.shanghai.primary.ui.game.TimedChallengeScreen
import com.shanghai.primary.ui.game.TimedChallengeViewModel
import com.shanghai.primary.ui.home.GameModeScreen
import com.shanghai.primary.ui.home.HomeScreen
import com.shanghai.primary.ui.home.HomeViewModel
import com.shanghai.primary.ui.home.DailyPracticeScreen
import com.shanghai.primary.ui.home.DailyPracticeViewModel
import com.shanghai.primary.ui.home.WrongQuestionScreen
import com.shanghai.primary.ui.home.WrongQuestionViewModel

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
                    val grade = homeViewModel.state.value.selectedGrade
                    navController.navigate("gamemode/${subject.name}/$grade")
                },
                onOpenWrongQuestions = {
                    navController.navigate("wrongQuestions")
                },
                onOpenDailyPractice = {
                    navController.navigate("dailyPractice")
                }
            )
        }
        composable("gamemode/{subject}/{grade}") { backStackEntry ->
            val subjectName = backStackEntry.arguments?.getString("subject") ?: "MATH"
            val grade = backStackEntry.arguments?.getString("grade")?.toIntOrNull() ?: 1
            val subject = Subject.valueOf(subjectName)
            GameModeScreen(
                subject = subject,
                grade = grade,
                onSelectGameMode = { gameType ->
                    navController.navigate("game/${subject.name}/$gameType/$grade")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("game/{subject}/{gameType}/{grade}") { backStackEntry ->
            val subjectName = backStackEntry.arguments?.getString("subject") ?: "MATH"
            val gameType = backStackEntry.arguments?.getString("gameType") ?: "QUIZ"
            val grade = backStackEntry.arguments?.getString("grade")?.toIntOrNull() ?: 1
            val subject = Subject.valueOf(subjectName)
            when (gameType) {
                "QUIZ" -> {
                    val gameViewModel: GameViewModel = viewModel(
                        factory = GameViewModelFactory(subject, grade)
                    )
                    GameScreen(
                        vm = gameViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                "FLASHCARD" -> {
                    val flashCardViewModel: FlashCardViewModel = viewModel(
                        factory = FlashCardViewModelFactory(subject, grade)
                    )
                    FlashCardScreen(
                        vm = flashCardViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                "DRAG_MATCH" -> {
                    val dragMatchViewModel: DragMatchViewModel = viewModel(
                        factory = DragMatchViewModelFactory(subject, grade)
                    )
                    DragMatchScreen(
                        vm = dragMatchViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                "WORD_SCRAMBLE" -> {
                    val wordScrambleViewModel: WordScrambleViewModel = viewModel(
                        factory = WordScrambleViewModelFactory(subject, grade)
                    )
                    WordScrambleScreen(
                        vm = wordScrambleViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                "TIMED" -> {
                    val timedViewModel: TimedChallengeViewModel = viewModel(
                        factory = TimedChallengeViewModelFactory(subject, grade)
                    )
                    TimedChallengeScreen(
                        vm = timedViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                else -> {
                    val gameViewModel: GameViewModel = viewModel(
                        factory = GameViewModelFactory(subject, grade)
                    )
                    GameScreen(
                        vm = gameViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
        composable("wrongQuestions") {
            val wrongQuestionViewModel: WrongQuestionViewModel = viewModel()
            WrongQuestionScreen(
                vm = wrongQuestionViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("dailyPractice") {
            val dailyPracticeViewModel: DailyPracticeViewModel = viewModel()
            DailyPracticeScreen(
                vm = dailyPracticeViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
