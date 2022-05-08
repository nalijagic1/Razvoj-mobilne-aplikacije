package ba.etf.rma21.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Poruka(@SerializedName("message") val message: String?){}