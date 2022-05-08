package ba.etf.rma21.projekat.data.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
@Entity
data class Kviz(@PrimaryKey @SerializedName("id") var id:Int,
                @ColumnInfo(name="apiId") var apiId:Int,
                @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String?,
                @ColumnInfo(name = "datumPocetka") @SerializedName("datumPocetak") var datumPocetka: String?,
                @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") var datumKraj:String?,
                @ColumnInfo(name = "trajanje") @SerializedName("trajanje") val trajanje:Int?,
                @ColumnInfo(name = "predan") var predan:Boolean,
                @ColumnInfo(name = "GrupaId") var GrupaId:Int,
                @Ignore var predmeti:String?,
                @Ignore var bodovi:Int?,
                @Ignore var rad:String?,
                @Ignore @SerializedName("message") val message:String?
                ) {

    constructor(id:Int,apiId: Int,naziv: String?,datumPocetka: String?,datumKraj: String?,trajanje: Int?,predan: Boolean,GrupaId:Int) : this(id,apiId, naziv, datumPocetka, datumKraj, trajanje,predan,GrupaId,null, 0, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Kviz

        if (id != other.id) return false
        if (naziv != other.naziv) return false
        if (datumPocetka != other.datumPocetka) return false
        if (datumKraj != other.datumKraj) return false
        if (trajanje != other.trajanje) return false
        if (predmeti != other.predmeti) return false
        if (bodovi != other.bodovi) return false
        if (rad != other.rad) return false
        if (message != other.message) return false

        return true
    }

    fun equals2(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Kviz

        if (apiId != other.apiId) return false
        if (naziv != other.naziv) return false
        if (datumPocetka != other.datumPocetka) return false
        if (datumKraj != other.datumKraj) return false
        if (trajanje != other.trajanje) return false

        return true
    }

    fun eq(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Kviz

        if (naziv != other.naziv) return false
        if (datumPocetka != other.datumPocetka) return false
        if (datumKraj != other.datumKraj) return false
        if (trajanje != other.trajanje) return false
        if (predmeti != other.predmeti) return false
        return true
    }



}
