package com.example.stw;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class CommonDiaryDAO {
    public  boolean create (String ccontents, String userid, String dtitle) {
        String result = null;
        try {
            URL url = new URL("http://3.35.47.128/diary/commoncreate/"+userid);
            JSONObject json = new JSONObject();
            json.put("ccontents",ccontents);
            json.put("title",dtitle);
            //json.put("contents", diary.get_content());
            String body = json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // conn.setRequestProperty("Authorization", access);
            conn.setRequestProperty("Content-Length", "length");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            //conn.setDoInput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
            {
                builder.append(inputLine);
            }

            result = builder.toString();
            Log.i("create", result);
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
        // return true;
    }
    public boolean update(String contents,int diary_id)
    {
        String result = null;
        try {
            URL url = new URL("http://3.35.47.128/diary/updatecommon/" + diary_id);
            JSONObject json = new JSONObject();
            json.put("content", contents);
            //json.put("contents", diary.get_content());
            String body = json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // conn.setRequestProperty("Authorization", access);
            conn.setRequestProperty("Content-Length", "length");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
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
            Log.i("update", result);
            in.close();
        }
        catch(Exception e)
        {
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

    public ArrayList<CommonDiaryDTO> read(String userid)
    {
        ArrayList<CommonDiaryDTO> comm = new ArrayList<CommonDiaryDTO>();
        try {
            URL url = new URL("http://3.35.47.128/diary/readC/" + userid);
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
                String content = json.getJSONObject(i).getString("ccontents");
                String create_at = json.getJSONObject(i).getString("create_at");
                String host = json.getJSONObject(i).getString("host");
                int diary_id = json.getJSONObject(i).getInt("id");
                String ctitle=json.getJSONObject(i).getString("title");
                CommonDiaryDTO result = new CommonDiaryDTO(content, create_at, host, diary_id,ctitle);
                comm.add(result);
            }
        }
        catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return comm;
    }

    public boolean invite(int id, String invited)
    {
        String result = null;
        try {
            URL url = new URL("http://3.35.47.128/diary/invite/"+id);
            JSONObject json = new JSONObject();
            json.put("invite", invited);
            //json.put("contents", diary.get_content());
            String body = json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // conn.setRequestProperty("Authorization", access);
            conn.setRequestProperty("Content-Length", "length");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
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
            Log.i("invite", result);
            in.close();
        }
        catch(Exception e)
        {
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
    public ArrayList<CommonDiaryDTO> readList()
    {
        ArrayList<CommonDiaryDTO> comm = new ArrayList<CommonDiaryDTO>();
        try {
            URL url = new URL("http://3.35.47.128/diary/commonlist");
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
                String content = json.getJSONObject(i).getString("ccontents");
                String create_at = json.getJSONObject(i).getString("create_at");
                String host = json.getJSONObject(i).getString("host");
                int diary_id = json.getJSONObject(i).getInt("id");
                String ctitle=json.getJSONObject(i).getString("title");
                CommonDiaryDTO result = new CommonDiaryDTO(content, create_at, host, diary_id,ctitle);
                comm.add(result);
            }
        }
        catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return comm;
    }

}