package in.nic.bih.cropcutting.activity.Utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;

import android.text.TextPaint;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.nic.bih.cropcutting.R;

public class Utilitties
{


    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() == true);
    }

    public static Bitmap GenerateThumbnail(Bitmap imageBitmap,
                                           int THUMBNAIL_HEIGHT, int THUMBNAIL_WIDTH) {

        Float width = new Float(imageBitmap.getWidth());
        Float height = new Float(imageBitmap.getHeight());
        Float ratio = width / height;
        Bitmap CompressedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                (int) (THUMBNAIL_HEIGHT * ratio), THUMBNAIL_HEIGHT, false);

        return CompressedBitmap;
    }



    public static Bitmap DrawText(Activity activity, Bitmap mBitmap, String displaytext1,
                                  String displaytext2, String displaytext3, String displaytext4) {
        Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        // create a canvas on which to draw
        Canvas canvas = new Canvas(bmOverlay);




        Paint paint = new Paint();
//
//        paint.setColor(activity.getResources().getColor(R.color.imgtxtColor));
//        Path mPath = new Path();
//        RectF mRectF = new RectF(20, 20, 40, 40);
//        mPath.addRect(mRectF, Path.Direction.CW);
//        paint.setStrokeWidth(20);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(mPath, paint);

        int margin = 5;


        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(45);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setFakeBoldText(false);
        paint.setShadowLayer(1, 0, 0, Color.BLACK);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        // if the background image is defined in main.xml, omit this line

        // set the bitmap into the ImageView
       // canvas.drawTextOnPath(displaytext3+"\n"+displaytext4, mPath, 5, 5, paint);
        Paint.FontMetrics fm = new Paint.FontMetrics();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(12 - margin, 1000+ fm.top - margin,
                5 + paint.measureText(displaytext4) + margin, 1270 + fm.bottom
                        + margin, paint);


        paint.setColor(activity.getResources().getColor(R.color.holo_red_dark));
        canvas.drawText(displaytext1, 10, mBitmap.getHeight() - 100, paint);
        canvas.drawText(displaytext2, 10, mBitmap.getHeight() - 50, paint);

        canvas.drawText(displaytext3, 10, mBitmap.getHeight() - 150, paint);

        canvas.drawText(displaytext4, 10, mBitmap.getHeight() - 200, paint);
        return bmOverlay;

    }


    private @NonNull
    Rect getTextBackgroundSize(float x, float y, @NonNull String text, @NonNull TextPaint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float halfTextLength = paint.measureText(text) / 2 + 5;
        return new Rect((int) (x - halfTextLength), (int) (y + fontMetrics.top), (int) (x + halfTextLength), (int) (y + fontMetrics.bottom));
    }

    public static void setActionBarBackground(Activity activity) {
        ActionBar actionBar;
        actionBar = activity.getActionBar();
        Resources res = activity.getResources();
        //Drawable drawable = res.getDrawable(R.drawable.digitallogo2);
        // actionBar.setBackgroundDrawable(drawable);
    }


    public static void setStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {

            Window window = activity.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(Color.parseColor("#1565a9"));
        }
    }

    public static boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public static void displayPromptForEnablingGPS(final Activity activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Do you want open GPS setting?";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                                activity.finish();
                            }
                        });
        builder.create().show();
    }
    public static String getDateString() {
      //  SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

        String newDateStr = postFormater.format(Calendar.getInstance()
                .getTime());
        return newDateStr;


    }
    public static String getDateString(String Formats) {
        SimpleDateFormat postFormater = new SimpleDateFormat(Formats);

        String newDateStr = postFormater.format(Calendar.getInstance()
                .getTime());
        return newDateStr;
    }

    public static String getCurrentDateWithTime() throws ParseException {

      //  SimpleDateFormat f = new SimpleDateFormat("MMM d,yyyy HH:mm");
        SimpleDateFormat f = new SimpleDateFormat("MMM d,yyyy");
        Date date = null;
        date = f.parse(getDateString());
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d,yyyy");
        String dateString = formatter.format(date);
        return dateString;
    }



    public static String getCurrentDate()
    {
        Calendar cal= Calendar.getInstance();
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        month=month+1;

        int h=cal.get(Calendar.HOUR);
        int m=cal.get(Calendar.MINUTE);
        int s=cal.get(Calendar.SECOND);

       // String date=day+"-"+month+"-"+year;
        String date=year+"-"+month+"-"+day;
        return date;

    }

    public static void vibrate(Context context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE))
                .vibrate(100);
    }


}
