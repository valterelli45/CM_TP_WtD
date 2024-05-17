package intro.android.cm_tp_wtd.data.repository

import androidx.lifecycle.LiveData
import intro.android.cm_tp_wtd.data.dao.WtDDao
import intro.android.cm_tp_wtd.data.entities.User

class WtDRepository(private val wtdDao: WtDDao) {
    val readAllUsers : LiveData<List<User>> = wtdDao.readAllUsers()

    suspend fun  addUser(name: User){
        wtdDao.addUser(name)
    }

    suspend fun updateUser(name: User){
        wtdDao.updateUser(name)
    }

    suspend fun deleteUser(name: User){
        wtdDao.deleteUser(name)
    }
}