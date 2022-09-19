package mn.turbo.datastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import mn.turbo.datastore.common.Constants
import java.io.IOException
import javax.inject.Inject

class SettingPreferenceRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferenceKeys {
        val DARK_MODE = booleanPreferencesKey(Constants.DARK_MODE)
    }

    suspend fun getInitialPreference() =
        toSettings(dataStore.data.first().toPreferences())

    val settings: Flow<Settings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            toSettings(preferences)
        }

    suspend fun updateDarkModeValue(boolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.DARK_MODE] = boolean
        }
    }

    private fun toSettings(preferences: Preferences): Settings {
        val darkMode = preferences[PreferenceKeys.DARK_MODE] ?: false

        return Settings(darkMode)
    }
}

data class Settings(
    val darkMode: Boolean
)