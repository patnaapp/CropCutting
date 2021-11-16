package in.nic.bih.cropcutting.activity.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Entity.Block_List;
import in.nic.bih.cropcutting.activity.Entity.CheckList;
import in.nic.bih.cropcutting.activity.Entity.CropType;
import in.nic.bih.cropcutting.activity.Entity.District_list;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.KhesraNo_List;
import in.nic.bih.cropcutting.activity.Entity.LandType;
import in.nic.bih.cropcutting.activity.Entity.Manure_type;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfo;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfoOnline;
import in.nic.bih.cropcutting.activity.Entity.Panchayat_List;
import in.nic.bih.cropcutting.activity.Entity.RabiCropType;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.Entity.ShapeCceArea;
import in.nic.bih.cropcutting.activity.Entity.Source_Seed;
import in.nic.bih.cropcutting.activity.Entity.System_Cutivation;
import in.nic.bih.cropcutting.activity.Entity.TypeList;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;
import in.nic.bih.cropcutting.activity.Entity.Varities_of_Crop;
import in.nic.bih.cropcutting.activity.Entity.Weather_Condition;
import in.nic.bih.cropcutting.activity.activity.KhesraEntryActivity;

//import static android.icu.text.MessagePattern.ArgType.SELECT;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "CropCutting.db";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context context) {


        super(context, DB_NAME, null, 3);
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist


        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        this.getReadableDatabase().close();
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            db.execSQL("CREATE TABLE  CheckList (Chk_Id TEXT, Chk_Name TEXT)");
            onCreate(myDataBase);
        }


    }

    public long deleteRec(String id) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(id)};
            c = db.delete("BasicDetails", "Id=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }

    public long deleteRecPhase1(String id) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(id)};
            c = db.delete("ChngKhesraDetails", "Id=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }

    public long deleteRecKhesra(String user_id) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(user_id)};
            c = db.delete("KhesraList", "userid=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }


    public long deleteEditRec(String id, String EntryBy) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(EntryBy), String.valueOf(id)};
            c = db.delete("BasicDetails", "EntryBy=? and Id=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }

    public long deleteEditRecPhase1(String id, String EntryBy) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(EntryBy), String.valueOf(id)};
            c = db.delete("ChngKhesraDetails", "User_Id=? and Id=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }

    public boolean deleterow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ChngKhesraDetails", "Id" + "=" + id, null) > 0;
    }


    public long insertUserDetail(UserLogin result) {
        long c = 0;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Login_name", result.getMobile());
            values.put("UserId", result.getUserId());
            values.put("Username", result.getUserName());
            values.put("Password", result.getPassword());
            values.put("Role", result.getRole());
            values.put("Distcode", result.getDistcode());
            values.put("distName", result.getDistname());
            values.put("Blockcode", result.getBlkcode());
            values.put("BlockName", result.getBlkname());
            values.put("Imei", result.getImei());

            String[] whereArgs = new String[]{result.getUserId()};

            c = db.update("UserLogin", values, "UserId =? ", whereArgs);

            if (!(c > 0)) {
                c = db.insert("UserLogin", null, values);

            }
            this.getWritableDatabase().close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }

    public UserLogin getDetail(String Userid) {

        UserLogin userLogin = new UserLogin();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] args = {Userid};
            Cursor cur = db.rawQuery("select * From UserLogin where UserId=?", args);


            int count = cur.getCount();

            while (cur.moveToNext()) {

                userLogin.setId((cur.getString(cur.getColumnIndex("id"))));
                userLogin.setUserId((cur.getString(cur.getColumnIndex("UserId"))));
                userLogin.setUserName(cur.getString(cur.getColumnIndex("Username")));
                userLogin.setPassword(cur.getString(cur.getColumnIndex("Password")));
                userLogin.setRole((cur.getString(cur.getColumnIndex("Role"))));
                userLogin.setDistcode((cur.getString(cur.getColumnIndex("Distcode"))));
                userLogin.setDistname((cur.getString(cur.getColumnIndex("distName"))));
                userLogin.setBlkcode((cur.getString(cur.getColumnIndex("Blockcode"))));
                userLogin.setBlkname((cur.getString(cur.getColumnIndex("BlockName"))));
                userLogin.setMobile((cur.getString(cur.getColumnIndex("Login_name"))));
                userLogin.setImei((cur.getString(cur.getColumnIndex("Imei"))));

            }
            this.getReadableDatabase().close();
            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return userLogin;
    }


    public long InsertInBasicDetails(BasicInfo result) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Agriculture_year", result.getAgri_year());
            values.put("Agri_year_nm", result.getAgri_year_nm());
            values.put("Season", result.getSeason());
            values.put("Crop", result.getCrop());
            values.put("CropName", result.getCrop_name());
            values.put("District", result.getDist());
            values.put("Block", result.getBlock());
            values.put("Panchayat", result.getPanchayat());
            values.put("Name_of_selected_village", result.getName_of_selected_village());
            values.put("Highest_plot_no_of_Panchayat", result.getHighest_plot_no());
            values.put("Selected_Survey_No_Khesra", result.getSurvey_no_khesra_no());
            values.put("Farmer_Name", result.getFarmer_name());
            values.put("Operational_Size_holding", result.getOperation_size_holding());
            values.put("Area_of_crop", result.getArea_covered_crop());
            values.put("System_of_cutivation", result.getSystem_of_cutivation());
            values.put("Type_Of_crop_varities", result.getVarities_of_crop());
            values.put("Irrigation_source", result.getIrrigation_source());
            values.put("Type_of_manure", result.getType_of_manure());
            values.put("Quantity_of_used_manure", result.getQuantity_of_used_manure());
            values.put("Source_of_seed", result.getSource_of_seed());
            values.put("Quantity_of_used_seed", result.getQuantity_of_used_seed());
            values.put("Weather_condition_during", result.getWeather_condition_during_crop_season());
            values.put("Extend_of_damage", result.getExtend_of_damage());
            values.put("Remarks", result.getRemarks());
            values.put("EntryBy", result.getUserid());
            values.put("Random_no_allotted_DCO", result.getRandom_no_alloted_by_dso());
            values.put("Order_Of_Experiment_according_to_random_No", result.getOrder_of_experiment());
            values.put("Shape_of_Cce_area", result.getShape_of_cce_area());
            values.put("Length_of_field", result.getLenghth_of_field());
            values.put("Breadth_of_field", result.getBreath_of_field());
            values.put("Type_of_land", result.getType_of_land());
            values.put("Date_of_cutting", result.getDate_of_cutting());
            values.put("Green_weight", result.getGreen_weight());
            values.put("Dry_weight", result.getDry_weight());
            values.put("No_of_baal_Maize", result.getNo_of_baal());
            values.put("Weight_of_baal_Maize", result.getWeight_of_baal());
            values.put("Green_weight_of_Dana", result.getGreen_weight_of_dana());
            values.put("Dry_weight_of_dana", result.getDry_weight_of_dana());

            values.put("remarks1", result.getRemarks1());
            values.put("Inspection_done", result.getInspectionDone());
            values.put("Quantity_seed_in", result.getUnit_seed_in());

            values.put("UnitOperationalSize", result.getUnitOperationalSize());
            values.put("UnitAreaCoverage", result.getUnitareaCoverage());
            Log.e("InsertDate", result.getEntryDate());
            values.put("EntryDate", result.getEntryDate());
            values.put("Type_of_land_Name", result.getType_of_land_Name());
            values.put("Weather_condition_during_name", result.getWeatherconditionname());

            values.put("sourceofseed_name", result.getSourceofseed_name());
            values.put("tymanure_name", result.getTypeofmanure_name());
            values.put("systemcultivation_name", result.getSystemofcultivation_name());
            values.put("varitiescrop_name", result.getVaritiesofcrop_name());
            values.put("Sub_Varities_Of_crop", result.getSub_varitiesofcrop_name());
            values.put("Pod_wt_aftr_plucking", result.getPod_wt_plucking());
            values.put("Pod_Wt_after_thresing", result.getPod_wt_threshing());
            values.put("Baal_wt_aftr_plucking", result.getBaal_wt_plucking());
            values.put("Baal_wt_aftr_threshing", result.getBaal_wt_threshing());
            values.put("Total_bundles", result.getTotal_nos_of_bundle());
            values.put("bundle1", result.getBundle1());
            values.put("bundle2", result.getBundle2());
            values.put("bundle3", result.getBundle3());
            values.put("bundle4", result.getBundle4());
            values.put("bundle5", result.getBundle5());
            values.put("bundle6", result.getBundle6());
            values.put("bundle7", result.getBundle7());
            values.put("bundle8", result.getBundle8());
            values.put("total_bundle_weight", result.getTotal_bundle_weight());
            values.put("dry_fiber_weight", result.getDryFiber_weight());

            values.put("Type_id", result.getType_id());
            values.put("Type_name", result.getType_name());

            c = db.insert("BasicDetails", null, values);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }
        return c;

    }


    public ArrayList<BasicInfo> getAllEntryDetail(String Userid) {
        ArrayList<BasicInfo> basicdetail = new ArrayList<BasicInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {Userid};

            Cursor cursor = sqLiteDatabase.rawQuery("select Id,Latitude,Agriculture_year,Season,Crop,District,Block,Panchayat,Name_of_selected_village,Highest_plot_no_of_Panchayat,Selected_Survey_No_Khesra,Farmer_Name,Operational_Size_holding,Area_of_crop,System_of_cutivation,Type_Of_crop_varities,Irrigation_source,Type_of_manure,Quantity_of_used_manure,Source_of_seed,Quantity_of_used_seed,Weather_condition_during,Extend_of_damage,Remarks,EntryBy,Random_no_allotted_DCO,Order_Of_Experiment_according_to_random_No,Shape_of_Cce_area,Length_of_field,Breadth_of_field,Type_of_land,Date_of_cutting,Green_weight,Dry_weight,No_of_baal_Maize,Weight_of_baal_Maize,Green_weight_of_Dana,Dry_weight_of_dana,remarks1,Inspection_done,Quantity_seed_in,UnitOperationalSize,UnitAreaCoverage,Longitude,EntryDate,Type_of_land_Name,Weather_condition_during_name,sourceofseed_name,tymanure_name,systemcultivation_name,varitiescrop_name,CropName,Sub_Varities_Of_crop,DuringThreshing_Img,Type_id,Type_name,Pod_wt_aftr_plucking,Pod_Wt_after_thresing,Baal_wt_aftr_plucking,Baal_wt_aftr_threshing,Total_bundles,bundle1,bundle2,bundle3,bundle4,bundle5,bundle6,bundle7,bundle8,total_bundle_weight,dry_fiber_weight From BasicDetails where Latitude IS NOT NULL AND EntryBy=? ORDER BY Id  DESC", args);
            int x = cursor.getCount();

            while (cursor.moveToNext()) {
                BasicInfo basicInfo = new BasicInfo();
                String rowID = cursor.getString(cursor.getColumnIndex("Id"));
                basicInfo.setId((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.setUserid((cursor.getString(cursor.getColumnIndex("EntryBy"))));
                basicInfo.setLat((cursor.getString(cursor.getColumnIndex("Latitude"))));
                basicInfo.setLongi((cursor.getString(cursor.getColumnIndex("Longitude"))));

                basicInfo.setAgri_year((cursor.getString(cursor.getColumnIndex("Agriculture_year"))));
                basicInfo.setSeason((cursor.getString(cursor.getColumnIndex("Season"))));
                basicInfo.setCrop((cursor.getString(cursor.getColumnIndex("Crop"))));
                basicInfo.setCrop_name((cursor.getString(cursor.getColumnIndex("CropName"))));
                basicInfo.setDist((cursor.getString(cursor.getColumnIndex("District"))));
                basicInfo.setBlock((cursor.getString(cursor.getColumnIndex("Block"))));
                basicInfo.setPanchayat((cursor.getString(cursor.getColumnIndex("Panchayat"))));
                basicInfo.setName_of_selected_village((cursor.getString(cursor.getColumnIndex("Name_of_selected_village"))));
                basicInfo.setHighest_plot_no((cursor.getString(cursor.getColumnIndex("Highest_plot_no_of_Panchayat"))));
                basicInfo.setSurvey_no_khesra_no((cursor.getString(cursor.getColumnIndex("Selected_Survey_No_Khesra"))));
                basicInfo.setFarmer_name((cursor.getString(cursor.getColumnIndex("Farmer_Name"))));
                basicInfo.setUnitOperationalSize((cursor.getString(cursor.getColumnIndex("UnitOperationalSize"))));
                basicInfo.setOperation_size_holding((cursor.getString(cursor.getColumnIndex("Operational_Size_holding"))));
                basicInfo.setUnitareaCoverage((cursor.getString(cursor.getColumnIndex("UnitAreaCoverage"))));
                basicInfo.setArea_covered_crop(cursor.getString((cursor.getColumnIndex("Area_of_crop"))));
                basicInfo.setSystem_of_cutivation((cursor.getString(cursor.getColumnIndex("System_of_cutivation"))));
                basicInfo.setVarities_of_crop((cursor.getString(cursor.getColumnIndex("Type_Of_crop_varities"))));
                basicInfo.setIrrigation_source((cursor.getString(cursor.getColumnIndex("Irrigation_source"))));
                basicInfo.setType_of_manure((cursor.getString(cursor.getColumnIndex("Type_of_manure"))));
                basicInfo.setQuantity_of_used_manure((cursor.getString(cursor.getColumnIndex("Quantity_of_used_manure"))));
                basicInfo.setSource_of_seed((cursor.getString(cursor.getColumnIndex("Source_of_seed"))));
                basicInfo.setUnit_seed_in((cursor.getString(cursor.getColumnIndex("Quantity_seed_in"))));
                basicInfo.setQuantity_of_used_seed((cursor.getString(cursor.getColumnIndex("Quantity_of_used_seed"))));
                basicInfo.setWeather_condition_during_crop_season((cursor.getString(cursor.getColumnIndex("Weather_condition_during"))));
                basicInfo.setExtend_of_damage((cursor.getString(cursor.getColumnIndex("Extend_of_damage"))));
                basicInfo.setRemarks((cursor.getString(cursor.getColumnIndex("Remarks"))));
                basicInfo.setRandom_no_alloted_by_dso((cursor.getString(cursor.getColumnIndex("Random_no_allotted_DCO"))));
                basicInfo.setOrder_of_experiment((cursor.getString(cursor.getColumnIndex("Order_Of_Experiment_according_to_random_No"))));
                basicInfo.setShape_of_cce_area((cursor.getString(cursor.getColumnIndex("Shape_of_Cce_area"))));
                basicInfo.setLenghth_of_field((cursor.getString(cursor.getColumnIndex("Length_of_field"))));
                basicInfo.setBreath_of_field((cursor.getString(cursor.getColumnIndex("Breadth_of_field"))));
                basicInfo.setType_of_land((cursor.getString(cursor.getColumnIndex("Type_of_land"))));
                basicInfo.setDate_of_cutting((cursor.getString(cursor.getColumnIndex("Date_of_cutting"))));
                basicInfo.setGreen_weight((cursor.getString(cursor.getColumnIndex("Green_weight"))));
                basicInfo.setDry_weight((cursor.getString(cursor.getColumnIndex("Dry_weight"))));
                basicInfo.setNo_of_baal((cursor.getString(cursor.getColumnIndex("No_of_baal_Maize"))));
                basicInfo.setWeight_of_baal((cursor.getString(cursor.getColumnIndex("Weight_of_baal_Maize"))));
                basicInfo.setGreen_weight_of_dana((cursor.getString(cursor.getColumnIndex("Green_weight_of_Dana"))));
                basicInfo.setDry_weight_of_dana((cursor.getString(cursor.getColumnIndex("Dry_weight_of_dana"))));

                basicInfo.setRemarks1((cursor.getString(cursor.getColumnIndex("remarks1"))));
                basicInfo.setEntryDate((cursor.getString(cursor.getColumnIndex("EntryDate"))));
                basicInfo.setVaritiesofcrop_name((cursor.getString(cursor.getColumnIndex("varitiescrop_name"))));
                basicInfo.setSub_varitiesofcrop_name((cursor.getString(cursor.getColumnIndex("Sub_Varities_Of_crop"))));
                basicInfo.setType_id((cursor.getString(cursor.getColumnIndex("Type_id"))));
                basicInfo.setType_name((cursor.getString(cursor.getColumnIndex("Type_name"))));

                basicInfo.setTotal_nos_of_bundle((cursor.getString(cursor.getColumnIndex("Total_bundles"))));
                basicInfo.setTotal_bundle_weight((cursor.getString(cursor.getColumnIndex("total_bundle_weight"))));
                basicInfo.setBundle1((cursor.getString(cursor.getColumnIndex("bundle1"))));
                basicInfo.setBundle2((cursor.getString(cursor.getColumnIndex("bundle2"))));
                basicInfo.setBundle3((cursor.getString(cursor.getColumnIndex("bundle3"))));
                basicInfo.setBundle4((cursor.getString(cursor.getColumnIndex("bundle4"))));
                basicInfo.setBundle5((cursor.getString(cursor.getColumnIndex("bundle5"))));
                basicInfo.setBundle6((cursor.getString(cursor.getColumnIndex("bundle6"))));
                basicInfo.setBundle7((cursor.getString(cursor.getColumnIndex("bundle7"))));
                basicInfo.setBundle8((cursor.getString(cursor.getColumnIndex("bundle8"))));


                basicInfo.setPod_wt_plucking((cursor.getString(cursor.getColumnIndex("Pod_wt_aftr_plucking"))));
                basicInfo.setPod_wt_threshing((cursor.getString(cursor.getColumnIndex("Pod_Wt_after_thresing"))));
                basicInfo.setBaal_wt_plucking((cursor.getString(cursor.getColumnIndex("Baal_wt_aftr_plucking"))));
                basicInfo.setBaal_wt_threshing((cursor.getString(cursor.getColumnIndex("Baal_wt_aftr_threshing"))));
                basicInfo.setDryFiber_weight((cursor.getString(cursor.getColumnIndex("dry_fiber_weight"))));

                String[] args2 = {rowID};
                String selectSQL = "select Img1,Img2 From BasicDetails where Id=? ORDER BY Id  DESC";
                Cursor cursor1 = sqLiteDatabase.rawQuery(selectSQL, args2);
                Log.e("USERID", Userid);
                while (cursor1.moveToNext()) {
                    basicInfo.setImg1((cursor1.getString(cursor1.getColumnIndex("Img1"))));
                    basicInfo.setImg2((cursor1.getString(cursor1.getColumnIndex("Img2"))));

                }

                //basicdetail.add(basicInfo);
                cursor1.close();

                String[] args3 = {rowID};
                String selectSQL1 = "select Img3,Img4 From BasicDetails where Id=? ORDER BY Id  DESC";
                Cursor cursor2 = sqLiteDatabase.rawQuery(selectSQL1, args3);
                Log.e("USERID", Userid);
                while (cursor1.moveToNext()) {

                    basicInfo.setImg3((cursor2.getString(cursor2.getColumnIndex("Img3"))));
                    basicInfo.setImg4((cursor2.getString(cursor2.getColumnIndex("Img4"))));
                }

                cursor2.close();

                String[] args4 = {rowID};
                String selectSQL2 = "select DuringThreshing_Img From BasicDetails where Id=? ORDER BY Id  DESC";
                Cursor cursor3 = sqLiteDatabase.rawQuery(selectSQL2, args4);
                Log.e("USERID", Userid);
                while (cursor3.moveToNext()) {

                    basicInfo.setImg5((cursor3.getString(cursor3.getColumnIndex("DuringThreshing_Img"))));

                }

                basicdetail.add(basicInfo);
                cursor3.close();
            }
            cursor.close();
            this.getReadableDatabase().close();
            sqLiteDatabase.close();


        }
        catch (Exception e) {
            e.printStackTrace();
            basicdetail = null;
            // TODO: handle exception

        }
        return basicdetail;
    }


    public ArrayList<BasicInfo> getAllEntryDetailOLD(String Userid) {
        ArrayList<BasicInfo> basicdetail = new ArrayList<BasicInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {Userid};

            Cursor cursor = sqLiteDatabase.rawQuery("select Id,Latitude,Agriculture_year,Season,Crop,District,Block,Panchayat,Name_of_selected_village,Highest_plot_no_of_Panchayat,Selected_Survey_No_Khesra,Farmer_Name,Operational_Size_holding,Area_of_crop,System_of_cutivation,Type_Of_crop_varities,Irrigation_source,Type_of_manure,Quantity_of_used_manure,Source_of_seed,Quantity_of_used_seed,Weather_condition_during,Extend_of_damage,Remarks,EntryBy,Random_no_allotted_DCO,Order_Of_Experiment_according_to_random_No,Shape_of_Cce_area,Length_of_field,Breadth_of_field,Type_of_land,Date_of_cutting,Green_weight,Dry_weight,No_of_baal_Maize,Weight_of_baal_Maize,Green_weight_of_Dana,Dry_weight_of_dana,remarks1,Inspection_done,Quantity_seed_in,UnitOperationalSize,UnitAreaCoverage,Longitude,EntryDate,Type_of_land_Name,Weather_condition_during_name,sourceofseed_name,tymanure_name,systemcultivation_name,varitiescrop_name,CropName,Sub_Varities_Of_crop From BasicDetails where Latitude IS NOT NULL AND EntryBy=? ORDER BY Id  DESC", args);
            int x = cursor.getCount();

            BasicInfo basicInfo = new BasicInfo();
            while (cursor.moveToNext()) {

                basicInfo.setId((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.setUserid((cursor.getString(cursor.getColumnIndex("EntryBy"))));
                basicInfo.setLat((cursor.getString(cursor.getColumnIndex("Latitude"))));
                basicInfo.setLongi((cursor.getString(cursor.getColumnIndex("Longitude"))));

                basicInfo.setAgri_year((cursor.getString(cursor.getColumnIndex("Agriculture_year"))));
                basicInfo.setSeason((cursor.getString(cursor.getColumnIndex("Season"))));
                basicInfo.setCrop((cursor.getString(cursor.getColumnIndex("Crop"))));
                basicInfo.setCrop_name((cursor.getString(cursor.getColumnIndex("CropName"))));
                basicInfo.setDist((cursor.getString(cursor.getColumnIndex("District"))));
                basicInfo.setBlock((cursor.getString(cursor.getColumnIndex("Block"))));
                basicInfo.setPanchayat((cursor.getString(cursor.getColumnIndex("Panchayat"))));
                basicInfo.setName_of_selected_village((cursor.getString(cursor.getColumnIndex("Name_of_selected_village"))));
                basicInfo.setHighest_plot_no((cursor.getString(cursor.getColumnIndex("Highest_plot_no_of_Panchayat"))));
                basicInfo.setSurvey_no_khesra_no((cursor.getString(cursor.getColumnIndex("Selected_Survey_No_Khesra"))));
                basicInfo.setFarmer_name((cursor.getString(cursor.getColumnIndex("Farmer_Name"))));
                basicInfo.setUnitOperationalSize((cursor.getString(cursor.getColumnIndex("UnitOperationalSize"))));
                basicInfo.setOperation_size_holding((cursor.getString(cursor.getColumnIndex("Operational_Size_holding"))));
                basicInfo.setUnitareaCoverage((cursor.getString(cursor.getColumnIndex("UnitAreaCoverage"))));
                basicInfo.setArea_covered_crop(cursor.getString((cursor.getColumnIndex("Area_of_crop"))));
                basicInfo.setSystem_of_cutivation((cursor.getString(cursor.getColumnIndex("System_of_cutivation"))));
                basicInfo.setVarities_of_crop((cursor.getString(cursor.getColumnIndex("Type_Of_crop_varities"))));
                basicInfo.setIrrigation_source((cursor.getString(cursor.getColumnIndex("Irrigation_source"))));
                basicInfo.setType_of_manure((cursor.getString(cursor.getColumnIndex("Type_of_manure"))));
                basicInfo.setQuantity_of_used_manure((cursor.getString(cursor.getColumnIndex("Quantity_of_used_manure"))));
                basicInfo.setSource_of_seed((cursor.getString(cursor.getColumnIndex("Source_of_seed"))));
                basicInfo.setUnit_seed_in((cursor.getString(cursor.getColumnIndex("Quantity_seed_in"))));
                basicInfo.setQuantity_of_used_seed((cursor.getString(cursor.getColumnIndex("Quantity_of_used_seed"))));
                basicInfo.setWeather_condition_during_crop_season((cursor.getString(cursor.getColumnIndex("Weather_condition_during"))));
                basicInfo.setExtend_of_damage((cursor.getString(cursor.getColumnIndex("Extend_of_damage"))));
                basicInfo.setRemarks((cursor.getString(cursor.getColumnIndex("Remarks"))));
                basicInfo.setRandom_no_alloted_by_dso((cursor.getString(cursor.getColumnIndex("Random_no_allotted_DCO"))));
                basicInfo.setOrder_of_experiment((cursor.getString(cursor.getColumnIndex("Order_Of_Experiment_according_to_random_No"))));
                basicInfo.setShape_of_cce_area((cursor.getString(cursor.getColumnIndex("Shape_of_Cce_area"))));
                basicInfo.setLenghth_of_field((cursor.getString(cursor.getColumnIndex("Length_of_field"))));
                basicInfo.setBreath_of_field((cursor.getString(cursor.getColumnIndex("Breadth_of_field"))));
                basicInfo.setType_of_land((cursor.getString(cursor.getColumnIndex("Type_of_land"))));
                basicInfo.setDate_of_cutting((cursor.getString(cursor.getColumnIndex("Date_of_cutting"))));
                basicInfo.setGreen_weight((cursor.getString(cursor.getColumnIndex("Green_weight"))));
                basicInfo.setDry_weight((cursor.getString(cursor.getColumnIndex("Dry_weight"))));
                basicInfo.setNo_of_baal((cursor.getString(cursor.getColumnIndex("No_of_baal_Maize"))));
                basicInfo.setWeight_of_baal((cursor.getString(cursor.getColumnIndex("Weight_of_baal_Maize"))));
                basicInfo.setGreen_weight_of_dana((cursor.getString(cursor.getColumnIndex("Green_weight_of_Dana"))));
                basicInfo.setDry_weight_of_dana((cursor.getString(cursor.getColumnIndex("Dry_weight_of_dana"))));

                basicInfo.setRemarks1((cursor.getString(cursor.getColumnIndex("remarks1"))));
                basicInfo.setEntryDate((cursor.getString(cursor.getColumnIndex("EntryDate"))));
                basicInfo.setVaritiesofcrop_name((cursor.getString(cursor.getColumnIndex("varitiescrop_name"))));
                basicInfo.setSub_varitiesofcrop_name((cursor.getString(cursor.getColumnIndex("Sub_Varities_Of_crop"))));


            }
            cursor.close();

            Cursor cursor1 = sqLiteDatabase.rawQuery("select Img1,Img2,Img3,Img4 From BasicDetails where Latitude IS NOT NULL AND EntryBy=? ORDER BY Id  DESC", args);
            Log.e("USERID", Userid);
            while (cursor1.moveToNext()) {
                basicInfo.setImg1((cursor1.getString(cursor1.getColumnIndex("Img1"))));
                basicInfo.setImg2((cursor1.getString(cursor1.getColumnIndex("Img2"))));
                basicInfo.setImg3((cursor1.getString(cursor1.getColumnIndex("Img3"))));
                basicInfo.setImg4((cursor1.getString(cursor1.getColumnIndex("Img4"))));
            }
            cursor1.close();
            if (x > 0) {
                basicdetail.add(basicInfo);
            }
            // this.getReadableDatabase().close();
            //cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            basicdetail = null;
            // TODO: handle exception

        }
        return basicdetail;
    }


    public ArrayList<BasicInfo> getAllEntryByIdold(String userid, String sno) {
        ArrayList<BasicInfo> basicInfos = new ArrayList<BasicInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.rawQuery("select * from BasicDetails where EntryBy=?" + " AND " + " Id=? ", new String[]{userid, sno});

            //Cursor cursor=sqLiteDatabase.rawQuery("select * from BasicDetails where EntryBy=?"+" AND "+" Id=? "+" ORDER BY " + "Id " +"ASC",new String[]{userid,sno});
            int x = cursor.getCount();

            while (cursor.moveToNext()) {
                BasicInfo basicInfo = new BasicInfo();
                basicInfo.setId((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.setUserid((cursor.getString(cursor.getColumnIndex("EntryBy"))));
                basicInfo.setLat((cursor.getString(cursor.getColumnIndex("Latitude"))));
                basicInfo.setLongi((cursor.getString(cursor.getColumnIndex("Longitude"))));
                basicInfo.setImg1((cursor.getString(cursor.getColumnIndex("Img1"))));
                basicInfo.setImg2((cursor.getString(cursor.getColumnIndex("Img2"))));
                basicInfo.setImg3((cursor.getString(cursor.getColumnIndex("Img3"))));
                basicInfo.setImg4((cursor.getString(cursor.getColumnIndex("Img4"))));
                basicInfo.setAgri_year((cursor.getString(cursor.getColumnIndex("Agriculture_year"))));
                basicInfo.setAgri_year_nm((cursor.getString(cursor.getColumnIndex("Agri_year_nm"))));
                basicInfo.setSeason((cursor.getString(cursor.getColumnIndex("Season"))));
                basicInfo.setCrop((cursor.getString(cursor.getColumnIndex("Crop"))));
                basicInfo.setCrop_name((cursor.getString(cursor.getColumnIndex("CropName"))));
                basicInfo.setSub_varitiesofcrop_name((cursor.getString(cursor.getColumnIndex("Sub_Varities_Of_crop"))));
                basicInfo.setDist((cursor.getString(cursor.getColumnIndex("District"))));
                basicInfo.setBlock((cursor.getString(cursor.getColumnIndex("Block"))));
                basicInfo.setPanchayat((cursor.getString(cursor.getColumnIndex("Panchayat"))));
                basicInfo.setName_of_selected_village((cursor.getString(cursor.getColumnIndex("Name_of_selected_village"))));
                basicInfo.setHighest_plot_no((cursor.getString(cursor.getColumnIndex("Highest_plot_no_of_Panchayat"))));
                basicInfo.setSurvey_no_khesra_no((cursor.getString(cursor.getColumnIndex("Selected_Survey_No_Khesra"))));
                basicInfo.setFarmer_name((cursor.getString(cursor.getColumnIndex("Farmer_Name"))));
                basicInfo.setUnitOperationalSize((cursor.getString(cursor.getColumnIndex("UnitOperationalSize"))));
                basicInfo.setOperation_size_holding((cursor.getString(cursor.getColumnIndex("Operational_Size_holding"))));
                basicInfo.setUnitareaCoverage((cursor.getString(cursor.getColumnIndex("UnitAreaCoverage"))));
                basicInfo.setArea_covered_crop(cursor.getString((cursor.getColumnIndex("Area_of_crop"))));
                basicInfo.setSystem_of_cutivation((cursor.getString(cursor.getColumnIndex("System_of_cutivation"))));
                basicInfo.setVarities_of_crop((cursor.getString(cursor.getColumnIndex("Type_Of_crop_varities"))));
                basicInfo.setIrrigation_source((cursor.getString(cursor.getColumnIndex("Irrigation_source"))));
                basicInfo.setType_of_manure((cursor.getString(cursor.getColumnIndex("Type_of_manure"))));
                basicInfo.setQuantity_of_used_manure((cursor.getString(cursor.getColumnIndex("Quantity_of_used_manure"))));
                basicInfo.setSource_of_seed((cursor.getString(cursor.getColumnIndex("Source_of_seed"))));
                basicInfo.setUnit_seed_in((cursor.getString(cursor.getColumnIndex("Quantity_seed_in"))));
                basicInfo.setQuantity_of_used_seed((cursor.getString(cursor.getColumnIndex("Quantity_of_used_seed"))));


                basicInfo.setWeather_condition_during_crop_season((cursor.getString(cursor.getColumnIndex("Weather_condition_during"))));
                basicInfo.setWeatherconditionname((cursor.getString(cursor.getColumnIndex("Weather_condition_during_name"))));


                basicInfo.setExtend_of_damage((cursor.getString(cursor.getColumnIndex("Extend_of_damage"))));
                basicInfo.setRemarks((cursor.getString(cursor.getColumnIndex("Remarks"))));
                basicInfo.setRandom_no_alloted_by_dso((cursor.getString(cursor.getColumnIndex("Random_no_allotted_DCO"))));
                basicInfo.setOrder_of_experiment((cursor.getString(cursor.getColumnIndex("Order_Of_Experiment_according_to_random_No"))));
                basicInfo.setShape_of_cce_area((cursor.getString(cursor.getColumnIndex("Shape_of_Cce_area"))));
                basicInfo.setLenghth_of_field((cursor.getString(cursor.getColumnIndex("Length_of_field"))));
                basicInfo.setBreath_of_field((cursor.getString(cursor.getColumnIndex("Breadth_of_field"))));


                basicInfo.setType_of_land((cursor.getString(cursor.getColumnIndex("Type_of_land"))));
                basicInfo.setType_of_land_Name((cursor.getString(cursor.getColumnIndex("Type_of_land_Name"))));

                basicInfo.setSourceofseed_name((cursor.getString(cursor.getColumnIndex("sourceofseed_name"))));
                basicInfo.setTypeofmanure_name((cursor.getString(cursor.getColumnIndex("tymanure_name"))));
                basicInfo.setSystemofcultivation_name((cursor.getString(cursor.getColumnIndex("systemcultivation_name"))));
                basicInfo.setVaritiesofcrop_name((cursor.getString(cursor.getColumnIndex("varitiescrop_name"))));

                basicInfo.setSub_varitiesofcrop_name((cursor.getString(cursor.getColumnIndex("Sub_Varities_Of_crop"))));

                basicInfo.setDate_of_cutting((cursor.getString(cursor.getColumnIndex("Date_of_cutting"))));
                basicInfo.setGreen_weight((cursor.getString(cursor.getColumnIndex("Green_weight"))));
                basicInfo.setDry_weight((cursor.getString(cursor.getColumnIndex("Dry_weight"))));
                basicInfo.setNo_of_baal((cursor.getString(cursor.getColumnIndex("No_of_baal_Maize"))));
                basicInfo.setWeight_of_baal((cursor.getString(cursor.getColumnIndex("Weight_of_baal_Maize"))));
                basicInfo.setGreen_weight_of_dana((cursor.getString(cursor.getColumnIndex("Green_weight_of_Dana"))));
                basicInfo.setDry_weight_of_dana((cursor.getString(cursor.getColumnIndex("Dry_weight_of_dana"))));

                basicInfo.setRemarks1((cursor.getString(cursor.getColumnIndex("remarks1"))));
                basicInfo.setEntryDate((cursor.getString(cursor.getColumnIndex("EntryDate"))));


                basicInfos.add(basicInfo);

            }

            this.getReadableDatabase().close();
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return basicInfos;
    }


    //--------------------------------------------------------------------
    public ArrayList<BasicInfo> getAllEntryById(String userid, String sno) {
        ArrayList<BasicInfo> basicInfos = new ArrayList<BasicInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            //  Cursor cursor = sqLiteDatabase.rawQuery("select * from BasicDetails where EntryBy=?" + " AND " + " Id=? ", new String[]{userid, sno});
            Cursor cursor = sqLiteDatabase.rawQuery("select Id,Latitude,Agriculture_year,Agri_year_nm,Season,Crop,District,Block,Panchayat,Name_of_selected_village,Highest_plot_no_of_Panchayat,Selected_Survey_No_Khesra,Farmer_Name,Operational_Size_holding,Area_of_crop,System_of_cutivation,Type_Of_crop_varities,Irrigation_source,Type_of_manure,Quantity_of_used_manure,Source_of_seed,Quantity_of_used_seed,Weather_condition_during,Extend_of_damage,Remarks,EntryBy,Random_no_allotted_DCO,Order_Of_Experiment_according_to_random_No,Shape_of_Cce_area,Length_of_field,Breadth_of_field,Type_of_land,Date_of_cutting,Green_weight,Dry_weight,No_of_baal_Maize,Weight_of_baal_Maize,Green_weight_of_Dana,Dry_weight_of_dana,remarks1,Inspection_done,Quantity_seed_in,UnitOperationalSize,UnitAreaCoverage,Longitude,EntryDate,Type_of_land_Name,Weather_condition_during_name,sourceofseed_name,tymanure_name,systemcultivation_name,varitiescrop_name,CropName,Sub_Varities_Of_crop,Type_id,Type_name,Pod_wt_aftr_plucking,Pod_Wt_after_thresing,Baal_wt_aftr_plucking,Baal_wt_aftr_threshing,Total_bundles,bundle1,bundle2,bundle3,bundle4,bundle5,bundle6,bundle7,bundle8,total_bundle_weight,dry_fiber_weight From BasicDetails where EntryBy=?" + " AND " + " Id=?  ORDER BY Id  DESC", new String[]{userid, sno});

            //Cursor cursor=sqLiteDatabase.rawQuery("select * from BasicDetails where EntryBy=?"+" AND "+" Id=? "+" ORDER BY " + "Id " +"ASC",new String[]{userid,sno});
            int x = cursor.getCount();

            while (cursor.moveToNext()) {
                BasicInfo basicInfo = new BasicInfo();
                String rowID = cursor.getString(cursor.getColumnIndex("Id"));
                basicInfo.setId((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.setUserid((cursor.getString(cursor.getColumnIndex("EntryBy"))));
                basicInfo.setLat((cursor.getString(cursor.getColumnIndex("Latitude"))));
                basicInfo.setLongi((cursor.getString(cursor.getColumnIndex("Longitude"))));

                basicInfo.setAgri_year((cursor.getString(cursor.getColumnIndex("Agriculture_year"))));
                basicInfo.setAgri_year_nm((cursor.getString(cursor.getColumnIndex("Agri_year_nm"))));
                basicInfo.setSeason((cursor.getString(cursor.getColumnIndex("Season"))));
                basicInfo.setCrop((cursor.getString(cursor.getColumnIndex("Crop"))));
                basicInfo.setCrop_name((cursor.getString(cursor.getColumnIndex("CropName"))));
                basicInfo.setSub_varitiesofcrop_name((cursor.getString(cursor.getColumnIndex("Sub_Varities_Of_crop"))));
                basicInfo.setDist((cursor.getString(cursor.getColumnIndex("District"))));
                basicInfo.setBlock((cursor.getString(cursor.getColumnIndex("Block"))));
                basicInfo.setPanchayat((cursor.getString(cursor.getColumnIndex("Panchayat"))));
                basicInfo.setName_of_selected_village((cursor.getString(cursor.getColumnIndex("Name_of_selected_village"))));
                basicInfo.setHighest_plot_no((cursor.getString(cursor.getColumnIndex("Highest_plot_no_of_Panchayat"))));
                basicInfo.setSurvey_no_khesra_no((cursor.getString(cursor.getColumnIndex("Selected_Survey_No_Khesra"))));
                basicInfo.setFarmer_name((cursor.getString(cursor.getColumnIndex("Farmer_Name"))));
                basicInfo.setUnitOperationalSize((cursor.getString(cursor.getColumnIndex("UnitOperationalSize"))));
                basicInfo.setOperation_size_holding((cursor.getString(cursor.getColumnIndex("Operational_Size_holding"))));
                basicInfo.setUnitareaCoverage((cursor.getString(cursor.getColumnIndex("UnitAreaCoverage"))));
                basicInfo.setArea_covered_crop(cursor.getString((cursor.getColumnIndex("Area_of_crop"))));
                basicInfo.setSystem_of_cutivation((cursor.getString(cursor.getColumnIndex("System_of_cutivation"))));
                basicInfo.setVarities_of_crop((cursor.getString(cursor.getColumnIndex("Type_Of_crop_varities"))));
                basicInfo.setIrrigation_source((cursor.getString(cursor.getColumnIndex("Irrigation_source"))));
                basicInfo.setType_of_manure((cursor.getString(cursor.getColumnIndex("Type_of_manure"))));
                basicInfo.setQuantity_of_used_manure((cursor.getString(cursor.getColumnIndex("Quantity_of_used_manure"))));
                basicInfo.setSource_of_seed((cursor.getString(cursor.getColumnIndex("Source_of_seed"))));
                basicInfo.setUnit_seed_in((cursor.getString(cursor.getColumnIndex("Quantity_seed_in"))));
                basicInfo.setQuantity_of_used_seed((cursor.getString(cursor.getColumnIndex("Quantity_of_used_seed"))));


                basicInfo.setWeather_condition_during_crop_season((cursor.getString(cursor.getColumnIndex("Weather_condition_during"))));
                basicInfo.setWeatherconditionname((cursor.getString(cursor.getColumnIndex("Weather_condition_during_name"))));


                basicInfo.setExtend_of_damage((cursor.getString(cursor.getColumnIndex("Extend_of_damage"))));
                basicInfo.setRemarks((cursor.getString(cursor.getColumnIndex("Remarks"))));
                basicInfo.setRandom_no_alloted_by_dso((cursor.getString(cursor.getColumnIndex("Random_no_allotted_DCO"))));
                basicInfo.setOrder_of_experiment((cursor.getString(cursor.getColumnIndex("Order_Of_Experiment_according_to_random_No"))));
                basicInfo.setShape_of_cce_area((cursor.getString(cursor.getColumnIndex("Shape_of_Cce_area"))));
                basicInfo.setLenghth_of_field((cursor.getString(cursor.getColumnIndex("Length_of_field"))));
                basicInfo.setBreath_of_field((cursor.getString(cursor.getColumnIndex("Breadth_of_field"))));


                basicInfo.setType_of_land((cursor.getString(cursor.getColumnIndex("Type_of_land"))));
                basicInfo.setType_of_land_Name((cursor.getString(cursor.getColumnIndex("Type_of_land_Name"))));

                basicInfo.setSourceofseed_name((cursor.getString(cursor.getColumnIndex("sourceofseed_name"))));
                basicInfo.setTypeofmanure_name((cursor.getString(cursor.getColumnIndex("tymanure_name"))));
                basicInfo.setSystemofcultivation_name((cursor.getString(cursor.getColumnIndex("systemcultivation_name"))));
                basicInfo.setVaritiesofcrop_name((cursor.getString(cursor.getColumnIndex("varitiescrop_name"))));

                basicInfo.setSub_varitiesofcrop_name((cursor.getString(cursor.getColumnIndex("Sub_Varities_Of_crop"))));

                basicInfo.setDate_of_cutting((cursor.getString(cursor.getColumnIndex("Date_of_cutting"))));
                basicInfo.setGreen_weight((cursor.getString(cursor.getColumnIndex("Green_weight"))));
                basicInfo.setDry_weight((cursor.getString(cursor.getColumnIndex("Dry_weight"))));
                basicInfo.setNo_of_baal((cursor.getString(cursor.getColumnIndex("No_of_baal_Maize"))));
                basicInfo.setWeight_of_baal((cursor.getString(cursor.getColumnIndex("Weight_of_baal_Maize"))));
                basicInfo.setGreen_weight_of_dana((cursor.getString(cursor.getColumnIndex("Green_weight_of_Dana"))));
                basicInfo.setDry_weight_of_dana((cursor.getString(cursor.getColumnIndex("Dry_weight_of_dana"))));
                basicInfo.setRemarks1((cursor.getString(cursor.getColumnIndex("remarks1"))));
                basicInfo.setEntryDate((cursor.getString(cursor.getColumnIndex("EntryDate"))));
                basicInfo.setType_id((cursor.getString(cursor.getColumnIndex("Type_id"))));
                basicInfo.setType_name((cursor.getString(cursor.getColumnIndex("Type_name"))));

                basicInfo.setTotal_nos_of_bundle((cursor.getString(cursor.getColumnIndex("Total_bundles"))));
                basicInfo.setTotal_bundle_weight((cursor.getString(cursor.getColumnIndex("total_bundle_weight"))));
                basicInfo.setBundle1((cursor.getString(cursor.getColumnIndex("bundle1"))));
                basicInfo.setBundle2((cursor.getString(cursor.getColumnIndex("bundle2"))));
                basicInfo.setBundle3((cursor.getString(cursor.getColumnIndex("bundle3"))));
                basicInfo.setBundle4((cursor.getString(cursor.getColumnIndex("bundle4"))));
                basicInfo.setBundle5((cursor.getString(cursor.getColumnIndex("bundle5"))));
                basicInfo.setBundle6((cursor.getString(cursor.getColumnIndex("bundle6"))));
                basicInfo.setBundle7((cursor.getString(cursor.getColumnIndex("bundle7"))));
                basicInfo.setBundle8((cursor.getString(cursor.getColumnIndex("bundle8"))));


                basicInfo.setPod_wt_plucking((cursor.getString(cursor.getColumnIndex("Pod_wt_aftr_plucking"))));
                basicInfo.setPod_wt_threshing((cursor.getString(cursor.getColumnIndex("Pod_Wt_after_thresing"))));
                basicInfo.setBaal_wt_plucking((cursor.getString(cursor.getColumnIndex("Baal_wt_aftr_plucking"))));
                basicInfo.setBaal_wt_threshing((cursor.getString(cursor.getColumnIndex("Baal_wt_aftr_threshing"))));
                basicInfo.setDryFiber_weight((cursor.getString(cursor.getColumnIndex("dry_fiber_weight"))));

                String[] args2 = {rowID};
                String selectSQL = "select Img1,Img2 From BasicDetails where Id=? ORDER BY Id  DESC";
                Cursor cursor1 = sqLiteDatabase.rawQuery(selectSQL, args2);
                Log.e("USERID", userid);
                while (cursor1.moveToNext()) {
                    basicInfo.setImg1((cursor1.getString(cursor1.getColumnIndex("Img1"))));
                    basicInfo.setImg2((cursor1.getString(cursor1.getColumnIndex("Img2"))));

                }

                String[] args3 = {rowID};
                String selectSQL1 = "select Img3,Img4 From BasicDetails where Id=? ORDER BY Id  DESC";
                Cursor cursor2 = sqLiteDatabase.rawQuery(selectSQL1, args3);
                Log.e("USERID", userid);
                while (cursor1.moveToNext()) {

                    basicInfo.setImg3((cursor2.getString(cursor2.getColumnIndex("Img3"))));
                    basicInfo.setImg4((cursor2.getString(cursor2.getColumnIndex("Img4"))));
                }


                basicInfos.add(basicInfo);
                cursor2.close();
            }
            cursor.close();
            this.getReadableDatabase().close();
            sqLiteDatabase.close();


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return basicInfos;
    }
    //----------------------------------------------------------------


    public long EntryUpdate(BasicInfo result) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put("EntryBy", result.getUserid());
            values.put("Agriculture_year", result.getAgri_year());
            values.put("Agri_year_nm", result.getAgri_year_nm());
            values.put("Season", result.getSeason());
            values.put("Crop", result.getCrop());
            values.put("CropName", result.getCrop_name());
            values.put("District", result.getDist());
            values.put("Block", result.getBlock());
            values.put("Panchayat", result.getPanchayat());
            values.put("Name_of_selected_village", result.getName_of_selected_village());
            values.put("Highest_plot_no_of_Panchayat", result.getHighest_plot_no());
            values.put("Selected_Survey_No_Khesra", result.getSurvey_no_khesra_no());
            values.put("Farmer_Name", result.getFarmer_name());
            values.put("UnitOperationalSize", result.getUnitOperationalSize());
            values.put("Operational_Size_holding", result.getOperation_size_holding());
            values.put("UnitAreaCoverage", result.getUnitareaCoverage());
            values.put("Area_of_crop", result.getArea_covered_crop());
            values.put("System_of_cutivation", result.getSystem_of_cutivation());
            values.put("Type_Of_crop_varities", result.getVarities_of_crop());
            values.put("Irrigation_source", result.getIrrigation_source());
            values.put("Type_of_manure", result.getType_of_manure());
            values.put("Quantity_of_used_manure", result.getQuantity_of_used_manure());
            values.put("Source_of_seed", result.getSource_of_seed());
            values.put("Quantity_seed_in", result.getUnit_seed_in());
            values.put("Quantity_of_used_seed", result.getQuantity_of_used_seed());
            values.put("Weather_condition_during", result.getWeather_condition_during_crop_season());
            values.put("Extend_of_damage", result.getExtend_of_damage());
            values.put("Remarks", result.getRemarks());
            values.put("Random_no_allotted_DCO", result.getRandom_no_alloted_by_dso());
            values.put("Order_Of_Experiment_according_to_random_No", result.getOrder_of_experiment());
            values.put("Shape_of_Cce_area", result.getShape_of_cce_area());
            values.put("Length_of_field", result.getLenghth_of_field());
            values.put("Breadth_of_field", result.getBreath_of_field());
            values.put("Type_of_land", result.getType_of_land());
            values.put("Type_of_land_Name", result.getType_of_land_Name());
            values.put("Date_of_cutting", result.getDate_of_cutting());
            values.put("Green_weight", result.getGreen_weight());
            values.put("Dry_weight", result.getDry_weight());
            values.put("No_of_baal_Maize", result.getNo_of_baal());
            values.put("Weight_of_baal_Maize", result.getWeight_of_baal());
            values.put("Green_weight_of_Dana", result.getGreen_weight_of_dana());
            values.put("Dry_weight_of_dana", result.getDry_weight_of_dana());

            values.put("remarks1", result.getRemarks1());
            values.put("Inspection_done", result.getInspectionDone());
            values.put("EntryDate", result.getEntryDate());
            values.put("sourceofseed_name", result.getSourceofseed_name());
            values.put("Weather_condition_during_name", result.getWeatherconditionname());
            values.put("tymanure_name", result.getTypeofmanure_name());
            values.put("systemcultivation_name", result.getSystemofcultivation_name());
            values.put("varitiescrop_name", result.getVaritiesofcrop_name());
            values.put("Sub_Varities_Of_crop", result.getSub_varitiesofcrop_name());
            values.put("Type_id", result.getType_id());
            values.put("Type_name", result.getType_name());
            values.put("Pod_wt_aftr_plucking", result.getPod_wt_plucking());
            values.put("Pod_Wt_after_thresing", result.getPod_wt_threshing());
            values.put("Baal_wt_aftr_plucking", result.getBaal_wt_plucking());
            values.put("Baal_wt_aftr_threshing", result.getBaal_wt_threshing());
            values.put("Total_bundles", result.getTotal_nos_of_bundle());
            values.put("bundle1", result.getBundle1());
            values.put("bundle2", result.getBundle2());
            values.put("bundle3", result.getBundle3());
            values.put("bundle4", result.getBundle4());
            values.put("bundle5", result.getBundle5());
            values.put("bundle6", result.getBundle6());
            values.put("bundle7", result.getBundle7());
            values.put("bundle8", result.getBundle8());
            values.put("total_bundle_weight", result.getTotal_bundle_weight());
            values.put("dry_fiber_weight", result.getDryFiber_weight());
            String[] whereArgs = new String[]{(String.valueOf(result.getId()))};

            // String[] whereArgs = {result.getId()};
            c = db.update("BasicDetails", values, "Id=?", whereArgs);
            // c = db.update("BasicDetails", values, "Id=?", whereArgs);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return c;
    }

    //----------------------------------------------------------------
    public int getNumberTotalOfUploadData(String userId) {

        int x = 0;
        try {

            Log.e("USERID", userId);
            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select * from BasicDetails where Latitude IS NOT NULL and EntryBy=?", new String[]{userId});
            x = cur.getCount();
//            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    //----------------------------------------------------------------
    public int getNumberTotalOfUploadDataPhase1(String userId) {

        int x = 0;
        try {

            Log.e("USERID", userId);
            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select * from ChngKhesraDetails where Lat_fieldfinal IS NOT NULL and User_Id=?", new String[]{userId});
            x = cur.getCount();
//            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }


    public ArrayList<Financial_Year> getFinancialYearLocal() {
        ArrayList<Financial_Year> bdetail = new ArrayList<Financial_Year>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Financial_Year order by Year_id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Financial_Year financial_year = new Financial_Year();
                financial_year.setYear_Id(cur.getString(cur.getColumnIndex("Year_id")));
                financial_year.setFinancial_year((cur.getString(cur.getColumnIndex("F_year"))));
                bdetail.add(financial_year);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    //----------------------------------------------------------------
    public long setFinancialYear(ArrayList<Financial_Year> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Financial_Year> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("Financial_Year",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Year_id", info.get(i).getYear_Id());
                    values.put("F_year", info.get(i).getFinancial_year());


                    String[] whereArgs = new String[]{info.get(i).getYear_Id()};

                    c = db.update("Financial_Year", values, "Year_id=?", whereArgs);
                    if (!(c > 0))
                    {
                        c = db.insert("Financial_Year", null, values);
                    }

                }
                db.close();


            }
            catch (Exception e)
            {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public long setTypeListData(ArrayList<TypeList> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<TypeList> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("TypeList",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("id", info.get(i).getId());
                    values.put("typeName", info.get(i).getName());
                    values.put("crop_id", info.get(i).getCropID());


                    String[] whereArgs = new String[]{info.get(i).getId()};

//                    c = db.update("TypeList", values, "id=?", whereArgs);
//                    if (!(c > 0))
//                    {
                        c = db.insert("TypeList", null, values);
                   // }

                }
                db.close();


            }
            catch (Exception e)
            {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<Season_List> getSeasonLocal() {
        ArrayList<Season_List> bdetail = new ArrayList<Season_List>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Season order by season_id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Season_List season_list = new Season_List();
                season_list.setSeason_Id(cur.getString(cur.getColumnIndex("season_id")));
                season_list.setSeason_Name((cur.getString(cur.getColumnIndex("season_name"))));
                season_list.setFlag((cur.getString(cur.getColumnIndex("flag"))));
                bdetail.add(season_list);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }


    public long setSeasonLocal(ArrayList<Season_List> list) {


        long c = -1;
        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Season_List> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("Season",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("season_id", info.get(i).getSeason_Id());
                    values.put("season_name", info.get(i).getSeason_Name());
                    values.put("flag", info.get(i).getFlag());

                    String[] whereArgs = new String[]{info.get(i).getSeason_Id()};

                    c = db.update("Season", values, "season_id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Season", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<CropType> getCropTypeLocal(String weatherId) {
        ArrayList<CropType> bdetail = new ArrayList<CropType>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  CropType where Weather_Id = '" + weatherId + "'", null);
            //Cursor cur = db.rawQuery("select * from CropType order by Weather_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                CropType cropType = new CropType();
                cropType.setWetherId(cur.getString(cur.getColumnIndex("Weather_Id")));
                cropType.setWeatherNm(cur.getString(cur.getColumnIndex("Weather_nm")));
                cropType.setCropId(cur.getString(cur.getColumnIndex("Crop_Id")));
                cropType.setCropName((cur.getString(cur.getColumnIndex("Crop_Name"))));
                bdetail.add(cropType);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public ArrayList<TypeList> getTypeLocal(String cropid) {
        ArrayList<TypeList> bdetail = new ArrayList<TypeList>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  TypeList where crop_id = '" + cropid + "'", null);
            //Cursor cur = db.rawQuery("select * from CropType order by Weather_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                TypeList cropType = new TypeList();
                cropType.setId(cur.getString(cur.getColumnIndex("id")));
                cropType.setName(cur.getString(cur.getColumnIndex("typeName")));


                bdetail.add(cropType);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }


    public ArrayList<KhesraNo_List> getKhesraLocal(String userid, String agriyr) {
        ArrayList<KhesraNo_List> bdetail = new ArrayList<KhesraNo_List>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  KhesraList where userid = '" + userid + "' and AgriYr = '" + agriyr + "'", null);
            //Cursor cur = db.rawQuery("select * from CropType order by Weather_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                KhesraNo_List cropType = new KhesraNo_List();

                cropType.setKhesraNm(cur.getString(cur.getColumnIndex("KhesraNo")));

                bdetail.add(cropType);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setCropTypeLocal(ArrayList<CropType> list) {


        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }
        ArrayList<CropType> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("CropType",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Weather_Id", info.get(i).getWetherId());
                    values.put("Weather_nm", info.get(i).getWeatherNm());
                    values.put("Crop_Id", info.get(i).getCropId());
                    values.put("Crop_Name", info.get(i).getCropName());
                    String[] whereArgs = new String[]{info.get(i).getCropId()};

                    c = db.update("CropType", values, "Crop_Id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("CropType", null, values);
                    }


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<Source_Seed> getSourceseedlocal() {
        ArrayList<Source_Seed> bdetail = new ArrayList<Source_Seed>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Source_Seed order by Seed_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Source_Seed source_seed = new Source_Seed();
                source_seed.setSeed_id(cur.getString(cur.getColumnIndex("Seed_Id")));
                source_seed.setSeed_Name((cur.getString(cur.getColumnIndex("Seed_Name"))));
                bdetail.add(source_seed);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setSourceseedlocal(ArrayList<Source_Seed> list) {


        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Source_Seed> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("Source_Seed",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Seed_Id", info.get(i).getSeed_id());
                    values.put("Seed_Name", info.get(i).getSeed_Name());
                    String[] whereArgs = new String[]{info.get(i).getSeed_id()};

                    c = db.update("Source_Seed", values, "Seed_Id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Source_Seed", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public String getNameFor(String tblName, String whereColumnName, String returnColumnValue, String thisID) {
        String thisValue = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from " + tblName + " WHERE " + whereColumnName + "='" + thisID.trim() + "'", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                thisValue = cur.getString(cur.getColumnIndex(returnColumnValue));
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisValue.trim();
    }

    public ArrayList<District_list> getDistrictLocal() {
        ArrayList<District_list> bdetail = new ArrayList<District_list>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from District order by Dist_code", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                District_list district_list = new District_list();
                district_list.setDistt_code(cur.getString(cur.getColumnIndex("Dist_code")));
                district_list.setDistt_name((cur.getString(cur.getColumnIndex("Dist_Name"))));
                bdetail.add(district_list);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setDistrictName(ArrayList<District_list> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<District_list> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("District",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Dist_code", info.get(i).getDistt_code());
                    values.put("Dist_Name", info.get(i).getDistt_name());

                    String[] whereArgs = new String[]{info.get(i).getDistt_code()};

                    c = db.update("District", values, "Dist_code=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("District", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }


    public ArrayList<Block_List> getBlockLocal(String distcode) {
        ArrayList<Block_List> bdetail = new ArrayList<Block_List>();
        try {
            String[] param = {distcode};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from Block where Dist_code=?", param);


            int x = cur.getCount();
            while (cur.moveToNext()) {
                Block_List block_list = new Block_List();
                block_list.setBlk_Code(cur.getString(cur.getColumnIndex("Block_Code")));
                block_list.setBlk_Name((cur.getString(cur.getColumnIndex("Block_Name"))));
                block_list.setDist_code((cur.getString(cur.getColumnIndex("Dist_code"))));
                bdetail.add(block_list);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setBlockName(ArrayList<Block_List> list, String dist_code) {


        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Block_List> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("Block",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Block_Code", info.get(i).getBlk_Code());
                    values.put("Block_Name", info.get(i).getBlk_Name());
                    values.put("Dist_code", dist_code);

                    String[] whereArgs = new String[]{info.get(i).getBlk_Code()};

                    c = db.update("Block", values, "Block_Code=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Block", null, values);
                    }

                    //c = db.insert("Block", null, values);


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }


    public ArrayList<Panchayat_List> getPanchayatLocal(String userid) {
        ArrayList<Panchayat_List> bdetail = new ArrayList<Panchayat_List>();
        try {
            String[] param = {userid};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  Panchayat where UserId = '" + userid + "'", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Panchayat_List panchayat_list = new Panchayat_List();
                panchayat_list.setPanchayat_code(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                panchayat_list.setPanchayat_Name((cur.getString(cur.getColumnIndex("Panchayat_Name"))));
                panchayat_list.setUserId((cur.getString(cur.getColumnIndex("UserId"))));
                bdetail.add(panchayat_list);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setPanchayatName(ArrayList<Panchayat_List> list) {


        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Panchayat_List> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("Panchayat",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Panchayat_Code", info.get(i).getPanchayat_code());
                    values.put("Panchayat_Name", info.get(i).getPanchayat_Name());
                    values.put("UserId", info.get(i).getUserId());

                    String[] whereArgs = new String[]{info.get(i).getPanchayat_code()};

                    c = db.update("Panchayat", values, "Panchayat_Code=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Panchayat", null, values);
                    }


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<System_Cutivation> getCutivationLocal() {
        ArrayList<System_Cutivation> bdetail = new ArrayList<System_Cutivation>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Cutivation_type order by cutivation_id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                System_Cutivation system_cutivation = new System_Cutivation();
                system_cutivation.setCutivation_id(cur.getString(cur.getColumnIndex("cutivation_id")));
                system_cutivation.setCutivation_name((cur.getString(cur.getColumnIndex("cutivation_name"))));
                bdetail.add(system_cutivation);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setCutivationLocal(ArrayList<System_Cutivation> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<System_Cutivation> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("Cutivation_type",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("cutivation_id", info.get(i).getCutivation_id());
                    values.put("cutivation_name", info.get(i).getCutivation_name());
                    String[] whereArgs = new String[]{info.get(i).getCutivation_id()};
                    c = db.update("Cutivation_type", values, "cutivation_id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Cutivation_type", null, values);
                    }

                    //c = db.insert("Cutivation_type", null, values);


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<Weather_Condition> getWeatherLocal() {
        ArrayList<Weather_Condition> bdetail = new ArrayList<Weather_Condition>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from WeatherCondition order by weather_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Weather_Condition weather_condition = new Weather_Condition();
                weather_condition.setWeather_Id(cur.getString(cur.getColumnIndex("weather_Id")));
                weather_condition.setWeather_Name((cur.getString(cur.getColumnIndex("weather_name"))));
                bdetail.add(weather_condition);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setWeatherLocal(ArrayList<Weather_Condition> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Weather_Condition> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("WeatherCondition",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("weather_Id", info.get(i).getWeather_Id());
                    values.put("weather_name", info.get(i).getWeather_Name());
                    String[] whereArgs = new String[]{info.get(i).getWeather_Id()};
                    c = db.update("WeatherCondition", values, "weather_Id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("WeatherCondition", null, values);
                    }

                    //c = db.insert("WeatherCondition", null, values);


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<Varities_of_Crop> getVaritiesCropLocal() {
        ArrayList<Varities_of_Crop> bdetail = new ArrayList<Varities_of_Crop>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Varitiesofcrop order by varities_id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Varities_of_Crop varities_of_crop = new Varities_of_Crop();
                varities_of_crop.setCrop_Varities_Id(cur.getString(cur.getColumnIndex("varities_id")));
                varities_of_crop.setCrop_Varities_Name((cur.getString(cur.getColumnIndex("varities_name"))));
                bdetail.add(varities_of_crop);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }


    public ArrayList<KhesraNo_List> getKhesraListLocal(String userid, String season, String crop, String pancode) {
        ArrayList<KhesraNo_List> bdetail = new ArrayList<KhesraNo_List>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] param = {userid, season, crop, pancode};

            Cursor cur = db.rawQuery("SELECT * FROM  KhesraList where userid =? AND season=? AND croptype=? AND pan_id = ?", param);
            // Cursor cur = db.rawQuery("SELECT * FROM  KhesraList where pan_id = ?", param);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                KhesraNo_List varities_of_crop = new KhesraNo_List();
                varities_of_crop.setKhesraNm((cur.getString(cur.getColumnIndex("KhesraNo"))));
                varities_of_crop.setPanid((cur.getString(cur.getColumnIndex("pan_id"))));
                varities_of_crop.setAgriyr((cur.getString(cur.getColumnIndex("AgriYr"))));
                varities_of_crop.setKhesra_crop((cur.getString(cur.getColumnIndex("croptype"))));
                varities_of_crop.setKhesra_season((cur.getString(cur.getColumnIndex("season"))));
                bdetail.add(varities_of_crop);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }


    public long setVaritiesCropLocal(ArrayList<Varities_of_Crop> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Varities_of_Crop> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("Varitiesofcrop",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("varities_id", info.get(i).getCrop_Varities_Id());
                    values.put("varities_name", info.get(i).getCrop_Varities_Name());

                    String[] whereArgs = new String[]{info.get(i).getCrop_Varities_Id()};
                    c = db.update("Varitiesofcrop", values, "varities_id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Varitiesofcrop", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<Manure_type> getManureTypeLocal() {
        ArrayList<Manure_type> bdetail = new ArrayList<Manure_type>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from ManureType order by manure_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Manure_type manure_type = new Manure_type();
                manure_type.setManure_type_id(cur.getString(cur.getColumnIndex("manure_Id")));
                manure_type.setManure_type_name((cur.getString(cur.getColumnIndex("manure_name"))));
                bdetail.add(manure_type);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setManureTypeLocal(ArrayList<Manure_type> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Manure_type> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("ManureType",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("manure_Id", info.get(i).getManure_type_id());
                    values.put("manure_name", info.get(i).getManure_type_name());

                    String[] whereArgs = new String[]{info.get(i).getManure_type_id()};
                    c = db.update("ManureType", values, "manure_Id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("ManureType", null, values);
                    }

                    //c = db.insert("ManureType", null, values);


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<ShapeCceArea> getShapeCceLocal() {
        ArrayList<ShapeCceArea> bdetail = new ArrayList<ShapeCceArea>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from ShapeCce order by CceId", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                ShapeCceArea shapeCceArea = new ShapeCceArea();
                shapeCceArea.setCceAreaId(cur.getString(cur.getColumnIndex("CceId")));
                shapeCceArea.setCceAreaNm((cur.getString(cur.getColumnIndex("CceNm"))));
                bdetail.add(shapeCceArea);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setShapeCceLocal(ArrayList<ShapeCceArea> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<ShapeCceArea> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("ShapeCce",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("CceId", info.get(i).getCceAreaId());
                    values.put("CceNm", info.get(i).getCceAreaNm());

                    String[] whereArgs = new String[]{info.get(i).getCceAreaId()};

                    c = db.update("ShapeCce", values, "CceId=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("ShapeCce", null, values);
                    }

                    //c = db.insert("ShapeCce", null, values);


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<LandType> getLandTypeLocal() {
        ArrayList<LandType> bdetail = new ArrayList<LandType>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from LandType order by Land_Id", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                LandType landType = new LandType();
                landType.setLand_Id(cur.getString(cur.getColumnIndex("Land_Id")));
                landType.setLand_type((cur.getString(cur.getColumnIndex("Land_Nm"))));
                bdetail.add(landType);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long setLandTypeLocall(ArrayList<LandType> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<LandType> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("LandType",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Land_Id", info.get(i).getLand_Id());
                    values.put("Land_Nm", info.get(i).getLand_type());

                    String[] whereArgs = new String[]{info.get(i).getLand_Id()};

                    c = db.update("LandType", values, "Land_Id=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("LandType", null, values);
                    }

                    //c = db.insert("LandType", null, values);


                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }


    public long setValidateData(String uid, String kheshraNum, String randomNum) {


        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("ValidateData",null,null);

        try {


            values.put("UserId", uid.trim());
            values.put("SurveyNo", kheshraNum.trim());
            values.put("RandomNo", randomNum.trim());
            String[] WhereCndition = {uid.trim(), kheshraNum.trim(), randomNum.trim()};
            c = db.update("ValidateData", values, "UserId=? AND SurveyNo=? AND RandomNo=?", WhereCndition);

            if (c <= 0) {
                c = db.insert("ValidateData", null, values);
            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }
        return c;
    }


    public ArrayList<BasicInfo> getAllEntryDetailsingle(String Userid, String rowid) {
        ArrayList<BasicInfo> basicdetail = new ArrayList<BasicInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {Userid, rowid};
            // Cursor cursor=sqLiteDatabase.rawQuery("select * From BasicDetails where EntryBy=? AND Id=?",args);
            Cursor cursor = sqLiteDatabase.rawQuery("select Id,Latitude,Agriculture_year,Season,Crop,District,Block,Panchayat,Name_of_selected_village,Highest_plot_no_of_Panchayat,Selected_Survey_No_Khesra,Farmer_Name,Operational_Size_holding,Area_of_crop,System_of_cutivation,Type_Of_crop_varities,Irrigation_source,Type_of_manure,Quantity_of_used_manure,Source_of_seed,Quantity_of_used_seed,Weather_condition_during,Extend_of_damage,Remarks,EntryBy,Random_no_allotted_DCO,Order_Of_Experiment_according_to_random_No,Shape_of_Cce_area,Length_of_field,Breadth_of_field,Type_of_land,Date_of_cutting,Green_weight,Dry_weight,No_of_baal_Maize,Weight_of_baal_Maize,Green_weight_of_Dana,Dry_weight_of_dana,remarks1,Inspection_done,Quantity_seed_in,UnitOperationalSize,UnitAreaCoverage,Longitude,EntryDate,Type_of_land_Name,Weather_condition_during_name,sourceofseed_name,tymanure_name,systemcultivation_name,varitiescrop_name,CropName,Sub_Varities_Of_crop,DuringThreshing_Img,Type_id,Type_name,Pod_wt_aftr_plucking,Pod_Wt_after_thresing,Baal_wt_aftr_plucking,Baal_wt_aftr_threshing,Total_bundles,bundle1,bundle2,bundle3,bundle4,bundle5,bundle6,bundle7,bundle8,total_bundle_weight,dry_fiber_weight From BasicDetails where EntryBy=? AND Id=?", args);

            int x = cursor.getCount();

            BasicInfo basicInfo = new BasicInfo();
            while (cursor.moveToNext()) {

                basicInfo.setId((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.setUserid((cursor.getString(cursor.getColumnIndex("EntryBy"))));
                basicInfo.setLat((cursor.getString(cursor.getColumnIndex("Latitude"))));
                basicInfo.setLongi((cursor.getString(cursor.getColumnIndex("Longitude"))));
                basicInfo.setAgri_year((cursor.getString(cursor.getColumnIndex("Agriculture_year"))));
                basicInfo.setSeason((cursor.getString(cursor.getColumnIndex("Season"))));
                basicInfo.setCrop((cursor.getString(cursor.getColumnIndex("Crop"))));
                basicInfo.setDist((cursor.getString(cursor.getColumnIndex("District"))));
                basicInfo.setBlock((cursor.getString(cursor.getColumnIndex("Block"))));
                basicInfo.setPanchayat((cursor.getString(cursor.getColumnIndex("Panchayat"))));
                basicInfo.setName_of_selected_village((cursor.getString(cursor.getColumnIndex("Name_of_selected_village"))));
                basicInfo.setHighest_plot_no((cursor.getString(cursor.getColumnIndex("Highest_plot_no_of_Panchayat"))));
                basicInfo.setSurvey_no_khesra_no((cursor.getString(cursor.getColumnIndex("Selected_Survey_No_Khesra"))));
                basicInfo.setFarmer_name((cursor.getString(cursor.getColumnIndex("Farmer_Name"))));
                basicInfo.setUnitOperationalSize((cursor.getString(cursor.getColumnIndex("UnitOperationalSize"))));
                basicInfo.setOperation_size_holding((cursor.getString(cursor.getColumnIndex("Operational_Size_holding"))));
                basicInfo.setUnitareaCoverage((cursor.getString(cursor.getColumnIndex("UnitAreaCoverage"))));
                basicInfo.setArea_covered_crop(cursor.getString((cursor.getColumnIndex("Area_of_crop"))));
                basicInfo.setSystem_of_cutivation((cursor.getString(cursor.getColumnIndex("System_of_cutivation"))));
                basicInfo.setVarities_of_crop((cursor.getString(cursor.getColumnIndex("Type_Of_crop_varities"))));
                basicInfo.setIrrigation_source((cursor.getString(cursor.getColumnIndex("Irrigation_source"))));
                basicInfo.setType_of_manure((cursor.getString(cursor.getColumnIndex("Type_of_manure"))));
                basicInfo.setQuantity_of_used_manure((cursor.getString(cursor.getColumnIndex("Quantity_of_used_manure"))));
                basicInfo.setSource_of_seed((cursor.getString(cursor.getColumnIndex("Source_of_seed"))));
                basicInfo.setUnit_seed_in((cursor.getString(cursor.getColumnIndex("Quantity_seed_in"))));
                basicInfo.setQuantity_of_used_seed((cursor.getString(cursor.getColumnIndex("Quantity_of_used_seed"))));
                basicInfo.setWeather_condition_during_crop_season((cursor.getString(cursor.getColumnIndex("Weather_condition_during"))));
                basicInfo.setExtend_of_damage((cursor.getString(cursor.getColumnIndex("Extend_of_damage"))));
                basicInfo.setRemarks((cursor.getString(cursor.getColumnIndex("Remarks"))));
                basicInfo.setRandom_no_alloted_by_dso((cursor.getString(cursor.getColumnIndex("Random_no_allotted_DCO"))));
                basicInfo.setOrder_of_experiment((cursor.getString(cursor.getColumnIndex("Order_Of_Experiment_according_to_random_No"))));
                basicInfo.setShape_of_cce_area((cursor.getString(cursor.getColumnIndex("Shape_of_Cce_area"))));
                basicInfo.setLenghth_of_field((cursor.getString(cursor.getColumnIndex("Length_of_field"))));
                basicInfo.setBreath_of_field((cursor.getString(cursor.getColumnIndex("Breadth_of_field"))));
                basicInfo.setType_of_land((cursor.getString(cursor.getColumnIndex("Type_of_land"))));
                basicInfo.setDate_of_cutting((cursor.getString(cursor.getColumnIndex("Date_of_cutting"))));
                basicInfo.setGreen_weight((cursor.getString(cursor.getColumnIndex("Green_weight"))));
                basicInfo.setDry_weight((cursor.getString(cursor.getColumnIndex("Dry_weight"))));
                basicInfo.setNo_of_baal((cursor.getString(cursor.getColumnIndex("No_of_baal_Maize"))));
                basicInfo.setWeight_of_baal((cursor.getString(cursor.getColumnIndex("Weight_of_baal_Maize"))));
                basicInfo.setGreen_weight_of_dana((cursor.getString(cursor.getColumnIndex("Green_weight_of_Dana"))));
                basicInfo.setDry_weight_of_dana((cursor.getString(cursor.getColumnIndex("Dry_weight_of_dana"))));

                basicInfo.setRemarks1((cursor.getString(cursor.getColumnIndex("remarks1"))));
                basicInfo.setEntryDate((cursor.getString(cursor.getColumnIndex("EntryDate"))));
                basicInfo.setType_id((cursor.getString(cursor.getColumnIndex("Type_id"))));
                basicInfo.setType_name((cursor.getString(cursor.getColumnIndex("Type_name"))));

                basicInfo.setTotal_nos_of_bundle((cursor.getString(cursor.getColumnIndex("Total_bundles"))));
                basicInfo.setTotal_bundle_weight((cursor.getString(cursor.getColumnIndex("total_bundle_weight"))));
                basicInfo.setBundle1((cursor.getString(cursor.getColumnIndex("bundle1"))));
                basicInfo.setBundle2((cursor.getString(cursor.getColumnIndex("bundle2"))));
                basicInfo.setBundle3((cursor.getString(cursor.getColumnIndex("bundle3"))));
                basicInfo.setBundle4((cursor.getString(cursor.getColumnIndex("bundle4"))));
                basicInfo.setBundle5((cursor.getString(cursor.getColumnIndex("bundle5"))));
                basicInfo.setBundle6((cursor.getString(cursor.getColumnIndex("bundle6"))));
                basicInfo.setBundle7((cursor.getString(cursor.getColumnIndex("bundle7"))));
                basicInfo.setBundle8((cursor.getString(cursor.getColumnIndex("bundle8"))));


                basicInfo.setPod_wt_plucking((cursor.getString(cursor.getColumnIndex("Pod_wt_aftr_plucking"))));
                basicInfo.setPod_wt_threshing((cursor.getString(cursor.getColumnIndex("Pod_Wt_after_thresing"))));
                basicInfo.setBaal_wt_plucking((cursor.getString(cursor.getColumnIndex("Baal_wt_aftr_plucking"))));
                basicInfo.setBaal_wt_threshing((cursor.getString(cursor.getColumnIndex("Baal_wt_aftr_threshing"))));
                basicInfo.setDryFiber_weight((cursor.getString(cursor.getColumnIndex("dry_fiber_weight"))));
            }

            cursor.close();
            Cursor cursor1 = sqLiteDatabase.rawQuery("select Img1,Img2 From BasicDetails where EntryBy=? AND Id=?", args);
            while (cursor1.moveToNext()) {
                basicInfo.setImg1((cursor1.getString(cursor1.getColumnIndex("Img1"))));
                basicInfo.setImg2((cursor1.getString(cursor1.getColumnIndex("Img2"))));
            }

            cursor1.close();
            Cursor cursor2 = sqLiteDatabase.rawQuery("select Img3,Img4 From BasicDetails where EntryBy=? AND Id=?", args);
            while (cursor2.moveToNext()) {
                basicInfo.setImg3((cursor2.getString(cursor2.getColumnIndex("Img3"))));
                basicInfo.setImg4((cursor2.getString(cursor2.getColumnIndex("Img4"))));
            }
            cursor2.close();

            Cursor cursor3 = sqLiteDatabase.rawQuery("select DuringThreshing_Img From BasicDetails where EntryBy=? AND Id=?", args);
            while (cursor3.moveToNext()) {
                basicInfo.setImg5((cursor3.getString(cursor3.getColumnIndex("DuringThreshing_Img"))));
            }
            cursor3.close();

            basicdetail.add(basicInfo);
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return basicdetail;
    }


    public ArrayList<CheckList> getchklist_List() {

        ArrayList<CheckList> checkList = new ArrayList<CheckList>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT Chk_Id,Chk_Name from CheckList ",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                CheckList gen = new CheckList();
                gen.setChecklist_Id(cur.getString(cur.getColumnIndex("Chk_Id")));
                gen.setChecklist_Name(cur.getString(cur.getColumnIndex("Chk_Name")));

                checkList.add(gen);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkList;
    }

    public long InsertNewEntry(KhesraEntryActivity newEntryActivity, NewLandKhesraInfo result) {

        long c = -1;
        try {
            DataBaseHelper placeData = new DataBaseHelper(newEntryActivity);
            SQLiteDatabase db = placeData.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("User_Id", result.get_phase1_userid());
            values.put("argi_year", result.get_phse1_agri_yr());
            values.put("agri_ye_nm", result.get_phse1_agri_yr_nm());
            values.put("season_id", result.get_phase1_season());
            values.put("season_name", result.get_phase1_season_nm());
            //values.put("season_name", result.getphase1());
            values.put("croptype_id", result.get_phase1_croptype());
            values.put("croptype_name", result.get_phase1_crop_name());
            values.put("dist_code", result.get_phase1_dist());
            values.put("blk_code", result.get_phase1_block());
            values.put("panchayat_id", result.get_phase1_panchayat());
            values.put("panchayat_nm", result.get_phase1_panchayat_nm());

            values.put("plot_no", result.get_phase1_highest_plot_no());
            values.put("alloted_khesra", result.get_phase1_alloted_khesra_no());
            values.put("chng_khesra_id", result.get_chng_khesra_no());
            values.put("chng_khesra_nm", result.get_chng_khesra_no_nm());

            values.put("final_khesra", result.get_phase1_final_khesra_no());

            values.put("img_naksha", result.getNazriNkasha());
            values.put("img_field", result.getFieldImg());
            values.put("FinalSelectedImg", result.getFinalSelectedField());
//            values.put("Lat_naksha", result.get_phase1_lat());
//            values.put("Long_naksha", result.get_phase1_longi());
//            values.put("Lat_field", result.get_phase1_lat1());
//            values.put("Long_field", result.get_phase1_longi1());
//            values.put("Lat_fieldfinal", result.get_phase1_lat2());
//            values.put("Long_filedFinal", result.get_phase1_longi2());

            values.put("Lat_naksha", result.get_phase1_lat1());
            values.put("Long_naksha", result.get_phase1_longi1());
            values.put("Lat_field", result.get_phase1_lat());
            values.put("Long_field", result.get_phase1_longi());
            values.put("Lat_fieldfinal", result.get_phase1_lat2());
            values.put("Long_filedFinal", result.get_phase1_longi2());


            values.put("Entry_date", result.get_phase1_Entry_date());
            values.put("FarmerName", result.get_Farmer_Name());
            values.put("chngRemarks", result.get_chng_Remarks());
            values.put("RevenueVilage", result.get_revenue_village());
            values.put("CceTentativedate", result.get_tentative_cce_date());
            values.put("Type_id", result.get_Type_List_Id());
            values.put("Type_name", result.get_Type_List_NM());

            values.put("Flag", "I");

            c = db.insert("ChngKhesraDetails", null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }

    public ArrayList<NewLandKhesraInfo> getAllChngKhesraDetails(String userid, String sno) {
        ArrayList<NewLandKhesraInfo> khesraInfos = new ArrayList<NewLandKhesraInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            //  Cursor cursor = sqLiteDatabase.rawQuery("select * from ChngKhesraDetails where User_Id=?" + " AND " + " Id=? ", new String[]{userid, sno});
            Cursor cursor = sqLiteDatabase.rawQuery("select * from ChngKhesraDetails where User_Id=?" + " AND " + " Id=? ", new String[]{userid, sno});

            int x = cursor.getCount();

            while (cursor.moveToNext()) {
                NewLandKhesraInfo chngkhesraInfo = new NewLandKhesraInfo();
                chngkhesraInfo.set_phase1_id((cursor.getString(cursor.getColumnIndex("Id"))));
                chngkhesraInfo.set_phse1_agri_yr((cursor.getString(cursor.getColumnIndex("argi_year"))));
                chngkhesraInfo.set_phse1_agri_yr_nm((cursor.getString(cursor.getColumnIndex("agri_ye_nm"))));
                chngkhesraInfo.set_phase1_season((cursor.getString(cursor.getColumnIndex("season_id"))));
                chngkhesraInfo.set_phase1_season_nm((cursor.getString(cursor.getColumnIndex("season_name"))));
                chngkhesraInfo.set_phase1_croptype((cursor.getString(cursor.getColumnIndex("croptype_id"))));
                chngkhesraInfo.set_phase1_crop_name((cursor.getString(cursor.getColumnIndex("croptype_name"))));
                chngkhesraInfo.set_phase1_panchayat((cursor.getString(cursor.getColumnIndex("panchayat_id"))));
                chngkhesraInfo.set_phase1_panchayat_nm((cursor.getString(cursor.getColumnIndex("panchayat_nm"))));
                chngkhesraInfo.set_phase1_dist((cursor.getString(cursor.getColumnIndex("dist_nm"))));
                chngkhesraInfo.set_phase1_block((cursor.getString(cursor.getColumnIndex("blk_nm"))));
                chngkhesraInfo.set_phase1_alloted_khesra_no((cursor.getString(cursor.getColumnIndex("alloted_khesra"))));
                chngkhesraInfo.set_chng_khesra_no((cursor.getString(cursor.getColumnIndex("chng_khesra_id"))));
                chngkhesraInfo.set_chng_khesra_no_nm((cursor.getString(cursor.getColumnIndex("chng_khesra_nm"))));
                chngkhesraInfo.set_phase1_final_khesra_no((cursor.getString(cursor.getColumnIndex("final_khesra"))));
                chngkhesraInfo.setNazriNkasha((cursor.getString(cursor.getColumnIndex("img_naksha"))));
                chngkhesraInfo.setFieldImg((cursor.getString(cursor.getColumnIndex("img_field"))));
                chngkhesraInfo.setFinalSelectedField((cursor.getString(cursor.getColumnIndex("FinalSelectedImg"))));
                chngkhesraInfo.set_phase1_highest_plot_no((cursor.getString(cursor.getColumnIndex("plot_no"))));
                chngkhesraInfo.set_phase1_Entry_date((cursor.getString(cursor.getColumnIndex("Entry_date"))));
                chngkhesraInfo.set_revenue_village((cursor.getString(cursor.getColumnIndex("RevenueVilage"))));

                chngkhesraInfo.set_phase1_lat((cursor.getString(cursor.getColumnIndex("Lat_field"))));
                chngkhesraInfo.set_phase1_lat1((cursor.getString(cursor.getColumnIndex("Lat_naksha"))));
                chngkhesraInfo.set_phase1_lat2((cursor.getString(cursor.getColumnIndex("Lat_fieldfinal"))));
                chngkhesraInfo.set_phase1_longi((cursor.getString(cursor.getColumnIndex("Long_field"))));
                chngkhesraInfo.set_phase1_longi1((cursor.getString(cursor.getColumnIndex("Long_naksha"))));
                chngkhesraInfo.set_phase1_longi2((cursor.getString(cursor.getColumnIndex("Long_filedFinal"))));
                chngkhesraInfo.set_Farmer_Name((cursor.getString(cursor.getColumnIndex("FarmerName"))));
                chngkhesraInfo.set_chng_Remarks((cursor.getString(cursor.getColumnIndex("chngRemarks"))));
                chngkhesraInfo.set_phase1_flag((cursor.getString(cursor.getColumnIndex("Flag"))));
                chngkhesraInfo.set_tentative_cce_date((cursor.getString(cursor.getColumnIndex("CceTentativedate"))));
                chngkhesraInfo.set_Type_List_Id((cursor.getString(cursor.getColumnIndex("Type_id"))));
                chngkhesraInfo.set_Type_List_NM((cursor.getString(cursor.getColumnIndex("Type_name"))));

                khesraInfos.add(chngkhesraInfo);

            }

            this.getReadableDatabase().close();
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return khesraInfos;
    }


    public ArrayList<NewLandKhesraInfo> getAllEntryDetailPhase1(String Userid) {
        ArrayList<NewLandKhesraInfo> basicdetail = new ArrayList<NewLandKhesraInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {Userid};

            Cursor cursor = sqLiteDatabase.rawQuery("select * From ChngKhesraDetails where Lat_fieldfinal IS NOT NULL AND User_Id=? ORDER BY Id  DESC", args);
            //Cursor cursor=sqLiteDatabase.rawQuery("select * From ChngKhesraDetails where  User_Id=? ORDER BY Id  DESC",args);
            int x = cursor.getCount();

            while (cursor.moveToNext()) {
                NewLandKhesraInfo basicInfo = new NewLandKhesraInfo();
                basicInfo.set_phase1_id((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.set_phase1_userid((cursor.getString(cursor.getColumnIndex("User_Id"))));
                basicInfo.set_phse1_agri_yr((cursor.getString(cursor.getColumnIndex("argi_year"))));
                basicInfo.set_phase1_season((cursor.getString(cursor.getColumnIndex("season_id"))));
                basicInfo.set_phase1_season_nm((cursor.getString(cursor.getColumnIndex("season_name"))));
                basicInfo.set_phase1_croptype((cursor.getString(cursor.getColumnIndex("croptype_id"))));
                basicInfo.set_phase1_crop_name((cursor.getString(cursor.getColumnIndex("croptype_name"))));
                basicInfo.set_phase1_dist((cursor.getString(cursor.getColumnIndex("dist_code"))));
                basicInfo.set_phase1_block((cursor.getString(cursor.getColumnIndex("blk_code"))));
                basicInfo.set_phase1_panchayat((cursor.getString(cursor.getColumnIndex("panchayat_id"))));
                basicInfo.set_phase1_panchayat_nm((cursor.getString(cursor.getColumnIndex("panchayat_nm"))));
                basicInfo.set_phase1_highest_plot_no((cursor.getString(cursor.getColumnIndex("plot_no"))));
                basicInfo.set_phase1_alloted_khesra_no((cursor.getString(cursor.getColumnIndex("alloted_khesra"))));
                basicInfo.set_chng_khesra_no((cursor.getString(cursor.getColumnIndex("chng_khesra_id"))));
                basicInfo.set_phase1_final_khesra_no((cursor.getString(cursor.getColumnIndex("final_khesra"))));
                basicInfo.setFieldImg((cursor.getString(cursor.getColumnIndex("img_field"))));
                basicInfo.setNazriNkasha((cursor.getString(cursor.getColumnIndex("img_naksha"))));
                basicInfo.setFinalSelectedField((cursor.getString(cursor.getColumnIndex("FinalSelectedImg"))));
                basicInfo.set_phase1_Entry_date((cursor.getString(cursor.getColumnIndex("Entry_date"))));
                basicInfo.set_phase1_lat((cursor.getString(cursor.getColumnIndex("Lat_field"))));
                basicInfo.set_phase1_longi((cursor.getString(cursor.getColumnIndex("Long_field"))));
                basicInfo.set_phase1_lat1((cursor.getString(cursor.getColumnIndex("Lat_naksha"))));
                basicInfo.set_phase1_longi1((cursor.getString(cursor.getColumnIndex("Long_naksha"))));
                basicInfo.set_phase1_lat2((cursor.getString(cursor.getColumnIndex("Lat_fieldfinal"))));
                basicInfo.set_phase1_longi2((cursor.getString(cursor.getColumnIndex("Long_filedFinal"))));
                basicInfo.set_Farmer_Name((cursor.getString(cursor.getColumnIndex("FarmerName"))));

                basicInfo.set_chng_Remarks((cursor.getString(cursor.getColumnIndex("chngRemarks"))));
                basicInfo.set_revenue_village((cursor.getString(cursor.getColumnIndex("RevenueVilage"))));
                basicInfo.set_tentative_cce_date((cursor.getString(cursor.getColumnIndex("CceTentativedate"))));
                basicInfo.set_Type_List_Id((cursor.getString(cursor.getColumnIndex("Type_id"))));
                basicInfo.set_Type_List_NM((cursor.getString(cursor.getColumnIndex("Type_name"))));


                basicdetail.add(basicInfo);

            }
            this.getReadableDatabase().close();
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return basicdetail;
    }


    public long updateNewEntryKhesradetails(KhesraEntryActivity newEntryActivity, NewLandKhesraInfo result) {

        long c = -1;
        try {
            DataBaseHelper placeData = new DataBaseHelper(newEntryActivity);
            SQLiteDatabase db = placeData.getWritableDatabase();
            ContentValues values = new ContentValues();

            // values.put("User_Id", result.getUser_ID());
            values.put("Id", result.get_phase1_id());
            values.put("User_Id", result.get_phase1_userid());
            values.put("argi_year", result.get_phse1_agri_yr());
            values.put("agri_ye_nm", result.get_phse1_agri_yr_nm());
            values.put("season_id", result.get_phase1_season());
            values.put("season_name", result.get_phase1_season_nm());
            //values.put("season_name", result.getphase1());
            values.put("croptype_id", result.get_phase1_croptype());
            values.put("croptype_name", result.get_phase1_crop_name());
            values.put("dist_code", result.get_phase1_dist());
            values.put("blk_code", result.get_phase1_block());
            values.put("panchayat_id", result.get_phase1_panchayat());
            values.put("panchayat_nm", result.get_phase1_panchayat_nm());

            values.put("plot_no", result.get_phase1_highest_plot_no());
            values.put("alloted_khesra", result.get_phase1_alloted_khesra_no());
            values.put("chng_khesra_id", result.get_chng_khesra_no());
            values.put("chng_khesra_nm", result.get_chng_khesra_no_nm());
            // values.put("chng_khesra_nm", result.get_reg_no());

            values.put("final_khesra", result.get_phase1_final_khesra_no());
            values.put("img_naksha", result.getNazriNkasha());
            values.put("img_field", result.getFieldImg());
            values.put("FinalSelectedImg", result.getFinalSelectedField());
//            values.put("Lat_naksha", result.get_phase1_lat());
//            values.put("Long_naksha", result.get_phase1_longi());
//            values.put("Lat_field", result.get_phase1_lat1());
//            values.put("Long_field", result.get_phase1_longi1());
//            values.put("Lat_fieldfinal", result.get_phase1_lat2());
//            values.put("Long_filedFinal", result.get_phase1_longi2());

            values.put("Lat_naksha", result.get_phase1_lat1());
            values.put("Long_naksha", result.get_phase1_longi1());
            values.put("Lat_field", result.get_phase1_lat());
            values.put("Long_field", result.get_phase1_longi());
            values.put("Lat_fieldfinal", result.get_phase1_lat2());
            values.put("Long_filedFinal", result.get_phase1_longi2());


            values.put("FarmerName", result.get_Farmer_Name());
            values.put("chngRemarks", result.get_chng_Remarks());
            values.put("RevenueVilage", result.get_revenue_village());
            values.put("CceTentativedate", result.get_tentative_cce_date());
            values.put("Type_id", result.get_Type_List_Id());
            values.put("Type_name", result.get_Type_List_NM());
            values.put("Flag", "I");

            String[] whereArgs = new String[]{(String.valueOf(result.get_phase1_id()))};
            c = db.update("ChngKhesraDetails", values, "Id=?", whereArgs);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }


    public ArrayList<NewLandKhesraInfo> getAllEntryDetailsinglePhase1(String Userid, String rowid) {
        ArrayList<NewLandKhesraInfo> basicdetail = new ArrayList<NewLandKhesraInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {Userid, rowid};
            Cursor cursor = sqLiteDatabase.rawQuery("select * From ChngKhesraDetails where User_Id=? AND Id=?", args);
            //Cursor cursor=sqLiteDatabase.rawQuery("select Id,Latitude,Agriculture_year,Season,Crop,District,Block,Panchayat,Name_of_selected_village,Highest_plot_no_of_Panchayat,Selected_Survey_No_Khesra,Farmer_Name,Operational_Size_holding,Area_of_crop,System_of_cutivation,Type_Of_crop_varities,Irrigation_source,Type_of_manure,Quantity_of_used_manure,Source_of_seed,Quantity_of_used_seed,Weather_condition_during,Extend_of_damage,Remarks,EntryBy,Random_no_allotted_DCO,Order_Of_Experiment_according_to_random_No,Shape_of_Cce_area,Length_of_field,Breadth_of_field,Type_of_land,Date_of_cutting,Green_weight,Dry_weight,No_of_baal_Maize,Weight_of_baal_Maize,Green_weight_of_Dana,Dry_weight_of_dana,remarks1,Inspection_done,Quantity_seed_in,UnitOperationalSize,UnitAreaCoverage,Longitude,EntryDate,Type_of_land_Name,Weather_condition_during_name,sourceofseed_name,tymanure_name,systemcultivation_name,varitiescrop_name,CropName,Sub_Varities_Of_crop From BasicDetails where EntryBy=? AND Id=?",args);

            int x = cursor.getCount();

            NewLandKhesraInfo basicInfo = new NewLandKhesraInfo();
            while (cursor.moveToNext()) {
                basicInfo.set_phase1_id((cursor.getString(cursor.getColumnIndex("Id"))));
                basicInfo.set_phase1_userid((cursor.getString(cursor.getColumnIndex("User_Id"))));
                basicInfo.set_phse1_agri_yr((cursor.getString(cursor.getColumnIndex("argi_year"))));
                basicInfo.set_phase1_season((cursor.getString(cursor.getColumnIndex("season_id"))));
                basicInfo.set_phase1_season_nm((cursor.getString(cursor.getColumnIndex("season_name"))));
                basicInfo.set_phase1_croptype((cursor.getString(cursor.getColumnIndex("croptype_id"))));
                basicInfo.set_phase1_crop_name((cursor.getString(cursor.getColumnIndex("croptype_name"))));
                basicInfo.set_phase1_dist((cursor.getString(cursor.getColumnIndex("dist_code"))));
                basicInfo.set_phase1_block((cursor.getString(cursor.getColumnIndex("blk_code"))));
                basicInfo.set_phase1_panchayat((cursor.getString(cursor.getColumnIndex("panchayat_id"))));
                basicInfo.set_phase1_panchayat_nm((cursor.getString(cursor.getColumnIndex("panchayat_nm"))));
                basicInfo.set_phase1_highest_plot_no((cursor.getString(cursor.getColumnIndex("plot_no"))));
                basicInfo.set_phase1_alloted_khesra_no((cursor.getString(cursor.getColumnIndex("alloted_khesra"))));
                basicInfo.set_chng_khesra_no((cursor.getString(cursor.getColumnIndex("chng_khesra_id"))));
                basicInfo.set_phase1_final_khesra_no((cursor.getString(cursor.getColumnIndex("final_khesra"))));
                basicInfo.setFieldImg((cursor.getString(cursor.getColumnIndex("img_field"))));
                basicInfo.setNazriNkasha((cursor.getString(cursor.getColumnIndex("img_naksha"))));
                basicInfo.setFinalSelectedField((cursor.getString(cursor.getColumnIndex("FinalSelectedImg"))));
                basicInfo.set_phase1_Entry_date((cursor.getString(cursor.getColumnIndex("Entry_date"))));
                basicInfo.set_phase1_lat((cursor.getString(cursor.getColumnIndex("Lat_field"))));
                basicInfo.set_phase1_longi((cursor.getString(cursor.getColumnIndex("Long_field"))));
                basicInfo.set_phase1_lat1((cursor.getString(cursor.getColumnIndex("Lat_naksha"))));
                basicInfo.set_phase1_longi1((cursor.getString(cursor.getColumnIndex("Long_naksha"))));
                basicInfo.set_phase1_lat2((cursor.getString(cursor.getColumnIndex("Lat_fieldfinal"))));
                basicInfo.set_phase1_longi2((cursor.getString(cursor.getColumnIndex("Long_filedFinal"))));
                basicInfo.set_Farmer_Name((cursor.getString(cursor.getColumnIndex("FarmerName"))));
                basicInfo.set_chng_Remarks((cursor.getString(cursor.getColumnIndex("chngRemarks"))));
                basicInfo.set_revenue_village((cursor.getString(cursor.getColumnIndex("RevenueVilage"))));
                basicInfo.set_tentative_cce_date((cursor.getString(cursor.getColumnIndex("CceTentativedate"))));
                basicInfo.set_Type_List_Id((cursor.getString(cursor.getColumnIndex("Type_id"))));
                basicInfo.set_Type_List_NM((cursor.getString(cursor.getColumnIndex("Type_name"))));


                basicdetail.add(basicInfo);


            }
            this.getReadableDatabase().close();
            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return basicdetail;
    }

    public long Datainsert(ArrayList<NewLandKhesraInfoOnline> result, String userid, String Panchayatid) {
        long c = -1;
        String imgfield = "";
        String imgfinal = "";
        String naksha = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            for (NewLandKhesraInfoOnline state : result) {

                ContentValues values = new ContentValues();
                values.put("argi_year", state.getPhse1_agri_yr());
                values.put("User_Id", userid);
                values.put("season_id", state.getPhase1_season());
                values.put("croptype_id", state.getPhase1_croptype());
                //values.put("dist_nm", state.getPhase1_dist());
                values.put("dist_code", state.getPhase1_dist());
                values.put("blk_code", state.getPhase1_block());
                // values.put("blk_nm", panchayatcode.trim());
                values.put("panchayat_id", Panchayatid);
                values.put("plot_no", state.getPhase1_highest_plot_no());
                values.put("alloted_khesra", state.getPhase1_alloted_khesra_no());

                values.put("final_khesra", state.getPhase1_final_khesra_no());

                //if (state.getPhase1_alloted_khesra_no() == state.getPhase1_final_khesra_no()) {
                if (state.getPhase1_alloted_khesra_no().equals(state.getPhase1_final_khesra_no())) {
                    values.put("chng_khesra_id", "2");
                } else {
                    values.put("chng_khesra_id", "1");
                }

                if (!state.getNazriNkasha().equalsIgnoreCase(null)) {
                    naksha = convertUrlToBase64(state.getNazriNkasha());
                }
                if (!state.getFieldImg().equalsIgnoreCase(null)) {
                    imgfield = convertUrlToBase64(state.getFieldImg());
                }

                if (!state.getFinalSelectedField().equalsIgnoreCase(null)) {
                    imgfinal = convertUrlToBase64(state.getFinalSelectedField());
                }

                values.put("img_naksha", naksha);
                values.put("img_field", imgfield);
                values.put("Lat_naksha", state.getPhase1_lat1());
                values.put("Long_naksha", state.getPhase1_longi1());
                values.put("Lat_field", state.getPhase1_lat());
                values.put("Long_field", state.getPhase1_longi());
                values.put("Entry_date", state.getPhase1_Entry_date());
                values.put("FinalSelectedImg", imgfinal);
                values.put("Lat_fieldfinal", state.getPhase1_lat2());
                values.put("Long_filedFinal", state.getPhase1_longi2());
                values.put("Long_filedFinal", state.getPhase1_longi2());
                values.put("FarmerName", state.getPhase1_farmerName());
                values.put("chngRemarks", state.getPhase1_chng_khesra_reason());
                values.put("RevenueVilage", state.getPhase1_village());
                values.put("CceTentativedate", state.getPhase1_tentative_cce_date());
                //S for server data
                values.put("Flag", "S");

                String[] whereArgs = new String[]{state.getPhase1_final_khesra_no()};
                c = db.update("ChngKhesraDetails", values, "final_khesra=?", whereArgs);
                if (c <= 0) {

                    c = db.insert("ChngKhesraDetails", null, values);

                }

            }
            db.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return c;
    }


    public String convertUrlToBase64(String url) {
        URL newurl;
        Bitmap bitmap;
        String base64 = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }


    public long setKhesraLocal(ArrayList<KhesraNo_List> list, String userid) {

        long c = -1;
        ArrayList<KhesraNo_List> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.delete("CropType",null,null);
        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("AgriYr", info.get(i).getAgriyr());
                    values.put("KhesraNo", info.get(i).getKhesraNm());
                    values.put("season", info.get(i).getKhesra_season());
                    values.put("croptype", info.get(i).getKhesra_crop());
                    values.put("userid", userid);
                    values.put("FarmerName", info.get(i).getFarmerName());
                    values.put("pan_id", info.get(i).getPanid());
                    values.put("villageName", info.get(i).getVillage_Name());
                    values.put("highestPlot", info.get(i).getHighest_PlotNo());
                    String[] whereArgs = new String[]{info.get(i).getKhesraId()};
                    c = db.insert("KhesraList", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;

    }


    public String getFarmerName(String userid, String Season, String crop, String pancode, String khesra) {

        String fName = "n";
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] param = {userid, Season, crop, pancode, khesra};
            Cursor cur = db.rawQuery("SELECT FarmerName from KhesraList where userid =? AND season=? AND croptype=? AND pan_id = ? AND KhesraNo = ?", param);
            int x = cur.getCount();
            while (cur.moveToNext()) {

                fName = cur.getString(cur.getColumnIndex("FarmerName"));
            }
            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return fName;

    }

    public Cursor getUserDetails(String user_id, String season_id, String cropid, String panchayt, String KhesraNo) {

        Cursor cur = null;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] param = {user_id, season_id, cropid, panchayt, KhesraNo};
            cur = db.rawQuery("SELECT * from KhesraList where userid =? AND season=? AND croptype=? AND pan_id = ? AND KhesraNo = ?", param);
            int x = cur.getCount();

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return cur;

    }

    public Cursor getPrevLatLong(String user_id, String row_id) {

        Cursor cur = null;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] param = {user_id, row_id};
            cur = db.rawQuery("SELECT LatForRadius,LongForRadius from BasicDetails where EntryBy =? AND Id=?", param);
            int x = cur.getCount();

            Log.e("Cur_COUNT", "" + x);
            db.close();

        } catch (Exception e) {
            Log.e("Cur_COUNT", "excep");
            e.printStackTrace();
            // TODO: handle exception

        }
        return cur;

    }


    public int gettotalCount(String userid) {

        String[] user_id = {userid};
        String countQuery = "SELECT  * FROM ChngKhesraDetails where User_Id=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, user_id);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


}
