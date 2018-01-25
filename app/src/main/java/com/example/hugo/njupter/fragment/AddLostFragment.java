package com.example.hugo.njupter.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.bean.User;
import com.example.hugo.njupter.provider.APIConstants;
import com.example.hugo.njupter.provider.BasePrvdr;
import com.example.hugo.njupter.provider.CallBackListener;
import com.example.hugo.njupter.utils.TimeUtils;
import com.example.hugo.njupter.utils.ToastUtil;
import com.rey.material.widget.RadioButton;
import com.rey.material.widget.Slider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by hugo on 2017/3/21.
 */
@EFragment(R.layout.fragment_add_lost)
public class AddLostFragment extends BaseFragment {

    private static final int RESULT_OK = -1;

    @FragmentArg
    public String page;

    @ViewById(R.id.lost_slider)
    Slider slider;

    @ViewById(R.id.tv_lost_point)
    TextView tvNum;

    @ViewById(R.id.ed_lost_title)
    com.example.hugo.njupter.widget.XWEditText edLosteTitle;

    @ViewById(R.id.ed_lost_content)
    com.example.hugo.njupter.widget.XWEditText edLostContent;

    @ViewById(R.id.ly_switch_rb)
    LinearLayout lySwitch;

    @ViewById(R.id.tv_add_notice)
    TextView tvAddNotice;

    @ViewById(R.id.et_price)
    EditText etPrice;

    @ViewById(R.id.tv_price)
    TextView tvPrice;

    @ViewById(R.id.tv_lost_point)
    TextView tvLostPoint;

    @ViewById(R.id.ic_point)
    ImageView icPoint;


    @ViewById(R.id.rb_shenghuo)
    RadioButton rbShengHuo;
    @ViewById(R.id.rb_tushu)
    RadioButton rbTuShu;
    @ViewById(R.id.rb_shuma)
    RadioButton rbShuMa;
    @ViewById(R.id.rb_other)
    RadioButton rbOther;

    /**
     * 选择的图片的GridView
     */
    @ViewById(R.id.prev_outlook_lost_gv)
    GridView gridView;

    private String cid;
    private User user;
    private String point;
    private String flag;

    @AfterViews
    public void initViews(){
        user=User.getCurrentUser(getContext());
        init(page);



    }

    private void init(String page) {
        switch (page){
            case "add_lost":
                flag="0";
                cid="5";

                tvAddNotice.setVisibility(View.VISIBLE);
                lySwitch.setVisibility(View.GONE);
                slider.setVisibility(View.VISIBLE);
                etPrice.setVisibility(View.GONE);
                tvPrice.setText("柚子悬赏");
                tvLostPoint.setVisibility(View.VISIBLE);
                icPoint.setImageResource(R.drawable.ic_point);


                //设置np1的最小值和最大值
                tvNum.setText("柚点："+slider.getValue());
                point=String.valueOf(slider.getValue());
                slider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
                    @Override
                    public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                        tvNum.setText("柚点："+newValue);
                        //将点数赋值
                        point=String.valueOf(newValue);
                    }
                });

                break;
            case "add_second":
                flag="1";

                tvPrice.setText("二手价格");
                icPoint.setImageResource(R.drawable.ic_money);
                //设置np1的最小值和最大值
                slider.setVisibility(View.GONE);
                etPrice.setVisibility(View.VISIBLE);
                tvNum.setText("价格："+slider.getValue());
                tvAddNotice.setVisibility(View.GONE);
                lySwitch.setVisibility(View.VISIBLE);

                rbShengHuo.setChecked(true);

                CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            rbShengHuo.setChecked(rbShengHuo == buttonView);
                            rbOther.setChecked(rbOther == buttonView);
                            rbShuMa.setChecked(rbShuMa == buttonView);
                            rbTuShu.setChecked(rbTuShu == buttonView);
                        }

                        switch (buttonView.getText().toString()){
                            case "生活用品":
                                cid="1";
                                break;
                            case "图书资料":
                                cid="2";
                                break;
                            case "数码产品":
                                cid="3";
                                break;
                            case "其他物品":
                                cid="4";
                                break;
                        }
                    }



                };

                rbShengHuo.setOnCheckedChangeListener(listener);
                rbTuShu.setOnCheckedChangeListener(listener);
                rbOther.setOnCheckedChangeListener(listener);
                rbShuMa.setOnCheckedChangeListener(listener);
                break;
        }
    }

    @Click(R.id.bt_add_do)
    public void onAddLost(View view){
       // checkInput(); 检查用户输入逻辑是否正确
        BasePrvdr basePrvdr=new BasePrvdr(getContext());
        Map<String,String> params=new HashMap<>();

        params.put("cid",cid);
        params.put("title",edLosteTitle.getText().toString());
        params.put("des",edLostContent.getText().toString());
        params.put("date", TimeUtils.getTime(TimeUtils.DEFAULT_DATE_FORMAT));
        params.put("nickName",user.getNickName());
        if(point==null){
            point=etPrice.getText().toString();
        }
        params.put("point",point);
        params.put("flag",flag);

        basePrvdr.post(APIConstants.addGoods, params, new CallBackListener() {
            @Override
            public void onFailure(Call call, Exception e) {
                ToastUtil.showShortToast(getContext(), "网络错误，请重试");
            }

            @Override
            public void onComplete(int code, String message, String data) {
                if (code == 200) {
                    ToastUtil.showShortToast(getContext(), "提交成功");
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        // 处理图片选择

        super.onActivityResult(requestCode, resultCode, data);
    }
}
