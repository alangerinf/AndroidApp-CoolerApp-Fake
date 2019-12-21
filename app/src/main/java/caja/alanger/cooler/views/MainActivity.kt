package caja.alanger.cooler.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import caja.alanger.cooler.R
import caja.alanger.cooler.services.ServiceReader
import caja.alanger.cooler.utils.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import jp.takke.cpustats.C
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class MainActivity : Activity() {

    private lateinit var mInterstitialAd: InterstitialAd

    private var processesMode: Int = 0

    private lateinit var mHandler: Handler
    private val mHandlerVG = Handler()
    private lateinit var mRunnable:Runnable

    private val mFormatPercent = DecimalFormat("##0.0")

    private lateinit var mSR: ServiceReader

    private var TAG : String ="TAGMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, ServiceReader::class.java))

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mHandler = Handler()
        val runShowInterestialForce = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                    mHandler.removeCallbacks(this)
                }else{
                    mHandler.postDelayed(this, 50)
                }
            }
        }
        mHandler.post(
            runShowInterestialForce
        )

        fabMain.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity, LoadingActivity::class.java)
            )
        }
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        iViewInfo.setOnClickListener{
            startActivity(Intent(this@MainActivity, InfoActivity::class.java))
        }
        iViewVote.setOnClickListener{
            val appPackageName =
                packageName // getPackageName() from Context or Activity object

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }

    }
    private fun setTextLabelMemory( percent: TextView, values: List<String>) {
        if (!values.isEmpty()) {
            var ram = (Integer.parseInt(values[0]) * 100.0f / mSR.memTotal).toDouble()
            percent.text =
                (  mFormatPercent.format( ram)+ C.percent)
            main_tViewTemp.text = ""+mFormatPercent.format(ram*1.2)+"°"

        }else{
            Toast.makeText(this,"vacio",Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        bindService(Intent(this, ServiceReader::class.java), mServiceConnection, 0)


    }

    private val mServiceConnection = object : ServiceConnection {

        @SuppressLint("NewApi")
        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            mSR = (service as ServiceReader.ServiceReaderDataBinder).getService()

            mRunnable = Runnable {


                if (mSR != null) { // finish() could have been called from the BroadcastReceiver
                    setTextLabelMemory(main_tViewRAMP, mSR.memUsed)
                }

                // Do something here
                var temp = getDeviceName()
                Log.d(TAG,temp)

                main_tViewModel.text=getDeviceName()
                main_tViewDisplay.text=""+getDisplayResolution(this@MainActivity)+" inches"
                main_tViewRam.text=getRamSize()
                main_tViewResolution.text=""+getScreenHeight()+"x"+getScreenWidth()+" pixels"
                main_tViewStorage.text=""+getTotalInternalMemorySize()
                main_tViewAndroidVersion.text=""+Build.VERSION.RELEASE

                var cpuUsages = execTask()
                val totalUsage = if (cpuUsages == null) 0 else cpuUsages[0]
                main_tViewCPU.text = ""+totalUsage+"%"

                // Schedule the task to repeat after 1 second
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
