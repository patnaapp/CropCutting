package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Manure_type implements KvmSerializable {

    public static Class<Manure_type>MANURE_TYPE_CLASS=Manure_type.class;

    private String Manure_type_id,Manure_type_name;

    public Manure_type(SoapObject sobj)
    {
        this.Manure_type_id=sobj.getProperty("ID").toString();
        this.Manure_type_name=sobj.getProperty("Name").toString();

    }
    public Manure_type()
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

    public String getManure_type_id() {
        return Manure_type_id;
    }

    public void setManure_type_id(String manure_type_id) {
        Manure_type_id = manure_type_id;
    }

    public String getManure_type_name() {
        return Manure_type_name;
    }

    public void setManure_type_name(String manure_type_name) {
        Manure_type_name = manure_type_name;
    }
}
