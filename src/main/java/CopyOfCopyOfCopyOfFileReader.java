import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.tomcat.jni.Thread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CopyOfCopyOfCopyOfFileReader {
	
	@Resource
	JdbcTemplate jdbcTemplate11;
	
	
	public static void main(String[] args) {
		for (int i = 10; i>-2; i--) {
			try {
				int 搜索 =100/i;
			} catch (Exception e) {
				continue;
			}
			System.err.println(i);
		}
	}

	@Test
	public void fileReaderTest() throws Exception{
		BufferedReader br = new BufferedReader(new java.io.FileReader("D:/BaiduYunDownload/0817/0817.txt"),5*1024*1024);   
	    char[] c = new char[100*1024*1024]; 
	    long start = System.currentTimeMillis();
	    int i = 1;
	    Ip ip = null;
		List<Ip> ips = new ArrayList<Ip>();
	    for(;;){  
	    	System.err.println("begin --------------------------page :"+i);
	        if(br.read(c)!=-1){  
	        	List<String> list1 = new ArrayList<String>();
	        	String[] split = String.valueOf(c).split("\r|\n");
	        	for (String string : split) {
					list1.add(string);
				}
	        	for (String str : list1) {
	        		try {
						ip = new Ip(str.split(" ")[0], str.split(" ")[1], Integer.valueOf(str.split(" ")[2]));
						ips.add(ip);
					} catch (Exception e) {
						System.err.println("errrrrrrrr"+e.getMessage()+"       str"+str);
						continue;
					}
				}
	        	final List<Ip> list = new ArrayList<Ip>();
				list.addAll(ips);
	        	jdbcTemplate11.batchUpdate("insert into ip(ip,url,size) values(?,?,?)",new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Ip ip2= list.get(i);
						ps.setString(1, ip2.getIp());
						ps.setString(2, ip2.getUrl());
						ps.setInt(3, ip2.getSize());
					}
					
					@Override
					public int getBatchSize() {
						return list.size();
					}
				});
	        }else{   
	            break;   
	        }  
	        System.err.println("over --------------------------page :"+i);
			i++;
			ips = new ArrayList<Ip>();
	    }   
	    System.err.println(System.currentTimeMillis()-start);
	    br.close();   
	    
	    
		
		
		
	}
}
