package in.nic.bih.cropcutting.activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.Block_List;
import in.nic.bih.cropcutting.activity.Entity.District_list;
import in.nic.bih.cropcutting.activity.Entity.SignUp;

public class SignUpActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;

    EditText et_name, et_address, et_fname, et_mobile, et_password, et_confirm_password;
    Spinner spn_dist, spn_block;
    Button btn_signUp, btn_cancel;
    String st_et_name, st_et_address, st_et_fname, st_et_mobile, st_et_password, st_et_confirm_password;
    String st_spn_dist, st_spn_dist_code = "", st_spn_block, st_spn_block_code = "";


    ArrayList<District_list> distList = new ArrayList<District_list>();
    ArrayList<Block_List> BlkList = new ArrayList<Block_List>();

    ArrayList<String> districtNameArray;
    ArrayList<String> blkNameArray;

    static ArrayList<String> blockstlist;

    ArrayAdapter<String> districtadapter;
    ArrayAdapter<String> blockadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Initialization();

        dataBaseHelper = new DataBaseHelper(SignUpActivity.this);
        dataBaseHelper = new DataBaseHelper(this);

        try {
            dataBaseHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            dataBaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        new DISTRICTDATA().execute();

        spn_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_dist_code = distList.get(pos - 1).getDistt_code();
                    st_spn_dist = distList.get(pos - 1).getDistt_name();
                    BlkList = dataBaseHelper.getBlockLocal(st_spn_dist_code);
                    if (BlkList.size() <= 0) {
                        new BLOCKTDATA().execute();
                    } else {
                        setBlockData();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_block_code = BlkList.get(pos - 1).getBlk_Code();
                    st_spn_block = BlkList.get(pos - 1).getBlk_Name();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        btn_signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SignUpEntry();
                    }

                });

        btn_cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SignUpActivity.this, Login.class);
                        startActivity(intent);
                    }

                });
    }

    public void Initialization() {
        spn_dist = (Spinner) findViewById(R.id.spn_dist);
        spn_block = (Spinner) findViewById(R.id.spn_block);
        et_name = (EditText) findViewById(R.id.et_farmer_name);
        et_fname = (EditText) findViewById(R.id.et_father_husband);
        et_address = (EditText) findViewById(R.id.et_adress);
        et_mobile = (EditText) findViewById(R.id.et_mobile_number);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_conform_password);
        btn_signUp = (Button) findViewById(R.id.Button_signup);
        btn_cancel = (Button) findViewById(R.id.reg_cancel);

    }

    public void loadDistrictSpinner() {
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        distList = dataBaseHelper.getDistrictLocal();
        districtNameArray = new ArrayList<String>();
        districtNameArray.add("-select-");
        int i = 0;
        for (District_list district_list : distList) {
            districtNameArray.add(district_list.getDistt_name());
            i++;
        }
        districtadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, districtNameArray);
        districtadapter.setDropDownViewResource(R.layout.dropdowlist);
        spn_dist.setAdapter(districtadapter);


    }

    private void loadBlockData(ArrayList<Block_List> pList) {
        blockstlist = new ArrayList<String>();
        blockstlist.add("-Select Block-");
        for (int i = 0; i < pList.size(); i++) {
            blockstlist.add(pList.get(i).getBlk_Name());
        }
        blockadapter = new ArrayAdapter(this, R.layout.dropdowlist, blockstlist);
        spn_block.setAdapter(blockadapter);

    }

    public void setBlockData() {
        DataBaseHelper placeData = new DataBaseHelper(SignUpActivity.this);
        BlkList = placeData.getBlockLocal(st_spn_dist_code);
        if (BlkList.size() > 0)
            loadBlockData(BlkList);
    }


    public void SignUpEntry() {
        long c = 0;
        setValue();
        DataBaseHelper placeData = new DataBaseHelper(SignUpActivity.this);
        SignUp signUp = new SignUp();
        if (Validate()) {
            signUp.setDist_code(st_spn_dist_code);
            signUp.setBlock_code(st_spn_block_code);
            signUp.setName(st_et_name);
            signUp.setAddress(st_et_address);
            signUp.setFname(st_et_fname);
            signUp.setMobile(st_et_mobile);
            signUp.setPassword(st_et_password);
            signUp.setConfirm_password(st_et_confirm_password);

            new UPLOADDATA(signUp).execute();

        }

    }

    public void setValue() {
        st_et_name = et_name.getText().toString();
        st_et_address = et_address.getText().toString();
        st_et_fname = et_fname.getText().toString();
        st_et_mobile = et_mobile.getText().toString();
        st_et_password = et_password.getText().toString();
        st_et_confirm_password = et_confirm_password.getText().toString();

    }

    private boolean Validate() {
        View focusview = null;
        boolean validate = true;
        if (TextUtils.isEmpty(st_et_name)) {
            focusview = et_name;
            validate = false;
            et_name.setError("please enter name");

        }
        if (TextUtils.isEmpty(st_et_address)) {
            focusview = et_address;
            validate = false;
            et_address.setError("please enter address");

        }
        if (TextUtils.isEmpty(st_et_fname)) {
            focusview = et_fname;
            validate = false;
            et_fname.setError("please enter father/husband name");

        }
        if (TextUtils.isEmpty(st_et_mobile)) {
            focusview = et_mobile;
            validate = false;
            et_mobile.setError("please enter mobile number");

        } else if (st_et_mobile.length() != 10) {
            et_mobile.setError(getString(R.string.Invalid_Number));
            focusview = et_mobile;
            validate = false;
        }
        if (TextUtils.isEmpty(st_et_password)) {
            focusview = et_password;
            validate = false;
            et_password.setError("please enter password");

        }

        if (TextUtils.isEmpty(st_et_confirm_password)) {
            focusview = et_confirm_password;
            validate = false;
            et_confirm_password.setError("please enter confirm password");

        } else if (!(st_et_password.equals(st_et_confirm_password))) {
            et_confirm_password.setError(getString(R.string.password_not_match));
            focusview = et_confirm_password;
            validate = false;
        }

        if (st_spn_dist_code.equals("")) {
            focusview = spn_dist;
            validate = false;
            Toast.makeText(getApplicationContext(), "please select district", Toast.LENGTH_LONG).show();
        }
        if (st_spn_block_code.equals("")) {
            focusview = spn_block;
            validate = false;
            Toast.makeText(getApplicationContext(), "please select Block", Toast.LENGTH_LONG).show();
        }

        return validate;
    }

    private class DISTRICTDATA extends AsyncTask<String, Void, ArrayList<District_list>> {

        private final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SignUpActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            if (!isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected ArrayList<District_list> doInBackground(String... param) {


            return WebServiceHelper.getDistrictData();

        }

        @Override
        protected void onPostExecute(ArrayList<District_list> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());


                long i = helper.setDistrictName(result);
                if (i > 0) {

                    loadDistrictSpinner();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Server is Slow.Please Try later", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private class BLOCKTDATA extends AsyncTask<String, Void, ArrayList<Block_List>> {

        private final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SignUpActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            if (!isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected ArrayList<Block_List> doInBackground(String... param) {


            return WebServiceHelper.getBlockName(st_spn_dist_code);

        }

        @Override
        protected void onPostExecute(ArrayList<Block_List> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());


                long i = helper.setBlockName(result, st_spn_dist_code);
                if (i > 0) {

                    setBlockData();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private class UPLOADDATA extends AsyncTask<String, Void, String> {
        SignUp data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SignUpActivity.this).create();


        UPLOADDATA(SignUp data) {
            this.data = data;

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            if (!isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected String doInBackground(String... param) {


            String res = WebServiceHelper.SIgnUpDetails(this.data);
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
                String part1 = parts[0]; // 004-


                if (part1.equals("1")) {
                    chk_msg_save("You are Registerd Successfully please wait for the approval!");
                    // Toast.makeText(getApplicationContext(), "You are Registerd Successfully please wait for the approval!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignUpActivity.this, Login.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    chk_msg_save_error("You are not Registerd Successfully");



                    // Toast.makeText(getApplicationContext(), "You are not Registerd Successfully", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    public void chk_msg_save(String msg) {
        // final String wantToUpdate;
        android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(SignUpActivity.this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.fsyicluncher);
        ab.setTitle("Registered Successfully");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(SignUpActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent rddirect = new Intent(SignUpActivity.this, Login.class);
                startActivity(rddirect);
                finish();
                dialog.dismiss();




            }
        });

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

    public void chk_msg_save_error(String msg) {
        // final String wantToUpdate;
        android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(SignUpActivity.this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.uploaderror);
        ab.setTitle("Registration Failed");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(SignUpActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {



                Intent rddirect = new Intent(SignUpActivity.this, Login.class);
                startActivity(rddirect);
                finish();
                dialog.dismiss();

            }
        });

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }
}
