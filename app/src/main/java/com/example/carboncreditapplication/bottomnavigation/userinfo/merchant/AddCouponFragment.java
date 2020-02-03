package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carboncreditapplication.R;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * AddGoodsActivity中添加coupon(优惠券)的Fragment
 */
public class AddCouponFragment extends Fragment {
    private static final String TAG = "AddCouponFragment";

    private int useStoreId;  //优惠券对应的商店
    private String couponName;  //优惠券名称
    private int couponType;  //优惠券类型
    private int useType;  //优惠券使用类型
    private int couponCost;  //所需碳积分
    private int couponSill;  //使用门槛
    private int couponValue;  //优惠券价值
    private DateTime expirationTime;  //过期时间

    private EditText editCouponName;
    private EditText editCouponCost;
    private EditText editCouponSill;
    private EditText editCouponValue;
    private TextView textCouponExpirationTime;

    View view;
    AddGoodsActivity activity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_coupon, container, false);

        initView();

        return view;
    }

    public void initView(){
        activity = (AddGoodsActivity)getActivity();

        editCouponName = view.findViewById(R.id.editSetCouponName);
        editCouponCost = view.findViewById(R.id.editSetCouponCreditsNeed);
        textCouponExpirationTime = view.findViewById(R.id.textSetCouponExpirationTime);
        editCouponSill = view.findViewById(R.id.editSetCouponSill);
        editCouponValue = view.findViewById(R.id.editSetCouponValue);

        textCouponExpirationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();

                showDatePickerDialog(activity, 2, textCouponExpirationTime, date);

                //提交的时候再将date转为dateTime
                //expirationTime = new DateTime(date.getTime());

            }
        });
    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param date
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, final Date date) {

        final Calendar calendar = Calendar.getInstance(Locale.CHINA);

        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText( + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                date.setYear(year);
                date.setMonth(monthOfYear);
                date.setDate(dayOfMonth);
                Log.d(TAG, "onDateSet: date="+date.toString());
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
