package android.wao.com.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public String Email;

    @ColumnInfo(name= "first_name")
    public String firstName;

    @ColumnInfo(name = "Last_Name")
    public String lastName;

    @ColumnInfo( name= "Age")
    public int age;


}
