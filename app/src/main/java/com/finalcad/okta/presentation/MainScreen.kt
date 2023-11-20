package com.finalcad.okta.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.finalcad.okta.UserRepository
import com.finalcad.okta.UserStatus

@Composable
fun MainScreen(
    activity: MainActivity,
    userRepository: UserRepository,
) {
    val state by userRepository.userStatus.collectAsState(initial = UserStatus.Loading)
    when (val actualState = state) {
        UserStatus.Loading -> LoadingScreen()
        UserStatus.NotLogged -> StartScreen { userRepository.login(activity = activity) }
        is UserStatus.Logged -> LoggedInScreen(userId = actualState.userId) {
            userRepository.logout(
                activity = activity
            )
        }
    }
}
