package android.wao.com.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wao.com.Database.WaoDatabase;
import android.wao.com.R;
import android.wao.com.activities.MainActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    WaoDatabase db = MainActivity.getDb();
    Dialog myDialog;
    private int positionClicked;

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.imageName.setText(mImageNames.get(position));

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.stores_popup_layout);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionClicked = position;
                myDialog = fillDialogData(myDialog);
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));
                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show(); // laat even zien waar je op klikte
                int visits =  db.shopDAO().getAmountOfTimesVisited(mImageNames.get(position));
                Log.d(TAG,"Voordat er een winkel gekozen is deze zoveel keer opgezocht: "+visits);

               // Toast.makeText(mContext, visits, Toast.LENGTH_SHORT).show(); // laat even zien waar je op klikte
                db.shopDAO().update(visits+1,mImageNames.get(position));
                Log.d(TAG,"Nadat er een winkel gekozen is: "+ db.shopDAO().getShopByName(mImageNames.get(position)).visitCounter);

                myDialog.show();
            }
        });
    }

    private Dialog fillDialogData(Dialog myDialog) {
        TextView storeName = myDialog.findViewById(R.id.popupStorename);
        TextView Monday = myDialog.findViewById(R.id.MondayTextView);
        TextView ThuesDay = myDialog.findViewById(R.id.TheusDayTextView);
        TextView WednesDay = myDialog.findViewById(R.id.WednesDayTextView);
        TextView ThursDay = myDialog.findViewById(R.id.ThursDayTextView);
        TextView Friday = myDialog.findViewById(R.id.FridayTextView);

        storeName.setText(mImageNames.get(positionClicked));
        Monday.setText("database uren?");
        ThuesDay.setText("database uren?");
        WednesDay.setText("database uren?");
        ThursDay.setText("database uren?");
        Friday.setText("database uren?");

        return  myDialog;
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout store_popup;
        ImageView imageView;
        TextView imageName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.logoImage);
            imageName = itemView.findViewById(R.id.storeNameTextview);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            store_popup = (LinearLayout) itemView.findViewById(R.id.popup_layout);
    }

    }
}
