package ba.etf.rma21.projekat.data.dao

import androidx.room.*
//import ba.etf.rma21.projekat.data.models.GrupeNaKvizu
import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.models.KvizoviNaGrupi

@Dao
interface KvizDAO {
    @Transaction
    @Query("SELECT * FROM Kviz")
    suspend fun getKvizove():List<Kviz>

    @Transaction
    @Query("SELECT * FROM Kviz WHERE id = :id")
    suspend fun getFromId(id:Int):Kviz

    @Transaction
    @Query("SELECT * FROM Kviz WHERE id = :id AND apiId =:api")
    suspend fun getFromIdAndApi(id:Int,api:Int):Kviz
    @Transaction
    @Query("SELECT * FROM Kviz WHERE apiId = :id")
    suspend fun getFromIdaApi(id:Int):Kviz

    @Transaction
    @Query("SELECT * FROM Kviz WHERE GrupaId = :id")
    suspend fun getKvizFromGrupa(id:Int):List<Kviz>

    @Transaction
    @Delete
    suspend fun deleteKviz(kviz:Kviz)

    @Transaction
    @Query("Delete from Kviz")
    suspend fun deleteAllKviz()

    @Transaction
    @Insert
    suspend fun insertNew(kvizovi:Kviz)

    @Transaction
    @Query("Update Kviz set naziv=:naziv,datumPocetka=:start,datumKraj=:end,trajanje=:length,GrupaId=:gId where id=:id")
    suspend fun updateKviz(naziv:String?,start:String?,end:String?,length:Int?,gId:Int,id:Int)

    @Transaction
    @Query("Update Kviz set predan =:predan where id=:id")
    suspend fun updatePredano(predan:Boolean,id:Int)

}