package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.icu.util.TimeZone
import ba.etf.rma21.projekat.data.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Odg
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class OdgovorRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getOdgovoriKvizApi(idKviza: Int): List<Odgovor>? {
            var kvizoviT = withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getZapoceti()
                val responseBody = response.body()
                return@withContext responseBody
            }
            var kvizT = kvizoviT!!.find { it.KvizId == idKviza }
            //System.out.println(kvizT)
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getOdgovori(kvizT!!.id)
                val responseBody = response.body()
                //System.out.println(responseBody)
                return@withContext responseBody
            }
        }
        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor>? {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(OdgovorRepository.context)
                var odgovori = db.odgovorDao().getAllOdgovorForKviz(idKviza)
                return@withContext odgovori
            }
        }

        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
            return withContext(Dispatchers.IO){
                var brojBodova:Int
                var db = AppDatabase.getInstance(OdgovorRepository.context)
                var trenutni = db.takenDao().getTakenFromId(idKvizTaken)
                if(trenutni==null)return@withContext -1
                brojBodova = trenutni.osvojeniBodovi
                var pitanje =db.pitanjeDao().getPitanjeForKviz(trenutni.KvizId)
                //System.out.println(pitanje)
                var pit= pitanje!!.find { it.id == idPitanje}
                var odgovori = db.odgovorDao().getAllOdgovorForKvizTaken(idKvizTaken)
                if(odgovori.find { it.KvizTakenId ==idKvizTaken && it.PitanjeId==idPitanje }!=null){
                    return@withContext brojBodova
                }
                if(pit!!.tacan == odgovor){
                    brojBodova += 100/pitanje.size
                }
                var id = db.odgovorDao().getAllOdgovore().size + 1
                db.odgovorDao().addOdgovor(Odgovor(id,odgovor,idKvizTaken,idPitanje,trenutni.KvizId,pit.apiId,null))
                db.takenDao().updatePoints(brojBodova,idKvizTaken)
                return@withContext brojBodova
            }
        }
        suspend fun predajOdgovore(idKviz:Int){
            var db = AppDatabase.getInstance(OdgovorRepository.context)
            var odg = db.odgovorDao().getAllOdgovorForKviz(idKviz)
            for(i in odg){
                postaviOdgovorApi(i.KvizTakenId,i.apiPitanjeId,i.odgovoreno)
            }
            db.kvizDao().updatePredano(true,idKviz)

        }
    suspend fun postaviOdgovorApi(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
        var brojBodova:Int
        var uradjeni = withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getZapoceti()
            val responseBody = response.body()
            return@withContext responseBody
        }
        if(uradjeni == null)return -1
        var trazeni = uradjeni!!.find { it.id == idKvizTaken }
        brojBodova = trazeni!!.osvojeniBodovi.toInt()
        var pitanja = withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getPitanjaKviza(trazeni.KvizId)
            val responseBody = response.body()
            return@withContext responseBody
        }
        var pit= pitanja!!.find { it.id == idPitanje}
        if(pit!!.tacan == odgovor){
            brojBodova += 100/pitanja.size
        }
        var odg = withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.addOdg(trazeni.id, Odg(odgovor,pit.id,brojBodova))
            val responseBody = response.body()

            return@withContext responseBody
        }

        if(odg==null || odg!!.message !=null) return -1
        return brojBodova
    }
}
}



