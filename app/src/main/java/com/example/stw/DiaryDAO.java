package com.example.stw;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiaryDAO {

    // 개인일기 만들기
    public boolean create(String content, String userid) {
        String result = null;
        try {
            URL url = new URL("http://3.35.47.128/diary/create/"+userid);
            JSONObject json = new JSONObject();
            json.put("userid", userid);
            json.put("contents", content);

            String body = json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", "length");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setDoOutput(true);
            //conn.setDoInput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }

            result = builder.toString();
            Log.i("APIManager", "create: "+result);
            in.close();

        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        if(result.equalsIgnoreCase("success"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    // 개인일기 업데이트
    public boolean update(int did, String contents) {
        String result = null;
        try {
            JSONObject json = new JSONObject();
            json.put("contents", contents);

            URL url = new URL("http://3.35.47.128/diary/update/"+did);
            String body = json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", "length");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }

            result = builder.toString();
            in.close();
            Log.i("APIManager", "update "+result);

        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        if(result.equalsIgnoreCase("success"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    // 개인일기 지우기
    public boolean delete(int did) {
        String result = null;
        try{

            URL url = new URL("http://3.35.47.128/diary/delete/"+did);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", "length");
            conn.setRequestProperty("Content-Type", "application/json");

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());

            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
            result = builder.toString();

            in.close();
            Log.i("APIManager", "delete "+result);


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.equalsIgnoreCase("success"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    // 개인일기 모두 읽기
    public static ArrayList<DiaryDTO> read(String userid){
        ArrayList<DiaryDTO> diary = new ArrayList<DiaryDTO>();
        try{
            URL url = new URL("http://3.35.47.128/diary/read/"+userid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            String resultJson = "";
            resultJson = builder.toString();
            JSONArray json = new JSONArray(resultJson);
            for (int i = 0; i < json.length(); i++)
            {
                int id = json.getJSONObject(i).getInt("id");
                String c_date = json.getJSONObject(i).getString("c_date");
                String content = json.getJSONObject(i).getString("contents");
                DiaryDTO result = new DiaryDTO(id, userid, c_date, content);
                diary.add(result);
            }
        }
        catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace(); }
        return diary;
    }
}