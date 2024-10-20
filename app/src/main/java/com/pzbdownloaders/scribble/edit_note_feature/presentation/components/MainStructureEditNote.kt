package com.pzbdownloaders.scribble.edit_note_feature.presentation.components

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.auth
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.pzbdownloaders.scribble.add_note_feature.presentation.components.BottomTextFormattingBar
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.*
import com.pzbdownloaders.scribble.common.presentation.components.AlertDialogBoxTrialEnded
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.checkIfUserHasCreatedPassword
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertBoxShareNote
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxDelete
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxEnterPassword
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxEnterPasswordToUnlock
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxPassword
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.components.LoadingDialogBox
import com.pzbdownloaders.scribble.settings_feature.screen.presentation.components.YouNeedToLoginFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStructureEditNote(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    id: Int,
    activity: MainActivity,
    screen: String
) {
    var context = LocalContext.current

    var dialogOpen = remember {
        mutableStateOf(false)
    }

    var coroutineScope = rememberCoroutineScope()

    var richStateText = mutableStateOf(rememberRichTextState())

    var isBoldActivated = remember { mutableStateOf(false) }
    var isUnderlineActivated = remember { mutableStateOf(false) }
    var isItalicActivated = remember { mutableStateOf(false) }
    var isOrderedListActivated = remember { mutableStateOf(false) }
    var isUnOrderedListActivated = remember { mutableStateOf(false) }
    var showFontSize = remember { mutableStateOf(false) }
    var fontSize = remember { mutableStateOf("20") }

    if (richStateText.value.annotatedString.text == "") fontSize.value = "20"

    var note = mutableStateOf(Note())

    var scope = rememberCoroutineScope()
    viewModel.getNoteById(id)
    scope.launch {
        activity.lifecycleScope.launch {
            note.value = viewModel.getNoteByIdFlow.first { it != null } ?: Note()
        }.join()
    }
    var title by rememberSaveable {
        mutableStateOf("")
    }


    var content = rememberSaveable {
        mutableStateOf("")
    }

    var mutableListOfCheckboxTexts = RememberSaveableSnapshotStateList()

    var converted = rememberSaveable { ArrayList<String>() }
// This is the list of checkbox notes which we saved in checkboxes
    var mutableListOfCheckBoxes =
        rememberSaveable { mutableStateOf<ArrayList<Boolean>>(arrayListOf()) }// This is the llst of checkboxes

    var convertedBulletPoints = rememberSaveable { ArrayList<String>() }

    var notebook by remember {
        mutableStateOf("")
    }

    var isExpanded = remember {
        mutableStateOf(false)
    }

    var selectedNotebook = remember {
        mutableStateOf("")
    }

    var passwordNotSetUpDialogBox = remember {
        mutableStateOf(false)
    }

    var mutableListOfBulletPoints = RememberSaveableSnapshotStateList()

    var enterPasswordToLockDialogBox = remember { mutableStateOf(false) }

    var enterPasswordToUnLockDialogBox = remember { mutableStateOf(false) }

    var showTrialEndedDialogBox = remember { mutableStateOf(false) }

    var count = rememberSaveable { mutableStateOf(0) }

    var countBullet = rememberSaveable { mutableStateOf(0) }

    var hideFormattingTextBarWhenTitleIsInFocus = remember { mutableStateOf(true) }

    var showMovingToArchiveLoadingBox = remember { mutableStateOf(false) }


    var showMovingFromArchiveLoadingBox = remember { mutableStateOf(false) }

    var showDeletingNoteDialogBox = remember { mutableStateOf(false) }

    var showYouNeedToLoginFIrst = rememberSaveable { mutableStateOf(false) }

    var showShareDialogBox = remember { mutableStateOf(false) }

    var pinnedOrNot = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        viewModel.getNoteById(id)
        var noteFromDb = viewModel.getNoteById
        pinnedOrNot.value = noteFromDb.value.notePinned

    }


    LaunchedEffect(key1 = true) {
        // WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    }

    LaunchedEffect(key1 = Unit) {
        for (i in note.value.listOfCheckedNotes) {
            val value = mutableStateOf(i).value

            // Extract the string values from the SnapshotStateList
            val stringList = mutableListOfCheckboxTexts.map { it.value }

            // Check if the string value is already present
            if (!stringList.contains(value)) {
                mutableListOfCheckboxTexts.add(mutableStateOf(i))
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        for (i in note.value.listOfBulletPointNotes) {
            val value = mutableStateOf(i).value

            // Extract the string values from the SnapshotStateList
            val stringList = mutableListOfBulletPoints.map { it.value }

            // Check if the string value is already present
            if (!stringList.contains(value)) {
                mutableListOfBulletPoints.add(mutableStateOf(i))
            }
        }
    }


    LaunchedEffect(Unit) {
        if (note.value != null) {
            mutableListOfCheckBoxes.value = note.value.listOfCheckedBoxes
        }
    }


    LaunchedEffect(key1 = true) {
        if (title == "" && content.value == "") {
            title = note.value.title ?: ""
            content.value = note.value.content ?: "Failed to get the contents.Please try again"
        }
        notebook = note.value.notebook

    }

//    if (note.value != null) {
//        if (note.value.listOfCheckedNotes.isEmpty() && note.value.listOfBulletPointNotes.isEmpty()) {
//            DisposableEffect(Unit) {
//                val job = coroutineScope.launch(Dispatchers.IO) {
//                    // Delay the autosave for 3 seconds, then run it every 10 seconds
//                    delay(3000L)
//                    while (isActive) {
//                        // Get the note by ID and update it
//                        viewModel.getNoteById(note.value.id)
//                        activity.lifecycleScope.launch(Dispatchers.IO) {
//                            val noteFromDb = viewModel.getNoteById.value
//                            var note = noteFromDb.copy(
//                                title = title,
//                                content = richStateText.value.toHtml(),
//                                timeModified = System.currentTimeMillis(),
//                                notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
////                listOfBulletPointNotes = convertedBulletPoints,
////                listOfCheckedNotes = converted,
////                listOfCheckedBoxes = mutableListOfCheckBoxes
//
//                            )
//                        viewModel.updateNote(note)
//                            delay(5000L)
//                        }
//                        // Save every 10 seconds
//                    }
//                }
//                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
//
//                onDispose {
//                    job.cancel()  // Cancel the coroutine when the component is disposed
//                }
//            }
//        }
//    }

    val lifecycleOwner = LocalLifecycleOwner.current

    // Observe the lifecycle to detect when the app goes into the background
    if (content.value.isNotEmpty()) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    // Trigger autosave when app goes to background (onStop)
                    viewModel.getNoteById(note.value.id)
                    activity.lifecycleScope.launch {
                        val noteFromDb = viewModel.getNoteByIdFlow.first() { true }
                        var note = noteFromDb?.copy(
                            title = title,
                            content = richStateText.value.toHtml(),
                            timeModified = System.currentTimeMillis(),
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,

                            )
                        viewModel.updateNote(note!!)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            // Cleanup the observer when the Composable is disposed
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    // Observe the lifecycle to detect when the app goes into the background
    if (mutableListOfCheckboxTexts.size > 0) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    convertMutableStateIntoString(
                        mutableListOfCheckboxTexts,
                        converted
                    )
                    // Trigger autosave when app goes to background (onStop)
                    viewModel.getNoteById(note.value.id)
                    activity.lifecycleScope.launch {
                        val noteFromDb = viewModel.getNoteById.value
                        var note = noteFromDb?.copy(
                            title = title,
                            // content = richStateText.value.toHtml(),
                            timeModified = System.currentTimeMillis(),
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
//                listOfBulletPointNotes = convertedBulletPoints,
                            listOfCheckedNotes = converted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes.value

                        )
                        viewModel.updateNote(note!!)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            // Cleanup the observer when the Composable is disposed
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    if (mutableListOfBulletPoints.size > 0) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    convertMutableStateIntoString(
                        mutableListOfBulletPoints,
                        convertedBulletPoints
                    )
                    // Trigger autosave when app goes to background (onStop)
                    viewModel.getNoteById(note.value.id)
                    activity.lifecycleScope.launch {
                        val noteFromDb = viewModel.getNoteById.value
                        var note = noteFromDb?.copy(
                            title = title,
                            // content = richStateText.value.toHtml(),
                            timeModified = System.currentTimeMillis(),
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                            listOfBulletPointNotes = convertedBulletPoints


                        )
                        viewModel.updateNote(note!!)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            // Cleanup the observer when the Composable is disposed
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    if (mutableListOfCheckboxTexts.size > 0) {
        LaunchedEffect(key1 = count.value) {
            delay(500)

            viewModel.getNoteById(id)
            var noteFromDb = viewModel.getNoteById

            var listOfNotes = noteFromDb.value.listOfCheckedNotes

            for (i in listOfNotes) {
                if (!converted.contains(i)) {
                    converted.add(i)
                }
            }

            convertMutableStateIntoString(
                mutableListOfCheckboxTexts,
                converted
            )

            converted.removeAll { it == "" }
            var note1 = noteFromDb.value.copy(
                title = title,
                timeModified = System.currentTimeMillis(),
                //timeStamp = System.currentTimeMillis(),
                notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                listOfCheckedNotes = converted,
                listOfCheckedBoxes = mutableListOfCheckBoxes.value,
            )
            viewModel.updateNote(note1)
        }
    }

    if (mutableListOfBulletPoints.size > 0) {
        LaunchedEffect(key1 = countBullet.value) {
            delay(500)

            viewModel.getNoteById(id)
            var noteFromDb = viewModel.getNoteById

            convertMutableStateIntoString(
                mutableListOfBulletPoints,
                convertedBulletPoints
            )

            convertedBulletPoints.removeAll { it == "" }
            var note1 = noteFromDb.value.copy(
                title = title,
                timeModified = System.currentTimeMillis(),
                //timeStamp = System.currentTimeMillis(),
                notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                listOfBulletPointNotes = convertedBulletPoints
            )
            viewModel.updateNote(note1)
        }
    }

    var remember = rememberCoroutineScope()
    BackHandler {
        var analytics = com.google.firebase.ktx.Firebase.analytics
        var bundle = Bundle()
        bundle.putString("back_handler_triggered_edit_notes", "back_handler_triggered_edit_notes")
        analytics.logEvent("back_handler_triggered_edit_notes", bundle)
        // keyboardController?.hide()
        remember.launch(Dispatchers.Main) {
            if (note.value.listOfCheckedNotes.size > 0 || note.value.listOfBulletPointNotes.size > 0) {
                count.value++
                countBullet.value++
            } else {
                viewModel.getNoteById(note.value.id)
                val noteFromDb = viewModel.getNoteById.value
                var note = noteFromDb.copy(
                    title = title,
                    content = richStateText.value.toHtml(),
                    timeModified = System.currentTimeMillis(),
                    notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
//                listOfBulletPointNotes = convertedBulletPoints,
//                listOfCheckedNotes = converted,
//                listOfCheckedBoxes = mutableListOfCheckBoxes

                )
                viewModel.updateNote(note)
            }
            navController.navigateUp()
        }
    }


    if (showTrialEndedDialogBox.value) {
        AlertDialogBoxTrialEnded {
            showTrialEndedDialogBox.value = false
        }
    }

//    var debounceJob: Job? = null
//
//    DisposableEffect(Unit) {
//        scope.launch {
//            // Listen for changes in the title or content
//            snapshotFlow { title to richStateText.value.toHtml() }
//                .debounce(2000L)  // Wait for 2 seconds of inactivity before saving
//                .collect { (newTitle, newContent) ->  // Destructure the Pair here
//                    // When the user stops typing, save the note
//                    viewModel.getNoteById(note.value.id)
//                    val note = viewModel.getNoteById.value
//                    val updatedNote = note.copy(
//                        title = newTitle,
//                        content = newContent,
//                        timeModified = System.currentTimeMillis(),
//                        notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value
//                    )
//                    viewModel.updateNote(updatedNote)
//                }
//        }
//        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
//
//        onDispose {
//            debounceJob?.cancel()
//        }
//    }

    var lastContent =
        remember { mutableStateOf(richStateText.value.annotatedString.text) } // Track last saved content
    var isTyping by remember { mutableStateOf(false) }

    LaunchedEffect(isTyping) {
        if (!isTyping) {
            delay(2000) // Wait for 2 seconds after typing stops
            if (richStateText.value.annotatedString.text != lastContent.value) {
                viewModel.getNoteById(note.value.id)
                val noteFromDb = viewModel.getNoteById.value
                var note = noteFromDb.copy(
                    title = title,
                    content = richStateText.value.toHtml(),
                    timeModified = System.currentTimeMillis(),
                    notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
//                listOfBulletPointNotes = convertedBulletPoints,
//                listOfCheckedNotes = converted,
//                listOfCheckedBoxes = mutableListOfCheckBoxes

                )
                viewModel.updateNote(note)
                lastContent.value =
                    richStateText.value.annotatedString.text // Update the last saved content
            }
        }
    }

    LaunchedEffect(richStateText.value.annotatedString.text) {
        while (true) {
            delay(500) // Poll every 500ms to check for changes
            if (richStateText.value.annotatedString.text != lastContent.value) {
                isTyping = true
                coroutineScope.launch {
                    delay(2000)
                    isTyping = false
                }
            }
        }
    }




    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        var analytics = com.google.firebase.ktx.Firebase.analytics
                        var bundle = Bundle()
                        bundle.putString(
                            "back_button_pressed_edit_notes",
                            "back_button_pressed_edit_notes"
                        )
                        analytics.logEvent("back_button_pressed_edit_notes", bundle)
                        println("MUTABLESTATE:$mutableListOfCheckBoxes")
                        convertMutableStateIntoString(mutableListOfCheckboxTexts, converted)
                        convertMutableStateIntoString(
                            mutableListOfBulletPoints,
                            convertedBulletPoints
                        )
                        viewModel.getNoteById(id)
                        var noteFromDb = viewModel.getNoteById
                        var archived = noteFromDb.value.archive
                        var lockedOrNote = noteFromDb.value.locked
                        var timeCreated = noteFromDb.value.timeStamp
                        var pinned = noteFromDb.value.notePinned
                        var note = Note(
                            id,
                            title,
                            richStateText.value.toHtml(),
                            archived,
                            locked = lockedOrNote,
                            listOfCheckedNotes = converted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes.value,
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                            listOfBulletPointNotes = convertedBulletPoints,
                            timeStamp = timeCreated,
                            timeModified = System.currentTimeMillis(),
                            notePinned = pinned
                        )
                        viewModel.updateNote(note)
                        Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT)
                            .show()
                        scope.launch {
                            delay(200)
                            navController.navigateUp()
                        }

                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Undo")
                    }
                },
                title = { Text(text = "") },
                actions = {
                    IconButton(onClick = {
                        var analytics = com.google.firebase.ktx.Firebase.analytics
                        var bundle = Bundle()
                        bundle.putString(
                            "save_button_pressed_edit_notes",
                            "save_button_pressed_edit_notes"
                        )
                        analytics.logEvent("save_button_pressed_edit_notes", bundle)

                        convertMutableStateIntoString(mutableListOfCheckboxTexts, converted)
                        convertMutableStateIntoString(
                            mutableListOfBulletPoints,
                            convertedBulletPoints
                        )
                        viewModel.getNoteById(id)
                        var noteFromDb = viewModel.getNoteById
                        var archived = noteFromDb.value.archive
                        var lockedOrNote = noteFromDb.value.locked
                        var timeCreated = noteFromDb.value.timeStamp
                        var pinned = noteFromDb.value.notePinned
                        var note = Note(
                            id,
                            title,
                            richStateText.value.toHtml(),
                            archived,
                            locked = lockedOrNote,
                            listOfCheckedNotes = converted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes.value,
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                            listOfBulletPointNotes = convertedBulletPoints,
                            timeStamp = timeCreated,
                            timeModified = System.currentTimeMillis(),
                            notePinned = pinned
                        )
                        viewModel.updateNote(note)
                        scope.launch {
                            delay(200)
                            navController.navigateUp()
                        }

                        Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT)
                            .show()


                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save")
                    }
                    IconButton(onClick = {
                        showShareDialogBox.value = true
                    }) {
                        Icon(imageVector = Icons.Filled.Share, contentDescription = "Share")
                    }
                    if (screen == Constant.HOME || screen == Constant.LOCKED_NOTE) {
                        IconButton(onClick = {
                            var analytics = com.google.firebase.ktx.Firebase.analytics
                            var bundle = Bundle()
                            bundle.putString(
                                "pinned_button_pressed_edit_notes",
                                "pinned_button_pressed_edit_notes"
                            )
                            analytics.logEvent("pinned_button_pressed_edit_notes", bundle)
                            pinOrUnpinNote(viewModel, id, scope, navController)

                        }) {
                            Icon(
                                imageVector = if (pinnedOrNot.value) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                contentDescription = "pin note",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                    if (screen == Constant.HOME || screen == Constant.LOCKED_NOTE) {
                        IconButton(onClick = {
                            if (screen == Constant.HOME) {
                                var analytics = com.google.firebase.ktx.Firebase.analytics
                                var bundle = Bundle()
                                bundle.putString(
                                    "lock_button_pressed_edit_notes",
                                    "lock_button_pressed_edit_notes"
                                )
                                analytics.logEvent("lock_button_pressed_edit_notes", bundle)
                                val user = Firebase.auth.currentUser
                                if (user != null) {
                                    val result = checkIfUserHasCreatedPassword()
                                    result.observe(activity) {
                                        if (it == false) {
                                            passwordNotSetUpDialogBox.value = true
                                        } else {
                                            convertMutableStateIntoString(
                                                mutableListOfCheckboxTexts,
                                                converted
                                            )
                                            convertMutableStateIntoString(
                                                mutableListOfBulletPoints,
                                                convertedBulletPoints
                                            )
                                            enterPasswordToLockDialogBox.value = true

                                        }
                                    }
                                } else {
                                    showYouNeedToLoginFIrst.value = true
                                }
                            } else if (screen == Constant.LOCKED_NOTE) {
                                var analytics = com.google.firebase.ktx.Firebase.analytics
                                var bundle = Bundle()
                                bundle.putString(
                                    "unlock_button_pressed_edit_notes",
                                    "unlock_button_pressed_edit_notes"
                                )
                                analytics.logEvent("unlock_button_pressed_edit_notes", bundle)
                                convertMutableStateIntoString(
                                    mutableListOfCheckboxTexts,
                                    converted
                                )
                                convertMutableStateIntoString(
                                    mutableListOfBulletPoints,
                                    convertedBulletPoints
                                )
                                enterPasswordToUnLockDialogBox.value = true
                            }
                        }) {
                            Icon(
                                imageVector = if (screen ==
                                    Constant.HOME
                                ) Icons.Filled.Lock else if (screen == Constant.ARCHIVE) Icons.Filled.Lock else Icons.Filled.LockOpen,
                                contentDescription = "Lock Note"
                            )
                        }
                    }
                    if (screen == Constant.HOME || screen == Constant.ARCHIVE) {
                        IconButton(onClick = {

                            if (screen == Constant.HOME) {
                                var analytics = com.google.firebase.ktx.Firebase.analytics
                                var bundle = Bundle()
                                bundle.putString(
                                    "archive_button_pressed_edit_notes",
                                    "archive_button_pressed_edit_notes"
                                )
                                analytics.logEvent("archive_button_pressed_edit_notes", bundle)

                                showMovingToArchiveLoadingBox.value = true

                                moveToArchive(
                                    id = id,
                                    navController = navController,
                                    viewModel = viewModel,
                                    scope = scope,
                                    activity = activity,
                                    showMovingToArchiveLoadingBox
                                )


                            } else if (screen == Constant.ARCHIVE) {
                                var analytics = com.google.firebase.ktx.Firebase.analytics
                                var bundle = Bundle()
                                bundle.putString(
                                    "unarchive_button_pressed_edit_notes",
                                    "unarchive_button_pressed_edit_notes"
                                )
                                analytics.logEvent("unarchive_button_pressed_edit_notes", bundle)

                                showMovingFromArchiveLoadingBox.value = true

                                unArchiveNote(
                                    id,
                                    viewModel,
                                    activity,
                                    navController,
                                    scope,
                                    showMovingFromArchiveLoadingBox
                                )
                            }

                        }) {
                            Icon(
                                imageVector = if (screen == Constant.HOME) Icons.Filled.Archive else if (screen == Constant.LOCKED_NOTE) Icons.Filled.Archive else Icons.Filled.Unarchive,
                                contentDescription = "Archive"
                            )
                        }
                    }
                    IconButton(onClick = {
                        // viewModel.deleteNoteById(id)
                        var analytics = com.google.firebase.ktx.Firebase.analytics
                        var bundle = Bundle()
                        bundle.putString(
                            "delete_button_pressed_edit_notes",
                            "delete_button_pressed_edit_notes"
                        )
                        analytics.logEvent("delete_button_pressed_edit_notes", bundle)
                        dialogOpen.value = true

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )


        },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(paddingValues)) {
                NoteContent(
                    selectedNotebook,
                    isExpanded,
                    viewModel,
                    title,
                    content,
                    notebook,
                    mutableListOfCheckboxTexts,
                    mutableListOfCheckBoxes,
                    mutableListOfBulletPoints,
                    activity,
                    richStateText,
                    count,
                    converted,
                    countBullet,
                    convertedBulletPoints,
                    hideFormattingTextBarWhenTitleIsInFocus,
                    { title = it },
                    { content.value = it },
                    screen
                )
            }
            if (mutableListOfCheckboxTexts.size == 0 && mutableListOfBulletPoints.size == 0 && !hideFormattingTextBarWhenTitleIsInFocus.value) {
                Box(
                    modifier =
                    Modifier
                        .padding(WindowInsets.ime.asPaddingValues())
                        .padding(15.dp)
                        .background(MaterialTheme.colors.primaryVariant)
                        .fillMaxWidth()
                        .height(if (showFontSize.value) 100.dp else 50.dp)
                        .align(
                            Alignment.BottomCenter
                        )
                ) {

                    BottomTextFormattingBar(
                        showFontSize = showFontSize,
                        fontSize = fontSize,
                        richTextState = richStateText,
                        isBoldActivated = isBoldActivated,
                        isUnderlineActivated = isUnderlineActivated,
                        isItalicActivated = isItalicActivated,
                        isOrderedListActivated = isOrderedListActivated,
                        isUnOrderedListActivated = isUnOrderedListActivated,
                        mutableStateOf(false)
                    )
                }
            }
        }
    }
    if (dialogOpen.value) {
        AlertDialogBoxDelete(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController,
            showDeletingNoteDialogBox
            // note = note
        ) {
            dialogOpen.value = false
        }
    }
    if (passwordNotSetUpDialogBox.value) {
        AlertDialogBoxPassword(
            viewModel = viewModel,
            activity = activity,
            navHostController = navController
        ) {
            passwordNotSetUpDialogBox.value = false
        }
    }
    if (enterPasswordToLockDialogBox.value) {
        AlertDialogBoxEnterPassword(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController,
            title = title,
            convertedMutableList = converted,
            listOfCheckboxes = mutableListOfCheckBoxes.value,
            listOfBulletPoints = convertedBulletPoints,
            content = content.value
        ) {
            enterPasswordToLockDialogBox.value = false
        }
    }
    if (enterPasswordToUnLockDialogBox.value) {
        AlertDialogBoxEnterPasswordToUnlock(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController,
            title = title,
            listOfCheckedNotes = converted,
            listOfCheckBoxes = mutableListOfCheckBoxes.value,
            listOfBulletPoints = convertedBulletPoints,
            content = content.value
        ) {
            enterPasswordToUnLockDialogBox.value = false

        }
    }
    if (showMovingToArchiveLoadingBox.value) {
        LoadingDialogBox(text = mutableStateOf("Moving to Archive"))
    }
    if (showMovingFromArchiveLoadingBox.value) {
        LoadingDialogBox(text = mutableStateOf("Moving from Archive"))
    }
    if (showDeletingNoteDialogBox.value) {
        LoadingDialogBox(text = mutableStateOf("Moving to trash"))
    }
    if (showYouNeedToLoginFIrst.value) {
        YouNeedToLoginFirst(navController) {
            showYouNeedToLoginFIrst.value = false
        }
    }
    if (showShareDialogBox.value) {
        AlertBoxShareNote(
            title,
            richStateText.value.annotatedString.text,
            mutableListOfCheckBoxes,
            mutableListOfCheckboxTexts,
            converted
        ) {
            showShareDialogBox.value = false
        }
    }
}

fun convertMutableStateIntoString(
    mutableList: SnapshotStateList<MutableState<String>>,
    mutableListConverted: ArrayList<String>
) {
    mutableListConverted.clear()
    for (i in mutableList) {
        if (!mutableListConverted.contains(i.value)) {
            mutableListConverted.add(i.value)
        }
    }
}

@Composable
fun RememberSaveableSnapshotStateList(): SnapshotStateList<MutableState<String>> {
    // Create a custom saver for SnapshotStateList<Mutable<String>>
    val listSaver = Saver<SnapshotStateList<MutableState<String>>, List<List<String>>>(
        save = { snapshotStateList ->
            // Convert SnapshotStateList<Mutable<String>> to List<List<String>> for saving
            snapshotStateList.map { state -> listOf(state.value) }
        },
        restore = { savedList ->
            // Convert List<List<String>> back to SnapshotStateList<Mutable<String>> on restore
            val restoredList = savedList.map { mutableStateOf(it.first()) }
            SnapshotStateList<MutableState<String>>().apply {
                addAll(restoredList)
            }
        })
    return rememberSaveable(saver = listSaver) {
        mutableStateListOf<MutableState<String>>() // Initial state
    }
}


fun moveToArchive(
    id: Int,
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    scope: CoroutineScope, activity: MainActivity,
    showMovingDialog: MutableState<Boolean>
) {
    scope.launch {
        viewModel.getNoteById(id)
        var it = viewModel.getNoteById.value
        // println("NOTE:$it")
        var note = it.copy(archive = true)

        viewModel.updateNote(note)

        viewModel.getNoteById(id)
        delay(200)
        var note1 = viewModel.getNoteById.value
        if (!note1.archive) {
            println("NESTED TRIGGERED")
            moveToArchive(
                id = id,
                navController = navController,
                viewModel = viewModel,
                scope = scope,
                activity = activity,
                showMovingDialog = showMovingDialog
            )
        } else {

            println("NOTE1:$note")


            delay(200)
            showMovingDialog.value = false
            navController.navigateUp()
            delay(200)
            Toast.makeText(
                activity,
                "Note has been archived",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}

fun unArchiveNote(
    id: Int,
    viewModel: MainActivityViewModel,
    activity: MainActivity,
    navController: NavHostController,
    scope: CoroutineScope,
    showMoveFromArchiveDialog: MutableState<Boolean>
) {
    scope.launch {
        viewModel.getNoteById(id)
        var it = viewModel.getNoteById.value
        //println("NOTE:$it")
        var note = it.copy(archive = false)

        viewModel.updateNote(note)
        delay(200)
        viewModel.getNoteById(id)
        var note1 = viewModel.getNoteById.value
        if (note1.archive) {
            println("NESTED TRIGGERED")
            unArchiveNote(
                id,
                viewModel,
                activity,
                navController,
                scope,
                showMoveFromArchiveDialog
            )
        } else {
            // println("NOTE1:$note")


            delay(200)
            showMoveFromArchiveDialog.value = false
            navController.navigateUp()
            delay(200)
            Toast.makeText(
                activity,
                "Note has been unarchived",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}

fun pinOrUnpinNote(
    viewModel: MainActivityViewModel,
    id: Int,
    scope: CoroutineScope,
    navController: NavHostController
) {
    scope.launch {
        viewModel.getNoteById(id)
        var note = viewModel.getNoteById.value
        var pinnedStatus = note.notePinned
        var note1 = note.copy(notePinned = !pinnedStatus)
        viewModel.updateNote(note1)
        delay(200)
        viewModel.getNoteById(id)
        var note2 = viewModel.getNoteById.value
        if (note2.notePinned == pinnedStatus) {
            println("PINNED TRIGGERED")
            pinOrUnpinNote(viewModel, id, scope, navController)
        } else {
            delay(200)
            navController.navigateUp()
        }
    }
}



