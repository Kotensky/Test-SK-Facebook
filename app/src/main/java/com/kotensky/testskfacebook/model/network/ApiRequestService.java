package com.kotensky.testskfacebook.model.network;

import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ImageEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiRequestService {

    @GET(NetworkVariables.ALBUMS)
    Observable<Response<ResponseEntity<AlbumEntity>>> getProfileAlbums(@Query("access_token") String accessToken,
                                                                       @Query("fields") String fields);

    @GET()
    Observable<Response<ResponseEntity<AlbumEntity>>> getProfileAlbumsByUrl(@Url() String url);


    @GET(NetworkVariables.ALBUM_PHOTOS)
    Observable<Response<ResponseEntity<List<ImageEntity>>>> getPhotosInAlbum(@Path("album-id") String albumId,
                                                                             @Query("access_token") String accessToken,
                                                                             @Query("fields") String fields);

    @GET()
    Observable<Response<ResponseEntity<List<ImageEntity>>>> getPhotosInAlbumByUrl(@Url() String url);
}
