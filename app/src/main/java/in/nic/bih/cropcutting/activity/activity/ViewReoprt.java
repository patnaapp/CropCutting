package in.nic.bih.cropcutting.activity.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nic.bih.cropcutting.R;
import in.nic.bih.cropcutting.activity.DataBase.DataBaseHelper;
import in.nic.bih.cropcutting.activity.DataBase.WebServiceHelper;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.ReportView;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.SerBList;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

public class ViewReoprt extends AppCompatActivity {

    private ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ListView listView;
    String username;
    //Adapter_view_report adaptor_view_report;
    ArrayList<ReportView> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    Spinner spin_agri_yr,spin_season;

    ArrayList<Financial_Year> FYearList = new ArrayList<Financial_Year>();
    ArrayList<Season_List> SeasonList = new ArrayList<Season_List>();

    ArrayList<String> FyearArray;
    ArrayList<String> SeasonArray;

    ArrayAdapter<String> Fyearadapter;
    ArrayAdapter<String> Seasonadapter;
    String _spn_agri_id = "", _spn_agri_year = "";
    String _spn_season_id = "", _spn_season_nm = "", _spn_seasn_flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reoprt);

        dataBaseHelper = new DataBaseHelper(ViewReoprt.this);


        spin_agri_yr = findViewById(R.id.spin_agri_yr);
        spin_season = findViewById(R.id.spin_season);


        spin_agri_yr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    _spn_agri_id = FYearList.get(pos - 1).getYear_Id();
                    _spn_agri_year = FYearList.get(pos - 1).getFinancial_year();

                    loadSeason_NEw();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spin_season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    _spn_season_id = SeasonList.get(pos - 1).getSeason_Id();
                    _spn_season_nm = SeasonList.get(pos - 1).getSeason_Name();
                    _spn_seasn_flag = SeasonList.get(pos - 1).getFlag();


                    new VIEWROPRT().execute();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });



        Utilitties.setActionBarBackground(this);

        Utilitties.setStatusBarColor(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(ViewReoprt.this);

        recyclerView.setLayoutManager(recylerViewLayoutManager);


        recyclerViewAdapter = new SerBList(ViewReoprt.this, list);

        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);

        username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", "");

        FYearList = dataBaseHelper.getFinancialYearLocal();
        if (FYearList.size() <= 0) {
            new FINANCIALYEAR_NewReport().execute();
        } else {
            loadFinancialYear();
        }
        SeasonList = dataBaseHelper.getSeasonLocal();
        if (SeasonList.size() <= 0) {
            new SEASON_NewReport().execute();
        }
//        else {
//            loadSeason_NEw();
//        }

//        new VIEWROPRT().execute();





    }

    private class VIEWROPRT extends AsyncTask<String, Void, ArrayList<ReportView>> {

        private final ProgressDialog dialog = new ProgressDialog(ViewReoprt.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(ViewReoprt.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ReportView> doInBackground(String... param) {


            return WebServiceHelper.getViewReport(username,_spn_season_id,_spn_agri_id);

        }

        @Override
        protected void onPostExecute(ArrayList<ReportView> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {

                DataBaseHelper helper = new DataBaseHelper(getApplicationContext());


                recylerViewLayoutManager = new LinearLayoutManager(ViewReoprt.this);

                recyclerView.setLayoutManager(recylerViewLayoutManager);


                recyclerViewAdapter = new SerBList(ViewReoprt.this, result);

                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(recyclerViewAdapter);


//                String uid,kheshraNum,randomNum;
//                uid=result.get(0).getUserid();
//                kheshraNum=result.get(0).getSurveyNo();
//                randomNum=result.get(0).getRandomNo();
//
//                long i = helper.setValidateData(uid,kheshraNum,randomNum);
//                Log.d("resultgenf",""+ result);
                // adaptor_view_report=new Adapter_view_report(ViewReoprt.this,result);

                // listView.setAdapter(adaptor_view_report);
//                if(i>0)
//                {
//                    //getValidateData();
//                }

            }
        }
    }


    private class FINANCIALYEAR_NewReport extends AsyncTask<String, Void, ArrayList<Financial_Year>> {

        private final ProgressDialog dialog = new ProgressDialog(ViewReoprt.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(ViewReoprt.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Financial_Year> doInBackground(String... param) {


            return WebServiceHelper.getFinancialYear();

        }

        @Override
        protected void onPostExecute(ArrayList<Financial_Year> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfg", "" + result);

                DataBaseHelper helper = new DataBaseHelper(ViewReoprt.this);


                long i = helper.setFinancialYear(result);
                if (i > 0) {
                    loadFinancialYear();

                } else {

                }

            } else {
                Toast.makeText(ViewReoprt.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadFinancialYear() {
        dataBaseHelper = new DataBaseHelper(ViewReoprt.this);

        FYearList = dataBaseHelper.getFinancialYearLocal();
        FyearArray = new ArrayList<String>();
        FyearArray.add("-select-");
        int i = 0;
        for (Financial_Year financial_year : FYearList) {
            FyearArray.add(financial_year.getFinancial_year());
            i++;
        }
        Fyearadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, FyearArray);
        Fyearadapter.setDropDownViewResource(R.layout.dropdowlist);
        spin_agri_yr.setAdapter(Fyearadapter);



    }


    private class SEASON_NewReport extends AsyncTask<String, Void, ArrayList<Season_List>> {

        private final ProgressDialog dialog = new ProgressDialog(ViewReoprt.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(ViewReoprt.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Season_List> doInBackground(String... param) {


            return WebServiceHelper.getSeason();

        }

        @Override
        protected void onPostExecute(ArrayList<Season_List> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                Log.d("Resultgfgddddd", "" + result);

                DataBaseHelper helper = new DataBaseHelper(ViewReoprt.this);


                long i = helper.setSeasonLocal(result);
                if (i > 0) {
                   // loadSeason_NEw();

                    // Toast.makeText(BasicDetails.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(BasicDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(ViewReoprt.this, "Result:null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void loadSeason_NEw() {
        dataBaseHelper = new DataBaseHelper(ViewReoprt.this);

        SeasonList = dataBaseHelper.getSeasonLocal();
        SeasonArray = new ArrayList<String>();
        SeasonArray.add("-select-");

        int i = 0;
        for (Season_List season_list : SeasonList) {
            SeasonArray.add(season_list.getSeason_Name());
            i++;
        }
        Seasonadapter = new ArrayAdapter<>(this, R.layout.dropdowlist, SeasonArray);
        Seasonadapter.setDropDownViewResource(R.layout.dropdowlist);
        spin_season.setAdapter(Seasonadapter);


    }

}
