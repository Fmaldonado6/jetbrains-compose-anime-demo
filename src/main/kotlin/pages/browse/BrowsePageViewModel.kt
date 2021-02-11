package pages.browse

import androidx.compose.runtime.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import models.Anime
import pages.browse.states.*
import retrofit2.await
import services.JikanService
import java.lang.Exception

class BrowsePageViewModel(val jikanService: JikanService) {

    var state by mutableStateOf<BrowseState>(LoadedState(null))


    fun searchAnime(anime: String) {
        state = LoadingState()

        GlobalScope.async {
            try {
                val result = jikanService.searchAnime(anime).await()
                if (result.results.isEmpty())
                    state = EmptyState()
                else
                    state = LoadedState(result.results)
                println(result)

            } catch (e: Exception) {
                print(e)
                state = ErrorState()
            }
        }
    }

}