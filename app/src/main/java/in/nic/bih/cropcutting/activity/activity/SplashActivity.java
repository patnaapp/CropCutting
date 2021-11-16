package in.nic.bih.cropcutting.activity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

//import com.google.android.gms.maps.model.LatLng;
//import com.google.maps.android.SphericalUtil;

import java.io.IOException;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.Versioninfo;
import in.nic.bih.cropcutting.activity.Utilities.CommonPref;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.MarshmallowPermission;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;


//import com.google.android.gms.maps.model.MarkerOptions;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1000;
    DataBaseHelper databaseHelper;
    MarshmallowPermission permission;
    public static SharedPreferences prefs;
    Context context;
    String imei = "", version = null;
    String username = "";
    String password = "";
    DataBaseHelper dataBaseHelper;
    Context ctx;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ctx = this;

        //Database Opening
        databaseHelper = new DataBaseHelper(SplashActivity.this);
        try
        {
            databaseHelper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database");
        }
        try
        {
            databaseHelper.openDataBase();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }

        //ModifyTable();
        username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PASWORD", "");
        //getDistance();
    }

    //-------------------------------------------------
//    public void getDistance()
//    {
//        Double centreLat,centreLong,radiusLat,radiusLong;
//        //current
//        centreLat=Double.valueOf("25.62901");
//        centreLong=Double.valueOf("85.102346666665");
//
//        //previous
//        radiusLat=Double.valueOf("25.6290296");
//        radiusLong=Double.valueOf("85.1024911");
//
//
////        //current
////        centreLat=Double.valueOf("25.6290249");
////        centreLong=Double.valueOf("85.1024889");
////
////        //previous
////        radiusLat=Double.valueOf("25.628996656");
////        radiusLong=Double.valueOf("85.10243");
//
//        float[] distance = new float[2];
//
//        //Location.distanceBetween(radiusLat,radiusLong,centreLong,centreLat, distance);
//        Location.distanceBetween(centreLong,centreLat,radiusLat,radiusLong, distance);
//
//        Log.e("Distance",""+distance[0]);
//        Log.e("Distance",""+distance[1]);
//
//
//
//        Log.e("Dist",""+distance(centreLat,centreLong,radiusLat,radiusLong)/1000);
//    }
//
//    private double distance(double lat1, double lon1, double lat2, double lon2) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }
//
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }


    //-----------------------
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permission.result == -1 || permission.result == 0)
        {
            try
            {
                //imei = getimeinumber();
                checkOnline();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (permission.result == 1)
        {
          //  imei = getimeinumber();
            checkOnline();
        }
        else
        {
            finish();
        }
        super.onResume();


    }

    public String getimeinumber()
    {
        String identifier = null;
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null)
            identifier = tm.getDeviceId();
        if (identifier == null || identifier.length() == 0)
            identifier = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        return identifier;
    }

    protected void checkOnline()
    {
        // TODO Auto-generated method stub
        super.onResume();

        if (Utilitties.isOnline(SplashActivity.this) == false)
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setTitle("Alert Dialog !!!");
            ab.setMessage(Html.fromHtml("<font color=#000000>Internet Connection is not avaliable... \n Please Turn ON Network Connection \n To Turn ON Network Connection Press Yes Button Else To Exit Press Cancel Button.</font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog,int whichButton)
                {
                    GlobalVariables.isOffline = false;
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.setNegativeButton("Go Offline", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog,int whichButton)
                {
                    start();
                }
            });

            ab.show();
        }
        else
        {
            GlobalVariables.isOffline = false;
            new CheckUpdate().execute();
        }
    }


    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo>
    {
        CheckUpdate()
        {

        }

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected Versioninfo doInBackground(Void... Params)
        {
            TelephonyManager tm = null;
            String imei = null;

            permission = new MarshmallowPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE);
            if (permission.result == -1 || permission.result == 0)
            {
                try
                {
                    tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    if (tm != null) imei = tm.getDeviceId();
                }
                catch (Exception e)
                {

                }
            }

            String version = null;
            try
            {
                version = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            }
            catch (PackageManager.NameNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Versioninfo versioninfo = WebServiceHelper.CheckVersion(version);

            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo)
        {
            final AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setCancelable(false);
            if (versioninfo != null && versioninfo.isValidDevice())
            {
                CommonPref.setCheckUpdate(getApplicationContext(), System.currentTimeMillis());

                if (versioninfo.getAdminMsg().trim().length() > 0 && !versioninfo.getAdminMsg().trim().equalsIgnoreCase("anyType{}"))
                {
                    ab.setTitle(versioninfo.getAdminTitle());
                    ab.setMessage(Html.fromHtml(versioninfo.getAdminMsg()));
                    ab.setPositiveButton(getResources().getString(in.nic.bih.cropcutting.R.string.ok),new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog,int whichButton)
                        {
                            dialog.dismiss();

                            showDailog(ab, versioninfo);

                        }
                    });
                    ab.show();
                }
                else
                {
                    showDailog(ab, versioninfo);
                }
            }
            else
            {
                if (versioninfo != null)
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(in.nic.bih.cropcutting.R.string.ok),Toast.LENGTH_LONG).show();

                }
                else
                {
                    start();

                }
            }

        }
    }

    public String getappversion()
    {
        String versionCode = null;
        PackageManager manager = this.getPackageManager();
        try
        {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String packageName = info.packageName;
            versionCode = String.valueOf(info.versionCode);
            String versionName = info.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // TODO Auto-generated catch block
        }
        return versionCode;
    }

    private void showDailog(AlertDialog.Builder ab, final Versioninfo versioninfo)
    {
        if (versioninfo.isVerUpdated())
        {
            if (versioninfo.getPriority() == 0)
            {
                dothis();
            }
            else if (versioninfo.getPriority() == 1)
            {
                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());
                ab.setPositiveButton("Update",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog,int whichButton)
                    {
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                        ComponentName comp = new ComponentName("com.android.vending","com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                        launchIntent.setComponent(comp);
                        launchIntent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                        try
                        {
                            startActivity(launchIntent);
                            finish();
                        }
                        catch (android.content.ActivityNotFoundException anfe)
                        {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(versioninfo.getAppUrl())));
                            finish();
                        }
                        dialog.dismiss();
                    }
                });

                ab.setNegativeButton("Ignore",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog,int whichButton)
                    {
                        dialog.dismiss();
                        dothis();
                    }

                });

                ab.show();
            }

            else if (versioninfo.getPriority() == 2)
            {
                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());
                // ab.setMessage("Please update your App its required. Click on Update button");
                ab.setPositiveButton("Update",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog,int whichButton)
                    {

                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                        ComponentName comp = new ComponentName("com.android.vending","com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package

                        launchIntent.setComponent(comp);
                        launchIntent.setData(Uri.parse("market://details?id="+ getApplicationContext().getPackageName()));

                        try
                        {
                            startActivity(launchIntent);
                            finish();
                        }
                        catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(
                                    Intent.ACTION_VIEW, Uri
                                    .parse(versioninfo
                                            .getAppUrl())));
                            finish();
                        }

                        dialog.dismiss();
                        // finish();
                    }
                });
                ab.show();
            }
        }
        else
        {
            dothis();
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void dothis()
    {
        if (!Utilitties.isOnline(SplashActivity.this))
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setMessage(Html.fromHtml("<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.create();
            ab.show();

        }
        else
        {
            start();
        }
    }


    private void start()
    {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (username.equals(""))
                {
                    Intent i = new Intent(SplashActivity.this, Login.class);
                    startActivity(i);
                }
                else
                    {
                    //Intent i = new Intent(SplashActivity.this, Home.class);
                    Intent i = new Intent(SplashActivity.this, PreHomeActivity.class);
                    startActivity(i);

                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    /*private static int SPLASH_TIME_OUT = 1000;
    DataBaseHelper databaseHelper;
    MarshmallowPermission permission;
    public static SharedPreferences prefs;
    Context context;
    String imei = "", version = null;
    Context ctx;

    String username = "";
    String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ctx = this;
        //Database Opening
        databaseHelper = new DataBaseHelper(SplashActivity.this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            databaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PASWORD", "");

        checkOnline();
        String version = getAppVersion();
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    private void dothis() {

        if (!Utilitties.isOnline(SplashActivity.this)) {

            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setMessage(Html.fromHtml(
                    "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create();
            ab.show();

        } else {


          *//*  Intent i = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(i);
            finish();*//*
            start();

        }
    }


    private void start() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (username.equals("")) {
                    Intent i = new Intent(SplashActivity.this, Login.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, Home.class);
                    startActivity(i);

                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    public String getAppVersion()
    {
        String version="";
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    private void checkAppUseMode()
    {
        if(!Utilitties.isGPSEnabled(SplashActivity.this)){
            Utilitties.displayPromptForEnablingGPS(SplashActivity.this);
        }else {
            boolean net = false;

            permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
            if (permission.result == -1 || permission.result == 0)
                net = Utilitties.isOnline(SplashActivity.this);
            if (net) {

                if (!prefs.getBoolean("firstTime", false)) {


                    //start();
                    //if(!prefs.getBoolean("fieldDownloaded",false))
                    //	 new loadAppData().execute("");
                    //else start();


                } else {
                    new CheckUpdate().execute();
                    //start();
                }
            } else if (!prefs.getBoolean("firstTime", false)) {

                final AlertDialog alertDialog = new AlertDialog.Builder(
                        SplashActivity.this).create();
                alertDialog.setTitle(getResources().getString(R.string.no_internet_connection));
                alertDialog.setMessage(getResources().getString(R.string.enable_internet_for_firsttime));
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed


                        GlobalVariables.isOffline = false;
                        Intent I = new Intent(
                                android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(I);
                        alertDialog.cancel();


                        //start();
                    }
                });

                alertDialog.show();
            } else {

                if (prefs.getBoolean("firstTime", false))
                    checkOnline();


            }
        }
    }


    private void showDailog(AlertDialog.Builder ab, final Versioninfo versioninfo) {

        if (versioninfo.isVerUpdated()) {
            Log.d("Resultgfg", "" + versioninfo);

            if (versioninfo.getPriority() == 0) {

                start();
            } else if (versioninfo.getPriority() == 1) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());

                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

								*//*Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri.parse(versioninfo
										.getAppUrl()));

								startActivity(myWebLink);

								dialog.dismiss();
								finish();
							}*//*

                                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                                ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                // name
                                // and
                                // activity
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(versioninfo.getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }


                        });
                ab.setNegativeButton(getResources().getString(R.string.ignore),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                                start();
                            }

                        });

                ab.show();

            } else if (versioninfo.getPriority() == 2) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());
                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

								*//*Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri.parse(versioninfo
										.getAppUrl()));

								startActivity(myWebLink);

								dialog.dismiss();*//*

                                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                                ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                // name
                                // and
                                // activity
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri.parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                                // finish();
                            }
                        });
                ab.show();
            }
        } else {

            start();
        }

    }

    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {


        CheckUpdate() {

        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Versioninfo doInBackground(Void... Params) {

            TelephonyManager tm = null;
            String imei = null;

            permission=new MarshmallowPermission(SplashActivity.this,Manifest.permission.READ_PHONE_STATE);
            if(permission.result==-1 || permission.result==0)
            {
                try
                {
                    tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    if(tm!=null) imei = tm.getDeviceId();
                }catch(Exception e){}
            }

            String version = null;
            try {
                version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Versioninfo versioninfo = WebServiceHelper.CheckVersion(
                    version);

            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo) {

            final AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setCancelable(false);
            if (versioninfo != null && versioninfo.isValidDevice()) {

                CommonPref.setCheckUpdate(getApplicationContext(),
                        System.currentTimeMillis());

                if (versioninfo.getAdminMsg().trim().length() > 0
                        && !versioninfo.getAdminMsg().trim()
                        .equalsIgnoreCase("anyType{}")) {

                    ab.setTitle(versioninfo.getAdminTitle());
                    ab.setMessage(Html.fromHtml(versioninfo.getAdminMsg()));
                    ab.setPositiveButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.dismiss();

                                    showDailog(ab, versioninfo);

                                }
                            });
                    ab.show();
                } else {
                    showDailog(ab, versioninfo);
                }
            } else {
                if (versioninfo != null) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.wrong_device_text),
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    start();

                }
            }

        }
    }

    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();

        boolean net=false;

        MarshmallowPermission permission=new MarshmallowPermission(this,Manifest.permission.READ_PHONE_STATE);
        if(permission.result==-1 || permission.result==0)  net= Utilitties.isOnline(SplashActivity.this);

        if (!net) {

            AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setMessage(getResources().getString(R.string.no_internet_connection));
            ab.setPositiveButton(getResources().getString(R.string.turnon_internet),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int whichButton) {
                            GlobalVariables.isOffline = false;
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
            ab.setNegativeButton(getResources().getString(R.string.continue_offline),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            GlobalVariables.isOffline = true;
                            start();
                        }
                    });


            ab.show();

        } else {

            GlobalVariables.isOffline = false;
            new CheckUpdate().execute();

        }
    }

    *//*protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();


        if (Utilitties.isOnline(SplashActivity.this) == false) {

            AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setTitle("Alert Dialog !!!");
            ab.setMessage(Html.fromHtml("<font color=#000000>Internet Connection is not avaliable... \n Please Turn ON Network Connection \n To Turn ON Network Connection Press Yes Button Else To Exit Press Cancel Button.</font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int whichButton) {
                    GlobalVariables.isOffline = false;
                    Intent I = new Intent(
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.setNegativeButton("Go Offline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int whichButton) {

                           *//**//* GlobalVariables.isOffline = true;
                            Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();*//**//*
                    start();
                }
            });


            ab.show();

        } else {

            GlobalVariables.isOffline = false;
            new CheckUpdate().execute();
        }
    }*//*

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        checkAppUseMode();
        checkOnline();
    }*/
//    public void ModifyTable() {
//        if(isColumnExists("BasicDetails","Type_of_land_Name")==false) {
//            AlterTable("BasicDetails", "Type_of_land_Name");
//        }
//        if(isColumnExists("BasicDetails","Weather_condition_during_name")==false) {
//            AlterTable("BasicDetails", "Weather_condition_during_name");
//        }
//        if(isColumnExists("BasicDetails","sourceofseed_name")==false) {
//            AlterTable("BasicDetails", "sourceofseed_name");
//        }
//        if(isColumnExists("BasicDetails","tymanure_name")==false) {
//            AlterTable("BasicDetails", "tymanure_name");
//        }
//        if(isColumnExists("BasicDetails","systemcultivation_name")==false) {
//            AlterTable("BasicDetails", "systemcultivation_name");
//        }
//        if(isColumnExists("BasicDetails","varitiescrop_name")==false) {
//            AlterTable("BasicDetails", "varitiescrop_name");
//        }
//        if(isColumnExists("BasicDetails","CropName")==false) {
//            AlterTable("BasicDetails", "CropName");
//        }
//    }
//    public boolean isColumnExists (String table, String column) {
//        db = dataBaseHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String name = cursor.getString(cursor.getColumnIndex("name"));
//                if (column.equalsIgnoreCase(name)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//    public void AlterTable(String tableName,String columnName)
//    {
//        db = dataBaseHelper.getReadableDatabase();
//
//        try{
//
//            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
//            Log.e("ALTER Done",tableName +"-"+ columnName);
//        }
//        catch (Exception e)
//        {
//            Log.e("ALTER Failed",tableName +"-"+ columnName);
//        }
//    }
}