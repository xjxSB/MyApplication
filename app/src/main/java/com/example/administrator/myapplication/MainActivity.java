package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView CityName,Time,Temperature,WeatherDesc,Wind;
    private ImageView DayWeatherPic,NightWeatherPic;
    private GridView WeatherGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    //初始化控件
    private void initView() {
        CityName = (TextView) findViewById(R.id.city_name);
        Time = (TextView) findViewById(R.id.time);
        Temperature = (TextView) findViewById(R.id.temperature);
        WeatherDesc = (TextView) findViewById(R.id.weather_desc);
        Wind = (TextView) findViewById(R.id.wind);

        DayWeatherPic = (ImageView) findViewById(R.id.day_weather_pic);
        NightWeatherPic = (ImageView) findViewById(R.id.night_weather_pic);

        WeatherGrid = (GridView) findViewById(R.id.weather_grid);
    }

    String result;
    //初始化数据,请求网络。将网络数据显示到控件上
    private void initData() {
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] params) {

                try {
                    URL url = new URL("http://api.map.baidu.com/telematics/v3/weather?location="+ URLEncoder.encode("长沙","utf-8")+"&output=json&ak=7hPzeLgr3NWgPwlpc7BvGUXG4ZnK8Smo");
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(6000);

                    if (conn.getResponseCode()==200){
                        InputStream in=conn.getInputStream();
                        byte[] bytes=new byte[1024*512];
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        int len=0;
                        while ((len =in.read(bytes)) > 0){
                            baos.write(bytes,0,len);
                        }

                        result=new String(baos.toByteArray());
                        Log.d("TAG", result);
                    }
                }catch(MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
