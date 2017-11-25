package babasport;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.babasport.mapper.test.BbsTestMapper;
import cn.itcast.babasport.pojo.test.BbsTest;
import cn.itcast.babasport.service.test.BbsTestService;

/** 
* @author jia 作者 E-mail: 184639633@qq.com
* @version 创建时间：2017年11月7日 下午9:44:25 
* 类说明 
*/
@ContextConfiguration(locations={"classpath:spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSM {
	@Resource
	private BbsTestMapper bbsTestMapper;
	
	 @Resource
	private BbsTestService bbsTestService;
	@Resource 
	private  SolrServer solrServer; 
	@Test
	public void testSolrServer() throws Exception{
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id", "001");
		doc.addField("name_ik", "spring和solr整合");
		solrServer.add(doc);
		solrServer.commit();
	}
	
	@Test
   public void testMybatis(){
	   BbsTest bbsTest = new BbsTest();
	   bbsTest.setName("tom");
	   bbsTest.setBirthday(new Date());
	   bbsTestMapper.insertBbsTest(bbsTest);
   }
	
	
	 @Test
	    public void testSpring(){
	    	BbsTest bbsTest = new BbsTest();
	    	bbsTest.setName("lili");
	    	bbsTest.setBirthday(new Date());
	    	bbsTestService.insertBbsTest(bbsTest);
	    }
}
