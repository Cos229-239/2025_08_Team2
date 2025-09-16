package com.example.ravengamingnews.navigation

import com.example.ravengamingnews.R

object AppRoutes {
    // Home section routes
    const val HOME_FEED = "feed"
    const val HOME_BROWSE = "browse"
    const val HOME_ALL = "all"
    const val ARTICLE_DETAILS = "article/{articleId}"

    // Settings section routes
    const val SETTINGS_EDIT_ACCOUNT = "settings/edit_account"
    const val SETTINGS_FILTERS = "settings/filters"
    const val SETTINGS_SAVED = "settings/saved"
    const val SETTINGS_SUPPORT = "settings/support"
    const val SETTINGS_ABOUT = "settings/about"

    fun isSettingsRoute(route: String): Boolean {
        return route.startsWith("settings/")
    }

    fun getTitleResId(route: String): Int {
        return when (route) {
            SETTINGS_EDIT_ACCOUNT -> R.string.account
            SETTINGS_FILTERS -> R.string.filters
            SETTINGS_SAVED -> R.string.saved
            SETTINGS_SUPPORT -> R.string.support
            SETTINGS_ABOUT -> R.string.about
            else -> R.string.app_name // Fallback
        }
    }
}

