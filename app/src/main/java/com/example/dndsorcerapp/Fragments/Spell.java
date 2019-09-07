package com.example.dndsorcerapp.Fragments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Spell {

    @SerializedName("_id")
    @Expose
    private String id;
    private String index;
    private String name;
    @SerializedName("desc")
    @Expose
    private List<String> desc;

    @SerializedName("higher_level")
    @Expose
    private List<String> descHigherLvl;
    private String page;
    private String range;
    private List<String> components;

    private String material;
    private String ritual;
    private String duration;
    private String concentration;

    @SerializedName("casting_time")
    @Expose
    private String castingTime;
    private String level;
    private UrlName school;
    private List<UrlName> classes;

    @SerializedName("subclasses")
    @Expose
    private List<UrlName> subClasses;
    private String url;

    /* This if for the inner url and name  */
    class UrlName {
        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UrlName(String url, String name) {
            this.url = url;
            this.name = name;
        }
    }


    public Spell(String id, String index, String name, List<String> desc,
                 List<String> descHigherLvl, String page, String range, List<String> components,
                 String material, String ritual, String duration, String concentration,
                 String castingTime, String level, UrlName school, List<UrlName> classes,
                 List<UrlName> subClasses, String url) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.desc = desc;
        this.descHigherLvl = descHigherLvl;
        this.page = page;
        this.range = range;
        this.components = components;
        this.material = material;
        this.ritual = ritual;
        this.duration = duration;
        this.concentration = concentration;
        this.castingTime = castingTime;
        this.level = level;
        this.school = school;
        this.classes = classes;
        this.subClasses = subClasses;
        this.url = url;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public List<String> getDescHigherLvl() {
        return descHigherLvl;
    }

    public void setDescHigherLvl(List<String> descHigherLvl) {
        this.descHigherLvl = descHigherLvl;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRitual() {
        return ritual;
    }

    public void setRitual(String ritual) {
        this.ritual = ritual;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public UrlName getSchool() {
        return school;
    }

    public void setSchool(UrlName school) {
        this.school = school;
    }

    public List<UrlName> getClasses() {
        return classes;
    }

    public void setClasses(List<UrlName> classes) {
        this.classes = classes;
    }

    public List<UrlName> getSubClasses() {
        return subClasses;
    }

    public void setSubClasses(List<UrlName> subClasses) {
        this.subClasses = subClasses;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}