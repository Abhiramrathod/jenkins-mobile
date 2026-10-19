package com.jenkinsmobile.api;
import com.jenkinsmobile.model.*;
import java.util.List;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
public interface JenkinsApi {
    @GET("crumbIssuer/api/json")
    Call<CrumbInterceptor.Crumb> getCrumb();
    @GET("api/json")
    Call<JenkinsRoot> getRoot(@Query("tree") String tree);
    @GET("api/json")
    Call<JenkinsRoot> getRootDepth(@Query("depth") int depth);
    @GET("view/all/api/json")
    Call<View> getAllView(@Query("tree") String tree);
    @GET("jobs/{name}/api/json")
    Call<Job> getJob(@Path("name") String name, @Query("tree") String tree);
    @GET("jobs/{name}/api/json")
    Call<Job> getJobDepth(@Path("name") String name, @Query("depth") int depth);
    @GET("jobs/{name}/buildHistory/api/json")
    Call<BuildHistory> getBuildHistory(@Path("name") String name, @Query("tree") String tree);
    @GET("job/{name}/{number}/api/json")
    Call<Build> getBuild(@Path("name") String name, @Path("number") int number, @Query("tree") String tree);
    @GET("job/{name}/{number}/api/json")
    Call<Build> getBuildDepth(@Path("name") String name, @Path("number") int number, @Query("depth") int depth);
    @GET("job/{name}/{number}/consoleText")
    Call<ResponseBody> getConsoleText(@Path("name") String name, @Path("number") int number);
    @GET("job/{name}/{number}/logText/progressiveText")
    Call<ResponseBody> getConsoleProgressive(@Path("name") String name, @Path("number") int number, @Query("start") Long start);
    @POST("job/{name}/build")
    Call<Void> build(@Path("name") String name);
    @POST("job/{name}/buildWithParameters")
    @FormUrlEncoded
    Call<Void> buildWithParameters(@Path("name") String name, @FieldMap java.util.Map<String,String> params);
    @POST("job/{name}/stop")
    Call<Void> stopBuild(@Path("name") String name, @Path("number") int number);
    @POST("queue/cancelItem")
    @FormUrlEncoded
    Call<Void> cancelQueueItem(@Field("id") long id);
    @GET("queue/api/json")
    Call<Queue> getQueue(@Query("tree") String tree);
    @GET("queue/item/{id}/api/json")
    Call<QueueItem> getQueueItem(@Path("id") long id, @Query("depth") int depth);
    @GET("computer/api/json")
    Call<Nodes> getNodes(@Query("tree") String tree);
    @GET("computer/{name}/api/json")
    Call<Node> getNode(@Path("name") String name, @Query("depth") int depth);
    @GET("api/json")
    Call<ResponseBody> testConnection();
    @GET
    Call<ResponseBody> rawGet(@Url String url);
}
