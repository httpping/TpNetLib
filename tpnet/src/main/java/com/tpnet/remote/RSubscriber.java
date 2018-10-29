package com.tpnet.remote;


import com.utils.log.NetLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.annotations.NonNull;


/**
 * 对话框操作处理
 * @param <T>
 */
public abstract  class RSubscriber<T> implements Subscriber<T> {

    //对话框操作
    public void showDialog(){
        NetLog.d("","showDialog()");
    }
    public void closeDialog(){
        NetLog.d("","closeDialog()");
    }

    public Subscription mDis ;
    public boolean isComplete;
    @Override
    public void onSubscribe(@NonNull Subscription d) {
        mDis = d ;
        d.request(Long.MAX_VALUE);

        try{
            if (!isComplete) {
                showDialog();
            }
        }catch (Exception e){

        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        onComplete();
        NetLog.e("onError",e.getMessage()+"");
    }

    @Override
    public void onComplete() {
        try {
            isComplete = true;
            try {
                closeDialog();
            } catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
