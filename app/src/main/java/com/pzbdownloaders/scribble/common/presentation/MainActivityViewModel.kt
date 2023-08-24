package com.pzbdownloaders.scribble.common.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pzbdownloaders.scribble.add_note_screen.data.repository.InsertNoteRepository
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNote
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNoteUseCase
import com.pzbdownloaders.scribble.common.domain.utils.GetResult
import com.pzbdownloaders.scribble.edit_screen.data.repository.EditNoteRepository
import com.pzbdownloaders.scribble.edit_screen.domain.usecase.DeleteNoteUseCase
import com.pzbdownloaders.scribble.edit_screen.domain.usecase.EditNoteUseCase
import com.pzbdownloaders.scribble.edit_screen.domain.usecase.UpdateNoteUseCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.AuthenticationSignInUseCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.AuthenticationSignUpUseCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.SignInUserCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.SignUpUserCase
import com.pzbdownloaders.scribble.main_screen.data.repository.NoteRepository
import com.pzbdownloaders.scribble.main_screen.domain.model.Note
import com.pzbdownloaders.scribble.main_screen.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val insertNoteRepository: InsertNoteRepository,
    private val noteRepository: NoteRepository,
    private val editNoteRepository: EditNoteRepository,
    private val authenticationSignUpUseCase: AuthenticationSignUpUseCase,
    private val signUpUserCase: SignUpUserCase,
    private val authenticationSignInUseCase: AuthenticationSignInUseCase,
    private val signInUserCase: SignInUserCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    var listOfNotes = mutableListOf<Note>()
        private set

    var getNote = mutableStateOf(Note())
        private set

    var getResultFromSignUp: MutableLiveData<String> = MutableLiveData<String>()
        private set

    var getResultFromSignIn: MutableLiveData<String> = MutableLiveData()
        private set

    var addNoteToCloud = MutableLiveData<GetResult>()
        private set

    var getResultToShowNotes = MutableLiveData<GetResult>()
        private set

    var getListOfNotesToShow: MutableLiveData<SnapshotStateList<AddNote>> =
        MutableLiveData()
        private set

    var getResultFromEditNote = MutableLiveData<GetResult>()
        private set

    var getNoteDetailsToEdit = MutableLiveData<AddNote>()
        private set

    var getResultFromUpdateNote = MutableLiveData<GetResult>()
        private set

    var pair: Pair<MutableLiveData<GetResult>, SnapshotStateList<AddNote>?> =
        Pair(getResultToShowNotes, getListOfNotesToShow.value)

    var getResultFromDeleteNote = MutableLiveData<GetResult>()
        private set

    /*  var getListOfNotesToShow = mutableListOf<AddNote>()
          private set
  */
    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            insertNoteRepository.insertNote(note)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            listOfNotes = noteRepository.getAllNotes().toMutableList()
        }
    }


    fun authenticationSignUp(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String,
        isChecked: Boolean
    ): String {
        return authenticationSignUpUseCase.checkAuth(
            name,
            email,
            password,
            repeatedPassword,
            isChecked
        )
    }

    fun authenticationSignIn(
        email: String,
        password: String
    ): String {
        return authenticationSignInUseCase.authenticateSignIn(email, password)
    }

    fun signInUser(email: String, password: String) {
        getResultFromSignIn = signInUserCase.signInUser(email, password)
    }

    fun signUpUser(email: String, password: String) {
        getResultFromSignUp = signUpUserCase.signUpUser(email, password)
    }

    fun addNoteToCloud(addNote: AddNote) {
        addNoteToCloud = addNoteUseCase.addNote(addNote)
    }

    fun getNotesToShow() {
        viewModelScope.launch {
            pair = getNotesUseCase.getNotes()
            getResultToShowNotes = pair.first
            getListOfNotesToShow.value = pair.second
            Log.i("model", getListOfNotesToShow.value.toString())
        }
    }

    fun getNoteToEdit(noteId: String) {
        viewModelScope.launch {
            var pair = editNoteUseCase.getNoteToEdit(noteId)
            getResultFromEditNote.value = pair.first
            getNoteDetailsToEdit.value = pair.second
        }
    }

    fun updateNote(noteId: String, map: HashMap<String, Any>) {
        getResultFromUpdateNote = updateNoteUseCase.updateNote(noteId, map)
        Log.i("result", getResultFromUpdateNote.value.toString())
    }

    fun deleteNote(noteId: String){
        getResultFromDeleteNote = deleteNoteUseCase.deleteNote(noteId)
    }
}