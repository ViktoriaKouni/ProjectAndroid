package com.example.sep4android.DATA.APIS;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {


    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://librarydatabaseapi.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ArchiveAPI archiveAPI = retrofit.create(ArchiveAPI.class);

    public static ArchiveAPI getArchiveApi() {
        return archiveAPI;
    }
}

