package android.wao.com.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wao.com.Database.WaoDatabase;
import android.wao.com.R;
import android.wao.com.activities.MainActivity;
import android.wao.com.activities.StoresListActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "RecyclerViewAdapter";
    private static final int REQUEST_CALL = 1;
    Button imageCall ;

    WaoDatabase db = MainActivity.getDb();
    Dialog myDialog;
    private int positionClicked;

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> websites = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<String> websites, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.websites = websites;
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
        Log.d(TAG, "onBindViewHolder: " + mImages.get(position));
        int tempInt = getResId(mImages.get(position), R.drawable.class);
        holder.imageView.setImageResource(tempInt);


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

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private Dialog fillDialogData(Dialog myDialog) {
        imageCall = myDialog.findViewById(R.id.image_call);
        TextView storeName = myDialog.findViewById(R.id.popupStorename);
        TextView Monday = myDialog.findViewById(R.id.MondayTextView);
        TextView TuesDay = myDialog.findViewById(R.id.TheusDayTextView);
        TextView WednesDay = myDialog.findViewById(R.id.WednesDayTextView);
        TextView ThursDay = myDialog.findViewById(R.id.ThursDayTextView);
        TextView Friday = myDialog.findViewById(R.id.FridayTextView);
        TextView Saturday = myDialog.findViewById(R.id.SaturDayTextView);
        TextView Sunday =myDialog.findViewById(R.id.SunDayTextView);
        Button websiteButton = myDialog.findViewById(R.id.websiteButton);

        storeName.setText(mImageNames.get(positionClicked));
        String openHours = db.shopDAO().getAll().get(positionClicked).open;
        Monday.setText(openHours);
        TuesDay.setText(openHours);
        WednesDay.setText(openHours);
        ThursDay.setText(openHours);
        Friday.setText(openHours);
        Saturday.setText(openHours);
        Sunday.setText(openHours);

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = websites.get(positionClicked);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });



        return  myDialog;
    }

    private void makePhoneCall(){
        String number = db.shopDAO().getAll().get(positionClicked).PhoneNumber;
        if(number.trim().length()>0){
                if(ContextCompat.checkSelfPermission(myDialog.getContext(),
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions( myDialog.getOwnerActivity(),new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                }else{
                    String dial = "tel:" + number;
                    mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantresults) {
        if(requestCode == REQUEST_CALL ){
            if(grantresults.length> 0 && grantresults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this.mContext,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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
