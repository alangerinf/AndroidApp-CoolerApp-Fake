package caja.alanger.cpucooler.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import caja.alanger.cpucooler.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        /*
        fabMain.setOnClickListener {
            startActivity(Intent(this@LoadingActivity, ListAppsActivity::class.java))
            finish()
        }
         */

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)

        val adRequestBottom = AdRequest.Builder().build()
        adViewBottom.loadAd(adRequestBottom)

        startProgress()

    }

    val handler = Handler()
    var progreso : Int = 0

    val runa = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {

            if(progreso==100){
                startActivity(Intent(this@LoadingActivity, ListAppsActivity::class.java))
                finish()
                handler.removeCallbacks(this)
            }else{
                progreso++
                tViewProgress.text = "$progreso%"
                handler.postDelayed(this, 50)
            }


        }
    }

    fun startProgress() {
        handler.removeCallbacks(runa)
        handler.postDelayed(runa, 200)
    }

    override fun onBackPressed() {
        handler.removeCallbacks(runa)
        super.onBackPressed()
    }
}
