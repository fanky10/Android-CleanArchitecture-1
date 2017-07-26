package com.globant.equattrocchio.data;

import com.globant.equattrocchio.data.response.Result;
import com.globant.equattrocchio.data.service.api.SplashbaseApi;
import com.globant.equattrocchio.domain.Image;
import com.globant.equattrocchio.domain.service.ImagesServices;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImagesServicesImpl implements ImagesServices {

    private static final String URL= "http://splashbase.co/";

    @Override
    public void getLatestImages(Observer<Boolean> observer) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create())
                .build();

        SplashbaseApi api  = retrofit.create(SplashbaseApi.class);

        Call<Result> call = api.getImages();

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //todo: show the response.body() on the ui
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                //todo: update the UI with a connection error message
            }
        });


    }

    @Override
    public Observable<List<Image>> getLatestImages() {
        return Observable.create(new ObservableOnSubscribe<List<Image>>() {
            @Override
            public void subscribe(final @NonNull ObservableEmitter<List<Image>> emitter) throws Exception {
                Retrofit retrofit = new Retrofit.Builder().
                        baseUrl(URL).
                        addConverterFactory(GsonConverterFactory.create())
                        .build();

                SplashbaseApi api  = retrofit.create(SplashbaseApi.class);

                Call<Result> call = api.getImages();

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        //todo: show the response.body() on the ui
                        // todo: add transformation
                        List<Image> domainImages = new ArrayList<Image>();
                        for (com.globant.equattrocchio.data.response.Image i: response.body().getImages()) {
                            // if the transformation is heavy then could emit on next every time it's transformed
                            Image domainImage = new Image();
                            domainImage.setId(i.getId());
                            domainImage.setLargeUrl(i.getLargeUrl());
                            domainImage.setUrl(i.getUrl());
                            domainImages.add(domainImage);
                        }
                        emitter.onNext(domainImages);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        emitter.onError(t);// could create a custom cross Error for just the app
                    }
                });
            }
        });
    }
}
