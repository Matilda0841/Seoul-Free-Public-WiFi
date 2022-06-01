package com.my.model;

import java.sql.Timestamp;

public class WifiDto {

    private String control_number;
    private String borough;
    private String wifi_name;
    private String address1;
    private String address2;
    private String floor;
    private String type;
    private String install_agency;
    private String service_type;
    private String network_type;
    private String install_year;
    private String in_out;
    private String connect_environment;
    private String coordinate;
    private Timestamp work_date;
    private String distance;


    public WifiDto(String control_number, String borough, String wifi_name, String address1, String address2, String floor, String type, String install_agency, String service_type, String network_type, String install_year, String in_out, String connect_environment, String coordinate, Timestamp work_date, String distance) {
        this.control_number = control_number;
        this.borough = borough;
        this.wifi_name = wifi_name;
        this.address1 = address1;
        this.address2 = address2;
        this.floor = floor;
        this.type = type;
        this.install_agency = install_agency;
        this.service_type = service_type;
        this.network_type = network_type;
        this.install_year = install_year;
        this.in_out = in_out;
        this.connect_environment = connect_environment;
        this.coordinate = coordinate;
        this.work_date = work_date;
        this.distance = distance;
    }

    public WifiDto() {
    }

    public String getControl_number() {
        return control_number;
    }

    public void setControl_number(String control_number) {
        this.control_number = control_number;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getWifi_name() {
        return wifi_name;
    }

    public void setWifi_name(String wifi_name) {
        this.wifi_name = wifi_name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstall_agency() {
        return install_agency;
    }

    public void setInstall_agency(String install_agency) {
        this.install_agency = install_agency;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getNetwork_type() {
        return network_type;
    }

    public void setNetwork_type(String network_type) {
        this.network_type = network_type;
    }

    public String getInstall_year() {
        return install_year;
    }

    public void setInstall_year(String install_year) {
        this.install_year = install_year;
    }

    public String getIn_out() {
        return in_out;
    }

    public void setIn_out(String in_out) {
        this.in_out = in_out;
    }

    public String getConnect_environment() {
        return connect_environment;
    }

    public void setConnect_environment(String connect_environment) {
        this.connect_environment = connect_environment;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Timestamp getWork_date() {
        return work_date;
    }

    public void setWork_date(Timestamp work_date) {
        this.work_date = work_date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "WifiDto{" +
                "control_number='" + control_number + '\'' +
                ", borough='" + borough + '\'' +
                ", wifi_name='" + wifi_name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", floor='" + floor + '\'' +
                ", type='" + type + '\'' +
                ", install_agency='" + install_agency + '\'' +
                ", service_type='" + service_type + '\'' +
                ", network_type='" + network_type + '\'' +
                ", install_year='" + install_year + '\'' +
                ", in_out='" + in_out + '\'' +
                ", connect_environment='" + connect_environment + '\'' +
                ", coordinate='" + coordinate + '\'' +
                ", work_date=" + work_date +
                ", distance='" + distance + '\'' +
                '}';
    }
}
