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

}
