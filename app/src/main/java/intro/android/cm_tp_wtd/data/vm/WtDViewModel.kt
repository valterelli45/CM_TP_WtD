package intro.android.cm_tp_wtd.data.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import intro.android.cm_tp_wtd.data.db.WtDDatabase
import intro.android.cm_tp_wtd.data.entities.User
import intro.android.cm_tp_wtd.data.repository.WtDRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WtDViewModel(application: Application) : AndroidViewModel(application) {
    val readAllUsers: LiveData<List<User>>
    private val repository: WtDRepository

    init {
        val wtdDao = WtDDatabase.getDatabase(application).wtdDao()
        repository = WtDRepository(wtdDao)
        readAllUsers = repository.readAllUsers
    }

    fun addUser(name: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(name)
        }
    }

    fun updateUser(id: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(id)
        }
    }

    fun deleteUser(id: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(id)
        }
    }
}