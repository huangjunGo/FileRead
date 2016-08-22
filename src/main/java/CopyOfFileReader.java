import java.io.BufferedReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CopyOfFileReader {
	
	@Resource
	JdbcTemplate jdbcTemplate11;

	@Test
	public void fileReaderTest() throws Exception{
		BufferedReader reader = new BufferedReader(new java.io.FileReader("D:/BaiduYunDownload/0817/0817.txt"),5*1024*1024);
		String str;
		Ip ip = null;
		int i = 1;
		List<Ip> ips = new ArrayList<Ip>();
		while ((str = reader.readLine()) != null) {
			
			ip = new Ip(str.split(" ")[0], str.split(" ")[1], Integer.valueOf(str.split(" ")[2]));
			ips.add(ip);
			if (ips.size() == 4000) {
				System.err.println("begin --------------------------page :"+i);
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
				System.err.println("over --------------------------page :"+i);
				i++;
				ips = new ArrayList<Ip>();
			}
		}
		reader.close();
	}
}
