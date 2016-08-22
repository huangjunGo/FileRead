import java.io.BufferedReader;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FileReader {
	
	@Resource
	JdbcTemplate jdbcTemplate11;

	@Test
	public void fileReaderTest() throws Exception{
		BufferedReader reader = new BufferedReader(new java.io.FileReader("D:/BaiduYunDownload/0817/0817.txt"));
		String str;
		int i = 1;
		while ((str = reader.readLine()) != null) {
			i++;
			/*System.err.println(str.split(" ")[0]+"  "+str.split(" ")[1]+"  "+str.split(" ")[2]);
			jdbcTemplate11.update("insert into ip(ip,url,size) values(?,?,?)", str.split(" ")[0],str.split(" ")[1],str.split(" ")[2]);*/
		}
		System.err.println("  over total : "+i);
		reader.close();
	}
}
