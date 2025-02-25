package com.liftstrong.data.repository

import com.liftstrong.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for user operations.
 */
interface UserRepository {
    
    /**
     * Get a user by ID.
     * 
     * @param id The ID of the user to get.
     * @return The user with the given ID, or null if no such user exists.
     */
    suspend fun getUserById(id: Int): UserEntity?
    
    /**
     * Get all users.
     * 
     * @return A Flow of all users.
     */
    fun getAllUsers(): Flow<List<UserEntity>>
    
    /**
     * Insert a new user.
     * 
     * @param user The user to insert.
     * @return The ID of the inserted user.
     */
    suspend fun insertUser(user: UserEntity): Long
    
    /**
     * Update an existing user.
     * 
     * @param user The user to update.
     */
    suspend fun updateUser(user: UserEntity)
    
    /**
     * Delete a user by ID.
     * 
     * @param id The ID of the user to delete.
     */
    suspend fun deleteUserById(id: Int)
    
    /**
     * Get or create a default user.
     * 
     * @return The default user.
     */
    suspend fun getOrCreateDefaultUser(): UserEntity
    
    /**
     * Update user settings.
     * 
     * @param weightUnit The weight unit to set.
     * @param defaultRestTime The default rest time to set.
     */
    suspend fun updateUserSettings(weightUnit: String, defaultRestTime: Int)
}
