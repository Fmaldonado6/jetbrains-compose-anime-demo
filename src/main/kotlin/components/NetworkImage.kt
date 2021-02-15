package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.skija.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

open class NetworkImageState {}

class NetworkImageLoadingState : NetworkImageState() {}

class NetworkImageLoadedState(val bitmap: ImageBitmap) : NetworkImageState() {}


@Composable
fun networkImage(url: String, imageModifier: Modifier, contentScale: ContentScale, spinnerSize: Dp = 80.dp) {
    var state = loadNetworkImage(url)

    when (state) {
        is NetworkImageLoadingState -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(spinnerSize)
        ) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(16.dp)
            )
        }
        is NetworkImageLoadedState -> Image(
            bitmap = (state as NetworkImageLoadedState).bitmap,
            modifier = imageModifier,
            contentScale = contentScale
        )
    }
}

@Composable
fun loadNetworkImage(link: String): NetworkImageState {
    var state by remember(link) {
        mutableStateOf<NetworkImageState>(NetworkImageLoadingState())
    }

    if(state is NetworkImageLoadedState)
        return state

    GlobalScope.launch {
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val bufferedImage = ImageIO.read(inputStream)

        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", stream)
        val byteArray = stream.toByteArray()

        val bitmap = Image.makeFromEncoded(byteArray).asImageBitmap()
        state = NetworkImageLoadedState(bitmap)

    }
    return state
}