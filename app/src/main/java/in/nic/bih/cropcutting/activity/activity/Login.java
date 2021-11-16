package in.nic.bih.cropcutting.activity.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Login extends AppCompatActivity
{
    private ProgressDialog dialog;
    EditText et_username, et_password;
    String username, password, imeicode;
    private static String imei;
    DataBaseHelper databaseHelper;
    Button btn_login;
    String Userid = "";
    String userName = "";
    String Password = "";
    UserLogin userLogin;
    String version;
    SQLiteDatabase db;
    TextView Signup, forget_password;

    TextView tv_imei;
    TelephonyManager tm;
    Context context;
    boolean doubleBackToExitPressedOnce = false;
    Activity activity;
    private static final int REQUEST_READ_PHONE_STATE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.et_User_Id);
        et_password = (EditText) findViewById(R.id.et_password_login);
        btn_login = (Button) findViewById(R.id.btn_Log_In);
        Signup = (TextView) findViewById(R.id.tv_signup);
        forget_password = (TextView) findViewById(R.id.tv_forgetpasword);
        dialog = new ProgressDialog(Login.this);

        databaseHelper = new DataBaseHelper(getApplicationContext());
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
        getVersion();
        ModifyTable();
        userName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        Password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PASWORD", "");

        userLogin = databaseHelper.getDetail(userName);
        final String name = userLogin.getUserId();


        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetValue();

                        if (ValidateData()) {
                            new LoginTask().execute();
                        }
                    }

                });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUpActivity.class);
                startActivity(i);

            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Custom_forgot_password.class);
                startActivity(i);

            }
        });

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        return true;
                    }
                }
                return false;
            }
        });

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    SetValue();
                    if (ValidateData()) {
                        new LoginTask().execute();
                    }

                    handled = true;
                }
                return handled;
            }
        });

    }

    private void SetValue() {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getVersion() {

        try {

            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.txtVersion);
            tv.setText("App Version  " + version);


        } catch (PackageManager.NameNotFoundException e) {

        }

    }

    private boolean ValidateData() {
        View focusView = null;
        boolean validate = true;
        if (et_username.getText().toString().equals("")) {
            focusView = et_username;
            Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_LONG).show();
            validate = false;
        } else if (et_password.getText().toString().equals("")) {
            focusView = et_password;
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            validate = false;
        }

        return validate;
    }

    private class LoginTask extends AsyncTask<String, Void, UserLogin> {


        protected void onPreExecute() {


            dialog.show();
            dialog.setMessage("Authenticating...");
        }

        @Override
        protected UserLogin doInBackground(String... params) {


            return WebServiceHelper.Login_Farmer(username, password);
        }

        @Override
        protected void onPostExecute(UserLogin result) {
            super.onPostExecute(result);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            long c = 0;
            if (result != null) {
                if (result.isAthenticate()) {
                    String userid = result.getUserId();
                    String userpss = result.getPassword();

                    Toast.makeText(getApplicationContext(), "Login Successful ", Toast.LENGTH_LONG).show();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USERID", userid).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PASWORD", userpss).commit();
                    databaseHelper.insertUserDetail(result);

                    Intent intent = new Intent(getApplicationContext(), PreHomeActivity.class);
                    startActivity(intent);

                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid UserId And Password ", Toast.LENGTH_LONG).show();
                }

                Log.d("ResultValue", "" + result);

            } else {
                Toast.makeText(getApplicationContext(), "Please Try Later.. ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ModifyTable() {


        if (isColumnExists("BasicDetails", "Latitude") == false) {
            AlterTable("BasicDetails", "Latitude");
        }
        if (isColumnExists("BasicDetails", "Agriculture_year") == false) {
            AlterTable("BasicDetails", "Agriculture_year");
        }
        if (isColumnExists("BasicDetails", "Season") == false) {
            AlterTable("BasicDetails", "Season");
        }
        if (isColumnExists("BasicDetails", "Crop") == false) {
            AlterTable("BasicDetails", "Crop");
        }
        if (isColumnExists("BasicDetails", "District") == false) {
            AlterTable("BasicDetails", "District");
        }
        if (isColumnExists("BasicDetails", "Block") == false) {
            AlterTable("BasicDetails", "Block");
        }
        if (isColumnExists("BasicDetails", "Panchayat") == false) {
            AlterTable("BasicDetails", "Panchayat");
        }

        if (isColumnExists("BasicDetails", "Name_of_selected_village") == false) {
            AlterTable("BasicDetails", "Name_of_selected_village");
        }
        if (isColumnExists("BasicDetails", "Highest_plot_no_of_Panchayat") == false) {
            AlterTable("BasicDetails", "Highest_plot_no_of_Panchayat");
        }
        if (isColumnExists("BasicDetails", "Selected_Survey_No_Khesra") == false) {
            AlterTable("BasicDetails", "Selected_Survey_No_Khesra");
        }
        if (isColumnExists("BasicDetails", "Farmer_Name") == false) {
            AlterTable("BasicDetails", "Farmer_Name");
        }
        if (isColumnExists("BasicDetails", "Operational_Size_holding") == false) {
            AlterTable("BasicDetails", "Operational_Size_holding");
        }
        if (isColumnExists("BasicDetails", "Area_of_crop") == false) {
            AlterTable("BasicDetails", "Area_of_crop");
        }
        if (isColumnExists("BasicDetails", "System_of_cutivation") == false) {
            AlterTable("BasicDetails", "System_of_cutivation");
        }
        if (isColumnExists("BasicDetails", "Type_Of_crop_varities") == false) {
            AlterTable("BasicDetails", "Type_Of_crop_varities");
        }
        if (isColumnExists("BasicDetails", "Irrigation_source") == false) {
            AlterTable("BasicDetails", "Irrigation_source");
        }
        if (isColumnExists("BasicDetails", "Type_of_manure") == false) {
            AlterTable("BasicDetails", "Type_of_manure");
        }
        if (isColumnExists("BasicDetails", "Quantity_of_used_manure") == false) {
            AlterTable("BasicDetails", "Quantity_of_used_manure");
        }
        if (isColumnExists("BasicDetails", "Source_of_seed") == false) {
            AlterTable("BasicDetails", "Source_of_seed");
        }
        if (isColumnExists("BasicDetails", "Quantity_of_used_seed") == false) {
            AlterTable("BasicDetails", "Quantity_of_used_seed");
        }
        if (isColumnExists("BasicDetails", "Weather_condition_during") == false) {
            AlterTable("BasicDetails", "Weather_condition_during");
        }
        if (isColumnExists("BasicDetails", "Extend_of_damage") == false) {
            AlterTable("BasicDetails", "Extend_of_damage");
        }
        if (isColumnExists("BasicDetails", "Remarks") == false) {
            AlterTable("BasicDetails", "Remarks");
        }
        if (isColumnExists("BasicDetails", "Img1") == false) {
            AlterTable1("BasicDetails", "Img1");
        }
        if (isColumnExists("BasicDetails", "Img2") == false) {
            AlterTable1("BasicDetails", "Img2");
        }
        if (isColumnExists("BasicDetails", "Img3") == false) {
            AlterTable1("BasicDetails", "Img3");
        }
        if (isColumnExists("BasicDetails", "Img4") == false) {
            AlterTable1("BasicDetails", "Img4");
        }
        if (isColumnExists("BasicDetails", "EntryBy") == false) {
            AlterTable("BasicDetails", "EntryBy");
        }
        if (isColumnExists("BasicDetails", "Random_no_allotted_DCO") == false) {
            AlterTable("BasicDetails", "Random_no_allotted_DCO");
        }
        if (isColumnExists("BasicDetails", "Order_Of_Experiment_according_to_random_No") == false) {
            AlterTable("BasicDetails", "Order_Of_Experiment_according_to_random_No");
        }
        if (isColumnExists("BasicDetails", "Shape_of_Cce_area") == false) {
            AlterTable("BasicDetails", "Shape_of_Cce_area");
        }
        if (isColumnExists("BasicDetails", "Length_of_field") == false) {
            AlterTable("BasicDetails", "Length_of_field");
        }
        if (isColumnExists("BasicDetails", "Breadth_of_field") == false) {
            AlterTable("BasicDetails", "Breadth_of_field");
        }
        if (isColumnExists("BasicDetails", "Type_of_land") == false) {
            AlterTable("BasicDetails", "Type_of_land");
        }
        if (isColumnExists("BasicDetails", "Date_of_cutting") == false) {
            AlterTable("BasicDetails", "Date_of_cutting");
        }
        if (isColumnExists("BasicDetails", "Green_weight") == false) {
            AlterTable("BasicDetails", "Green_weight");
        }
        if (isColumnExists("BasicDetails", "Dry_weight") == false) {
            AlterTable("BasicDetails", "Dry_weight");
        }
        if (isColumnExists("BasicDetails", "No_of_baal_Maize") == false) {
            AlterTable("BasicDetails", "No_of_baal_Maize");
        }
        if (isColumnExists("BasicDetails", "Weight_of_baal_Maize") == false) {
            AlterTable("BasicDetails", "Weight_of_baal_Maize");
        }
        if (isColumnExists("BasicDetails", "Green_weight_of_Dana") == false) {
            AlterTable("BasicDetails", "Green_weight_of_Dana");
        }
        if (isColumnExists("BasicDetails", "Dry_weight_of_dana") == false) {
            AlterTable("BasicDetails", "Dry_weight_of_dana");
        }
        if (isColumnExists("BasicDetails", "obsever_Name") == false) {
            AlterTable("BasicDetails", "obsever_Name");
        }
        if (isColumnExists("BasicDetails", "observer_Mobile") == false) {
            AlterTable("BasicDetails", "observer_Mobile");
        }
        if (isColumnExists("BasicDetails", "observer_designation") == false) {
            AlterTable("BasicDetails", "observer_designation");
        }
        if (isColumnExists("BasicDetails", "remarks1") == false) {
            AlterTable("BasicDetails", "remarks1");
        }
        if (isColumnExists("BasicDetails", "Inspection_done") == false) {
            AlterTable("BasicDetails", "Inspection_done");
        }
        if (isColumnExists("BasicDetails", "Quantity_seed_in") == false) {
            AlterTable("BasicDetails", "Quantity_seed_in");
        }
        if (isColumnExists("BasicDetails", "Supervisor_present") == false) {
            AlterTable("BasicDetails", "Supervisor_present");
        }
        if (isColumnExists("BasicDetails", "Observer_present") == false) {
            AlterTable("BasicDetails", "Observer_present");
        }
        if (isColumnExists("BasicDetails", "Supervisor_nm") == false) {
            AlterTable("BasicDetails", "Supervisor_nm");
        }
        if (isColumnExists("BasicDetails", "Supervisor_mob") == false) {
            AlterTable("BasicDetails", "Supervisor_mob");
        }
        if (isColumnExists("BasicDetails", "Supervisor_designation") == false) {
            AlterTable("BasicDetails", "Supervisor_designation");
        }
        if (isColumnExists("BasicDetails", "UnitOperationalSize") == false) {
            AlterTable("BasicDetails", "UnitOperationalSize");
        }
        if (isColumnExists("BasicDetails", "UnitAreaCoverage") == false) {
            AlterTable("BasicDetails", "UnitAreaCoverage");
        }
        if (isColumnExists("BasicDetails", "Longitude") == false) {
            AlterTable("BasicDetails", "Longitude");
        }
        if (isColumnExists("BasicDetails", "EntryDate") == false) {
            AlterTable("BasicDetails", "EntryDate");
        }
        if (isColumnExists("BasicDetails", "Type_of_land_Name") == false) {
            AlterTable("BasicDetails", "Type_of_land_Name");
        }
        if (isColumnExists("BasicDetails", "Weather_condition_during_name") == false) {
            AlterTable("BasicDetails", "Weather_condition_during_name");
        }
        if (isColumnExists("BasicDetails", "sourceofseed_name") == false) {
            AlterTable("BasicDetails", "sourceofseed_name");
        }
        if (isColumnExists("BasicDetails", "tymanure_name") == false) {
            AlterTable("BasicDetails", "tymanure_name");
        }
        if (isColumnExists("BasicDetails", "systemcultivation_name") == false) {
            AlterTable("BasicDetails", "systemcultivation_name");
        }

        if (isColumnExists("BasicDetails", "varitiescrop_name") == false) {
            AlterTable("BasicDetails", "varitiescrop_name");
        }
        if (isColumnExists("BasicDetails", "CropName") == false) {
            AlterTable("BasicDetails", "CropName");
        }
        if (isColumnExists("BasicDetails", "Sub_Varities_Of_crop") == false) {
            AlterTable("BasicDetails", "Sub_Varities_Of_crop");
        }

        if (isColumnExists("BasicDetails", "Agri_year_nm") == false) {
            AlterTable("BasicDetails", "Agri_year_nm");
        }
        if (isColumnExists("BasicDetails", "LatForRadius") == false) {
            AlterTable("BasicDetails", "LatForRadius");
        }
        if (isColumnExists("BasicDetails", "LongForRadius") == false) {
            AlterTable("BasicDetails", "LongForRadius");
        }
        if (isColumnExists("BasicDetails", "DuringThreshing_Img") == false) {
            AlterTable1("BasicDetails", "DuringThreshing_Img");
        }


    }

    public boolean isColumnExists(String table, String column) {
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        databaseHelper.getReadableDatabase().close();
        return false;
    }

    public void AlterTable(String tableName, String columnName) {
        try {
            db = databaseHelper.getWritableDatabase();
            db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT");
            Log.e("ALTER Done", tableName + "-" + columnName);
            databaseHelper.getWritableDatabase().close();
        } catch (Exception e) {
            Log.e("ALTER Failed", tableName + "-" + columnName);
        }
    }

    public void AlterTable1(String tableName, String columnName) {


        try {
            db = databaseHelper.getWritableDatabase();
            db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " BLOB");
            Log.e("ALTER Done", tableName + "-" + columnName);
            databaseHelper.getWritableDatabase().close();
        } catch (Exception e) {
            Log.e("ALTER Failed", tableName + "-" + columnName);
        }
    }


}
