package in.nic.bih.cropcutting.activity.activity;

import android.content.Intent;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.Adaptor_edit_entry;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;

public class Edit_entry extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    ListView listView;
    Adaptor_edit_entry adaptor_new_entry;
    ArrayList<BasicInfo> list = new ArrayList<>();
    String UserId;
    TextView tv_Norecord;
    String _uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        tv_Norecord = (TextView) findViewById(R.id.tv_Norecord);
        //Utilitties.setActionBarBackground(this);
        // Utilitties.setStatusBarColor(this);
        UserId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        listView = findViewById(R.id.listview);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        list = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
        Log.d("value2", "" + list.size());
        adaptor_new_entry = new Adaptor_edit_entry(this);

        listView.setAdapter(adaptor_new_entry);
        adaptor_new_entry.refresh(list);
        adaptor_new_entry.notifyDataSetChanged();
        //setReportListViewData();

        for (int x = 0; x < list.size(); x++)
        {
            Log.d("value3", "" + list.get(x).getSurvey_no_khesra_no());
        }

        if (list.size() > 0) {
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
//            adaptor_new_entry = new Adaptor_edit_entry(this);
//            listView.setAdapter(adaptor_new_entry);
        } else {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), BasicDetails.class);
                i.putExtra("KeyId", list.get(position).getId());
                i.putExtra("isEdit", "Yes");
                Log.d("rjkjbnkgb", "" + list.get(position).getId());
                startActivity(i);
                finish();
            }
        });


    }


    public void setReportListViewData() {
        list = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
        // adaptor_new_entry = new Adaptor_edit_entry(Edit_entry.this, list);
        adaptor_new_entry = new Adaptor_edit_entry(this);
        listView.setAdapter(adaptor_new_entry);
        adaptor_new_entry.notifyDataSetChanged();
    }

    public void setReportListViewDataForAdapter() {
        ArrayList<BasicInfo> data = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
        // adaptor_new_entry = new Adaptor_edit_entry(Edit_entry.this, data);
        adaptor_new_entry = new Adaptor_edit_entry(this);
        listView.setAdapter(adaptor_new_entry);
        adaptor_new_entry.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();
//        if(list.size()> 0){
//            tv_Norecord.setVisibility(View.GONE);
//            listView.setVisibility(View.VISIBLE);
//            listView.invalidate();
//            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
//          //  adaptor_new_entry = new Adaptor_edit_entry(Edit_entry.this, list);
//            adaptor_new_entry = new Adaptor_edit_entry(this);
//            listView.setAdapter(adaptor_new_entry);
//        }else{
//            listView.setVisibility(View.GONE);
//            tv_Norecord.setVisibility(View.VISIBLE);
//        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
