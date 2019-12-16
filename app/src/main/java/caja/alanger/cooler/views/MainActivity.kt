package caja.alanger.cooler.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.*
import android.os.Build
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import caja.alanger.cooler.R
import caja.alanger.cooler.services.ServiceReader
import caja.alanger.cooler.utils.*
import jp.takke.cpustats.C
import jp.takke.cpustats.C.intervalUpdate
import java.text.DecimalFormat


class MainActivity : Activity() {



    private var processesMode: Int = 0

    private lateinit var mHandler: Handler
    private val mHandlerVG = Handler()
    private lateinit var mRunnable:Runnable


    private val mFormat = DecimalFormat("##,###,##0")
    private val mFormatPercent = DecimalFormat("##0.0")
    private val mFormatTime = DecimalFormat("0.#")

    private lateinit var mSR: ServiceReader

    private var TAG : String ="TAGMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, ServiceReader::class.java))

        mHandler = Handler()

        fabMain.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity, LoadingActivity::class.java))

            }
        })


    }
    private fun setTextLabelMemory( percent: TextView, values: List<String>) {
        if (!values.isEmpty()) {
            percent.text =
                (  mFormatPercent.format((Integer.parseInt(values[0]) * 100.0f / mSR.memTotal).toDouble()) )+ C.percent
        }else{
            Toast.makeText(this,"vacio",Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        bindService(Intent(this, ServiceReader::class.java), mServiceConnection, 0)
    }

/*
    private val drawRunnable = object : Runnable {
        @SuppressLint("NewApi")
        override fun run() {
            mHandler.postDelayed(this, intervalUpdate.toLong())
            if (mSR != null) { // finish() could have been called from the BroadcastReceiver
                //mHandlerVG.post(drawRunnableGraphic)

                setTextLabelCPU(null, mTVCPUTotalP, mSR.getCPUTotalP())
                if (processesMode == C.processesModeShowCPU)
                    setTextLabelCPU(null, mTVCPUAMP, mSR.getCPUAMP())
                else
                    setTextLabelCPU(null, mTVCPUAMP, null, mSR.getMemoryAM())

                setTextLabelMemory(mTVMemUsed, mTVMemUsedP, mSR.getMemUsed())
                setTextLabelMemory(mTVMemAvailable, mTVMemAvailableP, mSR.getMemAvailable())
                setTextLabelMemory(mTVMemFree, mTVMemFreeP, mSR.getMemFree())
                setTextLabelMemory(mTVCached, mTVCachedP, mSR.getCached())
                setTextLabelMemory(mTVThreshold, mTVThresholdP, mSR.getThreshold())

                for (n in 0 until mLProcessContainer.getChildCount()) {
                    val l = mLProcessContainer.getChildAt(n) as LinearLayout
                    setTextLabelCPUProcess(l)
                    setTextLabelMemoryProcesses(l)
                }
            }
        }
    }
*/
    private val mServiceConnection = object : ServiceConnection {

        @SuppressLint("NewApi")
        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            mSR = (service as ServiceReader.ServiceReaderDataBinder).getService()

/*
            mVG.setService(mSR)
            mVG.setParameters(cpuTotal, cpuAM, memUsed, memAvailable, memFree, cached, threshold)
*/
 //           setIconRecording()
         //   main_tViewRAMP.setText(mFormat.format(mSR.getMemTotal()) + C.kB)
/*
            switchParameter(cpuTotal, mLCPUTotal)
            switchParameter(cpuAM, mLCPUAM)

            switchParameter(memUsed, mLMemUsed)
            switchParameter(memAvailable, mLMemAvailable)
            switchParameter(memFree, mLMemFree)
            switchParameter(cached, mLCached)
            switchParameter(threshold, mLThreshold)
*/
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


            // When on ActivityProcesses the screen is rotated, ActivityMain is destroyed and back is pressed from ActivityProcesses
            // mSR isn't ready before onActivityResult() is called. So the Intent is saved till mSR is ready.

/*
            if (tempIntent != null) {
                tempIntent.putExtra(C.screenRotated, true)
                onActivityResult(1, 1, tempIntent)
                tempIntent = null
            } else
                onActivityResult(1, 1, null)

            if (Build.VERSION.SDK_INT >= 16) {
                mLProcessContainer.getViewTreeObserver()
                    .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            mLProcessContainer.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this)
                            val lt = LayoutTransition()
                            lt.enableTransitionType(LayoutTransition.APPEARING)
                            lt.enableTransitionType(LayoutTransition.DISAPPEARING)
                            lt.enableTransitionType(LayoutTransition.CHANGING)
                            mLProcessContainer.setLayoutTransition(lt)
                            val lt2 = LayoutTransition()
                            lt2.enableTransitionType(LayoutTransition.CHANGING)
                            lt2.setStartDelay(LayoutTransition.CHANGING, 300)
                            (mLProcessContainer.getParent() as LinearLayout).layoutTransition = lt2
                        }
                    })
            }

             */
        }

        override fun onServiceDisconnected(className: ComponentName) {


        }
    }


}
