package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class AccountRepository {

    companion object {
        //TODO Ovdje trebate dodati hash string vašeg accounta
        var acHash: String = "8e9d3bcd-3be3-4508-9dff-26af90376adc"
        private lateinit var context: Context
        fun setContext(_context: Context) {
            context = _context
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun postaviHash(acHash: String): Boolean {
            this.acHash = acHash
            return withContext(Dispatchers.IO){
                val db = AppDatabase.getInstance(context)
                val accounts = db.accountDao().getAccount()
                if(accounts!=null){
                    if(!accounts.acHash.equals(acHash)){
                        db.accountDao().deleteOld(accounts)
                    }else return@withContext true
                }
                db.grupaDao().deleteAllGrupa()
                db.kvizDao().deleteAllKviz()
                db.grupaDao().deleteAllGrupa()
                db.odgovorDao().deleteAllOdgvoor()
                db.predmetDao().deleteAllPredmet()
                db.pitanjeDao().deleteAllPitanje()
                db.takenDao().deleteAll()
                val vrijeme :String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                db.accountDao().insertNew(Account(1,acHash,vrijeme))
                return@withContext true
            }


        }

        fun getHash(): String {
            return acHash
        }
    }}