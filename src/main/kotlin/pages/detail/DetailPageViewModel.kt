package pages.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pages.detail.states.ErrorState
import pages.detail.states.DetailState
import pages.detail.states.LoadedState
import pages.detail.states.LoadingState
import retrofit2.await
import services.JikanService
import java.lang.Exception

class DetailPageViewModel(private val JikanService: JikanService) {

    var state by mutableStateOf<DetailState>(LoadingState())

    fun getCharacters(id: String) {
        state = LoadingState()

        try {
            GlobalScope.async {
                val response = JikanService.getCharacters(id).await()
                state = LoadedState(characters = response.characters)
            }
        } catch (e: Exception) {
            state = ErrorState()

        }
    }

}