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
import com.pzbdownloaders.scribble.notebook_main_screen.data.NotebookRepository
import com.pzbdownloaders.scribble.notebook_main_screen.domain.GetNotebookNotesUseCase
import com.pzbdownloaders.scribble.search_main_screen_feature.domain.usecase.GetArchiveSearchResultUseCase
import com.pzbdownloaders.scribble.search_main_screen_feature.domain.usecase.GetSearchResultUseCase
import com.pzbdownloaders.scribble.settings_feature.screen.data.SettingsRepository
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

    var migration_14_15 = object : Migration(14, 15) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE dummyTable ADD COLUMN timeModified INTEGER NOT NULL DEFAULT (0) ")
        }
    }
    var migration_14_17 = object : Migration(14, 16) {
        override fun migrate(db: SupportSQLiteDatabase) {
            migration_14_15.migrate(db)
            migration_15_16.migrate(db)
            migration_16_17.migrate(db)
        }
    }
    var migration_15_16 = object : Migration(15, 16) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE dummyTable ADD COLUMN timeStamp INTEGER NOT NULL DEFAULT (0) ")
        }
    }
    var migration_16_17 = object : Migration(16, 17) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE dummyTable ADD COLUMN something INTEGER NOT NULL DEFAULT (0) ")
        }
    }
    var migration_15_17 = object : Migration(15, 17) {
        override fun migrate(db: SupportSQLiteDatabase) {
            migration_15_16.migrate(db)
            migration_16_17.migrate(db)
        }
    }
    var migration_17_18 = object : Migration(17, 18) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE dummyTable ADD COLUMN something1 INTEGER NOT NULL DEFAULT (0) ")
        }
    }
    var migration_18_19 = object : Migration(18, 19) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE dummyTable ADD COLUMN something2 INTEGER NOT NULL DEFAULT (0) ")
        }
    }


    @Provides
    @Singleton
    fun createDataBase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "notes")
            .addMigrations(
                migration_14_15,
                migration_15_16,
                migration_16_17,
                migration_17_18,
                migration_18_19
            )
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            //.setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
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

    @Provides
    fun notebookRepository(@ApplicationContext context: Context) =
        NotebookRepository(createDataBase(context))

    @Provides
    fun settingsRepository(@ApplicationContext context: Context) =
        SettingsRepository(createDataBase(context))

}