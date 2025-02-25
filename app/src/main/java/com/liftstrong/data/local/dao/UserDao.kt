package com.liftstrong.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.liftstrong.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the User table.
 */
@Dao
interface UserDao {
    
    /**
     * Insert a new user into the database.
     * 
     * @param user The user to insert.
     * @return The ID of the inserted user.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long
    
    /**
     * Update an existing user in the database.
     * 
     * @param user The user to update.
     */
    @Update
    suspend fun updateUser(user: UserEntity)
    
    /**
     * Get a user by ID.
     * 
     * @param id The ID of the user to get.
     * @return The user with the given ID, or null if no such user exists.
     */
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?
    
    /**
     * Get all users in the database.
     * 
     * @return A Flow of all users in the database.
     */
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserEntity>>
    
    /**
     * Delete a user by ID.
     * 
     * @param id The ID of the user to delete.
     */
    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Int)
}
