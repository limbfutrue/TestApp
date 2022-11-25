package lm.com.testapp.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
	
	private DataOutputStream dos;
	private FileInputStream is;
	private IProgressListener progressListener;
	public final static int RESULT_SUCCESS_CODE = 200;
	public final static int RESULT_FAIL_CODE = 500;
	public final static int ERROR_CODE = -1;
	public int sendFile(String hostName ,int port,String filePath,IProgressListener progressListener){
		try{
			File file = new File(filePath);
			is = new FileInputStream(filePath);
			Socket socket = new Socket(hostName,port);
			OutputStream os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
			byte[] bytes = new byte[1024 * 8];
			int length = 0;
			int size = is.available();
			int count = 0;
			int length1 = is.read(bytes, 0, bytes.length);
			while((length = is.read(bytes, 0, bytes.length))!=-1){
				dos.write(bytes, 0, length);
				count ++;
				double p = ((double)count) /((double)(size%length1 == 0 ? (size/length1) : (size/length1)+1)) * 100;
				BigDecimal b= new BigDecimal(p);
				p = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				if (progressListener != null){
					progressListener.onProgress(p);
				}
				dos.flush();
			}
			if (progressListener != null){
				progressListener.onProgress(100);
			}
			return RESULT_SUCCESS_CODE;
		}catch (ConnectException e) {
			return RESULT_FAIL_CODE;
		}catch(Exception e){
			return ERROR_CODE;
		} finally{
			try {
				dos.close();
				is.close();
			} catch (Exception e){
			}
		}
	}

	public interface IProgressListener{
		void onProgress(double p);
	}
}
