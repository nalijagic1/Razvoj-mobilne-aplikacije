package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class TakeKvizRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.addPokusaj(idKviza)
                val responseBody = response.body()
                if(responseBody!!.message==null){
                    responseBody!!.pitanja = mutableListOf()
                    responseBody.pitanja = PitanjeKvizRepository.getPitanjaApi(idKviza)
                    return@withContext responseBody
                }else return@withContext null

            }
        }

        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(TakeKvizRepository.context)
                val responseBody = db.takenDao().getAllTaken()
                if(responseBody!!.size == 0)return@withContext null
                responseBody.forEach{
                    it.pitanja = mutableListOf()
                    it.pitanja = PitanjeKvizRepository.getPitanja(it.KvizId)
                }
                //System.out.println("Pitanja:"+responseBody)
                return@withContext responseBody
            }
        }

        suspend fun getPocetiKvizoviApi(): List<KvizTaken>? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getZapoceti()
                val responseBody = response.body()
                if(responseBody!!.size == 0)return@withContext null
                responseBody.forEach{
                    it.datumRada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(it.datumRada)).toString()
                    it.pitanja = mutableListOf()
                    it.pitanja = PitanjeKvizRepository.getPitanjaApi(it.KvizId)
                }
                //System.out.println("Pitanja:"+responseBody)
                return@withContext responseBody
            }
        }

        suspend fun getZavrseniKvizovi():List<KvizTaken>?{
            val poceti = getPocetiKvizoviApi();
            //System.out.println("Odg:"+poceti)
            if(poceti ==null)return null
            val zavrseni : MutableList<KvizTaken> = mutableListOf()
            for(i in poceti!!.indices){
                var odgovori = OdgovorRepository.getOdgovoriKviz(poceti[i].KvizId)
                if(poceti[i].pitanja?.size == odgovori?.size){
                    zavrseni.add(poceti[i])
                }
            }
            //System.out.println("Odg:"+poceti)
            return zavrseni
        }
        suspend fun getPocetiById(idKviz:Int): KvizTaken? {
            return withContext(Dispatchers.IO) {
                var response = getPocetiKvizovi()
                if(response==null)return@withContext null
                //System.out.println(response)
                var kvizTaken = response.find { it.KvizId == idKviz }
                return@withContext kvizTaken
            }
        }

        suspend fun getPocetiByIdApi(idKviz:Int): KvizTaken? {
            return withContext(Dispatchers.IO) {
                var response = getPocetiKvizoviApi()
                if(response==null)return@withContext null
                var kvizTaken = response.find { it.KvizId == idKviz }
                return@withContext kvizTaken
            }
        }
        suspend fun zapocniKvizBase(kviz:KvizTaken,apiId:Int){
            var db = AppDatabase.getInstance(TakeKvizRepository.context)
            kviz.KvizId = apiId
            kviz.datumRada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(kviz.datumRada)).toString()
            val responseBody = db.takenDao().addNewTaken(kviz)
        }
    }

}