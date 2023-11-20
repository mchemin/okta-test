package com.finalcad.okta.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StartScreen(
    logIn: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Button(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            onClick = logIn
        ) {
            Text(text = "Login with google")
        }
    }
}
