package com.example.jandan;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class JandanRepository {
    JandanService service;

    JandanRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://i.jandan.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(JandanService.class);
    }



    public void getComments(final MutableLiveData<List<Comment>> listLiveData) {
        service.pic("jandan.get_pic_comments", 0).enqueue(new Callback<PicResponse>() {

            @Override
            public void onResponse(Call<PicResponse> call, Response<PicResponse> response) {
                listLiveData.setValue(response.body().comments);
            }

            @Override
            public void onFailure(Call<PicResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(getClass().getSimpleName(), "net error", t);
            }
        });
    }


    class PicResponse {
        String status;
        int current_page;
        int page_count;
        int total_comments;
        int count;
        List<Comment> comments;
    }


    interface JandanService {

        // ?oxwlxojflwblxbsapi=jandan.get_pic_comments&page=1
        @GET("/")
        public Call<PicResponse> pic(@Query("oxwlxojflwblxbsapi") String type, @Query("page") int page);

    }
}
