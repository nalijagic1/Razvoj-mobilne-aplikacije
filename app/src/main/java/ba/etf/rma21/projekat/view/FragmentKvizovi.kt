package ba.etf.rma21.projekat.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjaViewModel

class FragmentKvizovi: Fragment() {
    private lateinit var listaKviz: RecyclerView
    private lateinit var kvizoviAdapter: KvizListAdapter
    private var kvizListViewModel =  KvizListViewModel()
    private lateinit var spinner : Spinner
    private var pos :Int = 0
    lateinit var  toast:Toast
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        toast = Toast.makeText(context, "Čeka se odgovor sa servera", Toast.LENGTH_LONG)
        var view =  inflater.inflate(R.layout.kviz_list_fragment, container, false)
       val filteri = resources.getStringArray(R.array.Spinner)
        spinner = view.findViewById(R.id.filterKvizova)
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, filteri)
        spinner.adapter = adapter
        listaKviz = view.findViewById(R.id.listaKvizova)
        listaKviz.layoutManager = GridLayoutManager(view.context, 2)
        kvizoviAdapter = KvizListAdapter(listOf()){ kviz,pitanja -> startQuiz(kviz,pitanja) }
        listaKviz.adapter = kvizoviAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                pos = position
                toast.show()
                if(spinner.getItemAtPosition(position).toString().equals("Svi moji kvizovi"))kvizListViewModel.getMyQuizes(onSuccess = ::onSuccess, onError = ::onError)
                else if(spinner.getItemAtPosition(position).toString().equals("Urađeni kvizovi"))kvizListViewModel.getDoneQuizes(onSuccess = ::onSuccess, onError = ::onError)
                else if(spinner.getItemAtPosition(position).toString().equals("Budući kvizovi"))kvizListViewModel.getFutureQuizes(onSuccess = ::onSuccess, onError = ::onError)
                else if(spinner.getItemAtPosition(position).toString().equals("Prošli kvizovi"))kvizListViewModel.getMissedQuizes(onSuccess = ::onSuccess, onError = ::onError)
                else kvizListViewModel.getAllQuizes(onSuccess = ::onSuccess, onError = ::onError)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                toast.show()
                kvizListViewModel.getAllQuizes(onSuccess = ::onSuccess, onError = ::onError)
            }
        }
        return view;
    }
    fun onSuccess(kviz:List<Kviz>){
        //val toast = Toast.makeText(context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.cancel()
        kvizoviAdapter.updateQuizes(kviz)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
    private fun startQuiz(kviz: Kviz,zapoceti:KvizTaken) {
        nazivKviza = kviz
        pokusaj= zapoceti
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        fragm = FragmentPokusaj(pokusaj.pitanja!!)
        if (transaction != null) {
            transaction.replace(R.id.container, fragm)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
    }
    companion object {
        lateinit var nazivKviza :Kviz
        lateinit var pokusaj:KvizTaken
         var fragm : Fragment = Fragment()
        fun newInstance():FragmentKvizovi = FragmentKvizovi()
        fun getKviza ():Kviz{
            return nazivKviza
        }
        fun getFragment():Fragment{
            return fragm
        }
    }

}