package com.jenkinsmobile.api;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import android.util.Base64;
public class BasicAuthInterceptor implements Interceptor {
    private final String username, apiToken;
    public BasicAuthInterceptor(String username, String apiToken){
        this.username=username; this.apiToken=apiToken;
    }
    @Override public Response intercept(Chain chain) throws IOException {
        String creds = username + ":" + apiToken;
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
        Request req = chain.request().newBuilder().addHeader("Authorization", auth).build();
        return chain.proceed(req);
    }
}
