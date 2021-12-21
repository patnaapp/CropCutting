package in.nic.bih.cropcutting.activity.DataBase;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import in.nic.bih.cropcutting.activity.Entity.BasicInfo;
import in.nic.bih.cropcutting.activity.Entity.Block_List;
import in.nic.bih.cropcutting.activity.Entity.CropType;
import in.nic.bih.cropcutting.activity.Entity.District_list;
import in.nic.bih.cropcutting.activity.Entity.Financial_Year;
import in.nic.bih.cropcutting.activity.Entity.Forget_password;
import in.nic.bih.cropcutting.activity.Entity.KhesraNo_List;
import in.nic.bih.cropcutting.activity.Entity.LandType;
import in.nic.bih.cropcutting.activity.Entity.Manure_type;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfo;
import in.nic.bih.cropcutting.activity.Entity.NewLandKhesraInfoOnline;
import in.nic.bih.cropcutting.activity.Entity.Panchayat_List;
import in.nic.bih.cropcutting.activity.Entity.ReportView;
import in.nic.bih.cropcutting.activity.Entity.Season_List;
import in.nic.bih.cropcutting.activity.Entity.ShapeCceArea;
import in.nic.bih.cropcutting.activity.Entity.SignUp;
import in.nic.bih.cropcutting.activity.Entity.Source_Seed;
import in.nic.bih.cropcutting.activity.Entity.System_Cutivation;
import in.nic.bih.cropcutting.activity.Entity.TypeList;
import in.nic.bih.cropcutting.activity.Entity.UserLogin;
import in.nic.bih.cropcutting.activity.Entity.Varities_of_Crop;
import in.nic.bih.cropcutting.activity.Entity.Versioninfo;
import in.nic.bih.cropcutting.activity.Entity.Weather_Condition;
import in.nic.bih.cropcutting.activity.Utilities.Utilitties;

public class WebServiceHelper {
    public static final String SERVICENAMESPACE = "http://epacs.bih.nic.in/";
    // public static final String SERVICENAMESPACE = "http://10.133.20.159/";
    public static final String SERVICEURL = "http://epacs.bih.nic.in/CCEWebService.asmx";
    // public static final String SERVICEURL ="http://10.133.20.159/TestService/CCEWebService.asmx";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    public static final String FINANCIALYEAR = "GetFinancialYearList";
    public static final String SEASON = "GetSeasonList";
    public static final String CROPTYPE = "GetCropDetailsNew";
    public static final String RABICROPTYPE = "";
    public static final String SOURCE_OF_SEED_METHOD = "GetSourceOfSeed";
    public static final String CCESHAPE = "GetShapeOfCCEArea";
    public static final String DIST_LIST_METHOD = "getDstList";
    public static final String BLOCK_LIST_METHOD = "getBlockList";
    public static final String PANCHAYAT_LIST_METHOD = "GetUserPanchayatList";
    public static final String CUTIVATION_LIST_METHOD = "GetSystemOfCultivation";
    public static final String WEATHERCONDITION = "GetWhetherCondition";
    public static final String VARITIESOFCROP = "GetVariatiesOfCrops";
    public static final String MANURETYPE = "GetTypeOfManure";
    public static final String LANDTYPE = "GetTypeOfLand";
    public static final String BASIC_DETAILS = "InsertAreaCoverageOfCrop";
    public static final String VIEWREPORT = "GetKHESRA";
    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String SIGNUP_METHOD = "CreateCCEUser";
    public static final String FORGET_PASSWORD = "getCCEUserPassword";
    public static final String INSERT_Land_details = "Insertkhasaradetails";
    public static final String getkhesradetails= "getkhasaradetails";
    public static final String getkhesrano_list = "GetKHASRADETAILSFY";
    public static final String TypeListMethod = "GetCropTypeList";

