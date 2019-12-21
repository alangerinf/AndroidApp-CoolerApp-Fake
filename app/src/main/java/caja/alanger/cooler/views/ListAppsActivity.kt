package caja.alanger.cooler.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import caja.alanger.cooler.R
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import caja.alanger.cooler.SharedPreferencesManager
import caja.alanger.cooler.Utilities
import caja.alanger.cooler.services.ServiceReader
import caja.alanger.cooler.utils.*
import com.google.android.gms.ads.*
import jp.takke.cpustats.C
import kotlinx.android.synthetic.main.activity_list_apps.*
import kotlinx.android.synthetic.main.activity_list_apps.adView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class ListAppsActivity : Activity() {

    private lateinit var mHandler: Handler
    private val TAG = "TAG" + ListAppsActivity::class.java.simpleName
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_apps)

        mHandler = Handler()
        var appsRunnig = getAllApps(this)

        var adapter = RViewAdapterMain(this, appsRunnig)
        recyclerView.adapter = adapter

        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                SharedPreferencesManager.saveCold(this@ListAppsActivity,Utilities.getDateTime())
                siguiente()
            }
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        iViewBack.setOnClickListener {
            finish()
        }

        btnEnfriar.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }


    }

    fun siguiente() {
        startActivity(Intent(this, CoolingActivity::class.java))
        finish()
    }

    public override fun onStart() {
        super.onStart()
        bindService(Intent(this, ServiceReader::class.java), mServiceConnection, 0)


    }
    private val mFormatPercent = DecimalFormat("##0.0")
    private fun setTextLabelMemory(percent: TextView, values: List<String>) {
        if (!values.isEmpty()) {
            var ram = (Integer.parseInt(values[0]) * 100.0f / mSR.memTotal).toDouble()
            percent.text =
                (  mFormatPercent.format(ram*1.2)+ "°")

        }else{
            Toast.makeText(this,"vacio", Toast.LENGTH_SHORT).show()
        }
    }
    private lateinit var mRunnable: Runnable

    private lateinit var mSR: ServiceReader

    private val mServiceConnection = object : ServiceConnection {

        @SuppressLint("NewApi")
        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            mSR = (service as ServiceReader.ServiceReaderDataBinder).getService()

            mRunnable = Runnable {
                if (mSR != null) { // finish() could have been called from the BroadcastReceiver
                    setTextLabelMemory(listapps_tViewRam, mSR.memUsed)
                }
                mHandler.postDelayed(
                    mRunnable, // Runnable
                    2000 // Delay in milliseconds
                )
            }

            mHandler.post(
                mRunnable
            )
        }

        override fun onServiceDisconnected(className: ComponentName) {


        }
    }

}
