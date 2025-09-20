package com.example.ravengamingnews.data.local

import com.example.ravengamingnews.domain.model.UserFilters

interface UserPreferencesRepository {

    suspend fun saveGuestFilters(filters: UserFilters)
    suspend fun getGuestFilters(): UserFilters?
}
