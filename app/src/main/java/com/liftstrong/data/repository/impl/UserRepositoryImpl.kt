package com.liftstrong.data.repository.impl

import com.liftstrong.data.local.dao.UserDao
import com.liftstrong.data.local.entity.UserEntity
import com.liftstrong.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * Implementation of the UserRepository interface.
 */
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    
    override suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }
    
    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }
    
    override suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }
    
    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }
    
    override suspend fun deleteUserById(id: Int) {
        userDao.deleteUserById(id)
    }
    
    override suspend fun getOrCreateDefaultUser(): UserEntity {
        // Get all users
        val users = userDao.getAllUsers().firstOrNull()
        
        // If there are no users, create a default user
        if (users.isNullOrEmpty()) {
            val defaultUser = UserEntity(
                name = "User",
                weightUnit = "lbs",
                defaultRestTime = 60
            )
            val userId = userDao.insertUser(defaultUser).toInt()
            return defaultUser.copy(id = userId)
        }
        
        // Otherwise, return the first user
        return users.first()
    }
    
    override suspend fun updateUserSettings(weightUnit: String, defaultRestTime: Int) {
        val user = getOrCreateDefaultUser()
        val updatedUser = user.copy(
            weightUnit = weightUnit,
            defaultRestTime = defaultRestTime
        )
        userDao.updateUser(updatedUser)
    }
}
