package demo3.com.tpnetlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tpnet.params.VpRequestParams;
import com.tpnet.remote.RSubscriber;
import com.utils.log.NetLog;

import demo3.com.tpnetlib.remote.ApiManager;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        VpRequestParams params = new VpRequestParams();
        params.put("demo","测试");


        ApiManager.Api().post(params.createRequestBody()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        NetLog.d("" +s);
                    }
                });
    }
}
