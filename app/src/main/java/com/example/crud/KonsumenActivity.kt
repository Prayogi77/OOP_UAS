package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud.Database.AppRoomDB
import com.example.crud.Database.Constant
import com.example.crud.Database.Konsumen
import kotlinx.android.synthetic.main.activity_konsumen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KonsumenActivity : AppCompatActivity() {
    val db by lazy { AppRoomDB(this) }
    lateinit var KonsumenAdapter: KonsumenAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsumen)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadKonsumen()
    }

    fun loadKonsumen(){
        CoroutineScope(Dispatchers.IO).launch {
            val allKonsumen = db.konsumenDao().getAllKonsumen()
            Log.d("KonsumenActivity", "dbResponse: $allKonsumen")
            withContext(Dispatchers.Main) {
                KonsumenAdapter.setData(allKonsumen)
            }
        }
    }

    fun setupListener() {
        btn_createKonsumen.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        KonsumenAdapter = KonsumenAdapter(arrayListOf(), object: KonsumenAdapter.OnAdapterListener {
            override fun onClick(konsumen: Konsumen) {
                intentEdit(konsumen.id, Constant.TYPE_READ)
            }

            override fun onDelete(konsumen: Konsumen) {
                deleteDialog(konsumen)
            }
            override fun onUpdate(konsumen: Konsumen) {
                intentEdit(konsumen.id, Constant.TYPE_UPDATE)
            }
        })
        list_konsumen.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = KonsumenAdapter
        }
    }

    fun intentEdit(konsumenId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditeKonsumenActivity::class.java)
                .putExtra("intent_id", konsumenId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(konsumen: Konsumen) {
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
                    db.konsumenDao().deleteKonsumen(konsumen)
                    loadKonsumen()
                }
            }
        }
        alertDialog.show()
    }

}