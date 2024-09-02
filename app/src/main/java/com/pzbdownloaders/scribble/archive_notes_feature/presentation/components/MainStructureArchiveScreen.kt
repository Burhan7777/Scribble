package com.pzbdownloaders.scribble.archive_notes_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.NavigationItems
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.Screens
import kotlinx.coroutines.launch

@Composable
fun MainStructureArchiveScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    notebookNavigation: ArrayList<String>,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {


    var drawerState = androidx.compose.material3.rememberDrawerState(
        initialValue =
        DrawerValue.Closed
    )
    var coroutineScope = rememberCoroutineScope()

    if (selectedItem.value == 0) selectedNote.value = 100000


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colors.primaryVariant,
            ) {
                androidx.compose.material.Text(
                    text = "SCRIBBLE",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyBold,
                    modifier = Modifier.padding(20.dp),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                NavigationItems.navigationItems.forEachIndexed { indexed, item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colors.primary,
                            unselectedContainerColor = MaterialTheme.colors.primaryVariant
                        ),
                        label = {
                            androidx.compose.material.Text(
                                text = item.label,
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = FontFamily.fontFamilyRegular
                            )
                        },
                        selected = selectedItem.value == indexed,
                        onClick = {
                            selectedItem.value = indexed
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            if (selectedItem.value == 0) {
                                navHostController.popBackStack()
                                navHostController.popBackStack()
                                navHostController.navigate(Screens.HomeScreen.route)
                            } else if (selectedItem.value == 1) {
                                navHostController.popBackStack()
                                navHostController.navigate(Screens.ArchiveScreen.route)
                            } else if (selectedItem.value == 2) {
                                navHostController.navigate(Screens.SettingsScreen.route)
                            } else if (selectedItem.value == 3) {
                                navHostController.navigate(Screens.AboutUsScreen.route)
                            }
                        },
                        icon = {
                            if (selectedItem.value == indexed) Icon(
                                imageVector = item.selectedIcon,
                                tint = MaterialTheme.colors.onPrimary,
                                contentDescription = "Notes"
                            ) else Icon(
                                imageVector = item.unSelectedIcon,
                                contentDescription = "Notes",
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                androidx.compose.material.Text(
                    text = "NOTEBOOKS",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyBold,
                    modifier = Modifier.padding(20.dp),
                    fontSize = 20.sp
                )

                notebookNavigation.forEachIndexed { indexed, items ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colors.primary,
                            unselectedContainerColor = MaterialTheme.colors.primaryVariant
                        ),
                        selected = selectedNote.value == indexed,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        label = {
                            androidx.compose.material.Text(
                                text = items,
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = FontFamily.fontFamilyRegular
                            )
                        },
                        onClick = {
                            selectedNote.value = indexed
                            selectedItem.value = 100000
                            navHostController.navigate(
                                Screens.NotebookMainScreen.notebookWithTitle(
                                    items
                                )
                            )
                        },
                        icon = {
                            if (selectedNote.value == indexed) {
                                Icon(
                                    imageVector = Icons.Filled.Folder,
                                    contentDescription = "Folder",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Folder,
                                    contentDescription = "Folder",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
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
                TopSearchBarArchive(navHostController, drawerState, viewModel)
                Notes(viewModel, activity, navHostController)
            }
        }
    }
}