package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Block_List implements KvmSerializable {
    public static Class<Block_List> Block_Name_CLASS = Block_List.class;

    private String Blk_Code, Blk_Name, dist_code;

    public Block_List(SoapObject sobj) {
        this.Blk_Code = sobj.getProperty("Block_Code").toString();
        this.Blk_Name = sobj.getProperty("Block_Name").toString();

    }

    public Block_List() {

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

    public String getBlk_Code() {
        return Blk_Code;
    }

    public void setBlk_Code(String blk_Code) {
        Blk_Code = blk_Code;
    }

    public String getBlk_Name() {
        return Blk_Name;
    }

    public void setBlk_Name(String blk_Name) {
        Blk_Name = blk_Name;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }
}
