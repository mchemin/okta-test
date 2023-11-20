package com.finalcad.okta

import android.app.Activity
import android.content.Context
import android.util.Log
import com.okta.authfoundation.AuthFoundationDefaults
import com.okta.authfoundation.client.OidcClient
import com.okta.authfoundation.client.OidcClientResult
import com.okta.authfoundation.client.OidcConfiguration
import com.okta.authfoundation.client.SharedPreferencesCache
import com.okta.authfoundation.credential.CredentialDataSource.Companion.createCredentialDataSource
import com.okta.authfoundationbootstrap.CredentialBootstrap
import com.okta.webauthenticationui.WebAuthenticationClient.Companion.createWebAuthenticationClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrl

class UserRepository(
    context: Context,
) {

    init {
        AuthFoundationDefaults.cache = SharedPreferencesCache.create(context = context)
        val oidcConfiguration = OidcConfiguration(
            clientId = "0oasn9ulmgaCvOCPo0x6",
            defaultScope = "openid profile offline_access",
        )
        // okta init
        val client = OidcClient.createFromDiscoveryUrl(
            configuration = oidcConfiguration,
            discoveryUrl = "https://preview.finalcad.com/oauth2/ausv056t9AKHmSUvk0x6/.well-known/openid-configuration".toHttpUrl(),
        )
        CredentialBootstrap.initialize(
            credentialDataSource = client.createCredentialDataSource(
                context = context
            )
        )
    }

    private val _userStatus = MutableStateFlow<UserStatus>(UserStatus.NotLogged)
    val userStatus: Flow<UserStatus>
        get() = _userStatus

    fun login(activity: Activity) {
        GlobalScope.launch {
            _userStatus.value = UserStatus.Loading
            val credential = CredentialBootstrap.defaultCredential()
            val webAuthenticationClient =
                CredentialBootstrap.oidcClient.createWebAuthenticationClient()
            val result = webAuthenticationClient.login(
                context = activity,
                redirectUrl = "com.finalcad.finalcad-one.users:/callback",
                extraRequestParameters = mapOf("idp" to "0oauz0nrt59Fr4uDU0x6"),
            )
            when (result) {
                is OidcClientResult.Error -> _userStatus.value = UserStatus.NotLogged
                is OidcClientResult.Success -> {
                    Log.e("okta", "Successfully sign in with okta")
                    credential.storeToken(token = result.result)
                    val accessToken = credential.token?.accessToken
                    val refreshToken = credential.token?.refreshToken
                    val idToken = credential.token?.idToken
                    val userId = idToken ?: "no token"
                    Log.i("okta", "token = ")
                    Log.i("okta", userId)
                    _userStatus.value = UserStatus.Logged(userId = userId)
                }
            }
        }
    }

    fun logout(activity: Activity) {
        GlobalScope.launch {
            _userStatus.value = UserStatus.Loading
            val idToken = CredentialBootstrap.defaultCredential().token!!.idToken!!
            val webAuthenticationClient =
                CredentialBootstrap.oidcClient.createWebAuthenticationClient()
            val result = webAuthenticationClient.logoutOfBrowser(
                context = activity,
                redirectUrl = "com.finalcad.finalcad-one.users:/logoutCallback",
                idToken = idToken,
            )
            when (result) {
                is OidcClientResult.Error -> Log.e("okta", "Failed to log out")
                is OidcClientResult.Success -> _userStatus.value = UserStatus.NotLogged
            }
        }
    }
}
