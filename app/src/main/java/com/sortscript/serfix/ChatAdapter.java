package com.sortscript.serfix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sortscript.serfix.databinding.MsgLayoutLeftBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {
    ArrayList<ChatModel> arrayList;
    Context context;

    public ChatAdapter(ArrayList<ChatModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.msg_layout_left, parent, false);
        return new ChatAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.Holder holder, int position) {
        if (arrayList.get(position).isFromYou) {
            holder.binding.msgFromProviderCard.setVisibility(View.GONE);
            holder.binding.msgFromYouCard.setVisibility(View.VISIBLE);
            holder.binding.yourMsg.setText(arrayList.get(position).getMsg());
        } else {
            holder.binding.msgFromProviderCard.setVisibility(View.VISIBLE);
            holder.binding.msgFromYouCard.setVisibility(View.GONE);
            holder.binding.msg.setText(arrayList.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        MsgLayoutLeftBinding binding;

        public Holder(@NonNull View itemView) {
            super(itemView);
            binding = MsgLayoutLeftBinding.bind(itemView);

        }
    }
}
