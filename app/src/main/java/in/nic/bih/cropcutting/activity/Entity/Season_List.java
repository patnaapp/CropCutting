package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Season_List implements KvmSerializable
{
    public static Class<Season_List>SEASON_CLASS= Season_List.class;

    private boolean isAuthenticate;
    private String Season_Id;
    private String Season_Name;
    private String Flag;

    public Season_List(SoapObject sobj)
    {

        this.isAuthenticate= Boolean.parseBoolean(sobj.getProperty("isAuthenticated").toString());
        this.Season_Id=sobj.getProperty("id").toString();
        this.Season_Name=sobj.getProperty("Name").toString();
        this.Flag = sobj.getProperty("flag").toString();

    }


    public Season_List()
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

    public String getSeason_Id() {
        return Season_Id;
    }

    public void setSeason_Id(String season_Id) {
        Season_Id = season_Id;
    }

    public String getSeason_Name() {
        return Season_Name;
    }

    public void setSeason_Name(String season_Name) {
        Season_Name = season_Name;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public boolean isAuthenticate() {
        return isAuthenticate;
    }

    public void setAuthenticate(boolean authenticate) {
        isAuthenticate = authenticate;
    }
}
