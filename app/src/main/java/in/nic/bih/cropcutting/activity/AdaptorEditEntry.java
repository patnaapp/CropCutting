package in.nic.bih.cropcutting.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.activity.Edit_entry;

public class AdaptorEditEntry extends RecyclerView.Adapter<AdaptorEditEntry.ViewHolder> {


    //  Context context;
    Activity context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;
    DataBaseHelper dataBaseupload;
    ArrayList<BasicInfo> Listt = new ArrayList<>();
    private PopupWindow mPopupWindow;

    String UserId = "";
    ListView listView;
    Adaptor_edit_entry adaptor_edit_entry;
    String singlerowid;

    public AdaptorEditEntry(Edit_entry context1, ArrayList<BasicInfo> SubjectValues1) {

        Listt = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Vdate, Vname, upload_txt, txtdate;
        ImageView img_delete, img_upload;

        public ViewHolder(View convertView) {

            super(convertView);


            Vdate = convertView.findViewById(R.id.Edate);
            Vname = convertView.findViewById(R.id.Uname);
            txtdate = convertView.findViewById(R.id.txtdate);
            img_upload = convertView.findViewById(R.id.img_send);
            img_delete = convertView.findViewById(R.id.img_delete);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.activity_adaptor_edit_entry, parent, false);

        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Vname.setText(Listt.get(position).getSurvey_no_khesra_no());
        holder.Vdate.setText(Listt.get(position).getCrop_name());
        holder.txtdate.setText(Listt.get(position).getEntryDate());
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.del1);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure want to Delete the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                        String _uid = Listt.get(position).getId();
                        dataBaseHelper.deleteEditRec(_uid, PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));
                        ((Edit_entry) context).setReportListViewDataForAdapter();
                        Listt = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));
                        refresh(Listt);

                        Listt = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));

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


        holder.img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.fsyicluncher);
                builder.setTitle("Data upload");
                builder.setMessage("Are you sure want to Upload the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                        singlerowid = (Listt.get(position).getId());
                        DataBaseHelper dbHelper = new DataBaseHelper(context);

                        ArrayList<BasicInfo> dataProgress = dbHelper.getAllEntryDetailsingle(UserId, singlerowid);
                        if (dataProgress.size() > 0) {

                            for (BasicInfo data : dataProgress) {
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
                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {

        return Listt.size();
    }


    private class UPLOADDATA extends AsyncTask<String, Void, String> {
        BasicInfo data;
        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(context);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();


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
                    Toast.makeText(context, part1, Toast.LENGTH_SHORT).show();
                    // boolean cc = dataBaseHelper.deleterowconLab(Integer.parseInt(data.getId()));

                    dataBaseupload = new DataBaseHelper(context);
                    long c = dataBaseupload.deleteRec(rowid);
                    showCustomDialoguploadsuccess();
//                    if(cc){
//
//                        Toast.makeText(activity, "deleted !", Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                    else{
//
//                        Toast.makeText(activity, " not deleted !", Toast.LENGTH_LONG).show();
//
//                    }
                    /*Intent intent=new Intent(Edit_entry.this,Home.class);
                    startActivity(intent);*/
                } else if (part1.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.fsyicluncher);
                    builder.setTitle("Alert!!");
                    // Ask the final question
                    builder.setMessage(part2);

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when user clicked the Yes button
                            // Set the TextView visibility GONE
                            dialog.dismiss();
                            /*Intent intent=new Intent(Edit_entry.this,Edit_entry.class);
                            startActivity(intent);*/

                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

                    /*Toast.makeText(getApplicationContext(), "The data for this order of Experiment alloted by DSO is already uploaded !", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Home.this,Home.class);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(context, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(context, "Uploading failed. Server is Slow..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void refresh(ArrayList<BasicInfo> events) {
        this.Listt.clear();
        this.Listt.addAll(events);
        notifyDataSetChanged();
    }


    private void showCustomDialoguploadsuccess() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = context.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context).inflate(R.layout.my_dialoguploadsucess, viewGroup, false);
        Button buttonnook = (Button) dialogView.findViewById(R.id.buttonOkk);
        Button buttonyes = (Button) dialogView.findViewById(R.id.buttonyes);
        //Now we need an AlertDialog.Builder object

      AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        Dialog d = new Dialog(context);
        d.setCanceledOnTouchOutside(false);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        buttonnook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                context.finish();
            }
        });


        alertDialog.show();
    }

}