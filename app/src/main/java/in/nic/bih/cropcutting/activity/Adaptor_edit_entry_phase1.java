package in.nic.bih.cropcutting.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfo;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;
import in.nic.bih.cropcutting.activity.activity.Phase1EditEntryActivity;


public class Adaptor_edit_entry_phase1 extends BaseAdapter {
    DataBaseHelper dataBaseupload;

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<NewLandKhesraInfo> Phase1Listt = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    TextView Vdate, Vname;
    Context mContext;
    String _uid;
    String UserId = "", crop_Name = "";
    String version;
    ListView listView;
    Adaptor_edit_entry_phase1 adaptor_edit_entry_phase1;
    String singlerowid;

    public Adaptor_edit_entry_phase1(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return Phase1Listt.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (convertView == null)
            vi = (LinearLayout) mLayoutInflater.inflate(R.layout.activity_adaptor_edit_entry_phase1, null);
        TextView Vdate = (TextView) vi.findViewById(R.id.Edate);
        TextView Vname = (TextView) vi.findViewById(R.id.Uname);
        TextView txtdate = (TextView) vi.findViewById(R.id.txtdate);
        TextView txtfname = (TextView) vi.findViewById(R.id.txtfname);
        TextView txt_villname = (TextView) vi.findViewById(R.id.txt_villname);


        ImageView img_upload = (ImageView) vi.findViewById(R.id.img_send);
        ImageView img_delete = (ImageView) vi.findViewById(R.id.img_delete);


        // ViewHolder holder = null;
        dataBaseupload = new DataBaseHelper(mContext);
        UserId = PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", "");
        crop_Name = dataBaseupload.getNameFor("CropType", "Crop_Id", "Crop_Name", Phase1Listt.get(position).get_phase1_croptype());
        Vdate.setText(Phase1Listt.get(position).get_phase1_final_khesra_no());
        //Vname.setText(Phase1Listt.get(position).get_phase1_crop_name());
        Vname.setText(crop_Name);
        txtfname.setText(Phase1Listt.get(position).get_Farmer_Name());
        txtdate.setText(Phase1Listt.get(position).get_phase1_Entry_date());
        txt_villname.setText(Phase1Listt.get(position).get_revenue_village());

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.drawable.del1);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure want to Delete the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
                        String _uid = Phase1Listt.get(position).get_phase1_id();
                        dataBaseHelper.deleteEditRecPhase1(_uid, PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                        // ((Phase1EditEntryActivity)mContext).setReportListViewDataForAdapter();
                        Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                        refresh(Phase1Listt);

                        Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));

                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });


        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.drawable.que);
                builder.setTitle("Data upload");
                builder.setMessage("Are you sure want to Upload the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
                        singlerowid = (Phase1Listt.get(position).get_phase1_id());
                        DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                        dialog.dismiss();



                        ArrayList<NewLandKhesraInfo> dataProgress = dbHelper.getAllEntryDetailsinglePhase1(UserId, singlerowid);
                        if (dataProgress.size() > 0) {

                            for (NewLandKhesraInfo data : dataProgress) {
                                if(data.get_chng_khesra_no().equals("1")){
                                    if(data.getFieldImg()!=null && data.getFinalSelectedField()!=null && data.getNazriNkasha()!=null){
                                  //  if(data.getFieldImg()!=null ||data.getFieldImg()!="" && data.getFinalSelectedField()!=null ||data.getFinalSelectedField()!="" && data.getNazriNkasha()!=null || data.getNazriNkasha()!="") {
                                        new UPLOADDATAPhase1(data).execute();
                                    }
                                    else {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                        builder1.setIcon(R.drawable.uploaderror);
                                        builder1.setTitle("Alert !!");
                                        builder1.setMessage("Please capture all 3 images before uploading");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();
                                    }

                                }
                                else {
                                    new UPLOADDATAPhase1(data).execute();
                                }
//                                new UPLOADDATAPhase1(data).execute();
                            }

                            GlobalVariables.listSize = dataProgress.size();
                        }
                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                //  if(dialog.isFinishing()) {
                dialog.show();
                //}
            }
        });


        return vi;

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

            version = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;

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


    private class UPLOADDATAPhase1 extends AsyncTask<String, Void, String> {
        NewLandKhesraInfo data;
        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(mContext);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext).create();


        UPLOADDATAPhase1(NewLandKhesraInfo data) {
            this.data = data;
            //_uid = data.getId();
            rowid = data.get_phase1_id();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            String devicename = getDeviceName();
            String app_version = getAppVersion();
            boolean isTablet = isTablet(mContext);
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
                String[] parts = string.split(",");
                String part1 = parts[0]; // 004-
                String part2 = parts[1];

                if (part1.equals("1")) {

                    dataBaseupload = new DataBaseHelper(mContext);
                    long c = dataBaseupload.deleteRecPhase1(rowid);

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.fsyicluncher);
                    builder.setTitle("Success!!");
                    // Ask the final question
                    builder.setMessage(part2);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            dialog.dismiss();
                            refresh(Phase1Listt);
                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));


                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                } else if (part1.equals("0")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.uploaderror);

                    builder.setTitle("Alert!!");
                    // Ask the final question
                    builder.setMessage(part2);

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (part1.equals("2")) {
                    dataBaseupload = new DataBaseHelper(mContext);
                    long c = dataBaseupload.deleteRecPhase1(rowid);


                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.fsyicluncher);
                    builder.setTitle("Record Updated!!");
                    // Ask the final question
                    builder.setMessage(part2);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            dialog.dismiss();
                            refresh(Phase1Listt);
                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));

                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                } else {
                    Toast.makeText(mContext, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(mContext, "Result:null ..Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void refresh(ArrayList<NewLandKhesraInfo> events) {
        this.Phase1Listt.clear();
        this.Phase1Listt.addAll(events);
        notifyDataSetChanged();
    }


}
