package com.pzbdownloaders.scribble.archive_notes_feature.presentation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.common.presentation.Constant
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBarArchive(
    navHostController: NavHostController,
    drawerState: DrawerState,
    viewModel: MainActivityViewModel,
) {

    var text by remember { mutableStateOf("") }
    var active by remember {
        mutableStateOf(false)
    }

    var scope = rememberCoroutineScope()
    val context = LocalContext.current
    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            if (text.isNotEmpty()) {
                viewModel.getArchiveSearchResult(text)
                navHostController.navigate(Screens.SearchScreen.searchNoteWIthScreen(Constant.ARCHIVE))
            }
        },
        active = active,
        onActiveChange = { active = !active },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        enabled = true,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colors.primaryVariant,
            dividerColor = MaterialTheme.colors.primary,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colors.onPrimary
            )
        ),
        tonalElevation = SearchBarDefaults.Elevation.plus(10.dp),
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable {
                    scope.launch {
                        drawerState.open()
                    }
                },
                tint = androidx.compose.material.MaterialTheme.colors.onPrimary
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Clear",
                modifier = Modifier.clickable {
                    FirebaseAuth.getInstance().signOut()
                    val sharedPreferences =
                        context.getSharedPreferences(
                            Constant.SHARED_PREP_NAME,
                            Context.MODE_PRIVATE
                        )
                    sharedPreferences.edit().apply {
                        putString(Constant.USER_KEY, "LoggedOut")
                    }.apply()

                    navHostController.popBackStack()
                    navHostController.navigate(Screens.LoginScreen.route)
                },
                tint = androidx.compose.material.MaterialTheme.colors.onPrimary
            )
        },
        placeholder = {
            Text(
                text = "Search notes",
                modifier = Modifier.alpha(.5f),
                color = MaterialTheme.colors.onPrimary
            )
        }
    ) {
    }
}