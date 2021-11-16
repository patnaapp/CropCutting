package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class ShapeCceArea implements KvmSerializable {

    public static Class<ShapeCceArea>SHAPECCE_CLASS=ShapeCceArea.class;

    private String CceAreaId,CceAreaNm;

    public  ShapeCceArea(SoapObject sobj)
    {
        this.CceAreaId=sobj.getProperty("ID").toString();
        this.CceAreaNm=sobj.getProperty("Name").toString();
    }
    public ShapeCceArea()
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

    public String getCceAreaId() {
        return CceAreaId;
    }

    public void setCceAreaId(String cceAreaId) {
        CceAreaId = cceAreaId;
    }

    public String getCceAreaNm() {
        return CceAreaNm;
    }

    public void setCceAreaNm(String cceAreaNm) {
        CceAreaNm = cceAreaNm;
    }
}
