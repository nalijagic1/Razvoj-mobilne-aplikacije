
package ba.etf.rma21.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*

@Database(entities = arrayOf(Kviz::class, Pitanje::class,Odgovor::class, Grupa::class,Predmet::class,
    Account::class,KvizTaken::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDao():AccountDAO
    abstract fun grupaDao():GrupaDAO
    abstract fun kvizDao():KvizDAO
    abstract fun odgovorDao():OdgovorDAO
    abstract fun predmetDao():PredmetDAO
    abstract fun pitanjeDao():PitanjeDAO
    abstract fun takenDao():TakenKvizDAO
    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        fun setInstance(appdb: AppDatabase): Unit {
            INSTANCE = appdb
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA21DB"
            ).build()
    }
}


