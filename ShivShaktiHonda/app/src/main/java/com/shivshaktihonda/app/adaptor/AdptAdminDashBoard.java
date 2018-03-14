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
import com.shivshaktihonda.app.pojo.ModelGetAllSalespersonData;

import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class AdptAdminDashBoard extends RecyclerView.Adapter<AdptAdminDashBoard.DashBoardViewHolder> {
    Context context;
    private RecyclerClickListener clickListener;
    ArrayList<ModelGetAllSalespersonData> salesPersonDataList;
    public AdptAdminDashBoard(Context context,ArrayList<ModelGetAllSalespersonData> salesPersonDataList){
        this.context=context;
        this.salesPersonDataList=salesPersonDataList;
    }


    @Override
    public DashBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.row_admin_dashboard_list,parent,false);
        DashBoardViewHolder holder=new DashBoardViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DashBoardViewHolder holder, int position) {
        holder.txtPersonName.setText(salesPersonDataList.get(position).name);
        holder.txtPersonId.setText(salesPersonDataList.get(position).user_id);
    }

    public void clickListener(RecyclerClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return salesPersonDataList.size();
    }

    public class DashBoardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvParent;
        TextView txtPersonName,txtPersonId;
        public DashBoardViewHolder(View itemView) {
            super(itemView);
            cvParent=(CardView)itemView.findViewById(R.id.adminDasgboard_cvParent);
            txtPersonName=(TextView) itemView.findViewById(R.id.adminDasgboard_txtPersonName);
            txtPersonId=(TextView)itemView.findViewById(R.id.adminDasgboard_txtPersonId);
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
