package com.example.pimpmywed.utils

import com.example.pimpmywed.PimpMyWedApp
import org.threeten.bp.OffsetDateTime

object RepositoryUtil {

    private fun getSecondsSinceEpoch() = OffsetDateTime.now().toEpochSecond()

    /**
     *
     * @param cacheKey String - A unique string to represent the cache, ex: GetArtist
     * @param keyDescriptor String - A string to give a secondary description ex: ACDC
     * @param cacheLengthSeconds Long - How long is the cache considered fresh (use TimeUnit.[MINUTES/HOURS/DAYS].toSeconds(x))
     * @return Boolean
     */
    fun isCacheStale(): Boolean {

        var sharedPreferences = PimpMyWedApp.sharedPreferences
        var cacheKey = PimpMyWedApp.cacheKey
        var cacheLengthSeconds = PimpMyWedApp.threshold
        val lastCacheCurrentSeconds = sharedPreferences.getLong(cacheKey.toString(), -1L)
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