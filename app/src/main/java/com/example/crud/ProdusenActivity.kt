package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud.Database.AppRoomDB
import com.example.crud.Database.Constant
import com.example.crud.Database.Produsen
import kotlinx.android.synthetic.main.activity_produsen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProdusenActivity : AppCompatActivity() {
    val db by lazy { AppRoomDB(this) }
    lateinit var produsenAdapter: ProdusenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produsen)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadProdusen()
    }

    fun loadProdusen() {
        CoroutineScope(Dispatchers.IO).launch {
            val allProdusen = db.ProdusenDao().getAllProdusen()
            Log.d("ProdusenActivity", "dbResponse: $allProdusen")
            withContext(Dispatchers.Main) {
                produsenAdapter.setData(allProdusen)
            }
        }
    }

    fun setupListener() {
        btn_createProdusen.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
       produsenAdapter = ProdusenAdapter(arrayListOf(), object: ProdusenAdapter.OnAdapterListener {
            override fun onClick(produsen: Produsen) {
                intentEdit(produsen.id, Constant.TYPE_READ)
            }

            override fun onDelete(produsen: Produsen) {
                deleteDialog(produsen)
            }
            override fun onUpdate(produsen: Produsen) {
                // edit data
                intentEdit(produsen.id, Constant.TYPE_UPDATE)
            }

        })
        list_produsen.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = produsenAdapter
        }
    }

    fun intentEdit(pembeliId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditeProdusenActivity::class.java)
                .putExtra("intent_id", pembeliId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(produsen: Produsen) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.ProdusenDao().deleteProdusen(produsen)
                    loadProdusen()
                }
            }
        }
        alertDialog.show()
    }
}