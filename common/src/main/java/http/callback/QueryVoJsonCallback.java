package http.callback;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.request.base.Request;
import com.redoxyt.app.common.BuildConfig;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import http.model.QueryVoLzyResponse;
import okhttp3.Response;
import utils.ConfigUtils;
import utils.SpUtils;

/**
 * ================================================
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class QueryVoJsonCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;
    public Context mContext;

    public QueryVoJsonCallback(Context context) {
        mContext = context;
    }

    public QueryVoJsonCallback(Type type) {
        this.type = type;
    }

    public QueryVoJsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        request.headers("client_type","android");
        request.headers("client_version", BuildConfig.VERSION_NAME);
        request.headers("client_system",android.os.Build.MODEL);
        request.headers("system_type", "1");
        String baseUrl = request.getBaseUrl();
        if (baseUrl.contains("sms/smsCode")
                || baseUrl.contains("register/carOwner")
                || baseUrl.contains("oauth/token")
                || baseUrl.contains("mobile/token")
                || baseUrl.contains("imgVerify/imgCode")
                || baseUrl.contains("username/token")
                || baseUrl.contains("systemConfig/getLoginConfig")
                || baseUrl.contains("flashLogin/token")
                || baseUrl.contains("collect/appPackage")
                ||baseUrl.contains("sms/code")
                ||baseUrl.contains("upLoadImgNoToken")
                ||baseUrl.contains("pltfRegister/userRegister")){
                if (baseUrl.contains("register/carOwnerAuth")){
                    request.headers("authorization","Bearer "+ ConfigUtils.getToken());
                }else if (baseUrl.contains("imgVerify/imgCode")
                        || baseUrl.contains("sms/smsCode")
                        || baseUrl.contains("systemConfig/getLoginConfig")
                        ||baseUrl.contains("upLoadImgNoToken")
                        ||baseUrl.contains("pltfRegister/userRegister")){
                    request.headers("authorization","aaa");
                }
        }else {
            request.headers("authorization","Bearer "+ ConfigUtils.getToken());
        }
    }

    public abstract void onFail(int code, String desc);


    public abstract void onError(String desc);

    public abstract void onSuccess(com.lzy.okgo.model.Response<T> response, String desc);

    /**
     * 请求成功数据为空
     */
    public abstract void onSuccessNotData(com.lzy.okgo.model.Response<T> response, String desc);

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        try {
            if (((Activity) mContext).isFinishing()) {
                Log.i("isFinishing", "isFinishing");
                return;
            }
            QueryVoLzyResponse lzyResponse = (QueryVoLzyResponse) response.body();
            String desc = "";
            if (lzyResponse!=null){
                if (!TextUtils.isEmpty(lzyResponse.message)) {
                    desc = lzyResponse.message;
                }
                int code = lzyResponse.code;

                if (code == 1) {
                    onSuccess(response, desc);
                } else {
                    onFail(code, desc);
                }
            }else {
                onError("服务器异常请稍后再试");
            }
        } catch (Exception o) {
            o.printStackTrace();
        }
    }


    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        String errorMessage = "";
        Throwable exception = response.getException();
        if (exception != null)
            exception.getStackTrace();
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            errorMessage = "网络连接失败，请连接网络！";
        } else if (exception instanceof SocketTimeoutException) {
            errorMessage = "网络请求超时";
        } else if (exception instanceof HttpException) {
            errorMessage = "服务器响应异常";
        } else {
            errorMessage = "数据解析异常";
        }
        onError(errorMessage);
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                QueryVoJsonConvert<T> convert = new QueryVoJsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        QueryVoJsonConvert<T> convert = new QueryVoJsonConvert<>(type);
        return convert.convertResponse(response);
    }


}
