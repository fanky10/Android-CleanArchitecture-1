package com.globant.equattrocchio.domain.service;

import com.globant.equattrocchio.domain.Image;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public interface ImagesServices {

    void getLatestImages(Observer<Boolean> observer);

    Observable<List<Image>> getLatestImages();
}
