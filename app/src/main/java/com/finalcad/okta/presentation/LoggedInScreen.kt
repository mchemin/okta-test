package com.finalcad.okta.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoggedInScreen(
    userId: String,
    logOut: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally), text = userId
        )
        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            onClick = logOut
        ) {
            Text(text = "Logout")
        }
    }
}
