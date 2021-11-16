package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class UserLogin implements KvmSerializable
{
    public static Class<UserLogin> Userdetail = UserLogin.class;

    private boolean isAthenticate;
    private String userId,UserName,password;
    private String role,distcode,distname,blkcode,blkname,imei;
    private String id;
    private String mobile;

    public UserLogin(SoapObject res1) {


        this.userId=res1.getProperty("UserID").toString();
        this.UserName=res1.getProperty("UserName").toString();
        this.password=res1.getProperty("Password").toString();
        this.role=res1.getProperty("Role").toString();
        this.distcode=res1.getProperty("DistCode").toString();
        this.distname=res1.getProperty("DistName").toString();
        this.blkcode=res1.getProperty("BlockCode").toString();
        this.blkname=res1.getProperty("BlockName").toString();
        this.imei=res1.getProperty("IMEI").toString();
        this.isAthenticate= Boolean.parseBoolean(res1.getProperty("isAuthenticated").toString());

    }

    public UserLogin()
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isAthenticate()
    {
        return isAthenticate;
    }

    public void setAthenticate(boolean athenticate)
    {
        isAthenticate = athenticate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDistcode() {
        return distcode;
    }

    public void setDistcode(String distcode) {
        this.distcode = distcode;
    }

    public String getDistname() {
        return distname;
    }

    public void setDistname(String distname) {
        this.distname = distname;
    }

    public String getBlkcode() {
        return blkcode;
    }

    public void setBlkcode(String blkcode) {
        this.blkcode = blkcode;
    }

    public String getBlkname() {
        return blkname;
    }

    public void setBlkname(String blkname) {
        this.blkname = blkname;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
