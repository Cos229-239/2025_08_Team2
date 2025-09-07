package com.example.ravengamingnews.data.repository.impl

import android.util.Log
import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.model.AuthState
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

private const val logTag = "AuthRepository"

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthRepository {

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Initializing)
    override val authState: StateFlow<AuthState> = _authState

    private val _isGuest: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val continuedAsGuest: StateFlow<Boolean> = _isGuest

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            auth.sessionStatus.collect { status ->
                logSessionStatus(status)
            }
        }
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e(logTag, "SignIn error: ${e.message}")
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e(logTag, "SignUp error: ${e.message}")
            false
        }
    }

    override suspend fun continueAsGuest(): Boolean {
        return try {
            auth.signInAnonymously()
            true
        } catch (e: Exception) {
            Log.e(logTag, "ContinueAsGuest error: ${e.message}")
            false
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    private fun logSessionStatus(sessionStatus: SessionStatus) {
        when (sessionStatus) {
            is SessionStatus.Authenticated -> {
                Log.d(
                    logTag, """
                           Session source:${sessionStatus.source}
                           SessionStatus: Authenticated
                           Session expiry:${sessionStatus.session.expiresAt.toLocalDateTime(TimeZone.UTC)}
                    """
                )
                _isGuest.value = sessionStatus.source == SessionSource.AnonymousSignIn ||
                        sessionStatus.session.user?.email.isNullOrEmpty()
                _authState.value = AuthState.Authenticated
            }

            SessionStatus.Initializing -> {
                Log.d(logTag, "SessionStatus: Initializing")
                _authState.value = AuthState.Initializing
                _isGuest.value = false
            }


            is SessionStatus.RefreshFailure -> {
                Log.d(logTag, "SessionStatus: RefreshFailure")
                _isGuest.value = false
            }

            is SessionStatus.NotAuthenticated -> {
                Log.d(
                    logTag,
                    """
                           SessionStatus: NotAuthenticated
                           IsSignOut: ${sessionStatus.isSignOut}
                    """.trimIndent()
                )
                _isGuest.value = false
                _authState.value = AuthState.Unauthenticated
            }
        }
    }
}
