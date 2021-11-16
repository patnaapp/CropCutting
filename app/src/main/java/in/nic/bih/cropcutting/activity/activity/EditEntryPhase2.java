package in.nic.bih.cropcutting.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.ReportView;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.SerBList;
import in.nic.bih.cropcutting.activity.SerBList1;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

public class EditEntryPhase2 extends AppCompatActivity {

    private ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ListView listView;
    String username;
    //Adapter_view_report adaptor_view_report;
    ArrayList<BasicInfo> listPhase2 = new ArrayList<BasicInfo>();
    RecyclerView recyclerVieweditentry;
    RecyclerView.Adapter recyclerViewAdapterEdit;
    RecyclerView.LayoutManager recylerViewLayoutManager1;

    TextView tv_Norecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry_phase2);

        dataBaseHelper = new DataBaseHelper(EditEntryPhase2.this);

        username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");

        listPhase2 = dataBaseHelper.getAllEntryDetail(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
//        Log.d("value2", "" + listPhase2.size());
        Utilitties.setActionBarBackground(this);
        Utilitties.setStatusBarColor(this);

        recyclerVieweditentry = (RecyclerView) findViewById(R.id.my_recycler_view1);
        tv_Norecord = (TextView) findViewById(R.id.tv_Norecord1);



            if (listPhase2.size() > 0) {
                tv_Norecord.setVisibility(View.GONE);
                recyclerVieweditentry.setVisibility(View.VISIBLE);

            } else {
                recyclerVieweditentry.setVisibility(View.GONE);
                tv_Norecord.setVisibility(View.VISIBLE);
            }





        recylerViewLayoutManager1 = new LinearLayoutManager(EditEntryPhase2.this);

        recyclerVieweditentry.setLayoutManager(recylerViewLayoutManager1);
        recyclerViewAdapterEdit = new SerBList1(EditEntryPhase2.this, listPhase2);

        recyclerViewAdapterEdit.notifyDataSetChanged();
        recyclerVieweditentry.setAdapter(recyclerViewAdapterEdit);



    }


}
