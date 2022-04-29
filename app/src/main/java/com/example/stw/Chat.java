package com.example.stw;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class Chat {
    public String chatting(String send)
    {
        String result = null;
        try {
            URL url = new URL("http://ec2-3-36-99-116.ap-northeast-2.compute.amazonaws.com:5000/param");
            JSONObject json = new JSONObject();
            json.put("question", send);
            String body = json.toString();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", "length");
            conn.setRequestProperty("Content-Type", "application/json");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder1 = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder1.append(inputLine);
            }
            result = builder1.toString();
            in.close();
            Log.e("APIManager", result);
            result=result.substring(2);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }
}
