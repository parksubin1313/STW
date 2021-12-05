package com.example.stw;

public class UserDTO {

    private String userid;
    private String password;
    private String name;
    private String email;

    public UserDTO(String id, String pw, String email, String name)
    {
        this.userid=id;
        this.password=pw;
        this.email=email;
        this.name=name;
    }

    public UserDTO()
    {

    }

    // getter
    public String getUserid()
    {
        return userid;
    }

    public String getPassword()
    {
        return password;
    }
    public String getEmail()
    {
        return email;
    }
    public String getName()
    {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setUserid(String userid) {
        this.userid = userid;
    }
}

