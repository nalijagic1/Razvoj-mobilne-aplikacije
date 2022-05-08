package ba.etf.rma21.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjaViewModel
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList


class FragmentPitanje(pitanje:Pitanje): Fragment(){
    var pitanje = pitanje
    lateinit var tekst: TextView
    lateinit var odgovori :ListView
    lateinit var navigacija :NavigationView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.pitanje_fragment, container, false)
        navigacija = FragmentPokusaj.getNavigation()
        tekst = view.findViewById(R.id.tekstPitanja)
        odgovori = view.findViewById(R.id.odgovoriLista)
        tekst.text = pitanje.tekstPitanja
        var odg = pitanje.opcije
        //System.out.println(pitanje)
        var adapter =  ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, ArrayList(odg))
        odgovori.adapter = adapter
        odgovori.onItemClickListener =  object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                boje(position)
                PitanjaViewModel().dodajOdgovor(FragmentKvizovi.pokusaj.id,pitanje.id,position,onSuccess=::onSuccess,onError=::onError)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            if(pitanje.mojOdgovor!=null){
                if(pitanje.mojOdgovor !=-1) odgovori.post{boje(pitanje.mojOdgovor!!)}
                else odgovori.setEnabled(false)
            }
        }

    fun boje(index :Int){
        var odg = odgovori.get(pitanje.tacan) as TextView
        odg.setTextColor(Color.parseColor("#3DDC84"))
        var boja = "#3DDC84"
        if(pitanje.tacan!=index){
            odg.setTextColor(Color.parseColor("#000000"))
            odg.setBackgroundColor(Color.parseColor("#3DDC84"))
            var  greska :TextView= odgovori.get(index) as TextView
            greska.setBackgroundColor(Color.parseColor("#DB4F3D"))
            boja = "#DB4F3D"
        }
        val spanString = SpannableString(navigacija.menu.get(FragmentPokusaj.getPozicija()).getTitle().toString())
        spanString.setSpan(ForegroundColorSpan(Color.parseColor(boja)), 0, spanString.length, 0) // fix the color to white
        navigacija.menu.get(FragmentPokusaj.getPozicija()).setTitle(spanString)
        odgovori.setEnabled(false)

    }
    fun onSuccess(odg:Int){
        FragmentPokusaj.quastion[FragmentPokusaj.poz].mojOdgovor = odg
    }
    fun onError() {}



}
