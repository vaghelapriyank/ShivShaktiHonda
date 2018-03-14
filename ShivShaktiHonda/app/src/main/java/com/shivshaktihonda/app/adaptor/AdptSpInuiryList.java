package com.shivshaktihonda.app.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.activity.ViewInquiryDetail;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelInquiryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class AdptSpInuiryList extends RecyclerView.Adapter<AdptSpInuiryList.InquiryViewHolder> {
    Context context;
    ArrayList<ModelInquiryData> inquiryData;
    private RecyclerClickListener clickListener;
    SimpleDateFormat toSdf,fromSdf;
    public AdptSpInuiryList(Context context,ArrayList<ModelInquiryData> inquiryData){
        this.inquiryData=inquiryData;
        this.context=context;
        toSdf=new SimpleDateFormat("dd MMM yyyy");
        fromSdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }


    @Override
    public InquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.row_inquiry_list,parent,false);
        InquiryViewHolder holder=new InquiryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InquiryViewHolder holder, final int position) {
        holder.txtInquiryId.setText(inquiryData.get(position).inquery_id + " - "+inquiryData.get(position).model_name);
//        holder.txtCustomerName.setText(inquiryData.get(position).first_name+" "+inquiryData.get(position).last_name);
        holder.txtCustomerName.setText(inquiryData.get(position).first_name+ " "+ inquiryData.get(position).last_name);
        try {
            holder.txtInquiryDate.setText(toSdf.format(fromSdf.parse(inquiryData.get(position).created_on)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
/*        if ((boolean) CM.getSp(context, CV.IS_ADMIN,false)){
            holder.txtCustomerName.setText(inquiryData.get(position).salesperson_name);
        }else{
            holder.txtCustomerName.setText(inquiryData.get(position).first_name+ " "+ inquiryData.get(position).last_name);
        }*/

//        holder.txtInquiryDate.setText( fromSdf.parse(inquiryData.get(position).da) );

        holder.cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener!=null){
                    clickListener.onClickEvent(view,position);
                }

            }
        });
    }
    public void clickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return inquiryData.size();
    }

    public class InquiryViewHolder extends RecyclerView.ViewHolder {
        CardView cvParent;
        TextView txtInquiryId,txtCustomerName,txtInquiryDate;
        public InquiryViewHolder(View itemView) {
            super(itemView);
            cvParent=(CardView)itemView.findViewById(R.id.rowInquiry_cvParent);
            txtInquiryId=(TextView) itemView.findViewById(R.id.spInquiry_txtInquiryId);
            txtCustomerName=(TextView)itemView.findViewById(R.id.spInquiry_txtCustomerName);
            txtInquiryDate=(TextView)itemView.findViewById(R.id.spInquiry_txtInquiryDate);
        }
    }
}
