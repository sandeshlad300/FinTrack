package com.sandesh.fintrack.core.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("app_prefs")


class FirstLaunchStore(private val context: Context) {
    companion object {
        private val INTRO_SHOWN = booleanPreferencesKey("intro_shown")
    }


    val isOnboardingShown: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[INTRO_SHOWN] ?: false }


    suspend fun setOnboardingShown(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[INTRO_SHOWN] = value
        }
    }
}