package ba.etf.rma21.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Grupa(@PrimaryKey @SerializedName("id") val id:Int,
                 @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String?,
                 @ColumnInfo(name = "PredmetId") @SerializedName("PredmetId") val PredmetId:Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grupa

        if (id != other.id) return false
        if (naziv != other.naziv) return false
        if (PredmetId != other.PredmetId) return false

        return true
    }
}