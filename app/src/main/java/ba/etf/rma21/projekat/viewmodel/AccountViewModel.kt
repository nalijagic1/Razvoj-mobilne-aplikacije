package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    @RequiresApi(Build.VERSION_CODES.O)
    fun changeHash(hash:String, onSuccess: () -> Unit, onError: () -> Unit){
        scope.launch{
            val result = AccountRepository.postaviHash(hash)
            when (result) {
                true -> onSuccess?.invoke()
                else-> onError?.invoke()
            }
        }
    }
}