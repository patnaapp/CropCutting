package in.nic.bih.cropcutting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.Entity.ReportView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by nicsi on 3/23/2018.
 */
public class SerBList extends RecyclerView.Adapter<SerBList.ViewHolder> {


    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;
    ArrayList<ReportView> ListItem=new ArrayList<>();
    private PopupWindow mPopupWindow;


    public SerBList(Context context1, ArrayList<ReportView> SubjectValues1){

        ListItem = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

       public TextView Dist,Block,Panchayat,Fname,SurveyNo,RandomNo,crop_Nm;
        ImageView imgbasic,imgedit;
        LinearLayout sblist;
        LinearLayout allbtns;
        TextView level;
        public ViewHolder(View convertView){

            super(convertView);


//            Dist= convertView.findViewById(R.id.distt_nm);
//            Block= convertView.findViewById(R.id.block_nm);
            Panchayat= convertView.findViewById(R.id.panchayat_nm);
            Fname= convertView.findViewById(R.id.farmer_Nm);
            SurveyNo= convertView.findViewById(R.id.survey_No);
            RandomNo= convertView.findViewById(R.id.random_No);
            crop_Nm= convertView.findViewById(R.id.crop_Nm1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view1 = LayoutInflater.from(context).inflate(R.layout.activity_adapter_view_report,parent,false);

        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
//        holder.Dist.setText(ListItem.get(position).getDistNm());
//        holder.Block.setText(ListItem.get(position).getBlockNm());
        holder.Panchayat.setText(ListItem.get(position).getPanchayatNm());
        holder.Fname.setText(ListItem.get(position).getFarmername());
        holder.SurveyNo.setText(ListItem.get(position).getSurveyNo());
        holder.RandomNo.setText(ListItem.get(position).getRandomNo());
        holder.crop_Nm.setText(ListItem.get(position).getCropName());


    }





    @Override
    public int getItemCount(){

        return ListItem.size();
    }

}
