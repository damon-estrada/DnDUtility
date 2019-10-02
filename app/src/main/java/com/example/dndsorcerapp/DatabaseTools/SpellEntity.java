package com.example.dndsorcerapp.DatabaseTools;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * SpellEntity Model Object.
 *
 * <P>All attributes needed to display a spell for DnD 5e.
 *
 * @author Damon Estrada
 * @version 1.0
 * @since   2019-09-07
 */
@Entity(tableName = "spell_table")
public class SpellEntity implements Parcelable {

    // identify each entry with a unique id
    @PrimaryKey(autoGenerate = true)
    private int entry_id;

    private int priority;
    private String slug;
    private String name;
    private String desc;

    @SerializedName("higher_level")
    @Expose
    private String descHigherLvl;
    private String page;
    private String range;
    private String components;

    private String material;
    private String ritual;
    private String duration;
    private String concentration;

    @SerializedName("casting_time")
    @Expose
    private String castingTime;
    private String level;
    @SerializedName("level_int")
    @Expose
    private String levelInt;

    private String school;
    @SerializedName("dnd_class")
    @Expose
    private String dndClass;
    private String archetype;

    private String circles;
    private String document__slug;
    private String document__title;
    private String document__license_url;

    /**
     * Default constructor to create an instance of a spell.
     * @param priority
     * @param slug
     * @param name
     * @param desc
     * @param descHigherLvl
     * @param page
     * @param range
     * @param components
     * @param material
     * @param ritual
     * @param duration
     * @param concentration
     * @param castingTime
     * @param level
     * @param levelInt
     * @param school
     * @param dndClass
     * @param archetype
     * @param circles
     * @param document__slug
     * @param document__title
     * @param document__license_url
     */
    public SpellEntity(int priority, String slug, String name, String desc,
                       String descHigherLvl, String page, String range, String components,
                       String material, String ritual, String duration, String concentration,
                       String castingTime, String level, String levelInt, String school,
                       String dndClass, String archetype, String circles, String document__slug,
                       String document__title, String document__license_url) {
        this.priority = priority;
        this.slug = slug;
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
        this.levelInt = levelInt;
        this.school = school;
        this.dndClass = dndClass;
        this.archetype = archetype;
        this.circles = circles;
        this.document__slug = document__slug;
        this.document__title = document__title;
        this.document__license_url = document__license_url;
    }

    @Ignore
    public SpellEntity() {
    }

    /**
     * This constructor is used to take the Parcel and populate our object with its values (READ).
     * @param parcel
     */
    @Ignore
    private SpellEntity(Parcel parcel) {
        this.priority = parcel.readInt();
        this.slug = parcel.readString();
        this.name = parcel.readString();
        this.desc = parcel.readString();
        this.descHigherLvl = parcel.readString();
        this.page = parcel.readString();
        this.range = parcel.readString();
        this.components = parcel.readString();
        this.material = parcel.readString();
        this.ritual = parcel.readString();
        this.duration = parcel.readString();
        this.concentration = parcel.readString();
        this.castingTime = parcel.readString();
        this.level = parcel.readString();
        this.levelInt = parcel.readString();
        this.school = parcel.readString();
        this.dndClass = parcel.readString();
        this.archetype = parcel.readString();
        this.circles = parcel.readString();
        this.document__slug = parcel.readString();
        this.document__title = parcel.readString();
        this.document__license_url = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This will write toe the parcel when needed for object reinit.
     * @param dest The destination to write
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(priority);
        dest.writeString(slug);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(descHigherLvl);
        dest.writeString(page);
        dest.writeString(range);
        dest.writeString(components);
        dest.writeString(material);
        dest.writeString(ritual);
        dest.writeString(duration);
        dest.writeString(concentration);
        dest.writeString(castingTime);
        dest.writeString(level);
        dest.writeString(levelInt);
        dest.writeString(school);
        dest.writeString(dndClass);
        dest.writeString(archetype);
        dest.writeString(circles);
        dest.writeString(document__slug);
        dest.writeString(document__title);
        dest.writeString(document__license_url);
    }

    public static final Parcelable.Creator<SpellEntity> CREATOR = new Parcelable.Creator<SpellEntity>() {
        @Override
        public SpellEntity createFromParcel(Parcel source) {
            return new SpellEntity(source);
        }

        @Override
        public SpellEntity[] newArray(int size) {
            return new SpellEntity[size];
        }
    };

    /**
     * Room will use this setter to set id on the object.
     * @param entry_id assigned id.
     */
    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public int getEntry_id() {
        return entry_id;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescHigherLvl() {
        return descHigherLvl;
    }

    public String getPage() {
        return page;
    }

    public String getRange() {
        return range;
    }

    public String getComponents() {
        return components;
    }

    public String getMaterial() {
        return material;
    }

    public String getRitual() {
        return ritual;
    }

    public String getDuration() {
        return duration;
    }

    public String getConcentration() {
        return concentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public String getLevel() {
        return level;
    }

    public String getSchool() {
        return school;
    }

    public String getSlug() {
        return slug;
    }

    public String getLevelInt() {
        return levelInt;
    }

    public String getDndClass() {
        return dndClass;
    }

    public String getArchetype() {
        return archetype;
    }

    public String getCircles() {
        return circles;
    }

    public String getDocument__slug() {
        return document__slug;
    }

    public String getDocument__title() {
        return document__title;
    }

    public String getDocument__license_url() {
        return document__license_url;
    }
}
