package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UpisPredmetViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    //uzimaju se svi predmeti sa godine
    //uzimaju se svi predmeti na koje je student upisan
    fun predmetiGodine(god:Int,onSuccess: (info: ArrayList<String?>,vrsta:String) -> Unit, onError: () -> Unit){
        var predmeti = ArrayList<String?>()
        scope.launch{
                val resultSvi = PredmetIGrupaRepository.getPredmeti()
                when (resultSvi) {
                    is List<Predmet> -> {
                        val p = resultSvi.filter { it.godina == god }
                        val moji = PredmetIGrupaRepository.getUpisanePredmet()
                        //prvi clan je uvijek prayan string kako bi predstavljao opciju da korisnik ništa nije odabrao
                        predmeti.add("")
                        var brojac:Int
                        for (i in p.indices) {
                            brojac = 0
                            for(j in moji.indices){
                                //ukoliko se nađe predmet koji je upisan a na odabraoj je godini,on se preskače i ne dodaje se u listu
                                if(p[i].naziv.equals(moji[j]!!.naziv)){
                                    brojac++
                                    break
                                }
                            }
                            //u listu se dodavaju samo nazivi kako bi se samo oni ispisali u spinneru
                            if(brojac == 0) {
                                predmeti.add(p[i].id.toString() + " "+p[i].naziv)
                            }
                        }
                        onSuccess?.invoke(predmeti,"P")
                    }
                    else-> onError?.invoke()
                }
            }
        }


    fun grupePredmeta(pr:String,onSuccess: (info: ArrayList<String?>,vrsta:String) -> Unit, onError: () -> Unit) {
    val grupe = ArrayList<String?>()
    //uzimaju se sve grupe koje postoje na odabranom predemtu
        scope.launch{
            val g = PredmetIGrupaRepository.getGrupeZaPredmet(Integer.parseInt(pr.split(" ")[0]))
            when (g) {
                is List<Grupa> -> {
                    //prvi clan je uvijek prayan string kako bi predstavljao opciju da korisnik ništa nije odabrao
                    grupe.add("")
                    for (i in g.indices) {
                        //prolazi se kroz sve grupe i uzima se samo njihov naziv koji će se prikazati u spinneru
                        grupe.add(g[i].id.toString()+" "+ g[i].naziv)
                    }
                    onSuccess?.invoke(grupe,"G")
                }
                else-> onError?.invoke()
            }
        }
}

@RequiresApi(Build.VERSION_CODES.O)
fun upisiPredmet(grupa:String, onSuccess: () -> Unit, onError: () -> Unit){
    scope.launch{
        val g = PredmetIGrupaRepository.upisiUGrupu(Integer.parseInt(grupa.split(" ")[0]))
        when (g) {
            true -> {
                //System.out.println("proslo ubacivanje")
                DBRepository.updateNow()
                onSuccess?.invoke()
            }
            else-> onError?.invoke()
        }
    }
}
}