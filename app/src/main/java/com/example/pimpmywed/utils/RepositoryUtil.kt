package com.example.pimpmywed.utils

import com.example.pimpmywed.PimpMyWedApp
import org.threeten.bp.OffsetDateTime

object RepositoryUtil {

    private fun getSecondsSinceEpoch() = OffsetDateTime.now().toEpochSecond()

    fun isCacheStale(): Boolean {
        var sharedPreferences = PimpMyWedApp.sharedPreferences
        var cacheKey = PimpMyWedApp.cacheKey
        var cacheLengthSeconds = PimpMyWedApp.threshold
        val lastCacheCurrentSeconds = sharedPreferences.getLong(cacheKey, -1L)
        if (lastCacheCurrentSeconds == -1L) return true
        return (getSecondsSinceEpoch().minus(lastCacheCurrentSeconds)) > cacheLengthSeconds
    }

    fun resetCache() {
        var sharedPreferences = PimpMyWedApp.sharedPreferences
        var cacheKey = PimpMyWedApp.cacheKey
        sharedPreferences
            .edit()
            .putLong(cacheKey,
                getSecondsSinceEpoch()
            )
            .apply()
    }
}