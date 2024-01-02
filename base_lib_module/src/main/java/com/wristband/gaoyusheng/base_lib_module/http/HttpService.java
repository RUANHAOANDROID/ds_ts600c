package com.wristband.gaoyusheng.base_lib_module.http;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by TimAimee on 2016/7/20.
 */
public interface HttpService {

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadApkUseStream(@Url String url);

}
