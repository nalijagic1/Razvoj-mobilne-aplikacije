package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Grupa
//import ba.etf.rma21.projekat.data.models.GrupeNaKvizu
//import ba.etf.rma21.projekat.data.models.KvizoviNaGrupi

@Dao
interface GrupaDAO {
   /* @Transaction
    @Query("SELECT * FROM Kviz WHERE id = :id")
    suspend fun getGrupeFromKviz(id:Int):List<GrupeNaKvizu>*/

    @Transaction
    @Query("SELECT * FROM Grupa")
    suspend fun getGrupeStudenta():List<Grupa>

    @Transaction
    @Query("SELECT * FROM Grupa where id=:id")
    suspend fun getGrupaById(id:Int):Grupa

    @Transaction
    @Query("UPDATE Grupa SET naziv = :naziv, PredmetId = :predmet WHERE id = :id")
    suspend fun updateGrupa(id:Int,naziv:String?,predmet:Int)

    @Transaction
    @Query("SELECT * FROM Grupa WHERE PredmetId = :id")
    suspend fun getGrupeFromPredmet(id:Int):List<Grupa>

    @Insert
    suspend fun addToNewGroup(grupa: Grupa)

    @Delete
    suspend fun deleteGrupa(grupa:Grupa)

    @Transaction
    @Query("Delete from GRUPA")
    suspend fun deleteAllGrupa()
}