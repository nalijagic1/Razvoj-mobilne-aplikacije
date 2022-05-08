package ba.etf.rma21.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@Entity
data class Pitanje(@PrimaryKey @SerializedName("id") var id:Int,
                   @ColumnInfo(name="apiId") var apiId:Int,
                   @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String?,
                   @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja") val tekstPitanja: String?,
                   @Ignore @SerializedName("opcije") val opcije: MutableList<String>?,
                   @ColumnInfo(name = "tacan") @SerializedName("tacan") val tacan:Int,
                   var mojOdgovor:Int?,
                   @ColumnInfo(name = "opcije") var options :String,
                   @ColumnInfo(name="KvizId") var KvizId:Int){
    constructor(id:Int,apiId: Int, naziv: String?, tekstPitanja: String?,  tacan:Int,options :String,KvizId:Int):this(id,apiId, naziv, tekstPitanja, mutableListOf(), tacan, -1, options,KvizId){
        val splitOptions = options.split(",")
        opcije?.addAll(splitOptions)
    }

    fun equalsBase(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pitanje

        if (naziv != other.naziv) return false
        if (tekstPitanja != other.tekstPitanja) return false
        if (tacan != other.tacan) return false
        if (options != other.options) return false
        if (KvizId != other.KvizId) return false

        return true
    }




}