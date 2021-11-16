package in.nic.bih.cropcutting.activity.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

public class MultiplePhotoActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img1, img2, img3, img4, img_threshing;
    Button btnOk;
    Intent imageData1, imageData2, imageData3, imageData4, imageData_threshing;
    private final static int CAMERA_PIC = 99;
    int ThumbnailSize = 0;
    String PID = "0";
    DataBaseHelper dataBaseHelper;
    Double preLat, preLong, crntLat, crntLong;
    String UserId = "";


    public boolean isPictureTaken = false;
    String purpose = "";
    String keyid = "", str_img1 = "", str_img2 = "", str_img3 = "", str_img4 = "", str_img_threshing = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_photo);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        PID = getIntent().getStringExtra("index");
        UserId = PreferenceManager.getDefaultSharedPreferences(MultiplePhotoActivity.this).getString("USERID", "");
        //keyid = getIntent().getExtras().getString("KeyId");
        Log.d("duhoeh", "" + PID);
        // isOpen = getIntent().getStringExtra("isOpen");
        purpose = getIntent().getStringExtra("edited");

        if (PID == null) {
            PID = "0";
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        img1 = (ImageView) findViewById(R.id.Field_Overview);
        img2 = (ImageView) findViewById(R.id.Crop_Cut_Area);
        img3 = (ImageView) findViewById(R.id.During_Cutting);
        img4 = (ImageView) findViewById(R.id.During_Weighing);
        img_threshing = (ImageView) findViewById(R.id.During_Threshing);


        btnOk = (Button) findViewById(R.id.btn_Save);
        btnOk.setOnClickListener(this);


        if (!purpose.equalsIgnoreCase("yes"))
        //    if (!purpose.equalsIgnoreCase("isEdit"))
        {
            btnOk.setEnabled(false);
            btnOk.setBackgroundResource(R.drawable.buttonshape);
        }

        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            ThumbnailSize = displaymetrics.widthPixels / 2;
        } else {

            ThumbnailSize = displaymetrics.widthPixels / 4;
            img1.getLayoutParams().height = img2.getLayoutParams().height = img3
                    .getLayoutParams().height = ThumbnailSize = img4.getLayoutParams().height;
        }

        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();
        Cursor cursor = db
                .rawQuery("SELECT Img1,Img2,Img3,DuringThreshing_Img,Img4 FROM BasicDetails where Id =?", new String[]{String.valueOf(PID)});


        if (cursor.moveToNext()) {

            if (!cursor.isNull(0)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Img1")), Base64.DEFAULT);
                //byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo1"));
                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                if (bmp != null) {
                    img1.setImageBitmap(Utilitties.GenerateThumbnail(bmp, ThumbnailSize, ThumbnailSize));
                    //img1.setImageBitmap(Utilitties.GenerateThumbnail(null,ThumbnailSize, ThumbnailSize));
                    img1.setOnClickListener(this);
                    btnOk.setEnabled(true);
                } else {
                    img1.setOnClickListener(this);
                    img2.setEnabled(false);
                    img3.setEnabled(false);
                    img4.setEnabled(false);
                    img_threshing.setEnabled(false);
                }
            } else {
                img1.setOnClickListener(this);
                img2.setEnabled(false);
                img3.setEnabled(false);
                img4.setEnabled(false);
                img_threshing.setEnabled(false);

            }

            if (!cursor.isNull(1)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Img2")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo2"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img2.setImageBitmap(Utilitties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img2.setOnClickListener(this);
                } else {
                    img2.setOnClickListener(this);
                }

            }

            if (!cursor.isNull(2)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Img3")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img3.setImageBitmap(Utilitties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img3.setOnClickListener(this);
                } else {
                    img3.setOnClickListener(this);
                }
            }

            if (!cursor.isNull(3)) {
                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("DuringThreshing_Img")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img_threshing.setImageBitmap(Utilitties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img_threshing.setOnClickListener(this);
                    //img4.setEnabled(true);
                } else {
                    img_threshing.setOnClickListener(this);
                }

            }

            if (!cursor.isNull(4)) {

                byte[] imgData = Base64.decode(cursor.getString(cursor.getColumnIndex("Img4")), Base64.DEFAULT);
                // byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                if (bmp != null) {
                    img4.setImageBitmap(Utilitties.GenerateThumbnail(bmp,
                            ThumbnailSize, ThumbnailSize));
                    img4.setOnClickListener(this);
                } else {
                    img4.setOnClickListener(this);
                }
            }

        }
        cursor.close();
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");

//
//
//

