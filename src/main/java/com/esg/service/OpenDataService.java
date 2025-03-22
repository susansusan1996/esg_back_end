package com.esg.service;

import com.esg.DisableSSL;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OpenDataService {

    public String getCarbon() {
        DisableSSL.disableSSLVerification();
        String url = "https://openapi.twse.com.tw/v1/opendata/t187ap46_L_1";
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // 這裡改用 JSONArray
            JSONArray jsonArray = new JSONArray(response.toString());
            return jsonArray.toString();

            // 如果只要第一筆的 "公司名稱"：
            // JSONObject firstItem = jsonArray.getJSONObject(0);
            // String companyName = firstItem.getString("公司名稱");
            // return companyName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
