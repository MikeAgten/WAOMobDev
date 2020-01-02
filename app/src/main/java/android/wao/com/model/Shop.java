package android.wao.com.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shop {
    @PrimaryKey( autoGenerate = true)
    public int uid;


    @ColumnInfo(name= "name")
    public String shopName;

    @ColumnInfo(name="visits")
    public int visitCounter;

    @ColumnInfo(name="type_business")
    public String typeBusiness;

    @ColumnInfo(name="latitute")
    public double latitute;

    @ColumnInfo(name= "longitute")
    public double longitute;


    @ColumnInfo(name="city")
    public String city;

    @ColumnInfo (name="imageurl")
    public String imageUrl;

    @ColumnInfo ( name= "website")
    public String website;

    @ColumnInfo (name = "open")
    public String open;

    @ColumnInfo (name = "phonenumber")
    public String PhoneNumber;



}
