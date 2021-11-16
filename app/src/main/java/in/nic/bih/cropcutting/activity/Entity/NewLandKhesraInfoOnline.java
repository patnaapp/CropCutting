package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class NewLandKhesraInfoOnline implements KvmSerializable
{
    public static Class<NewLandKhesraInfoOnline> NewKhesraInfoOnline = NewLandKhesraInfoOnline.class;
    private String phase1_id;
    private String phase1_userid;
    private String phase1_lat;
    private String phase1_longi;
    private String phase1_lat1;
    private String phase1_longi1;
    private String phase1_lat2;
    private String phase1_longi2;
    private String nazriNkasha;
    private String FieldImg;
    private String FinalSelectedField;
    private String phse1_agri_yr;
    private String phse1_agri_yr_nm;
    private String phase1_season;
    private String phase1_season_nm;
    private String phase1_croptype;
    private String phase1_crop_name;
    private String phase1_dist;
    private String phase1_block;
    private String phase1_panchayat;
    private String phase1_panchayat_nm;

    private String phase1_highest_plot_no;
    private String phase1_alloted_khesra_no;
    private String chng_khesra_no;
    private String chng_khesra_no_nm;
    private String phase1_final_khesra_no;
    private String phase1_Entry_date;
    private String phase1_farmerName;
    private String phase1_chng_khesra_reason;
    private String phase1_tentative_cce_date;
    private String phase1_village;



    public NewLandKhesraInfoOnline(SoapObject sobj)
    {
        this.phase1_farmerName=sobj.getProperty("FarmerName").toString();
        this.phase1_dist=sobj.getProperty("District").toString();
        this.phase1_block=sobj.getProperty("Block").toString();
        this.phase1_panchayat=sobj.getProperty("Panchayat").toString();
        this.phase1_season=sobj.getProperty("Season").toString();
        this.phase1_croptype=sobj.getProperty("Crop").toString();
        this.phase1_alloted_khesra_no=sobj.getProperty("AllocatedKhasaraNo").toString();
        this.phase1_final_khesra_no=sobj.getProperty("FinalKhasaraNo").toString();
       // this.phase1_chng_khesra_reason=sobj.getProperty("ReasonforKhasaraNochange").toString();

        if (sobj.getProperty("ReasonforKhasaraNochange").toString().equalsIgnoreCase("anyType{}")){
            this.phase1_chng_khesra_reason="";
        }
        else {
            this.phase1_chng_khesra_reason = sobj.getProperty("ReasonforKhasaraNochange").toString();
        }


        this.phase1_highest_plot_no=sobj.getProperty("HighestPlotNo").toString();

        if (sobj.getProperty("FieldOverviewing").toString().equalsIgnoreCase("anyType{}")){
            this.FieldImg="";
        }
        else {
            this.FieldImg = sobj.getProperty("FieldOverviewing").toString();
        }

        if (sobj.getProperty("NajariMap").toString().equalsIgnoreCase("anyType{}")){
            this.nazriNkasha="";
        }
        else {
            this.nazriNkasha = sobj.getProperty("NajariMap").toString();
        }

        if (sobj.getProperty("FinalPloting").toString().equalsIgnoreCase("anyType{}")){
            this.FinalSelectedField="";
        }
        else {
            this.FinalSelectedField = sobj.getProperty("FinalPloting").toString();
        }


        this.phase1_lat=sobj.getProperty("FOLat").toString();
        this.phase1_longi=sobj.getProperty("FOLan").toString();
        this.phase1_lat1=sobj.getProperty("NPLat").toString();
        this.phase1_longi1=sobj.getProperty("NPLan").toString();
        this.phase1_lat2=sobj.getProperty("FPLat").toString();
        this.phase1_longi2=sobj.getProperty("FPLan").toString();
        this.phse1_agri_yr=sobj.getProperty("AgricultureYear").toString();
        this.phase1_Entry_date=sobj.getProperty("SurveyDate").toString();

        this.phase1_village=sobj.getProperty("Village").toString();
        this.phase1_tentative_cce_date=sobj.getProperty("TentativeDateCCE").toString();






    }

    public String getPhase1_id() {
        return phase1_id;
    }

    public void setPhase1_id(String phase1_id) {
        this.phase1_id = phase1_id;
    }

    public String getPhase1_userid() {
        return phase1_userid;
    }

    public void setPhase1_userid(String phase1_userid) {
        this.phase1_userid = phase1_userid;
    }

    public String getPhase1_lat() {
        return phase1_lat;
    }

    public void setPhase1_lat(String phase1_lat) {
        this.phase1_lat = phase1_lat;
    }

    public String getPhase1_longi() {
        return phase1_longi;
    }

    public void setPhase1_longi(String phase1_longi) {
        this.phase1_longi = phase1_longi;
    }

    public String getPhase1_lat1() {
        return phase1_lat1;
    }

    public void setPhase1_lat1(String phase1_lat1) {
        this.phase1_lat1 = phase1_lat1;
    }

    public String getPhase1_longi1() {
        return phase1_longi1;
    }

    public void setPhase1_longi1(String phase1_longi1) {
        this.phase1_longi1 = phase1_longi1;
    }

    public String getPhase1_lat2() {
        return phase1_lat2;
    }

    public void setPhase1_lat2(String phase1_lat2) {
        this.phase1_lat2 = phase1_lat2;
    }

    public String getPhase1_longi2() {
        return phase1_longi2;
    }

    public void setPhase1_longi2(String phase1_longi2) {
        this.phase1_longi2 = phase1_longi2;
    }

    public String getNazriNkasha() {
        return nazriNkasha;
    }

    public void setNazriNkasha(String nazriNkasha) {
        this.nazriNkasha = nazriNkasha;
    }

    public String getFieldImg() {
        return FieldImg;
    }

    public void setFieldImg(String fieldImg) {
        FieldImg = fieldImg;
    }

    public String getFinalSelectedField() {
        return FinalSelectedField;
    }

    public void setFinalSelectedField(String finalSelectedField) {
        FinalSelectedField = finalSelectedField;
    }

    public String getPhse1_agri_yr() {
        return phse1_agri_yr;
    }

    public void setPhse1_agri_yr(String phse1_agri_yr) {
        this.phse1_agri_yr = phse1_agri_yr;
    }

    public String getPhse1_agri_yr_nm() {
        return phse1_agri_yr_nm;
    }

    public void setPhse1_agri_yr_nm(String phse1_agri_yr_nm) {
        this.phse1_agri_yr_nm = phse1_agri_yr_nm;
    }

    public String getPhase1_season() {
        return phase1_season;
    }

    public void setPhase1_season(String phase1_season) {
        this.phase1_season = phase1_season;
    }

    public String getPhase1_season_nm() {
        return phase1_season_nm;
    }

    public void setPhase1_season_nm(String phase1_season_nm) {
        this.phase1_season_nm = phase1_season_nm;
    }

    public String getPhase1_croptype() {
        return phase1_croptype;
    }

    public void setPhase1_croptype(String phase1_croptype) {
        this.phase1_croptype = phase1_croptype;
    }

    public String getPhase1_crop_name() {
        return phase1_crop_name;
    }

    public void setPhase1_crop_name(String phase1_crop_name) {
        this.phase1_crop_name = phase1_crop_name;
    }

    public String getPhase1_dist() {
        return phase1_dist;
    }

    public void setPhase1_dist(String phase1_dist) {
        this.phase1_dist = phase1_dist;
    }

    public String getPhase1_block() {
        return phase1_block;
    }

    public void setPhase1_block(String phase1_block) {
        this.phase1_block = phase1_block;
    }

    public String getPhase1_panchayat() {
        return phase1_panchayat;
    }

    public void setPhase1_panchayat(String phase1_panchayat) {
        this.phase1_panchayat = phase1_panchayat;
    }

    public String getPhase1_panchayat_nm() {
        return phase1_panchayat_nm;
    }

    public void setPhase1_panchayat_nm(String phase1_panchayat_nm) {
        this.phase1_panchayat_nm = phase1_panchayat_nm;
    }

    public String getPhase1_highest_plot_no() {
        return phase1_highest_plot_no;
    }

    public void setPhase1_highest_plot_no(String phase1_highest_plot_no) {
        this.phase1_highest_plot_no = phase1_highest_plot_no;
    }

    public String getPhase1_alloted_khesra_no() {
        return phase1_alloted_khesra_no;
    }

    public void setPhase1_alloted_khesra_no(String phase1_alloted_khesra_no) {
        this.phase1_alloted_khesra_no = phase1_alloted_khesra_no;
    }

    public String getChng_khesra_no() {
        return chng_khesra_no;
    }

    public void setChng_khesra_no(String chng_khesra_no) {
        this.chng_khesra_no = chng_khesra_no;
    }

    public String getChng_khesra_no_nm() {
        return chng_khesra_no_nm;
    }

    public void setChng_khesra_no_nm(String chng_khesra_no_nm) {
        this.chng_khesra_no_nm = chng_khesra_no_nm;
    }

    public String getPhase1_final_khesra_no() {
        return phase1_final_khesra_no;
    }

    public void setPhase1_final_khesra_no(String phase1_final_khesra_no) {
        this.phase1_final_khesra_no = phase1_final_khesra_no;
    }

    public String getPhase1_Entry_date() {
        return phase1_Entry_date;
    }

    public void setPhase1_Entry_date(String phase1_Entry_date) {
        this.phase1_Entry_date = phase1_Entry_date;
    }

    public String getPhase1_farmerName() {
        return phase1_farmerName;
    }

    public void setPhase1_farmerName(String phase1_farmerName) {
        this.phase1_farmerName = phase1_farmerName;
    }

    public String getPhase1_chng_khesra_reason() {
        return phase1_chng_khesra_reason;
    }

    public void setPhase1_chng_khesra_reason(String phase1_chng_khesra_reason) {
        this.phase1_chng_khesra_reason = phase1_chng_khesra_reason;
    }


    public String getPhase1_tentative_cce_date() {
        return phase1_tentative_cce_date;
    }

    public void setPhase1_tentative_cce_date(String phase1_tentative_cce_date) {
        this.phase1_tentative_cce_date = phase1_tentative_cce_date;
    }

    public String getPhase1_village() {
        return phase1_village;
    }

    public void setPhase1_village(String phase1_village) {
        this.phase1_village = phase1_village;
    }

    @Override
    public Object getProperty(int index) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int index, Object value) {

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

    }


}
