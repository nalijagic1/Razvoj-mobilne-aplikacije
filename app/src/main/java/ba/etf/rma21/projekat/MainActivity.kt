package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.core.view.size
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.*
import ba.etf.rma21.projekat.view.*
import ba.etf.rma21.projekat.viewmodel.AccountViewModel
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var container: FrameLayout
    lateinit var poruka :FragmentPoruka
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.kvizovi -> {
                val favoritesFragment = FragmentKvizovi.newInstance()
                openFragment(favoritesFragment,"Kvizovi")
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val recentFragments = FragmentPredmeti.newInstance()
                openFragment(recentFragments,"Predemti")
                return@OnNavigationItemSelectedListener true
            }
            R.id.predajKviz -> {
                for(i in FragmentPokusaj.quastion){
                    if(i.mojOdgovor==null)i.mojOdgovor = -1
                }
                PitanjaViewModel().predaj(FragmentKvizovi.pokusaj.id,FragmentKvizovi.nazivKviza.id,FragmentPokusaj.quastion,onSuccess = :: onSuccessMessage,onError = :: onError)
                return@OnNavigationItemSelectedListener false
            }
            R.id.zaustaviKviz -> {
                if(FragmentPokusaj.quastion.find { it.mojOdgovor == null } == null){
                    PitanjaViewModel().predaj(FragmentKvizovi.pokusaj.id,FragmentKvizovi.nazivKviza.id,FragmentPokusaj.quastion,onSuccess = :: onSuccessZaustavi,onError = :: onError)
                }else onSuccessZaustavi()
                return@OnNavigationItemSelectedListener false

            }
        }
        false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val i: Intent = this.intent
        bottomNavigation= findViewById(R.id.bottomNav)
        AccountRepository.setContext(bottomNavigation.context)
        DBRepository.setContext(bottomNavigation.context)
        KvizRepository.setContext(bottomNavigation.context)
        OdgovorRepository.setContext(bottomNavigation.context)
        PitanjeKvizRepository.setContext(bottomNavigation.context)
        PredmetIGrupaRepository.setContext(bottomNavigation.context)
        TakeKvizRepository.setContext(bottomNavigation.context)
        if(i.data!=null) i.getStringExtra("payload")?.let {  AccountViewModel().changeHash(it,onSuccess =:: onSuccess,onError = ::onError) }
        else AccountViewModel().changeHash(AccountRepository.acHash,onSuccess =:: onSuccess,onError = ::onError)

    }


    private fun openFragment(fragment: Fragment,tag:String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment,tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        //var c :  TextView? = container.findViewById(R.id.tvPoruka)

        if(bottomNavigation.selectedItemId == R.id.predmeti ){
            super.onBackPressed()
            bottomNavigation.selectedItemId= R.id.kvizovi
            val favoritesFragment = FragmentKvizovi.newInstance()
            openFragment(favoritesFragment,"Kvizovi")
        }else if(supportFragmentManager.findFragmentByTag("Poruka")?.isResumed == true){
            super.onBackPressed()
            bottomNavigation.selectedItemId= R.id.kvizovi
            val favoritesFragment = FragmentKvizovi.newInstance()
            openFragment(favoritesFragment,"Kvizovi")
        }
        else return

    }
    fun onSuccess(){
        bottomNavigation= findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //bottomNavigation.selectedItemId= R.id.kvizovi
        var zaustavi = bottomNavigation.menu.findItem(R.id.zaustaviKviz)
        var kvizovi = bottomNavigation.menu.findItem(R.id.kvizovi)
        var predmeti = bottomNavigation.menu.findItem(R.id.predmeti)
        var predaj = bottomNavigation.menu.findItem(R.id.predajKviz)
        //System.out.println(KvizRepository.getById(30))
        zaustavi.setVisible(false)
        predaj.setVisible(false)
        val favoritesFragment = FragmentKvizovi.newInstance()
        openFragment(favoritesFragment,"Kvizovi")
        container = findViewById(R.id.container)
        container.addOnLayoutChangeListener(View.OnLayoutChangeListener() { view: View, i: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int ->
            var pokusaj = FragmentKvizovi.getFragment()
            if (pokusaj.isResumed ) {
                val n = pokusaj.view?.findViewById(R.id.navigacijaPitanja) as NavigationView
                //System.out.println(FragmentKvizovi.nazivKviza.datumKraj!!.before(Date()))
                if(n.menu.size != FragmentPokusaj.quastion.size|| (FragmentKvizovi.nazivKviza.datumKraj!=null && SimpleDateFormat("yyyy-mm-ddThh:mm:ss").parse(FragmentKvizovi.nazivKviza.datumKraj)!!.before(Date()))) return@OnLayoutChangeListener
                zaustavi.setVisible(true)
                predaj.setVisible(true)
                kvizovi.setVisible(false)
                predmeti.setVisible(false)
            } else {
                zaustavi.setVisible(false)
                predaj.setVisible(false)
                kvizovi.setVisible(true)
                predmeti.setVisible(true)
            }

        })
    }
    fun onSuccessMessage(){
        poruka = FragmentPoruka.newInstance("Završili ste kviz ${FragmentKvizovi.getKviza().naziv} sa tacnosti ${PitanjaViewModel().procenat(FragmentPokusaj.quastion)}")
        openFragment(poruka,"Poruka")
        }
    fun onSuccessZaustavi(){
        val zaustaviFragment = FragmentKvizovi.newInstance()
        openFragment(zaustaviFragment,"Kvizovi")
    }
    fun onError() {}
}


