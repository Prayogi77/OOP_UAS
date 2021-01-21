package com.example.crud.Database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "produsen")
data class Produsen(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nama") val nama: String,
    @ColumnInfo(name = "alamat") val alamat: String,
    @ColumnInfo(name = "telpon") val telpon: Int
)