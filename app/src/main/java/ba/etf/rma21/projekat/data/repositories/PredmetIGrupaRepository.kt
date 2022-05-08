package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PredmetIGrupaRepository {
        companion object{
            private lateinit var context: Context
            fun setContext(_context: Context){
                context=_context
            }
            suspend fun getPredmeti():List<Predmet>? {
                return withContext(Dispatchers.IO) {
                    var response = ApiAdapter.retrofit.getAllPredmet()
                    val responseBody = response.body()
                    return@withContext responseBody
                }
            }
            suspend fun getGrupe():List<Grupa>?{
                return withContext(Dispatchers.IO) {
                    var response = ApiAdapter.retrofit.getGrupe()
                    val responseBody = response.body()
                    return@withContext responseBody
                }
            }
            suspend fun getPredmetById(idPredmeta: Int):Predmet?{
                return withContext(Dispatchers.IO) {
                    var response = ApiAdapter.retrofit.getPredmetFromId(idPredmeta)
                    val responseBody = response.body()
                    return@withContext responseBody
                }
            }
            suspend fun getGrupeZaPredmet(idPredmeta:Int):List<Grupa>?{
                return withContext(Dispatchers.IO) {
                    var response = ApiAdapter.retrofit.getGrupeFromPredmet(idPredmeta)
                    val responseBody = response.body()
                    return@withContext responseBody
                }
            }

            suspend fun upisiUGrupu(idGrupa:Int):Boolean?{
                    var poruka = withContext(Dispatchers.IO) {
                        var response = ApiAdapter.retrofit.addToGroup(idGrupa)
                        val responseBody = response.body()
                        return@withContext responseBody
                    }

                return poruka?.message?.contains("je dodan u grupu") == true
            }

            suspend fun getUpisaneGrupe():List<Grupa>?{
                return withContext(Dispatchers.IO) {
                    var db = AppDatabase.getInstance(PredmetIGrupaRepository.context)
                    val responseBody = db.grupaDao().getGrupeStudenta()
                    return@withContext responseBody
                }
            }
            suspend fun getUpisaneGrupeApi():List<Grupa>?{
                    return withContext(Dispatchers.IO) {
                        var response = ApiAdapter.retrofit.getMyGrupe(AccountRepository.acHash)
                        val responseBody = response.body()
                        return@withContext responseBody
                    }
            }

            suspend fun getUpisanePredmet():List<Predmet?>{
                return withContext(Dispatchers.IO) {
                    var db = AppDatabase.getInstance(PredmetIGrupaRepository.context)
                    var predmeti = db.predmetDao().getPredmet()
                    return@withContext predmeti
                }
            }
            suspend fun getUpisanePredmetApi():List<Predmet?>{
                return withContext(Dispatchers.IO) {
                    var response = ApiAdapter.retrofit.getMyGrupe(AccountRepository.acHash)
                    val responseBody = response.body()
                    val predmeti:MutableList<Predmet?> = mutableListOf()
                    //System.out.println(responseBody)
                    for(i in responseBody!!.indices){
                        var responseP = ApiAdapter.retrofit.getPredmetFromId(responseBody[i].PredmetId)
                        predmeti.add(responseP.body())
                    }
                    return@withContext predmeti
                }
            }
            suspend fun getPredmetFromKviz(idKviz:Int):List<Predmet>?{
                return withContext(Dispatchers.IO) {
                    var responseGrupe = ApiAdapter.retrofit.getGrupeKviza(idKviz)
                    val responseBodyGrupe = responseGrupe.body()
                    //System.out.println(responseBodyGrupe)
                    var predmeti :MutableList<Predmet> = mutableListOf()
                    for(i in responseBodyGrupe!!.indices){
                        val p = getPredmetById(responseBodyGrupe[i].PredmetId)
                        val pretraga = predmeti.find{it.id == p!!.id}
                        if(pretraga==null)predmeti.add(p!!)

                    }
                    return@withContext predmeti
                }

            }


        }
}