package com.example.dummyjsonapp.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private const val DATA_RETRIEVED_KEY : String = "data_retrieved"
    }

    private object PreferencesKeys {
        val DATA_RETRIEVED = booleanPreferencesKey(name = DATA_RETRIEVED_KEY)
    }

    suspend fun isDataRetrieved() : Boolean {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.DATA_RETRIEVED] ?: false
        }.first()
    }

    suspend fun setDataRetrieved(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DATA_RETRIEVED] = value
        }
    }



}