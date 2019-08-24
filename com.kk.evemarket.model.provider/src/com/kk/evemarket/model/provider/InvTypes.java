package com.kk.evemarket.model.provider;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"typeID", "groupID", "typeName", "description", "mass", "volume", "capacity", "portionSize",
        "raceID", "basePrice", "published", "marketGroupID", "iconID", "soundID", "graphicID"})
public class InvTypes {
    private Integer typeID;
    private Integer groupID;
    private String typeName;
    private String description;
    private Float mass;
    private Float volume;
    private Float capacity;
    private Integer portionSize;
    private Integer raceID;
    private Float basePrice;
    private Integer published;
    private Integer marketGroupID;
    private Integer iconID;
    private Integer soundID;
    private Integer graphicID;

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMass() {
        return mass;
    }

    public void setMass(Float mass) {
        this.mass = mass;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Integer getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(Integer portionSize) {
        this.portionSize = portionSize;
    }

    public Integer getRaceID() {
        return raceID;
    }

    public void setRaceID(Integer raceID) {
        this.raceID = raceID;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getMarketGroupID() {
        return marketGroupID;
    }

    public void setMarketGroupID(Integer marketGroupID) {
        this.marketGroupID = marketGroupID;
    }

    public Integer getIconID() {
        return iconID;
    }

    public void setIconID(Integer iconID) {
        this.iconID = iconID;
    }

    public Integer getSoundID() {
        return soundID;
    }

    public void setSoundID(Integer soundID) {
        this.soundID = soundID;
    }

    public Integer getGraphicID() {
        return graphicID;
    }

    public void setGraphicID(Integer graphicID) {
        this.graphicID = graphicID;
    }

    public InvTypes() {

    }
}
