package in.nic.bih.cropcutting.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;
import in.nic.bih.cropcutting.activity.activity.Edit_entry;
import in.nic.bih.cropcutting.activity.activity.Home;

public class Adaptor_edit_entry extends BaseAdapter {
    DataBaseHelper dataBaseupload;

   Activity activity;
    LayoutInflater mInflater;
    ArrayList<BasicInfo> Listt = new ArrayList<>();
    TextView Vdate, Vname;
    Context context;
    String _uid;
    String UserId = "";
    ListView listView;
    Adaptor_edit_entry adaptor_edit_entry;
    String singlerowid;

    Context mContext;
    private LayoutInflater mLayoutInflater;

//    public Adaptor_edit_entry() {
//
//
//    }

//    public Adaptor_edit_entry(Edit_entry editTHRList, ArrayList<BasicInfo> CropList) {
//        this.activity = editTHRList;
//        this.Listt = CropList;
//        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//    }

    public Adaptor_edit_entry(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return Listt.size();
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
        ViewHolder holder = null;

        View vi = convertView;

        dataBaseupload = new DataBaseHelper(mContext);
        UserId = PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", "");
        if (convertView == null) {
            vi = (LinearLayout) mLayoutInflater.inflate(R.layout.activity_adaptor_edit_entry, null);
           // convertView = mInflater.inflate(R.layout.activity_adaptor_edit_entry, null);

          //  holder = new ViewHolder();

            TextView Vdate = (TextView) vi.findViewById(R.id.Edate);
            TextView Vname = (TextView) vi.findViewById(R.id.Uname);
            TextView fname = (TextView) vi.findViewById(R.id.txtfname);
            TextView txtdate = (TextView) vi.findViewById(R.id.txtdate);
            TextView txt_vill_name = (TextView) vi.findViewById(R.id.txt_vill_name);
            ImageView img_upload = (ImageView) vi.findViewById(R.id.img_send);
            ImageView img_delete = (ImageView) vi.findViewById(R.id.img_delete);



//            holder.Vdate = convertView.findViewById(R.id.Edate);
//            holder.Vname = convertView.findViewById(R.id.Uname);
//            holder.fname = convertView.findViewById(R.id.txtfname);
//            holder.txtdate = convertView.findViewById(R.id.txtdate);
//            holder.txt_vill_name = convertView.findViewById(R.id.txt_vill_name);
//            holder.img_upload = convertView.findViewById(R.id.img_send);
//            holder.img_delete = convertView.findViewById(R.id.img_delete);
            Vname.setText(Listt.get(position).getSurvey_no_khesra_no());
            Vdate.setText(Listt.get(position).getCrop_name());
            txtdate.setText(Listt.get(position).getEntryDate());
            fname.setText(Listt.get(position).getFarmer_name());
            txt_vill_name.setText(Listt.get(position).getName_of_selected_village());


            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Build an AlertDialog
                    Utilitties.vibrate(mContext);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.del1);
                    builder.setTitle("Delete Data");
                    builder.setMessage("Are you sure want to Delete the Record");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
                            String _uid = Listt.get(position).getId();
                            dataBaseHelper.deleteEditRec(_uid, PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                          //  ((Edit_entry) activity).setReportListViewDataForAdapter();
                            Listt = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            refresh(Listt);

                            Listt = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));

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
                    Utilitties.vibrate(mContext);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.que);
                    builder.setTitle("Data upload");
                    builder.setMessage("Are you sure want to Upload the Record");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
                            singlerowid = (Listt.get(position).getId());
                            DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                            dialog.dismiss();

                            ArrayList<BasicInfo> dataProgress = dbHelper.getAllEntryDetailsingle(UserId, singlerowid);
                            if (dataProgress.size() > 0) {

                                for (BasicInfo data : dataProgress) {
//                                    if(data.getImg1()!=null && data.getImg2()!=null && data.getImg3()!=null && data.getImg4()!=null){
//                                        new UPLOADDATA(data).execute();
//                                    }
//                                    else {
//                                        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(activity);
//                                        alertDialog.setTitle("Alert !!");
//
//                                        alertDialog.setMessage("Please capture all images before uploading for Khasra No-"+data.getSurvey_no_khesra_no());
//                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                                        alertDialog.show();
//                                    }
                                    new UPLOADDATA(data).execute();
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
//                    if (!activity.isFinishing()) {
//                        dialog.show();
//                    }
                }
            });

           // convertView.setTag(holder);
        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        return convertView;
        return vi;
    }

    private class ViewHolder {
        TextView Vdate, Vname, upload_txt, txtdate,fname,txt_vill_name;
        ImageView img_delete, img_upload;

    }

    private class UPLOADDATA extends AsyncTask<String, Void, String> {
        BasicInfo data;
        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(mContext);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext).create();


        UPLOADDATA(BasicInfo data) {
            this.data = data;
            //_uid = data.getId();
            rowid = data.getId();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String res = WebServiceHelper.UploadBasicDetails(data);
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
                    Toast.makeText(mContext, part2, Toast.LENGTH_SHORT).show();

                    dataBaseupload = new DataBaseHelper(mContext);
                    long c = dataBaseupload.deleteRec(rowid);
                    Utilitties.vibrate(mContext);
                    showCustomDialoguploadsuccess();
                    Listt = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                    refresh(Listt);

                    Listt = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));


                } else if (part1.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.uploaderror);
                    Utilitties.vibrate(mContext);
                    builder.setTitle("Alert!!");
                    // Ask the final question
                    builder.setMessage(part2);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
//                    if (!activity.isFinishing()) {
//                        dialog.show();
//                    }

                }
              else  if (part1.equals("3")) {
                    Toast.makeText(mContext, part2, Toast.LENGTH_SHORT).show();

                    dataBaseupload = new DataBaseHelper(mContext);
                    long c = dataBaseupload.deleteRec(rowid);
                    Utilitties.vibrate(mContext);

                    if(c>0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setIcon(R.drawable.fsyicluncher);
                        builder.setTitle("Alert!!");
                        // Ask the final question
                        builder.setMessage(part2);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Listt = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                                refresh(Listt);

                                Listt = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));

                            }
                        });

                        AlertDialog dialog = builder.create();
                        // Display the alert dialog on interface
//                        if (!activity.isFinishing()) {
//                            dialog.show();
//                        }
                    }
                }

                else {
                    Toast.makeText(mContext, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(mContext, "Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void refresh(ArrayList<BasicInfo> events) {
        this.Listt.clear();
        this.Listt.addAll(events);
        notifyDataSetChanged();
    }


    private void showCustomDialoguploadsuccess() {

        ViewGroup viewGroup = activity.findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.my_dialoguploadsucess, viewGroup, false);
        Button buttonnook = (Button) dialogView.findViewById(R.id.buttonOkk);
        Button buttonyes = (Button) dialogView.findViewById(R.id.buttonyes);


      AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        Dialog d = new Dialog(mContext);
        d.setCanceledOnTouchOutside(false);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        buttonnook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                activity.finish();
            }
        });

        if (!activity.isFinishing()) {
            alertDialog.show();
        }
    }

}
