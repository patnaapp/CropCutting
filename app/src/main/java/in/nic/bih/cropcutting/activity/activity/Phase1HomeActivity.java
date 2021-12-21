package in.nic.bih.cropcutting.activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.CropType;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfo;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfoOnline;
import in.nic.bih.cropcutting.activity.Entity.Panchayat_List;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.Entity.TypeList;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

public class Phase1HomeActivity extends AppCompatActivity {

    Dialog dialogselectdata;
    int indexposition = 0;
    Button dialog_btnOk, btn_reject;
    Spinner Spn_pan, Spn_season, spn_crop;
    String final_khesra;
    EditText et_finalkhesra_NO;
    LinearLayout lin_land_information, lin_Edit_khesra, lin_upload_details, lin_sync_records;
    TextView tv_block_phase1, tv_district_phase1, tv_uploadTask_phase1,tv_EditTask_phase1;
    DataBaseHelper dataBaseHelper;
    String UserId, password;
    int mid = 0;
    UserLogin userLogin;
    String distnm;
    String blocknm;
    String version;

    SQLiteDatabase db;
    ImageView img_logout_phase1;
    ProgressDialog pd1;
    long countpending;
    ImageView sync_phase1;
    ArrayList<Panchayat_List> PanchayatPhase1List = new ArrayList<Panchayat_List>();
    ArrayAdapter<String> panchayphase1atadapter;
    ArrayList<Season_List> SeasonListPhase1 = new ArrayList<Season_List>();
    ArrayAdapter<String> Seasonadapter;
    ArrayList<CropType> cropListPhase1 = new ArrayList<CropType>();
    ArrayAdapter<String> Cropadapter;
    static ArrayList<String> CropListphase1, panchayatstlistphase1;
    ArrayList<String> SeasonArray;
    ArrayList<String> CropArray = new ArrayList<String>();
    ArrayList<Financial_Year> FYearList = new ArrayList<Financial_Year>();
    ArrayList<Season_List> SeasonList = new ArrayList<Season_List>();

    String var_spn_panchayat_code_phase1 = "", var_spn_panchayat_name_phase1 = "", var_spn_season_id_phase1 = "", var_spn_season_nm_phase1 = "", var_spn_croptyp_phase1 = "", var_spn_croptype_nm_phase1 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase1_home);
        pd1 = new ProgressDialog(Phase1HomeActivity.this);

        dataBaseHelper = new DataBaseHelper(Phase1HomeActivity.this);
        pd1.setTitle("Data is Uploading Wait");
        pd1.setCancelable(false);

        lin_land_information = findViewById(R.id.lin_land_information);
        lin_Edit_khesra = findViewById(R.id.lin_Edit_khesra);
        lin_upload_details = findViewById(R.id.lin_upload_details);
        tv_district_phase1 = findViewById(R.id.tv_district_phase1);
        tv_block_phase1 = findViewById(R.id.tv_block_phase1);
        tv_uploadTask_phase1 = findViewById(R.id.tv_uploadTask_phase1);
        tv_EditTask_phase1 = findViewById(R.id.tv_EditTask_phase1);
        img_logout_phase1 = findViewById(R.id.img_logout_phase1);
        sync_phase1 = findViewById(R.id.syncdata_phase1);
        lin_sync_records = findViewById(R.id.lin_syncrecords_khesra);

        UserId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PASWORD", "");

        getVersion();
        FYearList = dataBaseHelper.getFinancialYearLocal();
        if (FYearList.size() <= 0) {
            new FINANCIALYEAR_New().execute();
        }

        SeasonListPhase1 = dataBaseHelper.getSeasonLocal();
        if (SeasonListPhase1.size() <= 0) {
            new SEASON_NewLoad().execute();
        }


        lin_land_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Phase1HomeActivity.this, KhesraEntryActivity.class);
                startActivity(i);
            }
        });

        lin_Edit_khesra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Phase1HomeActivity.this, Phase1EditEntryActivity.class);
                startActivity(i);
            }
        });
        lin_sync_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPanchayatDialogue();
            }
        });

        lin_upload_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

                int data_counts = dataBaseHelper.gettotalCount(UserId);

