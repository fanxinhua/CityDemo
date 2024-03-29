package kemizhibo.rhkj.com.ijkpalaydemo;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by dell on 2019/9/16.
 */

public class LevelsListDate {
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Context context;

    public LevelsListDate(Context context) {
        this.context = context;
    }

    public ArrayList<JsonBean> initJsonData(String path) {
        String JsonData = JsonFileReader.getJson(context, path);
        options1Items.clear();
        options1Items = parseData(JsonData);//用Gson 转成实体
        return options1Items;
    }

    public ArrayList<ArrayList<String>> initJsonData1(String path) {
        String JsonData = JsonFileReader.getJson(context, path);
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        options2Items.clear();
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getREGION_NAME();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getRes() == null
                        || jsonBean.get(i).getCity().get(c).getRes().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCity().get(c).getRes().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCity().get(c).getRes().get(d).getREGION_NAME();
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }
        return options2Items;
    }

    public ArrayList<ArrayList<ArrayList<String>>> initJsonData2(String path) {
        String JsonData = JsonFileReader.getJson(context, path);
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        options3Items.clear();
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getREGION_NAME();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getRes() == null
                        || jsonBean.get(i).getCity().get(c).getRes().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCity().get(c).getRes().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCity().get(c).getRes().get(d).getREGION_NAME();
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        return options3Items;
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
