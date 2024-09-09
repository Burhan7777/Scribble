package com.pzbdownloaders.scribble.main_screen.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ButtonColors
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.domain.utils.NavigationItems
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity
import com.pzbdownloaders.scribble.common.presentation.MainActivityViewModel
import com.pzbdownloaders.scribble.common.presentation.Screens
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.checkIfUserHasCreatedPassword
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureMainScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    // notebookNavigation: ArrayList<String>,
    selectedItem: MutableState<Int>,
    selectedNote: MutableState<Int>
) {

    var drawerState = androidx.compose.material3.rememberDrawerState(
        initialValue =
        DrawerValue.Closed
    )
    var coroutineScope = rememberCoroutineScope()

    var showDialogToAccessLockedNotes = remember { mutableStateOf(false) }




    if (selectedItem.value == 0) selectedNote.value = 100000


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colors.primaryVariant,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material.Text(
                        text = "SCRIBBLE",
                        color = MaterialTheme.colors.onPrimary,
                        fontFamily = FontFamily.fontFamilyBold,
                        modifier = Modifier.padding(20.dp),
                        fontSize = 20.sp
                    )
                    androidx.compose.material.OutlinedButton(
                        onClick = {
                            FirebaseAuth.getInstance().signOut()
                            val sharedPreferences =
                                activity.getSharedPreferences(
                                    Constant.SHARED_PREP_NAME,
                                    Context.MODE_PRIVATE
                                )
                            sharedPreferences.edit().apply {
                                putString(Constant.USER_KEY, "LoggedOut")
                            }.apply()

                            navHostController.popBackStack()
                            navHostController.navigate(Screens.LoginScreen.route)
                        },
                        border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        androidx.compose.material.Text(
                            text = "Log out",
                            color = MaterialTheme.colors.onPrimary,
                            fontFamily = FontFamily.fontFamilyLight,
                            fontSize = 10.sp,
                        )
                    }
                }
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
                            selectedNote.value = 100000

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
                                var result = checkIfUserHasCreatedPassword()
                                result.observe(activity) {
                                    if (it == true) {
                                        showDialogToAccessLockedNotes.value = true
                                    } else {
                                        Toast.makeText(
                                            activity,
                                            "Please setup password in settings first",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }


                            } else if (selectedItem.value == 3) {
                                navHostController.navigate(Screens.SettingsScreen.route)
                            } else if (selectedItem.value == 4) {
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

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    viewModel.notebooks.forEachIndexed { indexed, items ->

                        if (indexed != 0) {
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
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colors.primary),
            bottomBar = {
                BottomAppBar {
                    BottomAppBar() {
                        IconButton(onClick = { navHostController.navigate(Screens.CheckboxMainScreen.route) }) {
                            Icon(
                                imageVector = Icons.Outlined.CheckBox,
                                contentDescription = "CheckBox",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                        IconButton(onClick = { navHostController.navigate(Screens.BulletPointMainScreen.route) }) {
                            Icon(
                                imageVector = Icons.Filled.FormatListBulleted,
                                contentDescription = "Bullet point list",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    onClick = {
                        navHostController.navigate(Screens.AddNoteScreen.route)
                    },
                    shape = MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(15.dp),
                        topEnd = CornerSize(15.dp),
                        bottomStart = CornerSize(15.dp),
                        bottomEnd = CornerSize(15.dp),
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = "Add Note"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TopSearchBar(navHostController, drawerState, viewModel)
                ShowPremiumBar(activity)
                if (showDialogToAccessLockedNotes.value) {
                    AlertDialogBoxEnterPasswordToOpenLockedNotes(
                        viewModel = viewModel,
                        activity = activity,
                        navHostController = navHostController,
                    ) {
                        showDialogToAccessLockedNotes.value = false
                    }
                }
                Notes(viewModel, activity, navHostController)
            }
        }
    }
}

