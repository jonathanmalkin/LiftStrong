package com.liftstrong.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.liftstrong.data.local.LiftStrongDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies for the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provides the Room database instance.
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LiftStrongDatabase {
        return Room.databaseBuilder(
            context,
            LiftStrongDatabase::class.java,
            "liftstrong.db"
        ).build()
    }
    
    /**
     * Provides the DataStore instance for user preferences.
     */
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("user_preferences")
        }
    }
}
