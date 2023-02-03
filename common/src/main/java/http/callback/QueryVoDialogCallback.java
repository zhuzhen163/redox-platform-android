package http.callback;

import android.app.Activity;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import utils.ToastUtil;
import view.RequestDialog;


/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：对于网络请求是否需要弹出进度对话框
 * 修订历史：
 * ================================================
 */
public abstract class QueryVoDialogCallback<T> extends QueryVoJsonCallback<T> {

    private RequestDialog dialog;
    Activity mActivity;


    private void initDialog(Activity activity) {
        mActivity = activity;
        dialog = new RequestDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
    }

    public QueryVoDialogCallback(Activity activity) {
        super(activity);
        initDialog(activity);
    }

    public QueryVoDialogCallback(Activity activity, boolean isShowDialog) {
        super(activity);
        mActivity = activity;
        if (isShowDialog) {
            initDialog(activity);
        }
    }

    StatusLayoutManager mStatusLayoutManager;

    public QueryVoDialogCallback(Activity activity, boolean isShowDialog, StatusLayoutManager statusLayoutManager) {
        super(activity);
        mActivity = activity;
        this.mStatusLayoutManager = statusLayoutManager;
        if (isShowDialog) {
            initDialog(activity);
        }
    }

    @Override
    public void onSuccessNotData(Response<T> response, String desc) {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showEmptyLayout();
        }
    }

    @Override
    public void onSuccess(Response<T> response, String desc) {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showSuccessLayout();
        }
    }


    @Override
    public void onFail(int code, String desc) {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showErrorLayout();
        }
    }

    @Override
    public void onError(String desc) {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showErrorLayout();
        }
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
            if (mStatusLayoutManager != null) {
                mStatusLayoutManager.showLoadingLayout();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception o) {
        }
    }
}
