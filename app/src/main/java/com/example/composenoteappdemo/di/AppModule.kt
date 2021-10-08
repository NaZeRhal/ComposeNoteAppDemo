package com.example.composenoteappdemo.di

import android.app.Application
import androidx.room.Room
import com.example.composenoteappdemo.feature_note.data.repository.NoteRepositoryImpl
import com.example.composenoteappdemo.feature_note.data.source.NoteDatabase
import com.example.composenoteappdemo.feature_note.domain.repository.NoteRepository
import com.example.composenoteappdemo.feature_note.domain.usecase.AddNoteUseCase
import com.example.composenoteappdemo.feature_note.domain.usecase.DeleteNoteUseCase
import com.example.composenoteappdemo.feature_note.domain.usecase.GetNotesUseCase
import com.example.composenoteappdemo.feature_note.domain.usecase.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDataBase(app: Application): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository)
        )
    }
}

