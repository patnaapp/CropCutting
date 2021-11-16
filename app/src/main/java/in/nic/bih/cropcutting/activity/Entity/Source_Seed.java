package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Source_Seed implements KvmSerializable {

    public static Class<Source_Seed>SOURCE_SEED_CLASS= Source_Seed.class;

    private String Seed_id,Seed_Name;

    public Source_Seed(SoapObject sobj)
    {
        this.Seed_id=sobj.getProperty("ID").toString();
        this.Seed_Name=sobj.getProperty("Name").toString();

    }

    public Source_Seed()
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

    public String getSeed_id() {
        return Seed_id;
    }

    public void setSeed_id(String seed_id) {
        Seed_id = seed_id;
    }

    public String getSeed_Name() {
        return Seed_Name;
    }

    public void setSeed_Name(String seed_Name) {
        Seed_Name = seed_Name;
    }
}
