package com.kk.evemarket.viewpartprovider.toolbar;

public class RegionIdName implements Comparable<RegionIdName> {
    private int region_id;
    private String name;

    public RegionIdName() {

    }

    public RegionIdName(int rid, String rname) {
        region_id = rid;
        name = rname;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(RegionIdName o) {

        return this.toString().compareTo(o.toString());
    }
}
