package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.gridlayout.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class KvizListAdapter(
     private var kvizovi: List<Kviz>,
     private val onItemClicked: (kviz:Kviz,pitanja:KvizTaken) -> Unit
) : RecyclerView.Adapter<KvizListAdapter.KvizViewHolder>() {
lateinit var mojiKvizovi:List<Kviz>
var pos:Int = 0
    lateinit var toast:Toast
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
   val view = LayoutInflater
         .from(parent.context)
         .inflate(R.layout.kviz_layout, parent, false)
     return KvizViewHolder(view)
 }
 override fun getItemCount(): Int = kvizovi.size
 @RequiresApi(Build.VERSION_CODES.O)
 override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
     toast = Toast.makeText(holder.subject.context, "Čeka se odgovor sa servera", Toast.LENGTH_SHORT)
    holder.subject.text = kvizovi[position].predmeti
     holder.name.text = kvizovi[position].naziv
     holder.duration.text = kvizovi[position].trajanje.toString()+ " min"
     val format: DateFormat = SimpleDateFormat("dd.MM.yyyy")
     //Pronalazimo id drawable elementa na osnovu naziva žanra
     val context: Context = holder.state.getContext()
     val currentDate = Date()
     var status: String
     //System.out.println(kvizovi[position].predan)
     //var splitDatum = kvizovi[position].datumPocetka
     if(kvizovi[position].rad != null){
         status ="plava"
         //System.out.println(kvizovi[position].rad)
         var datum = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].rad)
         holder.date.text = format.format(datum)
         holder.points.text = kvizovi[position].bodovi.toString()
     }
     else if(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].datumPocetka)!!.before(currentDate) && (kvizovi[position].datumKraj == null || SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].datumKraj)!!.after(currentDate))){
         status ="zelena"
         if(kvizovi[position].datumKraj==null)holder.date.text = "Beskonačan rok"
         else {
             var datum = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].datumKraj)
             holder.date.text = format.format(datum)
         }
         holder.points.text = ""
     }else if(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].datumPocetka)!!.after(currentDate)){
         status = "zuta"
         var datum = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].datumPocetka)
         holder.date.text = format.format(datum)
         holder.points.text = ""
     }else {
         status = "crvena"
         var datum = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[position].datumKraj)
         holder.date.text = format.format(datum)
         holder.points.text = ""
     }
     var id: Int = context.getResources()
         .getIdentifier(status, "drawable", context.getPackageName())
     if (id===0) id=context.getResources()
         .getIdentifier("picture1", "drawable", context.getPackageName())
     holder.state.setImageResource(id)
     holder.grid.setOnClickListener{
         pos = position
         //System.out.println(kvizovi[pos])
         KvizListViewModel().getMyQuizes(onSuccess = ::onSuccess, onError = ::onError)

         }

 }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSuccess(kviz:List<Kviz>){
        mojiKvizovi = kviz
        //System.out.println(kviz)
        var pronadjen = mojiKvizovi.findLast { kviz: Kviz -> kviz.equals(kvizovi[pos])}
        if(pronadjen!=null){
            if(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(kvizovi[pos].datumPocetka)!!.after(Date()))return
            toast.show()
            KvizListViewModel().pokreniPokušaj(kvizovi[pos].id,kvizovi[pos].apiId,onSuccess = ::onSuccessDodavanje, onError = ::onError)

    }}
    fun onError() {}

    fun onSuccessDodavanje(zapoceti:KvizTaken){
        toast.cancel()
        onItemClicked(kvizovi[pos], zapoceti)
    }
 fun updateQuizes(kvizovi: List<Kviz>) {
     this.kvizovi = kvizovi
     notifyDataSetChanged()
 }
 inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val state: ImageView = itemView.findViewById(R.id.status)
     val subject: TextView = itemView.findViewById(R.id.predmet)
     val date: TextView = itemView.findViewById(R.id.datum)
     val name: TextView = itemView.findViewById(R.id.naziv)
     val duration: TextView = itemView.findViewById(R.id.trajanje)
     val points: TextView = itemView.findViewById(R.id.bodovi)
     val grid : GridLayout = itemView.findViewById(R.id.kvizGrid)
     val item = itemView
 }

}