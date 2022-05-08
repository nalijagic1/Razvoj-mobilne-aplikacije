package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel

class FragmentPredmeti : Fragment() {
    private lateinit var godine: Spinner
    private lateinit var predmeti: Spinner
    private lateinit var grupe: Spinner
    private  var upisViewModel = UpisPredmetViewModel()
    private lateinit var upisi : Button
    var indexGodina = 0
    var indexPredmet = 0
    var indexGrupa = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.upis_layer, container, false)
        godine = view.findViewById(R.id.odabirGodina)
        predmeti = view.findViewById(R.id.odabirPredmet)
        predmeti.setEnabled(false)
        grupe = view.findViewById(R.id.odabirGrupa)
        grupe.setEnabled(false)
        upisi = view.findViewById(R.id.dodajPredmetDugme)
        upisi.setEnabled(false)
        val god = resources.getStringArray(R.array.Godine)
        val adapterG = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, god)
        godine.adapter = adapterG
        val context: Context = predmeti.getContext()
        godine.setSelection(getGodina())
        godine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                changeGodina(position)
                if(godine.getItemAtPosition(position).equals("")){
                    predmeti.setEnabled(false)
                    grupe.setEnabled(false)
                    upisi.setEnabled(false)
                    predmeti.adapter = null
                    grupe.adapter = null
                    changeGrupa(0)
                    changePredmet(0)
                    return
                }
                indexGodina = position
                upisViewModel.predmetiGodine(indexGodina,onSuccess = ::onSuccessSpinner,onError = ::onError)

            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        predmeti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView1: AdapterView<*>?, selectedItemView1: View?, position1: Int, id1: Long) {
                changePredmet(position1)
                if(predmeti.getItemAtPosition(position1).equals("")){
                    grupe.setEnabled(false)
                    upisi.setEnabled(false)
                    grupe.adapter = null
                    changeGrupa(0)
                    return
                }
                upisViewModel.grupePredmeta(predmeti.getItemAtPosition(position1).toString(),onSuccess =:: onSuccessSpinner,onError = ::onError)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }

        grupe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView1: AdapterView<*>?, selectedItemView1: View?, position1: Int, id1: Long) {
                changeGrupa(position1)
                if(grupe.getItemAtPosition(position1).equals("")){
                    upisi.setEnabled(false)
                    return
                }
                indexGrupa = position1
                upisi.setEnabled(true)
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
        upisi.setOnClickListener { view ->
            changeGrupa(0)
            changePredmet(0)
            upisViewModel.upisiPredmet(grupe.getItemAtPosition(indexGrupa).toString(),onSuccess = ::onSuccessUpis,onError = ::onError)

        }
        return view;

    }
    fun onSuccessSpinner(info: ArrayList<String?>,vrsta:String){
        if(vrsta.equals("P")){
            predmeti.setEnabled(true)
            val adapterP =  ArrayAdapter(predmeti.getContext(), android.R.layout.simple_spinner_item, info)
            predmeti.adapter = adapterP
            predmeti.setSelection(getPredmet())
        }else if(vrsta.equals("G")){
            grupe.setEnabled(true)
            val adapterGr =  ArrayAdapter(grupe.context, android.R.layout.simple_spinner_item, info)
            grupe.adapter = adapterGr
            grupe.setSelection(getGrupa())
        }
    }
    fun onSuccessUpis(){
        openFragment(FragmentPoruka.newInstance("Uspješno ste upisani u grupu ${grupe.getItemAtPosition(indexGrupa)} predmeta ${predmeti.getItemAtPosition(indexPredmet)}!"))
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
    companion object {
        var indexGodina = 0
        var indexPredmt = 0
        var indexGrupa = 0
        fun newInstance():FragmentPredmeti = FragmentPredmeti()

        fun changeGodina(godina:Int){
            indexGodina = godina
        }
        fun changePredmet(predmet:Int){
            indexPredmt = predmet
        }
        fun changeGrupa(grupa:Int){
            indexGrupa = grupa
        }
        fun getGodina():Int{
            return indexGodina
        }
        fun getPredmet():Int{
            return indexPredmt
        }
        fun getGrupa():Int{
            return indexGrupa
        }
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.container, fragment)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
    }




    }
