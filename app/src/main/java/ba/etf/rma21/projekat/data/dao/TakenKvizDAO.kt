package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.KvizTaken

@Dao
interface TakenKvizDAO {
    @Transaction
    @Query("Select * from KvizTaken")
    suspend fun getAllTaken():List<KvizTaken>

    @Transaction
    @Query("Select * from KvizTaken where id=:id")
    suspend fun getTakenFromId(id:Int):KvizTaken

    @Transaction
    @Query("Select * from KvizTaken where KvizId=:id")
    suspend fun getTakenFromKvizId(id:Int):KvizTaken

    @Transaction
    @Delete
    suspend fun deleteOneTaken(taken:KvizTaken)

    @Transaction
    @Insert
    suspend fun addNewTaken(taken: KvizTaken)

    @Transaction
    @Query("Delete from KvizTaken")
    suspend fun deleteAll()

   /* @Transaction
    @Query("Update KvizTaken set student=:student,osvojeniBodovi=:bodovi,datumRada = :rad where id=:id")
    suspend fun updateTaken(student:String,bodovi:Int,rad:String,idKviz:Int,id:Int)*/

    @Transaction
    @Query("Update KvizTaken set osvojeniBodovi=:bodovi where id=:id")
    suspend fun updatePoints(bodovi:Int,id:Int)
}
