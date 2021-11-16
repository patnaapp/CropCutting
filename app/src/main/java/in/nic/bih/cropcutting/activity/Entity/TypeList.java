package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class TypeList implements KvmSerializable {
    public static Class<TypeList> TYPE_CLASS = TypeList.class;
    private String id, Name, CropID;

    public TypeList(SoapObject sobj) {
        this.id = sobj.getProperty("id").toString().trim();
        this.Name = sobj.getProperty("Name").toString().trim();
        this.CropID = sobj.getProperty("CropID").toString().trim();

    }

    public TypeList() {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCropID() {
        return CropID;
    }

    public void setCropID(String cropID) {
        CropID = cropID;
    }
}
