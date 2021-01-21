package com.example.crud.Database

import androidx.room.*
import com.example.crud.Database.Produsen

@Dao
interface ProdusenDao {
    @Insert
    suspend fun addProdusen(produsen: Produsen)

    @Update
    suspend fun updateProdusen(produsen: Produsen)

    @Delete
    suspend fun deleteProdusen(produsen: Produsen)

    @Query("SELECT * FROM Produsen")
    suspend fun getAllProdusen(): List<Produsen>

    @Query("SELECT * FROM produsen WHERE id=:produsen_id")
    suspend fun getProdusen(produsen_id: Int) : List<Produsen>
}