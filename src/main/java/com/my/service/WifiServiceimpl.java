package com.my.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.my.dao.WifiDao;
import com.my.dao.WifiDaoimpl;
import com.my.model.WifiDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WifiServiceimpl implements WifiService {

    private final String key = "4370764a516d616c38377342586e4f";
    private final WifiDao wifiDaoimpl = new WifiDaoimpl();

    public int saveWifi() throws SQLException, ClassNotFoundException, IOException {
        Queue<JsonElement> wifiInfoQueue = new LinkedList<>();
        wifiDaoimpl.deleteAllWifiDate();
        String jsonData = getWifiApi(1, 1);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonData);
        jsonElement = jsonElement.getAsJsonObject().get("TbPublicWifiInfo");
        int wifiInfoCount = Integer.parseInt(jsonElement.getAsJsonObject().get("list_total_count").getAsString());
        int wifiInfoApiRange = wifiInfoCount % 1000 == 0 ? wifiInfoCount / 1000 : (wifiInfoCount / 1000) + 1;
        int start = 1;
        int end = 1000;
        int count = 0;
        for (int i = 0; i < wifiInfoApiRange; i++) {
            jsonElement = parser.parse(getWifiApi(start, end));
            wifiInfoQueue.offer(jsonElement);
            start += 1000;
            end += 1000;
        }

        while (!wifiInfoQueue.isEmpty()) {
            count += wifiDaoimpl.insertWifiInfo(wifiInfoQueue.poll());
        }
        wifiDaoimpl.deleteWrongLot();
        return count;
    }

    public List<WifiDto> selectWifiInfo(String x, String y) throws SQLException, ClassNotFoundException {
        return wifiDaoimpl.selectWifiInfo(x, y);
    }

    public String getWifiApi(int start, int end) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode(this.key, "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start), "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end), "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("20220501", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("Content-type", "application/xml");
        BufferedReader rd;

        if (httpConn.getResponseCode() >= 200 && httpConn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(httpConn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        httpConn.disconnect();
        return sb.toString();
    }

}
