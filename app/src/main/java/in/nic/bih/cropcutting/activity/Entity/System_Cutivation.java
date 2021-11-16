package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class System_Cutivation implements KvmSerializable
{
    public static Class<System_Cutivation>System_Cutivation_CLASS=System_Cutivation.class;

    private String cutivation_id,cutivation_name;

    public System_Cutivation(SoapObject sobj)
    {
        this.cutivation_id=sobj.getProperty("ID").toString();
        this.cutivation_name=sobj.getProperty("Name").toString();

    }

    public System_Cutivation()
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

    public String getCutivation_id() {
        return cutivation_id;
    }

    public void setCutivation_id(String cutivation_id) {
        this.cutivation_id = cutivation_id;
    }

    public String getCutivation_name() {
        return cutivation_name;
    }

    public void setCutivation_name(String cutivation_name) {
        this.cutivation_name = cutivation_name;
    }
}
