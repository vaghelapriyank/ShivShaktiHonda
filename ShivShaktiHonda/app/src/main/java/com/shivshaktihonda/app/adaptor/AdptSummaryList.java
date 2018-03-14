package com.shivshaktihonda.app.adaptor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelPriceListData;

import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class AdptSummaryList extends RecyclerView.Adapter<AdptSummaryList.PriceViewHolder> {
    Context context;
    private RecyclerClickListener clickListener;
    ArrayList<ModelPriceListData> priceListData;
    public AdptSummaryList(Context context, ArrayList<ModelPriceListData> priceListData){
        this.context=context;
        this.priceListData=priceListData;
    }

    @Override
    public PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.row_summary,parent,false);
        PriceViewHolder holder=new PriceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PriceViewHolder holder, int position) {
        if (!priceListData.get(position).veriant.equalsIgnoreCase("")){
            holder.txtModelName.setText(priceListData.get(position).model_name+" - "+ priceListData.get(position).veriant);
        }else{
            holder.txtModelName.setText(priceListData.get(position).model_name);
        }
    }

    public void clickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return priceListData.size();
    }

    public class PriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvParent;
        TextView txtModelName;
        public PriceViewHolder(View itemView) {
            super(itemView);
            cvParent=(CardView)itemView.findViewById(R.id.summary_cvParent);
            txtModelName=(TextView) itemView.findViewById(R.id.summary_txtModelName);
            cvParent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener!=null){
                clickListener.onClickEvent(view,getAdapterPosition());
            }
        }
    }
}
