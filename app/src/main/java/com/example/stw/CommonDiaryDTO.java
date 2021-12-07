package com.example.stw;

public class CommonDiaryDTO {
    private int cid;
    private String ccontents;
    private String host;
    private String create_at;
    private String title;

    public CommonDiaryDTO(String content,String create, String h, int diaryid, String ctitle)
    {
        ccontents=content;
        host=h;
        create_at=create;
        cid=diaryid;
        title=ctitle;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCcontents(String ccontents) {
        this.ccontents = ccontents;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getCid() {
        return cid;
    }

    public String getCcontents() {
        return ccontents;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getHost() {
        return host;
    }

}