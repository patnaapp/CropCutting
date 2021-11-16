package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class ReportView implements KvmSerializable {
    public static Class<ReportView> ReportView_CLASS = ReportView.class;

    private String userid;
    private String DistNm,BlockNm,PanchayatNm;
    private String Farmername,SurveyNo,RandomNo,CropName;

    public ReportView(SoapObject res1) {

        this.SurveyNo=res1.getProperty("KHESRA").toString();
        if (res1.getProperty("KHESRA").toString().equalsIgnoreCase("anyType{}")){
            this.SurveyNo="";
        }
        else {
            this.SurveyNo = res1.getProperty("KHESRA").toString();
        }

        if (res1.getProperty("OrderOfExperiment").toString().equalsIgnoreCase("anyType{}")){
            this.RandomNo="";
        }
        else {
            this.RandomNo = res1.getProperty("OrderOfExperiment").toString();
        }
        this.DistNm=res1.getProperty("DistrictName").toString();
        this.BlockNm=res1.getProperty("BlockName").toString();

        if (res1.getProperty("PanchayatName").toString().equalsIgnoreCase("anyType{}")){
            this.PanchayatNm="";
        }
        else {
            this.PanchayatNm = res1.getProperty("PanchayatName").toString();
        }

        if (res1.getProperty("FarmerName").toString().equalsIgnoreCase("anyType{}")){
            this.Farmername="";
        }
        else {
            this.Farmername = res1.getProperty("FarmerName").toString();
        }

        if (res1.getProperty("Name").toString().equalsIgnoreCase("anyType{}")){
            this.CropName="";
        }
        else {
            this.CropName = res1.getProperty("Name").toString();
        }
    }

    public ReportView()
    {

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

    public String getDistNm() {
        return DistNm;
    }

    public void setDistNm(String distNm) {
        DistNm = distNm;
    }

    public String getBlockNm() {
        return BlockNm;
    }

    public void setBlockNm(String blockNm) {
        BlockNm = blockNm;
    }

    public String getPanchayatNm() {
        return PanchayatNm;
    }

    public void setPanchayatNm(String panchayatNm) {
        PanchayatNm = panchayatNm;
    }

    public String getFarmername() {
        return Farmername;
    }

    public void setFarmername(String farmername) {
        Farmername = farmername;
    }

    public String getSurveyNo() {
        return SurveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        SurveyNo = surveyNo;
    }

    public String getRandomNo() {
        return RandomNo;
    }

    public void setRandomNo(String randomNo) {
        RandomNo = randomNo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }
}
