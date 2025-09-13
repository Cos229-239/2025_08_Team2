package com.example.ravengamingnews.data.repository.impl

import android.util.Log
import com.example.ravengamingnews.data.AuthRepository
import com.example.ravengamingnews.domain.model.AuthState
import com.example.ravengamingnews.domain.model.UserFilters
import com.example.ravengamingnews.domain.model.UserMetadata
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

private const val logTag = "AuthRepository"

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val json: Json
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

    override suspend fun signUp(email: String, password: String, firstName: String, lastName: String, dateOfBirth: LocalDate): Boolean {
        return try {
            val metadata = UserMetadata(
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = dateOfBirth,
                filters = UserFilters() // default empty filters
            )

            val metadataJson = json.encodeToString(metadata)

            auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = json.parseToJsonElement(metadataJson).jsonObject
            }
            true
        } catch (e: Exception) {
            Log.e(logTag, "SignUp error: ${e.message}")
            false
        }
    }

    override suspend fun updateUserProfile(email: String, password: String, firstName: String, lastName: String, dateOfBirth: LocalDate): Boolean {
        val user = auth.currentUserOrNull()
        return if (user != null) {
            try {
                val currentMetadata = getUserMetadata() ?: UserMetadata(
                    firstName = firstName,
                    lastName = lastName,
                    dateOfBirth = dateOfBirth,
                    filters = UserFilters()
                )

                val updatedMetadata = currentMetadata.copy(
                    firstName = firstName,
                    lastName = lastName,
                    dateOfBirth = dateOfBirth
                )

                val metadataJson = json.encodeToString(updatedMetadata)

                auth.updateUser {
                    this.email = email
                    this.password = password
                    data = json.parseToJsonElement(metadataJson).jsonObject
                }
                true
            } catch (e: Exception) {
                Log.e(logTag, "UpdateUserProfile error: ${e.message}")
                false
            }
        } else {
            Log.e(logTag, "UpdateUserProfile error: No authenticated user")
            false
        }
    }

    override suspend fun getUserMetadata(): UserMetadata? {
        val user = auth.currentUserOrNull() ?: return null
        return try {
            val userData = user.userMetadata

            val firstName = userData?.get("first_name")?.jsonPrimitive?.content ?: ""
            val lastName = userData?.get("last_name")?.jsonPrimitive?.content ?: ""
            val dateOfBirth = userData?.get("date_of_birth")?.jsonPrimitive?.content ?: ""

            val filters = try {
                val filtersJson = userData?.get("filters")
                if (filtersJson != null) {
                    json.decodeFromJsonElement(UserFilters.serializer(), filtersJson)
                } else {
                    UserFilters()
                }
            } catch (e: Exception) {
                Log.e(logTag, "Error parsing filters: ${e.message}")
                UserFilters()
            }

            UserMetadata(
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = if (dateOfBirth.isNotEmpty()) LocalDate.parse(dateOfBirth) else LocalDate(1970, 1, 1),
                filters = filters
            )
        } catch (e: Exception) {
            Log.e(logTag, "Error getting user metadata: ${e.message}")
            null
        }
    }

    override suspend fun updateUserFilters(filters: UserFilters): Boolean {
        return try {
            val currentMetadata = getUserMetadata() ?: return false
            val updatedMetadata = currentMetadata.copy(filters = filters)
            val metadataJson = json.encodeToString(updatedMetadata)
            auth.updateUser {
                data = json.parseToJsonElement(metadataJson).jsonObject
            }
            true
        } catch (e: Exception) {
            Log.e(logTag, "Error updating filters: ${e.message}")
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
