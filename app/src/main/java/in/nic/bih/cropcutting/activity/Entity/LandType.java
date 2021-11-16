package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class LandType implements KvmSerializable
{
    public static Class<LandType>LANDTYPE_CLASS=LandType.class;
    private String Land_Id,Land_type;

    public LandType(SoapObject sobj)
    {
        this.Land_Id=sobj.getProperty("ID").toString();
        this.Land_type=sobj.getProperty("Name").toString();

    }
    public LandType()
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

    public String getLand_Id() {
        return Land_Id;
    }

    public void setLand_Id(String land_Id) {
        Land_Id = land_Id;
    }

    public String getLand_type() {
        return Land_type;
    }

    public void setLand_type(String land_type) {
        Land_type = land_type;
    }
}
