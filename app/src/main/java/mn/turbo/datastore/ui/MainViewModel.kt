package mn.turbo.datastore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mn.turbo.datastore.data.SettingPreferenceRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingPreferenceRepository: SettingPreferenceRepository
) : ViewModel() {

    val settings = settingPreferenceRepository.settings.asLiveData()

    fun updateDarkMode(boolean: Boolean) {
        viewModelScope.launch {
            settingPreferenceRepository.updateDarkModeValue(boolean)
        }
    }
}