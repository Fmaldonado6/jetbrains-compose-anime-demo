package pages.detail

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sun.org.apache.xpath.internal.operations.Mod
import components.appBar
import components.errorWidget
import components.networkImage
import models.Anime
import models.Character
import pages.BasePage
import pages.detail.states.ErrorState
import pages.detail.states.LoadedState
import pages.detail.states.LoadingState
import services.JikanService

class DetailPage(val anime: Anime) : BasePage() {

}

val viewModel = DetailPageViewModel(JikanService())

@Composable
fun detailPage(anime: Anime) {
    viewModel.getCharacters(anime.mal_id)
    Spacer(Modifier.height(10.dp))
    ScrollableColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        appBar(anime.title, true)
        Row(Modifier.fillMaxWidth(.75f).padding(20.dp)) {
            Card(shape = RoundedCornerShape(7),elevation = 10.dp) {

                networkImage(
                    anime.image_url,
                    imageModifier = Modifier.width(170.dp).height(220.dp),
                    ContentScale.Crop,
                    170.dp
                )
            }
            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                Text(anime.title, style = TextStyle(fontSize = 30.sp))
                Spacer(Modifier.height(20.dp))
                Text(anime.synopsis, style = TextStyle(fontSize = 15.sp))

            }
        }
        characters(anime.mal_id)
    }
}

@Composable
fun characters(id:String) {
    Spacer(Modifier.height(10.dp))
    Text("Characters: ", style = TextStyle(fontSize = 24.sp), modifier = Modifier.fillMaxWidth(.75f))
    Spacer(Modifier.height(20.dp))
    when (viewModel.state) {
        is LoadingState -> Box(
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        is LoadedState ->
            for (character in (viewModel.state as LoadedState).characters!!) {
                characterCard(character)
            }
        is ErrorState ->
            errorWidget {
                viewModel.getCharacters(id)
            }
    }
}

@Composable
fun characterCard(character: Character) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth(.75f)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,) {
            Card(shape = RoundedCornerShape(7)) {
                networkImage(
                    character.image_url,
                    Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                character.name,
                modifier = Modifier.padding(20.dp),
                style = TextStyle(fontSize = 20.sp)
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}