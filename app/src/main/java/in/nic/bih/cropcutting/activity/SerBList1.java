package in.nic.bih.cropcutting.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import in.nic.bih.cropcutting.activity.Entity.ReportView;
import in.nic.bih.cropcutting.activity.Utilities.GlobalVariables;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;
import in.nic.bih.cropcutting.activity.activity.BasicDetails;


/**
 * Created by nicsi on 3/23/2018.
 */
public class SerBList1 extends RecyclerView.Adapter<SerBList1.ViewHolder> {

    Activity activity;
    Context context;
    View view2;
    ViewHolder viewHolder2;
    TextView textView;
    ArrayList<BasicInfo> ListItem1=new ArrayList<>();
    private PopupWindow mPopupWindow;
    String UserId = "";
    String singlerowid;
    DataBaseHelper dataBaseupload;
   // private OnItemClicked onClick;


    public SerBList1(Context context1, ArrayList<BasicInfo> SubjectValues2){

        ListItem1 = SubjectValues2;
        context = context1;
    }
//
//    public interface OnItemClicked {
//        void onItemClick(int position);
//    }
//

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Vdate,Vname,fname,txtdate,txt_vill_name,RandomNo,crop_Nm;
        ImageView img_upload,img_delete;
        LinearLayout sblist;
        LinearLayout allbtns;
        TextView level;
        public ViewHolder(View convertView){

            super(convertView);




            Vdate = convertView.findViewById(R.id.Edate);
            Vname = convertView.findViewById(R.id.Uname);
            fname = convertView.findViewById(R.id.txtfname);
            txtdate = convertView.findViewById(R.id.txtdate);
            txt_vill_name = convertView.findViewById(R.id.txt_vill_name);
            img_upload = convertView.findViewById(R.id.img_send);
            img_delete = convertView.findViewById(R.id.img_delete);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view2 = LayoutInflater.from(context).inflate(R.layout.activity_adaptor_edit_entry,parent,false);

        viewHolder2 = new ViewHolder(view2);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
//        holder.Dist.setText(ListItem.get(position).getDistNm());
//        holder.Block.setText(ListItem.get(position).getBlockNm());
        UserId = PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", "");
        holder.Vdate.setText(ListItem1.get(position).getCrop_name());
        holder.Vname.setText(ListItem1.get(position).getSurvey_no_khesra_no());
        holder.fname.setText(ListItem1.get(position).getFarmer_name());
        holder.txtdate.setText(ListItem1.get(position).getEntryDate());
        holder.txt_vill_name.setText(ListItem1.get(position).getName_of_selected_village());



//        holder.Vname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClick.onItemClick(position);
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BasicDetails.class);
                i.putExtra("KeyId", ListItem1.get(position).getId());
                i.putExtra("isEdit", "Yes");
                Log.d("rjkjbnkgb", "" + ListItem1.get(position).getId());
                context.startActivity(i);
              //  context.finish();
            }
        });


        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                Utilitties.vibrate(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.del1);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure want to Delete the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                        String _uid = ListItem1.get(position).getId();
                        dataBaseHelper.deleteEditRec(_uid, PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));
                        //  ((Edit_entry) activity).setReportListViewDataForAdapter();
                        ListItem1 = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));
                        refresh(ListItem1);

                        ListItem1 = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));

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
                Utilitties.vibrate(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.que);
                builder.setTitle("Data upload");
                builder.setMessage("Are you sure want to Upload the Record");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                        singlerowid = (ListItem1.get(position).getId());
                        DataBaseHelper dbHelper = new DataBaseHelper(context);
                        dialog.dismiss();

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
                dialog.show();
            }
        });


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
                    Toast.makeText(context, part2, Toast.LENGTH_SHORT).show();

                    dataBaseupload = new DataBaseHelper(context);
                    long c = dataBaseupload.deleteRec(rowid);
                    Utilitties.vibrate(context);
                    showCustomDialoguploadsuccess();
                    ListItem1 = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));

                    refresh(ListItem1);
                    ListItem1 = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));


                }
                else if (part1.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.uploaderror);
                    Utilitties.vibrate(context);
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
                    dialog.show();
                    // Display the alert dialog on interface
//                    if (!activity.isFinishing()) {
//                        dialog.show();
//                    }

                }
                else  if (part1.equals("3")) {
                    Toast.makeText(context, part2, Toast.LENGTH_SHORT).show();

                    dataBaseupload = new DataBaseHelper(context);
                    long c = dataBaseupload.deleteRec(rowid);
                    Utilitties.vibrate(context);

                    if(c>0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setIcon(R.drawable.fsyicluncher);
                        builder.setTitle("Alert!!");
                        // Ask the final question
                        builder.setMessage(part2);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                ListItem1 = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));
                                refresh(ListItem1);

                                ListItem1 = dataBaseupload.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(context).getString("USERID", ""));

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        // Display the alert dialog on interface
//                        if (!activity.isFinishing()) {
//                            dialog.show();
//                        }
                    }
                }

                else {
                    Toast.makeText(context, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(context, "Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void refresh(ArrayList<BasicInfo> events) {
        this.ListItem1.clear();
        this.ListItem1.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){

        return ListItem1.size();
    }



    private void showCustomDialoguploadsuccess() {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.fsyicluncher);
        builder.setTitle("Uploaded Successfully!!");
        // Ask the final question
        builder.setMessage("Your data is uploaded Successfully");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


//        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
//
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.my_dialoguploadsucess, viewGroup, false);
//        Button buttonnook = (Button) dialogView.findViewById(R.id.buttonOkk);
//        Button buttonyes = (Button) dialogView.findViewById(R.id.buttonyes);
//
//
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
//        builder.setCancelable(false);
//        Dialog d = new Dialog(context);
//        d.setCanceledOnTouchOutside(false);
//        //setting the view of the builder to our custom view that we already inflated
//        builder.setView(dialogView);
//
//        //finally creating the alert dialog and displaying it
//        final android.support.v7.app.AlertDialog alertDialog = builder.create();
//        buttonnook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                alertDialog.dismiss();
//                activity.finish();
//            }
//        });
//
//        if (!activity.isFinishing()) {
//            alertDialog.show();
//        }
    }
}
