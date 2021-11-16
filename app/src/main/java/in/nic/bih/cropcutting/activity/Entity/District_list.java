package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class District_list implements KvmSerializable {
    public static Class<District_list> District_Name_CLASS = District_list.class;

    private String Distt_code, Distt_name;

    public District_list(SoapObject sobj) {
        this.Distt_code = sobj.getProperty("District_Code").toString();
        this.Distt_name = sobj.getProperty("District_Name").toString();

    }

    public District_list() {

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

    public String getDistt_code() {
        return Distt_code;
    }

    public void setDistt_code(String distt_code) {
        Distt_code = distt_code;
    }

    public String getDistt_name() {
        return Distt_name;
    }

    public void setDistt_name(String distt_name) {
        Distt_name = distt_name;
    }
}
