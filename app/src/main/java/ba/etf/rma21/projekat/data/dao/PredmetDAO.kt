package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDAO {
    @Transaction
    @Query("Delete from Predmet")
    suspend fun deleteAllPredmet()

    @Transaction
    @Query("Select * From Predmet")
    suspend fun getPredmet(): List<Predmet>

    @Transaction
    @Query("Select * From Predmet where id=:id")
    suspend fun getPredmetById(id:Int): Predmet

    @Transaction
    @Query("UPDATE Predmet SET naziv = :naziv, godina = :godina WHERE id = :id")
    suspend fun updatePredmet(id:Int,naziv:String?,godina:Int)

    @Transaction
    @Insert
    suspend fun addPredmet(predmet:Predmet)
}