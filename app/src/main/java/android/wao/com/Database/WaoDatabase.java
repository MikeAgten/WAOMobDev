package android.wao.com.Database;

import android.wao.com.DAO.ShopDAO;
import android.wao.com.model.Shop;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Shop.class},version =8)
public abstract class WaoDatabase extends RoomDatabase {
    public abstract ShopDAO shopDAO();
}
