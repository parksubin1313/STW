package com.example.stw;

import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class UserDao {
    public boolean createUser(UserDTO dt)
    {
        String result = null;
        try {
            URL url=new URL("http://3.38.181.62/sign-up");
            JSONObject json = new JSONObject();
            json.put("userid",dt.getUserid());
            json.put("password",dt.getPassword());
            json.put("email",dt.getEmail());
            json.put("name",dt.getName());
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
            while((inputLine = in.readLine()) != null) {
                builder1.append(inputLine);
            }
            result = builder1.toString();
            in.close();
            Log.e("APIManager", result);
            if(result.equalsIgnoreCase("success"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public String login(String userid,String password)
    {
        String access_token="";
        try
        {
            URL url = new URL("http://3.38.181.62/login");
            JSONObject json = new JSONObject();
            json.put("userid",userid);
            json.put("password", password);
            String body=json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", "length");
            conn.setRequestProperty("Content-Type", "application/json");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String result="";
            StringBuilder builder = new StringBuilder();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = reader.readLine()) != null)
                builder.append(line);
            result=builder.toString();
            JSONObject json2=new JSONObject(result);
            access_token=json2.getString("access");
            Log.i("APIManager",access_token);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return access_token;
    }

    public UserDTO myPage(String userid)
    {
        UserDTO myD=new UserDTO();

        try {
            URL url = new URL("http://3.38.181.62/mypage/" + userid);
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
            JSONObject json=new JSONObject(resultJson);
            String uid = json.getString("userid");
            String password = json.getString("password");
            String nick = json.getString("name");
            String email = json.getString("email");
            myD.setName(nick);
            myD.setEmail(email);
            myD.setUserid(userid);
            myD.setPassword(password);
        }
        catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return myD;
    }

}

