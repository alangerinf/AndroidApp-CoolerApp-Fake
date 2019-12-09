package caja.alanger.cooler.views

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.*
import java.lang.Process
import android.os.Build
import java.io.*
import caja.alanger.cooler.R
import caja.alanger.cooler.utils.*


class MainActivity : Activity() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

    private var TAG : String ="MainActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHandler = Handler()


        fabMain.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity, LoadingActivity::class.java))

            }
        })


        mRunnable = Runnable {
            // Do something here
            var temp = getDeviceName()
            Log.d(TAG,temp)
            main_tViewModel.text=getDeviceName()
            main_tViewDisplay.text=""+getDisplayResolution(this)+" inches"
            main_tViewRam.text=getRamSize()
            main_tViewResolution.text=""+getScreenHeight()+"x"+getScreenWidth()+" pixels"
            main_tViewStorage.text=""+getTotalInternalMemorySize()
            main_tViewAndroidVersion.text=""+Build.VERSION.RELEASE.toFloat()


            var cpuUsages = execTask();
            val totalUsage = if (cpuUsages == null) 0 else cpuUsages[0]
            main_tViewCPU.text = ""+totalUsage+"%"

            // Schedule the task to repeat after 1 second
            mHandler.postDelayed(
                mRunnable, // Runnable
                5000 // Delay in milliseconds
            )
        }

        mHandler.post(
            mRunnable
        )

    }








    fun getCPUDetails(): String {

        /*
     *Created By Atiar Talukdar
     * 01/01/2018
     * contact@atiar.info
     */

        Log.d(TAG,"getCPUDetails")
        val processBuilder: ProcessBuilder
        var cpuDetails = ""
        val DATA = arrayOf("/system/bin/cat", "/proc/cpuinfo")
        val `is`: InputStream
        val process: Process
        val bArray: ByteArray
        bArray = ByteArray(1024)

        try {
            processBuilder = ProcessBuilder(*DATA)

            process = processBuilder.start()

            `is` = process.inputStream

            while (`is`.read(bArray) !== -1) {
                cpuDetails += String(bArray)   //Stroing all the details in cpuDetails
                Log.i(TAG, String(bArray))
            }
            `is`.close()

        } catch (ex: IOException) {
            ex.printStackTrace()
            Log.d(TAG,ex.toString())
        }

        return cpuDetails
    }







}
