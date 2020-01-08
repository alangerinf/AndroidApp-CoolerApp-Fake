package caja.alanger.cpucooler.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import android.content.Context.ACTIVITY_SERVICE
import kotlin.collections.ArrayList


var TAG :String  = "AppUtils"

    fun getAllApps(ctx :Context): MutableList <PackageInfo>{

        var aplist :MutableList <PackageInfo> = ArrayList()

        val packages = ctx.packageManager.getInstalledPackages(0)
        for (pack in packages) {
            val activityInfo =
                ctx.packageManager.getPackageInfo(pack.packageName, PackageManager.GET_ACTIVITIES).activities
            if (activityInfo != null) {
                if(pack.packageName!=ctx.packageName){
                    aplist.add(pack)
                }
            }
        }
        return  aplist
    }



    fun getAppsRunning(ctx: Context):MutableList <PackageInfo>{
        var apps : MutableList <PackageInfo> = ArrayList()

        val activityManager = ctx
            .getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val procInfos = activityManager
            .runningAppProcesses
        for(pkg in procInfos){
            apps.add(ctx.packageManager.getPackageInfo(pkg.processName,0))
        }
        for (idx in procInfos.indices)
        {
        Log.d(
            TAG, "" + (idx + 1) + "."
            + procInfos[idx].processName + "\n"
        )
}
        return apps
    }

    fun isAppRunning(context: Context, packageName: String): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val procInfos = activityManager.runningAppProcesses
        if (procInfos != null) {
            for (processInfo in procInfos) {
                if (processInfo.processName == packageName) {
                    return true
                }
            }
        }
        return false
    }
