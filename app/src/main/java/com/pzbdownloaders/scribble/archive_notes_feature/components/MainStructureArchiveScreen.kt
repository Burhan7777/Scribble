package com.pzbdownloaders.scribble.archive_notes_feature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.Screens
import kotlinx.coroutines.launch


data class NavigationItems(
    var label: String,
    var selectedIcon: ImageVector,
    var unSelectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureArchiveScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity
) {

    var navigationItems = listOf(
        NavigationItems(
            label = "Notes",
            selectedIcon = Icons.Filled.Note,
            unSelectedIcon = Icons.Outlined.Note
        ),
        NavigationItems(
            label = "Archive",
            selectedIcon = Icons.Filled.Archive,
            unSelectedIcon = Icons.Outlined.Archive
        ),
        NavigationItems(
            label = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings
        )
    )

    var drawerState = androidx.compose.material3.rememberDrawerState(
        initialValue =
        DrawerValue.Closed
    )
    var coroutineScope = rememberCoroutineScope()

    var selectedItem by remember {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colors.primaryVariant,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                navigationItems.forEachIndexed { indexed, item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colors.primary,
                            unselectedContainerColor = MaterialTheme.colors.primaryVariant
                        ),
                        label = {
                            androidx.compose.material.Text(
                                text = item.label,
                                color = MaterialTheme.colors.onPrimary
                            )
                        },
                        onClick = {
                            selectedItem = indexed
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            if (selectedItem == 0) {
                                navHostController.popBackStack()
                                navHostController.navigate(Screens.HomeScreen.route)
                            } else if (selectedItem == 1) {
                                navHostController.popBackStack()
                                navHostController.navigate(Screens.ArchiveScreen.route)
                            } else if (selectedItem == 2) {
                                navHostController.navigate(Screens.SettingsScreen.route)
                            }
                        },
                        selected = selectedItem == indexed,
                        icon = {
                            if (selectedItem == indexed) Icon(
                                imageVector = item.selectedIcon,
                                tint = MaterialTheme.colors.onPrimary,
                                contentDescription = "selectedNavImage"
                            ) else Icon(
                                imageVector = item.unSelectedIcon,
                                contentDescription = "selectedNavImage",
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            containerColor = MaterialTheme.colors.primary
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TopSearchBarArchive(navHostController, drawerState)
                Notes(viewModel, activity, navHostController)
            }
        }
    }
}