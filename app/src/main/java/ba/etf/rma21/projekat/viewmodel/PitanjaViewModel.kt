package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.view.FragmentKvizovi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjaViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    fun dajPitanja(kviz: Kviz): List<Pitanje> {
        return emptyList()
        //return PitanjeKvizRepository.getPitanja(kviz.naziv,kviz.nazivPredmeta)
    }

    fun dodajOdgovor(taken:Int, pitanje:Int, odg:Int, onSuccess: (odg:Int) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = OdgovorRepository.postaviOdgovorKviz(taken,pitanje,odg)
            when (result) {
                is Int -> onSuccess?.invoke(odg)
                else-> onError?.invoke()
            }
        }
    }

    fun predaj(taken:Int, kviz:Int, pitanja:List<Pitanje>, onSuccess: () -> Unit, onError: () -> Unit){
        scope.launch{
            var int = 0
            for(i in pitanja){
                if(i.mojOdgovor==-1) OdgovorRepository.postaviOdgovorKviz(taken,i.id,-1)
                int++
            }
            when (int) {
                pitanja.size -> {
                    OdgovorRepository.predajOdgovore(kviz)
                    onSuccess?.invoke()
                }

                else-> onError?.invoke()
            }
        }
    }

    fun procenat(lista: List<Pitanje>):Double{
        var pogodak = 0.0
        for(i  in lista.indices){
            if(lista[i].tacan == lista[i].mojOdgovor){
                pogodak++
            }
        }
        var procenat = (pogodak/ lista.size)*100
        procenat = String.format("%.2f", procenat).toDouble()
        return procenat
        }
}