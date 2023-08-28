package com.pzbdownloaders.scribble.di

import android.content.Context
import androidx.room.Room
import com.pzbdownloaders.scribble.add_note_feature.data.repository.InsertNoteRepository
import com.pzbdownloaders.scribble.add_note_feature.domain.usecase.AddNoteUseCase
import com.pzbdownloaders.scribble.archive_notes_feature.domain.GetArchiveNotesUseCase
import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import com.pzbdownloaders.scribble.edit_note_feature.data.repository.EditNoteRepository
import com.pzbdownloaders.scribble.edit_note_feature.domain.usecase.*
import com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase.AuthenticationSignInUseCase
import com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase.AuthenticationSignUpUseCase
import com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase.SignInUserCase
import com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase.SignUpUserCase
import com.pzbdownloaders.scribble.main_screen.data.repository.NoteRepository
import com.pzbdownloaders.scribble.main_screen.domain.usecase.GetNotesUseCase
import com.pzbdownloaders.scribble.search_feature.domain.usecase.GetSearchResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun createDataBase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "notes").build()
    }


    @Provides
    fun noteRepository(@ApplicationContext context: Context) =
        NoteRepository(createDataBase(context))


    @Provides
    fun insertNoteRepo(@ApplicationContext context: Context) =
        InsertNoteRepository(createDataBase(context))

    @Provides
    fun editNoteRepo(@ApplicationContext context: Context) =
        EditNoteRepository(createDataBase(context))

    @Provides
    fun authSignUpUseCase() = AuthenticationSignUpUseCase()

    @Provides
    fun signUpUserCase() = SignUpUserCase()

    @Provides
    fun authSignUinUserCase() = AuthenticationSignInUseCase()

    @Provides
    fun signInUserCase() = SignInUserCase()

    @Provides
    fun addNoteToCloud() = AddNoteUseCase()

    @Provides
    fun getNoteUserCase() = GetNotesUseCase()

    @Provides
    fun editNoteUseCase() = EditNoteUseCase()

    @Provides
    fun updateNoteUseCase() = UpdateNoteUseCase()

    @Provides
    fun deleteNoteUseCase() = DeleteNoteUseCase()

    @Provides
    fun getArchivedNoteUseCase() = GetArchiveNotesUseCase()

    @Provides
    fun archiveNotesUseCase() = ArchiveNoteUseCase()

    @Provides
    fun unArchiveNotesUseCase() = UnArchiveNoteUseCase()

    @Provides
    fun getSearchResultUseCase() = GetSearchResultUseCase()

}