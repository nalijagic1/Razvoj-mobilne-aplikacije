package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.view.FragmentKvizovi
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class KvizRepository {

    companion object {
        // TODO: Implementirati

        var moji:MutableList<Kviz>?
        private lateinit var context: Context
        init{
            // TODO: Implementirati
            moji = mutableListOf()
        }


        fun setContext(_context: Context){
            context=_context
        }
        suspend fun getAll(): List<Kviz>? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getKvizove()
                val kvizovi = response.body()
                val zapoceti = TakeKvizRepository.getZavrseniKvizovi()
                for(i in kvizovi!!.indices){
                    kvizovi[i].predmeti=""
                    var predmeti = PredmetIGrupaRepository.getPredmetFromKviz(kvizovi[i].id!!)
                    for(j in predmeti!!.indices){
                        kvizovi[i].predmeti+=predmeti[j].naziv
                        if(j!=predmeti.size-1)kvizovi[i].predmeti+=", "
                    }
                    kvizovi[i].datumPocetka=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(kvizovi[i].datumPocetka)).toString()
                    if(kvizovi[i].datumKraj!=null) kvizovi[i].datumKraj = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd").parse(kvizovi[i].datumKraj)).toString()
                    if(zapoceti == null)continue
                    var find = zapoceti!!.find { it.KvizId == kvizovi[i].id }
                    if(find != null){
                        kvizovi[i].rad = find.datumRada
                        kvizovi[i].bodovi = find.osvojeniBodovi.toInt()
                    }
                }
                return@withContext kvizovi
            }
        }

        suspend fun getById(id:Int):Kviz?{
         return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getFromId(id)
                val kviz = response.body()
             //System.out.println(kviz)
             if(kviz?.message ==null){
                    kviz?.predmeti=""
                    var predmeti = PredmetIGrupaRepository.getPredmetFromKviz(kviz!!.id)
                    //System.out.println(predmeti)
                    for(j in predmeti!!.indices){
                        kviz?.predmeti+=predmeti[j].naziv
                        if(j!=predmeti.size-1)kviz?.predmeti+=", "
                    }
                    val zapoceti = TakeKvizRepository.getZavrseniKvizovi()
                    if(zapoceti !=null){
                    var find = zapoceti!!.find { it.KvizId == kviz?.id }
                    if(find != null){
                        kviz?.rad = find.datumRada
                        kviz?.bodovi = find.osvojeniBodovi.toInt()
                    }}
                    return@withContext kviz
                }else return@withContext  null

            }
        }
        suspend fun getUpisani():List<Kviz>?{
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(KvizRepository.context)
                var my = db.kvizDao().getKvizove()
                //System.out.println(my)
                var m = mutableListOf<Kviz>()
                for(i in my.indices){
                    my[i].predmeti=""
                    var predmeti = PredmetIGrupaRepository.getPredmetFromKviz(my[i].apiId!!)
                    //System.out.println(my[i])
                    for(j in predmeti!!.indices){
                        my[i].predmeti+=predmeti[j].naziv
                        if(j!=predmeti.size-1)my[i].predmeti+=", "
                    }
                    //System.out.println(my[i].predmeti)
                        if(my[i].predan){
                            var zapoceti = db.takenDao().getAllTaken()
                            var find = zapoceti!!.find { it.KvizId == my[i].id }
                            if(find != null){
                                //System.out.println(find.datumRada)
                                my[i].rad = find.datumRada
                                my[i].bodovi = find.osvojeniBodovi
                            }
                        }
                        var pr = m!!.find{it.eq(my[i])}

                        if(pr == null) m!!.add(my[i])

                    }
                return@withContext m}
                }
        suspend fun getUpisaniApi():List<Kviz>?{
            return withContext(Dispatchers.IO) {
                moji = mutableListOf()
                var mojeGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
                for(i in mojeGrupe?.indices!!){
                    var response = ApiAdapter.retrofit.getFromGrupa(mojeGrupe[i].id)
                    var body =response.body()
                    //System.out.println(body)
                    for(i in body!!.indices){
                        body[i].predmeti=""
                        var predmeti = PredmetIGrupaRepository.getPredmetFromKviz(body[i].id!!)
                        for(j in predmeti!!.indices){
                            body[i].predmeti+=predmeti[j].naziv
                            if(j!=predmeti.size-1)body[i].predmeti+=", "
                        }
                        var zapoceti = TakeKvizRepository.getZavrseniKvizovi()
                        if(zapoceti !=null){
                            var find = zapoceti!!.find { it.KvizId == body[i].id }
                            if(find != null){
                                body[i].rad = find.datumRada
                                body[i].bodovi = find.osvojeniBodovi.toInt()
                            }
                        }
                        var pr = moji!!.find{it.equals(body[i])}
                        if(pr == null) moji!!.add(body[i])}

                }
                return@withContext moji
            }

        }
        suspend fun getMyKvizes(): List<Kviz> {
            return getUpisani()!!
        }

        suspend  fun getAllKvizes(): List<Kviz> {
            return getAll()!!
        }

        suspend fun getDone(): List<Kviz> {
            val z :MutableList<Kviz> = mutableListOf<Kviz>()
            val m = getUpisani()
            for(i in m!!.indices){
                    if(m!![i].rad != null) z.add(m!![i])
                }
            return z
        }

        fun getDoneApi(): List<Kviz> {
            val z :MutableList<Kviz> = mutableListOf<Kviz>()
            for(i in moji!!.indices){
                if(moji!![i].rad != null) z.add(moji!![i])
            }
            return z
        }

        suspend fun getFuture(): List<Kviz> {
            val buduci :MutableList<Kviz> = mutableListOf()
            val m = getUpisani()
            for(i in m!!.indices){
                if(SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss").parse(m!![i].datumPocetka)!!.after(Date()))buduci.add(m!![i])
            }
            return buduci
        }

        fun getFutureApi(): List<Kviz> {
            val buduci :MutableList<Kviz> = mutableListOf()
            for(i in moji!!.indices){
                if(SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss").parse(moji!![i].datumPocetka)!!.after(Date()))buduci.add(moji!![i])
            }
            return buduci
        }

        suspend fun getNotTaken(): List<Kviz> {
            val k = getUpisani()
            val preskoceni :MutableList<Kviz> = mutableListOf()
            for(i in k!!.indices){
                if(k!![i].datumKraj==null)continue
                if(SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss").parse(k!![i].datumKraj)!!.before(Date()) && k[i].predan==false) preskoceni.add(k!![i])
            }
            return preskoceni
        }

        suspend fun getNotTakenApi(): List<Kviz> {
            val taken = TakeKvizRepository.getPocetiKvizovi()
            val preskoceni :MutableList<Kviz> = mutableListOf()
            for(i in moji!!.indices){
                var nadjeno = false
                for(j in taken!!.indices){
                    if(taken[j].KvizId == moji!![i].id)nadjeno = true
                }
                if(nadjeno == false){
                    if(moji!![i].datumKraj==null)continue
                    if(SimpleDateFormat("yyyy-mm-ddThh:mm:ss").parse(moji!![i].datumKraj)!!.before(Date())) preskoceni.add(moji!![i])
                }
            }
            return preskoceni
        }
    }}
