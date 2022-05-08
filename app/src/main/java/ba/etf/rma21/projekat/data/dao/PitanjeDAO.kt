package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
 interface PitanjeDAO {
    @Transaction
    @Query("Delete from Pitanje")
    suspend fun deleteAllPitanje()

    @Transaction
    @Query("Select * from Pitanje where id=:id")
    suspend fun getPitanjeById(id:Int):Pitanje

    @Transaction
    @Query("Select * from Pitanje where KvizId=:id")
    suspend fun getPitanjeForKviz(id:Int):List<Pitanje>

   @Transaction
   @Query("Update Pitanje set naziv=:naziv,tekstPitanja=:tekst,tacan=:right,opcije=:options,KvizId=:kid where id=:id ")
   suspend fun updatePitanje(id:Int,naziv:String?,tekst :String?,right:Int,options:String,kid:Int)

   @Transaction
  @Insert
   suspend fun addPitanje(pitanje: Pitanje)
}