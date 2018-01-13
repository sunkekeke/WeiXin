package sk.weichat.utils;

import java.util.Arrays;

public class CheckUtil {

	private static final String token ="superman";
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(arr);
		
		StringBuffer content = new StringBuffer();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		
		//加密
		String str = EncryptUtil.getSha1(content.toString());
		return signature.equals(str);
	}
}
