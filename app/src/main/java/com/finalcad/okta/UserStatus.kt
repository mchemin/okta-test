package com.finalcad.okta

sealed class UserStatus {
    object NotLogged : UserStatus()
    data class Logged(val userId: String) : UserStatus()
    object Loading : UserStatus()
}
