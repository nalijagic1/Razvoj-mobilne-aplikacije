package ba.etf.rma21.projekat.data.repositories

import android.accounts.Account
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DBRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun updateNow():Boolean{
            return withContext(Dispatchers.IO){
                var db = AppDatabase.getInstance(context)
                var racuni = db.accountDao().getAccount()
                //Log.d(TAG,racuni.lastUpdate)
                var ruta = ApiAdapter.retrofit.checkForUpdate(AccountRepository.acHash,racuni.lastUpdate)
                var body = ruta.body()
                if(body!!.changed==false) return@withContext false
                else {
                    db.accountDao().updateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),AccountRepository.acHash)
                    //System.out.println("P")
                    azurirajPredmete(db)
                    //System.out.println("G")
                    azurirajGrupe(db)
                    //System.out.println("KT")
                    azurirajKvizTaken(db)
                    //System.out.println("K")
                    azurirajKvizove(db)
                   // System.out.println("PIT")
                    azurirajPitanja(db)
                    //Log.d(TAG,db.accountDao().getAccount().lastUpdate)
                    return@withContext true }
            }
        }
        suspend fun azurirajPredmete(db : AppDatabase){
            var predmeti = PredmetIGrupaRepository.getUpisanePredmetApi()
            predmeti.forEach {
                var pr = db.predmetDao().getPredmetById(it!!.id)
                if(pr==null){
                    db.predmetDao().addPredmet(it)
                }else if(!pr.equals(it)){
                    db.predmetDao().updatePredmet(it.id,it.naziv,it.godina)
                }
            }
        }
        suspend fun azurirajGrupe(db: AppDatabase){
            var grupe = PredmetIGrupaRepository.getUpisaneGrupeApi()
            grupe!!.forEach {
                var gr = db.grupaDao().getGrupaById(it!!.id)
                if(gr==null){
                    db.grupaDao().addToNewGroup(it)
                }else if(!gr.equals(it)){
                    db.grupaDao().updateGrupa(it.id,it.naziv,it.PredmetId)
                }
            }

        }

    suspend fun azurirajKvizTaken(db : AppDatabase){
        var kt = TakeKvizRepository.getPocetiKvizoviApi()
        if (kt!=null){
        kt!!.forEach {
            var tk = db.takenDao().getTakenFromId(it.id)
            it.datumRada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(it.datumRada)).toString()
            if(tk==null){
                db.takenDao().addNewTaken(it)
            }
        }
    }}
        suspend fun azurirajKvizove(db : AppDatabase){
            var sveGrupe = db.grupaDao().getGrupeStudenta()
            var j = 1
            sveGrupe!!.forEach {
                var kvizovi = ApiAdapter.retrofit.getFromGrupa(it.id)
                var kvizoviBody = kvizovi.body()
                for(i in kvizoviBody!!.indices){
                    kvizoviBody[i].GrupaId = it.id
                    kvizoviBody[i].datumPocetka = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(kvizoviBody[i].datumPocetka)).toString()
                    if(kvizoviBody[i].datumKraj!=null){
                        kvizoviBody[i].datumKraj = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(kvizoviBody[i].datumKraj)).toString()
                    }
                    var zavrseni = TakeKvizRepository.getZavrseniKvizovi()
                    if(zavrseni!=null){
                        if(zavrseni!!.find{it.KvizId == kvizoviBody[i].id}!=null){
                            System.out.println("tu")
                            kvizoviBody[i].predan = true
                        }
                    }

                    kvizoviBody[i].apiId = kvizoviBody[i].id
                    System.out.println(kvizoviBody[i].id)
                    kvizoviBody[i].id = db.kvizDao().getKvizove().size+1
                    var k = db.kvizDao().getFromIdaApi(kvizoviBody[i].apiId)
                    if(k ==null){
                        db.kvizDao().insertNew(kvizoviBody[i])
                    }else if(!k.equals2(kvizoviBody[i])) {
                        db.kvizDao().updateKviz(
                            kvizoviBody[i].naziv,
                            kvizoviBody[i].datumPocetka,
                            kvizoviBody[i].datumKraj,
                            kvizoviBody[i].trajanje,
                            kvizoviBody[i].GrupaId,
                            kvizoviBody[i].id
                        )
                    }
                    j++;
                }
            }
        }
        suspend fun azurirajPitanja(db : AppDatabase){
            var sviKvizovi = db.kvizDao().getKvizove()
            var r = 1
            sviKvizovi!!.forEach {
                var pitanja = ApiAdapter.retrofit.getPitanjaKviza(it.apiId)
                var pitanjaBody = pitanja.body()
                for(i in pitanjaBody!!.indices){
                    pitanjaBody[i].KvizId = it.id
                    pitanjaBody[i].apiId = pitanjaBody[i].id
                    pitanjaBody[i].id = r
                    var p = db.pitanjeDao().getPitanjeById(pitanjaBody[i].id)
                    pitanjaBody[i].options = ""
                    var j = 1
                    pitanjaBody[i].opcije!!.forEach{
                        if(j==3)pitanjaBody[i].options+=it
                       else pitanjaBody[i].options+=it + ","
                        j++
                    }
                    if(p ==null){
                        db.pitanjeDao().addPitanje(pitanjaBody[i])
                    }else if(!p.equalsBase(pitanjaBody[i])){
                        db.pitanjeDao().updatePitanje(pitanjaBody[i].id,pitanjaBody[i].naziv,pitanjaBody[i].tekstPitanja,pitanjaBody[i].tacan,pitanjaBody[i].options,pitanjaBody[i].KvizId)
                    }
                    r++
                }
            }

        }
}}