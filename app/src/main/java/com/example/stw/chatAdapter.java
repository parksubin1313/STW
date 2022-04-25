package com.example.stw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder> {

    private List<chatData> chatList;
    private String nick1="user";

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nick;
        public TextView msg;
        public View rootView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.TextView_msg);
            msg = itemView.findViewById(R.id.TextView_msg);
            rootView = itemView;

            itemView.setClickable(true);
            itemView.setEnabled(true);
        }
    }

    public chatAdapter(List<chatData> myDataSet, Context context, String myNickName){

        chatList=myDataSet;
        this.nick1=myNickName;
    }

    @NonNull
    @Override
    public chatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_item,parent,false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.MyViewHolder holder, int position) {

        chatData chat = chatList.get(position);

        holder.nick.setText(chat.getNickname());
        holder.msg.setText(chat.getMsg());

        if(chat.getNickname().equals(this.nick1)){
            holder.msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
        else{
            holder.msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }
    }

    @Override
    public int getItemCount() {
        return chatList==null ? 0 : chatList.size();
    }

    public chatData getChat(int position){

        return chatList != null ? chatList.get(position) : null;
    }

    public void addChat(chatData chat){
        chatList.add(chat);
        notifyItemInserted(chatList.size()-1);
    }

}

