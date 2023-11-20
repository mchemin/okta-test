package com.finalcad.okta

import android.app.Application

class OktaIntegrationApp : Application() {

    val userRepository by lazy { UserRepository(context = this) }
}
