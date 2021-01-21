package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crud.Database.AppRoomDB
import com.example.crud.Database.Constant
import com.example.crud.Database.Konsumen
import kotlinx.android.synthetic.main.activity_edite_konsumen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditeKonsumenActivity : AppCompatActivity() {
    val db by lazy { AppRoomDB(this) }
    private var konsumenId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edite_konsumen)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveKonsumen.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.konsumenDao().addKonsumen(
                    Konsumen(0, txt_nama.text.toString(), txt_username.text.toString())
                )
                finish()
            }
        }
        btn_updateKonsumen.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.konsumenDao().updateKonsumen(
                    Konsumen(konsumenId, txt_nama.text.toString(), txt_username.text.toString())
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateKonsumen.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveKonsumen.visibility = View.GONE
                btn_updateKonsumen.visibility = View.GONE
                getKonsumen()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveKonsumen.visibility = View.GONE
                getKonsumen()
            }
        }
    }

    fun getKonsumen() {
        konsumenId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val kn =  db.konsumenDao().getKonsumen( konsumenId )[0]
            txt_nama.setText( kn.nama )
            txt_username.setText( kn.username )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}