package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.widget.ArrayAdapter
import ba.etf.rma21.projekat.data.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
//import ba.etf.rma21.projekat.data.dajPitajaKviza
//import ba.etf.rma21.projekat.data.dajPitanja
//import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.Predmet
//import ba.etf.rma21.projekat.data.predmeti
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PitanjeKvizRepository {
    companion object {
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getPitanja(idKviza: Int): List<Pitanje>? {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(PitanjeKvizRepository.context)
                val responseBody = db.pitanjeDao().getPitanjeForKviz(idKviza)
                val odgovori = OdgovorRepository.getOdgovoriKviz(idKviza)
                for(i in responseBody!!){
                    var pit = odgovori!!.find{ it.PitanjeId == i.id}
                    if(pit!=null) i.mojOdgovor = pit!!.odgovoreno
                }
                return@withContext responseBody
            }
        }

        suspend fun getPitanjaApi(idKviza: Int): List<Pitanje>? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getPitanjaKviza(idKviza)
                val responseBody = response.body()
                val odgovori = OdgovorRepository.getOdgovoriKvizApi(idKviza)
                for(i in responseBody!!){
                    var pit = odgovori!!.find{ it.PitanjeId == i.id}
                    if(pit!=null) i.mojOdgovor = pit!!.odgovoreno
                }
                return@withContext responseBody
            }
        }
        /*fun dajStatusPitanja(pitanje:Pitanje,kviz: Kviz):Int{
            //var pitanjaKviza = dajPitajaKviza()
            var odg = -1
            for(i in pitanjaKviza.indices){
                if(pitanjaKviza[i].pitanje.equals(pitanje) && pitanjaKviza[i].kviz.equals(kviz)){
                    odg = pitanjaKviza[i].odgovoreno
                    break
                }
            }
            return odg
        }
        fun promijeniStatus(indexOdg:Int,pitanje:Pitanje,kviz: Kviz){
            //var pitanjaKviza = dajPitajaKviza()
            for(i in pitanjaKviza.indices) {
                if (pitanjaKviza[i].pitanje.equals(pitanje) && pitanjaKviza[i].kviz.equals(kviz)) {
                    pitanjaKviza[i].odgovoreno = indexOdg
                    break
                }
            }
        }
        fun dajProcenat(kviz: String,predmet :String):Double{
            var pogodak = 0.0
            var pitanja = getPitanja(kviz,predmet)
            for(i  in pitanjaKviza.indices){
                for(j in pitanja.indices){
                    if(pitanja[j].equals(pitanjaKviza[i].pitanje))
                        if(pitanjaKviza[i].pitanje.tacan == pitanjaKviza[i].odgovoreno){
                            pogodak++
                            break
                        }
                }
            }
            var procenat = (pogodak/ pitanja.size)*100
            procenat = String.format("%.2f", procenat).toDouble()
            return procenat

        }*/


    }
}