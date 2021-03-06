package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

/**
 * Created by nicsi on 2/13/2018.
 */
public class Versioninfo implements KvmSerializable {


    public static Class<Versioninfo> Versioninfo_CLASS = Versioninfo.class;
    private String adminTitle;
    private String adminMsg;
    private String updateTile;
    private String updateMsg;
    private String appUrl;
    private String role;
    private String imei;
    private boolean isVerUpdated;
    private boolean isValidDevice;
    private int priority;


    public Versioninfo() {
/*
<IsUpdated>true</IsUpdated>
<Priority>2</Priority>
<AdminMsg/>
<AdminTitle>Alert !</AdminTitle>
<UpdateTile>Update Available !</UpdateTile>
<UpdateMsg>
Please update your aap. New version 1.3 is available. Click on Update button.
</UpdateMsg>
<Role/>
<AppUrl>http://epacs.bih.nic.in/brfsy</AppUrl>
<IsValidDevice>true</IsValidDevice>
<IMEI/>
 */
    }

    public Versioninfo(SoapObject obj) {

        this.isVerUpdated = Boolean.valueOf(obj.getProperty("IsUpdated").toString().toLowerCase());
        this.priority = Integer.valueOf(obj.getProperty("Priority").toString().trim());
        this.adminMsg = obj.getProperty("AdminMsg").toString();
        this.adminTitle = obj.getProperty("AdminTitle").toString();
        this.updateTile = obj.getProperty("UpdateTile").toString();
        this.updateMsg = obj.getProperty("UpdateMsg").toString();
        this.role = obj.getProperty("Role").toString();
        this.appUrl = obj.getProperty("AppUrl").toString();
        this.isValidDevice = Boolean.valueOf(obj.getProperty("IsValidDevice").toString().toLowerCase());
       /// this.imei = obj.getProperty("IMEI").toString();

    }

    public String getAdminMsg() {
        return adminMsg;
    }

    public void setAdminMsg(String adminMsg) {
        this.adminMsg = adminMsg;
    }

    public String getAdminTitle() {
        return adminTitle;
    }

    public void setAdminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
    }

    public String getUpdateTile() {
        return updateTile;
    }

    public void setUpdateTile(String updateTile) {
        this.updateTile = updateTile;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public boolean isVerUpdated() {
        return isVerUpdated;
    }

    public void setVerUpdated(boolean isVerUpdated) {
        this.isVerUpdated = isVerUpdated;
    }

    public boolean isValidDevice() {
        return isValidDevice;
    }

    public void setValidDevice(boolean isValidDevice) {
        this.isValidDevice = isValidDevice;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

}

