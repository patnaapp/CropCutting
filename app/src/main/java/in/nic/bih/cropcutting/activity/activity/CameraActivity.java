package in.nic.bih.cropcutting.activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.Utilities.CameraPreview;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.MarshmallowPermission;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;


@SuppressLint("NewApi")
public class CameraActivity extends Activity {
    MarshmallowPermission permission;
    Button btnCamType;
    Button takePhoto;
    ProgressBar progress_finding_location;
    boolean init;
    boolean backCam = true;
    DataBaseHelper dataBaseHelper;
    String UserId = "", rowid = "";
    int camType;
    FrameLayout preview;
    //  GoogleApiClient googleApiClient, mGoogleApiClint;
    //  private GoogleMap mMap;
    static double latitude = 0.0, longitude = 0.0;
    Button buttonrotate, btnSave;
    public static int cameraid = 0;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //  private LocationRequest mLocationRequest;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 300; // 3 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    Bitmap bMapRotate;
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        init = false;
        permission = new MarshmallowPermission(this, Manifest.permission.CAMERA);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera(camType);
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera(camType);
        }

        permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera(camType);
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera(camType);
        }

        super.onResume();

    }


    private Camera mCamera;
    private CameraPreview mPreview;

    static Location LastLocation = null;
    LocationManager mlocManager = null;


    AlertDialog.Builder alert;


    private final int UPDATE_ADDRESS = 1;
    private final int UPDATE_LATLNG = 2;
    private static final String TAG = "MyActivity";
    private static byte[] CompressedImageByteArray;
    private static Bitmap CompressedImage;
    private boolean isTimerStarted = false;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        takePhoto = (Button) findViewById(R.id.btnCapture);
        btnCamType = (Button) findViewById(R.id.btnCamType);
        // buttonrotate=(Button)findViewById(R.id.buttonrotate);
        btnSave = (Button) findViewById(R.id.btnSave);
        progress_finding_location = (ProgressBar) findViewById(R.id.progress_finding_location);

        dataBaseHelper = new DataBaseHelper(CameraActivity.this);
        UserId = PreferenceManager.getDefaultSharedPreferences(CameraActivity.this).getString("USERID", "");

        rowid = getIntent().getExtras().getString("index");

        camType = CameraInfo.CAMERA_FACING_BACK;
        preview = (FrameLayout) findViewById(R.id.camera_preview);


        btnCamType.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mCamera != null) {
                    mCamera.stopPreview();

                }


                if (camType == CameraInfo.CAMERA_FACING_BACK) {
                    camType = CameraInfo.CAMERA_FACING_FRONT;

                } else {
                    camType = CameraInfo.CAMERA_FACING_BACK;

                }

                preview.removeAllViews();

                initializeCamera(camType);


            }

        });

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmapBitmap2 = bMapRotate;


                Date d = new Date(GlobalVariables.glocation.getTime());
                String dat = d.toLocaleString();
                Bitmap bitmap2 = Utilitties.DrawText(CameraActivity.this, bmapBitmap2, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                        , "Accuracy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "GpsTime:" + dat);
                latitude = GlobalVariables.glocation.getLatitude();
                longitude = GlobalVariables.glocation.getLongitude();
                setCameraImage(Utilitties.GenerateThumbnail(bitmap2, 500, 500));
                // new CustomeDialogClass(CameraActivity.this,bmapBitmap2,Integer.parseInt(getIntent().getStringExtra("KEY_PIC"))).show();
            }
        });
    }

    private void locationManager() {
//        if (GlobalVariables.glocation == null) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            takePhoto.setEnabled(false);
//            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, mlistener);
//            mlocManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER, 5000, 10, mlistener);
//            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
//            //  mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
//
//            mlocManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER, 5000, 10, mlistener);
//        } else {
//            String keypic = getIntent().getStringExtra("KEY_PIC");
//            Log.e("KEY_PICLM",keypic);
//            takePhoto.setEnabled(true);
//            progress_finding_location.setVisibility(View.GONE);
//            takePhoto.setText(" Take Photo ");
//        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        takePhoto.setEnabled(false);
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, mlistener);
        mlocManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 1, mlistener);
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
        //  mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);

        mlocManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000, 1, mlistener);

    }

    private void initializeCamera(int camType) {

        init = true;
        chronometer = (Chronometer) findViewById(R.id.chronometer1);
        isTimerStarted = false;

        mCamera = getCameraInstance(camType);
        Parameters param;
        if (mCamera != null) {
            param = mCamera.getParameters();


            List<Size> sizes = param.getSupportedPictureSizes();
            int iTarget = 0;
            for (int i = 0; i < sizes.size(); i++) {
                Size size = sizes.get(i);

                if (size.width >= 1024 && size.width <= 1280) {
                    iTarget = i;
                    break;
                } else {
                    if (size.width < 1024) {
                        iTarget = i;

                    }
                }

            }
            param.setJpegQuality(100);

            param.setPictureSize(sizes.get(iTarget).width,
                    sizes.get(iTarget).height);
            mCamera.setParameters(param);
            alert = new AlertDialog.Builder(this);
            Display getOrient = getWindowManager().getDefaultDisplay();

            int rotation = getOrient.getRotation();

            switch (rotation) {
                case Surface.ROTATION_0:
                    mCamera.setDisplayOrientation(90);
                    break;
                case Surface.ROTATION_90:

                    break;
                case Surface.ROTATION_180:
                    mCamera.setDisplayOrientation(0);

                    break;
                case Surface.ROTATION_270:
                    mCamera.setDisplayOrientation(90);
                    break;
                default:
                    break;
            }
            try {

                mPreview = new CameraPreview(this, mCamera);
                preview.addView(mPreview);


            } catch (Exception e) {
                finish();
            }
            locationManager();
        }

    }

    public String setCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = mlocManager.getBestProvider(criteria, true);
        return provider;
    }

    public void startTimer() {

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        isTimerStarted = true;

    }


    public static Camera getCameraInstance(int cameraType) {
        // Camera c = null;
        try {

            int numberOfCameras = Camera.getNumberOfCameras();
            int cameraId = 0;
            for (int i = 0; i < numberOfCameras; i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == cameraType) {
                    // Log.d(DEBUG_TAG, "Camera found");
                    cameraId = i;
                    break;

                }
            }

            return Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            return null;
        }
    }

    PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {

                Log.e("pic callback", "Yes");
                Log.d(TAG, "Start");
                Bitmap bmp = BitmapFactory
                        .decodeByteArray(data, 0, data.length);

                Matrix mat = new Matrix();
                if (camType == CameraInfo.CAMERA_FACING_FRONT) {
                    mat.postRotate(-90);

                } else mat.postRotate(90);

                bMapRotate = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);
                takePhoto.setVisibility(View.GONE);
                Bitmap bmapBitmap2 = bMapRotate;

                Date d = new Date(GlobalVariables.glocation.getTime());
                String dat = d.toLocaleString();
                Bitmap bitmap2 = Utilitties.DrawText(CameraActivity.this, bmapBitmap2, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                        , "Accuracy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "GpsTime:" + dat);
                latitude = GlobalVariables.glocation.getLatitude();

                longitude = GlobalVariables.glocation.getLongitude();

                setCameraImage(Utilitties.GenerateThumbnail(bitmap2, 500, 500));
                //buttonrotate.setVisibility(View.VISIBLE);
                //btnSave.setVisibility(View.VISIBLE);
                // onrotate(bMapRotate,2);
                //changing
                /*Bitmap bmapBitmap2 = bMapRotate;
                Date d = new Date(GlobalVariables.glocation.getTime());
                String dat = d.toLocaleString();
                Bitmap bitmap2 = Utiilties.DrawText(CameraActivity.this, bmapBitmap2, "Lat:" + Double.toString(GlobalVariables.glocation.getLatitude()), "Long:" + Double.toString(GlobalVariables.glocation.getLongitude())
                        , "Accurecy:" + Float.toString(GlobalVariables.glocation.getAccuracy()), "GpsTime:" + dat);
                latitude=GlobalVariables.glocation.getLatitude();
                longitude=GlobalVariables.glocation.getLongitude();
                setCameraImage(Utiilties.GenerateThumbnail(bitmap2, 500, 500));*/
                // new CustomeDialogClass(CameraActivity.this,bmapBitmap2,Integer.parseInt(getIntent().getStringExtra("KEY_PIC"))).show();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    };

    ShutterCallback shutterCallback = new ShutterCallback() {
        @Override
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };
    /**
     * Handles data for raw picture
     */
    PictureCallback rawCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    public void onCaptureClick(View view) {
        // System.gc();

        if (mCamera != null) {
            mCamera.takePicture(shutterCallback, rawCallback, mPicture);
        }


        Log.e("pic taken", "Yes");

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        // mCamera.takePicture(null, null, mPicture);

    }

    public void onrotate(Bitmap bitmap, int i) {
        // System.gc();

        if (i == 1) {
            Bitmap bmp = bitmap;
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            mCamera.setDisplayOrientation(90);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            mPreview = new CameraPreview(this, mCamera);
            preview.addView(mPreview);
            bMapRotate = bmp;
        } else if (i == 2) {
            Bitmap bmp = bitmap;
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            mCamera.setDisplayOrientation(270);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            mPreview = new CameraPreview(this, mCamera);
            preview.addView(mPreview);
            bMapRotate = bmp;
        }

    }

    private void setCameraImage(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

        byte[] byte_arr = stream.toByteArray();
        CompressedImageByteArray = byte_arr;

        bitmap.recycle();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("CapturedImage", CompressedImageByteArray);
        returnIntent.putExtra("Lat", new DecimalFormat("#.0000000")
                .format(GlobalVariables.glocation.getLatitude()));
        returnIntent.putExtra("Lng", new DecimalFormat("#.0000000")
                .format(GlobalVariables.glocation.getLongitude()));
        try {
            returnIntent.putExtra("CapturedTime", Utilitties.getCurrentDateWithTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d = new Date(GlobalVariables.glocation.getTime());
        String dat = d.toLocaleString();
        returnIntent.putExtra("GPSTime", dat);
        returnIntent.putExtra("KEY_PIC",
                Integer.parseInt(getIntent().getStringExtra("KEY_PIC")));
        // returnIntent.putExtra("ss", 0);
        setResult(RESULT_OK, returnIntent);
        Log.e("Set camera image", "Yes");

        finish();

    }

    public static byte[] getCompressedImage() {
        return CompressedImageByteArray;
    }

    public static void setCompressedImage(byte[] compressedImageByteArray) {
        CompressedImageByteArray = compressedImageByteArray;
    }

    public static Bitmap getCompressedBitmap() {
        return CompressedImage;
    }

    public static void setCompressedBitmap(Bitmap compressedImage) {
        CompressedImage = compressedImage;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

    }


    private boolean checkLocationPermission() {
        if (!hasLocationPermission()) {
            Log.e("Tuts+", "Does not have location permission granted");
            requestLocationPermission();
            return false;
        }

        return true;
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private final static int REQUEST_PERMISSION_RESULT_CODE = 42;

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                CameraActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSION_RESULT_CODE);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case UPDATE_ADDRESS:
                case UPDATE_LATLNG:
                    String[] LatLon = ((String) msg.obj).split("-");
                    TextView tv_Lat = (TextView) findViewById(R.id.tvLat);
                    TextView tv_Lon = (TextView) findViewById(R.id.tvLon);

                    tv_Lat.setText("" + latitude);
                    tv_Lon.setText("" + longitude);
                    Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);

                    if (!isTimerStarted) {
                        startTimer();
                    }

                    break;
            }
        }
    };

    private void updateUILocation(Location location) {

        Message.obtain(
                mHandler,
                UPDATE_LATLNG,
                new DecimalFormat("#.0000000").format(location.getLatitude())
                        + "-"
                        + new DecimalFormat("#.0000000").format(location
                        .getLongitude()) + "-" + location.getAccuracy() + "-" + location.getTime())
                .sendToTarget();

    }

    private final LocationListener mlistener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Float preLat = null, preLong = null, crntLat, crntLong;

            if (Utilitties.isGPSEnabled(CameraActivity.this)) {
                LastLocation = location;
//                GlobalVariables.glocation = location;
//                updateUILocation(GlobalVariables.glocation);
                //  if (getIntent().getStringExtra("KEY_PIC").equals("1")) {

                if (location.getLatitude() > 0.0) {
                    GlobalVariables.glocation = location;
                    updateUILocation(GlobalVariables.glocation);
                    //long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    if (location.getAccuracy() < 200) {

//                        String keypic = getIntent().getStringExtra("KEY_PIC");
//                        if(getIntent().getStringExtra("KEY_PIC").equalsIgnoreCase("5"))
//                        {
//                            Log.e("KEY_PICIF",keypic);
//                            ArrayList data = getLatLong(UserId, rowid);
//                            if (data.size()>0){
//                                preLat = Float.parseFloat(data.get(0).toString());
//                                preLong =Float.parseFloat(data.get(1).toString());
//                                Log.e("KEY_PICLat1",""+preLat);
//                                Log.e("KEY_PICLong1",""+preLong);
//                                Float lat2=Float.parseFloat(String.valueOf(location.getLatitude()));
//                                Float long2 = Float.parseFloat(String.valueOf(location.getLongitude()));
//
//                                //Float lat2=Float.parseFloat("23.369900");
//                                //Float long2 =Float.parseFloat( "85.325280");
//
//                                Log.e("KEY_PICLat2",""+lat2);
//                                Log.e("KEY_PICLong2",""+long2);
//                                try{
//                                    float distance= (float) distance(preLat,preLong,lat2,long2) * 1000;
//                                    Log.e("KEY_PICDist",""+distance);
//                                    if(distance<=30)
//                                    {
//                                        takePhoto.setText(" Take Photo ");
//                                        progress_finding_location.setVisibility(View.GONE);
//                                        takePhoto.setEnabled(true);
//                                    }
//                                    else {
//                                        takePhoto.setText("Not In Range To Capture Image");
//                                        progress_finding_location.setVisibility(View.VISIBLE);
//                                        takePhoto.setEnabled(false);
//                                        //  Toast.makeText(CameraActivity.this, "Not In Range To Capture Image", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                                catch (Exception e)
//                                {
//                                    e.printStackTrace();
//                                }
//                            }
//                            else
//                            {
//                                Toast.makeText(CameraActivity.this, "No previous lat - long found", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else
//                        {
                        // Log.e("KEY_PICElse",keypic);
                        takePhoto.setText(" Take Photo ");
                        progress_finding_location.setVisibility(View.GONE);
                        takePhoto.setEnabled(true);
                        // }


                    } else {

                        takePhoto.setText(" Wait for GPS to Stable ");
                        progress_finding_location.setVisibility(View.VISIBLE);
                        takePhoto.setEnabled(false);

                    }
                }

            }
