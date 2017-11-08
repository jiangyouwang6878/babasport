package cn.itcast.babasport.service.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.babasport.mapper.test.BbsTestMapper;
import cn.itcast.babasport.pojo.test.BbsTest;

/** 
* @author jia 作者 E-mail: 184639633@qq.com
* @version 创建时间：2017年11月7日 下午10:56:58 
* 类说明 
*/
@Service("bbsTestService")
public class BbsTestServiceImpl implements BbsTestService {
    @Resource
	private BbsTestMapper bbsTestMapper;
    
   
    @Transactional
	@Override
	public void insertBbsTest(BbsTest bbsTest) {
		// TODO Auto-generated method stub
    	bbsTestMapper.insertBbsTest(bbsTest);
	}


	@Override
	public List<BbsTest> selectBbsTests() {
		// TODO Auto-generated method stub
		return bbsTestMapper.selectBbsTests();
	}
    
   

}
