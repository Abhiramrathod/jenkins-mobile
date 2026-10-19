package com.jenkinsmobile.api;
import android.content.Context;
import com.jenkinsmobile.util.Prefs;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    private static ApiClient instance;
    private Retrofit retrofit;
    private JenkinsApi api;
    private String baseUrl;
    public static synchronized ApiClient get(Context ctx){
        if(instance==null) instance = new ApiClient(ctx.getApplicationContext());
        return instance;
    }
    private ApiClient(Context ctx){
        Prefs prefs = new Prefs(ctx);
        baseUrl = normalize(prefs.getUrl());
        String user = prefs.getUser();
        String token = prefs.getToken();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS);
        if(user!=null && !user.isEmpty() && token!=null){
            builder.addInterceptor(new BasicAuthInterceptor(user, token));
        }
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(log);
        retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        api = retrofit.create(JenkinsApi.class);
        builder.addInterceptor(new CrumbInterceptor(api));
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(builder.build()).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(JenkinsApi.class);
    }
    public JenkinsApi api(){ return api; }
    private String normalize(String url){
        if(url==null) return "";
        if(!url.endsWith("/")) url += "/";
        return url;
    }
    public void updateCredentials(Context ctx){
        instance = new ApiClient(ctx.getApplicationContext());
    }
}
