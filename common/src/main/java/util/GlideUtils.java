package util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import http.utils.BaseUrl;

/**
 * Created by Administrator on 2018/3/28 0028.
 * Glide 工具类  zq
 * 使用简单
 * 可配置度高，自适应程度高
 * 支持常见图片格式 Jpg png gif webp
 * 支持多种数据源  网络、本地、资源、Assets 等
 * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
 * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
 * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
 * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
 */

public class GlideUtils {
    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        if (TextUtils.isEmpty(path)) return;
        Glide.with(mContext).load(BaseUrl.baseImg + path).into(mImageView);


    }

    public static void loadImageViewPath(Context mContext, String path, ImageView mImageView) {
        if (TextUtils.isEmpty(path)) return;
        Glide.with(mContext).load(path).into(mImageView);
    }

    //加载失败图片
    public static void loadImageViewLoading(Context mContext, String path, ImageView mImageView, int errorImageView) {
        if (TextUtils.isEmpty(path)) return;

        RequestOptions options = new RequestOptions()
                .error(errorImageView);
        if (path.contains("http")) {
            Glide.with(mContext).load(path).apply(options).into(mImageView);
        } else if (path.contains("/images/")) {
            Glide.with(mContext).load(BaseUrl.LoadImgUrl + path).into(mImageView);
        } else {
            Glide.with(mContext).load(BaseUrl.LoadImgUrl + "/images/" + path).into(mImageView);

        }

    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoading(Context mContext, Object path, final ImageView mImageView, int loadingImage, int errorImageView) {
        if (path == null) return;

        RequestOptions options = new RequestOptions()
                .placeholder(loadingImage)
                .error(errorImageView);
        if (path.toString().contains("http")) {
            Glide.with(mContext).load(path).apply(options).into(mImageView);
        } else if (path.toString().contains("/images/")) {
            Glide.with(mContext).load(BaseUrl.LoadImgUrl + path).into(mImageView);
        } else {
            Glide.with(mContext).load(BaseUrl.LoadImgUrl + "/images/" + path).into(mImageView);

        }

    }


    //设置加载中以及加载失败图片
//    public static void loadImageViewLoading(Context mContext, String path, ImageView mImageView) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.mipmap.loading_photo)
//                .error(R.mipmap.loading_photo);
//        Glide.with(mContext).load(path).apply(options).into(mImageView);
//    }
//

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}