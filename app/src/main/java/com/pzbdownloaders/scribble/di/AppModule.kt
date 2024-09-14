package com.pzbdownloaders.scribble.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pzbdownloaders.scribble.add_note_feature.data.repository.InsertNoteRepository
import com.pzbdownloaders.scribble.add_note_feature.domain.usecase.AddNoteUseCase
import com.pzbdownloaders.scribble.add_note_feature.domain.usecase.AddNotebookUseCase
import com.pzbdownloaders.scribble.add_note_feature.domain.usecase.GetNoteBookUseCase
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
import com.pzbdownloaders.scribble.notebook_main_screen.domain.GetNotebookNotesUseCase
import com.pzbdownloaders.scribble.search_main_screen_feature.domain.usecase.GetArchiveSearchResultUseCase
import com.pzbdownloaders.scribble.search_main_screen_feature.domain.usecase.GetSearchResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


//ALTER TABLE notes ADD COLUMN editTime TEXT NOT NULL DEFAULT ' '

    var migration_10_11 = object : Migration(10, 11) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE notes ADD COLUMN notePinned INTEGER NOT NULL DEFAULT (0) ")
        }
    }

    @Provides
    @Singleton
    fun createDataBase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "notes")
            .addMigrations(migration_10_11)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
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

    @Provides
    fun getArchivedSearchResultUseCase() = GetArchiveSearchResultUseCase()

    @Provides
    fun addNoteBookUseCase() = AddNotebookUseCase()

    @Provides
    fun getNoteBookUseCase() = GetNoteBookUseCase()

    @Provides
    fun getNotebookNotesUseCase() = GetNotebookNotesUseCase()


}