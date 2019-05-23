package com.example.jandan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class JandanViewModel extends ViewModel {

    JandanRepository repository;
    MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    public JandanViewModel() {
        repository = new JandanRepository();
    }

    LiveData<List<Comment>> getComments() {
        repository.getComments(comments);
        return comments;
    }

    void updateComments() {
        repository.getComments(comments);
    }
}