//                ArrayList<NewLandKhesraInfo> dataProgress = dataBaseHelper.getAllEntryDetailPhase1(UserId);
//                if (dataProgress.size() > 0) {
//                    for (NewLandKhesraInfo data : dataProgress) {
                if (data_counts > 0) {
                    uploadToServerPhase1();
                }
                // GlobalVariables.listSize = dataProgress.size();

                //  }
                else {
                    Toast.makeText(getApplicationContext(), "No Data to Upload", Toast.LENGTH_LONG).show();
                }
            }
        });

        sync_phase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PANCHAYATDATA_phase1().execute();

            }
        });


        userLogin = new UserLogin();

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        userLogin = dataBaseHelper.getDetail(UserId);
        distnm = userLogin.getDistname();
        blocknm = userLogin.getBlkname();


        tv_district_phase1.setText(distnm);
        tv_block_phase1.setText(blocknm);


        img_logout_phase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Build an AlertDialog

                AlertDialog.Builder builder = new AlertDialog.Builder(Phase1HomeActivity.this);
                // android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Phase1HomeActivity.this);
                builder.setIcon(R.drawable.log2);
                builder.setTitle("Log Out");
                // Ask the final question
                builder.setMessage("Are you sure want to Logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        logout_phasee1();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });


    }

    private void logout_phasee1()
    {
        final EditText userPass = (EditText) findViewById(R.id.et_password_login);
        final AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USERID", "").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PASWORD", "").commit();

        Intent intent = new Intent(Phase1HomeActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void showPending()
    {
        int count_phase1 = dataBaseHelper.getNumberTotalOfUploadDataPhase1(UserId);
        Log.e("COunt", String.valueOf(count_phase1));
        if (count_phase1 > 0)
        {
            tv_uploadTask_phase1.setText(String.valueOf(count_phase1));
            tv_EditTask_phase1.setText(String.valueOf(count_phase1));
        }
        else
            {
            tv_uploadTask_phase1.setText("0");
            tv_EditTask_phase1.setText("0");
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        super.onResume();
        showPending();

    }

    private void getVersion() {

        try

        {

            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv_phase1 = (TextView) findViewById(R.id.txtVersion_phase1);
            tv_phase1.setText("App Version  " + version);


        }
        catch (PackageManager.NameNotFoundException e)
        {

        }

    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {

            return model.toUpperCase();
        } else {

            return manufacturer.toUpperCase() + " " + model;
        }
    }

    public String getAppVersion() {
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Phase1HomeActivity.this);
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
                Intent i = new Intent(Phase1HomeActivity.this, PreHomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });
        alertDialog.show();
    }


    public void uploadToServerPhase1() {
        if (Utilitties.isOnline(Phase1HomeActivity.this)) {


            //showCustomDialogupload();
            android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(
                    Phase1HomeActivity.this);
            ab.setTitle("Upload");
            ab.setMessage("Do You Want to Upload Data");
            ab.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    dialog.dismiss();

                }
            });

            ab.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    DataBaseHelper dbHelper = new DataBaseHelper(Phase1HomeActivity.this);
                    ArrayList<NewLandKhesraInfo> dataProgress = dbHelper.getAllEntryDetailPhase1(UserId);
                    if (dataProgress.size() > 0) {

                        for (NewLandKhesraInfo data : dataProgress) {
                            if(data.get_chng_khesra_no().equals("1")){
                                if(data.getFieldImg()!=null && data.getFinalSelectedField()!=null && data.getNazriNkasha()!=null) {
                                    new UPLOADDATAPhase1(data).execute();
                                }
                                else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Phase1HomeActivity.this);
                                    alertDialog.setTitle("Alert !!");
                                    alertDialog.setIcon(R.drawable.uploaderror);

                                    alertDialog.setMessage("Please capture all 3 images before uploading for Khasra No-"+data.get_phase1_final_khesra_no());
                                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    alertDialog.show();
                                }
                            }
                            else {
                                new UPLOADDATAPhase1(data).execute();
                            }
                            GlobalVariables.listSize = dataProgress.size();
                            // new UPLOADDATAPhase1(data).execute();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "No Data to Upload", Toast.LENGTH_SHORT).show();

                    }


                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            ab.show();

        }
    }


    private class UPLOADDATAPhase1 extends AsyncTask<String, Void, String> {
        NewLandKhesraInfo data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();


        UPLOADDATAPhase1(NewLandKhesraInfo data) {
            this.data = data;
            this._uid = data.get_phase1_id();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            if (!Phase1HomeActivity.this.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected String doInBackground(String... param) {
            String devicename = getDeviceName();
            String app_version = getAppVersion();
            boolean isTablet = isTablet(Phase1HomeActivity.this);
            if (isTablet) {
                devicename = "Tablet::" + devicename;
                Log.e("DEVICE_TYPE", "Tablet");
            } else {
                devicename = "Mobile::" + devicename;
                Log.e("DEVICE_TYPE", "Mobile");
            }

            String res = WebServiceHelper.UploadLandDetailsPhase1(data, devicename, app_version);
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
                String parts[] = string.split(",");
                String part1 = parts[0];// 004-
                String part2 = parts[1];// 004-


                if (part1.equals("1")) {

                    long c = dataBaseHelper.deleteRecPhase1(_uid);
                    if(c>0){
                        showPending();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Phase1HomeActivity.this);
                        builder.setIcon(R.drawable.fsyicluncher);
                        builder.setTitle("Success!!");
                        builder.setMessage(part2);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });
                        AlertDialog dialog = builder.create();
                        if (!Phase1HomeActivity.this.isFinishing()) {
                            dialog.show();
                        }
                    }


                } else if (part1.equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Phase1HomeActivity.this);

                    builder.setMessage(part2);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    if (!Phase1HomeActivity.this.isFinishing()) {
                        dialog.show();
                    }
                }
                else if (part1.equals("2")) {
                    long c = dataBaseHelper.deleteRecPhase1(_uid);
                    showPending();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Phase1HomeActivity.this);
                    builder.setIcon(R.drawable.fsyicluncher);
                    builder.setTitle("Updated!!");
                    builder.setMessage(part2);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    if (!Phase1HomeActivity.this.isFinishing()) {
                        dialog.show();
                    }
                }

                else {
                    Toast.makeText(getApplicationContext(), "Uploading data failed ", Toast.LENGTH_SHORT).show();
                }

            } else {
                chk_msg_OK_networkdata("Result:null..Uploading failed.Please Try Again Later");
                //Toast.makeText(getApplicationContext(), "Uploading failed. Server is Slow..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void chk_msg_OK_networkdata(String msg) {
        // final String wantToUpdate;
        android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(Phase1HomeActivity.this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.warnet);
        ab.setTitle("Try Again Later");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(Phase1HomeActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();

            }
        });

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

    private class PANCHAYATDATA_phase1 extends AsyncTask<String, Void, ArrayList<Panchayat_List>> {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Panchayat...");
            this.dialog.show();
            sync_phase1.setBackgroundResource(R.drawable.syncr);
        }

        @Override
        protected ArrayList<Panchayat_List> doInBackground(String... param) {


            return WebServiceHelper.getPanchayatName(UserId);

        }

        @Override
        protected void onPostExecute(ArrayList<Panchayat_List> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());


                long i = helper.setPanchayatName(result);
                if (i > 0) {
                    // setPanchayatData();
                    sync_phase1.setBackgroundResource(R.drawable.sync);
                    Toast.makeText(getApplicationContext(), "Panchayat loaded", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                }

            }

            new FINANCIALYEAR_New().execute();

        }
    }


    public void ShowPanchayatDialogue() {
        dialogselectdata = new Dialog(Phase1HomeActivity.this);
        dialogselectdata.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogselectdata.setContentView(R.layout.selectvaluedialog);
        dialog_btnOk = (Button) dialogselectdata.findViewById(R.id.btn_ok);
        btn_reject = (Button) dialogselectdata.findViewById(R.id.btn_reject);
        Spn_pan = (Spinner) dialogselectdata.findViewById(R.id.Spn_panchayat_phse1);
        Spn_season = (Spinner) dialogselectdata.findViewById(R.id.Spn_season_phse1);
        spn_crop = (Spinner) dialogselectdata.findViewById(R.id.Spn_crop_phse1);

        PanchayatPhase1List = dataBaseHelper.getPanchayatLocal(UserId);
        if (PanchayatPhase1List.size() <= 0) {
            new PANCHAYATDATANEW().execute();
        } else {
            loadPanchayatDataNew(PanchayatPhase1List);
        }

        if (SeasonListPhase1.size() <= 0) {
            new SEASON_New().execute();
        } else {
            loadSeason_NEw();
        }


        Spn_pan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    Panchayat_List district = PanchayatPhase1List.get(arg2 - 1);
                    var_spn_panchayat_code_phase1 = district.getPanchayat_code();
                    var_spn_panchayat_name_phase1 = district.getPanchayat_Name();
                    //loadBlockSpinnerData();

                } else {
                    var_spn_panchayat_code_phase1 = "";
                    var_spn_panchayat_name_phase1 = "";


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        Spn_season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {
                    //spn_dialogward.setSelection(0);

                    Season_List block = SeasonListPhase1.get(arg2 - 1);
                    var_spn_season_id_phase1 = block.getSeason_Id();
                    var_spn_season_nm_phase1 = block.getSeason_Name();
                    //new DwnldPanchayat(CommonPref.getUserDetails(getApplicationContext()).getUserID()).execute();
                    // ReloadWardData();
                    //setPanchayatSpinnerData();
                    cropListPhase1 = dataBaseHelper.getCropTypeLocal(var_spn_season_id_phase1);
                    if (cropListPhase1.size() <= 0) {
                        new CROPTYPENew().execute();
                    } else {
                        loadCropType();
                    }

                } else {
                    var_spn_season_id_phase1 = "";
                    var_spn_season_nm_phase1 = "";


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spn_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id) {
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                CropType panchayat = new CropType();
                if (arg2 != 0) {
                    indexposition = arg2;

                    panchayat = cropListPhase1.get(arg2 - 1);
                    var_spn_croptyp_phase1 = panchayat.getCropId().trim();
                    var_spn_croptype_nm_phase1 = panchayat.getCropName().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog_btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SynchronizeData(var_spn_panchayat_code_phase1, var_spn_season_id_phase1, var_spn_croptyp_phase1, UserId).execute();


            }

        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogselectdata.dismiss();
            }
        });

        if (dialogselectdata.isShowing()) dialogselectdata.dismiss();
        dialogselectdata.show();
        //panchayatadapter.setNotifyOnChange(true);
    }

    private void loadPanchayatDataNew(ArrayList<Panchayat_List> pList) {
        panchayatstlistphase1 = new ArrayList<String>();
        panchayatstlistphase1.add("-Select Panchayat-");
        for (int i = 0; i < pList.size(); i++) {
            panchayatstlistphase1.add(pList.get(i).getPanchayat_Name());
        }
        panchayphase1atadapter = new ArrayAdapter(this, R.layout.dropdowlist, panchayatstlistphase1);
        Spn_pan.setAdapter(panchayphase1atadapter);


    }

    private class PANCHAYATDATANEW extends AsyncTask<String, Void, ArrayList<Panchayat_List>> {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Panchayat...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Panchayat_List> doInBackground(String... param) {


            return WebServiceHelper.getPanchayatName(UserId);

        }

        @Override
        protected void onPostExecute(ArrayList<Panchayat_List> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);


                long i = helper.setPanchayatName(result);
                if (i > 0) {
                    setPanchayatDataNewPhase1();

                } else {

                }

            } else {
                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void setPanchayatDataNewPhase1() {
        DataBaseHelper placeData = new DataBaseHelper(Phase1HomeActivity.this);
        PanchayatPhase1List = placeData.getPanchayatLocal(UserId);
        Log.d("Wardlist", "" + PanchayatPhase1List.size());
        if (PanchayatPhase1List.size() > 0)
            loadPanchayatDataNew(PanchayatPhase1List);

    }


    public void loadSeason_NEw() {
        dataBaseHelper = new DataBaseHelper(Phase1HomeActivity.this);

        SeasonListPhase1 = dataBaseHelper.getSeasonLocal();
        SeasonArray = new ArrayList<String>();
        SeasonArray.add("-select-");

        int i = 0;
        for (Season_List season_list : SeasonListPhase1) {
            SeasonArray.add(season_list.getSeason_Name());
            i++;
        }
        Seasonadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, SeasonArray);
        Seasonadapter.setDropDownViewResource(R.layout.dropdowlist);
        Spn_season.setAdapter(Seasonadapter);

    }


    private class SEASON_New extends AsyncTask<String, Void, ArrayList<Season_List>> {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);


                long i = helper.setSeasonLocal(result);
                if (i > 0) {
                    loadSeason_NEw();

                } else {

                }

            } else {
                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class SEASON_NewLoad extends AsyncTask<String, Void, ArrayList<Season_List>> {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);


                long i = helper.setSeasonLocal(result);
                if (i > 0) {
                    // loadSeason_NEw();
                    Toast.makeText(Phase1HomeActivity.this, "Season loaded", Toast.LENGTH_SHORT).show();
                } else {

                }

                new TypeListDownload().execute();

            } else {
                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void loadCropType() {
        dataBaseHelper = new DataBaseHelper(Phase1HomeActivity.this);

        CropArray.clear();
        cropListPhase1 = dataBaseHelper.getCropTypeLocal(var_spn_season_id_phase1);

        CropArray.add("-select-");
        int i = 0;
        for (CropType cropType : cropListPhase1) {
            CropArray.add(cropType.getCropName());
            i++;
        }
        Cropadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, CropArray);
        Cropadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_crop.setAdapter(Cropadapter);


    }

    private class CROPTYPENew extends AsyncTask<String, Void, ArrayList<CropType>> {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<CropType> doInBackground(String... param) {


            return WebServiceHelper.getCroptype(var_spn_season_id_phase1);

        }

        @Override
        protected void onPostExecute(ArrayList<CropType> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);


                long i = helper.setCropTypeLocal(result);
                if (i > 0) {
                    loadCropType();


                } else {

                }

            } else {
                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class SynchronizeData extends AsyncTask<String, Void, ArrayList<NewLandKhesraInfoOnline>>
    {

        String Panchayatid = "";
        String seasonid = "";
        String croptid = "";
        String finalkhesrano = "";

        public SynchronizeData(String panchayatcode, String seasoncode, String cropcode, String finalkhesrano) {
            this.Panchayatid = panchayatcode;
            this.seasonid = seasoncode;
            this.croptid = cropcode;
            this.finalkhesrano = finalkhesrano;
            Log.e("PCode", Panchayatid);

        }

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("डेटा लोड हो रहा है.\nकृपया प्रतीक्षा करें...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<NewLandKhesraInfoOnline> doInBackground(String... params)
        {
            ArrayList<NewLandKhesraInfoOnline> res1 = WebServiceHelper.GetDataLIst1(Panchayatid, seasonid, croptid, UserId);
            return res1;

        }

        @Override
        protected void onPostExecute(final ArrayList<NewLandKhesraInfoOnline> result)
        {
            if (result != null)
            {
                if (!result.isEmpty())
                {

                    DataBaseHelper placeData = new DataBaseHelper(Phase1HomeActivity.this);
                    long c = placeData.Datainsert(result, UserId, Panchayatid);

                    //ReloadWardData();
                    if (c > 0)
                    {
                        this.dialog.dismiss();
                        showPending();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Phase1HomeActivity.this);
                        alertDialog.setTitle("Success");
                        alertDialog.setIcon(R.drawable.fsyicluncher);
                        alertDialog.setMessage("Records Synced Successfully");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                dialogselectdata.cancel();
                            }
                        });

                        alertDialog.show();

                    }

                }
                else
                {
                    indexposition = 0;

                    Toast.makeText(Phase1HomeActivity.this, "कोई रिकॉर्ड नहीं मिला", Toast.LENGTH_SHORT).show();
                    this.dialog.dismiss();
                }
            }
            else {

                Toast.makeText(Phase1HomeActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                this.dialog.dismiss();
            }

        }
    }


    private class FINANCIALYEAR_New extends AsyncTask<String, Void, ArrayList<Financial_Year>>
    {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);

                long i = helper.setFinancialYear(result);
                if (i > 0)
                {
                    Toast.makeText(Phase1HomeActivity.this, "Agricultural Year Loaded", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
                new SEASON_NewLoad().execute();
            }
            else
            {
                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private class TypeListDownload extends AsyncTask<String, Void, ArrayList<TypeList>>
    {

        private final ProgressDialog dialog = new ProgressDialog(Phase1HomeActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Phase1HomeActivity.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(Phase1HomeActivity.this);

                long i = helper.setTypeListData(result);
                if (i > 0)
                {
                    Toast.makeText(Phase1HomeActivity.this, "Type List Loaded", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }

            }
            else
            {
                Toast.makeText(Phase1HomeActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}
