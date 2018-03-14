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
import com.shivshaktihonda.app.pojo.ModelBookingData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class AdptSpBookingList extends RecyclerView.Adapter<AdptSpBookingList.InquiryViewHolder> {
    Context context;
    private RecyclerClickListener clickListener;
    ArrayList<ModelBookingData> bookingData;
    SimpleDateFormat toSdf,fromSdf;
    public AdptSpBookingList(Context context,ArrayList<ModelBookingData> bookingData){
        this.context=context;
        this.bookingData=bookingData;
        toSdf=new SimpleDateFormat("dd MMM yyyy");
        fromSdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }

    @Override
    public InquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.row_booking_list,parent,false);
        InquiryViewHolder holder=new InquiryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InquiryViewHolder holder, int position) {
        holder.txtBookindId.setText(bookingData.get(position).booking_id +" - "+bookingData.get(position).model_name);
        holder.txtCustomerName.setText(bookingData.get(position).first_name+ " "+ bookingData.get(position).last_name);
        try {
            holder.txtBookingDate.setText(toSdf.format(fromSdf.parse(bookingData.get(position).created_on)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*        if ((boolean) CM.getSp(context, CV.IS_ADMIN,false)){
            holder.txtCustomerName.setText(bookingData.get(position).salesperson_name);
        }else{
            holder.txtCustomerName.setText(bookingData.get(position).first_name+ " "+ bookingData.get(position).last_name);
        }*/


/*        try {
            holder.txtBookingDate.setText(toSdf.format(fromSdf.parse(bookingData.get(position).delivery_datettime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    public void clickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return bookingData.size();
    }

    public class InquiryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvParent;
        TextView txtBookindId,txtCustomerName,txtBookingDate;
        public InquiryViewHolder(View itemView) {
            super(itemView);
            cvParent=(CardView)itemView.findViewById(R.id.spBooking_cvParent);
            txtBookindId=(TextView)itemView.findViewById(R.id.spBooking_txtBookingId);
            txtCustomerName=(TextView)itemView.findViewById(R.id.spBooking_txtCustomerName);
            txtBookingDate=(TextView) itemView.findViewById(R.id.spBooking_txtBookingDate);
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
