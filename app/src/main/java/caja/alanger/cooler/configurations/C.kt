package jp.takke.cpustats

object C {

    const val LOG_NAME = "CpuStats"

    // Preference keys
    const val PREF_KEY_START_ON_BOOT = "StartOnBoot"

    const val PREF_KEY_UPDATE_INTERVAL_SEC = "UpdateIntervalSec"
    const val PREF_DEFAULT_UPDATE_INTERVAL_SEC = 5

    const val PREF_KEY_SHOW_FREQUENCY_NOTIFICATION = "ShowFrequencyNotification"
    const val PREF_KEY_SHOW_USAGE_NOTIFICATION = "ShowUsageNotification"

    // 初期Alarmの遅延時間[ms]
    const val ALARM_STARTUP_DELAY_MSEC = 1000

    // Service維持のためのAlarmの更新間隔[ms]
    const val ALARM_INTERVAL_MSEC = 60 * 1000

    // CPU使用率通知のアイコンモード
    const val PREF_KEY_CORE_DISTRIBUTION_MODE = "CoreDistributionMode"
    const val CORE_DISTRIBUTION_MODE_2ICONS = 0          // 最大2アイコン(デフォルト)
    const val CORE_DISTRIBUTION_MODE_1ICON_UNSORTED = 1  // 1アイコン+非ソート
    const val CORE_DISTRIBUTION_MODE_1ICON_SORTED = 2    // 1アイコン+ソート

    const val READ_BUFFER_SIZE = 1024

    const val prefs = "Prefs"
    const val sbh = "status_bar_height"
    const val nbh = "navigation_bar_height"
    const val dimen = "dimen"
    const val android = "android"
    const val europeLondon = "Europe/London"
    const val marketDetails = "market://details?id="
    const val defaultIntervalRead = 1000
    const val defaultIntervalUpdate = 1000
    const val defaultIntervalWidth = 1

    // ServiceReader
    const val readThread = "readThread"

    const val actionStartRecord = "actionRecord"
    const val actionStopRecord = "actionStop"
    const val actionClose = "actionClose"
    const val actionSetIconRecord = "actionSetIconRecord"
    const val actionDeadProcess = "actionRemoveProcess"
    const val actionFinishActivity = "actionCloseActivity"

    const val pId = "pId"
    const val pName = "pName"
    const val pPackage = "pPackage"
    const val pAppName = "pAppName"
    const val pTPD = "pPTD"
    const val pSelected = "pSelected"
    const val pDead = "pDead"
    const val pColour = "pColour"
    const val work = "work"
    const val workBefore = "workBefore"
    const val pFinalValue = "finalValue"
    const val process = "process"
    const val screenRotated = "screenRotated"
    const val listSelected = "listSelected"
    const val listProcesses = "listProcesses"

    // ActivityMain
    const val storagePermission = 1
    const val kB = "kB"
    const val percent = "%"
    //	static final String drawThread = "drawThread";
    const val menuShown = "menuShown"
    const val settingsShown = "settingsShown"
    const val orientation = "orientation"
    const val processesMode = "processesMode"
    const val canvasLocked = "canvasLocked"

    const val welcome = "firstTime"
    const val welcomeDate = "firstTimeDate"
    const val firstTimeProcesses = "firstTimeProcesses"
    const val feedbackFirstTime = "feedbackFirstTime"
    const val feedbackDone = "feedbackDone"

    const val intervalRead = "intervalRead"
    const val intervalUpdate = "intervalUpdate"
    const val intervalWidth = "intervalWidth"

    const val cpuTotal = "cpuTotalD"
    const val cpuAM = "cpuAMD"
    const val memUsed = "memUsedD"
    const val memAvailable = "memAvailableD"
    const val memFree = "memFreeD"
    const val cached = "cachedD"
    const val threshold = "thresholdD"

    // GraphicView
    const val processMode = "processMode"
    const val processesModeShowCPU = 0
    const val processesModeShowMemory = 1

    const val graphicMode = "graphicMode"
    const val graphicModeShowMemory = 0
    const val graphicModeHideMemory = 1

    // ActivityPreferences
    const val currentItem = "ci"

    const val mSRead = "mSRead"
    const val mSUpdate = "mSUpdate"
    const val mSWidth = "mSWidth"

    const val mCBMemFreeD = "memFreeD"
    const val mCBBuffersD = "buffersD"
    const val mCBCachedD = "cachedD"
    const val mCBActiveD = "activeD"
    const val mCBInactiveD = "inactiveD"
    const val mCBSwapTotalD = "swapTotalD"
    const val mCBDirtyD = "dirtyD"
    const val mCBCpuTotalD = "cpuTotalD"
    const val mCBCpuAMD = "cpuAMD"
//	static final String mCBCpuRestD = "cpuRestD";
}
