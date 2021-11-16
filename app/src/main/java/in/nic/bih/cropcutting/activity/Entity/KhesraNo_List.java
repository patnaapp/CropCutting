package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class KhesraNo_List implements KvmSerializable {
    public static Class<KhesraNo_List> KhesraNo_List_CLASS = KhesraNo_List.class;
    private String KhesraId, KhesraNm, agriyr, khesra_season, khesra_crop, panid,farmerName,village_Name,highest_PlotNo;

    public KhesraNo_List(SoapObject sobj) {
       // this.KhesraId = sobj.getProperty("WheatherID").toString().trim();
        this.farmerName = sobj.getProperty("FarmerName").toString().trim();
        this.panid = sobj.getProperty("panchayat").toString().trim();
        this.khesra_season = sobj.getProperty("season").toString().trim();
        this.khesra_crop = sobj.getProperty("Crop").toString().trim();
        this.KhesraNm = sobj.getProperty("FinalKhasaraNo").toString().trim();
        this.agriyr = sobj.getProperty("AgricultureYear").toString().trim();
        this.village_Name = sobj.getProperty("Village").toString().trim();
        this.highest_PlotNo = sobj.getProperty("HighestPlotNo").toString().trim();



    }

    public KhesraNo_List() {

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

    public String getKhesraId() {
        return KhesraId;
    }

    public void setKhesraId(String khesraId) {
        KhesraId = khesraId;
    }

    public String getKhesraNm() {
        return KhesraNm;
    }

    public void setKhesraNm(String khesraNm) {
        KhesraNm = khesraNm;
    }

    public String getAgriyr() {
        return agriyr;
    }

    public void setAgriyr(String agriyr) {
        this.agriyr = agriyr;
    }

    public String getKhesra_season() {
        return khesra_season;
    }

    public void setKhesra_season(String khesra_season) {
        this.khesra_season = khesra_season;
    }

    public String getKhesra_crop() {
        return khesra_crop;
    }

    public void setKhesra_crop(String khesra_crop) {
        this.khesra_crop = khesra_crop;
    }

    public String getPanid() {
        return panid;
    }

    public void setPanid(String panid) {
        this.panid = panid;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getVillage_Name() {
        return village_Name;
    }

    public void setVillage_Name(String village_Name) {
        this.village_Name = village_Name;
    }

    public String getHighest_PlotNo() {
        return highest_PlotNo;
    }

    public void setHighest_PlotNo(String highest_PlotNo) {
        this.highest_PlotNo = highest_PlotNo;
    }
}
