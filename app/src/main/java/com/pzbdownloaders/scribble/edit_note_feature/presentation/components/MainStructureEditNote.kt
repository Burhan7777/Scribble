package com.pzbdownloaders.scribble.edit_note_feature.presentation.components

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
import androidx.navigation.NavHostController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.pzbdownloaders.scribble.add_note_feature.presentation.components.BottomTextFormattingBar
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.*
import com.pzbdownloaders.scribble.common.presentation.components.AlertDialogBoxTrialEnded
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.checkIfUserHasCreatedPassword
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxDelete
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxEnterPassword
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxEnterPasswordToUnlock
import com.pzbdownloaders.scribble.edit_note_feature.presentation.components.alertBoxes.AlertDialogBoxPassword
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
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

    var richStateText = mutableStateOf(rememberRichTextState())

    var isBoldActivated = remember { mutableStateOf(false) }
    var isUnderlineActivated = remember { mutableStateOf(false) }
    var isItalicActivated = remember { mutableStateOf(false) }
    var isOrderedListActivated = remember { mutableStateOf(false) }
    var isUnOrderedListActivated = remember { mutableStateOf(false) }
    var showFontSize = remember { mutableStateOf(false) }
    var fontSize = remember { mutableStateOf("20") }

    if (richStateText.value.annotatedString.text == "") fontSize.value = "20"

//    var note: AddNote? by remember {
//        mutableStateOf(AddNote())
//    }

    //viewModel.getNoteToEdit(id)
    // note = viewModel.getNoteDetailsToEdit.observeAsState().value

    viewModel.getNoteById(id)
    var note = viewModel.getNoteById

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
        rememberSaveable { ArrayList<Boolean>() }// This is the llst of checkboxes

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


//    LaunchedEffect(key1 = Unit) {
//        for (i in note.value.listOfBulletPointNotes) {
//            mutableListOfBulletPoints.add(mutableStateOf(i))
//        }
//    }

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

    if (note.value != null) {
        mutableListOfCheckBoxes = note.value.listOfCheckedBoxes
    }

    LaunchedEffect(key1 = true) {
        if (title == "" && content.value == "") {
            title = note.value.title ?: ""
            content.value = note.value.content ?: "Failed to get the contents.Please try again"
        }
        notebook = note.value.notebook

    }