//            else {
//
//                Message.obtain(
//                        mHandler,
//                        UPDATE_LATLNG,
//                        new DecimalFormat("#.0000000").format(location.getLatitude())
//                                + "-"
//                                + new DecimalFormat("#.0000000").format(location
//                                .getLongitude()) + "-" + location.getAccuracy() + "-" + location.getTime())
//                        .sendToTarget();
//                takePhoto.setText(" Take Photo ");
//                progress_finding_location.setVisibility(View.GONE);
//            }

        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


    }
    };

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public ArrayList getLatLong(String uid, String rowid) {
        Cursor cur = dataBaseHelper.getPrevLatLong(uid, rowid);
        String prevLat = "0.0", prevLong = "0.0";
        ArrayList data = new ArrayList();
        try {

            while (cur.moveToNext()) {

                prevLat = cur.getString(cur.getColumnIndex("LatForRadius"));
                prevLong = cur.getString(cur.getColumnIndex("LongForRadius"));
                Log.e("LatForRadius", prevLat);
                Log.e("LongForRadius", prevLong);
                data.add(prevLat);
                data.add(prevLong);
            }

            cur.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return data;

    }


    @Override
    public void onDestroy()
      {
        super.onDestroy();

        Runtime.getRuntime().gc();
    }

    //Get Distance
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}