    static String rest;


    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String value1, String value2) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, methodName);
            request.addProperty(param1, value1);
            request.addProperty(param2, value2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, bindClass.getSimpleName(), bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static UserLogin Login_Farmer(String User_ID, String Pwd) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_METHOD);

            request.addProperty("UserID", User_ID);
            request.addProperty("Password", Pwd);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, UserLogin.Userdetail.getSimpleName(), UserLogin.Userdetail);
            //HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,60000);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL, 60000);
            androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_METHOD, envelope);

            Object result = envelope.getResponse();
            // response = envelope.getResponse().toString();

            if (result != null) {

                return new UserLogin((SoapObject) result);
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

    public static String UploadBasicDetails(BasicInfo data) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, BASIC_DETAILS);
            request.addProperty("Lat", data.getLat());
            request.addProperty("Long_", data.getLongi());
            request.addProperty("Img_OverViewField", data.getImg1());
            request.addProperty("Img_CropCutting", data.getImg2());
            request.addProperty("Img_duringWeighing", data.getImg3());
            Log.d("image", "" + data.getImg1());
            request.addProperty("Img_duringCutting", data.getImg4());
            request.addProperty("Img_duringthreshing", data.getImg5());
            request.addProperty("Season", data.getSeason());
            request.addProperty("Crop", data.getCrop());
            request.addProperty("Type", data.getType_id());
            request.addProperty("District", data.getDist());
            request.addProperty("Block", data.getBlock());
            request.addProperty("Panchayat", data.getPanchayat());
            request.addProperty("VillageName", data.getName_of_selected_village());
            request.addProperty("HighestPlotNoOfPanchayat", data.getHighest_plot_no());
            request.addProperty("SelectedServeyNo", data.getSurvey_no_khesra_no());
            request.addProperty("FarmerName", data.getFarmer_name());
            request.addProperty("OperationalSize", data.getOperation_size_holding());
            request.addProperty("UnitOfOperationalSizeHolding", data.getUnitOperationalSize());
            request.addProperty("AreaCoverageOf", data.getArea_covered_crop());
            request.addProperty("UnitOfOareaCoverageOfCrop", data.getUnitareaCoverage());
            request.addProperty("SystemOfCultivation", data.getSystem_of_cutivation());
            request.addProperty("CropVaraities", data.getVarities_of_crop());
            request.addProperty("IrrigationSource", data.getIrrigation_source());
            request.addProperty("TypeOfManure", data.getType_of_manure());
            request.addProperty("QunatityOfUsedManure", data.getQuantity_of_used_manure());
            request.addProperty("SourceOfSeed", data.getSource_of_seed());
            request.addProperty("QuantityOfUsedSeed", data.getQuantity_of_used_seed());
            request.addProperty("WheatherCondition", data.getWeather_condition_during_crop_season());
            request.addProperty("ExtendOfDamage", data.getExtend_of_damage());
            request.addProperty("AnyStress", data.getRemarks());
            request.addProperty("RandomNoAllotedByDCO", data.getRandom_no_alloted_by_dso());
            request.addProperty("ShapeOfCCE", data.getShape_of_cce_area());
            request.addProperty("LengthOfField", data.getLenghth_of_field());
            request.addProperty("BreathOfField", data.getBreath_of_field());
            request.addProperty("TypeOfLand", data.getType_of_land());
            request.addProperty("DateOfCutting", data.getDate_of_cutting());
            request.addProperty("GreenWeight", data.getGreen_weight());
            if (data.getCrop().equals("19")||data.getCrop().equals("20"))
            {
                request.addProperty("DryWeight", data.getDryFiber_weight());
            }
            else {
                request.addProperty("DryWeight", data.getDry_weight());
            }

            request.addProperty("SpervisorName", data.getSupervisor_nm());
            request.addProperty("SpervisorMobNo", data.getSupervisor_mob());
            request.addProperty("SpervisorDesignation", data.getSupervisor_designation());
            request.addProperty("Remarks", data.getRemarks1());
            request.addProperty("noOfBaal", data.getNo_of_baal());
            request.addProperty("weightOfBaal", data.getWeight_of_baal());
            request.addProperty("GreenWeightOfDana", data.getGreen_weight_of_dana());
            request.addProperty("DryWeightOfDana", data.getDry_weight_of_dana());
            request.addProperty("orderOfExperiment", data.getOrder_of_experiment());
            request.addProperty("IsInspectionDone", data.getInspectionDone());
            request.addProperty("EntryBy", data.getUserid());
            request.addProperty("UnitOfUsedSeed", data.getUnit_seed_in());
            request.addProperty("ObserverPresent", data.getObserver_present());
            request.addProperty("SuperwiserPresent", data.getSupervisor_present());
            request.addProperty("ObserberName", data.getObserver_name());
            request.addProperty("ObserberMob", data.getObserve_mobile_no());
            request.addProperty("ObserverDesignation", data.getObserver_designation());
            request.addProperty("AgricultureYear", data.getAgri_year());

            request.addProperty("NoOfBundle", data.getTotal_nos_of_bundle());
            request.addProperty("WeightOfBundle1", data.getBundle1());
            request.addProperty("WeightOfBundle2", data.getBundle2());
            request.addProperty("WeightOfBundle3", data.getBundle3());
            request.addProperty("WeightOfBundle4", data.getBundle4());
            request.addProperty("WeightOfBundle5", data.getBundle5());
            request.addProperty("WeightOfBundle6", data.getBundle6());
            request.addProperty("WeightOfBundle7", data.getBundle7());
            request.addProperty("WeightOfBundle8", data.getBundle8());

            if (data.getCrop().equals("17"))
            {
                request.addProperty("WeightOfProductBreaking", data.getBaal_wt_plucking());
                request.addProperty("WeightOfProductBeating", data.getBaal_wt_threshing());
            }
            else if (data.getCrop().equals("18")||data.getCrop().equals("14"))
            {
                request.addProperty("WeightOfProductBreaking", data.getPod_wt_plucking());
                request.addProperty("WeightOfProductBeating", data.getPod_wt_threshing());
            }
            else
            {
                request.addProperty("WeightOfProductBreaking", "");
                request.addProperty("WeightOfProductBeating", "");
            }

            request.addProperty("EntryDate", data.getEntryDate());
            request.addProperty("TypeOfShape", data.getField_type_id());
            request.addProperty("NoOfLine", data.getNoof_Lines());
            request.addProperty("NoOfSelectedLine", data.getLength_of_selected_lines());


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,BasicInfo.Basicdetail.getSimpleName(), BasicInfo.Basicdetail);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + BASIC_DETAILS, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Financial_Year> getFinancialYear() {

        SoapObject res1;
        res1 = getServerData(FINANCIALYEAR, Financial_Year.Financial_Year_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Financial_Year> fieldList = new ArrayList<Financial_Year>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Financial_Year sm = new Financial_Year(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }


    public static ArrayList<TypeList> gettypeList() {

        SoapObject res1;
        res1 = getServerData(TypeListMethod, TypeList.TYPE_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<TypeList> fieldList = new ArrayList<TypeList>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    TypeList sm = new TypeList(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<Season_List> getSeason() {

        SoapObject res1;
        res1 = getServerData(SEASON, Season_List.SEASON_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Season_List> fieldList = new ArrayList<Season_List>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Season_List sm = new Season_List(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<ShapeCceArea> getCceShape() {

        SoapObject res1;
        res1 = getServerData(CCESHAPE, ShapeCceArea.SHAPECCE_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<ShapeCceArea> fieldList = new ArrayList<ShapeCceArea>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ShapeCceArea sm = new ShapeCceArea(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    /*public static ArrayList<CropType> getCroptype() {

        SoapObject res1;
        res1=getServerData(CROPTYPE,CropType.CROPTYPE_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();
        ArrayList<CropType> fieldList = new ArrayList<CropType>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    CropType sm = new CropType(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }



        return fieldList;
    }*/
    public static ArrayList<CropType> getCroptype(String str1) {


        SoapObject request = new SoapObject(SERVICENAMESPACE, CROPTYPE);
        request.addProperty("WheatherID", str1);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, CropType.CROPTYPE_CLASS.getSimpleName(), CropType.CROPTYPE_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + CROPTYPE, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<CropType> pvmArrayList = new ArrayList<CropType>();
        if (TotalProperty > 0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        CropType state = new CropType(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }

    public static ArrayList<Source_Seed> getSOurceOfSeed() {

        SoapObject res1;
        res1 = getServerData(SOURCE_OF_SEED_METHOD, Source_Seed.SOURCE_SEED_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Source_Seed> fieldList = new ArrayList<Source_Seed>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Source_Seed sm = new Source_Seed(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }


    public static ArrayList<District_list> getDistrictData() {

        SoapObject res1;
        res1 = getServerData(DIST_LIST_METHOD, District_list.District_Name_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<District_list> fieldList = new ArrayList<District_list>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    District_list sm = new District_list(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<Block_List> getBlockName(String code) {

        SoapObject res1;
        res1 = getServerData(BLOCK_LIST_METHOD, Block_List.Block_Name_CLASS, "DistCode", code);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Block_List> block_lists = new ArrayList<Block_List>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Block_List sm = new Block_List(final_object);
                    block_lists.add(sm);
                }
            } else
                return block_lists;
        }


        return block_lists;
    }


    public static ArrayList<Panchayat_List> getPanchayatName(String code) {

        SoapObject res1;
        res1 = getServerData(PANCHAYAT_LIST_METHOD, Panchayat_List.Panchayat_Name_CLASS, "userid", code);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Panchayat_List> fieldList = new ArrayList<Panchayat_List>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Panchayat_List sm = new Panchayat_List(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<System_Cutivation> getCutivationtData() {

        SoapObject res1;
        res1 = getServerData(CUTIVATION_LIST_METHOD, System_Cutivation.System_Cutivation_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<System_Cutivation> fieldList = new ArrayList<System_Cutivation>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    System_Cutivation sm = new System_Cutivation(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<Weather_Condition> getWeatherData() {

        SoapObject res1;
        res1 = getServerData(WEATHERCONDITION, Weather_Condition.WETHER_CONDITION_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Weather_Condition> fieldList = new ArrayList<Weather_Condition>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Weather_Condition sm = new Weather_Condition(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<Varities_of_Crop> getVaritiesOfCrop() {

        SoapObject res1;
        res1 = getServerData(VARITIESOFCROP, Varities_of_Crop.VARITIES_OF_CROP_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Varities_of_Crop> fieldList = new ArrayList<Varities_of_Crop>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Varities_of_Crop sm = new Varities_of_Crop(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<Manure_type> getManureType() {

        SoapObject res1;
        res1 = getServerData(MANURETYPE, Manure_type.MANURE_TYPE_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<Manure_type> fieldList = new ArrayList<Manure_type>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Manure_type sm = new Manure_type(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }

    public static ArrayList<LandType> getLandType() {

        SoapObject res1;
        res1 = getServerData(LANDTYPE, LandType.LANDTYPE_CLASS);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<LandType> fieldList = new ArrayList<LandType>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    LandType sm = new LandType(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }


        return fieldList;
    }


    public static ArrayList<ReportView> getViewReport(String userid, String season,String agriyr ) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, VIEWREPORT);

        request.addProperty("userID", userid);
        request.addProperty("Season", season);
        request.addProperty("AgricultureYear", agriyr);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, ReportView.ReportView_CLASS.getSimpleName(), ReportView.ReportView_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + VIEWREPORT, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<ReportView> pvmArrayList = new ArrayList<ReportView>();
        if (TotalProperty > 0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        ReportView view = new ReportView(final_object);
                        pvmArrayList.add(view);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }

    public static Versioninfo CheckVersion(String version) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, APPVERSION_METHOD);

        request.addProperty("Ver", version);
        Versioninfo versioninfo;
        SoapObject res1;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, Versioninfo.Versioninfo_CLASS.getSimpleName(), Versioninfo.Versioninfo_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + APPVERSION_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            // Object property = res1.getProperty(0);
            SoapObject final_object = (SoapObject) res1.getProperty(0);
            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;

    }

    public static String SIgnUpDetails(SignUp data) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    SIGNUP_METHOD);
            request.addProperty("DistCode", data.getDist_code());
            request.addProperty("BlockCode", data.getBlock_code());
            request.addProperty("Address", data.getAddress());
            request.addProperty("Name", data.getName());
            request.addProperty("FatherName", data.getFname());
            request.addProperty("Mobile", data.getMobile());
            request.addProperty("EntryBy", data.getEntryBy());
            request.addProperty("password", data.getPassword());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, SignUp.UserSignUp.getSimpleName(), SignUp.UserSignUp);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + SIGNUP_METHOD, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String ForgetPassword(String data) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, FORGET_PASSWORD);
            request.addProperty("Mobile", data);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, Forget_password.FORGET_PASSWORD_CLASS.getSimpleName(), Forget_password.FORGET_PASSWORD_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + FORGET_PASSWORD, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static SoapObject getServerData(String methodName, Class bindClass, String param, String value) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, methodName);
            request.addProperty(param, value);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, bindClass.getSimpleName(), bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static SoapObject getServerData(String methodName, Class bindClass) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, bindClass.getSimpleName(), bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }


    public static String UploadLandDetailsPhase1(NewLandKhesraInfo data, String devicename, String App_ver) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, INSERT_Land_details);
        request.addProperty("FarmerName", data.get_Farmer_Name());
        request.addProperty("District", data.get_phase1_dist());
        request.addProperty("Block", data.get_phase1_block());
        request.addProperty("Panchayat", data.get_phase1_panchayat());
        request.addProperty("Village", data.get_revenue_village());
        request.addProperty("Season", data.get_phase1_season());
        request.addProperty("Crop", data.get_phase1_croptype());
        request.addProperty("Type", data.get_Type_List_Id());

        request.addProperty("AllocatedKhasaraNo", data.get_phase1_alloted_khesra_no());
        request.addProperty("FinalKhasaraNo", data.get_phase1_final_khesra_no());
        request.addProperty("ReasonforKhasaraNochange", data.get_chng_Remarks());
        request.addProperty("HighestPlotNo", data.get_phase1_highest_plot_no());
        request.addProperty("FieldOverviewing", data.getFieldImg());
        request.addProperty("NajariMap", data.getNazriNkasha());
        request.addProperty("FinalPloting", data.getFinalSelectedField());
        request.addProperty("AppVersion", App_ver);
        request.addProperty("DeviceId", devicename);
        request.addProperty("AgricultureYear", data.get_phse1_agri_yr());
        request.addProperty("SurveyDate", data.get_phase1_Entry_date());
        request.addProperty("TentativeDateCCE", data.get_tentative_cce_date());

        if (data.get_chng_khesra_no().equalsIgnoreCase("1")) {
            request.addProperty("FOLat", data.get_phase1_lat());
            request.addProperty("FOLan", data.get_phase1_longi());
            request.addProperty("NPLat", data.get_phase1_lat1());
            request.addProperty("NPLan", data.get_phase1_longi1());
            request.addProperty("FPLat", data.get_phase1_lat2());
            request.addProperty("FPLan", data.get_phase1_longi2());

        } else if (data.get_chng_khesra_no().equalsIgnoreCase("2")) {

            request.addProperty("FOLat", "0");
            request.addProperty("FOLan", "0");
            request.addProperty("NPLat", "0");
            request.addProperty("NPLan", "0");
            request.addProperty("FPLat", data.get_phase1_lat2());
            request.addProperty("FPLan", data.get_phase1_longi2());
        }
        request.addProperty("CreatedBy", data.get_phase1_userid());
        request.addProperty("Createddate", Utilitties.getCurrentDate());

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + INSERT_Land_details, envelope);

            rest = envelope.getResponse().toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            //return "0";
            return null;
        }
        return rest;

    }


    public static ArrayList<NewLandKhesraInfoOnline> GetDataLIst1(String panchayatcode, String seasoncode, String cropcode, String userid) {
        SoapObject res1;

        SoapObject request = new SoapObject(SERVICENAMESPACE, getkhesradetails);
        Log.d("yttusydi", "" + panchayatcode);

        request.addProperty("Panchayat", panchayatcode);
        request.addProperty("Season", seasoncode);
        request.addProperty("Crop", cropcode);
        request.addProperty("CreatedBy", userid);
        // request.addProperty("userid",Role);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(SERVICENAMESPACE, NewLandKhesraInfoOnline.NewKhesraInfoOnline.getSimpleName(), NewLandKhesraInfoOnline.NewKhesraInfoOnline);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);

        ArrayList<NewLandKhesraInfoOnline> dataList = new ArrayList<NewLandKhesraInfoOnline>();

        try {
            androidHttpTransport.call(SERVICENAMESPACE + getkhesradetails, envelope);
            res1 = (SoapObject) envelope.getResponse();

            //res1=getServerData(SYNCHRONIZE_METHOD,GetDataListClass.GetData_CLASS,"_ULBID","_WardNo", UlbCode,wardid);

            int TotalProperty = 0;
            if (res1 != null)
                TotalProperty = res1.getPropertyCount();


            for (int i = 0; i < TotalProperty; i++) {
                if (res1.getProperty(i) != null) {
                    Object property = res1.getProperty(i);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        NewLandKhesraInfoOnline sm = new NewLandKhesraInfoOnline(final_object);
                        dataList.add(sm);
                    }
                } else
                    return dataList;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataList;
    }


    public static ArrayList<KhesraNo_List> getKhesraList(String userid, String agri_yr) {


        SoapObject request = new SoapObject(SERVICENAMESPACE, getkhesrano_list);
        request.addProperty("CreatedBy", userid);
        request.addProperty("FinancialYear", agri_yr);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, KhesraNo_List.KhesraNo_List_CLASS.getSimpleName(), KhesraNo_List.KhesraNo_List_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + getkhesrano_list, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<KhesraNo_List> pvmArrayList = new ArrayList<KhesraNo_List>();
        if (TotalProperty > 0) {

            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        KhesraNo_List state = new KhesraNo_List(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }


}
