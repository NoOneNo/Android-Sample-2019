package com.example.remote.view.talk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.remote.Console;

import java.util.ArrayList;
import java.util.List;

public class TalkFragment extends Fragment implements Console {

    List<String> mMsgs = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_talk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = view.findViewById(R.id.send);
                String msg = et.getText().toString();
                send(msg);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MsgAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void send(String msg) {

    }

    public void onSend(String msg) {
        mMsgs.add(msg);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void onMsg(String msg) {
        mMsgs.add(msg);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void log(String msg) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    class MsgAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mMsgs.get(position));
        }

        @Override
        public int getItemCount() {
            return mMsgs.size();
        }
    }
}
