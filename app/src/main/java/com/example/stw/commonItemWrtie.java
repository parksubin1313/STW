package com.example.stw;

public class commonItemWrtie {

    String ID, memory;

    public commonItemWrtie(String ID, String memory)
    {
        this.ID=ID;
        this.memory=memory;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory)
    {
        this.memory = memory;
    }
}
