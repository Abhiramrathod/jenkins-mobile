package com.jenkinsmobile.api;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
public class CrumbInterceptor implements Interceptor {
    private final JenkinsApi api;
    private volatile Crumb crumb;
    public CrumbInterceptor(JenkinsApi api){ this.api=api; }
    @Override public Response intercept(Chain chain) throws IOException {
        try {
            if(crumb==null){
                crumb = api.getCrumb().execute().body();
            }
            if(crumb!=null && crumb.crumb!=null){
                Request req = chain.request().newBuilder()
                    .addHeader(crumb.crumbRequestField, crumb.crumb)
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .build();
                Response r = chain.proceed(req);
                if(r.code()==403){ r.close(); crumb=null; }
                return r;
            }
        } catch(Exception e){ }
        return chain.proceed(chain.request());
    }
    public static class Crumb {
        public String crumb;
        public String crumbRequestField;
    }
}
