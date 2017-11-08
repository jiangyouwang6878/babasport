package cn.itcast.babasport.pojo.test;

import java.io.Serializable;
import java.util.Date;

/** 
* @author jia 作者 E-mail: 184639633@qq.com
* @version 创建时间：2017年11月7日 下午9:29:33 
* 类说明 
*/
public class BbsTest implements Serializable{
    private Integer id;
    private String name;
    private Date birthday;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
