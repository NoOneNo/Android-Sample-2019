package com.example.todo;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

import java.util.List;

public class TodoFragment extends Fragment {


    List<Task> mTasks = null;

    static final String HTML_PATH = "game/runtime.html";
    static final String FILE_SCHEMA = "file://";
    static final String ASSET_URI = FILE_SCHEMA + "/android_asset";
    static final String GAME_ASSET_URI = ASSET_URI + "/game";
    static final String GAME_OPERATION_DOMAIN_URI = "https://api.op.hybrid.xiaomi.com/";
    static final String GAME_OPERATION_JS = "game/JsSdk.js";
    static final String GAME_OPERATION_URI = GAME_OPERATION_DOMAIN_URI + GAME_OPERATION_JS;
    static final String GAME_URL = ASSET_URI + "/" + HTML_PATH;
    static final String GAME_URL_QUERY_RELOAD = "recreate-webview";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String url = GAME_URL + "?recreate-webview=true";
        String needReload = Uri.parse(url).getQueryParameter(GAME_URL_QUERY_RELOAD);
        System.out.println(needReload);

        return inflater.inflate(R.layout.frag_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TasksAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TasksViewModel tasksViewModel = ViewModelProviders.of(getActivity()).get(TasksViewModel.class);
        tasksViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mTasks = tasks;
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    class TasksAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.task_item, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String title = mTasks.get(position).getTitle();
            holder.textView.setText(title);
        }

        @Override
        public int getItemCount() {
            return mTasks == null ? 0 : mTasks.size();
        }
    }
}
