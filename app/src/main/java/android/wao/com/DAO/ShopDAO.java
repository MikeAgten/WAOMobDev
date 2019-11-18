package android.wao.com.DAO;

import android.wao.com.model.Shop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShopDAO {
     @Query("Select * from shop")
     List<Shop> getAll();

     @Query("Select * from  shop where name Is (:name)")
     Shop getShopByName(String name);

     @Query("Select * from shop where type_business Is (:business) ")
     List<Shop> loadAllTypeBusiness(String business);

     @Query("Select visits from shop where name Is(:name)")
     int getAmountOfTimesVisited(String name);

    @Query("UPDATE shop SET visits=:visit WHERE name = :name")
    void update(int visit, String name);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Shop shop);

    @Delete
    void delete(Shop shop);
}

