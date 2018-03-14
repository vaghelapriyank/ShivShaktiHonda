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
import com.shivshaktihonda.app.pojo.ModelSummaryData;

import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class AdptSpSummaryList extends RecyclerView.Adapter<AdptSpSummaryList.PriceViewHolder> {
    Context context;
    private RecyclerClickListener clickListener;
    ArrayList<ModelSummaryData> summaryListData;
    public AdptSpSummaryList(Context context, ArrayList<ModelSummaryData> summaryListData){
        this.context=context;
        this.summaryListData=summaryListData;
    }

    @Override
    public PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.row_sp_price_list,parent,false);
        PriceViewHolder holder=new PriceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PriceViewHolder holder, int position) {
        if (!summaryListData.get(position).model_type_name.equalsIgnoreCase("")){
            holder.txtModelName.setText(summaryListData.get(position).model_name+" - "+ summaryListData.get(position).model_type_name);
        }else{
            holder.txtModelName.setText(summaryListData.get(position).model_name);
        }
    }

    public void clickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return summaryListData.size();
    }

    public class PriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvParent;
        TextView txtModelName;
        public PriceViewHolder(View itemView) {
            super(itemView);
            cvParent=(CardView)itemView.findViewById(R.id.spPriceList_cvParent);
            txtModelName=(TextView) itemView.findViewById(R.id.spPriceList_txtModelName);
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
