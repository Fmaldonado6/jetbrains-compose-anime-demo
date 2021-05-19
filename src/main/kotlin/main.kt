import androidx.compose.desktop.Window
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.sun.org.apache.xpath.internal.operations.Bool
import pages.BasePage
import pages.browse.BrowsePage
import pages.browse.BrowsePageViewModel
import pages.browse.browsePage
import pages.detail.DetailPage
import pages.detail.detailPage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import services.JikanService


object Paginator {
    var currentPage by mutableStateOf<BasePage>(BrowsePage())
}

object AppSettings {
    var darkMode by mutableStateOf<Boolean>(false)
}


fun main() {

    val DarkColors = darkColors(primary = Color(245, 127, 127), secondary = Color.White)
    val LightColors = lightColors(primary = Color(222, 78, 78), secondary = Color.White)

    Window {
        MaterialTheme(colors = if (!AppSettings.darkMode) LightColors else DarkColors) {

            Scaffold(bodyContent = {
                when (Paginator.currentPage) {
                    is BrowsePage ->
                        browsePage()
                    is DetailPage -> detailPage((Paginator.currentPage as DetailPage).anime)
                }
            })
        }
    }
}

