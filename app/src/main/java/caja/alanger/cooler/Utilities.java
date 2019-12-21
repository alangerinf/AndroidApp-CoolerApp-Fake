package caja.alanger.cooler;

import android.content.Context;

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

    private final static int minMinutesAfected = 30;
    private final static float multMax = 1;
    private final static float multMin = 0.7f;
    public static float getMultiplicator(Context ctx) {
        float resp = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String ultimateDateCold = SharedPreferencesManager.getUltimateCold(ctx);
        Date dateFin = new Date();
        try {
            dateFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ultimateDateCold);
            long seconds = date.getTime() - dateFin.getTime();
            long minutesTranscurridos = seconds/60;
            if(minutesTranscurridos< minMinutesAfected){
                resp= multMin+ (minutesTranscurridos * ((multMax-multMin)/ minMinutesAfected));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resp;
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
