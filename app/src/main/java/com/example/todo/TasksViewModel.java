package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TasksViewModel extends ViewModel {
    LiveData<List<Task>> tasks = null;
    List<Task> taskList = new ArrayList<>();

    public TasksViewModel() {
        taskList.add(new Task("hello"));
        taskList.add(new Task(","));
        taskList.add(new Task("how"));
        taskList.add(new Task("are"));
        tasks = new MutableLiveData<>();
        ((MutableLiveData<List<Task>>) tasks).setValue(taskList);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

}
