package components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun errorWidget(retry: () -> Unit) {
    val icon = Icons.Outlined.Warning.copy(defaultHeight = 50.dp, defaultWidth = 50.dp)
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, tint = Color.LightGray)
        Spacer(Modifier.height(20.dp))
        Button(content = { Text("RETRY") }, onClick = retry,shape = RoundedCornerShape(50))
    }
}