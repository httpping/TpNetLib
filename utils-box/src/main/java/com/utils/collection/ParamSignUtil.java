package com.utils.collection;

import android.util.Log;

import com.utils.sign.MD5Util;
import com.utils.log.VPLog;

import org.json.JSONObject;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 容器类工具：
 *  签名的算法的一种
 */
public class ParamSignUtil {

	
	public static String sign(JSONObject json,String key) throws Exception{
		
		if (json == null) {
			return null;
		}
		
		List<String> keys = new LinkedList<String>();
	    Iterator it = json.keys();
		while (it.hasNext()){
			 keys.add(it.next().toString());
		}
		
		Collections.sort(keys);
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			sb.append( keys.get(i) +"="+ json.get(keys.get(i)).toString());
			
			if (i+1 < keys.size()) {
				sb.append("&");
			}
		}
		
		VPLog.d("sign", sb.toString()+"&key="+key);
		
		return MD5Util.MD532(sb.toString()+"&key="+key).toUpperCase();
		 
		
	}
	
	
	
    /**
     * 获取ip地址
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.d("pay", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }
	
}
