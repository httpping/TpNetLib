package demo3.com.tpnetlib.remote;



import com.tpnet.VpHttpClient;
import com.tpnet.remote.BasicDataManager;

import java.util.HashMap;

import demo3.com.tpnetlib.remote.api.Api;


/**
 * @author tp
 * @date 2018/07/31
 * 管理product所有的接口列表
 */
public class ApiManager extends BasicDataManager {
    public static final String BASE_URL = "https://httpbin.org/";

    private static ApiManager dataManager;


    ApiManager() {
        super();
    }

    public synchronized static ApiManager newInstance() {
        if (dataManager == null) {
            dataManager = new ApiManager();
            dataManager.init(BASE_URL);
        }
        return dataManager;
    }

    @Override
    public void init(String baseUrl) {
        vpNewtWork = new VpHttpClient.Builder().addBaseUrl(baseUrl).build();
    }

    public static Api Api() {
        return newInstance().getInterIml(Api.class);
    }




}
