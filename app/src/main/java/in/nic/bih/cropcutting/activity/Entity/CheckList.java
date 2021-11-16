package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class CheckList implements KvmSerializable {

    private static final long serialVersionUID = 1L;

    public static Class<CheckList> CheckList_CLASS = CheckList.class;

    private String Checklist_Id = "";
    private String Checklist_Name = "";


    public String getChecklist_Id() {
        return Checklist_Id;
    }

    public void setChecklist_Id(String checklist_Id) {
        Checklist_Id = checklist_Id;
    }

    public String getChecklist_Name() {
        return Checklist_Name;
    }

    public void setChecklist_Name(String checklist_Name) {
        Checklist_Name = checklist_Name;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
