package caja.alanger.cpucooler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

public class SharedPreferencesManager {

    private static String TAG = SharedPreferencesManager.class.getSimpleName();
    private static String namePreferences_userdata = "data";

    //data user
    private static String user_ultimateColled = "id";

    public static boolean saveCold(Context ctx, String datetime){
        boolean flag = false;
        try{
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(user_ultimateColled,datetime);
            flag = editor.commit();
        }catch (Exception e){
            Log.d(TAG,"saveUser:"+e.toString());
            Toast.makeText(ctx,"saveUser:"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static String getUltimateCold(Context ctx){
        String user = "";
            try {
                SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
                if(
                        preferences.contains(user_ultimateColled)
                ){
                    user = (preferences.getString(user_ultimateColled,""));
                }
            }catch (Exception e) {
                Log.d(TAG,"getUser:" + e.toString()) ;
                Toast.makeText(ctx, "getUser:" + e.toString(), Toast.LENGTH_LONG).show();
            }
        return user;
    }

    public static boolean deleteUser(Context ctx){
        boolean flag = false;
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            flag = editor.commit(); // commit changes
        }catch (Exception e) {
            Toast.makeText(ctx, "deleteUser:" + e.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG,"deleteUser: "+e.toString());
        }
        return flag;
    }

    public static Map<String,?> getMapUser(Context ctx){
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            Map<String,?> map = preferences.getAll();
            return  map;
        }catch (Exception e) {
            Toast.makeText(ctx, "getMapUser:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

}
