package kemizhibo.rhkj.com.ijkpalaydemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView tvAddress;
    private OptionsPickerView pvOptions;
    private LevelsListDate levelsListDate;
    private ArrayList<JsonBean> jsonBeans;
    private ArrayList<ArrayList<String>> arrayLists;
    private ArrayList<ArrayList<ArrayList<String>>> arrayLists1;
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                showHyPickerView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAddress = findViewById(R.id.tvAddress);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pvOptions != null) {
                    pvOptions.show();
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    levelsListDate = new LevelsListDate(MainActivity.this);
                    jsonBeans = levelsListDate.initJsonData("citys_data.json");
                    arrayLists = levelsListDate.initJsonData1("citys_data.json");
                    arrayLists1 = levelsListDate.initJsonData2("citys_data.json");
                    handler1.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 初始化城市选择器
     */
    private void showHyPickerView() {
        //条件选择器
        pvOptions = new OptionsPickerBuilder(MainActivity.this, new com.bigkoo.pickerview.listener.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                try {
                    //  cityId = jsonBeans.get(options1).getCity().get(options2).getRes().get(options3).getID() + "";
                    tvAddress.setText(jsonBeans.get(options1).getCity().get(options2).getRes().get(options3).getREGION_NAME());
                } catch (Exception e) {
                    // cityId = jsonBeans.get(options1).getCity().get(options2).getID() + "";
                    tvAddress.setText(jsonBeans.get(options1).getCity().get(options2).getREGION_NAME());
                }
            }
        }).build();
        pvOptions.setPicker(jsonBeans, arrayLists, arrayLists1);
    }
}
