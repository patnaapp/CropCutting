package in.nic.bih.cropcutting.activity.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import in.nic.bih.cropcutting.activity.Entity.UserLogin;

public class CommonPref
{

    public static void setCheckUpdate(Context context, long dateTime) {

        String key = "_CheckUpdate";

        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();


        dateTime=dateTime+1*3600000;
        editor.putLong("LastVisitedDate", dateTime);

        editor.commit();

    }

    public static UserLogin getUserDetails(Context context) {

        String key = "_USER_DETAILS";
        UserLogin userInfo = new UserLogin();
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        userInfo.setUserId(prefs.getString("UserId", ""));
        userInfo.setUserName(prefs.getString("Username", ""));
        userInfo.setPassword(prefs.getString("Password", ""));
        userInfo.setRole(prefs.getString("Role", ""));
        userInfo.setImei(prefs.getString("Imei", ""));
        userInfo.setDistname(prefs.getString("distName", ""));
        userInfo.setDistcode(prefs.getString("Distcode", ""));
        userInfo.setBlkname(prefs.getString("BlockName", ""));
        userInfo.setBlkcode(prefs.getString("Blockcode", ""));
        return userInfo;
    }
}
