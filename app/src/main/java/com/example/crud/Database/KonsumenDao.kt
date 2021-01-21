package com.example.crud.Database

import androidx.room.*

@Dao
interface KonsumenDao {
    @Insert
    suspend fun addKonsumen(konsumen: Konsumen)

    @Update
    suspend fun updateKonsumen(konsumen: Konsumen)

    @Delete
    suspend fun deleteKonsumen(konsumen: Konsumen)

    @Query("SELECT * FROM konsumen")
    suspend fun getAllKonsumen(): List<Konsumen>

    @Query("SELECT * FROM konsumen WHERE id=:konsumen_id")
    suspend fun getKonsumen(konsumen_id: Int) : List<Konsumen>
}