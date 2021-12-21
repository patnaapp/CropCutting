package in.nic.bih.cropcutting.activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.Panchayat_List;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.Entity.TypeList;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

public class Home extends AppCompatActivity {
    LinearLayout lin_personal_information, lin_Edit, lin_upload, lin_viewReport;
    ImageView img_logout, sync;
    TextView tv_district, tv_block, numberOfUploadData, txtVersion1, tv_editTask;
    DataBaseHelper dataBaseHelper;
    String UserId, password;
    int mid = 0;
    UserLogin userLogin;
    String distnm;
    String blocknm;
    String version;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        try
        {
            dataBaseHelper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database");
        }

        try
        {
            dataBaseHelper.openDataBase();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }

        ModifyTable();

        lin_personal_information = (LinearLayout) findViewById(R.id.lin_personal_information);
        lin_upload = (LinearLayout) findViewById(R.id.lin_upload);
        lin_Edit = (LinearLayout) findViewById(R.id.lin_Edit);
        lin_viewReport = (LinearLayout) findViewById(R.id.lin_viewreport);
        numberOfUploadData = (TextView) findViewById(R.id.tv_uploadTask);
        tv_editTask = (TextView) findViewById(R.id.tv_editTask);
        img_logout = (ImageView) findViewById(R.id.img_logout);

        sync = (ImageView) findViewById(R.id.syncdata);


        tv_district = (TextView) findViewById(R.id.tv_district);
        tv_block = (TextView) findViewById(R.id.tv_block);


        UserId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PASWORD", "");

        userLogin = new UserLogin();

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        userLogin = dataBaseHelper.getDetail(UserId);
        distnm = userLogin.getDistname();
        blocknm = userLogin.getBlkname();

        tv_district.setText(distnm);
        tv_block.setText(blocknm);

        getVersion();

