package com.example.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

//    @Multipart
//    @POST("post") Call<ResponseBody> upload(@Multipart desc, @Part("myFile") RequestBody file);

    @Multipart
    @POST("in.php")
    Call<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);


//    @Multipart
//    @POST("/api/Accounts/editaccount")
//    Call<User> editUser (@Header("Authorization") String authorization, @Part("file\"; filename=\"pp.png\" ") RequestBody file , @Part("FirstName") RequestBody fname, @Part("Id") RequestBody id);
}
