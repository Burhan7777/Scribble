package com.pzbdownloaders.scribble.trash_bin_feature.presentation.components

import com.pzbdownloaders.scribble.main_screen.presentation.components.AlertDialogBoxEnterPasswordToOpenLockedNotes
import com.pzbdownloaders.scribble.main_screen.presentation.components.Notes
import com.pzbdownloaders.scribble.main_screen.presentation.components.TopSearchBar


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
import com.pzbdownloaders.scribble.trash_bin_feature.presentation.components.AlertBoxes.DeleteAllTrashNotes
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureTrashBinScreen(
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
    var showDeleteAllTrashNotesDialogBox = remember { mutableStateOf(false) }


    var listOfTrashNotes = ArrayList<Int>()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllNotes()
    }

    var allNotes = viewModel.listOfNotes
    for (i in allNotes) {
        if (i.deletedNote) {
            listOfTrashNotes.add(i.id)
        }
    }


//
//    if (selectedItem.value == 0) selectedNote.value = 100000
//
//
//    ModalNavigationDrawer(
//        drawerContent = {
//            ModalDrawerSheet(
//                drawerContainerColor = MaterialTheme.colors.primaryVariant,
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    androidx.compose.material.Text(
//                        text = "SCRIBBLE",
//                        color = MaterialTheme.colors.onPrimary,
//                        fontFamily = FontFamily.fontFamilyBold,
//                        modifier = Modifier.padding(20.dp),
//                        fontSize = 20.sp
//                    )
//                    androidx.compose.material.OutlinedButton(
//                        onClick = {
//                            FirebaseAuth.getInstance().signOut()
//                            val sharedPreferences =
//                                activity.getSharedPreferences(
//                                    Constant.SHARED_PREP_NAME,
//                                    Context.MODE_PRIVATE
//                                )
//                            sharedPreferences.edit().apply {
//                                putString(Constant.USER_KEY, "LoggedOut")
//                            }.apply()
//
//                            navHostController.popBackStack()
//                            navHostController.navigate(Screens.LoginScreen.route)
//                        },
//                        border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        androidx.compose.material.Text(
//                            text = "Log out",
//                            color = MaterialTheme.colors.onPrimary,
//                            fontFamily = FontFamily.fontFamilyLight,
//                            fontSize = 10.sp,
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                NavigationItems.navigationItems.forEachIndexed { indexed, item ->
//                    NavigationDrawerItem(
//                        colors = NavigationDrawerItemDefaults.colors(
//                            selectedContainerColor = MaterialTheme.colors.primary,
//                            unselectedContainerColor = MaterialTheme.colors.primaryVariant
//                        ),
//                        label = {
//                            androidx.compose.material.Text(
//                                text = item.label,
//                                color = MaterialTheme.colors.onPrimary,
//                                fontFamily = FontFamily.fontFamilyRegular
//                            )
//                        },
//                        selected = selectedItem.value == indexed,
//                        onClick = {
//                            selectedItem.value = indexed
//                            selectedNote.value = 100000
//
//                            coroutineScope.launch {
//                                drawerState.close()
//                            }
//                            if (selectedItem.value == 0) {
//                                navHostController.popBackStack()
//                                navHostController.popBackStack()
//                                navHostController.navigate(Screens.HomeScreen.route)
//                            } else if (selectedItem.value == 1) {
//                                navHostController.popBackStack()
//                                navHostController.navigate(Screens.ArchiveScreen.route)
//                            } else if (selectedItem.value == 2) {
//                                var result = checkIfUserHasCreatedPassword()
//                                result.observe(activity) {
//                                    if (it == true) {
//                                        showDialogToAccessLockedNotes.value = true
//                                    } else {
//                                        Toast.makeText(
//                                            activity,
//                                            "Please setup password in settings first",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//
//                            } else if (selectedItem.value == 3) {
//                                navHostController.navigate(Screens.TrashBinScreen.route)
//                            } else if (selectedItem.value == 4) {
//                                navHostController.navigate(Screens.SettingsScreen.route)
//                            } else if (selectedItem.value == 5) {
//                                navHostController.navigate(Screens.AboutUsScreen.route)
//                            }
//                        },
//                        icon = {
//                            if (selectedItem.value == indexed) Icon(
//                                imageVector = item.selectedIcon,
//                                tint = MaterialTheme.colors.onPrimary,
//                                contentDescription = "Notes"
//                            ) else Icon(
//                                imageVector = item.unSelectedIcon,
//                                contentDescription = "Notes",
//                                tint = MaterialTheme.colors.onPrimary,
//                            )
//                        },
//                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                }
//                androidx.compose.material.Text(
//                    text = "NOTEBOOKS",
//                    color = MaterialTheme.colors.onPrimary,
//                    fontFamily = FontFamily.fontFamilyBold,
//                    modifier = Modifier.padding(20.dp),
//                    fontSize = 20.sp
//                )
//
//                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                    viewModel.notebooks.forEachIndexed { indexed, items ->
//
//                        if (indexed != 0) {
//                            NavigationDrawerItem(
//                                colors = NavigationDrawerItemDefaults.colors(
//                                    selectedContainerColor = MaterialTheme.colors.primary,
//                                    unselectedContainerColor = MaterialTheme.colors.primaryVariant
//                                ),
//                                selected = selectedNote.value == indexed,
//                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
//                                label = {
//                                    androidx.compose.material.Text(
//                                        text = items,
//                                        color = MaterialTheme.colors.onPrimary,
//                                        fontFamily = FontFamily.fontFamilyRegular
//                                    )
//                                },
//                                onClick = {
//                                    selectedNote.value = indexed
//                                    selectedItem.value = 100000
//                                    navHostController.navigate(
//                                        Screens.NotebookMainScreen.notebookWithTitle(
//                                            items
//                                        )
//                                    )
//                                },
//                                icon = {
//                                    if (selectedNote.value == indexed) {
//                                        Icon(
//                                            imageVector = Icons.Filled.Folder,
//                                            contentDescription = "Folder",
//                                            tint = MaterialTheme.colors.onPrimary
//                                        )
//                                    } else {
//                                        Icon(
//                                            imageVector = Icons.Default.Folder,
//                                            contentDescription = "Folder",
//                                            tint = MaterialTheme.colors.onPrimary
//                                        )
//                                    }
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//        },
//        drawerState = drawerState
//    ) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.primary),
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Trash Bin",
                        fontFamily = FontFamily.fontFamilyRegular,
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back to main screen",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }

                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Restore,
                            contentDescription = "restore all files",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    IconButton(onClick = { showDeleteAllTrashNotesDialogBox.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "delete all files",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        ) {
            //  TopSearchBarTrash(navHostController, drawerState, viewModel)
            // ShowPremiumBar(activity)
            if (showDialogToAccessLockedNotes.value) {
                AlertDialogBoxEnterPasswordToOpenLockedNotes(
                    viewModel = viewModel,
                    activity = activity,
                    navHostController = navHostController,
                ) {
                    showDialogToAccessLockedNotes.value = false
                }
            }
            if (showDeleteAllTrashNotesDialogBox.value) {
                DeleteAllTrashNotes(listOfIds = listOfTrashNotes, viewModel = viewModel) {
                    showDeleteAllTrashNotesDialogBox.value = false
                }
            }
            TrashNotes(viewModel, activity, navHostController)
        }
    }
}

