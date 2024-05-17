package intro.android.cm_tp_wtd.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import intro.android.cm_tp_wtd.data.entities.User

@Dao
interface WtDDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(name: User)

    @Update
    suspend fun updateUser(name: User)

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun readAllUsers() : LiveData<List<User>>

    @Delete
    suspend fun deleteUser(name: User)
}