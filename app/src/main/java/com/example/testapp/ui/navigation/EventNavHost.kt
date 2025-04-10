package com.example.testapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testapp.ui.eventdetail.EventDetailScreen
import com.example.testapp.ui.eventlist.EventListScreen

@Composable
fun EventNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.EventList.route
    ) {
        composable(route = Screen.EventList.route) {
            EventListScreen(
                onEventClick = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId))
                }
            )
        }

        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.IntType
                }
            )
        ) {
            EventDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object EventList : Screen("event_list")
    data object EventDetail : Screen("event_detail/{eventId}") {
        fun createRoute(eventId: Int) = "event_detail/$eventId"
    }
}