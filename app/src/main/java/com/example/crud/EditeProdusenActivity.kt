package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crud.Database.AppRoomDB
import com.example.crud.Database.Constant
import com.example.crud.Database.Produsen
import kotlinx.android.synthetic.main.activity_edite_produsen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditeProdusenActivity : AppCompatActivity() {

        val db by lazy { AppRoomDB(this) }
        private var produsenId: Int = 0


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edite_produsen)
            setupListener()
            setupView()
        }

        fun setupListener(){
            btn_saveProdusen.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    db.ProdusenDao().addProdusen(
                        Produsen(0, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_telpon.text.toString()) )
                    )
                    finish()
                }
            }
            btn_updateProdusen.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    db.ProdusenDao().updateProdusen(
                        Produsen(produsenId, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_telpon.text.toString()) )
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
                    btn_updateProdusen.visibility = View.GONE
                }
                Constant.TYPE_READ -> {
                    btn_saveProdusen.visibility = View.GONE
                    btn_updateProdusen.visibility = View.GONE
                    getProdusen()
                }
                Constant.TYPE_UPDATE -> {
                    btn_saveProdusen.visibility = View.GONE
                    getProdusen()
                }
            }
        }

        fun getProdusen() {
            produsenId = intent.getIntExtra("intent_id", 0)
            CoroutineScope(Dispatchers.IO).launch {
                val pr =  db.ProdusenDao().getProdusen( produsenId )[0]
                txt_nama.setText( pr.nama )
                txt_alamat.setText( pr.alamat )
                txt_telpon.setText( pr.telpon.toString() )
            }
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return super.onSupportNavigateUp()
        }
    }