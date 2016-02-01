package cn.change365.framework.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.Executors;

/**
 * Created by Jack on 2015/6/18.
 */
public class NetworkUtil {
    private static NetworkUtil mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    private boolean mTest;

    private NetworkUtil(Context context, boolean test) {
        mCtx = context;
        mTest = test;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized NetworkUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkUtil(context, false);
        }
        return mInstance;
    }

    public static synchronized NetworkUtil getTestInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkUtil(context, true);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            if(!mTest){
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }else{
                mRequestQueue = getTestQequestQueue();
            }
        }
        return mRequestQueue;
    }

    private RequestQueue getTestQequestQueue(){
        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);

        ResponseDelivery responseDelivery = new ExecutorDelivery(Executors.newSingleThreadExecutor());

        RequestQueue queue = new RequestQueue(new NoCache(), network, 4, responseDelivery);

        queue.start();
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
