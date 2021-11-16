package in.nic.bih.cropcutting.activity.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Entity.CheckList;
import in.nic.bih.cropcutting.activity.Entity.CropType;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfo;
import in.nic.bih.cropcutting.activity.Entity.Panchayat_List;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.Entity.Source_Seed;
import in.nic.bih.cropcutting.activity.Entity.TypeList;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

public class KhesraEntryActivity extends AppCompatActivity {

    Spinner spn_agriculture_year_new, spn_season_new, spn_crop_type_new, spn_panchayat_new, spn_chng_khesra;
    EditText et_dist_new, et_block_new, et_PlotNo_new, et_allotedKhesra_NO, et_finalKhesra_NO, et_farmerName, et_chng_remarks, et_revenue_village_new, et_tentative_date_cce;
    ImageView img_pic_naksha, img_pic_field, img_pic_finalplot;
    ArrayList<BasicInfo> EntryList = new ArrayList<>();
    ArrayList<Financial_Year> FYearList = new ArrayList<Financial_Year>();
    ArrayList<Season_List> SeasonList = new ArrayList<Season_List>();
    ArrayList<CropType> cropList = new ArrayList<CropType>();
    ArrayList<Source_Seed> SourceSeedList = new ArrayList<Source_Seed>();
    ArrayList<Panchayat_List> PanchayatList = new ArrayList<Panchayat_List>();
    ArrayList<TypeList> TypeList = new ArrayList<TypeList>();
    ArrayList<String> FyearArray;
    ArrayList<String> SeasonArray;
    ArrayList<String> CropArray = new ArrayList<String>();
    ArrayList<String> TypeArray = new ArrayList<String>();
    String flag;

    ArrayList<String> SourceSeedArray;
    ArrayAdapter<String> Fyearadapter;
    ArrayAdapter<String> Seasonadapter;
    ArrayAdapter<String> Cropadapter;
    ArrayAdapter<String> typadapter;
    ArrayAdapter<String> SourceSeedadapter;
    ArrayAdapter<String> panchayatadapter;
    String var_spn_agri_id = "", var_spn_agri_year = "", st_spn_season_id_new = "", var_spn_croptyp = "", var_spn_croptype_nm = "";
    String var_spn_season_id = "", var_spn_season_nm = "", var_spn_seasn_flag;
    DataBaseHelper dataBaseHelper;
    String _tentative_date = "", _village_name = "";
    String UserId = "", password = "";
    String keyid = "";
    boolean edit;
    String var_spn_panchayat_code = "", var_spn_panchayat_name = "", _varChecklist_Id = "", _varChecklist_Name = "";
    static ArrayList<String> CropList, panchayatstlist;
    String _panCode = null;
    ArrayList<CheckList> Checklist_List = new ArrayList<CheckList>();
    String checklist;
    ArrayAdapter<String> chklistadapter;
    UserLogin userLogin;
    String distnm = "";
    String blocknm = "";
    String distcode = "";
    String blockcode = "";
    LinearLayout lin_fieldimage, lin_nazrinaksha, lin_FinalPlot;
    private final static int CAMERA_PIC = 1;
    int ThumbnailSize = 145;
    byte[] imgData;
    byte[] imgData1;
    String str_img = "N", str_lat = "", str_long = "", str_gpstime = "";
    String str_lat1 = "", str_long1 = "", str_gpstime1 = "", str_lat2 = "", str_long2 = "", str_gpstime2 = "";
    byte[] imageData2, imageData4;
    byte[] imageData3;
    String str_imagcap;
    String str_imagcap1, str_imagcap2;
    String _var_alloted_khesra = "", _var_final_khesra = "";
    String _Plot_no = "", _alloted_khesra = "", _final_khesra = "", _farmer_name = "", _chng_remarks = "";
    String isEdit = "";
    Button button_save;
    String fyearlist;
    String _spin_agri_yr = "", _spin_season = "", _spin_crop_type = "", _spin_panchayat = "", _spin_chng_khesra = "",_spin_type_List="";
    ImageView viewIMG, viewIMG1, viewIMG2, img_tentative_cce;
    TextView tv_chngRemarks;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    Spinner spn_type_new;
    String var_spn_type_id="",var_spn_type_nm="";
    LinearLayout ll_typeList;


