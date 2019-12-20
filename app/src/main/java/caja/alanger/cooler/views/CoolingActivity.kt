package caja.alanger.cooler.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import caja.alanger.cooler.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_cooling.*
import kotlinx.android.synthetic.main.activity_loading.*

class CoolingActivity : Activity() {

    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooling)
        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        cool_adViewTop.loadAd(adRequestTop)

        val adRequestBottom = AdRequest.Builder().build()
        cool_adViewBottom.loadAd(adRequestBottom)



        handler.postDelayed({
            lottieCheck.playAnimation()
        },3000)

        handler.postDelayed({
            finish()
        },7000)

    }



}
