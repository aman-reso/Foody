package com.dev.foody;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ViewModel extends androidx.lifecycle.ViewModel {
    MutableLiveData<List<Models>> model;

    @SuppressLint("StaticFieldLeak")
    Context context;
    DataRepo repo;

    public void init(Context context, String query) {
        this.context = context;
        repo = new DataRepo();
        model = repo.getTimeFromServer(context, query);
    }

    public LiveData<List<Models>> getCheckModel() {
        return model;
    }


}