        lin_personal_information.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Home.this, BasicDetails.class);
                startActivity(i);
            }
        });

        lin_Edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //   Intent i = new Intent(Home.this, Edit_entry.class);
                Intent i = new Intent(Home.this, EditEntryPhase2.class);
                startActivity(i);
                //finish();

            }
        });

        lin_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

                ArrayList<BasicInfo> dataProgress = dataBaseHelper.getAllEntryDetail(UserId);
                if (dataProgress.size() > 0)
                {
                    for (BasicInfo data : dataProgress)
                    {
                        uploadToServer();
                    }
                    GlobalVariables.listSize = dataProgress.size();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Data to Upload", Toast.LENGTH_LONG).show();
                }
            }
        });

        lin_viewReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Home.this, ViewReoprt.class);
                startActivity(i);
            }
        });


        img_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setIcon(R.drawable.log2);
                builder.setTitle("Log Out");
                // Ask the final question
                builder.setMessage("Are you sure want to Logout?");
                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        logout();
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        Intent i = new Intent(Home.this, Home.class);
                        startActivity(i);
                    }
                });
                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

        //showPending();
        sync.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new PANCHAYATDATA().execute();
            }
        });
    }


    private void logout()
    {

        final EditText userPass = (EditText) findViewById(R.id.et_password_login);
        final AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USERID", "").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PASWORD", "").commit();

        Intent intent = new Intent(Home.this, Login.class);
        startActivity(intent);
        finish();

    }

    private class UPLOADDATA extends AsyncTask<String, Void, String> {
        BasicInfo data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(Home.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Home.this).create();


        UPLOADDATA(BasicInfo data) {
            this.data = data;
            this._uid = data.getId();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            if (!Home.this.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected String doInBackground(String... param) {


            String res = WebServiceHelper.UploadBasicDetails(this.data);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                String string = result;
                String[] parts = string.split(",");
                String part1 = parts[0];// 004-
                String part2 = parts[1];// 004-


                if (part1.equals("1")) {

                    long c = dataBaseHelper.deleteRec(_uid);
                    if (c > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                        builder.setMessage(part2);
                        Utilitties.vibrate(Home.this);
                        builder.setIcon(R.drawable.fsyicluncher);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                showPending();

                            }
                        });
                        AlertDialog dialog = builder.create();
                        if (!Home.this.isFinishing()) {
                            dialog.show();
                        }
                    }

                } else if (part1.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    Utilitties.vibrate(Home.this);
                    builder.setMessage(part2);
                    builder.setIcon(R.drawable.uploaderror);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    if (!Home.this.isFinishing()) {
                        dialog.show();
                    }
                }
                else if (part1.equals("3")) {

                    long c = dataBaseHelper.deleteRec(_uid);
                    if (c > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                        builder.setMessage(part2);
                        Utilitties.vibrate(Home.this);
                        builder.setIcon(R.drawable.fsyicluncher);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                showPending();

                            }
                        });
                        AlertDialog dialog = builder.create();
                        if (!Home.this.isFinishing()) {
                            dialog.show();
                        }
                    }

                }

                else {
                    Toast.makeText(getApplicationContext(), "Uploading data failed ", Toast.LENGTH_SHORT).show();
                }

            } else {
                chk_msg_OK_networkdata("Result:null Uploading failed.Please Try Again Later");
                //Toast.makeText(getApplicationContext(), "Uploading failed. Server is Slow..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void uploadToServer() {
        if (Utilitties.isOnline(Home.this))
        {
            showCustomDialogupload();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void showPending() {
        int count = dataBaseHelper.getNumberTotalOfUploadData(UserId);
        Log.e("COunt", String.valueOf(count));
        if (count > 0)
        {
            numberOfUploadData.setText(String.valueOf(count));
            tv_editTask.setText(String.valueOf(count));
        }
        else
        {
            numberOfUploadData.setText("0");
            tv_editTask.setText("0");
        }

    }

    private void getVersion()
    {
        try
        {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.txtVersion);
            tv.setText("App Version  " + version);
        }
        catch (PackageManager.NameNotFoundException e)
        {

        }
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        showPending();

    }

    private void showCustomDialogupload()
    {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialogupload, viewGroup, false);
        Button buttonno = (Button) dialogView.findViewById(R.id.buttonno);
        Button buttonyes = (Button) dialogView.findViewById(R.id.buttonyes);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        Dialog d = new Dialog(this);
        d.setCanceledOnTouchOutside(false);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        buttonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setCancelable(false);
                alertDialog.dismiss();

            }
        });

        buttonyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dbHelper = new DataBaseHelper(Home.this);
                ArrayList<BasicInfo> dataProgress = dbHelper.getAllEntryDetail(UserId);
                alertDialog.dismiss();
                if (dataProgress.size() > 0) {

                    for (BasicInfo data : dataProgress) {

                        new UPLOADDATA(data).execute();

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No Data to Upload", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }


            }
        });
        alertDialog.show();
    }


    private void showCustomDialoguploadsuccess() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialoguploadsucess, viewGroup, false);
        Button buttonnook = (Button) dialogView.findViewById(R.id.buttonOkk);
        Button buttonyes = (Button) dialogView.findViewById(R.id.buttonyes);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        Dialog d = new Dialog(this);
        d.setCanceledOnTouchOutside(false);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        buttonnook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                finish();
            }
        });


        alertDialog.show();
    }

    public void chk_msg_OK_networkdata(String msg) {
        android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(Home.this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.warnet);
        ab.setTitle("Network Error");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(Home.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();


            }
        });

        ab.show();
    }

    private class PANCHAYATDATA extends AsyncTask<String, Void, ArrayList<Panchayat_List>> {

        private final ProgressDialog dialog = new ProgressDialog(Home.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Home.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Panchayat...");
            this.dialog.show();
            sync.setBackgroundResource(R.drawable.syncr);
        }

        @Override
        protected ArrayList<Panchayat_List> doInBackground(String... param)
        {
            return WebServiceHelper.getPanchayatName(UserId);
        }

        @Override
        protected void onPostExecute(ArrayList<Panchayat_List> result) {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if (result != null)
            {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());


                long i = helper.setPanchayatName(result);
                if (i > 0)
                {
                    sync.setBackgroundResource(R.drawable.sync);

                    Toast.makeText(getApplicationContext(), "Panchayat loaded", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
                new SEASON_NewLoad().execute();

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something on back.
            // Display alert message when back button has been pressed
            backButtonHandler();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("Leave");
        alertDialog.setIcon(R.drawable.que);
        alertDialog.setMessage("Are you sure you want to go back?");
        alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Home.this, PreHomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });
        alertDialog.show();
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

        if (isColumnExists("BasicDetails", "No_Of_Lines") == false) {
            AlterTable1("BasicDetails", "No_Of_Lines");
        }
        if (isColumnExists("BasicDetails", "LengthOfSelectedLine") == false) {
            AlterTable1("BasicDetails", "LengthOfSelectedLine");
        }

        if (isColumnExists("BasicDetails", "TypeOfField_Id") == false) {
            AlterTable1("BasicDetails", "TypeOfField_Id");
        }

        if (isColumnExists("BasicDetails", "TypeOfField_Name") == false) {
            AlterTable1("BasicDetails", "TypeOfField_Name");
        }


    }

    public boolean isColumnExists(String table, String column)
    {
        db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        dataBaseHelper.getReadableDatabase().close();
        return false;
    }

    public void AlterTable(String tableName, String columnName)
    {
        try
        {
            db = dataBaseHelper.getWritableDatabase();
            db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT");
            Log.e("ALTER Done", tableName + "-" + columnName);
            dataBaseHelper.getWritableDatabase().close();
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed", tableName + "-" + columnName);
        }
    }

    public void AlterTable1(String tableName, String columnName)
    {
        try
        {
            db = dataBaseHelper.getWritableDatabase();
            db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " BLOB");
            Log.e("ALTER Done", tableName + "-" + columnName);
            dataBaseHelper.getWritableDatabase().close();
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed", tableName + "-" + columnName);
        }
    }


    private class SEASON_NewLoad extends AsyncTask<String, Void, ArrayList<Season_List>> {

        private final ProgressDialog dialog = new ProgressDialog(Home.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Home.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Season_List> doInBackground(String... param) {


            return WebServiceHelper.getSeason();

        }

        @Override
        protected void onPostExecute(ArrayList<Season_List> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfgddddd", "" + result);

                DataBaseHelper helper = new DataBaseHelper(Home.this);


                long i = helper.setSeasonLocal(result);
                if (i > 0) {
                    // loadSeason_NEw();
                    Toast.makeText(Home.this, "Season loaded", Toast.LENGTH_SHORT).show();
                } else {

                }
                new FINANCIALYEAR_New().execute();

            } else {
                Toast.makeText(Home.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class FINANCIALYEAR_New extends AsyncTask<String, Void, ArrayList<Financial_Year>>
    {

        private final ProgressDialog dialog = new ProgressDialog(Home.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Home.this).create();

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Financial_Year> doInBackground(String... param)
        {
            return WebServiceHelper.getFinancialYear();
        }

        @Override
        protected void onPostExecute(ArrayList<Financial_Year> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if (result != null)
            {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(Home.this);

                long i = helper.setFinancialYear(result);
                if (i > 0)
                {
                    Toast.makeText(Home.this, "Agricultural Year Loaded", Toast.LENGTH_SHORT).show();
                    new TypeListDownload().execute();
                }
                else
                {

                }

            }
            else
            {
                Toast.makeText(Home.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class TypeListDownload extends AsyncTask<String, Void, ArrayList<TypeList>>
    {

        private final ProgressDialog dialog = new ProgressDialog(Home.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Home.this).create();

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<TypeList> doInBackground(String... param)
        {
            return WebServiceHelper.gettypeList();
        }

        @Override
        protected void onPostExecute(ArrayList<TypeList> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if (result != null)
            {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(Home.this);

                long i = helper.setTypeListData(result);
                if (i > 0)
                {
                    Toast.makeText(Home.this, "Type List Loaded", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }

            }
            else
            {
                Toast.makeText(Home.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
