package ba.etf.rma21.projekat.view

import android.app.AppOpsManager
import android.graphics.Color
import android.media.MediaDrm
import android.os.Bundle
import android.os.Parcelable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.viewmodel.PitanjaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.ArrayList


class FragmentPokusaj(pitanje:List<Pitanje>): Fragment(){
    private lateinit var leftNavigationView:NavigationView
    var p = pitanje

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.pokusaj_fragment, container, false)
        leftNavigationView = view.findViewById(R.id.navigacijaPitanja)
        quastion = p
        var menu = leftNavigationView.menu
        //System.out.println(p.indices)
        for(i in quastion.indices ){
            menu.add(i,i,i,"${i+1}")
            if(quastion[i].mojOdgovor != null){
                var boja = "#3DDC84"
                if(quastion[i].tacan!=p[i].mojOdgovor){
                    boja = "#DB4F3D"
                }
                val spanString = SpannableString(menu.get(i).getTitle().toString())
                spanString.setSpan(ForegroundColorSpan(Color.parseColor(boja)), 0, spanString.length, 0) // fix the color to white
                menu.get(i).setTitle(spanString)
            }
        }
       if(FragmentKvizovi.getKviza().rad!=null) menu.add(p.size,p.size,p.size,"Rezultat")
        leftNavigationView.setNavigationItemSelectedListener { item ->
            if(item.title.toString().equals("Rezultat")){
                val porukaFragment = FragmentPoruka.newInstance("Završili ste kviz ${FragmentKvizovi.getKviza().naziv} sa tacnosti ${PitanjaViewModel().procenat(quastion)}")
                prikažiPitanje(porukaFragment)
            }else {
                poz = item.title.toString().toInt()-1
                prikažiPitanje(FragmentPitanje(quastion[poz]))
            }

            false }
        return view
    }
    fun prikažiPitanje(fragment: Fragment){
        nav = leftNavigationView
        val transaction = childFragmentManager.beginTransaction()
        //System.out.println(kviz)
            transaction.replace(R.id.framePitanje, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
    }

    companion object {
        var quastion:List<Pitanje> = listOf()
        var poz = -1
        lateinit var nav:NavigationView
        fun getPozicija():Int{
            return poz
        }
        fun getNavigation():NavigationView{
            return nav
        }

    }
}




