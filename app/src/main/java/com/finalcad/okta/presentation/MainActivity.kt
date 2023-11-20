package com.finalcad.okta.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.finalcad.okta.OktaIntegrationApp
import com.finalcad.okta.UserRepository
import com.finalcad.okta.ui.theme.OktaIntegrationTheme

class MainActivity : ComponentActivity() {

    private val repository: UserRepository by lazy {
        (application as? OktaIntegrationApp)
            ?.userRepository
            ?: throw IllegalStateException("This activity should be used by OktaIntegrationApp")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OktaIntegrationTheme {
                // A surface container using the 'background' color from the theme
                MainScreen(
                    activity = this,
                    userRepository = repository,
                )
            }
        }
    }
}
