package in.nic.bih.cropcutting.activity.Entity;

import android.view.View;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Varities_of_Crop implements KvmSerializable
{
    public static Class<Varities_of_Crop>VARITIES_OF_CROP_CLASS= Varities_of_Crop.class;

    private String Crop_Varities_Id,Crop_Varities_Name;

    public Varities_of_Crop(SoapObject sobj)
    {
        this.Crop_Varities_Id=sobj.getProperty("ID").toString();
        this.Crop_Varities_Name=sobj.getProperty("Name").toString();

    }


    public Varities_of_Crop()
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

    public String getCrop_Varities_Id() {
        return Crop_Varities_Id;
    }

    public void setCrop_Varities_Id(String crop_Varities_Id) {
        Crop_Varities_Id = crop_Varities_Id;
    }

    public String getCrop_Varities_Name() {
        return Crop_Varities_Name;
    }

    public void setCrop_Varities_Name(String crop_Varities_Name) {
        Crop_Varities_Name = crop_Varities_Name;
    }
}
