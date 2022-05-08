package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDAO {
    @Transaction
    @Query("SELECT * FROM Account")
    suspend fun getAccount():Account

    @Insert
    suspend fun insertNew(acc:Account)

    @Delete
    suspend fun deleteOld(acc: Account)

    @Transaction
    @Query("UPDATE Account SET lastUpdate = :newUpdate WHERE acHash = :id")
    suspend fun updateDate(newUpdate:String,id:String)
}