package caja.alanger.cpucooler;

import android.content.Context;
import android.util.Log;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilities {

    public static String getDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    private final static String TAG = "TAG"+Utilities.class.getSimpleName();
    private final static int minMinutesAfected = 30;
    private final static float multMax = 1;
    private final static float multMin = 0.7f;
    public static float getMultiplicator(Context ctx) {
        float resp = 1;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d(TAG,"1");
            Date date = new Date();
            Log.d(TAG,"2");
            String ultimateDateCold = SharedPreferencesManager.getUltimateCold(ctx);

            Log.d(TAG,"3");
            Date dateFin = new Date();
            dateFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ultimateDateCold);
            Log.d(TAG,"4");
            long miliseconds = date.getTime() - dateFin.getTime();
            Log.d(TAG,"5");
            long minutesTranscurridos = miliseconds/60000;
            Log.d(TAG,"6");
            Log.d(TAG,"init"+formatter.format(date));
            Log.d(TAG,"ultimate:"+ultimateDateCold);
            Log.d(TAG,"minutes:"+minutesTranscurridos);
            if(minutesTranscurridos< minMinutesAfected){
                resp= multMin+ (minutesTranscurridos * ((multMax-multMin)/ minMinutesAfected));
                Log.d(TAG,"resp:"+resp);
            }
            Log.d(TAG,"7");
        } catch (ParseException e) {
            Log.e(TAG,e.toString());
            e.printStackTrace();
        }
        return resp;
    }


    public static  boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            Log.e(TAG,"isInternetAvailable "+e.toString());
            return false;
        }
    }

    public static String getHour(){
        Calendar calendar = new GregorianCalendar();

        int hour  = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String strHour = "";
        String strMinute = "";

        if(hour<10){
            strHour = "0"+hour;
        }else {
            strHour = ""+hour;
        }

        if(minute<10){
            strMinute = "0"+minute;
        }else {
            strMinute = ""+minute;
        }

        return strHour+":"+strMinute;
    }

}
