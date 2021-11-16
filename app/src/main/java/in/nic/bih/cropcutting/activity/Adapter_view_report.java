package in.nic.bih.cropcutting.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.Entity.ReportView;
import in.nic.bih.cropcutting.activity.activity.ViewReoprt;

public class Adapter_view_report extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<ReportView> List = new ArrayList<>();
    TextView Vdate, Vname;


    public Adapter_view_report(ViewReoprt editTHRList, ArrayList<ReportView> CropList) {
        this.activity = editTHRList;
        this.List = CropList;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Adapter_view_report.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_adapter_view_report, null);

            holder = new Adapter_view_report.ViewHolder();
//            holder.Dist = convertView.findViewById(R.id.distt_nm);
//            holder.Block = convertView.findViewById(R.id.block_nm);
            holder.Panchayat = convertView.findViewById(R.id.panchayat_nm);
            holder.Fname = convertView.findViewById(R.id.farmer_Nm);
            holder.SurveyNo = convertView.findViewById(R.id.survey_No);
            holder.RandomNo = convertView.findViewById(R.id.random_No);
            holder.crop_Nm = convertView.findViewById(R.id.crop_Nm1);


//            holder.Dist.setText(List.get(position).getDistNm());
//            holder.Block.setText(List.get(position).getBlockNm());
            holder.Panchayat.setText(List.get(position).getPanchayatNm());
            holder.Fname.setText(List.get(position).getFarmername());
            holder.SurveyNo.setText(List.get(position).getSurveyNo());
            holder.RandomNo.setText(List.get(position).getRandomNo());
            holder.crop_Nm.setText(List.get(position).getCropName());


            convertView.setTag(holder);
        } else {
            holder = (Adapter_view_report.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView Dist, Block, Panchayat, Fname, SurveyNo, RandomNo,crop_Nm;

    }
}
