package in.nic.bih.cropcutting.activity.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Entity.CropType;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.KhesraNo_List;
import in.nic.bih.cropcutting.activity.Entity.LandType;
import in.nic.bih.cropcutting.activity.Entity.Manure_type;
import in.nic.bih.cropcutting.activity.Entity.Panchayat_List;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.Entity.ShapeCceArea;
import in.nic.bih.cropcutting.activity.Entity.Source_Seed;
import in.nic.bih.cropcutting.activity.Entity.System_Cutivation;
import in.nic.bih.cropcutting.activity.Entity.TypeList;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;
import in.nic.bih.cropcutting.activity.Entity.Varities_of_Crop;
import in.nic.bih.cropcutting.activity.Entity.Weather_Condition;
import in.nic.bih.cropcutting.activity.Utilities.MarshmallowPermission;

public class BasicDetails extends AppCompatActivity {
    private final static int CAMERA_PIC = 99;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    public static String str_latitude = "", str_longitude = "";
    static ArrayList<String> CropList, panchayatstlist;

    //Location
    final String TAG = "GPS";
    String isEdit = "";
    String var_surveyNo = "";
    LinearLayout lin_geenWeight, lin_dryWeight, li_latitude, lin_noOfMaize, lin_weightOFMaize, lin_greenweightOfMaize, lin_dryWeightOfMaize, lin_quantity_of_used_seed, lin_source_of_seed, lin_order_of_experiment, lin_subtype_cropVarities;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv_used_Seed, tv_operationalSize, tv_areaCoverage, tv_green_weight;
    EditText et_log, et_lat, et_dist, et_blk, et_plotNo, et_village, et_farmername, et_operation_size, et_crop_area, et_irrigation, et_quantity_used_manure, et_quantity_seed, et_extend_damage_paste, et_remarks, et_randomNo, et_length, et_breath, et_date, et_greenWeight, et_DryWeight, et_No_of_baal, et_weight_of_baal, et_green_weight_of_dana, et_dry_weight_of_dana, et_supervisor_name, et_supervisor_mob, et_supervisor_designation, et_observer_name, et_observer_mob_no, et_observer_designation, et_remarks1, et_shape_of_area;
    Spinner spn_agri_year, spnn_season, spnn_crop, spn_dist, spn_blk, spn_pnchayat, spn_unit_operationalsize_holding, spn_SurveyNo, spn_unit_areaCoverage_crop, spn_cutivtion, spn_crop_type_varities, spn_subtype_of_varities_of_crop, spn_manure_type, spn_source_seed, spn_unit_used_seed, spn_weather_at_crop_season, spn_order_of_experiment, spn_CCE_shape, spn_land_type, spn_observer_present, spn_supervisor_present;
    Button btn_save;
    String agrival;
    String cropval;
    ImageView img_date;
    ImageView sync_khesra;
    String st_log, st_lat, st_agri_year, st_plot_no, st_village, st_survey_no, st_farmername, st_operational_size, st_crop_area, st_irrigation, st_quantity_used_manure, st_quantity_used_seed, st_extend_damage_paste, st_remarks, st_randomNo, st_length, st_breath, st_date, st_greenweight, st_dryweight, st_no_of_baal, st_weight_of_baal, st_green_weight_of_dana, st_dry_weight_of_dana, st_supervisor_name, st_supervisor_mobile, st_supervisor_designation, st_observer_nm, st_observer_mob, st_observer_designation, st_remarks1;
    String st_spn_agri_year, st_spn_season_nm, st_spn_seasn_flag, st_spn_croptype_nm, st_spn_dist_nm, st_spn_block_name, st_spn_panchayat_name, st_spn_cutivation_name, st_spn_crop_type_varities_name, st_spn_manure_type_id, st_spn_manure_type_name, st_spn_source_seed_name, st_spn_weather_at_crop_season_name, st_spn_operational_size, st_spn_area_coverage, st_spn_land_type_id, st_spn_land_type, st_spn_crop_name;
    String st_survey_code = "", st_survey_code_name = "";
    ArrayList<String> OrderOfExperimentlist = new ArrayList<String>();
    ArrayList<String> SubTypeVaritiesCroplist = new ArrayList<String>();
    ArrayList<String> Observerpresentlist = new ArrayList<String>();
    ArrayList<String> SupervisorPresentlist = new ArrayList<String>();
    ArrayList<String> UnitOfOperationalsize = new ArrayList<String>();
    ArrayList<String> UnitOfCoverageAreaCrop = new ArrayList<String>();
    String unit_operationalsize_holding = "", observer_present = "", supervisior_present = "", typeofland = "", orderofexperiment = "", whethercondition = "", sourceofseed = "", typeofmanure = "", varityofcrop = "", subvarity_ofcrop = "", systemofcultivation = "", unitoftheareacovgcrop = "";
    ProgressBar progress_finding_location, progress_finding_location2;
    String seasonval = "";
    String sourceofseed_name = "";
    String typeofmanure_name = "";
    String cultivation_name = "";
    String varitiescrop_name = "";
    String sub_varitiescrop_name = "";
    String weatheratcrop = "";
    String cceshape = "";
    String weatherconditionname = "";
    String spn_unitoperationalsize = "";
    Calendar myCalendar = Calendar.getInstance();
    DataBaseHelper dataBaseHelper;
    ArrayList<Financial_Year> FYearList = new ArrayList<Financial_Year>();
    ArrayList<Season_List> SeasonList = new ArrayList<Season_List>();
    ArrayList<CropType> cropList = new ArrayList<CropType>();
    ArrayList<Source_Seed> SourceSeedList = new ArrayList<Source_Seed>();
    ArrayList<Panchayat_List> PanchayatList = new ArrayList<Panchayat_List>();
    ArrayList<KhesraNo_List> KhesraList = new ArrayList<KhesraNo_List>();
    ArrayList<System_Cutivation> Cutivation_list = new ArrayList<System_Cutivation>();
    ArrayList<Weather_Condition> WeatherList = new ArrayList<Weather_Condition>();
    ArrayList<Varities_of_Crop> CropVaritiesList = new ArrayList<Varities_of_Crop>();
    ArrayList<Manure_type> ManureTypeList = new ArrayList<Manure_type>();
    ArrayList<ShapeCceArea> CceList = new ArrayList<ShapeCceArea>();
    ArrayList<LandType> LandList = new ArrayList<LandType>();
    ArrayList<String> OrderOfexperList = new ArrayList<>();
    ArrayList<String> SubVaritiesOfCropList = new ArrayList<>();
    ArrayList<String> SupervisorPresent = new ArrayList<>();
    ArrayList<String> ObserverPresent = new ArrayList<>();
    ArrayList<String> UnitOperationalSize = new ArrayList<>();
    ArrayList<String> UnitCoverageArea = new ArrayList<>();
    ArrayList<String> Khesra = new ArrayList<>();
    ArrayList<String> FyearArray;
    ArrayList<String> SeasonArray;
    ArrayList<String> CropArray = new ArrayList<String>();
    ArrayList<String> SourceSeedArray;
    ArrayList<String> cutivationarray;
    ArrayList<String> weatherArray;
    ArrayList<String> cropVaritiesArray;
    ArrayList<String> manureTypeArray;
    ArrayList<String> cceShapeArray;
    ArrayList<String> landTypeArray;
    ArrayList<String> khesraArray;
    ArrayAdapter<String> ManureTypeadapter;
    ArrayAdapter<String> Fyearadapter;
    ArrayAdapter<String> Seasonadapter;
    ArrayAdapter<String> Cropadapter;
    ArrayAdapter<String> SourceSeedadapter;
    ArrayAdapter<String> panchayatadapter;
    ArrayAdapter<String> cutivationadapter;
    ArrayAdapter<String> weatheradapter;
    ArrayAdapter<String> cropVaritiesadapter;
    ArrayAdapter<String> cceShapeadapter;
    ArrayAdapter<String> landtypeadapter;
    ArrayAdapter<String> khesraadapter;
    ArrayAdapter<String> typadapter;
    ImageView img1, img2, img3, img4;
    MarshmallowPermission marshMallowPermission;
    String str_img1 = "", str_img2 = "", str_img3 = "", str_img4 = "";
    String UserId = "", password = "";
    UserLogin userLogin;
    String distnm = "";
    String blocknm = "";
    String distcode = "";
    String blockcode = "";
    Intent imageData;
    ArrayList<BasicInfo> EntryList = new ArrayList<>();
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    BasicInfo basicInfo = new BasicInfo();
    String keyid = "";
    boolean edit;
    String _panCode = null;
    String _croptype = null;
    String _farmer_Name = "", _village_name = "", _highestPlot = "";
    DatePickerDialog datedialog;
    Spinner spn_type, spn_bundles;
    String _spin_type_List = "";
    ArrayList<String> TypeArray = new ArrayList<String>();
    ArrayList<TypeList> TypeList = new ArrayList<TypeList>();
    LinearLayout ll_typeList, ll_dry_fiber_wt, ll_bundles, ll_weightBunldes, lin_weightofBundles;
    String spn_type_val = "", st_spn_type_id = "", st_spn_type_nm = "";
    String noOfBunlesArray[] = {"Select", "1", "2", "3", "4", "5", "6", "7", "8"};
    String noOfBunles = "";
    EditText et_bundle1, et_bundle2, et_bundle3, et_bundle4, et_bundle5, et_bundle6, et_bundle7, et_bundle8, et_total_bundle_weight;
    LinearLayout ll_podweightafter_plucking, ll_podweightafter_threshing, ll_maizeweightafter_plucking, ll_maizeweightafter_threshing, ll_total_wt,ll_green_wight,ll_irrigation_source;
    TextView tv_dry_weight;
    EditText et_podweightafter_plucking, et_podweightafter_threshing, et_maizeweightafter_plucking, et_maizeweightafter_threshing, et_dry_weight_fiber;
    EditText et_green_weight_of_daana;
    Double total_sum_bundle = 0.0;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {

                int answer = Integer.parseInt(et_bundle1.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {
                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {
                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim()) +
                        Integer.parseInt(et_bundle3.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {


                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim()) +
                        Integer.parseInt(et_bundle3.getText().toString().trim()) +
                        Integer.parseInt(et_bundle4.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {


                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim()) +
                        Integer.parseInt(et_bundle3.getText().toString().trim()) +
                        Integer.parseInt(et_bundle4.getText().toString().trim());
                Integer.parseInt(et_bundle5.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {


                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim()) +
                        Integer.parseInt(et_bundle3.getText().toString().trim()) +
                        Integer.parseInt(et_bundle4.getText().toString().trim());
                Integer.parseInt(et_bundle5.getText().toString().trim());
                Integer.parseInt(et_bundle6.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {


                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim()) +
                        Integer.parseInt(et_bundle3.getText().toString().trim()) +
                        Integer.parseInt(et_bundle4.getText().toString().trim());
                Integer.parseInt(et_bundle5.getText().toString().trim());
                Integer.parseInt(et_bundle6.getText().toString().trim());
                Integer.parseInt(et_bundle7.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else if (!TextUtils.isEmpty(et_bundle1.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle2.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle3.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle4.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle5.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle6.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle7.getText().toString().trim())
                    && !TextUtils.isEmpty(et_bundle8.getText().toString().trim())
            ) {


                int answer = Integer.parseInt(et_bundle1.getText().toString().trim()) +
                        Integer.parseInt(et_bundle2.getText().toString().trim()) +
                        Integer.parseInt(et_bundle3.getText().toString().trim()) +
                        Integer.parseInt(et_bundle4.getText().toString().trim());
                Integer.parseInt(et_bundle5.getText().toString().trim());
                Integer.parseInt(et_bundle6.getText().toString().trim());
                Integer.parseInt(et_bundle7.getText().toString().trim());
                Integer.parseInt(et_bundle8.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                et_total_bundle_weight.setText(String.valueOf(answer));
            } else {
                et_total_bundle_weight.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private String st_spn_agri_id = "", st_spn_season_id = "", st_spn_croptyp = "", st_spn_dist_code = "", st_spn_bolck_code = "", st_spn_panchayat_code = "", st_spn_cutivation_id = "", st_spn_crop_type_varities_id = "", st_spn_sub_type_cropVarities, st_spn_source_seed_id = "", st_spn_weather_at_cop_season_id = "", st_spn_order_of_experiment = "", st_spn_cceshape_name = "", st_spn_super_prent = "", st_spn_observer_present = "", st_spn_cce_shape = "", st_spn_unit_seed = "";
    private int mYear, mMonth, mDay;
    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            String ds = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            ds = ds.replace("/", "-");
            String[] separated = ds.split(" ");

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());

                String smDay = "" + mDay, smMonth = "" + (mMonth + 1);
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }


                et_date.setText(mYear + "-" + smMonth + "-" + smDay);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                //_ed_dob = mYear + smMonth + smDay;

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        Initialization();
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, noOfBunlesArray);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_bundles.setAdapter(adaptor);

        et_farmername.setEnabled(false);
        et_village.setEnabled(false);
        et_plotNo.setEnabled(false);
        sync_khesra.setEnabled(false);


        et_greenWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (et_greenWeight.getText().toString().length() > 0) {
                        et_greenWeight.setText(getRoundFigure(et_greenWeight.getText().toString()));
                    }
                }
            }
        });


        et_DryWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (et_DryWeight.getText().toString().length() > 0) {
                        et_DryWeight.setText(getRoundFigure(et_DryWeight.getText().toString()));
                    }
                }
            }
        });

        et_green_weight_of_dana.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (et_green_weight_of_dana.getText().toString().length() > 0) {
                        et_green_weight_of_dana.setText(getRoundFigure(et_green_weight_of_dana.getText().toString()));
                    }
                }
            }
        });

        et_dry_weight_of_dana.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (et_dry_weight_of_dana.getText().toString().length() > 0) {
                        et_dry_weight_of_dana.setText(getRoundFigure(et_dry_weight_of_dana.getText().toString()));
                    }
                }
            }
        });

        spn_agri_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_agri_id = FYearList.get(pos - 1).getYear_Id();
                    st_spn_agri_year = FYearList.get(pos - 1).getFinancial_year();

                    sync_khesra.setEnabled(true);
                    if (!(getIntent().hasExtra("KeyId"))) {
                        KhesraList = dataBaseHelper.getKhesraLocal(UserId, st_spn_agri_id);
                        if (KhesraList.size() <= 0) {
                            new KhesraList().execute();
                        }
                    }

                } else {
                    st_spn_agri_id = "";
                    st_spn_agri_year = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        sync_khesra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new KhesraList().execute();

            }
        });

        spnn_season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_season_id = SeasonList.get(pos - 1).getSeason_Id();
                    st_spn_season_nm = SeasonList.get(pos - 1).getSeason_Name();
                    st_spn_seasn_flag = SeasonList.get(pos - 1).getFlag();

                    cropList = dataBaseHelper.getCropTypeLocal(st_spn_season_id);
                    if (cropList.size() <= 0) {
                        new CROPTYPE().execute();
                    } else {
                        loadCropType();
                    }
                    if (st_spn_season_id.equals("1")) {
                        et_shape_of_area.setText("Rectangle");
                    }

                } else {
                    spnn_crop.setSelection(0);
                    spn_type.setSelection(0);
                    st_spn_season_id = "";
                    st_spn_season_nm = "";
                    st_spn_seasn_flag = "";
                    loadCropType();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        spnn_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {

                    st_spn_croptyp = cropList.get(pos - 1).getCropId();
                    st_spn_croptype_nm = cropList.get(pos - 1).getCropName();

                    loadPanchayatData(PanchayatList);
                    loadTypeList();
                    if (st_spn_season_id.equals("1") && st_spn_croptyp.equals("1")) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        ll_green_wight.setVisibility(View.VISIBLE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        lin_order_of_experiment.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("1") && st_spn_croptyp.equals("18")) {
                        lin_geenWeight.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        tv_dry_weight.setText("Weight Of Dry daana");
                        tv_dry_weight.setHint("Weight Of Dry daana");
                        ll_podweightafter_plucking.setVisibility(View.VISIBLE);
                        ll_podweightafter_threshing.setVisibility(View.VISIBLE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        ll_green_wight.setVisibility(View.VISIBLE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("1") && (st_spn_croptyp.equals("17"))) {
                        lin_geenWeight.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.VISIBLE);
                        ll_maizeweightafter_threshing.setVisibility(View.VISIBLE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("1") && st_spn_croptyp.equals("2")) {
                        lin_noOfMaize.setVisibility(View.VISIBLE);
                        lin_weightOFMaize.setVisibility(View.VISIBLE);
                        lin_greenweightOfMaize.setVisibility(View.VISIBLE);
                        lin_dryWeightOfMaize.setVisibility(View.VISIBLE);
                        et_No_of_baal.setVisibility(View.VISIBLE);
                        et_weight_of_baal.setVisibility(View.VISIBLE);
                        et_green_weight_of_dana.setVisibility(View.VISIBLE);
                        et_dry_weight_of_dana.setVisibility(View.VISIBLE);
                        lin_geenWeight.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.GONE);
                        et_greenWeight.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        lin_order_of_experiment.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("3")) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_subtype_cropVarities.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        lin_order_of_experiment.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("4")) {
                        lin_noOfMaize.setVisibility(View.VISIBLE);
                        lin_weightOFMaize.setVisibility(View.VISIBLE);
                        lin_greenweightOfMaize.setVisibility(View.VISIBLE);
                        lin_dryWeightOfMaize.setVisibility(View.VISIBLE);
                        et_No_of_baal.setVisibility(View.VISIBLE);
                        et_weight_of_baal.setVisibility(View.VISIBLE);
                        et_green_weight_of_dana.setVisibility(View.VISIBLE);
                        et_dry_weight_of_dana.setVisibility(View.VISIBLE);
                        lin_geenWeight.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.GONE);
                        et_greenWeight.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        lin_order_of_experiment.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("2") && (st_spn_croptyp.equals("5") || st_spn_croptyp.equals("21") || st_spn_croptyp.equals("22"))) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("11")) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("6")) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("7")) {
                        lin_quantity_of_used_seed.setVisibility(View.GONE);
                        lin_source_of_seed.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.GONE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.GONE);
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_typeList.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("8")) {
                        lin_quantity_of_used_seed.setVisibility(View.GONE);
                        lin_source_of_seed.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.GONE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.GONE);
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Weight of Potato without soil");
                        ll_typeList.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("1") && ((st_spn_croptyp.equals("12")))) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_typeList.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("1") && ((st_spn_croptyp.equals("19")) || (st_spn_croptyp.equals("20")))) {

                        ll_irrigation_source.setVisibility(View.VISIBLE);
                        lin_geenWeight.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.VISIBLE);
                        ll_green_wight.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.GONE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.GONE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_typeList.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                    } else if (st_spn_season_id.equals("2") && st_spn_croptyp.equals("10")) {

                        lin_quantity_of_used_seed.setVisibility(View.GONE);
                        lin_source_of_seed.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.GONE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.GONE);
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Weight of Onion");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);

                    } else if (st_spn_season_id.equals("1") && st_spn_croptyp.equals("16")) {
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        ll_typeList.setVisibility(View.VISIBLE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("3") && st_spn_croptyp.equals("14")) {
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_geenWeight.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        ll_typeList.setVisibility(View.GONE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.VISIBLE);
                        ll_podweightafter_threshing.setVisibility(View.VISIBLE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        tv_dry_weight.setText("Weight Of Dry daana");
                        tv_dry_weight.setHint("Weight Of Dry daana");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("3") && st_spn_croptyp.equals("13")) {
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        ll_typeList.setVisibility(View.VISIBLE);
                        et_shape_of_area.setText("Rectangle");
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("3") && st_spn_croptyp.equals("15")) {
                        lin_noOfMaize.setVisibility(View.VISIBLE);
                        lin_weightOFMaize.setVisibility(View.VISIBLE);
                        lin_greenweightOfMaize.setVisibility(View.VISIBLE);
                        lin_dryWeightOfMaize.setVisibility(View.VISIBLE);
                        et_No_of_baal.setVisibility(View.VISIBLE);
                        et_weight_of_baal.setVisibility(View.VISIBLE);
                        et_green_weight_of_dana.setVisibility(View.VISIBLE);
                        et_dry_weight_of_dana.setVisibility(View.VISIBLE);
                        lin_geenWeight.setVisibility(View.GONE);
                        lin_dryWeight.setVisibility(View.GONE);
                        et_greenWeight.setVisibility(View.GONE);
                        et_DryWeight.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_typeList.setVisibility(View.VISIBLE);
                        et_shape_of_area.setText("Rectangle");
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else if (st_spn_season_id.equals("2") && (st_spn_croptyp.equals("9"))) {
                        lin_geenWeight.setVisibility(View.VISIBLE);
                        lin_dryWeight.setVisibility(View.VISIBLE);
                        et_greenWeight.setVisibility(View.VISIBLE);
                        et_DryWeight.setVisibility(View.VISIBLE);
                        lin_noOfMaize.setVisibility(View.GONE);
                        lin_weightOFMaize.setVisibility(View.GONE);
                        lin_greenweightOfMaize.setVisibility(View.GONE);
                        lin_dryWeightOfMaize.setVisibility(View.GONE);
                        et_No_of_baal.setVisibility(View.GONE);
                        et_weight_of_baal.setVisibility(View.GONE);
                        et_green_weight_of_dana.setVisibility(View.GONE);
                        et_dry_weight_of_dana.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        et_shape_of_area.setText("Square");
                        tv_green_weight.setText("Green Weight(In kg)");
                        ll_typeList.setVisibility(View.GONE);
                        ll_dry_fiber_wt.setVisibility(View.GONE);
                        ll_bundles.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    } else {
                        ll_typeList.setVisibility(View.GONE);
                        ll_podweightafter_plucking.setVisibility(View.GONE);
                        ll_podweightafter_threshing.setVisibility(View.GONE);
                        ll_maizeweightafter_plucking.setVisibility(View.GONE);
                        ll_maizeweightafter_threshing.setVisibility(View.GONE);
                        lin_order_of_experiment.setVisibility(View.GONE);
                        tv_dry_weight.setText("Dry Weight(in Kg)");
                        tv_dry_weight.setHint("Enter Dry Weight(in Kg)");
                        ll_green_wight.setVisibility(View.VISIBLE);
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    }

                } else {
                    st_spn_croptyp = "";
                    st_spn_croptype_nm = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_type_id = TypeList.get(pos - 1).getId();
                    st_spn_type_nm = TypeList.get(pos - 1).getName();
                    if (st_spn_croptyp.equals("16")&& st_spn_type_id.equals("8"))
                    {
                        ll_irrigation_source.setVisibility(View.GONE);
                    }
                    else {
                        ll_irrigation_source.setVisibility(View.VISIBLE);
                    }
                } else {
                    st_spn_type_id = "";
                    st_spn_type_nm = "";
                    ll_irrigation_source.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_cutivtion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_cutivation_id = Cutivation_list.get(pos - 1).getCutivation_id();
                    st_spn_cutivation_name = Cutivation_list.get(pos - 1).getCutivation_name();
                } else {
                    st_spn_cutivation_id = "";
                    st_spn_cutivation_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_crop_type_varities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_crop_type_varities_id = CropVaritiesList.get(pos - 1).getCrop_Varities_Id();
                    st_spn_crop_type_varities_name = CropVaritiesList.get(pos - 1).getCrop_Varities_Name();
                } else {
                    st_spn_crop_type_varities_id = "";
                    st_spn_crop_type_varities_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_manure_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_manure_type_id = ManureTypeList.get(pos - 1).getManure_type_id();
                    st_spn_manure_type_name = ManureTypeList.get(pos - 1).getManure_type_name();

                } else {
                    st_spn_manure_type_id = "";
                    st_spn_manure_type_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_source_seed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_source_seed_id = SourceSeedList.get(pos - 1).getSeed_id();
                    st_spn_source_seed_name = SourceSeedList.get(pos - 1).getSeed_Name();
                } else {
                    st_spn_source_seed_id = "";
                    st_spn_source_seed_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        spn_weather_at_crop_season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_weather_at_cop_season_id = WeatherList.get(pos - 1).getWeather_Id();
                    st_spn_weather_at_crop_season_name = WeatherList.get(pos - 1).getWeather_Name();
                } else {
                    st_spn_weather_at_cop_season_id = "";
                    st_spn_weather_at_crop_season_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });


        spn_pnchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_panchayat_code = PanchayatList.get(pos - 1).getPanchayat_code();
                    st_spn_panchayat_name = PanchayatList.get(pos - 1).getPanchayat_Name();


                    loadkhesralist(UserId, st_spn_season_id, st_spn_croptyp, st_spn_panchayat_code);

                } else {
                    st_spn_panchayat_code = "";
                    st_spn_panchayat_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_SurveyNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {


                    st_survey_code_name = KhesraList.get(pos - 1).getKhesraNm();

                    ArrayList data = getDetails(UserId, st_spn_season_id, st_spn_croptyp, st_spn_panchayat_code, st_survey_code_name);
                    if (data.size() > 0) {
                        _farmer_Name = data.get(0).toString();
                        _village_name = data.get(1).toString();
                        _highestPlot = data.get(2).toString();
                    }

                    et_farmername.setText(_farmer_Name);
                    et_village.setText(_village_name);
                    et_plotNo.setText(_highestPlot);
                } else {
                    st_survey_code_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_order_of_experiment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {

                    st_spn_order_of_experiment = OrderOfExperimentlist.get(position);
                    //  st_survey_no=et_surveyNo.getText().toString();
                    st_survey_no = st_survey_code_name;

                } else {
                    st_spn_order_of_experiment = "";
                    //  st_survey_no=et_surveyNo.getText().toString();
                    st_survey_no = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_subtype_of_varities_of_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {

                    st_spn_sub_type_cropVarities = SubTypeVaritiesCroplist.get(position);

                } else {
                    st_spn_sub_type_cropVarities = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        spn_unit_operationalsize_holding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_operational_size = UnitOfOperationalsize.get(position);
                    if (st_spn_operational_size.equals("Hectare")) {
                        tv_operationalSize.setText("  Operational Size holding of the Farmer (In Hectare).");

                    } else if (st_spn_operational_size.equals("Acre")) {
                        tv_operationalSize.setText("  Operational Size holding of the Farmer (In Acre).");

                    } else {
                        tv_operationalSize.setText("  Operational Size holding of the Farmer  .");

                    }

                } else {
                    st_spn_operational_size = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        spn_unit_areaCoverage_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_area_coverage = UnitOfCoverageAreaCrop.get(position);
                    if (st_spn_area_coverage.equals("Hectare")) {
                        tv_areaCoverage.setText("Area covered to the Crop (In Hectare).");

                    } else if (st_spn_area_coverage.equals("Acre")) {
                        tv_areaCoverage.setText("Area covered to the Crop (In Acre).");

                    } else {
                        tv_areaCoverage.setText("Area covered to the Crop .");

                    }

                } else {
                    st_spn_area_coverage = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        spn_land_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_land_type_id = LandList.get(pos - 1).getLand_Id();
                    st_spn_land_type = LandList.get(pos - 1).getLand_type();
                } else {
                    st_spn_land_type_id = "";
                    st_spn_land_type = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spn_bundles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                if (position > 0) {
                    noOfBunles = noOfBunlesArray[position];
                    if (noOfBunles.equals("1")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.GONE);
                        et_bundle3.setVisibility(View.GONE);
                        et_bundle4.setVisibility(View.GONE);
                        et_bundle5.setVisibility(View.GONE);
                        et_bundle6.setVisibility(View.GONE);
                        et_bundle7.setVisibility(View.GONE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("2")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.GONE);
                        et_bundle4.setVisibility(View.GONE);
                        et_bundle5.setVisibility(View.GONE);
                        et_bundle6.setVisibility(View.GONE);
                        et_bundle7.setVisibility(View.GONE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("3")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.VISIBLE);
                        et_bundle4.setVisibility(View.GONE);
                        et_bundle5.setVisibility(View.GONE);
                        et_bundle6.setVisibility(View.GONE);
                        et_bundle7.setVisibility(View.GONE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("4")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.VISIBLE);
                        et_bundle4.setVisibility(View.VISIBLE);
                        et_bundle5.setVisibility(View.GONE);
                        et_bundle6.setVisibility(View.GONE);
                        et_bundle7.setVisibility(View.GONE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("5")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.VISIBLE);
                        et_bundle4.setVisibility(View.VISIBLE);
                        et_bundle5.setVisibility(View.VISIBLE);
                        et_bundle6.setVisibility(View.GONE);
                        et_bundle7.setVisibility(View.GONE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("6")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.VISIBLE);
                        et_bundle4.setVisibility(View.VISIBLE);
                        et_bundle5.setVisibility(View.VISIBLE);
                        et_bundle6.setVisibility(View.VISIBLE);
                        et_bundle7.setVisibility(View.GONE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("7")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.VISIBLE);
                        et_bundle4.setVisibility(View.VISIBLE);
                        et_bundle5.setVisibility(View.VISIBLE);
                        et_bundle6.setVisibility(View.VISIBLE);
                        et_bundle7.setVisibility(View.VISIBLE);
                        et_bundle8.setVisibility(View.GONE);
                    } else if (noOfBunles.equals("8")) {
                        lin_weightofBundles.setVisibility(View.VISIBLE);
                        ll_total_wt.setVisibility(View.VISIBLE);
                        et_bundle1.setVisibility(View.VISIBLE);
                        et_bundle2.setVisibility(View.VISIBLE);
                        et_bundle3.setVisibility(View.VISIBLE);
                        et_bundle4.setVisibility(View.VISIBLE);
                        et_bundle5.setVisibility(View.VISIBLE);
                        et_bundle6.setVisibility(View.VISIBLE);
                        et_bundle7.setVisibility(View.VISIBLE);
                        et_bundle8.setVisibility(View.VISIBLE);
                    }

                } else {
                    noOfBunles = "";
                    lin_weightofBundles.setVisibility(View.GONE);
                    ll_total_wt.setVisibility(View.GONE);
                    et_bundle1.setVisibility(View.GONE);
                    et_bundle2.setVisibility(View.GONE);
                    et_bundle3.setVisibility(View.GONE);
                    et_bundle4.setVisibility(View.GONE);
                    et_bundle5.setVisibility(View.GONE);
                    et_bundle6.setVisibility(View.GONE);
                    et_bundle7.setVisibility(View.GONE);
                    et_bundle8.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        img_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BasicDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        UserId = PreferenceManager.getDefaultSharedPreferences(BasicDetails.this).getString("USERID", "");
        password = PreferenceManager.getDefaultSharedPreferences(BasicDetails.this).getString("PASWORD", "");
        userLogin = dataBaseHelper.getDetail(UserId);
        distnm = userLogin.getDistname();
        blocknm = userLogin.getBlkname();
        distcode = userLogin.getDistcode();
        blockcode = userLogin.getBlkcode();

        et_dist.setText(distnm);
        et_blk.setText(blocknm);


        dataBaseHelper = new DataBaseHelper(BasicDetails.this);


        FYearList = dataBaseHelper.getFinancialYearLocal();
        if (FYearList.size() <= 0) {
            new FINANCIALYEAR().execute();
        }


        SeasonList = dataBaseHelper.getSeasonLocal();
        if (SeasonList.size() <= 0) {
            new SEASON().execute();
        } else {
            loadSeason();
        }

        PanchayatList = dataBaseHelper.getPanchayatLocal(UserId);
        if (PanchayatList.size() <= 0) {
            new PANCHAYATDATA().execute();
        } else {
            loadPanchayatData(PanchayatList);
        }
        SourceSeedList = dataBaseHelper.getSourceseedlocal();
        if (SourceSeedList.size() <= 0) {
            new SOURCEOFSEED().execute();
        } else {
            loadSourceOfSeed();
        }

        Cutivation_list = dataBaseHelper.getCutivationLocal();
        if (Cutivation_list.size() <= 0) {
            new CUTIVATION().execute();
        } else {
            loadCutivationSpinner();
        }

        WeatherList = dataBaseHelper.getWeatherLocal();
        if (WeatherList.size() <= 0) {
            new WEATHERCONDITION().execute();
        } else {
            loadWeatherCondition();
        }
        CropVaritiesList = dataBaseHelper.getVaritiesCropLocal();
        if (CropVaritiesList.size() <= 0) {
            new CROPVARITIES().execute();
        } else {
            loadCRopVarities();
        }

        ManureTypeList = dataBaseHelper.getManureTypeLocal();
        if (ManureTypeList.size() <= 0) {
            new MANURETYPE().execute();
        } else {
            loadManureType();
        }

        LandList = dataBaseHelper.getLandTypeLocal();
        if (LandList.size() <= 0) {
            new LANDTYPE().execute();
        } else {
            loadLandType();
        }

        try {

            keyid = getIntent().getExtras().getString("KeyId");
            String isEdit = "";
            isEdit = getIntent().getExtras().getString("isEdit");
            Log.d("kvfrgv", "" + keyid + "" + isEdit);
            if (Integer.parseInt(keyid) > 0 && isEdit.equals("Yes")) {
                edit = true;
                ShowEditEntry(keyid);

            }

        } catch (Exception e) {
            Log.e("EXP", e.getLocalizedMessage());
            e.printStackTrace();
        }


        btn_save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (btn_save.getText().toString().equals("UPDATE")) {
                            String isvalid = Validate();

                            if (isvalid.equalsIgnoreCase("yes")) {
                                UpdateNewEntry();
                            }
                        } else {
                            String isvalid = Validate();
                            if (isvalid.equalsIgnoreCase("yes")) {
                                InsertNewEntry();
                            }
                        }


                    }

                });

        et_bundle1.addTextChangedListener(textWatcher);
        et_bundle2.addTextChangedListener(textWatcher);
        et_bundle3.addTextChangedListener(textWatcher);
        et_bundle4.addTextChangedListener(textWatcher);
        et_bundle5.addTextChangedListener(textWatcher);
        et_bundle6.addTextChangedListener(textWatcher);
        et_bundle7.addTextChangedListener(textWatcher);
        et_bundle8.addTextChangedListener(textWatcher);


    }

    public void Initialization() {

        tv_used_Seed = (TextView) findViewById(R.id.tv_usedSeed);
        tv_operationalSize = (TextView) findViewById(R.id.tv_operational_size);
        tv_areaCoverage = (TextView) findViewById(R.id.tv_area_coverage);
        tv_green_weight = (TextView) findViewById(R.id.tv_green_weight);

        spn_agri_year = (Spinner) findViewById(R.id.spn_agriculture_year);
        et_dist = (EditText) findViewById(R.id.et_dist);
        et_blk = (EditText) findViewById(R.id.et_block);
        et_plotNo = (EditText) findViewById(R.id.et_PlotNo);
        et_village = (EditText) findViewById(R.id.et_Village);
        spn_SurveyNo = (Spinner) findViewById(R.id.spn_SurveyNo);
        et_farmername = (EditText) findViewById(R.id.et_farmer_name);
        spn_unit_operationalsize_holding = (Spinner) findViewById(R.id.spn_unit_operationalSize);
        et_operation_size = (EditText) findViewById(R.id.et_operational_size_holding);
        spn_unit_areaCoverage_crop = (Spinner) findViewById(R.id.spn_unit_AreaCoverage);
        et_crop_area = (EditText) findViewById(R.id.et_crop_coverd_area);
        et_irrigation = (EditText) findViewById(R.id.et_irrigation_source);
        et_quantity_used_manure = (EditText) findViewById(R.id.et_manure_quantity);
        et_quantity_seed = (EditText) findViewById(R.id.et_quantity_Of_seed);
        et_extend_damage_paste = (EditText) findViewById(R.id.et_extend_damage);
        et_remarks = (EditText) findViewById(R.id.et_remarks);
        spnn_season = (Spinner) findViewById(R.id.spn_season);
        spnn_crop = (Spinner) findViewById(R.id.spn_crop_type);
        spn_pnchayat = (Spinner) findViewById(R.id.spn_panchayat);
        spn_cutivtion = (Spinner) findViewById(R.id.spn_cutivation);
        spn_crop_type_varities = (Spinner) findViewById(R.id.spn_type_crop);
        spn_subtype_of_varities_of_crop = (Spinner) findViewById(R.id.spn_subtype_of_varities_of_crop);
        spn_manure_type = (Spinner) findViewById(R.id.spn_manuretype);
        spn_source_seed = (Spinner) findViewById(R.id.spn_source_seed);
        spn_weather_at_crop_season = (Spinner) findViewById(R.id.spn_weather_at_crop_season);
        et_randomNo = (EditText) findViewById(R.id.et_random_no);
        et_length = (EditText) findViewById(R.id.et_length_of_field);
        et_breath = (EditText) findViewById(R.id.et_breath_of_field);
        et_date = (EditText) findViewById(R.id.et_date_of_cutting);
        et_greenWeight = (EditText) findViewById(R.id.et_green_weight);
        et_DryWeight = (EditText) findViewById(R.id.et_dry_weight);
        et_No_of_baal = (EditText) findViewById(R.id.et_no_of_baal);
        et_weight_of_baal = (EditText) findViewById(R.id.et_weight_of_baal);
        et_green_weight_of_dana = (EditText) findViewById(R.id.et_green_weight_of_daana);
        et_dry_weight_of_dana = (EditText) findViewById(R.id.et_dry_weight_of_dana);

        et_remarks1 = (EditText) findViewById(R.id.et_remarks1);
        et_total_bundle_weight = (EditText) findViewById(R.id.et_total_bundle_weight);
        et_total_bundle_weight.setEnabled(false);

        et_bundle1 = (EditText) findViewById(R.id.et_bundle1);
        et_bundle2 = (EditText) findViewById(R.id.et_bundle2);
        et_bundle3 = (EditText) findViewById(R.id.et_bundle3);
        et_bundle4 = (EditText) findViewById(R.id.et_bundle4);
        et_bundle5 = (EditText) findViewById(R.id.et_bundle5);
        et_bundle6 = (EditText) findViewById(R.id.et_bundle6);
        et_bundle7 = (EditText) findViewById(R.id.et_bundle7);
        et_bundle8 = (EditText) findViewById(R.id.et_bundle8);

        et_podweightafter_plucking = (EditText) findViewById(R.id.et_podweightafter_plucking);
        et_podweightafter_threshing = (EditText) findViewById(R.id.et_podweightafter_threshing);
        et_maizeweightafter_plucking = (EditText) findViewById(R.id.et_maizeweightafter_plucking);
        et_maizeweightafter_threshing = (EditText) findViewById(R.id.et_maizeweightafter_threshing);
        et_dry_weight_fiber = (EditText) findViewById(R.id.et_dry_weight_fiber);
        et_green_weight_of_daana = (EditText) findViewById(R.id.et_green_weight_of_daana);

        et_shape_of_area = (EditText) findViewById(R.id.et_shape_of_area);
        spn_order_of_experiment = (Spinner) findViewById(R.id.spn_order_of_experiment);
        spn_CCE_shape = (Spinner) findViewById(R.id.spn_shape_of_Cce);
        spn_land_type = (Spinner) findViewById(R.id.spn_type_of_land);
        img_date = (ImageView) findViewById(R.id.img_date);
        sync_khesra = (ImageView) findViewById(R.id.sync_khesra);

        btn_save = (Button) findViewById(R.id.button_save);
        lin_geenWeight = (LinearLayout) findViewById(R.id.lin_greenWeight);
        lin_dryWeight = (LinearLayout) findViewById(R.id.lin_dryWeight);
        lin_noOfMaize = (LinearLayout) findViewById(R.id.lin_noOfMaize);
        lin_weightOFMaize = (LinearLayout) findViewById(R.id.lin_weightOfmaize);
        lin_greenweightOfMaize = (LinearLayout) findViewById(R.id.lin_green_weight_of_dana);
        lin_dryWeightOfMaize = (LinearLayout) findViewById(R.id.lin_dry_weight_of_dana);
        progress_finding_location = (ProgressBar) findViewById(R.id.progress_finding_location);
        progress_finding_location2 = (ProgressBar) findViewById(R.id.progress_finding_location2);
        lin_source_of_seed = (LinearLayout) findViewById(R.id.lin_source_of_seed);
        lin_order_of_experiment = (LinearLayout) findViewById(R.id.lin_order_of_experiment);
        lin_quantity_of_used_seed = (LinearLayout) findViewById(R.id.lin_quantity_of_used_seed);
        lin_subtype_cropVarities = (LinearLayout) findViewById(R.id.lin_subtype_cropVarities);
        li_latitude = (LinearLayout) findViewById(R.id.li_latitude);
        ll_typeList = (LinearLayout) findViewById(R.id.ll_typeList);
        ll_green_wight = (LinearLayout) findViewById(R.id.ll_green_wight);
        ll_typeList.setVisibility(View.GONE);
        spn_type = findViewById(R.id.spn_type);

        ll_dry_fiber_wt = (LinearLayout) findViewById(R.id.ll_dry_fiber_wt);
        ll_bundles = (LinearLayout) findViewById(R.id.ll_bundles);
        lin_weightofBundles = (LinearLayout) findViewById(R.id.lin_weightofBundles);
        ll_podweightafter_plucking = (LinearLayout) findViewById(R.id.ll_podweightafter_plucking);
        ll_podweightafter_threshing = (LinearLayout) findViewById(R.id.ll_podweightafter_threshing);
        ll_maizeweightafter_plucking = (LinearLayout) findViewById(R.id.ll_maizeweightafter_plucking);
        ll_maizeweightafter_threshing = (LinearLayout) findViewById(R.id.ll_maizeweightafter_threshing);
        ll_total_wt = (LinearLayout) findViewById(R.id.ll_total_wt);
        ll_podweightafter_plucking.setVisibility(View.GONE);
        ll_podweightafter_threshing.setVisibility(View.GONE);
        ll_maizeweightafter_threshing.setVisibility(View.GONE);
        ll_maizeweightafter_plucking.setVisibility(View.GONE);
        ll_total_wt.setVisibility(View.GONE);
        //ll_weightBunldes = (LinearLayout) findViewById(R.id.ll_weightBunldes);
        spn_bundles = (Spinner) findViewById(R.id.spn_bundles);
        tv_dry_weight = (TextView) findViewById(R.id.tv_dry_weight);
        ll_irrigation_source = (LinearLayout) findViewById(R.id.ll_irrigation_source);

        loadFinancialYear();

        loadPanchayatData(PanchayatList);
        addSubTypeVaritiesOfCrop();
        addUnitOperationalSizeHolding();
        addUnitAreaCoverageCrop();
        loadCRopVarities();
        loadCutivationSpinner();
        loadManureType();
        loadSourceOfSeed();
        loadWeatherCondition();
        addItemsOnSpinner();
        loadLandType();


    }

    private void SetValue() {

        st_plot_no = et_plotNo.getText().toString();
        st_village = et_village.getText().toString();

        st_farmername = et_farmername.getText().toString();
        st_operational_size = et_operation_size.getText().toString();
        st_crop_area = et_crop_area.getText().toString();
        st_irrigation = et_irrigation.getText().toString();
        st_quantity_used_manure = et_quantity_used_manure.getText().toString();
        st_quantity_used_seed = et_quantity_seed.getText().toString();
        st_extend_damage_paste = et_extend_damage_paste.getText().toString();
        st_remarks = et_remarks.getText().toString();
        st_randomNo = et_randomNo.getText().toString();
        st_length = et_length.getText().toString();
        st_breath = et_breath.getText().toString();
        st_date = et_date.getText().toString();
        st_greenweight = et_greenWeight.getText().toString();
        st_dryweight = et_DryWeight.getText().toString();
        st_no_of_baal = et_No_of_baal.getText().toString();
        st_weight_of_baal = et_weight_of_baal.getText().toString();
        st_green_weight_of_dana = et_green_weight_of_dana.getText().toString();
        st_dry_weight_of_dana = et_dry_weight_of_dana.getText().toString();

        st_remarks1 = et_remarks1.getText().toString();
        st_spn_cce_shape = et_shape_of_area.getText().toString();

    }

    private String Validate() {

        String isvalid = "yes";

        if ((spn_agri_year != null && spn_agri_year.getSelectedItem() != null)) {
            if ((String) spn_agri_year.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(BasicDetails.this, "Please select agriculture year.", Toast.LENGTH_LONG).show();

                return "no";
            }
        }

        if ((spnn_season != null && spnn_season.getSelectedItem() != null)) {
            if ((String) spnn_season.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(BasicDetails.this, "Please select Season.", Toast.LENGTH_LONG).show();

                return "no";
            }
        }
        if ((spnn_crop != null && spnn_crop.getSelectedItem() != null)) {
            if ((String) spnn_crop.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(BasicDetails.this, "Please select CropType.", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

        if ((spn_pnchayat != null && spn_pnchayat.getSelectedItem() != null)) {
            if ((String) spn_pnchayat.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(BasicDetails.this, "Please select Panchayat.", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

        if ((spn_SurveyNo != null && spn_SurveyNo.getSelectedItem() != null)) {
            if ((String) spn_SurveyNo.getSelectedItem() != "-select-") {
                isvalid = "yes";
            } else {
                Toast.makeText(BasicDetails.this, "Please select Survey/Khesra No.", Toast.LENGTH_LONG).show();

                return "no";
            }
        }

        if (et_farmername.getText().toString().trim().length() <= 0) {
            Toast.makeText(BasicDetails.this, "Please enter farmers name.", Toast.LENGTH_LONG).show();
            et_farmername.requestFocus();
            return "no";
        }

        if ((st_spn_season_id.equals("3") && st_spn_croptyp.equals("15")) || (st_spn_season_id.equals("3") && st_spn_croptyp.equals("13")) || (st_spn_season_id.equals("1") && st_spn_croptyp.equals("16"))) {
            if ((spn_type != null && spn_type.getSelectedItem() != null)) {
                if ((String) spn_type.getSelectedItem() != "-select-") {
                    isvalid = "yes";
                } else {
                    Toast.makeText(BasicDetails.this, "Please select Type.", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }
        }
        if (st_spn_croptyp.equals("19")||st_spn_croptyp.equals("20")){
            if (noOfBunles.equals("1"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("2"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("3"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
                if (et_bundle3.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 3.", Toast.LENGTH_LONG).show();
                    et_bundle3.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("4"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
                if (et_bundle3.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 3.", Toast.LENGTH_LONG).show();
                    et_bundle3.requestFocus();
                    return "no";
                }
                if (et_bundle4.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 4.", Toast.LENGTH_LONG).show();
                    et_bundle4.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("5"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
                if (et_bundle3.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 3.", Toast.LENGTH_LONG).show();
                    et_bundle3.requestFocus();
                    return "no";
                }
                if (et_bundle4.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 4.", Toast.LENGTH_LONG).show();
                    et_bundle4.requestFocus();
                    return "no";
                }
                if (et_bundle5.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 5.", Toast.LENGTH_LONG).show();
                    et_bundle5.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("6"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
                if (et_bundle3.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 3.", Toast.LENGTH_LONG).show();
                    et_bundle3.requestFocus();
                    return "no";
                }
                if (et_bundle4.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 4.", Toast.LENGTH_LONG).show();
                    et_bundle4.requestFocus();
                    return "no";
                }
                if (et_bundle5.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 5.", Toast.LENGTH_LONG).show();
                    et_bundle5.requestFocus();
                    return "no";
                }
                if (et_bundle6.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 6.", Toast.LENGTH_LONG).show();
                    et_bundle6.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("7"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
                if (et_bundle3.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 3.", Toast.LENGTH_LONG).show();
                    et_bundle3.requestFocus();
                    return "no";
                }
                if (et_bundle4.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 4.", Toast.LENGTH_LONG).show();
                    et_bundle4.requestFocus();
                    return "no";
                }
                if (et_bundle5.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 5.", Toast.LENGTH_LONG).show();
                    et_bundle5.requestFocus();
                    return "no";
                }
                if (et_bundle6.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 6.", Toast.LENGTH_LONG).show();
                    et_bundle6.requestFocus();
                    return "no";
                }
                if (et_bundle7.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 7.", Toast.LENGTH_LONG).show();
                    et_bundle7.requestFocus();
                    return "no";
                }
            }
            else if (noOfBunles.equals("8"))
            {
                if (et_bundle1.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 1.", Toast.LENGTH_LONG).show();
                    et_bundle1.requestFocus();
                    return "no";
                }
                if (et_bundle2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 2.", Toast.LENGTH_LONG).show();
                    et_bundle2.requestFocus();
                    return "no";
                }
                if (et_bundle3.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 3.", Toast.LENGTH_LONG).show();
                    et_bundle3.requestFocus();
                    return "no";
                }
                if (et_bundle4.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 4.", Toast.LENGTH_LONG).show();
                    et_bundle4.requestFocus();
                    return "no";
                }
                if (et_bundle5.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 5.", Toast.LENGTH_LONG).show();
                    et_bundle5.requestFocus();
                    return "no";
                }
                if (et_bundle6.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 6.", Toast.LENGTH_LONG).show();
                    et_bundle6.requestFocus();
                    return "no";
                }
                if (et_bundle7.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 7.", Toast.LENGTH_LONG).show();
                    et_bundle7.requestFocus();
                    return "no";
                }
                if (et_bundle8.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BasicDetails.this, "Please enter weight in bundle 8.", Toast.LENGTH_LONG).show();
                    et_bundle8.requestFocus();
                    return "no";
                }
            }
        }


        return isvalid;

    }

    public void InsertNewEntry() {
        long c = 0;
        SetValue();
        DataBaseHelper placeData = new DataBaseHelper(BasicDetails.this);

        basicInfo.setUserid(UserId);
        basicInfo.setAgri_year(st_spn_agri_id);
        basicInfo.setAgri_year_nm(st_spn_agri_year);
        basicInfo.setSeason(st_spn_season_id);
        basicInfo.setCrop(st_spn_croptyp);
        basicInfo.setCrop_name(st_spn_croptype_nm);
        basicInfo.setDist(distcode);
        basicInfo.setBlock(blockcode);
        basicInfo.setPanchayat(st_spn_panchayat_code);
        basicInfo.setName_of_selected_village(st_village);
        basicInfo.setHighest_plot_no(st_plot_no);
        basicInfo.setSurvey_no_khesra_no(st_survey_code_name);
        basicInfo.setFarmer_name(st_farmername);
        basicInfo.setUnitOperationalSize(st_spn_operational_size);
        basicInfo.setOperation_size_holding(st_operational_size);
        basicInfo.setUnitareaCoverage(st_spn_area_coverage);
        basicInfo.setArea_covered_crop(st_crop_area);
        basicInfo.setSystem_of_cutivation(st_spn_cutivation_id);
        basicInfo.setSystemofcultivation_name(st_spn_cutivation_name);
        basicInfo.setVarities_of_crop(st_spn_crop_type_varities_id);
        basicInfo.setVaritiesofcrop_name(st_spn_crop_type_varities_name);

        basicInfo.setSub_varitiesofcrop_name(st_spn_sub_type_cropVarities);
        basicInfo.setIrrigation_source(st_irrigation);
        basicInfo.setType_of_manure(st_spn_manure_type_id);
        basicInfo.setTypeofmanure_name(st_spn_manure_type_name);
        basicInfo.setQuantity_of_used_manure(st_quantity_used_manure);
        basicInfo.setSource_of_seed(st_spn_source_seed_id);
        basicInfo.setSourceofseed_name(st_spn_source_seed_name);
        basicInfo.setQuantity_of_used_seed(st_quantity_used_seed);
        basicInfo.setWeather_condition_during_crop_season(st_spn_weather_at_cop_season_id);
        basicInfo.setWeatherconditionname(st_spn_weather_at_crop_season_name);
        basicInfo.setExtend_of_damage(st_extend_damage_paste);
        basicInfo.setRemarks(st_remarks);
        basicInfo.setRandom_no_alloted_by_dso(st_randomNo);
        if (st_spn_order_of_experiment.equalsIgnoreCase("<--select-->")) {
            basicInfo.setOrder_of_experiment("");
        } else {
            basicInfo.setOrder_of_experiment(st_spn_order_of_experiment);
        }

        basicInfo.setShape_of_cce_area(st_spn_cce_shape);
        basicInfo.setLenghth_of_field(st_length);
        basicInfo.setBreath_of_field(st_breath);

        basicInfo.setType_of_land(st_spn_land_type_id);
        basicInfo.setType_of_land_Name(st_spn_land_type);
        basicInfo.setDate_of_cutting(st_date);
        basicInfo.setGreen_weight(st_greenweight);
        basicInfo.setDry_weight(st_dryweight);
        basicInfo.setNo_of_baal(st_no_of_baal);
        basicInfo.setWeight_of_baal(st_weight_of_baal);
        basicInfo.setGreen_weight_of_dana(st_green_weight_of_dana);
        basicInfo.setDry_weight_of_dana(st_dry_weight_of_dana);

        basicInfo.setRemarks1(st_remarks1);
        basicInfo.setType_id(st_spn_type_id);
        basicInfo.setType_name(st_spn_type_nm);
        basicInfo.setPod_wt_plucking(et_podweightafter_plucking.getText().toString());
        basicInfo.setPod_wt_threshing(et_podweightafter_threshing.getText().toString());
        basicInfo.setBaal_wt_plucking(et_maizeweightafter_plucking.getText().toString());
        basicInfo.setBaal_wt_threshing(et_maizeweightafter_threshing.getText().toString());
        basicInfo.setTotal_nos_of_bundle(noOfBunles);
        basicInfo.setTotal_bundle_weight(et_total_bundle_weight.getText().toString());
        basicInfo.setBundle1(et_bundle1.getText().toString());
        basicInfo.setBundle2(et_bundle2.getText().toString());
        basicInfo.setBundle3(et_bundle3.getText().toString());
        basicInfo.setBundle4(et_bundle4.getText().toString());
        basicInfo.setBundle5(et_bundle5.getText().toString());
        basicInfo.setBundle6(et_bundle6.getText().toString());
        basicInfo.setBundle7(et_bundle7.getText().toString());
        basicInfo.setBundle8(et_bundle8.getText().toString());
        basicInfo.setDryFiber_weight(et_dry_weight_fiber.getText().toString());
        //basicInfo.setDry_weight_of_dana(et_dry_weight_fiber.getText().toString());
        String s = getCurrentDate();
        basicInfo.setEntryDate(getCurrentDate());
        c = placeData.InsertInBasicDetails(basicInfo);
        if (c > 0) {

            Toast.makeText(getApplicationContext(), "Data Successfully Saved !", Toast.LENGTH_LONG).show();
            Intent rddirect = new Intent(BasicDetails.this, MultiplePhotoActivity.class);
            rddirect.putExtra("index", Long.toString(c));
            rddirect.putExtra("edited", isEdit);
            startActivity(rddirect);

        } else {
            Toast.makeText(BasicDetails.this, "Not successfull", Toast.LENGTH_SHORT).show();
        }


    }

    public void loadFinancialYear() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

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
        spn_agri_year.setAdapter(Fyearadapter);

        if (getIntent().hasExtra("KeyId")) {

            spn_agri_year.setSelection(i);
            spn_agri_year.setSelection((((ArrayAdapter<String>) spn_agri_year.getAdapter()).getPosition(agrival)));
        }


    }

    public void loadSeason() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

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
        spnn_season.setAdapter(Seasonadapter);
        //   if(!seasonval.equals("")) {
        if (getIntent().hasExtra("KeyId")) {
            spnn_season.setSelection((((ArrayAdapter<String>) spnn_season.getAdapter()).getPosition(seasonval)));
        }


    }

    public void loadCropType() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        CropArray.clear();
        cropList = dataBaseHelper.getCropTypeLocal(st_spn_season_id);

        CropArray.add("-select-");
        int i = 0;
        for (CropType cropType : cropList) {
            CropArray.add(cropType.getCropName());
            i++;
        }
        Cropadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, CropArray);
        Cropadapter.setDropDownViewResource(R.layout.dropdowlist);
        spnn_crop.setAdapter(Cropadapter);

        if (getIntent().hasExtra("KeyId")) {

            spnn_crop.setSelection((((ArrayAdapter<String>) spnn_crop.getAdapter()).getPosition(cropval)));

        }
    }

    public void loadSourceOfSeed() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        SourceSeedList = dataBaseHelper.getSourceseedlocal();
        SourceSeedArray = new ArrayList<String>();
        SourceSeedArray.add("-select-");
        int i = 0;
        for (Source_Seed source_seed : SourceSeedList) {
            SourceSeedArray.add(source_seed.getSeed_Name());
            i++;
        }
        SourceSeedadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, SourceSeedArray);
        SourceSeedadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_source_seed.setAdapter(SourceSeedadapter);
        if (getIntent().hasExtra("KeyId")) {
            spn_source_seed.setSelection(((ArrayAdapter<String>) spn_source_seed.getAdapter()).getPosition(sourceofseed_name));
        }

    }

    public void loadCutivationSpinner() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        Cutivation_list = dataBaseHelper.getCutivationLocal();
        cutivationarray = new ArrayList<String>();
        cutivationarray.add("-select-");
        int i = 0;
        for (System_Cutivation system_cutivation : Cutivation_list) {
            cutivationarray.add(system_cutivation.getCutivation_name());
            i++;
        }
        cutivationadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, cutivationarray);
        cutivationadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_cutivtion.setAdapter(cutivationadapter);
        if (getIntent().hasExtra("KeyId")) {
            spn_cutivtion.setSelection(((ArrayAdapter<String>) spn_cutivtion.getAdapter()).getPosition(cultivation_name));
        }

    }

    public void loadWeatherCondition() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        WeatherList = dataBaseHelper.getWeatherLocal();
        weatherArray = new ArrayList<String>();
        weatherArray.add("-select-");
        int i = 0;
        for (Weather_Condition weather_condition : WeatherList) {
            weatherArray.add(weather_condition.getWeather_Name());
            i++;
        }
        weatheradapter = new ArrayAdapter<>(this, R.layout.dropdowlist, weatherArray);
        weatheradapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_weather_at_crop_season.setAdapter(weatheradapter);
        if (getIntent().hasExtra("KeyId")) {
            spn_weather_at_crop_season.setSelection(((ArrayAdapter<String>) spn_weather_at_crop_season.getAdapter()).getPosition(weatherconditionname));
        }

    }

    public void loadCRopVarities() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        CropVaritiesList = dataBaseHelper.getVaritiesCropLocal();
        cropVaritiesArray = new ArrayList<String>();
        cropVaritiesArray.add("-select-");
        int i = 0;
        for (Varities_of_Crop varities_of_crop : CropVaritiesList) {
            cropVaritiesArray.add(varities_of_crop.getCrop_Varities_Name());
            i++;
        }
        cropVaritiesadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, cropVaritiesArray);
        cropVaritiesadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_crop_type_varities.setAdapter(cropVaritiesadapter);
        if (getIntent().hasExtra("KeyId")) {
            spn_crop_type_varities.setSelection(((ArrayAdapter<String>) spn_crop_type_varities.getAdapter()).getPosition(varitiescrop_name));
        }

    }

    public void loadManureType() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        ManureTypeList = dataBaseHelper.getManureTypeLocal();
        manureTypeArray = new ArrayList<String>();
        manureTypeArray.add("-select Manuretype-");
        int i = 0;
        for (Manure_type manure_type : ManureTypeList) {
            manureTypeArray.add(manure_type.getManure_type_name());
            i++;
        }
        ManureTypeadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, manureTypeArray);
        ManureTypeadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_manure_type.setAdapter(ManureTypeadapter);
        if (getIntent().hasExtra("KeyId")) {
            spn_manure_type.setSelection(((ArrayAdapter<String>) spn_manure_type.getAdapter()).getPosition(typeofmanure_name));
        }
    }

    private void loadPanchayatData(ArrayList<Panchayat_List> pList) {
        panchayatstlist = new ArrayList<String>();
        panchayatstlist.add("-select-");
        for (int i = 0; i < pList.size(); i++) {
            panchayatstlist.add(pList.get(i).getPanchayat_Name());
        }
        panchayatadapter = new ArrayAdapter(this, R.layout.dropdowlist, panchayatstlist);
        spn_pnchayat.setAdapter(panchayatadapter);

        if (keyid != null) {
            if (_panCode != null) {
                //CREATE TABLE `Panchayat` ( `Panchayat_Code` TEXT, `Panchayat_Name` TEXT )
                String panval = dataBaseHelper.getNameFor("Panchayat", "Panchayat_Code", "Panchayat_Name", _panCode);
                spn_pnchayat.setSelection((((ArrayAdapter<String>) spn_pnchayat.getAdapter()).getPosition(panval)));
            }

        }
        EntryList = dataBaseHelper.getAllEntryById(UserId, keyid);
        if (EntryList.size() != 0) {
            for (BasicInfo basicInfo : EntryList) {
                String panchaval = dataBaseHelper.getNameFor("Panchayat", "Panchayat_Code", "Panchayat_Name", basicInfo.getPanchayat());
                spn_pnchayat.setSelection((((ArrayAdapter<String>) spn_pnchayat.getAdapter()).getPosition(panchaval)));

            }
        }
    }

    public void setPanchayatData() {
        DataBaseHelper placeData = new DataBaseHelper(BasicDetails.this);
        PanchayatList = placeData.getPanchayatLocal(UserId);
        Log.d("Wardlist", "" + PanchayatList.size());
        if (PanchayatList.size() > 0)
            loadPanchayatData(PanchayatList);

    }

    public void loadLandType() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        LandList = dataBaseHelper.getLandTypeLocal();
        landTypeArray = new ArrayList<String>();
        landTypeArray.add("-select-");
        int i = 0;
        for (LandType landType : LandList) {
            landTypeArray.add(landType.getLand_type());
            i++;
        }
        landtypeadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, landTypeArray);
        landtypeadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_land_type.setAdapter(landtypeadapter);
        if (getIntent().hasExtra("KeyId")) {
            spn_land_type.setSelection(((ArrayAdapter<String>) spn_land_type.getAdapter()).getPosition(typeofland));
        }
    }

    public void addItemsOnSpinner() {
        OrderOfExperimentlist.add("<--select-->");
        OrderOfExperimentlist.add("1st");
        OrderOfExperimentlist.add("2nd");
        OrderOfExperimentlist.add("3rd");
        OrderOfExperimentlist.add("4th");
        OrderOfExperimentlist.add("5th");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.dropdowlist, OrderOfExperimentlist);

        spn_order_of_experiment.setAdapter(dataAdapter);
        OrderOfexperList = OrderOfExperimentlist;
//        if (getIntent().hasExtra("KeyId")) {
//            spn_order_of_experiment.setSelection(((ArrayAdapter<String>) spn_order_of_experiment.getAdapter()).getPosition(orderofexperiment));
//        }

    }

    public void addSubTypeVaritiesOfCrop() {
        SubTypeVaritiesCroplist.add("<--select-->");
        SubTypeVaritiesCroplist.add("Irrigated/सिंचित");
        SubTypeVaritiesCroplist.add("Unirrigated/असिंचित");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.dropdowlist, SubTypeVaritiesCroplist);

        spn_subtype_of_varities_of_crop.setAdapter(dataAdapter);
        SubVaritiesOfCropList = SubTypeVaritiesCroplist;
        if (getIntent().hasExtra("KeyId")) {
            spn_subtype_of_varities_of_crop.setSelection(((ArrayAdapter<String>) spn_subtype_of_varities_of_crop.getAdapter()).getPosition(subvarity_ofcrop));
        }
    }

    public void addUnitOperationalSizeHolding() {
        UnitOfOperationalsize.add("<--select-->");
        UnitOfOperationalsize.add("Hectare");
        UnitOfOperationalsize.add("Acre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.dropdowlist, UnitOfOperationalsize);

        spn_unit_operationalsize_holding.setAdapter(dataAdapter);
        UnitOperationalSize = UnitOfOperationalsize;
        // UnitCoverageArea = UnitOfCoverageAreaCrop;
        if (getIntent().hasExtra("KeyId")) {
            spn_unit_operationalsize_holding.setSelection(((ArrayAdapter<String>) spn_unit_operationalsize_holding.getAdapter()).getPosition(unit_operationalsize_holding));
        }

    }

    public void addUnitAreaCoverageCrop() {
        UnitOfCoverageAreaCrop.add("<--select-->");
        UnitOfCoverageAreaCrop.add("Hectare");
        UnitOfCoverageAreaCrop.add("Acre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.dropdowlist, UnitOfCoverageAreaCrop);

        spn_unit_areaCoverage_crop.setAdapter(dataAdapter);
        //  UnitOperationalSize = UnitOfOperationalsize;
        UnitCoverageArea = UnitOfCoverageAreaCrop;
        if (getIntent().hasExtra("KeyId")) {
            spn_unit_areaCoverage_crop.setSelection(((ArrayAdapter<String>) spn_unit_areaCoverage_crop.getAdapter()).getPosition(unitoftheareacovgcrop));
        }

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    public String getRoundFigure(String num) {
        String retNum = num;
        String[] sinNumB4 = {"0", "1", "2", "3", "4"};
        String[] sinNumA4 = {"5", "6", "7", "8", "9"};
        try {
            double d = Double.parseDouble(num);
            double kilobytes = d;
            if (num.contains(".")) {
                String[] dot = num.split("\\.");
                if (dot.length > 1) {

                    if (dot[1].length() == 3) {
                        double newKB = Math.round(kilobytes * 100.0) / 100.0;
                        retNum = "" + newKB;
                    }

                }
            }
            if (retNum.contains(".")) {
                String[] dot = retNum.split("\\.");
                if (dot.length > 1) {

                    if (dot[1].length() == 2) {
                        retNum = retNum + "0";
                    } else if (dot[1].length() == 1) {
                        retNum = retNum + "00";
                    }
                }
            }

            return retNum;
        } catch (Exception ex) {
            Toast.makeText(this, "Please enter number [0-9]", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;

                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(BasicDetails.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void ShowEditEntry(String keyid) {
        DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);
        UserId = PreferenceManager.getDefaultSharedPreferences(BasicDetails.this).getString("USERID", "");
        // Log.d("valjhhhhhhhues",""+UserId+"%"+keyid);
        EntryList = helper.getAllEntryById(UserId, keyid);

        ArrayList<BasicInfo> basicInfo = EntryList;
        btn_save.setText("UPDATE");

        et_plotNo.setText(basicInfo.get(0).getHighest_plot_no());
        et_village.setText(basicInfo.get(0).getName_of_selected_village());


        et_farmername.setText(basicInfo.get(0).getFarmer_name());
        et_operation_size.setText(basicInfo.get(0).getOperation_size_holding());
        et_crop_area.setText(basicInfo.get(0).getArea_covered_crop());
        et_irrigation.setText(basicInfo.get(0).getIrrigation_source());
        et_date.setText(basicInfo.get(0).getDate_of_cutting());
        et_greenWeight.setText(basicInfo.get(0).getGreen_weight());
        et_DryWeight.setText(basicInfo.get(0).getDry_weight());
        et_No_of_baal.setText(basicInfo.get(0).getNo_of_baal());
        et_weight_of_baal.setText(basicInfo.get(0).getWeight_of_baal());
        et_green_weight_of_dana.setText(basicInfo.get(0).getGreen_weight_of_dana());
        et_dry_weight_of_dana.setText(basicInfo.get(0).getDry_weight_of_dana());
        et_quantity_used_manure.setText(basicInfo.get(0).getQuantity_of_used_manure());
        et_quantity_seed.setText(basicInfo.get(0).getQuantity_of_used_seed());
        et_extend_damage_paste.setText(basicInfo.get(0).getExtend_of_damage());
        et_remarks.setText(basicInfo.get(0).getRemarks());
        et_randomNo.setText(basicInfo.get(0).getRandom_no_alloted_by_dso());
        et_length.setText(basicInfo.get(0).getLenghth_of_field());
        et_breath.setText(basicInfo.get(0).getBreath_of_field());

        et_podweightafter_plucking.setText(basicInfo.get(0).getPod_wt_plucking());
        et_podweightafter_threshing.setText(basicInfo.get(0).getPod_wt_threshing());
        et_maizeweightafter_plucking.setText(basicInfo.get(0).getBaal_wt_plucking());
        et_maizeweightafter_threshing.setText(basicInfo.get(0).getBaal_wt_threshing());
        et_dry_weight_fiber.setText(basicInfo.get(0).getDryFiber_weight());

        et_remarks1.setText(basicInfo.get(0).getRemarks1());

        unit_operationalsize_holding = basicInfo.get(0).getUnitOperationalSize();

        typeofland = basicInfo.get(0).getType_of_land_Name();
        orderofexperiment = basicInfo.get(0).getOrder_of_experiment();
        whethercondition = basicInfo.get(0).getWeather_condition_during_crop_season();
        weatherconditionname = basicInfo.get(0).getWeatherconditionname();
        sourceofseed = basicInfo.get(0).getSource_of_seed();
        sourceofseed_name = basicInfo.get(0).getSourceofseed_name();
        typeofmanure_name = basicInfo.get(0).getTypeofmanure_name();
        cultivation_name = basicInfo.get(0).getSystemofcultivation_name();
        varitiescrop_name = basicInfo.get(0).getVaritiesofcrop_name();
        typeofmanure = basicInfo.get(0).getType_of_manure();
        varityofcrop = basicInfo.get(0).getVarities_of_crop();
        subvarity_ofcrop = basicInfo.get(0).getSub_varitiesofcrop_name();
        systemofcultivation = basicInfo.get(0).getSystem_of_cutivation();
        unitoftheareacovgcrop = basicInfo.get(0).getUnitareaCoverage();
        spn_type_val = basicInfo.get(0).getType_name();


        var_surveyNo = basicInfo.get(0).getSurvey_no_khesra_no();

        agrival = basicInfo.get(0).getAgri_year_nm();
        loadFinancialYear();
        //   addItemsOnSpinner();
        addUnitAreaCoverageCrop();
        addUnitOperationalSizeHolding();

        cropval = dataBaseHelper.getNameFor("CropType", "Crop_Id", "Crop_Name", basicInfo.get(0).getCrop());

        seasonval = dataBaseHelper.getNameFor("Season", "season_id", "season_name", basicInfo.get(0).getSeason());

        loadSeason();
        loadFinancialYear();
        st_spn_season_id = basicInfo.get(0).getSeason();
        st_spn_croptyp = basicInfo.get(0).getCrop();
        st_spn_panchayat_code = basicInfo.get(0).getPanchayat();
        loadPanchayatData(PanchayatList);

        addUnitOperationalSizeHolding();
        addSubTypeVaritiesOfCrop();
        // addUnitOperationalSizeHolding();
        addUnitAreaCoverageCrop();
        loadCRopVarities();
        loadCutivationSpinner();
        loadManureType();
        loadSourceOfSeed();
        loadWeatherCondition();
        //addItemsOnSpinner();
        loadLandType();


        if (getIntent().hasExtra("KeyId")) {
            spn_subtype_of_varities_of_crop.setSelection((((ArrayAdapter<String>) spn_subtype_of_varities_of_crop.getAdapter()).getPosition(basicInfo.get(0).getSub_varitiesofcrop_name())));
            spn_bundles.setSelection((((ArrayAdapter<String>) spn_bundles.getAdapter()).getPosition(basicInfo.get(0).getTotal_nos_of_bundle())));
        }

        if (getIntent().hasExtra("KeyId")) {
            spn_order_of_experiment.setSelection((((ArrayAdapter<String>) spn_order_of_experiment.getAdapter()).getPosition(basicInfo.get(0).getOrder_of_experiment())));
        }

        et_bundle1.setText(basicInfo.get(0).getBundle1());
        et_bundle2.setText(basicInfo.get(0).getBundle2());
        et_bundle3.setText(basicInfo.get(0).getBundle3());
        et_bundle4.setText(basicInfo.get(0).getBundle4());
        et_bundle5.setText(basicInfo.get(0).getBundle5());
        et_bundle6.setText(basicInfo.get(0).getBundle6());
        et_bundle7.setText(basicInfo.get(0).getBundle7());
        et_bundle8.setText(basicInfo.get(0).getBundle8());
        et_total_bundle_weight.setText(basicInfo.get(0).getTotal_bundle_weight());
        et_dry_weight_fiber.setText(basicInfo.get(0).getDryFiber_weight());



    }

    public void UpdateNewEntry() {
        long c = -1;
        SetValue();
        // DataBaseHelper placeData = new DataBaseHelper(BasicDetails.this);
        BasicInfo basicInfo = new BasicInfo();

        basicInfo.setId(keyid);
        basicInfo.setUserid(UserId);

        basicInfo.setAgri_year(st_spn_agri_id);
        basicInfo.setAgri_year_nm(st_spn_agri_year);
        basicInfo.setSeason(st_spn_season_id);
        basicInfo.setCrop(st_spn_croptyp);
        basicInfo.setCrop_name(st_spn_croptype_nm);
        basicInfo.setDist(distcode);
        basicInfo.setBlock(blockcode);
        basicInfo.setPanchayat(st_spn_panchayat_code);
        basicInfo.setName_of_selected_village(st_village);
        basicInfo.setHighest_plot_no(st_plot_no);

        basicInfo.setSurvey_no_khesra_no(st_survey_code_name);
        basicInfo.setFarmer_name(st_farmername);
        basicInfo.setUnitOperationalSize(st_spn_operational_size);
        basicInfo.setOperation_size_holding(st_operational_size);
        basicInfo.setUnitareaCoverage(st_spn_area_coverage);
        basicInfo.setArea_covered_crop(st_crop_area);
        basicInfo.setSystem_of_cutivation(st_spn_cutivation_id);
        basicInfo.setVarities_of_crop(st_spn_crop_type_varities_id);
        basicInfo.setSub_varitiesofcrop_name(st_spn_sub_type_cropVarities);
        basicInfo.setIrrigation_source(st_irrigation);
        basicInfo.setType_of_manure(st_spn_manure_type_id);
        basicInfo.setQuantity_of_used_manure(st_quantity_used_manure);
        basicInfo.setSource_of_seed(st_spn_source_seed_id);
        basicInfo.setQuantity_of_used_seed(st_quantity_used_seed);
        basicInfo.setWeather_condition_during_crop_season(st_spn_weather_at_cop_season_id);
        basicInfo.setExtend_of_damage(st_extend_damage_paste);
        basicInfo.setRemarks(st_remarks);
        basicInfo.setRandom_no_alloted_by_dso(st_randomNo);
        basicInfo.setOrder_of_experiment(st_spn_order_of_experiment);
        basicInfo.setShape_of_cce_area(st_spn_cce_shape);
        basicInfo.setLenghth_of_field(st_length);
        basicInfo.setBreath_of_field(st_breath);
        basicInfo.setType_of_land(st_spn_land_type_id);
        basicInfo.setDate_of_cutting(st_date);
        basicInfo.setGreen_weight(st_greenweight);
        basicInfo.setDry_weight(st_dryweight);
        basicInfo.setNo_of_baal(st_no_of_baal);
        basicInfo.setWeight_of_baal(st_weight_of_baal);
        basicInfo.setGreen_weight_of_dana(st_green_weight_of_dana);
        basicInfo.setDry_weight_of_dana(st_dry_weight_of_dana);

        basicInfo.setType_of_land_Name(st_spn_land_type);

        basicInfo.setWeatherconditionname(st_spn_weather_at_crop_season_name);

        basicInfo.setSourceofseed_name(st_spn_source_seed_name);

        basicInfo.setTypeofmanure_name(st_spn_manure_type_name);
        basicInfo.setSystemofcultivation_name(st_spn_cutivation_name);
        basicInfo.setVaritiesofcrop_name(st_spn_crop_type_varities_name);
        basicInfo.setRemarks1(st_remarks1);
        basicInfo.setType_id(st_spn_type_id);
        basicInfo.setType_name(st_spn_type_nm);
        basicInfo.setPod_wt_plucking(et_podweightafter_plucking.getText().toString());
        basicInfo.setPod_wt_threshing(et_podweightafter_threshing.getText().toString());
        basicInfo.setBaal_wt_plucking(et_maizeweightafter_plucking.getText().toString());
        basicInfo.setBaal_wt_threshing(et_maizeweightafter_threshing.getText().toString());
        basicInfo.setTotal_nos_of_bundle(noOfBunles);
        basicInfo.setBundle1(et_bundle1.getText().toString());
        basicInfo.setBundle2(et_bundle2.getText().toString());
        basicInfo.setBundle3(et_bundle3.getText().toString());
        basicInfo.setBundle4(et_bundle4.getText().toString());
        basicInfo.setBundle5(et_bundle5.getText().toString());
        basicInfo.setBundle6(et_bundle6.getText().toString());
        basicInfo.setBundle7(et_bundle7.getText().toString());
        basicInfo.setBundle8(et_bundle8.getText().toString());
        basicInfo.setTotal_bundle_weight(et_total_bundle_weight.getText().toString());
        basicInfo.setDryFiber_weight(et_dry_weight_fiber.getText().toString());
        String s = getCurrentDate();
        Log.e("UpdateDate", s);
        basicInfo.setEntryDate(getCurrentDate());
        c = dataBaseHelper.EntryUpdate(basicInfo);
        if (c > 0) {
            Toast.makeText(BasicDetails.this, "Data Successfully Updated !", Toast.LENGTH_SHORT).show();
            Intent rddirect = new Intent(BasicDetails.this, MultiplePhotoActivity.class);
            rddirect.putExtra("index", keyid);
            rddirect.putExtra("edited", isEdit);
            startActivity(rddirect);

        } else {
            Toast.makeText(BasicDetails.this, "Not successfull", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void loadkhesralist(String userid, String Season, String crop, String pancode) {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        KhesraList = dataBaseHelper.getKhesraListLocal(userid, Season, crop, pancode);

        khesraArray = new ArrayList<String>();
        khesraArray.add("-select-");
        int i = 0;
        for (KhesraNo_List varities_of_crop : KhesraList) {
            khesraArray.add(varities_of_crop.getKhesraNm());
            i++;
        }
        khesraadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, khesraArray);
        khesraadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_SurveyNo.setAdapter(khesraadapter);

        if (getIntent().hasExtra("KeyId")) {
            spn_SurveyNo.setSelection(((ArrayAdapter<String>) spn_SurveyNo.getAdapter()).getPosition(var_surveyNo));
        }


    }

    public ArrayList getDetails(String uid, String seasonid, String crop, String panid, String Khesra) {
        Cursor cur = dataBaseHelper.getUserDetails(uid, seasonid, crop, panid, Khesra);
        String fname = "", vill = "", plot = "";
        ArrayList data = new ArrayList();
        try {
            while (cur.moveToNext()) {

                fname = cur.getString(cur.getColumnIndex("FarmerName"));
                vill = cur.getString(cur.getColumnIndex("villageName"));
                plot = cur.getString(cur.getColumnIndex("highestPlot"));

            }
            data.add(fname);
            data.add(vill);
            data.add(plot);
            cur.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return data;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public void ShowDialog() {


        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(BasicDetails.this,
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

    public void loadTypeList() {
        dataBaseHelper = new DataBaseHelper(BasicDetails.this);

        TypeArray.clear();
        TypeList = dataBaseHelper.getTypeLocal(st_spn_croptyp);

        TypeArray.add("-select-");
        int i = 0;
        for (TypeList cropType : TypeList) {
            TypeArray.add(cropType.getName());
            i++;
        }
        typadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, TypeArray);
        typadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_type.setAdapter(typadapter);


        if (getIntent().hasExtra("KeyId")) {
            spn_type.setSelection(((ArrayAdapter<String>) spn_type.getAdapter()).getPosition(spn_type_val));
        }

    }

    private class FINANCIALYEAR extends AsyncTask<String, Void, ArrayList<Financial_Year>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setFinancialYear(result);
                if (i > 0) {
                    loadFinancialYear();

                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SEASON extends AsyncTask<String, Void, ArrayList<Season_List>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setSeasonLocal(result);
                if (i > 0) {
                    loadSeason();

                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CROPTYPE extends AsyncTask<String, Void, ArrayList<CropType>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<CropType> doInBackground(String... param) {


            return WebServiceHelper.getCroptype(st_spn_season_id);

        }

        @Override
        protected void onPostExecute(ArrayList<CropType> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setCropTypeLocal(result);
                if (i > 0) {
                    Toast.makeText(BasicDetails.this, "Crop Type loaded", Toast.LENGTH_SHORT).show();
                    loadCropType();


                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CROPTYPESync extends AsyncTask<String, Void, ArrayList<CropType>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<CropType> doInBackground(String... param) {


            return WebServiceHelper.getCroptype(st_spn_season_id);

        }

        @Override
        protected void onPostExecute(ArrayList<CropType> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setCropTypeLocal(result);
                if (i > 0) {
                    Toast.makeText(BasicDetails.this, "Crop Type loaded", Toast.LENGTH_SHORT).show();
                    //loadCropType();


                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SOURCEOFSEED extends AsyncTask<String, Void, ArrayList<Source_Seed>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Source_Seed> doInBackground(String... param) {


            return WebServiceHelper.getSOurceOfSeed();

        }

        @Override
        protected void onPostExecute(ArrayList<Source_Seed> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setSourceseedlocal(result);
                if (i > 0) {

                    loadSourceOfSeed();


                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PANCHAYATDATA extends AsyncTask<String, Void, ArrayList<Panchayat_List>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

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

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setPanchayatName(result);
                if (i > 0) {
                    setPanchayatData();

                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CUTIVATION extends AsyncTask<String, Void, ArrayList<System_Cutivation>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<System_Cutivation> doInBackground(String... param) {


            return WebServiceHelper.getCutivationtData();

        }

        @Override
        protected void onPostExecute(ArrayList<System_Cutivation> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setCutivationLocal(result);
                if (i > 0) {

                    loadCutivationSpinner();


                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class WEATHERCONDITION extends AsyncTask<String, Void, ArrayList<Weather_Condition>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Weather_Condition> doInBackground(String... param) {


            return WebServiceHelper.getWeatherData();

        }

        @Override
        protected void onPostExecute(ArrayList<Weather_Condition> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setWeatherLocal(result);
                if (i > 0) {

                    loadWeatherCondition();

                } else {

                }

            } else {
                Toast.makeText(BasicDetails.this, "null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CROPVARITIES extends AsyncTask<String, Void, ArrayList<Varities_of_Crop>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Varities_of_Crop> doInBackground(String... param) {


            return WebServiceHelper.getVaritiesOfCrop();

        }

        @Override
        protected void onPostExecute(ArrayList<Varities_of_Crop> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);

                long i = helper.setVaritiesCropLocal(result);
                if (i > 0) {

                    loadCRopVarities();
                    // Toast.makeText(BasicDetails.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(BasicDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(BasicDetails.this, "null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MANURETYPE extends AsyncTask<String, Void, ArrayList<Manure_type>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Manure_type> doInBackground(String... param) {


            return WebServiceHelper.getManureType();

        }

        @Override
        protected void onPostExecute(ArrayList<Manure_type> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setManureTypeLocal(result);
                if (i > 0) {

                    loadManureType();
                    // Toast.makeText(BasicDetails.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(BasicDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LANDTYPE extends AsyncTask<String, Void, ArrayList<LandType>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<LandType> doInBackground(String... param) {


            return WebServiceHelper.getLandType();

        }

        @Override
        protected void onPostExecute(ArrayList<LandType> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);


                long i = helper.setLandTypeLocall(result);
                if (i > 0) {
                    loadLandType();

                    // Toast.makeText(BasicDetails.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(BasicDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class KhesraList extends AsyncTask<String, Void, ArrayList<KhesraNo_List>> {

        private final ProgressDialog dialog = new ProgressDialog(BasicDetails.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BasicDetails.this).create();

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<KhesraNo_List> doInBackground(String... param) {
            return WebServiceHelper.getKhesraList(UserId, st_spn_agri_id);
        }

        @Override
        protected void onPostExecute(ArrayList<KhesraNo_List> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(BasicDetails.this);
                KhesraList = dataBaseHelper.getKhesraLocal(UserId, st_spn_agri_id);
                if (KhesraList.size() > 0) {
                    long c = helper.deleteRecKhesra(UserId);
                    if (c > 0) {
                        long i = helper.setKhesraLocal(result, UserId);
                        if (i > 0) {
                            Toast.makeText(BasicDetails.this, "Khesra list loaded", Toast.LENGTH_SHORT).show();
                            loadkhesralist(UserId, st_spn_season_id, st_spn_croptyp, st_spn_panchayat_code);

                        } else {

                        }
                    }
                } else {
                    long i = helper.setKhesraLocal(result, UserId);
                    if (i > 0) {
                        Toast.makeText(BasicDetails.this, "Khesra list loaded", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                }
            } else {
                Toast.makeText(BasicDetails.this, "Result:null", Toast.LENGTH_SHORT).show();
            }

            new CROPTYPESync().execute();
        }
    }


}
