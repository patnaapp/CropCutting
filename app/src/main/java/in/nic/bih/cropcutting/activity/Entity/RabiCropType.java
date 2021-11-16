package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class RabiCropType implements KvmSerializable {

    public static Class<RabiCropType>RABICROPTYPE_CLASS=RabiCropType.class;

    private String  WetherId,WeatherNm,CropId,CropName;

    public RabiCropType(SoapObject sobj)
    {
        this.WetherId=sobj.getProperty("WheatherID").toString();
        this.WeatherNm=sobj.getProperty("WheatherName").toString();
        this.CropId=sobj.getProperty("CropID").toString();
        this.CropName=sobj.getProperty("CropName").toString();

    }
    public RabiCropType()
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

    public String getWetherId() {
        return WetherId;
    }

    public void setWetherId(String wetherId) {
        WetherId = wetherId;
    }

    public String getWeatherNm() {
        return WeatherNm;
    }

    public void setWeatherNm(String weatherNm) {
        WeatherNm = weatherNm;
    }

    public String getCropId() {
        return CropId;
    }

    public void setCropId(String cropId) {
        CropId = cropId;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }
}
