package com.globant.equattrocchio.cleanarchitecture.mvp.presenter;

import android.app.Activity;
import android.util.Log;

import com.globant.equattrocchio.cleanarchitecture.mvp.view.ImagesView;
import com.globant.equattrocchio.cleanarchitecture.util.bus.RxBus;
import com.globant.equattrocchio.cleanarchitecture.util.bus.observers.CallServiceButtonObserver;
import com.globant.equattrocchio.domain.GetLatestImagesUseCase;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class ImagesPresenter {

    private ImagesView view;
    private GetLatestImagesUseCase getLatestImagesUseCase;


    public ImagesPresenter(ImagesView view, GetLatestImagesUseCase getLatestImagesUseCase) {
        this.view = view;
        this.getLatestImagesUseCase = getLatestImagesUseCase;
    }

    public void onCountButtonPressed() {

        view.showText(new String(""));//todo: aca va el string que me devuelva el execute del usecase


    }

    private void onCallServiceButtonPressed() {

        getLatestImagesUseCase.execute(new DisposableObserver<List<com.globant.equattrocchio.domain.Image>>() {
            @Override
            public void onNext(@NonNull List<com.globant.equattrocchio.domain.Image> images) {
                Log.d("fanky10","Well some images...");
                view.showImagesString(images);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("fanky10","Oh an error :(");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d("fanky10","Completed successfully! :D");
            }
        }, null);
    }

    private void loadFromPreferences(){
       // view.showText("EL TEXTO QUE ME TRAGIA DE LAS PREFERENCES");// todo: traerme el texto de las preferences
    }









    public void register() {
        Activity activity = view.getActivity();

        if (activity==null){
            return;
        }

        RxBus.subscribe(activity, new CallServiceButtonObserver() {
            @Override
            public void onEvent(CallServiceButtonPressed event) {
                onCallServiceButtonPressed();
            }
        });

    }

    public void unregister() {
        Activity activity = view.getActivity();

        if (activity==null){
            return;
        }
        RxBus.clear(activity);
    }
}