    NewLandKhesraInfo newlandbasicInfo = new NewLandKhesraInfo();
    ArrayList<NewLandKhesraInfo> LandInfoList = new ArrayList<NewLandKhesraInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_land_entry_acitvity);


        dataBaseHelper = new DataBaseHelper(KhesraEntryActivity.this);
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

        //UserId= PreferenceManager.getDefaultSharedPreferences(KhesraEntryActivity.this).getString("USERID", "");
        //UserId="AFA1520";
        spn_agriculture_year_new = findViewById(R.id.spn_agriculture_year_new);
        spn_season_new = findViewById(R.id.spn_season_new);
        spn_crop_type_new = findViewById(R.id.spn_crop_type_new);

        spn_panchayat_new = findViewById(R.id.spn_panchayat_new);
        spn_chng_khesra = findViewById(R.id.spn_chng_khesra);
        et_dist_new = findViewById(R.id.et_dist_new);
        et_block_new = findViewById(R.id.et_block_new);
        et_PlotNo_new = findViewById(R.id.et_PlotNo_new);
        et_allotedKhesra_NO = findViewById(R.id.et_allotedKhesra_NO);
        et_finalKhesra_NO = findViewById(R.id.et_finalKhesra_NO);
        et_farmerName = findViewById(R.id.et_farmerName);
        et_chng_remarks = findViewById(R.id.et_chng_remarks);
        img_pic_naksha = findViewById(R.id.img_pic_naksha);
        img_pic_field = findViewById(R.id.img_pic_field);
        img_pic_finalplot = findViewById(R.id.img_pic_finalplot);
        lin_fieldimage = findViewById(R.id.lin_fieldimage);
        lin_nazrinaksha = findViewById(R.id.lin_nazrinaksha);
        lin_FinalPlot = findViewById(R.id.lin_FinalPlot);
        tv_chngRemarks = findViewById(R.id.tv_chngRemarks);

        et_revenue_village_new = findViewById(R.id.et_revenue_village_new);
        et_tentative_date_cce = findViewById(R.id.et_tentative_date_cce);
        img_tentative_cce = findViewById(R.id.img_tentative_cce);
        ll_typeList = findViewById(R.id.ll_typeList);

        viewIMG = findViewById(R.id.viewIMG);
        viewIMG1 = findViewById(R.id.viewIMG1);
        viewIMG2 = findViewById(R.id.viewIMG2);

        button_save = findViewById(R.id.button_save);
        spn_type_new = findViewById(R.id.spn_type_new);

        button_save.setEnabled(false);
        img_pic_naksha.setEnabled(false);
        img_pic_finalplot.setEnabled(false);
        et_tentative_date_cce.setEnabled(false);
        et_chng_remarks.setVisibility(View.GONE);
        tv_chngRemarks.setVisibility(View.GONE);
        ll_typeList.setVisibility(View.GONE);
        loadFinancialYear();
        loadPanchayatDataNew(PanchayatList);
        loadChngKhesraSpinnerdata();

        try
        {
            keyid = getIntent().getExtras().getString("KeyId");
            String isEdit = "";
            isEdit = getIntent().getExtras().getString("isEdit");
            Log.d("kvfrgv", "" + keyid + "" + isEdit);
            if (Integer.parseInt(keyid) > 0 && isEdit.equals("Yes"))
            {
                edit = true;
                loadFinancialYear();
                loadPanchayatDataNew(PanchayatList);
                loadChngKhesraSpinnerdata();
                ShowEditEntryKhesra(keyid);

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        spn_agriculture_year_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0)
                {
                    var_spn_agri_id = FYearList.get(pos - 1).getYear_Id();
                    var_spn_agri_year = FYearList.get(pos - 1).getFinancial_year();
                }
                else {
                    var_spn_agri_id="";
                    var_spn_agri_year="";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // TODO Auto-generated method stub
            }

        });

        spn_season_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0)
                {
                    st_spn_season_id_new = SeasonList.get(pos - 1).getSeason_Id();
                    var_spn_season_nm = SeasonList.get(pos - 1).getSeason_Name();
                    var_spn_seasn_flag = SeasonList.get(pos - 1).getFlag();

                    cropList = dataBaseHelper.getCropTypeLocal(st_spn_season_id_new);
                    if (cropList.size() <= 0)
                    {
                        new CROPTYPENew().execute();
                    }
                    else
                    {
                        loadCropType();
                    }


                }
                else {
                    spn_crop_type_new.setSelection(0);
                    spn_type_new.setSelection(0);
                    st_spn_season_id_new = "";
                    var_spn_season_nm = "";
                    var_spn_seasn_flag = "";
                    loadCropType();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_crop_type_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {

                    var_spn_croptyp = cropList.get(pos - 1).getCropId();
                    var_spn_croptype_nm = cropList.get(pos - 1).getCropName();

                    if (st_spn_season_id_new.equals("3")&& var_spn_croptyp.equals("13"))
                    {
                        ll_typeList.setVisibility(View.VISIBLE);
                    }
                    else if(st_spn_season_id_new.equals("3")&& var_spn_croptyp.equals("15"))
                    {
                        ll_typeList.setVisibility(View.VISIBLE);
                    }
                    else if(st_spn_season_id_new.equals("1")&& var_spn_croptyp.equals("16"))
                    {
                        ll_typeList.setVisibility(View.VISIBLE);
                    }
//                    else if(st_spn_season_id_new.equals("1")&& var_spn_croptyp.equals("1"))
//                    {
//                        ll_typeList.setVisibility(View.VISIBLE);
//                    }
                    else
                    {
                        ll_typeList.setVisibility(View.GONE);
                        var_spn_type_id="";
                        var_spn_type_nm="";
                    }


                    loadTypeList();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_type_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {

                    var_spn_type_id = TypeList.get(pos - 1).getId();
                    var_spn_type_nm = TypeList.get(pos - 1).getName();


                }
                else {
                    var_spn_type_id="";
                    var_spn_type_nm="";
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        spn_panchayat_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    var_spn_panchayat_code = PanchayatList.get(pos - 1).getPanchayat_code();
                    var_spn_panchayat_name = PanchayatList.get(pos - 1).getPanchayat_Name();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        spn_chng_khesra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    CheckList wrd = Checklist_List.get(arg2 - 1);
                    _varChecklist_Id = wrd.getChecklist_Id();
                    _varChecklist_Name = wrd.getChecklist_Name();

                    _var_alloted_khesra = et_allotedKhesra_NO.getText().toString().trim();
                    _var_final_khesra = et_finalKhesra_NO.getText().toString().trim();


                    if (_varChecklist_Id.equalsIgnoreCase("2")) {
                        lin_FinalPlot.setVisibility(View.VISIBLE);
                        lin_nazrinaksha.setVisibility(View.GONE);
                        lin_fieldimage.setVisibility(View.GONE);
                        et_chng_remarks.setVisibility(View.GONE);
                        tv_chngRemarks.setVisibility(View.GONE);

                        img_pic_finalplot.setEnabled(true);
                        et_finalKhesra_NO.setText(_var_alloted_khesra);
                        et_finalKhesra_NO.setEnabled(false);

                    } else if (_varChecklist_Id.equalsIgnoreCase("1")) {
                        lin_FinalPlot.setVisibility(View.VISIBLE);
                        lin_nazrinaksha.setVisibility(View.VISIBLE);
                        lin_fieldimage.setVisibility(View.VISIBLE);
                        et_chng_remarks.setVisibility(View.VISIBLE);
                        tv_chngRemarks.setVisibility(View.VISIBLE);
                        et_finalKhesra_NO.setEnabled(true);
                        if (!(getIntent().hasExtra("KeyId"))) {
                            et_finalKhesra_NO.setText("");
                        }
                    }

                } else if (arg2 == 0) {
                    // spWard.setSelection(0);
                    // spVillage.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        img_pic_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilitties.isGPSEnabled(KhesraEntryActivity.this)) {
                    //str_img = "YES";

                    Intent iCamera = new Intent(getApplicationContext(),
                            CameraActivity.class);
                    iCamera.putExtra("KEY_PIC", "1");
                    startActivityForResult(iCamera, CAMERA_PIC);
                } else {

                    turnGPSOn();
                }

            }
        });

        img_pic_naksha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilitties.isGPSEnabled(KhesraEntryActivity.this)) {
                    //str_img = "YES";

                    Intent iCamera = new Intent(getApplicationContext(),
                            CameraActivity.class);
                    iCamera.putExtra("KEY_PIC", "2");
                    startActivityForResult(iCamera, CAMERA_PIC);
                } else {

                    turnGPSOn();
                }


            }
        });
        img_pic_finalplot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilitties.isGPSEnabled(KhesraEntryActivity.this)) {
                    //str_img = "YES";

                    Intent iCamera = new Intent(getApplicationContext(),
                            CameraActivity.class);
                    iCamera.putExtra("KEY_PIC", "3");
                    startActivityForResult(iCamera, CAMERA_PIC);
                } else {

                    turnGPSOn();
                }


            }
        });

        img_tentative_cce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDialog();
            }
        });


        UserId = PreferenceManager.getDefaultSharedPreferences(KhesraEntryActivity.this).getString("USERID", "");
        password = PreferenceManager.getDefaultSharedPreferences(KhesraEntryActivity.this).getString("PASWORD", "");
        userLogin = dataBaseHelper.getDetail(UserId);
        distnm = userLogin.getDistname();
        blocknm = userLogin.getBlkname();
        distcode = userLogin.getDistcode();
        blockcode = userLogin.getBlkcode();

        et_dist_new.setText(distnm);
        et_block_new.setText(blocknm);


        FYearList = dataBaseHelper.getFinancialYearLocal();
        if (FYearList.size() <= 0) {
            new FINANCIALYEAR_New().execute();
        } else {
            loadFinancialYear();
        }
        SeasonList = dataBaseHelper.getSeasonLocal();
        if (SeasonList.size() <= 0) {
            new SEASON_New().execute();
        } else {
            loadSeason_NEw();
        }
        PanchayatList = dataBaseHelper.getPanchayatLocal(UserId);
        if (PanchayatList.size() <= 0) {
            new PANCHAYATDATANEW().execute();
        } else {
            loadPanchayatDataNew(PanchayatList);
        }
        loadChngKhesraSpinnerdata();
    }


    private class FINANCIALYEAR_New extends AsyncTask<String, Void, ArrayList<Financial_Year>> {

        private final ProgressDialog dialog = new ProgressDialog(KhesraEntryActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(KhesraEntryActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Financial_Year> doInBackground(String... param) {


            return WebServiceHelper.getFinancialYear();

        }

        @Override
        protected void onPostExecute(ArrayList<Financial_Year> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(KhesraEntryActivity.this);


                long i = helper.setFinancialYear(result);
                if (i > 0) {
                    loadFinancialYear();

                } else {

                }

            } else {
                Toast.makeText(KhesraEntryActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClick_Save(View view) {
        if (button_save.getText().toString().equals("UPDATE")) {
            String isvalid = validateRecordBeforeSaving();

            if (isvalid.equalsIgnoreCase("yes")) {
                updatedNewEntryPhase1();
            }
        } else {
            String isvalid = validateRecordBeforeSaving();
            if (isvalid.equalsIgnoreCase("yes")) {
                InsertIntoLocal();
            }
        }

    }

    public void loadFinancialYear() {
        dataBaseHelper = new DataBaseHelper(KhesraEntryActivity.this);

        FYearList = dataBaseHelper.getFinancialYearLocal();
        FyearArray = new ArrayList<String>();
        FyearArray.add("-select-");
        int i = 0;
        for (Financial_Year financial_year : FYearList) {
            FyearArray.add(financial_year.getFinancial_year());
            i++;
        }
        Fyearadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, FyearArray);
        Fyearadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_agriculture_year_new.setAdapter(Fyearadapter);

        if (getIntent().hasExtra("KeyId")) {
            spn_agriculture_year_new.setSelection(((ArrayAdapter<String>) spn_agriculture_year_new.getAdapter()).getPosition(_spin_agri_yr));
        }

    }


    private class CROPTYPENew extends AsyncTask<String, Void, ArrayList<CropType>> {

        private final ProgressDialog dialog = new ProgressDialog(KhesraEntryActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(KhesraEntryActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<CropType> doInBackground(String... param) {


            return WebServiceHelper.getCroptype(st_spn_season_id_new);

        }

        @Override
        protected void onPostExecute(ArrayList<CropType> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(KhesraEntryActivity.this);


                long i = helper.setCropTypeLocal(result);
                if (i > 0) {
                    loadCropType();


                } else {

                }

            } else {
                Toast.makeText(KhesraEntryActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void loadCropType() {
        dataBaseHelper = new DataBaseHelper(KhesraEntryActivity.this);

        CropArray.clear();
        cropList = dataBaseHelper.getCropTypeLocal(st_spn_season_id_new);

        CropArray.add("-select-");
        int i = 0;
        for (CropType cropType : cropList) {
            CropArray.add(cropType.getCropName());
            i++;
        }
        Cropadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, CropArray);
        Cropadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_crop_type_new.setAdapter(Cropadapter);


        if (getIntent().hasExtra("KeyId")) {
            spn_crop_type_new.setSelection(((ArrayAdapter<String>) spn_crop_type_new.getAdapter()).getPosition(_spin_crop_type));
        }

    }


    public void loadTypeList() {
        dataBaseHelper = new DataBaseHelper(KhesraEntryActivity.this);

        TypeArray.clear();
        TypeList = dataBaseHelper.getTypeLocal(var_spn_croptyp);

        TypeArray.add("-select-");
        int i = 0;
        for (TypeList cropType : TypeList) {
            TypeArray.add(cropType.getName());
            i++;
        }
        typadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, TypeArray);
        typadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_type_new.setAdapter(typadapter);


        if (getIntent().hasExtra("KeyId")) {
            spn_type_new.setSelection(((ArrayAdapter<String>) spn_type_new.getAdapter()).getPosition(_spin_type_List));
        }

    }

    private class SEASON_New extends AsyncTask<String, Void, ArrayList<Season_List>> {

        private final ProgressDialog dialog = new ProgressDialog(KhesraEntryActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(KhesraEntryActivity.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(KhesraEntryActivity.this);


                long i = helper.setSeasonLocal(result);
                if (i > 0) {
                    loadSeason_NEw();

                    // Toast.makeText(BasicDetails.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(BasicDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(KhesraEntryActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadSeason_NEw() {
        dataBaseHelper = new DataBaseHelper(KhesraEntryActivity.this);

        SeasonList = dataBaseHelper.getSeasonLocal();
        SeasonArray = new ArrayList<String>();
        SeasonArray.add("-select-");

        int i = 0;
        for (Season_List season_list : SeasonList) {
            SeasonArray.add(season_list.getSeason_Name());
            i++;
        }
        Seasonadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, SeasonArray);
        Seasonadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_season_new.setAdapter(Seasonadapter);
//        if(!seasonvalnew.equals("")) {
//            spn_season_new.setSelection((((ArrayAdapter<String>) spn_season_new.getAdapter()).getPosition(seasonvalnew)));
//        }

        if (getIntent().hasExtra("KeyId")) {
            spn_season_new.setSelection(((ArrayAdapter<String>) spn_season_new.getAdapter()).getPosition(_spin_season));
        }


    }

    private void loadPanchayatDataNew(ArrayList<Panchayat_List> pList) {
        panchayatstlist = new ArrayList<String>();
        panchayatstlist.add("-Select Panchayat-");
        for (int i = 0; i < pList.size(); i++) {
            panchayatstlist.add(pList.get(i).getPanchayat_Name());
        }
        panchayatadapter = new ArrayAdapter(this, R.layout.dropdowlist, panchayatstlist);
        spn_panchayat_new.setAdapter(panchayatadapter);

        if (getIntent().hasExtra("KeyId")) {
            spn_panchayat_new.setSelection(((ArrayAdapter<String>) spn_panchayat_new.getAdapter()).getPosition(_spin_panchayat));
        }


    }


    private class PANCHAYATDATANEW extends AsyncTask<String, Void, ArrayList<Panchayat_List>> {

        private final ProgressDialog dialog = new ProgressDialog(KhesraEntryActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(KhesraEntryActivity.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(KhesraEntryActivity.this);


                long i = helper.setPanchayatName(result);
                if (i > 0) {
                    setPanchayatDataNew();

                    // Toast.makeText(BasicDetails.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(BasicDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(KhesraEntryActivity.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadChngKhesraSpinnerdata() {

        Checklist_List = dataBaseHelper.getchklist_List();
        String[] divNameArray = new String[Checklist_List.size() + 1];
        divNameArray[0] = "-select-";
        int i = 1;
        int setID = 0;
        for (CheckList gen : Checklist_List) {
            divNameArray[i] = gen.getChecklist_Name();
            if (checklist == gen.getChecklist_Id()) {
                setID = i;
            }
            i++;

        }
        chklistadapter = new ArrayAdapter<String>(this, R.layout.dropdowlist, divNameArray);
        chklistadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_chng_khesra.setAdapter(chklistadapter);

        if (getIntent().hasExtra("KeyId")) {
            spn_chng_khesra.setSelection(((ArrayAdapter<String>) spn_chng_khesra.getAdapter()).getPosition(_spin_chng_khesra));
        }


    }

    private void turnGPSOn() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is off...\nDo you want to turn on GPS..")

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

    public void setPanchayatDataNew() {
        DataBaseHelper placeData = new DataBaseHelper(KhesraEntryActivity.this);
        PanchayatList = placeData.getPanchayatLocal(UserId);
        Log.d("Wardlist", "" + PanchayatList.size());
        if (PanchayatList.size() > 0)
            loadPanchayatDataNew(PanchayatList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    imgData = data.getByteArrayExtra("CapturedImage");

                    getIntent().removeExtra("CapturedImage");
                    //imageData.add(imgData);

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img_pic_field.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_pic_field.setImageBitmap(Utilitties.GenerateThumbnail(bmp,
                                    ThumbnailSize, ThumbnailSize));
                            viewIMG.setVisibility(View.VISIBLE);
                            str_img = "Y";
                            imageData2 = imgData;
                            str_lat = data.getStringExtra("Lat");
                            str_long = data.getStringExtra("Lng");
                            str_gpstime = data.getStringExtra("GPSTime");
                            str_img = "Y";
                            str_imagcap = org.kobjects.base64.Base64.encode(imgData);
                            img_pic_naksha.setEnabled(true);
                            img_pic_finalplot.setEnabled(true);
                            button_save.setEnabled(true);
                            break;


                        case 2:
                            Bitmap bmp1 = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img_pic_naksha.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_pic_naksha.setImageBitmap(Utilitties.GenerateThumbnail(bmp1,
                                    ThumbnailSize, ThumbnailSize));
                            viewIMG1.setVisibility(View.VISIBLE);
                            str_img = "Y";
                            imageData3 = imgData;
                            str_lat1 = data.getStringExtra("Lat");
                            str_long1 = data.getStringExtra("Lng");
                            str_gpstime1 = data.getStringExtra("GPSTime");

                            str_imagcap1 = org.kobjects.base64.Base64.encode(imgData);
                            img_pic_finalplot.setEnabled(true);
                            button_save.setEnabled(true);
                            break;

                        case 3:
                            Bitmap bmp2 = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img_pic_finalplot.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_pic_finalplot.setImageBitmap(Utilitties.GenerateThumbnail(bmp2,
                                    ThumbnailSize, ThumbnailSize));
                            viewIMG2.setVisibility(View.VISIBLE);
                            str_img = "Y";
                            imageData4 = imgData;
                            str_lat2 = data.getStringExtra("Lat");
                            str_long2 = data.getStringExtra("Lng");
                            str_gpstime2 = data.getStringExtra("GPSTime");

                            str_imagcap2 = org.kobjects.base64.Base64.encode(imgData);
                            button_save.setEnabled(true);
                            break;


                    }
                }
        }
    }


    private String validateRecordBeforeSaving() {
        String isvalid = "yes";

        if ((spn_agriculture_year_new != null && spn_agriculture_year_new.getSelectedItem() != null)) {
            if ((String) spn_agriculture_year_new.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(KhesraEntryActivity.this, "कृपया एग्रीकल्चर इयर का चयन करें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

        if ((spn_season_new != null && spn_season_new.getSelectedItem() != null)) {
            if ((String) spn_season_new.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(KhesraEntryActivity.this, "कृपया सीजन का चयन करें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

        if ((spn_crop_type_new != null && spn_crop_type_new.getSelectedItem() != null)) {
            if ((String) spn_crop_type_new.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(KhesraEntryActivity.this, "कृपया क्रॉप टाइप चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if ((spn_panchayat_new != null && spn_panchayat_new.getSelectedItem() != null)) {
            if ((String) spn_panchayat_new.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(KhesraEntryActivity.this, "कृपया पंचायत चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if ((spn_chng_khesra != null && spn_chng_khesra.getSelectedItem() != null)) {
            if ((String) spn_chng_khesra.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(KhesraEntryActivity.this, "कृपया खेसरा चुनें", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if ((st_spn_season_id_new.equals("3") && var_spn_croptyp.equals("15"))||(st_spn_season_id_new.equals("3") && var_spn_croptyp.equals("13"))||(st_spn_season_id_new.equals("1") && var_spn_croptyp.equals("16"))) {
            if ((spn_type_new != null && spn_type_new.getSelectedItem() != null)) {
                if ((String) spn_type_new.getSelectedItem() != "-select-") {
                    isvalid = "yes";
                } else {
                    Toast.makeText(KhesraEntryActivity.this, "Please select Type.", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }
        }

        if (et_dist_new.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया जिला दर्ज करें.", Toast.LENGTH_LONG).show();
            et_dist_new.requestFocus();
            return "no";
        }

        if (et_farmerName.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया किशान का नाम' दर्ज करें.", Toast.LENGTH_LONG).show();
            et_farmerName.requestFocus();
            return "no";
        }


        if (et_block_new.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया ब्लॉक दर्ज करें.", Toast.LENGTH_LONG).show();
            et_block_new.requestFocus();
            return "no";
        }

        if (et_PlotNo_new.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया हाईएस्ट प्लाट नंबर दर्ज करें.", Toast.LENGTH_LONG).show();
            et_PlotNo_new.requestFocus();
            return "no";
        }

        if (et_allotedKhesra_NO.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया अल्लोतेद खेसरा दर्ज करें.", Toast.LENGTH_LONG).show();
            et_allotedKhesra_NO.requestFocus();
            return "no";
        }


        if (et_finalKhesra_NO.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया फाइनल खेसरा दर्ज करें.", Toast.LENGTH_LONG).show();
            et_finalKhesra_NO.requestFocus();
            return "no";
        }

        if (et_revenue_village_new.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया गाँव का नाम दर्ज करें.", Toast.LENGTH_LONG).show();
            et_revenue_village_new.requestFocus();
            return "no";
        }

        if (et_tentative_date_cce.getText().toString().trim().length() <= 0) {
            Toast.makeText(KhesraEntryActivity.this, "कृपया कटाई की तिथि दर्ज करें.", Toast.LENGTH_LONG).show();
            et_tentative_date_cce.requestFocus();
            return "no";
        }

        if (_varChecklist_Id.equals("1")) {

            if (et_chng_remarks.getText().toString().trim().length() <= 0) {
                Toast.makeText(KhesraEntryActivity.this, "कृपया रीज़न दर्ज करें.", Toast.LENGTH_LONG).show();
                et_chng_remarks.requestFocus();
                return "no";
            }
        }

        return isvalid;
    }

    private void SetValue() {
        _Plot_no = et_PlotNo_new.getText().toString();
        _alloted_khesra = et_allotedKhesra_NO.getText().toString();
        _final_khesra = et_finalKhesra_NO.getText().toString();
        _farmer_name = et_farmerName.getText().toString();
        _chng_remarks = et_chng_remarks.getText().toString();
        _village_name = et_revenue_village_new.getText().toString();
        _tentative_date = et_tentative_date_cce.getText().toString();


    }

    private void InsertIntoLocal() {

        long id = 0;
        SetValue();
        DataBaseHelper placeData = new DataBaseHelper(KhesraEntryActivity.this);
        NewLandKhesraInfo newEntryEntity = new NewLandKhesraInfo();

        newEntryEntity.set_phase1_userid(UserId);
        newEntryEntity.set_phse1_agri_yr(var_spn_agri_id);
        newEntryEntity.set_phse1_agri_yr_nm(var_spn_agri_year);
        newEntryEntity.set_phase1_season(st_spn_season_id_new);
        newEntryEntity.set_phase1_season_nm(var_spn_season_nm);
        newEntryEntity.set_phase1_croptype(var_spn_croptyp);
        newEntryEntity.set_phase1_crop_name(var_spn_croptype_nm);
        newEntryEntity.set_phase1_dist(distcode);
        newEntryEntity.set_phase1_block(blockcode);
        newEntryEntity.set_phase1_panchayat(var_spn_panchayat_code);
        newEntryEntity.set_phase1_panchayat_nm(var_spn_panchayat_name);
        newEntryEntity.set_phase1_highest_plot_no(_Plot_no);
        newEntryEntity.set_phase1_alloted_khesra_no(_alloted_khesra);
        newEntryEntity.set_chng_khesra_no(_varChecklist_Id);
        newEntryEntity.set_chng_khesra_no_nm(_varChecklist_Name);
        newEntryEntity.set_phase1_final_khesra_no(_final_khesra);
        newEntryEntity.setFieldImg(str_imagcap);
        newEntryEntity.setNazriNkasha(str_imagcap1);
        newEntryEntity.setFinalSelectedField(str_imagcap2);

        newEntryEntity.set_phase1_lat(str_lat);
        newEntryEntity.set_phase1_lat1(str_lat1);
        newEntryEntity.set_phase1_lat2(str_lat2);
        newEntryEntity.set_phase1_longi(str_long);
        newEntryEntity.set_phase1_longi1(str_long1);
        newEntryEntity.set_phase1_longi2(str_long2);
        newEntryEntity.set_Farmer_Name(_farmer_name);
        newEntryEntity.set_chng_Remarks(_chng_remarks);
        newEntryEntity.set_revenue_village(_village_name);
        newEntryEntity.set_tentative_cce_date(_tentative_date);
        newEntryEntity.set_Type_List_Id(var_spn_type_id);
        newEntryEntity.set_Type_List_NM(var_spn_type_nm);
        newEntryEntity.set_phase1_Entry_date(Utilitties.getCurrentDate());

        id = new DataBaseHelper(KhesraEntryActivity.this).InsertNewEntry(KhesraEntryActivity.this, newEntryEntity);

        if (id > 0) {
            Toast.makeText(getApplicationContext(), "डेटा सफलतापूर्वक सहेजा गया", Toast.LENGTH_LONG).show();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("index", Long.toString(id)).commit();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("KeyId", Long.toString(id)).commit();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("edited", isEdit).commit();
            Intent intent = new Intent(getApplicationContext(), PreHomeActivity.class);

            finish();

        } else {
            Toast.makeText(getApplicationContext(), "डेटा सहेजा नहीं गया", Toast.LENGTH_LONG).show();
        }
    }

    public void updatedNewEntryPhase1() {

        long id = 0;
        SetValue();

        DataBaseHelper placeData = new DataBaseHelper(KhesraEntryActivity.this);

        NewLandKhesraInfo newEntryEntity = new NewLandKhesraInfo();
        newEntryEntity.set_phase1_id(keyid);
        newEntryEntity.set_phase1_userid(UserId);
        newEntryEntity.set_phse1_agri_yr(var_spn_agri_id);
        newEntryEntity.set_phse1_agri_yr_nm(var_spn_agri_year);
        newEntryEntity.set_phase1_season(st_spn_season_id_new);
        newEntryEntity.set_phase1_season_nm(var_spn_season_nm);
        newEntryEntity.set_phase1_croptype(var_spn_croptyp);
        newEntryEntity.set_phase1_crop_name(var_spn_croptype_nm);
        newEntryEntity.set_phase1_dist(distcode);
        newEntryEntity.set_phase1_block(blockcode);
        newEntryEntity.set_phase1_panchayat(var_spn_panchayat_code);
        newEntryEntity.set_phase1_panchayat_nm(var_spn_panchayat_name);
        newEntryEntity.set_phase1_highest_plot_no(_Plot_no);
        newEntryEntity.set_phase1_alloted_khesra_no(_alloted_khesra);
        newEntryEntity.set_chng_khesra_no(_varChecklist_Id);
        newEntryEntity.set_chng_khesra_no_nm(_varChecklist_Name);
        newEntryEntity.set_phase1_final_khesra_no(_final_khesra);

        newEntryEntity.setNazriNkasha(str_imagcap1);
        newEntryEntity.setFieldImg(str_imagcap);
        newEntryEntity.setFinalSelectedField(str_imagcap2);
        newEntryEntity.set_phase1_lat(str_lat);
        newEntryEntity.set_phase1_lat1(str_lat1);
        newEntryEntity.set_phase1_lat2(str_lat2);
        newEntryEntity.set_phase1_longi(str_long);
        newEntryEntity.set_phase1_longi1(str_long1);
        newEntryEntity.set_phase1_longi2(str_long2);
        newEntryEntity.set_Farmer_Name(_farmer_name);
        newEntryEntity.set_chng_Remarks(_chng_remarks);
        newEntryEntity.set_revenue_village(_village_name);
        newEntryEntity.set_tentative_cce_date(_tentative_date);
        newEntryEntity.set_Type_List_Id(var_spn_type_id);
        newEntryEntity.set_Type_List_NM(var_spn_type_nm);

        id = new DataBaseHelper(KhesraEntryActivity.this).updateNewEntryKhesradetails(KhesraEntryActivity.this, newEntryEntity);

        if (id > 0) {
            Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_LONG).show();

            finish();

        } else {
            Toast.makeText(getApplicationContext(), "अपडेट नहीं किया गया", Toast.LENGTH_LONG).show();
        }


    }


    public void ShowEditEntryKhesra(String keyid) {

        UserId = PreferenceManager.getDefaultSharedPreferences(KhesraEntryActivity.this).getString("USERID", "");
        LandInfoList = dataBaseHelper.getAllChngKhesraDetails(UserId, keyid);

        img_pic_field.setEnabled(true);
        img_pic_naksha.setEnabled(true);
        img_pic_finalplot.setEnabled(true);
        button_save.setEnabled(true);
        viewIMG.setVisibility(View.GONE);
        viewIMG1.setVisibility(View.GONE);
        viewIMG2.setVisibility(View.GONE);
        for (NewLandKhesraInfo dewormingEntity : LandInfoList) {

            button_save.setText("UPDATE");

            //   _spin_agri_yr = dewormingEntity.get_phse1_agri_yr_nm();
            _spin_agri_yr = dataBaseHelper.getNameFor("Financial_Year", "Year_id", "F_year", dewormingEntity.get_phse1_agri_yr());
            _spin_type_List = dewormingEntity.get_Type_List_NM();

            _spin_season = dataBaseHelper.getNameFor("Season", "season_id", "season_name", dewormingEntity.get_phase1_season());
            // _spin_crop_type = dewormingEntity.get_phase1_crop_name();

            _spin_crop_type = dataBaseHelper.getNameFor("CropType", "Crop_Id", "Crop_Name", dewormingEntity.get_phase1_croptype());

            //   _spin_panchayat = dewormingEntity.get_phase1_panchayat_nm();

            _spin_panchayat = dataBaseHelper.getNameFor("Panchayat", "Panchayat_Code", "Panchayat_Name", dewormingEntity.get_phase1_panchayat());

            // _spin_chng_khesra = dewormingEntity.get_chng_khesra_no();
            _spin_chng_khesra = dataBaseHelper.getNameFor("CheckList", "Chk_Id", "Chk_Name", dewormingEntity.get_chng_khesra_no());

//            et_dist_new.setText(dewormingEntity.get_phase1_dist());
//            et_block_new.setText(dewormingEntity.get_phase1_block());
            et_PlotNo_new.setText(dewormingEntity.get_phase1_highest_plot_no());
            et_allotedKhesra_NO.setText(dewormingEntity.get_phase1_alloted_khesra_no());
            et_finalKhesra_NO.setText(dewormingEntity.get_phase1_final_khesra_no());
            et_farmerName.setText(dewormingEntity.get_Farmer_Name());
            et_chng_remarks.setText(dewormingEntity.get_chng_Remarks());
            et_revenue_village_new.setText(dewormingEntity.get_revenue_village());
            et_tentative_date_cce.setText(dewormingEntity.get_tentative_cce_date());

            str_lat = dewormingEntity.get_phase1_lat();
            str_lat1 = dewormingEntity.get_phase1_lat1();
            str_lat2 = dewormingEntity.get_phase1_lat2();

            str_long = dewormingEntity.get_phase1_longi();
            str_long1 = dewormingEntity.get_phase1_longi1();
            str_long2 = dewormingEntity.get_phase1_longi2();

            flag = dewormingEntity.get_phase1_flag();


            ThumbnailSize = 100;

            if (dewormingEntity != null) {

                str_img = "N";

                str_imagcap = dewormingEntity.getFieldImg();
                str_imagcap1 = dewormingEntity.getNazriNkasha();
                str_imagcap2 = dewormingEntity.getFinalSelectedField();


                if (_spin_chng_khesra.equalsIgnoreCase("No")) {
                    imageData4 = Base64.decode(str_imagcap2, Base64.DEFAULT);

                    if (!imageData4.equals(null)) {
                        Bitmap bmp2 = BitmapFactory.decodeByteArray(imageData4, 0, imageData4.length);
                        img_pic_finalplot.setScaleType(ImageView.ScaleType.FIT_XY);
                        img_pic_finalplot.setImageBitmap(Utilitties.GenerateThumbnail(bmp2, ThumbnailSize, ThumbnailSize));
                        viewIMG2.setVisibility(View.VISIBLE);

                    }
                } else if (_spin_chng_khesra.equalsIgnoreCase("Yes")) {
                    if (str_imagcap != null) {
                        imageData2 = Base64.decode(str_imagcap, Base64.DEFAULT);
                    }


                    if (str_imagcap1 != null) {
                        imageData3 = Base64.decode(str_imagcap1, Base64.DEFAULT);
                    }
                    if (str_imagcap2 != null) {
                        imageData4 = Base64.decode(str_imagcap2, Base64.DEFAULT);
                    }


                    if (!imageData2.equals(null)) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(imageData2, 0, imageData2.length);
                        img_pic_field.setScaleType(ImageView.ScaleType.FIT_XY);
                        img_pic_field.setImageBitmap(Utilitties.GenerateThumbnail(bmp, ThumbnailSize, ThumbnailSize));
                        viewIMG.setVisibility(View.VISIBLE);

                    }
                    if (!imageData3.equals(null)) {
                        Bitmap bmp1 = BitmapFactory.decodeByteArray(imageData3, 0, imageData3.length);
                        img_pic_naksha.setScaleType(ImageView.ScaleType.FIT_XY);
                        img_pic_naksha.setImageBitmap(Utilitties.GenerateThumbnail(bmp1, ThumbnailSize, ThumbnailSize));
                        viewIMG1.setVisibility(View.VISIBLE);
                    }

                    if (!imageData4.equals(null)) {
                        Bitmap bmp2 = BitmapFactory.decodeByteArray(imageData4, 0, imageData4.length);
                        img_pic_finalplot.setScaleType(ImageView.ScaleType.FIT_XY);
                        img_pic_finalplot.setImageBitmap(Utilitties.GenerateThumbnail(bmp2, ThumbnailSize, ThumbnailSize));
                        viewIMG2.setVisibility(View.VISIBLE);

                    }
                }


            }

        }
    }

    public void onClick_ViewImg1(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("-Field overview which is to be changed-");


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (imageData2 != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageData2, 0, imageData2.length);

            imgview.setImageBitmap(bmp);
        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    public void onClick_ViewImg2(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("-Nazri Naksha-");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (imageData3 != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageData3, 0, imageData3.length);

            imgview.setImageBitmap(bmp);
        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void onClick_ViewImg3(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("-Final Plot-");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (imageData4 != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageData4, 0, imageData4.length);

            imgview.setImageBitmap(bmp);
        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    public void ShowDialog() {


        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(KhesraEntryActivity.this,
                mDateSetListener, mYear, mMonth, mDay);

        // datedialog.getDatePicker().setMinDate(System.currentTimeMillis());

        if (c.getTimeInMillis() < System.currentTimeMillis()) {

            datedialog.getDatePicker().setMinDate(c.getTimeInMillis());
        } else {
            datedialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }

        // datedialog.getDatePicker().setMinDate(min.getTime());
        datedialog.show();

    }


    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            String ds = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            ds = ds.replace("/", "-");
            String[] separated = ds.split(" ");

            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());

                String smDay = "" + mDay, smMonth = "" + (mMonth + 1);
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }


                et_tentative_date_cce.setText(mYear + "-" + smMonth + "-" + smDay);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                //_ed_dob = mYear + smMonth + smDay;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };


}
