package com.kotensky.testskfacebook.presenter;


import android.util.Log;

import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.model.network.ApiFactory;
import com.kotensky.testskfacebook.utils.SharedPreferencesManager;
import com.kotensky.testskfacebook.view.activities.view.AlbumsView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class AlbumsPresenter extends BasePresenter<AlbumsView> {

    private CompositeDisposable compositeDisposable;

    public AlbumsPresenter(AlbumsView view) {
        setView(view);
        compositeDisposable = new CompositeDisposable();
    }

    public void getAlbumsFirstPage(){
        if (SharedPreferencesManager.getAccessToken() == null) {
            getView().showLoginScreen();
            return;
        }
        getAlbums(ApiFactory.getApiRequestService()
                .getProfileAlbums(SharedPreferencesManager.getAccessToken(),"cover_photo{images},created_time,name"));

    }

    public void getAlbumsNextPage(String nextUrl){
        getAlbums(ApiFactory.getApiRequestService().getProfileAlbumsByUrl(nextUrl));
    }

    private void getAlbums(Observable<Response<ResponseEntity<AlbumEntity>>> profileAlbumsObservable) {
        getView().showLoading();
        profileAlbumsObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseEntity<AlbumEntity>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<ResponseEntity<AlbumEntity>> responseEntityAlbums) {
                        if (responseEntityAlbums.isSuccessful()) {
                            getView().onResponseObtained(responseEntityAlbums.body());
                        } else {
                            getView().showErrorMessage("Error: " + responseEntityAlbums.code() + " " + responseEntityAlbums.message());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().hideLoading();
                        getView().showErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    @Override
    public void cancel() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void destroy() {
        setView(null);
        compositeDisposable = null;
    }
}
