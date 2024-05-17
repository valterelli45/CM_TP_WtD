package intro.android.cm_tp_wtd.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import intro.android.cm_tp_wtd.data.dao.WtDDao
import intro.android.cm_tp_wtd.data.entities.User

@Database(entities = [User :: class], version = 1, exportSchema = false)
abstract class WtDDatabase : RoomDatabase() {
    abstract fun wtdDao(): WtDDao

    companion object {
        @Volatile
        private var INSTANCE: WtDDatabase? = null

        fun getDatabase(context: Context): WtDDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WtDDatabase::class.java,
                    "wtd_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}