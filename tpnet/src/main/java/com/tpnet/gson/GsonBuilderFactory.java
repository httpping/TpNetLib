package com.tpnet.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;

import java.lang.reflect.Type;
import java.util.Collections;

/**
 * add by tanping
 * 为了gson 能够正常解析出服务器返回数据 做容错处理。
 */
public class GsonBuilderFactory {

    //基本类型转换
    public static final BooleanTypeAdapter BOOLEAN = new BooleanTypeAdapter();
    public static final DoubleTypeAdapter DOUBLE = new DoubleTypeAdapter();
    public static final FloatTypeAdapter FLOAT = new FloatTypeAdapter();
    public static final IntegerTypeAdapter INTEGER = new IntegerTypeAdapter();
    public static final LongTypeAdapter LONG = new LongTypeAdapter();
    public static final StringTypeAdapter STRING = new StringTypeAdapter();

    public static GsonBuilder createDefaultBuild(){
        GsonBuilder gsonBuilder = new GsonBuilder();


        //注入 type adapter

        // 集合
        JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(
                new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap()));

        ReflectiveTypeAdapterFactory rta = new ReflectiveTypeAdapterFactory(
                new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap()),
                FieldNamingPolicy.IDENTITY,
                Excluder.DEFAULT,
                jsonAdapterFactory);
        //Object
        gsonBuilder.registerTypeAdapterFactory(rta);

        gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap())));

        //注入 8大基本类型 type adapter
        gsonBuilder.registerTypeAdapter(Double.class,DOUBLE);
        gsonBuilder.registerTypeAdapter(Boolean.class,BOOLEAN);
        gsonBuilder.registerTypeAdapter(Float.class,FLOAT);
        gsonBuilder.registerTypeAdapter(Integer.class,INTEGER);
        gsonBuilder.registerTypeAdapter(Long.class,LONG);
        gsonBuilder.registerTypeAdapter(int.class,INTEGER);
        gsonBuilder.registerTypeAdapter(float.class,FLOAT);
        gsonBuilder.registerTypeAdapter(double.class,DOUBLE);
        gsonBuilder.registerTypeAdapter(boolean.class,BOOLEAN);
        gsonBuilder.registerTypeAdapter(long.class,LONG);
        gsonBuilder.registerTypeAdapter(String.class,STRING);

        return gsonBuilder;
    }
}
