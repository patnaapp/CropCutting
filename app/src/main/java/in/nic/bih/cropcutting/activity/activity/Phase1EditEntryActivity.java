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
import in.nic.bih.cropcutting.activity.Adaptor_edit_entry_phase1;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfo;

public class Phase1EditEntryActivity extends AppCompatActivity
{
    DataBaseHelper dataBaseHelper;
    ListView listview_phase1;
    Adaptor_edit_entry_phase1 adaptor_new_entry_phase1;
    ArrayList<NewLandKhesraInfo> listPhase1 = new ArrayList<>();
    String UserId;
    TextView tv_Norecord_phase1;
    String _uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase1_edit_entry);

        tv_Norecord_phase1 = (TextView) findViewById(R.id.tv_Norecord_phase1);

        UserId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");
        listview_phase1 = findViewById(R.id.listviewn_phase1);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        listPhase1 = dataBaseHelper.getAllEntryDetailPhase1(UserId);
        Log.d("value2", "" + listPhase1.size());
        adaptor_new_entry_phase1 = new Adaptor_edit_entry_phase1(this);
        listview_phase1.setAdapter(adaptor_new_entry_phase1);
        adaptor_new_entry_phase1.refresh(listPhase1);
        adaptor_new_entry_phase1.notifyDataSetChanged();

        //setReportListViewData();
        for (int x = 0; x < listPhase1.size(); x++)
        {
            Log.d("value2", "" + listPhase1.get(x).get_phase1_final_khesra_no());
        }

        if (listPhase1.size() > 0)
        {
            tv_Norecord_phase1.setVisibility(View.GONE);
            listview_phase1.setVisibility(View.VISIBLE);
        }
        else
        {
            listview_phase1.setVisibility(View.GONE);
            tv_Norecord_phase1.setVisibility(View.VISIBLE);
        }

        listview_phase1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(getApplicationContext(), KhesraEntryActivity.class);
                i.putExtra("KeyId", listPhase1.get(position).get_phase1_id());
                i.putExtra("isEdit", "Yes");
                Log.d("rjkjbnkgb", "" + listPhase1.get(position).get_phase1_id());
                startActivity(i);
                finish();
            }
        });
    }

    public void setReportListViewDataForAdapter()
    {
        ArrayList<NewLandKhesraInfo> data = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
        adaptor_new_entry_phase1 = new Adaptor_edit_entry_phase1(this);
        listview_phase1.setAdapter(adaptor_new_entry_phase1);
        adaptor_new_entry_phase1.notifyDataSetChanged();
    }

    protected void onResume()
    {
        super.onResume();
    }

}
