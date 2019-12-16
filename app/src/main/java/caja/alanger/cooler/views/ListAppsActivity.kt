package caja.alanger.cooler.views

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import caja.alanger.cooler.R
import android.util.Log
import caja.alanger.cooler.utils.getAllApps
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_list_apps.*


class ListAppsActivity : Activity() {

    private val TAG = "TAG"+ListAppsActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_apps)
        var appsRunnig  = getAllApps(this)

        var adapter = RViewAdapterMain(this,appsRunnig)
        recyclerView.adapter=adapter

        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        iViewBack.setOnClickListener{
            finish()
        }
    }
}
