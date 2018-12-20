package sercandevops.com.wifimanagerapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SercanAdapter extends RecyclerView.Adapter<SercanAdapter.MyViewHolder> {

        ArrayList<WifiBilgi> mDataList;
        LayoutInflater layoutInflater;


        public SercanAdapter(Context context, ArrayList<WifiBilgi> data){

            layoutInflater  = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mDataList = data;

        }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = layoutInflater.inflate(R.layout.list_item,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        WifiBilgi tiklanilanWifi = mDataList.get(i);
            myViewHolder.setData(tiklanilanWifi,i);


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

            TextView mWAdi,mWBilgi,mWSinyal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mWAdi = itemView.findViewById(R.id.mWAdi);
            mWBilgi = itemView.findViewById(R.id.mWbilgi);
            mWSinyal = itemView.findViewById(R.id.mWSinyal);


        }

        public void setData(WifiBilgi tiklanilanWifi,int position){

            this.mWAdi.setText(tiklanilanWifi.getAdi());
            this.mWBilgi.setText(tiklanilanWifi.getMac());
            this.mWSinyal.setText(""+tiklanilanWifi.getSinyal());
        }
    }//MYHOLDER CLASS
}
