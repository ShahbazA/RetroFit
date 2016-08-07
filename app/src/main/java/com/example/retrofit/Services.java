package com.example.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by hp on 8/4/2016.
 */
public interface Services {
    @GET("api/timezone/json?location=38.908133,-77.047119&timestamp=1458000000") Call<Foo> listRepos();

    @GET("get.php") Call<Foo> getData();

    @GET("get_url_params.php?param1=p1&param2=p2") Call<UrlParams> getDataViaUrlParams();

    @FormUrlEncoded
    @POST("post.php")
    Call<Info> getLandingPageReport(@Field("name") String name, @Field("email") String email);

    @Multipart
    @POST("in.php")
    Call<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);


}