//
//

                    //imageData.add(data);


                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            imageData1 = data;
                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img1.setScaleType(ImageView.ScaleType.FIT_XY);
                            img1.setImageBitmap(Utilitties.GenerateThumbnail(bmp,
                                    500, 500));
                            //img1.setOnClickListener(null);
                            img2.setEnabled(true);
                            img3.setEnabled(true);
                            // img4.setEnabled(true);
                            img_threshing.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonshape);
                            btnOk.setEnabled(true);
                            break;

                        case 2:
                            imageData2 = data;
                            img2.setScaleType(ImageView.ScaleType.FIT_XY);
                            img2.setImageBitmap(Utilitties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            // img2.setOnClickListener(null);
                            //img3.setEnabled(true);
                            btnOk.setEnabled(true);
                            break;

                        case 3:
                            imageData3 = data;
                            img3.setScaleType(ImageView.ScaleType.FIT_XY);
                            img3.setImageBitmap(Utilitties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img3.setOnClickListener(null);
                            //img4.setEnabled(true);
                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonshape);
                            break;

                        case 4:
                            imageData_threshing = data;
                            img_threshing.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_threshing.setImageBitmap(Utilitties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img3.setOnClickListener(null);

                            img4.setEnabled(true);

                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonshape);
                            break;

                        case 5:
                            imageData4 = data;
                            img4.setScaleType(ImageView.ScaleType.FIT_XY);
                            img4.setImageBitmap(Utilitties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img3.setOnClickListener(null);
                            //img4.setEnabled(true);

                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonshape);
                            break;


                    }


                }

        }

    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnOk)) {
            if (imageData1 == null && imageData2 == null && imageData3 == null && imageData_threshing == null && imageData4 == null && purpose.equals("isEdit")) {
                finish();
            } else {
                saveImage();

                Intent iUserHome = new Intent(MultiplePhotoActivity.this, Home.class);
                iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iUserHome);
            }
        } else {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                buildAlertMessageNoGps();
            }
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

                Intent iCamera = new Intent(MultiplePhotoActivity.this, CameraActivity.class);
                if (view.equals(img1))
                    iCamera.putExtra("KEY_PIC", "1");
                else if (view.equals(img2))
                    iCamera.putExtra("KEY_PIC", "2");
                else if (view.equals(img3))
                    iCamera.putExtra("KEY_PIC", "3");
                else if (view.equals(img_threshing))
                    iCamera.putExtra("KEY_PIC", "4");
                else if (view.equals(img4))
                    iCamera.putExtra("KEY_PIC", "5");
                iCamera.putExtra("index", PID);


                startActivityForResult(iCamera, CAMERA_PIC);
            }
        }
    }

    private void saveImage() {

        int i = 0;
        DataBaseHelper placeData = new DataBaseHelper(MultiplePhotoActivity.this);
        SQLiteDatabase db = placeData.getWritableDatabase();
        for (i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            String[] whereArgs;
            byte[] imgData;
            switch (i) {
                case 0:
                    //GPSTime
                    if (imageData1 != null) {

                        imgData = imageData1.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img1 = org.kobjects.base64.Base64.encode(imgData);
                        values.put("Img1", str_img1);
                        values.put("Latitude", String.valueOf(imageData1.getStringExtra("Lat")));
                        values.put("Longitude", String.valueOf(imageData1.getStringExtra("Lng")));
                        whereArgs = new String[]{String.valueOf(PID)};


                        long c = db.update("BasicDetails", values, "Id=?", whereArgs);
                        if (c > 0) {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 1 saved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 1 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 1:
                    if (imageData2 != null) {
                        imgData = imageData2.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img2 = org.kobjects.base64.Base64.encode(imgData);

                        values.put("Img2", str_img2);
                        whereArgs = new String[]{String.valueOf(PID)};
                        long c = db.update("BasicDetails", values, "Id=?", whereArgs);

                        if (c > 0) {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 2 saved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 2 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 2:
                    if (imageData3 != null) {
                        imgData = imageData3.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img3 = org.kobjects.base64.Base64.encode(imgData);
                        values.put("Img3", str_img3);
                        whereArgs = new String[]{String.valueOf(PID)};
                        long c = db.update("BasicDetails", values, "Id=?", whereArgs);
                        if (c > 0) {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 3 saved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 3 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 3:
                    if (imageData_threshing != null) {
                        imgData = imageData_threshing.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img_threshing = org.kobjects.base64.Base64.encode(imgData);
                        values.put("DuringThreshing_Img", str_img_threshing);
                        values.put("LatForRadius", String.valueOf(imageData_threshing.getStringExtra("Lat")));
                        values.put("LongForRadius", String.valueOf(imageData_threshing.getStringExtra("Lng")));
                        whereArgs = new String[]{String.valueOf(PID)};


                        long c = db.update("BasicDetails", values, "Id=?", whereArgs);
                        if (c > 0) {
                            Toast.makeText(MultiplePhotoActivity.this, "Threshing Image saved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MultiplePhotoActivity.this, "Threshing Image not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;

                case 4:
                    if (imageData4 != null) {
                        imgData = imageData4.getByteArrayExtra("CapturedImage");
                        getIntent().removeExtra("CapturedImage");
                        str_img4 = org.kobjects.base64.Base64.encode(imgData);
                        values.put("Img4", str_img4);


                        whereArgs = new String[]{String.valueOf(PID)};
                        long c = db.update("BasicDetails", values, "Id=?", whereArgs);
                        if (c > 0) {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 4 saved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MultiplePhotoActivity.this, "Images 4 not saved ", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
            }

        }

        db.close();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        Cursor cursor = db
                .rawQuery("SELECT Img1,Img2,Img3,DuringThreshing_Img,Img4 FROM BasicDetails where Id =?", new String[]{String.valueOf(PID)});


        if (cursor.moveToNext()) {

            if (!cursor.isNull(0)) {

                byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Img1"));
                if (imgData != null) {
                    finish();
                }
            } else {
                Toast.makeText(MultiplePhotoActivity.this, "Please capture images", Toast.LENGTH_LONG).show();
            }

        }

    }


}
