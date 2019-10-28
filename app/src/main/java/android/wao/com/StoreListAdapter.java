package android.wao.com;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.wao.com.data.StoresContract;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreViewHolder>{

    private Context mContext;

    private Cursor mCursor;

    public StoreListAdapter(Context context, Cursor cursor){
        this.mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return; //cursor kan niet naar positie navigeren
        }
        String name = mCursor.getString(mCursor.getColumnIndex(StoresContract.StoreEntry.COLUMN_NAME_NAME));
        String address = mCursor.getString(mCursor.getColumnIndex(StoresContract.StoreEntry.COLUMN_NAME_ADDRESS));
        String phoneNumber = mCursor.getString(mCursor.getColumnIndex(StoresContract.StoreEntry.COLUMN_NAME_PHONENUMBER));
        String website = mCursor.getString(mCursor.getColumnIndex(StoresContract.StoreEntry.COLUMN_NAME_WEBSITE));
        String open = mCursor.getString(mCursor.getColumnIndex(StoresContract.StoreEntry.COLUMN_NAME_OPEN));


    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        public StoreViewHolder(View storeView){
            super(storeView);
        }
    }
}
