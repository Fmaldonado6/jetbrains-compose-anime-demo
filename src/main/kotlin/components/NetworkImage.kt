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
import org.jetbrains.skija.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

@Composable
fun networkImage(url: String, imageModifier: Modifier, contentScale: ContentScale, spinnerSize: Dp = 80.dp) {
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    GlobalScope.async {
        bitmap = loadNetworkImage(url)
    }

    if (bitmap != null) Image(
        bitmap = bitmap!!,
        modifier = imageModifier,
        contentScale = contentScale
    ) else Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(spinnerSize)
    ) {
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            modifier = Modifier.size(16.dp)
        )
    }
}

fun loadNetworkImage(link: String): ImageBitmap {
    val url = URL(link)
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()

    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", stream)
    val byteArray = stream.toByteArray()

    return Image.makeFromEncoded(byteArray).asImageBitmap()
}