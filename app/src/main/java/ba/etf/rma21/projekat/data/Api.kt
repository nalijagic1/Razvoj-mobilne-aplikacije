package ba.etf.rma21.projekat.data

import android.os.Message
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface Api {
    //Pitanja Api
    @GET("/kviz/{id}/pitanja")
    suspend fun getPitanjaKviza(@Path("id") id:Int
                        
    ): Response<List<Pitanje>>
    //Kviz Api
    @GET("/kviz")
    suspend fun getKvizove(@Query("api_key") apiKey: String = AccountRepository.acHash
    ): Response<List<Kviz>>
    @GET("/kviz/{id}")
    suspend fun getFromId(@Path("id") id:Int
                         
    ): Response<Kviz>
    @GET("/grupa/{id}/kvizovi")
    suspend fun getFromGrupa(@Path("id") id:Int
                         
    ): Response<List<Kviz>>
    //Grupa Api
    @GET("/kviz/{id}/grupa")
    suspend fun getGrupeKviza(@Path("id") id:Int

    ): Response<List<Grupa>>

    @GET("/student/{id}/grupa")
    suspend fun getMyGrupe(@Path("id") id:String
                         
    ): Response<List<Grupa>>

    @GET("/grupa")
    suspend fun getGrupe(
                          
    ): Response<List<Grupa>>

    @GET("/predmet/{id}/grupa")
    suspend fun getGrupeFromPredmet(@Path("id") id:Int
                          
    ): Response<List<Grupa>>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun addToGroup(@Path("gid") gid:Int,
                   @Path("id") id:String = AccountRepository.acHash,
    ): Response<Poruka>
    //Odgovor Api
    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun getOdgovori(@Path("ktid") ktid:Int,
                           @Path("id") id:String = AccountRepository.acHash,
    ): Response<List<Odgovor>>
    @POST("/student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun addOdg(@Path("ktid") ktid:Int,
                       @Body body:Odg,
                       @Path("id") id:String = AccountRepository.acHash,

                       ): Response<Odgovor>
    //Predmeti Api
    @GET("/predmet")
    suspend fun getAllPredmet(
                          
    ): Response<List<Predmet>>

    @GET("/predmet/{id}")
    suspend fun getPredmetFromId(@Path("id") id:Int,
                          
    ): Response<Predmet>
    //Kviz Taken api
    @GET("/student/{id}/kviztaken")
    suspend fun getZapoceti(@Path("id") id:String = AccountRepository.acHash,
    ): Response<List<KvizTaken>>

    @POST("/student/{id}/kviz/{kid}")
    suspend fun addPokusaj(@Path("kid") kid:Int,
                           @Path("id") id:String = AccountRepository.acHash
    ): Response<KvizTaken>

    //Update Api
    @GET(" https://rma21-etf.herokuapp.com/account/{id}/lastUpdate")
    suspend fun  checkForUpdate(@Path("id") id:String,
                                @Query("date") datum: String
    ):Response<Change>
}