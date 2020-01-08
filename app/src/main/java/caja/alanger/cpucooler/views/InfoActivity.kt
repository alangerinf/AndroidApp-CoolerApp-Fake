package caja.alanger.cpucooler.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import caja.alanger.cpucooler.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_list_apps.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        title="Informacion Inportante"

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

    }
}
