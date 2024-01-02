package com.wristband.gaoyusheng.base_lib_module.http;


import com.wristband.gaoyusheng.base_lib_module.progress.ProgressListener;
import com.wristband.gaoyusheng.base_lib_module.progress.ProgressResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by TimAimee on 2016/7/27.
 */
public class HttpDownUtil {
    private final static String BASE_URL = "http://10.1.249.148:18092/";
    private final static String TAG = HttpDownUtil.class.getSimpleName();
    private final static int CONNECT_TIME_OUT = 30;
    private volatile static HttpDownUtil instance; //声明成 volatile
    static OkHttpClient.Builder httpClient;
    static HttpLoggingInterceptor httpLoggingInterceptor;
    static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private HttpDownUtil() {

    }

    public static HttpDownUtil getInstance() {
        if (instance == null) {
            synchronized (HttpDownUtil.class) {
                if (instance == null) {
                    instance = new HttpDownUtil();
                    httpLoggingInterceptor = getHttpLoggingInterceptor();
                    getHttpClient();
                }
            }
        }
        return instance;
    }

    private static void getHttpClient() {
        httpClient = new OkHttpClient().newBuilder();
        httpClient.addInterceptor(httpLoggingInterceptor);
        httpClient.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
    }


    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }


    public static <S> S creatService(Class<S> serviceClass, final ProgressListener progressListener) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                ProgressResponseBody responseBody = new ProgressResponseBody(originalResponse.body(), progressListener);
                return originalResponse.newBuilder()
                        .body(responseBody)
                        .build();
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public void down(String url, ProgressListener progressListener,
                            Observer<Response<ResponseBody>> responseBodySubscriber) {
        creatService(HttpService.class, progressListener)
                .downloadApkUseStream(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBodySubscriber);
    }


}
