package sk.weichat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sk.weichat.entity.AccessToken;

public class AccessTokenUtil {
	
	//private static final String path="E:/Users/sunkeit/workspace/WeiXin/src/main/webapp/file/token.txt";
	private static final String path="file"+File.separator+"token.txt";
	// File.separator
	/**
	 * 将AccessToken保存为本地文件,会乱码，但读取没问题
	 * @throws IOException 
	 */
	public static void saveAccessToken(AccessToken token) throws IOException{
		FileOutputStream fileOutputStream;//打开文件输出流
	    ObjectOutputStream objectOutputStream;//打开对象输出流
	    File file = new File(path);//新建文件
	    if (!file.getParentFile().exists()) {
	        file.getParentFile().mkdirs();
	    }
	    if (file.exists()) {
	        file.delete();
	    }
	    file.createNewFile();
	   
	    fileOutputStream = new FileOutputStream(file.toString());//将新建的文件写入文件输出流
	    objectOutputStream = new ObjectOutputStream(fileOutputStream);//向对象输出流写入文件输出流
	    objectOutputStream.writeObject(token);//将序列化后的对象写入对象输出流
	    objectOutputStream.close();//关闭对象输出流
	    fileOutputStream.close();//关闭文件输出流
	}
	
	/**
	 * 读取保存的AccessToken
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static AccessToken readAccessToken() throws IOException, ClassNotFoundException{
	    File file = new File(path);//新建文件
	    if (!file.getParentFile().exists()) {
	        file.getParentFile().mkdirs();
	    }
	    if (file.exists()) {
	        //如果文件存在
	    	FileInputStream fis;
		    ObjectInputStream ois;
		    fis = new FileInputStream(file.toString());//将新建的文件写入文件输入流
		    ois = new ObjectInputStream(fis);//将文件输入流写入对象输入流
		    AccessToken token2 = new AccessToken();
		    token2 = (AccessToken) ois.readObject();//获取对象输入流的对象
		    ois.close();//关闭对象输入流
		    fis.close();//关闭文件输入流
		    return token2;
	    } else {
	        return null;
	    }
	}
	
	//递归删除文件夹
	public void deleteDir(File file){
	        if (file.isFile()) {
	            //如果是文件的话
	            file.delete();
	            return;
	        }
	        if (file.isDirectory()) {
	            //如果是文件夹的话
	            File[] childFile = file.listFiles();//获取这个文件夹里面的所有文件(包括文件夹)
	            if (childFile == null || childFile.length == 0) {
	               //如果这个文件夹没有子文件或者文件夹的话，就删除掉这个文件(文件夹)
	                file.delete();
	                return;
	            }
	            for (File f : childFile) {
	                //递归这个方法
	                deleteDir(f);
	            }
	            file.delete();//这个方法最后就是删除掉目标文件
	        }
	}
}
