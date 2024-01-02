package com.wristband.gaoyusheng.base_lib_module.pos;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.wristband.gaoyusheng.base_lib_module.ApiException;
import com.wristband.gaoyusheng.base_lib_module.BusinessException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by gaoyusheng on 16/12/7.
 */

public abstract class PosCallBackSubscriber<T> implements Observer<T> {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        e.printStackTrace();

        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        if (e instanceof BusinessException) {
            onBusinessError((BusinessException) e);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ApiException apiException = new ApiException(e, httpException.code());
            switch (httpException.code()) {
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                    onTimeOutError(apiException);
                    break;
                default:
                    apiException = new ApiException(e, httpException.code());
                    onApiError(apiException);
                    break;
            }
        } else if (e instanceof ConnectException) {
            ApiException apiException = new ApiException(e, ApiException.RC_CONNECT_TIMEOUT);
            onTimeOutError(apiException);
        } else if (e instanceof SocketTimeoutException) {
            ApiException apiException = new ApiException(e, ApiException.RC_REQUEST_TIMEOUT);
            onTimeOutError(apiException);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ApiException apiException = new ApiException(e, ApiException.PARSE_ERROR);
            onApiError(apiException);
        } else if (e instanceof UnknownHostException) {
            ApiException apiException = new ApiException(e, ApiException.RC_HTTP_UNKNOWNHOSTEXCEPTION);
            onApiError(apiException);
        } else {
            ApiException apiException = new ApiException(e, ApiException.UNKNOWN);
            onApiError(apiException);
        }
    }

    protected abstract void onBusinessError(BusinessException ex);

    protected void onApiError(ApiException ex) {
        BusinessException businessException = new BusinessException(String.valueOf(ex.getCode()), ex.getDisplayMessage(),
                StringUtils.EMPTY);
        onBusinessError(businessException);
    }

    public void onTimeOutError(ApiException ex) {
        onApiError(ex);
    }
}
