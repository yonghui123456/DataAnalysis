package org.zkpk.zhuaqu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteLocal {
	 private static BufferedWriter bw=null;
	
	public static void getWrite(String html){
		try {
			bw.write(html+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getbw(String file) throws IOException{
		bw = new BufferedWriter(new FileWriter(file));
	}
	public static void close() throws IOException{
		bw.flush();
		bw.close();
	}
}
