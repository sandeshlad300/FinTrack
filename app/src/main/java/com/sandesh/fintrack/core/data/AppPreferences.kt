package com.sandesh.fintrack.core.data


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ✅ CREATED ONCE — APPLICATION LEVEL
private val Context.dataStore by preferencesDataStore(
    name = "app_prefs"
)

class AppPreferences private constructor(
    private val context: Context
) {

    companion object {
        @Volatile
        private var INSTANCE: AppPreferences? = null

        fun getInstance(context: Context): AppPreferences =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppPreferences(
                    context.applicationContext
                ).also { INSTANCE = it }
            }

        private val INTRO_SHOWN = booleanPreferencesKey("intro_shown")
        private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val TOTAL_AMOUNT = doublePreferencesKey("total_amount")
    }

    /* ---------- FLOWS ---------- */

    val isIntroShown: Flow<Boolean> =
        context.dataStore.data.map { it[INTRO_SHOWN] ?: false }

    val userName: Flow<String> =
        context.dataStore.data.map { it[USER_NAME] ?: "User" }

    val totalAmount: Flow<Double> =
        context.dataStore.data.map { it[TOTAL_AMOUNT] ?: 0.0 }

    /* ---------- WRITE ---------- */

    suspend fun setIntroShown(value: Boolean) {
        context.dataStore.edit { it[INTRO_SHOWN] = value }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { it[USER_NAME] = name }
    }

    suspend fun initFirstLaunch() {
        context.dataStore.edit { prefs ->
            if (prefs[FIRST_LAUNCH] != false) {
                prefs[FIRST_LAUNCH] = false
                prefs[TOTAL_AMOUNT] = 0.0
            }
        }
    }
}
