package caja.alanger.cooler.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import caja.alanger.cooler.R
import kotlinx.android.synthetic.main.activity_main.*

class LoadingActivity : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading2)

        fabMain.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
            }
        })
    }
}
