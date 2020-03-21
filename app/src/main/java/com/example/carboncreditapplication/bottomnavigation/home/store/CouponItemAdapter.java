package com.example.carboncreditapplication.bottomnavigation.home.store;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CouponResultBean;
import java.util.List;


public class CouponItemAdapter extends RecyclerView.Adapter<CouponItemAdapter.CouponViewHolder> {
    private Context context;
    private List<CouponResultBean.ResultBean.CouponBean> couponBeanList;

    public CouponItemAdapter(Context context, List<CouponResultBean.ResultBean.CouponBean> couponBeanList){
        this.context = context;
        this.couponBeanList = couponBeanList;
    }

    static class CouponViewHolder extends RecyclerView.ViewHolder{
        TextView tvValue;
        TextView tvName;
        TextView tvCost;
        Button btnExchange;

        public CouponViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvValue = itemView.findViewById(R.id.textCouponValueStore);
            tvName = itemView.findViewById(R.id.textCouponNameStore);
            tvCost = itemView.findViewById(R.id.textCouponCostStore);
            btnExchange = itemView.findViewById(R.id.buttonExchangeCoupon);
        }
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coupon_store, viewGroup, false);
        CouponViewHolder viewHolder = new CouponViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder couponViewHolder, int i) {
        CouponResultBean.ResultBean.CouponBean bean = couponBeanList.get(i);
        couponViewHolder.tvValue.setText(bean.getValue()+"");
        couponViewHolder.tvName.setText(bean.getCoupon_name());
        couponViewHolder.tvCost.setText(bean.getCoupon_cost()+"积分");
        couponViewHolder.btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("是否兑换？")
                        .setPositiveButton("兑换", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "兑换成功！", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return couponBeanList.size();
    }

}
