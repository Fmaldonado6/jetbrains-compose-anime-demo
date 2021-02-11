package pages.browse

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.appBar
import components.emptyWidget
import components.errorWidget
import components.networkImage
import models.Anime
import pages.browse.states.LoadedState
import pages.browse.states.LoadingState
import services.JikanService
import pages.BasePage
import pages.browse.states.EmptyState
import pages.browse.states.ErrorState
import pages.detail.DetailPage

class BrowsePage : BasePage() {
}

val viewModel = BrowsePageViewModel(JikanService())

@Composable
fun browsePage() {
    var currentString = ""
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        appBar("Search Anime")
        when (viewModel.state) {
            is LoadingState -> CircularProgressIndicator(modifier = Modifier.padding(50.dp))
            is LoadedState -> loaded(
                (viewModel.state as LoadedState).animes
            ) {
                currentString = it
                viewModel.searchAnime(it)
            }
            is ErrorState -> Box(modifier = Modifier.padding(20.dp)) { errorWidget { viewModel.searchAnime(currentString) } }
            is EmptyState -> Box(modifier = Modifier.padding(20.dp)) { emptyWidget() }

        }


    }
}

@Composable
fun loaded(animes: List<Anime>?, buttonClicked: (textField: String) -> Unit) {
    var textFieldValue by remember { mutableStateOf("") }

    Box(
        Modifier.padding(20.dp).fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                    },
                    label = { Text("Search", color = Color.Gray) },
                    shape = RoundedCornerShape(20),
                    backgroundColor = Color(235, 235, 235),
                    inactiveColor = Color.Transparent,
                    textStyle = TextStyle(Color.Black),
                    maxLines = 1
                )
                Button(
                    content = {
                        Icon(Icons.Filled.Search)
                    },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier.padding(5.dp).height(50.dp).width(50.dp),
                    onClick = {
                        print(textFieldValue)
                        buttonClicked(textFieldValue)
                    }
                )
            }
        }
    )
    ScrollableColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (animes != null)
            for (anime in animes) {
                animeCard(anime) {
                    Paginator.currentPage = DetailPage(it)
                }
            }
        else
            Text("Search an anime to begin", style = TextStyle(color = Color.LightGray), fontSize = 16.sp)
    }
}

@Composable
fun animeCard(anime: Anime?, animeClick: (anime: Anime) -> Unit) {
    if (anime == null)
        return

    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth(.75f)
            .clickable {
                animeClick(anime)
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(shape = RoundedCornerShape(7)) {
                networkImage(anime.image_url, Modifier.size(80.dp), contentScale = ContentScale.Crop)
            }
            Text(anime.title, modifier = Modifier.padding(20.dp), style = TextStyle(fontSize = 20.sp))
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

