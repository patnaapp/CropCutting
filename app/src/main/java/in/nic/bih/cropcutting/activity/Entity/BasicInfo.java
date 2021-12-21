package in.nic.bih.cropcutting.activity.Entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class BasicInfo implements KvmSerializable {
    public static Class<BasicInfo> Basicdetail = BasicInfo.class;
    private String id;
    private String userid;
    private String lat;
    private String longi;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String Agri_year;
    private String Agri_year_nm;
    private String season;
    private String crop;
    private String crop_name;
    private String dist;
    private String block;
    private String panchayat;
    private String Name_of_selected_village;
    private String highest_plot_no;
    private String survey_no_khesra_no;
    private String farmer_name;
    private String UnitOperationalSize;
    private String operation_size_holding;
    private String UnitareaCoverage;
    private String area_covered_crop;
    private String system_of_cutivation;
    private String varities_of_crop;
    private String irrigation_source;
    private String type_of_manure;
    private String quantity_of_used_manure;
    private String source_of_seed;
    private String unit_seed_in;
    private String quantity_of_used_seed;
    private String weather_condition_during_crop_season;
    private String extend_of_damage;
    private String remarks;
    private String random_no_alloted_by_dso;
    private String Order_of_experiment;
    private String shape_of_cce_area;
    private String lenghth_of_field;
    private String breath_of_field;
    private String type_of_land;
    private String type_of_land_Name;
    private String date_of_cutting;
    private String green_weight;
    private String dry_weight;
    private String no_of_baal;
    private String weight_of_baal;
    private String green_weight_of_dana;
    private String dry_weight_of_dana;
    private String observer_present;
    private String supervisor_present;
    private String supervisor_nm;
    private String supervisor_mob;
    private String supervisor_designation;
    private String observer_name;
    private String observe_mobile_no;
    private String observer_designation;
    private String remarks1;
    private String EntryDate;
    private String inspectionDone;
    private String weatherconditionname;
    private String sourceofseed_name;
    private String typeofmanure_name;
    private String systemofcultivation_name;
    private String varitiesofcrop_name;
    private String sub_varitiesofcrop;
    private String sub_varitiesofcrop_name;
    private String type_id;
    private String type_name;

    private String pod_wt_plucking;
    private String dryFiber_weight;
    private String noof_Lines;
    private String length_of_selected_lines;
    private String field_type_id;
    private String field_type_name;

    public String getPod_wt_plucking() {
        return pod_wt_plucking;
    }

    public void setPod_wt_plucking(String pod_wt_plucking) {
        this.pod_wt_plucking = pod_wt_plucking;
    }

    public String getPod_wt_threshing() {
        return pod_wt_threshing;
    }

    public void setPod_wt_threshing(String pod_wt_threshing) {
        this.pod_wt_threshing = pod_wt_threshing;
    }

    public String getBaal_wt_plucking() {
        return baal_wt_plucking;
    }

    public void setBaal_wt_plucking(String baal_wt_plucking) {
        this.baal_wt_plucking = baal_wt_plucking;
    }

    public String getBaal_wt_threshing() {
        return baal_wt_threshing;
    }

    public void setBaal_wt_threshing(String baal_wt_threshing) {
        this.baal_wt_threshing = baal_wt_threshing;
    }

    public String getTotal_nos_of_bundle() {
        return total_nos_of_bundle;
    }

    public void setTotal_nos_of_bundle(String total_nos_of_bundle) {
        this.total_nos_of_bundle = total_nos_of_bundle;
    }

    public String getBundle1() {
        return bundle1;
    }

    public void setBundle1(String bundle1) {
        this.bundle1 = bundle1;
    }

    public String getBundle2() {
        return bundle2;
    }

    public void setBundle2(String bundle2) {
        this.bundle2 = bundle2;
    }

    public String getBundle3() {
        return bundle3;
    }

    public void setBundle3(String bundle3) {
        this.bundle3 = bundle3;
    }

    public String getBundle4() {
        return bundle4;
    }

    public void setBundle4(String bundle4) {
        this.bundle4 = bundle4;
    }

    public String getBundle5() {
        return bundle5;
    }

    public void setBundle5(String bundle5) {
        this.bundle5 = bundle5;
    }

    public String getBundle6() {
        return bundle6;
    }

    public void setBundle6(String bundle6) {
        this.bundle6 = bundle6;
    }

    public String getBundle7() {
        return bundle7;
    }

    public void setBundle7(String bundle7) {
        this.bundle7 = bundle7;
    }

    public String getBundle8() {
        return bundle8;
    }

    public void setBundle8(String bundle8) {
        this.bundle8 = bundle8;
    }

    public String getTotal_bundle_weight() {
        return total_bundle_weight;
    }

    public void setTotal_bundle_weight(String total_bundle_weight) {
        this.total_bundle_weight = total_bundle_weight;
    }

    private String pod_wt_threshing;
    private String baal_wt_plucking;
    private String baal_wt_threshing;
    private String total_nos_of_bundle;
    private String bundle1;
    private String bundle2;
    private String bundle3;
    private String bundle4;
    private String bundle5;
    private String bundle6;
    private String bundle7;
    private String bundle8;
    private String total_bundle_weight;


    public BasicInfo() {

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

    public String getAgri_year() {
        return Agri_year;
    }

    public void setAgri_year(String agri_year) {
        Agri_year = agri_year;
    }

    public String getAgri_year_nm() {
        return Agri_year_nm;
    }

    public void setAgri_year_nm(String agri_year_nm) {
        Agri_year_nm = agri_year_nm;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCrop() {
        return crop;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getName_of_selected_village() {
        return Name_of_selected_village;
    }

    public void setName_of_selected_village(String name_of_selected_village) {
        Name_of_selected_village = name_of_selected_village;
    }

    public String getHighest_plot_no() {
        return highest_plot_no;
    }

    public void setHighest_plot_no(String highest_plot_no) {
        this.highest_plot_no = highest_plot_no;
    }

    public String getSurvey_no_khesra_no() {
        return survey_no_khesra_no;
    }

    public void setSurvey_no_khesra_no(String survey_no_khesra_no) {
        this.survey_no_khesra_no = survey_no_khesra_no;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getOperation_size_holding() {
        return operation_size_holding;
    }

    public void setOperation_size_holding(String operation_size_holding) {
        this.operation_size_holding = operation_size_holding;
    }

    public String getArea_covered_crop() {
        return area_covered_crop;
    }

    public void setArea_covered_crop(String area_covered_crop) {
        this.area_covered_crop = area_covered_crop;
    }

    public String getSystem_of_cutivation() {
        return system_of_cutivation;
    }

    public void setSystem_of_cutivation(String system_of_cutivation) {
        this.system_of_cutivation = system_of_cutivation;
    }

    public String getVarities_of_crop() {
        return varities_of_crop;
    }

    public void setVarities_of_crop(String varities_of_crop) {
        this.varities_of_crop = varities_of_crop;
    }

    public String getSub_varitiesofcrop() {
        return sub_varitiesofcrop;
    }

    public void setSub_varitiesofcrop(String sub_varitiesofcrop) {
        this.sub_varitiesofcrop = sub_varitiesofcrop;
    }

    public String getSub_varitiesofcrop_name() {
        return sub_varitiesofcrop_name;
    }

    public void setSub_varitiesofcrop_name(String sub_varitiesofcrop_name) {
        this.sub_varitiesofcrop_name = sub_varitiesofcrop_name;
    }

    public String getIrrigation_source() {
        return irrigation_source;
    }

    public void setIrrigation_source(String irrigation_source) {
        this.irrigation_source = irrigation_source;
    }

    public String getType_of_manure() {
        return type_of_manure;
    }

    public void setType_of_manure(String type_of_manure) {
        this.type_of_manure = type_of_manure;
    }

    public String getSource_of_seed() {
        return source_of_seed;
    }

    public void setSource_of_seed(String source_of_seed) {
        this.source_of_seed = source_of_seed;
    }

    public String getQuantity_of_used_manure() {
        return quantity_of_used_manure;
    }

    public void setQuantity_of_used_manure(String quantity_of_used_manure) {
        this.quantity_of_used_manure = quantity_of_used_manure;
    }

    public String getQuantity_of_used_seed() {
        return quantity_of_used_seed;
    }

    public void setQuantity_of_used_seed(String quantity_of_used_seed) {
        this.quantity_of_used_seed = quantity_of_used_seed;
    }

    public String getWeather_condition_during_crop_season() {
        return weather_condition_during_crop_season;
    }

    public void setWeather_condition_during_crop_season(String weather_condition_during_crop_season) {
        this.weather_condition_during_crop_season = weather_condition_during_crop_season;
    }

    public String getExtend_of_damage() {
        return extend_of_damage;
    }

    public void setExtend_of_damage(String extend_of_damage) {
        this.extend_of_damage = extend_of_damage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRandom_no_alloted_by_dso() {
        return random_no_alloted_by_dso;
    }

    public void setRandom_no_alloted_by_dso(String random_no_alloted_by_dso) {
        this.random_no_alloted_by_dso = random_no_alloted_by_dso;
    }

    public String getOrder_of_experiment() {
        return Order_of_experiment;
    }

    public void setOrder_of_experiment(String order_of_experiment) {
        Order_of_experiment = order_of_experiment;
    }

    public String getShape_of_cce_area() {
        return shape_of_cce_area;
    }

    public void setShape_of_cce_area(String shape_of_cce_area) {
        this.shape_of_cce_area = shape_of_cce_area;
    }

    public String getLenghth_of_field() {
        return lenghth_of_field;
    }

    public void setLenghth_of_field(String lenghth_of_field) {
        this.lenghth_of_field = lenghth_of_field;
    }

    public String getBreath_of_field() {
        return breath_of_field;
    }

    public void setBreath_of_field(String breath_of_field) {
        this.breath_of_field = breath_of_field;
    }

    public String getType_of_land() {
        return type_of_land;
    }

    public void setType_of_land(String type_of_land) {
        this.type_of_land = type_of_land;
    }

    public String getDate_of_cutting() {
        return date_of_cutting;
    }

    public void setDate_of_cutting(String date_of_cutting) {
        this.date_of_cutting = date_of_cutting;
    }

    public String getGreen_weight() {
        return green_weight;
    }

    public void setGreen_weight(String green_weight) {
        this.green_weight = green_weight;
    }

    public String getDry_weight() {
        return dry_weight;
    }

    public void setDry_weight(String dry_weight) {
        this.dry_weight = dry_weight;
    }

    public String getNo_of_baal() {
        return no_of_baal;
    }

    public void setNo_of_baal(String no_of_baal) {
        this.no_of_baal = no_of_baal;
    }

    public String getWeight_of_baal() {
        return weight_of_baal;
    }

    public void setWeight_of_baal(String weight_of_baal) {
        this.weight_of_baal = weight_of_baal;
    }

    public String getGreen_weight_of_dana() {
        return green_weight_of_dana;
    }

    public void setGreen_weight_of_dana(String green_weight_of_dana) {
        this.green_weight_of_dana = green_weight_of_dana;
    }

    public String getDry_weight_of_dana() {
        return dry_weight_of_dana;
    }

    public void setDry_weight_of_dana(String dry_weight_of_dana) {
        this.dry_weight_of_dana = dry_weight_of_dana;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getObserver_name() {
        return observer_name;
    }

    public void setObserver_name(String observer_name) {
        this.observer_name = observer_name;
    }

    public String getObserve_mobile_no() {
        return observe_mobile_no;
    }

    public void setObserve_mobile_no(String observe_mobile_no) {
        this.observe_mobile_no = observe_mobile_no;
    }

    public String getObserver_designation() {
        return observer_designation;
    }

    public void setObserver_designation(String observer_designation) {
        this.observer_designation = observer_designation;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getInspectionDone() {
        return inspectionDone;
    }

    public void setInspectionDone(String inspectionDone) {
        this.inspectionDone = inspectionDone;
    }


    public String getSupervisor_nm() {
        return supervisor_nm;
    }

    public void setSupervisor_nm(String supervisor_nm) {
        this.supervisor_nm = supervisor_nm;
    }

    public String getSupervisor_mob() {
        return supervisor_mob;
    }

    public void setSupervisor_mob(String supervisor_mob) {
        this.supervisor_mob = supervisor_mob;
    }

    public String getSupervisor_designation() {
        return supervisor_designation;
    }

    public void setSupervisor_designation(String supervisor_designation) {
        this.supervisor_designation = supervisor_designation;
    }

    public String getUnit_seed_in() {
        return unit_seed_in;
    }

    public void setUnit_seed_in(String unit_seed_in) {
        this.unit_seed_in = unit_seed_in;
    }

    public String getObserver_present() {
        return observer_present;
    }

    public void setObserver_present(String observer_present) {
        this.observer_present = observer_present;
    }

    public String getSupervisor_present() {
        return supervisor_present;
    }

    public void setSupervisor_present(String supervisor_present) {
        this.supervisor_present = supervisor_present;
    }

    public String getUnitOperationalSize() {
        return UnitOperationalSize;
    }

    public void setUnitOperationalSize(String unitOperationalSize) {
        UnitOperationalSize = unitOperationalSize;
    }

    public String getUnitareaCoverage() {
        return UnitareaCoverage;
    }

    public void setUnitareaCoverage(String unitareaCoverage) {
        UnitareaCoverage = unitareaCoverage;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getType_of_land_Name() {
        return type_of_land_Name;
    }

    public void setType_of_land_Name(String type_of_land_Name) {
        this.type_of_land_Name = type_of_land_Name;
    }

    public String getWeatherconditionname() {
        return weatherconditionname;
    }

    public void setWeatherconditionname(String weatherconditionname) {
        this.weatherconditionname = weatherconditionname;
    }

    public String getSourceofseed_name() {
        return sourceofseed_name;
    }

    public void setSourceofseed_name(String sourceofseed_name) {
        this.sourceofseed_name = sourceofseed_name;
    }

    public String getTypeofmanure_name() {
        return typeofmanure_name;
    }

    public void setTypeofmanure_name(String typeofmanure_name) {
        this.typeofmanure_name = typeofmanure_name;
    }

    public String getSystemofcultivation_name() {
        return systemofcultivation_name;
    }

    public void setSystemofcultivation_name(String systemofcultivation_name) {
        this.systemofcultivation_name = systemofcultivation_name;
    }

    public String getVaritiesofcrop_name() {
        return varitiesofcrop_name;
    }

    public void setVaritiesofcrop_name(String varitiesofcrop_name) {
        this.varitiesofcrop_name = varitiesofcrop_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getDryFiber_weight() {
        return dryFiber_weight;
    }

    public void setDryFiber_weight(String dryFiber_weight) {
        this.dryFiber_weight = dryFiber_weight;
    }

    public String getNoof_Lines() {
        return noof_Lines;
    }

    public void setNoof_Lines(String noof_Lines) {
        this.noof_Lines = noof_Lines;
    }

    public String getLength_of_selected_lines() {
        return length_of_selected_lines;
    }

    public void setLength_of_selected_lines(String length_of_selected_lines) {
        this.length_of_selected_lines = length_of_selected_lines;
    }

    public String getField_type_id() {
        return field_type_id;
    }

    public void setField_type_id(String field_type_id) {
        this.field_type_id = field_type_id;
    }

    public String getField_type_name() {
        return field_type_name;
    }

    public void setField_type_name(String field_type_name) {
        this.field_type_name = field_type_name;
    }
}