//    if (mutableListOfCheckboxTexts.size == 0 && mutableListOfBulletPoints.size == 0) {
//        DisposableEffect(Unit) {
//
//            val timer = Timer()
//            // Schedule a task to run every 10 seconds
//            timer.schedule(delay = 3000L, period = 1000L) {
//                println("TRIGGERED")
//                viewModel.getNoteById(id)
//                var noteFromDb = viewModel.getNoteById
//                var note = noteFromDb.value.copy(
//                    title = title,
//                    content = richStateText.value.toHtml(),
//                    timeModified = System.currentTimeMillis(),
//                    notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
////                listOfBulletPointNotes = convertedBulletPoints,
////                listOfCheckedNotes = converted,
////                listOfCheckedBoxes = mutableListOfCheckBoxes
//
//                )
//                viewModel.updateNote(note)
//            }
//
//            // Clean up the timer when the composable leaves the composition
//            onDispose {
//                timer.cancel() // Stop the timer
//            }
//        }
//    }

    var coroutineScope = rememberCoroutineScope()

    if (note.value.listOfCheckedNotes.size == 0 && note.value.listOfBulletPointNotes.size == 0) {
        DisposableEffect(Unit) {
            val job = coroutineScope.launch {
                // Delay the autosave for 3 seconds, then run it every 10 seconds
                delay(3000L)
                while (isActive) {
                    // Get the note by ID and update it
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
//
                    viewModel.updateNote(note)
                    delay(5000L)
                    // Save every 10 seconds
                }
            }

            onDispose {
                job.cancel()  // Cancel the coroutine when the component is disposed
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    // Observe the lifecycle to detect when the app goes into the background
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                // Trigger autosave when app goes to background (onStop)
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
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        // Cleanup the observer when the Composable is disposed
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (mutableListOfCheckboxTexts.size > 0) {
        LaunchedEffect(key1 = count.value) {
            delay(500)

            viewModel.getNoteById(id)
            var noteFromDb = viewModel.getNoteById

//        for (i in listOfCheckedNotes) {
//            println("TEXT1:${i.value}")
//        }
//
//        for (i in mutableListConverted) {
//            println("TEXT2:${i}")
//        }
            var listOfNotes = noteFromDb.value.listOfCheckedNotes

//            for (i in listOfNotes) {
//                if (!converted.contains(i)) {
//                    converted.add(i)
//                }
//            }
//            var listOFCheckBoxes = noteFromDb.value.listOfCheckedBoxes
//            for (i in listOFCheckBoxes) {
//                if (!mutableListOfCheckBoxes.contains(i)) {
//                    mutableListOfCheckBoxes.add(i)
//                }
//            }
            convertMutableStateIntoString(
                mutableListOfCheckboxTexts,
                converted
            )
            //println("TEXT2:$listOFCheckBoxes")
            //  println("TEXT1:$converted")
            // println("TEXT2:${mutableListOfCheckboxTexts.toCollection(ArrayList())}")
            //println(mutableListOfCheckBoxes)
            converted.removeAll { it == "" }
            var note1 = noteFromDb.value.copy(
                title = title,
                timeModified = System.currentTimeMillis(),
                //timeStamp = System.currentTimeMillis(),
                notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                listOfCheckedNotes = converted,
                listOfCheckedBoxes = mutableListOfCheckBoxes,
            )
            viewModel.updateNote(note1)
        }
    }

    if (mutableListOfBulletPoints.size > 0) {
        LaunchedEffect(key1 = countBullet.value) {
            delay(500)

            viewModel.getNoteById(id)
            var noteFromDb = viewModel.getNoteById

//        for (i in listOfCheckedNotes) {
//            println("TEXT1:${i.value}")
//        }
//
//        for (i in mutableListConverted) {
//            println("TEXT2:${i}")
//        }
            var listOfNotes = noteFromDb.value.listOfBulletPointNotes

//            for (i in listOfNotes) {
//                if (!converted.contains(i)) {
//                    converted.add(i)
//                }
//            }
//            var listOFCheckBoxes = noteFromDb.value.listOfCheckedBoxes
//            for (i in listOFCheckBoxes) {
//                if (!mutableListOfCheckBoxes.contains(i)) {
//                    mutableListOfCheckBoxes.add(i)
//                }
//            }
            convertMutableStateIntoString(
                mutableListOfBulletPoints,
                convertedBulletPoints
            )
            //println("TEXT2:$listOFCheckBoxes")
            //  println("TEXT1:$converted")
            // println("TEXT2:${mutableListOfCheckboxTexts.toCollection(ArrayList())}")
            //println(mutableListOfCheckBoxes)
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

            delay(800)
            navController.popBackStack()
        }
    }


    if (showTrialEndedDialogBox.value) {
        AlertDialogBoxTrialEnded {
            showTrialEndedDialogBox.value = false
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
                        var note = Note(
                            id,
                            title,
                            richStateText.value.toHtml(),
                            archived,
                            locked = lockedOrNote,
                            listOfCheckedNotes = converted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes,
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                            listOfBulletPointNotes = convertedBulletPoints,
                            timeStamp = timeCreated,
                            timeModified = System.currentTimeMillis()
                        )
                        viewModel.updateNote(note)
                        Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT)
                            .show()
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Undo")
                    }
                },
                title = { Text(text = "") },
                actions = {
                    IconButton(onClick = {
                        //    var updatedNote = Note(id, title, content, getTimeInMilliSeconds(), 123)
                        //   Log.i("title", title)
                        //  viewModel.updateNote(updatedNote)
//                        val map = HashMap<String, Any>()
//                        map["title"] = title
//                        map["content"] = content
//                        map["timeStamp"] = System.currentTimeMillis()
//                        if (selectedNotebook.value.isNotEmpty()) map["label"] =
//                            selectedNotebook.value
//                      //  viewModel.updateNote(note!!.noteId, map)
//                        viewModel.getResultFromUpdateNote.observe(activity) {
//                            when (it) {
//                                is GetResult.Success -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Updated Successfully",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                    navController.popBackStack()
//                                }
//                                is GetResult.Failure -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Update failed. Try again",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//                            }
//                        }


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
                        var note = Note(
                            id,
                            title,
                            richStateText.value.toHtml(),
                            archived,
                            locked = lockedOrNote,
                            listOfCheckedNotes = converted,
                            listOfCheckedBoxes = mutableListOfCheckBoxes,
                            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
                            listOfBulletPointNotes = convertedBulletPoints,
                            timeStamp = timeCreated,
                            timeModified = System.currentTimeMillis()
                        )
                        viewModel.updateNote(note)
                        navController.popBackStack()
                        Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT)
                            .show()


                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save")
                    }
                    if (screen == Constant.HOME || screen == Constant.LOCKED_NOTE) {
                        IconButton(onClick = {
                            viewModel.getNoteById(id)
                            var noteFromDb = viewModel.getNoteById
                            var pinned = noteFromDb.value.notePinned
                            var note = noteFromDb.value.copy(
                                notePinned = !pinned,
                                timeModified = System.currentTimeMillis()
                            )
                            viewModel.updateNote(note)
                            navController.popBackStack()

                        }) {
                            Icon(
                                imageVector = if (pinnedOrNot.value) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                contentDescription = "pin note",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                    IconButton(onClick = {
                        if (screen == Constant.HOME || screen == Constant.ARCHIVE) {
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


                        } else if (screen == Constant.LOCKED_NOTE) {
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
                    IconButton(onClick = {
                        convertMutableStateIntoString(mutableListOfCheckboxTexts, converted)
                        convertMutableStateIntoString(
                            mutableListOfBulletPoints,
                            convertedBulletPoints
                        )
                        if (screen == Constant.HOME || screen == Constant.LOCKED_NOTE) {
                            viewModel.getNoteById(id)
                            var noteFromDb = viewModel.getNoteById
                            var note = noteFromDb.value.copy(
                                archive = true,
                                timeModified = System.currentTimeMillis(),
                                notebook = Constant.NOT_CATEGORIZED
                            )
                            viewModel.updateNote(note)
                            Toast.makeText(
                                activity,
                                "Note has been archived",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
//                            var hashmap = HashMap<String, Any>()
//                            hashmap["archived"] = true
                            //   viewModel.archiveNotes(id, hashmap)
//                            viewModel.getResultFromArchivedNotes.observe(activity) {
//                                when (it) {
//                                    is GetResult.Success -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note has be archived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        navController.popBackStack()
//                                    }
//
//                                    is GetResult.Failure -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note failed to be archived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                            }
                        } else if (screen == Constant.ARCHIVE) {
                            viewModel.getNoteById(id)
                            var noteFromDb = viewModel.getNoteById
                            var note = noteFromDb.value.copy(
                                archive = false,
                                timeModified = System.currentTimeMillis(),
                                notebook = Constant.NOT_CATEGORIZED
                            )
                            viewModel.updateNote(note)
                            Toast.makeText(
                                activity,
                                "Note has been unarchived",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.popBackStack()
//                            val hashmap = HashMap<String, Any>()
//                            hashmap["archived"] = false
                            // viewModel.unArchiveNotes(id, hashmap)
//                            viewModel.getResultFromUnArchiveNotes.observe(activity) {
//                                when (it) {
//                                    is GetResult.Success -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note has been unarchived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        navController.popBackStack()
//                                    }
//
//                                    is GetResult.Failure -> {
//                                        Toast.makeText(
//                                            context,
//                                            "Note failed to be unarchived",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                            }
                        }

                    }) {
                        Icon(
                            imageVector = if (screen == Constant.HOME) Icons.Filled.Archive else if (screen == Constant.LOCKED_NOTE) Icons.Filled.Archive else Icons.Filled.Unarchive,
                            contentDescription = "Archive"
                        )
                    }
                    IconButton(onClick = {
                        // viewModel.deleteNoteById(id)
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
                        isUnOrderedListActivated = isUnOrderedListActivated
                    )
                }
            }
        }
    }
    if (dialogOpen.value) {
        viewModel.getNoteById(id)
        var noteFromDb = viewModel.getNoteById
        var archived = noteFromDb.value.archive
        var lockedOrNote = noteFromDb.value.locked
        var note = Note(
            id,
            title,
            richStateText.value.toHtml(),
            archived,
            locked = lockedOrNote,
            listOfCheckedNotes = converted,
            listOfCheckedBoxes = mutableListOfCheckBoxes,
            notebook = if (selectedNotebook.value == "") notebook else selectedNotebook.value,
            listOfBulletPointNotes = convertedBulletPoints,
            timeStamp = System.currentTimeMillis()
        )
        AlertDialogBoxDelete(
            viewModel = viewModel,
            id = id,
            activity = activity,
            navHostController = navController,
            note = note
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
            listOfCheckboxes = mutableListOfCheckBoxes,
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
            listOfCheckBoxes = mutableListOfCheckBoxes,
            listOfBulletPoints = convertedBulletPoints,
            content = content.value
        ) {
            enterPasswordToUnLockDialogBox.value = false

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





