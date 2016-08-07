package com.example.retrofit;

import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import com.squareup.okhttp3.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String base_url = "https://maps.googleapis.com/maps/";
    private String base_url_post_get = "http://192.168.10.33/";
    Button hit_server,create_file,get,post,get_url_params;
    Gson gson;
    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hit_server =(Button) findViewById(R.id.hit_server);
        create_file = (Button) findViewById(R.id.create_file);
        get = (Button) findViewById(R.id.get);
        post = (Button) findViewById(R.id.post);
        get_url_params = (Button) findViewById(R.id.get_url_params);
        gson = new GsonBuilder().create();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url_post_get)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Services service = retrofit.create(Services.class);
                Call<Foo> call = service.getData();

                call.enqueue(new Callback<Foo>() {
                    @Override
                    public void onResponse(Call<Foo> call, Response<Foo> response) {
                        Foo body = response.body();
                    }

                    @Override
                    public void onFailure(Call<Foo> call, Throwable t) {

                    }
                });
            }
        });

        get_url_params.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url_post_get)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Services service = retrofit.create(Services.class);
                Call<UrlParams> call = service.getDataViaUrlParams();

                call.enqueue(new Callback<UrlParams>() {
                    @Override
                    public void onResponse(Call<UrlParams> call, Response<UrlParams> response) {
                        UrlParams body = response.body();
                    }

                    @Override
                    public void onFailure(Call<UrlParams> call, Throwable t) {

                    }
                });
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url_post_get)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Services service = retrofit.create(Services.class);
                Call<Info> call = service.getLandingPageReport("Shahbaz","m.azam.shahbaz@gmail.com");

                call.enqueue(new Callback<Info>() {
                    @Override
                    public void onResponse(Call<Info> call, Response<Info> response) {
                        Info body = response.body();
                    }

                    @Override
                    public void onFailure(Call<Info> call, Throwable t) {

                    }
                });
            }
        });

        create_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO multipart post

                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

                File file = new File(path+"/video.mp4");
                uploadFile(file);
                System.out.println();
            }
        });

        hit_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Services service = retrofit.create(Services.class);
                Call<Foo> call = service.listRepos();

                call.enqueue(new Callback<Foo>() {
                    @Override
                    public void onResponse(Call<Foo> call, Response<Foo> response) {
                        Foo body = response.body();
                    }

                    @Override
                    public void onFailure(Call<Foo> call, Throwable t) {

                    }
                });
            }
        });
    }


    private void uploadFile(File file) {
        // create upload service client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url_post_get)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Services service = retrofit.create(Services.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
}
