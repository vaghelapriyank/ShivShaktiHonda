package com.shivshaktihonda.app.adaptor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelDeliveryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class AdptSpDeliveryList extends RecyclerView.Adapter<AdptSpDeliveryList.InquiryViewHolder> {
    Context context;
    private RecyclerClickListener clickListener;
    ArrayList<ModelDeliveryData> deliveryData;
    SimpleDateFormat toSdf,fromSdf;
    public AdptSpDeliveryList(Context context,ArrayList<ModelDeliveryData> deliveryData){
        this.context=context;
        this.deliveryData=deliveryData;
        toSdf=new SimpleDateFormat("dd MMM yyyy");
        fromSdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }


    @Override
    public InquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.row_delivery_list,parent,false);
        InquiryViewHolder holder=new InquiryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InquiryViewHolder holder, int position) {
        holder.txtDeliveryId.setText(deliveryData.get(position).delivery_id+" - "+ deliveryData.get(position).model_name);

        holder.txtCustName.setText(deliveryData.get(position).first_name+" "+deliveryData.get(position).last_name);
        if ((boolean)CM.getSp(context, CV.IS_ADMIN,false)){
            holder.llSPParent.setVisibility(View.VISIBLE);
            holder.txtSalesPersonName.setText(deliveryData.get(position).salesperson_name);
        }else{
            holder.llSPParent.setVisibility(View.GONE);
//            holder.txtCustName.setText(deliveryData.get(position).first_name+" "+deliveryData.get(position).last_name);
        }
//        holder.txtDeliveryDate.setText(deliveryData.get(position).total);

        try {
            holder.txtDeliveryDate.setText(toSdf.format(fromSdf.parse(deliveryData.get(position).created_on)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void clickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return deliveryData.size();
    }

    public class InquiryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvParent;
        LinearLayout llSPParent;
        TextView txtDeliveryId,txtModelName,txtCustName,txtSalesPersonName,txtDeliveryDate;

        public InquiryViewHolder(View itemView) {
            super(itemView);
            cvParent=(CardView)itemView.findViewById(R.id.spDelivery_cvParent);
            llSPParent=(LinearLayout)itemView.findViewById(R.id.spDelivery_llSPParent);
            txtDeliveryId=(TextView) itemView.findViewById(R.id.spDelivery_txtDeliveryId);
            txtCustName=(TextView) itemView.findViewById(R.id.spDelivery_txtCustomerName);
            txtSalesPersonName=(TextView) itemView.findViewById(R.id.spDelivery_txtSPName);
            txtDeliveryDate=(TextView) itemView.findViewById(R.id.spDelivery_txtDeliveryDate);
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
