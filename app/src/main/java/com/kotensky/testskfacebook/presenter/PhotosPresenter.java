package com.kotensky.testskfacebook.presenter;

import com.kotensky.testskfacebook.model.data.ImageEntity;
import com.kotensky.testskfacebook.model.data.ImageListEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.model.network.ApiFactory;
import com.kotensky.testskfacebook.model.network.NetworkVariables;
import com.kotensky.testskfacebook.utils.SharedPreferencesManager;

import com.kotensky.testskfacebook.view.fragments.view.PhotosView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class PhotosPresenter extends BasePresenter<PhotosView> {

    private CompositeDisposable compositeDisposable;

    public PhotosPresenter(PhotosView view) {
        setView(view);
        compositeDisposable = new CompositeDisposable();
    }

    public void getPhotosFirstPage(String albumId) {
        getPhotos(ApiFactory.getApiRequestService()
                .getPhotosInAlbum(albumId == null ? "me" : albumId,
                        SharedPreferencesManager.getAccessToken(),
                        NetworkVariables.IMAGES_FIELD), true);

    }

    public void getPhotosNextPage(String nextUrl) {
        getPhotos(ApiFactory.getApiRequestService().getPhotosInAlbumByUrl(nextUrl), false);
    }

    private void getPhotos(Observable<Response<ResponseEntity<ImageListEntity>>> photosObservable, boolean isFirst) {
        getView().showLoading();
        photosObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseEntity<ImageListEntity>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<ResponseEntity<ImageListEntity>> responseEntityPhotos) {
                        if (responseEntityPhotos.isSuccessful()) {
                            getView().onResponseObtained(responseEntityPhotos.body(), isFirst);
                        } else {
                            getView().showErrorMessage("Error: " + responseEntityPhotos.code() + " " + responseEntityPhotos.message());
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
