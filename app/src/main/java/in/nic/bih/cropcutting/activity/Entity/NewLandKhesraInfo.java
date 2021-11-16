package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class NewLandKhesraInfo implements KvmSerializable
{
    public static Class<NewLandKhesraInfo> Basicdetail = NewLandKhesraInfo.class;
    private String _phase1_id;
    private String _phase1_userid;
    private String _phase1_lat;
    private String _phase1_longi;
    private String _phase1_lat1;
    private String _phase1_longi1;
    private String _phase1_lat2;
    private String _phase1_longi2;
    private String nazriNkasha;
    private String FieldImg;
    private String FinalSelectedField;
    private String _phse1_agri_yr;
    private String _phse1_agri_yr_nm;
    private String _phase1_season;
    private String _phase1_season_nm;
    private String _phase1_croptype;
    private String _phase1_crop_name;
    private String _phase1_dist;
    private String _phase1_block;
    private String _phase1_panchayat;
    private String _phase1_panchayat_nm;

    private String _phase1_highest_plot_no;
    private String _phase1_alloted_khesra_no;
    private String _chng_khesra_no;
    private String _chng_khesra_no_nm;
    private String _phase1_final_khesra_no;
    private String _phase1_Entry_date;
    private String _phase1_flag;
    private String _Farmer_Name;
    private String _chng_Remarks;
    private String _revenue_village;
    private String _tentative_cce_date;
    private String _Type_List_Id;
    private String _Type_List_NM;



    public NewLandKhesraInfo()
    {

    }

    public String get_phase1_flag() {
        return _phase1_flag;
    }

    public void set_phase1_flag(String _phase1_flag) {
        this._phase1_flag = _phase1_flag;
    }

    public String get_phase1_lat2() {
        return _phase1_lat2;
    }

    public void set_phase1_lat2(String _phase1_lat2) {
        this._phase1_lat2 = _phase1_lat2;
    }

    public String get_chng_khesra_no_nm() {
        return _chng_khesra_no_nm;
    }

    public void set_chng_khesra_no_nm(String _chng_khesra_no_nm) {
        this._chng_khesra_no_nm = _chng_khesra_no_nm;
    }

    public String get_phase1_longi2() {
        return _phase1_longi2;
    }

    public void set_phase1_longi2(String _phase1_longi2) {
        this._phase1_longi2 = _phase1_longi2;
    }


    public String get_phase1_id() {
        return _phase1_id;
    }

    public void set_phase1_id(String _phase1_id) {
        this._phase1_id = _phase1_id;
    }

    public String get_phase1_userid() {
        return _phase1_userid;
    }

    public void set_phase1_userid(String _phase1_userid) {
        this._phase1_userid = _phase1_userid;
    }

    public String get_phase1_lat() {
        return _phase1_lat;
    }

    public void set_phase1_lat(String _phase1_lat) {
        this._phase1_lat = _phase1_lat;
    }

    public String get_phase1_longi() {
        return _phase1_longi;
    }

    public void set_phase1_longi(String _phase1_longi) {
        this._phase1_longi = _phase1_longi;
    }

    public String get_phase1_lat1() {
        return _phase1_lat1;
    }

    public void set_phase1_lat1(String _phase1_lat1) {
        this._phase1_lat1 = _phase1_lat1;
    }

    public String get_phase1_longi1() {
        return _phase1_longi1;
    }

    public void set_phase1_longi1(String _phase1_longi1) {
        this._phase1_longi1 = _phase1_longi1;
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

    public String get_phse1_agri_yr() {
        return _phse1_agri_yr;
    }

    public void set_phse1_agri_yr(String _phse1_agri_yr) {
        this._phse1_agri_yr = _phse1_agri_yr;
    }

    public String get_phse1_agri_yr_nm() {
        return _phse1_agri_yr_nm;
    }

    public void set_phse1_agri_yr_nm(String _phse1_agri_yr_nm) {
        this._phse1_agri_yr_nm = _phse1_agri_yr_nm;
    }

    public String get_phase1_season() {
        return _phase1_season;
    }

    public void set_phase1_season(String _phase1_season) {
        this._phase1_season = _phase1_season;
    }

    public String get_phase1_season_nm() {
        return _phase1_season_nm;
    }

    public void set_phase1_season_nm(String _phase1_season_nm) {
        this._phase1_season_nm = _phase1_season_nm;
    }

    public String get_phase1_croptype() {
        return _phase1_croptype;
    }

    public void set_phase1_croptype(String _phase1_croptype) {
        this._phase1_croptype = _phase1_croptype;
    }

    public String get_phase1_crop_name() {
        return _phase1_crop_name;
    }

    public void set_phase1_crop_name(String _phase1_crop_name) {
        this._phase1_crop_name = _phase1_crop_name;
    }

    public String get_phase1_dist() {
        return _phase1_dist;
    }

    public void set_phase1_dist(String _phase1_dist) {
        this._phase1_dist = _phase1_dist;
    }

    public String get_phase1_block() {
        return _phase1_block;
    }

    public void set_phase1_block(String _phase1_block) {
        this._phase1_block = _phase1_block;
    }

    public String get_phase1_panchayat() {
        return _phase1_panchayat;
    }

    public void set_phase1_panchayat(String _phase1_panchayat) {
        this._phase1_panchayat = _phase1_panchayat;
    }

    public String get_phase1_panchayat_nm() {
        return _phase1_panchayat_nm;
    }

    public void set_phase1_panchayat_nm(String _phase1_panchayat_nm) {
        this._phase1_panchayat_nm = _phase1_panchayat_nm;
    }

    public String get_phase1_highest_plot_no() {
        return _phase1_highest_plot_no;
    }

    public void set_phase1_highest_plot_no(String _phase1_highest_plot_no) {
        this._phase1_highest_plot_no = _phase1_highest_plot_no;
    }

    public String get_phase1_alloted_khesra_no() {
        return _phase1_alloted_khesra_no;
    }

    public void set_phase1_alloted_khesra_no(String _phase1_alloted_khesra_no) {
        this._phase1_alloted_khesra_no = _phase1_alloted_khesra_no;
    }

    public String get_chng_khesra_no() {
        return _chng_khesra_no;
    }

    public void set_chng_khesra_no(String _chng_khesra_no) {
        this._chng_khesra_no = _chng_khesra_no;
    }

    public String get_phase1_final_khesra_no() {
        return _phase1_final_khesra_no;
    }

    public void set_phase1_final_khesra_no(String _phase1_final_khesra_no) {
        this._phase1_final_khesra_no = _phase1_final_khesra_no;
    }

    public String get_phase1_Entry_date() {
        return _phase1_Entry_date;
    }

    public void set_phase1_Entry_date(String _phase1_Entry_date) {
        this._phase1_Entry_date = _phase1_Entry_date;
    }

    public String get_Farmer_Name() {
        return _Farmer_Name;
    }

    public void set_Farmer_Name(String _Farmer_Name) {
        this._Farmer_Name = _Farmer_Name;
    }

    public String get_chng_Remarks() {
        return _chng_Remarks;
    }

    public void set_chng_Remarks(String _chng_Remarks) {
        this._chng_Remarks = _chng_Remarks;
    }

    public String get_revenue_village() {
        return _revenue_village;
    }

    public void set_revenue_village(String _revenue_village) {
        this._revenue_village = _revenue_village;
    }

    public String get_tentative_cce_date() {
        return _tentative_cce_date;
    }

    public void set_tentative_cce_date(String _tentative_cce_date) {
        this._tentative_cce_date = _tentative_cce_date;
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


    public String get_Type_List_Id() {
        return _Type_List_Id;
    }

    public void set_Type_List_Id(String _Type_List_Id) {
        this._Type_List_Id = _Type_List_Id;
    }

    public String get_Type_List_NM() {
        return _Type_List_NM;
    }

    public void set_Type_List_NM(String _Type_List_NM) {
        this._Type_List_NM = _Type_List_NM;
    }
}
