package in.nic.bih.cropcutting.activity.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;

public class PreHomeActivity extends AppCompatActivity {
    Button btn_phase1, btn_phase2;
    TextView txtVersion_1;
    String version;
    ImageView logout_phse1;
    SQLiteDatabase db;
    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_home);

        helper = new DataBaseHelper(this);

        btn_phase1 = findViewById(R.id.btn_phase1);
        btn_phase2 = findViewById(R.id.btn_phase2);
        txtVersion_1 = findViewById(R.id.txtVersion_1);
        logout_phse1 = findViewById(R.id.logout_phse1);

        CREATETABLEIFNOTEXIST();
        CREATETABLEIFNOTEXIST1();
        CREATETABLEIFNOTEXIST2();
        CREATETypeLisTablIfNotExist();
        ModifyTable();

        db = helper.getWritableDatabase();
        String count = "SELECT count(*) FROM CheckList";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            Log.e("Table Checklist", "Row already exists");
        } else {
            INSERTVALUESIFTABLEEXIST();
            INSERTVALUESIFTABLEEXIST1();

        }


        getVersion();
        btn_phase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreHomeActivity.this, Phase1HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });

        btn_phase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreHomeActivity.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });


        logout_phse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(PreHomeActivity.this);
                builder.setIcon(R.drawable.log2);

                builder.setTitle("Log Out");
                // Ask the final question
                builder.setMessage("Are you sure want to Logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        logout_phasee1();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


            }
        });
    }

    @Override
    public void onBackPressed() {
        showCustomDialog();
    }


    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);
        Button buttonok = (Button) dialogView.findViewById(R.id.buttonOk);
        Button buttcancela = (Button) dialogView.findViewById(R.id.buttoncancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        Dialog d = new Dialog(this);
        d.setCanceledOnTouchOutside(false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttcancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialog.setCancelable(false);
            }
        });
        if (!PreHomeActivity.this.isFinishing()) {
            alertDialog.show();
        }
    }

    private void getVersion() {

        try {

            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv_phase1 = (TextView) findViewById(R.id.txtVersion_phase1);
            txtVersion_1.setText("App Version  " + version);


        } catch (PackageManager.NameNotFoundException e) {

        }

    }

    private void logout_phasee1() {

        final EditText userPass = (EditText) findViewById(R.id.et_password_login);
        final AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USERID", "").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PASWORD", "").commit();

        Intent intent = new Intent(PreHomeActivity.this, Login.class);
        startActivity(intent);
        finish();

    }


    public void CREATETABLEIFNOTEXIST() {
        db = helper.getWritableDatabase();
        try {
            //db.execSQL("CREATE TABLE IF NOT EXISTS ChngKhesraDetails (Id INTEGER PRIMARY KEY AUTOINCREMENT,argi_year TEXT,agri_ye_nm TEXT,User_Id TEXT,season_id TEXT, season_name TEXT,croptype_id TEXT,croptype_name TEXT,dist_nm TEXT,dist_code TEXT,blk_code TEXT,blk_nm TEXT,panchayat_id TEXT,panchayat_nm TEXT,plot_no TEXT,alloted_khesra TEXT,chng_khesra_id TEXT,chng_khesra_nm TEXT,final_khesra TEXT,img_naksha TEXT,img_field TEXT,Lat_naksha TEXT,Long_naksha TEXT,Lat_field TEXT,Long_field TEXT,Entry_date TEXT,FinalSelectedImg TEXT,Lat_fieldfinal TEXT,Long_filedFinal TEXT,Flag TEXT )");
            db.execSQL("CREATE TABLE IF NOT EXISTS ChngKhesraDetails ( Id INTEGER PRIMARY KEY AUTOINCREMENT, argi_year TEXT, agri_ye_nm TEXT, User_Id TEXT, season_id TEXT, season_name TEXT, croptype_id TEXT, croptype_name TEXT, dist_nm TEXT, dist_code TEXT, blk_code TEXT, blk_nm TEXT, panchayat_id TEXT, panchayat_nm TEXT, plot_no TEXT, alloted_khesra TEXT, chng_khesra_id TEXT, chng_khesra_nm TEXT, final_khesra TEXT, img_naksha TEXT, img_field TEXT, Lat_naksha TEXT, Long_naksha TEXT, Lat_field TEXT, Long_field TEXT, Entry_date TEXT, FinalSelectedImg TEXT, Lat_fieldfinal TEXT, Long_filedFinal TEXT, Flag TEXT, FarmerName TEXT, chngRemarks TEXT, RevenueVilage TEXT, CceTentativedate TEXT,Field35 TEXT,Field36 TEXT,Field37 TEXT,Field38 TEXT,Field39 TEXT,Field40 TEXT )");
            helper.getWritableDatabase().close();
            Log.e("CREATE SUCCESS ", "ChngKhesraDetails");

            helper.getWritableDatabase().close();
            //db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CREATE Failed ", "ChngKhesraDetails");
        }
    }


    public void CREATETABLEIFNOTEXIST1() {

        db = helper.getWritableDatabase();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS CheckList (Chk_Id TEXT, Chk_Name TEXT)");
            helper.getWritableDatabase().close();
            Log.e("CREATE SUCCESS ", "CheckList");
            helper.getWritableDatabase().close();
            //db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CREATE Failed ", "CheckList");
        }
    }


    public void CREATETABLEIFNOTEXIST2() {

        db = helper.getWritableDatabase();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS KhesraList (AgriYr TEXT, KhesraNo TEXT, season TEXT, croptype TEXT, userid TEXT, pan_id TEXT, FarmerName TEXT, villageName TEXT, highestPlot TEXT )");
            helper.getWritableDatabase().close();
            Log.e("CREATE SUCCESS ", "KhesraList");
            helper.getWritableDatabase().close();
            //db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CREATE Failed ", "KhesraList");
        }
    }

    public void CREATETypeLisTablIfNotExist() {

        db = helper.getWritableDatabase();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TypeList (id TEXT, typeName TEXT, crop_id TEXT)");
            helper.getWritableDatabase().close();
            Log.e("CREATE SUCCESS ", "TypeList");
            helper.getWritableDatabase().close();
            //db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CREATE Failed ", "TypeList");
        }
    }

    public void INSERTVALUESIFTABLEEXIST() {

        db = helper.getWritableDatabase();
        try {
            //  db.execSQL("INSERT or replace INTO CheckList (Chk_Id,Chk_Name) VALUES('1','Yes')");
            db.execSQL("INSERT or REPLACE INTO CheckList (Chk_Id,Chk_Name) VALUES(1,'Yes')");

            helper.getWritableDatabase().close();
            Log.e("INSERT SUCCESS ", "CheckList");
            helper.getWritableDatabase().close();
            //db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("INSERT Failed ", "CheckList");
        }
    }

    public void INSERTVALUESIFTABLEEXIST1() {

        db = helper.getWritableDatabase();
        try {
            //db.execSQL("INSERT or replace INTO CheckList (Chk_Id,Chk_Name) VALUES('2','No')");
            db.execSQL("insert or replace INTO CheckList (Chk_Id,Chk_Name) VALUES(2,'No')");
            helper.getWritableDatabase().close();
            Log.e("INSERT SUCCESS ", "CheckList");
            helper.getWritableDatabase().close();
            //db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("INSERT Failed ", "CheckList");
        }
    }


    public void ModifyTable() {


        if (isColumnExists("ChngKhesraDetails", "argi_year") == false) {
            AlterTable("ChngKhesraDetails", "argi_year");
        }
        if (isColumnExists("ChngKhesraDetails", "agri_ye_nm") == false) {
            AlterTable("ChngKhesraDetails", "agri_ye_nm");
        }
        if (isColumnExists("ChngKhesraDetails", "User_Id") == false) {
            AlterTable("ChngKhesraDetails", "User_Id");
        }
        if (isColumnExists("ChngKhesraDetails", "season_id") == false) {
            AlterTable("ChngKhesraDetails", "season_id");
        }
        if (isColumnExists("ChngKhesraDetails", "season_name") == false) {
            AlterTable("ChngKhesraDetails", "season_name");
        }
        if (isColumnExists("ChngKhesraDetails", "croptype_id") == false) {
            AlterTable("ChngKhesraDetails", "croptype_id");
        }
        if (isColumnExists("ChngKhesraDetails", "croptype_name") == false) {
            AlterTable("ChngKhesraDetails", "croptype_name");
        }

        if (isColumnExists("ChngKhesraDetails", "dist_nm") == false) {
            AlterTable("ChngKhesraDetails", "dist_nm");
        }
        if (isColumnExists("ChngKhesraDetails", "dist_code") == false) {
            AlterTable("ChngKhesraDetails", "dist_code");
        }
        if (isColumnExists("ChngKhesraDetails", "blk_code") == false) {
            AlterTable("ChngKhesraDetails", "blk_code");
        }
        if (isColumnExists("ChngKhesraDetails", "blk_nm") == false) {
            AlterTable("ChngKhesraDetails", "blk_nm");
        }
        if (isColumnExists("ChngKhesraDetails", "panchayat_id") == false) {
            AlterTable("ChngKhesraDetails", "panchayat_id");
        }
        if (isColumnExists("ChngKhesraDetails", "panchayat_nm") == false) {
            AlterTable("ChngKhesraDetails", "panchayat_nm");
        }
        if (isColumnExists("ChngKhesraDetails", "plot_no") == false) {
            AlterTable("ChngKhesraDetails", "plot_no");
        }
        if (isColumnExists("ChngKhesraDetails", "alloted_khesra") == false) {
            AlterTable("ChngKhesraDetails", "alloted_khesra");
        }
        if (isColumnExists("ChngKhesraDetails", "chng_khesra_id") == false) {
            AlterTable("ChngKhesraDetails", "chng_khesra_id");
        }
        if (isColumnExists("ChngKhesraDetails", "chng_khesra_nm") == false) {
            AlterTable("ChngKhesraDetails", "chng_khesra_nm");
        }
        if (isColumnExists("ChngKhesraDetails", "final_khesra") == false) {
            AlterTable("ChngKhesraDetails", "final_khesra");
        }
        if (isColumnExists("ChngKhesraDetails", "img_naksha") == false) {
            AlterTable("ChngKhesraDetails", "img_naksha");
        }
        if (isColumnExists("ChngKhesraDetails", "img_field") == false) {
            AlterTable("ChngKhesraDetails", "img_field");
        }
        if (isColumnExists("ChngKhesraDetails", "Lat_naksha") == false) {
            AlterTable("ChngKhesraDetails", "Lat_naksha");
        }
        if (isColumnExists("ChngKhesraDetails", "Long_naksha") == false) {
            AlterTable("ChngKhesraDetails", "Long_naksha");
        }
        if (isColumnExists("ChngKhesraDetails", "Lat_field") == false) {
            AlterTable("ChngKhesraDetails", "Lat_field");
        }
        if (isColumnExists("ChngKhesraDetails", "Long_field") == false) {
            AlterTable("ChngKhesraDetails", "Long_field");
        }
        if (isColumnExists("ChngKhesraDetails", "Entry_date") == false) {
            AlterTable("ChngKhesraDetails", "Entry_date");
        }
        if (isColumnExists("ChngKhesraDetails", "FinalSelectedImg") == false) {
            AlterTable("ChngKhesraDetails", "FinalSelectedImg");
        }
        if (isColumnExists("ChngKhesraDetails", "Lat_fieldfinal") == false) {
            AlterTable("ChngKhesraDetails", "Lat_fieldfinal");
        }
        if (isColumnExists("ChngKhesraDetails", "Long_filedFinal") == false) {
            AlterTable("ChngKhesraDetails", "Long_filedFinal");
        }
        if (isColumnExists("ChngKhesraDetails", "Flag") == false) {
            AlterTable("ChngKhesraDetails", "Flag");
        }
        if (isColumnExists("ChngKhesraDetails", "FarmerName") == false) {
            AlterTable("ChngKhesraDetails", "FarmerName");
        }
        if (isColumnExists("ChngKhesraDetails", "chngRemarks") == false) {
            AlterTable("ChngKhesraDetails", "chngRemarks");
        }
        if (isColumnExists("ChngKhesraDetails", "RevenueVilage") == false) {
            AlterTable("ChngKhesraDetails", "RevenueVilage");
        }
        if (isColumnExists("ChngKhesraDetails", "CceTentativedate") == false) {
            AlterTable("ChngKhesraDetails", "CceTentativedate");
        }



        if (isColumnExists("ChngKhesraDetails", "Field35") == false) {
            AlterTable("ChngKhesraDetails", "Field35");
        }
        if (isColumnExists("ChngKhesraDetails", "Field36") == false) {
            AlterTable("ChngKhesraDetails", "Field36");
        }
        if (isColumnExists("ChngKhesraDetails", "Field37") == false) {
            AlterTable("ChngKhesraDetails", "Field37");
        }
        if (isColumnExists("ChngKhesraDetails", "Field38") == false) {
            AlterTable("ChngKhesraDetails", "Field38");
        }
        if (isColumnExists("ChngKhesraDetails", "Field39") == false) {
            AlterTable("ChngKhesraDetails", "Field39");
        }
        if (isColumnExists("ChngKhesraDetails", "Field40") == false) {
            AlterTable("ChngKhesraDetails", "Field40");
        }

        if (isColumnExists("ChngKhesraDetails", "Type_id") == false) {
            AlterTable("ChngKhesraDetails", "Type_id");
        }

        if (isColumnExists("ChngKhesraDetails", "Type_name") == false) {
            AlterTable("ChngKhesraDetails", "Type_name");
        }

        if (isColumnExists("KhesraList", "AgriYr") == false) {
            AlterTable("KhesraList", "AgriYr");
        }
        if (isColumnExists("KhesraList", "KhesraNo") == false) {
            AlterTable("KhesraList", "KhesraNo");
        }
        if (isColumnExists("KhesraList", "season") == false) {
            AlterTable("KhesraList", "season");
        }
        if (isColumnExists("KhesraList", "croptype") == false) {
            AlterTable("KhesraList", "croptype");
        }
        if (isColumnExists("KhesraList", "userid") == false) {
            AlterTable("KhesraList", "userid");
        }
        if (isColumnExists("KhesraList", "pan_id") == false) {
            AlterTable("KhesraList", "pan_id");
        }

        if (isColumnExists("KhesraList", "FarmerName") == false) {
            AlterTable("KhesraList", "FarmerName");
        }
        if (isColumnExists("KhesraList", "villageName") == false) {
            AlterTable("KhesraList", "villageName");
        }
        if (isColumnExists("KhesraList", "highestPlot") == false) {
            AlterTable("KhesraList", "highestPlot");
        }

        if (isColumnExists("BasicDetails", "Type_id") == false) {
            AlterTable("BasicDetails", "Type_id");
        }
        if (isColumnExists("BasicDetails", "Type_name") == false) {
            AlterTable("BasicDetails", "Type_name");
        }

        if (isColumnExists("BasicDetails", "Pod_wt_aftr_plucking") == false) {
            AlterTable("BasicDetails", "Pod_wt_aftr_plucking");
        }
        if (isColumnExists("BasicDetails", "Pod_Wt_after_thresing") == false) {
            AlterTable("BasicDetails", "Pod_Wt_after_thresing");
        }
        if (isColumnExists("BasicDetails", "Baal_wt_aftr_plucking") == false) {
            AlterTable("BasicDetails", "Baal_wt_aftr_plucking");
        }
        if (isColumnExists("BasicDetails", "Baal_wt_aftr_threshing") == false) {
            AlterTable("BasicDetails", "Baal_wt_aftr_threshing");
        }
        if (isColumnExists("BasicDetails", "Total_bundles") == false) {
            AlterTable("BasicDetails", "Total_bundles");
        }
        if (isColumnExists("BasicDetails", "bundle1") == false) {
            AlterTable("BasicDetails", "bundle1");
        }
        if (isColumnExists("BasicDetails", "bundle2") == false) {
            AlterTable("BasicDetails", "bundle2");
        }
        if (isColumnExists("BasicDetails", "bundle3") == false) {
            AlterTable("BasicDetails", "bundle3");
        }
        if (isColumnExists("BasicDetails", "bundle4") == false) {
            AlterTable("BasicDetails", "bundle4");
        }
        if (isColumnExists("BasicDetails", "bundle5") == false) {
            AlterTable("BasicDetails", "bundle5");
        }
        if (isColumnExists("BasicDetails", "bundle6") == false) {
            AlterTable("BasicDetails", "bundle6");
        }
        if (isColumnExists("BasicDetails", "bundle7") == false) {
            AlterTable("BasicDetails", "bundle7");
        }
        if (isColumnExists("BasicDetails", "bundle8") == false) {
            AlterTable("BasicDetails", "bundle8");
        }
        if (isColumnExists("BasicDetails", "total_bundle_weight") == false) {
            AlterTable("BasicDetails", "total_bundle_weight");
        }
        if (isColumnExists("BasicDetails", "dry_fiber_weight") == false) {
            AlterTable("BasicDetails", "dry_fiber_weight");
        }
    }

    public boolean isColumnExists(String table, String column) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        helper.getReadableDatabase().close();
        return false;
    }

    public void AlterTable(String tableName, String columnName) {
        try {
            db = helper.getWritableDatabase();
            db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT");
            Log.e("ALTER Done", tableName + "-" + columnName);
            helper.getWritableDatabase().close();
        } catch (Exception e) {
            Log.e("ALTER Failed", tableName + "-" + columnName);
        }
    }
//    @Override
//    public void onDestroy() {
//
//        super.onDestroy();
//      //  clearCache();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();


    }



    public void clearCache() {
        Log.d("TAG", "Clearing Cache.");
        File[] dir = getBaseContext().getCacheDir().listFiles();
        if(dir != null){
            for (File f : dir){
                f.delete();
                Log.d("TAG", "Clearing Cleared.");
            }
        }
    }

}
