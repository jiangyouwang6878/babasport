package cn.itcast.babasport.mapper.test;

import java.util.List;

import cn.itcast.babasport.pojo.test.BbsTest;

/** 
* @author jia 作者 E-mail: 184639633@qq.com
* @version 创建时间：2017年11月7日 下午9:33:26 
* 类说明 
*/
public interface BbsTestMapper {
  public void insertBbsTest(BbsTest bbsTest);
  
  public List<BbsTest> selectBbsTests();
}
