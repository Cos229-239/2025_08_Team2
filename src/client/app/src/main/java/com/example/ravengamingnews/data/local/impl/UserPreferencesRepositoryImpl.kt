package com.example.ravengamingnews.data.local.impl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.ravengamingnews.domain.model.UserFilters
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import com.example.ravengamingnews.data.local.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext

private const val LOG_TAG = "UserPreferences"

@Singleton
class UserPreferencesImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val json: Json
) : UserPreferencesRepository {
    companion object {
        private const val PREFS_NAME = "raven_gaming_prefs"
        private const val KEY_GUEST_FILTERS = "guest_user_filters"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun saveGuestFilters(filters: UserFilters) {
        val filtersJson = json.encodeToString(filters)
        prefs.edit { putString(KEY_GUEST_FILTERS, filtersJson) }
    }

    override suspend fun getGuestFilters(): UserFilters? {
        val filtersJson = prefs.getString(KEY_GUEST_FILTERS, null) ?: return null
        return try {
            json.decodeFromString<UserFilters>(filtersJson)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error decoding guest filters: ${e.message}")
            null
        }
    }
}
