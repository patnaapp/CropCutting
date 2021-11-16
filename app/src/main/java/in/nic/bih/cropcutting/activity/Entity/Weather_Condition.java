package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Weather_Condition implements KvmSerializable {

    public static Class<Weather_Condition>WETHER_CONDITION_CLASS=Weather_Condition.class;

    private String Weather_Id,Weather_Name;

    public Weather_Condition(SoapObject sobj)
    {
        this.Weather_Id=sobj.getProperty("ID").toString();
        this.Weather_Name=sobj.getProperty("Name").toString();

    }

    public Weather_Condition()
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

    public String getWeather_Id() {
        return Weather_Id;
    }

    public void setWeather_Id(String weather_Id) {
        Weather_Id = weather_Id;
    }

    public String getWeather_Name() {
        return Weather_Name;
    }

    public void setWeather_Name(String weather_Name) {
        Weather_Name = weather_Name;
    }
}
