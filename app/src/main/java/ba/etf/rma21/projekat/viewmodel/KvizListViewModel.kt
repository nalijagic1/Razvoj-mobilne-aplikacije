package ba.etf.rma21.projekat.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class KvizListViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    fun getAllQuizes(onSuccess: (kviz: List<Kviz>) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = KvizRepository.getAll()
            when (result) {
                is List<Kviz> -> onSuccess?.invoke(sort(result))
                else-> onError?.invoke()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyQuizes(onSuccess: (kviz: List<Kviz>) -> Unit, onError: () -> Unit){
        scope.launch{
            DBRepository.updateNow()
            val result = KvizRepository.getUpisani()
            when (result) {
                is List<Kviz> -> {
                    //System.out.println(result)
                    onSuccess?.invoke(sort(result))
                }
                else-> onError?.invoke()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDoneQuizes(onSuccess: (kviz: List<Kviz>) -> Unit, onError: () -> Unit){
        scope.launch{
            DBRepository.updateNow()
            val result = KvizRepository.getDone()
            when (result) {
                is List<Kviz> -> onSuccess?.invoke(sort(result))
                else-> onError?.invoke()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFutureQuizes(onSuccess: (kviz: List<Kviz>) -> Unit, onError: () -> Unit){
        scope.launch{
            DBRepository.updateNow()
            val result = KvizRepository.getFuture()
            when (result) {
                is List<Kviz> -> onSuccess?.invoke(sort(result))
                else-> onError?.invoke()
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMissedQuizes(onSuccess: (kviz: List<Kviz>) -> Unit, onError: () -> Unit){
        scope.launch{
            DBRepository.updateNow()
            val result = KvizRepository.getNotTaken()
            when (result) {
                is List<Kviz> -> onSuccess?.invoke(sort(result))
                else-> onError?.invoke()
            }
        }
    }
    fun sort(lista:List<Kviz>):List<Kviz>{
        return lista.sortedBy {  kviz -> kviz.datumPocetka }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun pokreniPokušaj(idKviz: Int,idKvizApi:Int, onSuccess: (kviz: KvizTaken) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = TakeKvizRepository.getPocetiById(idKviz)
            when (result) {
                is KvizTaken ->{
                    onSuccess?.invoke(result)}
                else-> {
                    //System.out.println("tada")
                    val dodavanje = TakeKvizRepository.zapocniKviz(idKvizApi)
                    when (dodavanje) {
                        is KvizTaken -> {
                            TakeKvizRepository.zapocniKvizBase(dodavanje,idKviz)
                            dodavanje.pitanja = PitanjeKvizRepository.getPitanja(idKviz)
                            dodavanje.KvizId = idKviz
                            onSuccess?.invoke(dodavanje)
                        }
                        else->  onError?.invoke()
                    }
                }
            }
        }
    }




}