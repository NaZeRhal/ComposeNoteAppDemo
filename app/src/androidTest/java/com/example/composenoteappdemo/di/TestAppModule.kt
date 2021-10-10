package com.example.composenoteappdemo.di

import android.app.Application
import androidx.room.Room
import com.example.composenoteappdemo.feature_note.data.repository.NoteRepositoryImpl
import com.example.composenoteappdemo.feature_note.data.source.NoteDatabase
import com.example.composenoteappdemo.feature_note.domain.repository.NoteRepository
import com.example.composenoteappdemo.feature_note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    fun provideNoteDataBase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(app, NoteDatabase::class.java)
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
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        )
    }
}

