package com.pzbdownloaders.scribble.di

import android.content.Context
import androidx.room.Room
import com.pzbdownloaders.scribble.add_note_screen.data.repository.InsertNoteRepository
import com.pzbdownloaders.scribble.add_note_screen.domain.AddNoteUseCase
import com.pzbdownloaders.scribble.common.data.data_source.NoteDatabase
import com.pzbdownloaders.scribble.edit_screen.data.repository.EditNoteRepository
import com.pzbdownloaders.scribble.edit_screen.domain.usecase.DeleteNoteUseCase
import com.pzbdownloaders.scribble.edit_screen.domain.usecase.EditNoteUseCase
import com.pzbdownloaders.scribble.edit_screen.domain.usecase.UpdateNoteUseCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.AuthenticationSignInUseCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.AuthenticationSignUpUseCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.SignInUserCase
import com.pzbdownloaders.scribble.login_and_signup.domain.usecase.SignUpUserCase
import com.pzbdownloaders.scribble.main_screen.data.repository.NoteRepository
import com.pzbdownloaders.scribble.main_screen.domain.usecase.GetNotesUseCase
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

}