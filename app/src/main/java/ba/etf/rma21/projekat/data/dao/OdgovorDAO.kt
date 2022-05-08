package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma21.projekat.data.models.Odgovor

@Dao
interface OdgovorDAO {
    @Transaction
    @Query("Delete from Odgovor")
    suspend fun deleteAllOdgvoor()

    @Transaction
    @Insert
    suspend fun addOdgovor(odgovor:Odgovor)

    @Transaction
    @Query("Select * from Odgovor where KvizTakenId = :idKvizTaken")
    suspend fun getAllOdgovorForKvizTaken(idKvizTaken:Int):List<Odgovor>

    @Transaction
    @Query("Select * from Odgovor")
    suspend fun getAllOdgovore():List<Odgovor>
    @Transaction
    @Query("Select * from Odgovor where KvizId = :idKviz")
    suspend fun getAllOdgovorForKviz(idKviz:Int):List<Odgovor>
}