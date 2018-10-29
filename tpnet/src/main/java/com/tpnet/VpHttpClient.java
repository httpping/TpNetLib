package com.tpnet;


import com.tpnet.interceptor.LoggingInterceptor;
import com.tpnet.remote.GsonConverterFactory;
import com.tpnet.remote.StringConverterFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class VpHttpClient {
	public static final String tag = "VpHttpClient";
	public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType FORM_TYPE =MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");

	public static final String BASE_URL ="https://httpbin.org/";

	//	private static DemoIn mDemoIn;
	private  OkHttpClient okHttpClient;
	private  Retrofit mRetrofit;
	private  String mBaseUrl;
	private List<Interceptor> interceptors;

	private  boolean isGson ;
	private MediaType PARAM_TYPE ;//参数类型

	/**
	 * 创建 retorfit
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	public <T> T createRetrofit(Class<T> tClass) {
		return getRetrofit().create(tClass);
	}





	/**
	 * okhttp clicent
	 * @return
	 */
	public synchronized OkHttpClient  getOkHttpClient(){

		if (okHttpClient == null){
			OkHttpClient.Builder builder =  new OkHttpClient.Builder();
			if (interceptors !=null) {
				for (Interceptor interceptor : interceptors){
					builder.addInterceptor(interceptor);
				}
			}
			builder.addInterceptor(new LoggingInterceptor());
			okHttpClient = builder.connectTimeout(15, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
					.build();


		}

		return  okHttpClient;
	}


	private VpHttpClient(){

	}

	private Retrofit getRetrofit() {
		if (mBaseUrl == null){
			mBaseUrl = BASE_URL;
		}
		if (mRetrofit == null) {
			Retrofit.Builder builder =  new Retrofit.Builder().client(okHttpClient)
					.baseUrl(mBaseUrl);
			if (isGson){
				builder.addConverterFactory(GsonConverterFactory.create(FORM_TYPE));
			}else {
				builder.addConverterFactory(StringConverterFactory.create());
			}
			mRetrofit = builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
		return mRetrofit;
	}

	/**
	 * 构建builder
	 */
	public static class Builder {

		VpHttpClient netWorkFactory;

		public Builder() {
			netWorkFactory = new VpHttpClient();
		}

		public Builder addBaseUrl(String url){
			netWorkFactory.mBaseUrl = url;
			return this ;
		}
		public Builder addInterceptor(Interceptor interceptor){
			if (netWorkFactory.interceptors == null){
				netWorkFactory.interceptors = new LinkedList<>();
			}
			netWorkFactory.interceptors.add(interceptor);
			return this ;
		}


		public Builder addOkHttpClient(OkHttpClient okHttpClient){
			if (okHttpClient == null){
				throw new IllegalArgumentException("okhttpclient is null !");
			}
			netWorkFactory.okHttpClient = okHttpClient;

			return  this;
		}

		public Builder addRetrofit(Retrofit retrofit){
			netWorkFactory.mRetrofit = retrofit;
			return  this;
		}


		public Builder addConverterIsGson(boolean isGson){
			netWorkFactory.isGson = isGson;
			return this;
		}


		public VpHttpClient build() {
			if ( netWorkFactory.okHttpClient == null){
				netWorkFactory.okHttpClient = netWorkFactory.getOkHttpClient();
			}

			if (netWorkFactory.mRetrofit == null){
				Retrofit retrofit = netWorkFactory.getRetrofit();
				netWorkFactory.mRetrofit = retrofit;
			}

			return netWorkFactory;
		}
	}

}
