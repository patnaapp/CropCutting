package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class CropType implements KvmSerializable {
    public static Class<CropType> CROPTYPE_CLASS = CropType.class;
    private String WetherId, WeatherNm, CropId, CropName;

    public CropType(SoapObject sobj) {
        this.WetherId = sobj.getProperty("WheatherID").toString().trim();
        this.WeatherNm = sobj.getProperty("WheatherName").toString().trim();
        this.CropId = sobj.getProperty("CropID").toString().trim();
        this.CropName = sobj.getProperty("CropName").toString().trim();

    }

    public CropType() {

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
}
