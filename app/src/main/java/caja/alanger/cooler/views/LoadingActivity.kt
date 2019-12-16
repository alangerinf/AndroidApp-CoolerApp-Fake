package caja.alanger.cooler.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import caja.alanger.cooler.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.activity_main.fabMain

class LoadingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading2)

        fabMain.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@LoadingActivity, ListAppsActivity::class.java))
                finish()
            }
        })

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)

        val adRequestBottom = AdRequest.Builder().build()
        adViewBottom.loadAd(adRequestBottom)

    }
}
