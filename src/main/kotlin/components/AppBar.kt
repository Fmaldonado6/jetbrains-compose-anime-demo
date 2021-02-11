package components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pages.browse.BrowsePage

@Composable
fun appBar(title: String, returnButton: Boolean = false) {
    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            if (returnButton)
                IconButton(content = {
                    Icon(Icons.Outlined.ArrowBack, tint = Color.White)
                }, onClick = {
                    Paginator.currentPage = BrowsePage()
                })
        },
        actions = {
            IconButton(content = {
                Icon(Icons.Outlined.Settings, tint = Color.White)
            }, onClick = {
                AppSettings.darkMode = !AppSettings.darkMode
            })
        }
    )
}