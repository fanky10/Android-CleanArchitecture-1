package com.globant.equattrocchio.domain;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {

    private final CompositeDisposable disposables;

    UseCase() {
        this.disposables = new CompositeDisposable();
    }

    abstract void buildUseCaseObservable(DisposableObserver<T> observer, Params params);

    abstract Observable<T> buildUseCaseObservable();

    public void execute(DisposableObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        Observable<T> observable = buildUseCaseObservable();
        // todo: define actual thread executor and Looper.getMainLoop for returning callbacks
//                .subscribeOn(Schedulers.from(threadExecutor))
//                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    public void execute(Params params) {
        // nothing to observe, nothing to do
        // this.buildUseCaseObservable(null, params);
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}
