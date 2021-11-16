package in.nic.bih.cropcutting.activity.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.Forget_password;

public class Custom_forgot_password extends AppCompatActivity {
    EditText et_mobile;
    Button btn_submit;
    String st_mobile;
    String UserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_forgot_password);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        btn_submit = (Button) findViewById(R.id.btn_OK);

        btn_submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ForgetPassword();
                    }

                });

    }

    public void ForgetPassword() {

        st_mobile = et_mobile.getText().toString();
        DataBaseHelper placeData = new DataBaseHelper(Custom_forgot_password.this);
        Forget_password forget_password = new Forget_password();

        forget_password.setMobile(st_mobile);


        new FORGETPASSWORD(et_mobile.getText().toString()).execute();

    }


    private class FORGETPASSWORD extends AsyncTask<String, Void, String> {
        String data;
        private final ProgressDialog dialog = new ProgressDialog(Custom_forgot_password.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Custom_forgot_password.this).create();

        FORGETPASSWORD(String data) {
            this.data = data;

        }


        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String res = WebServiceHelper.ForgetPassword(this.data);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responseval", "" + result);
            if (result != null) {
                if (result.equals("1")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Custom_forgot_password.this);

                    // Ask the final question
                    builder.setMessage("The Password has been sent to your registerd mobile number !");

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when user clicked the Yes button
                            // Set the TextView visibility GONE

                            Intent intent = new Intent(Custom_forgot_password.this, Login.class);
                            startActivity(intent);
                            finish();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();
               /* Toast.makeText(getApplicationContext(), "The Password has been sent to your registerd mobile number", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Custom_forgot_password.this, Home.class);
                startActivity(intent);*/

                } else {
                    et_mobile.setError("Please Enter the registerd mobile number");
                }

            }
        }
    }
}
