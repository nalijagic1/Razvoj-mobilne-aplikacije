package ba.etf.rma21.projekat

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel

class UpisPredmet : AppCompatActivity() {
    /*private lateinit var godine: Spinner
    private lateinit var predmeti: Spinner
    private lateinit var grupe: Spinner
    private  var upisViewModel = UpisPredmetViewModel()
    private lateinit var upisi : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upis_layer)
        godine = findViewById(R.id.odabirGodina)
        predmeti = findViewById(R.id.odabirPredmet)
        predmeti.setEnabled(false)
        grupe = findViewById(R.id.odabirGrupa)
        grupe.setEnabled(false)
        upisi = findViewById(R.id.dodajPredmetDugme)
        upisi.setEnabled(false)
        val god = resources.getStringArray(R.array.Godine)
        val adapterG = ArrayAdapter(this, android.R.layout.simple_spinner_item, god)
        godine.adapter = adapterG
        //godine.setSelection(upisViewModel.prethodnaGodinda())
        val context: Context = predmeti.getContext()
        var indexGodina = 0
        godine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
               if(godine.getItemAtPosition(position).equals("")){
                   predmeti.setEnabled(false)
                   grupe.setEnabled(false)
                   upisi.setEnabled(false)
                   predmeti.adapter = null
                   grupe.adapter = null
                   return
               }
                indexGodina = position
                   predmeti.setEnabled(true)
                   System.out.println(godine.getItemAtPosition(position))
                   val pred :ArrayList<String> = upisViewModel.predmetiGodine(godine.getItemAtPosition(position).toString().toInt())
                   val adapterP =  ArrayAdapter(context, android.R.layout.simple_spinner_item, pred)
                   predmeti.adapter = adapterP
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
        var indexPredmet = 0
        predmeti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView1: AdapterView<*>?, selectedItemView1: View?, position1: Int, id1: Long) {
                if(predmeti.getItemAtPosition(position1).equals("")){
                    grupe.setEnabled(false)
                    upisi.setEnabled(false)
                    grupe.adapter = null
                    return
                }
                indexPredmet = position1
                grupe.setEnabled(true)
                val gr :ArrayList<String> = upisViewModel.grupePredmeta(predmeti.getItemAtPosition(position1).toString())
                val adapterGr =  ArrayAdapter(context, android.R.layout.simple_spinner_item, gr)
                grupe.adapter = adapterGr
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
        var indexGrupa = 0
        grupe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView1: AdapterView<*>?, selectedItemView1: View?, position1: Int, id1: Long) {
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
            upisViewModel.upisiPredmet(predmeti.getItemAtPosition(indexPredmet).toString(),grupe.getItemAtPosition(indexGrupa).toString(),indexGodina)
            finish()
        }


    }

*/

}