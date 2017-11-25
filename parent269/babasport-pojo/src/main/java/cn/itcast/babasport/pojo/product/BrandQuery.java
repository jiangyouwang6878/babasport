package cn.itcast.babasport.pojo.product;

import java.io.Serializable;
@SuppressWarnings("serial")
public class BrandQuery implements Serializable{
  private String name;// 品牌名称
  private Integer isDisplay;// 是否可用   0 不可用 1 可用
  
  private Integer startRow;
  private Integer pageNo=1;
  private Integer pageSize=3;
  
public Integer getStartRow() {
	return startRow;
}
public void setStartRow(Integer startRow) {
	this.startRow = startRow;
}
public Integer getPageNo() {
	return pageNo;
}
public void setPageNo(Integer pageNo) {
	this.startRow=(pageNo-1)*pageSize;
	this.pageNo = pageNo;
}
public Integer getPageSize() {
	return pageSize;
}
public void setPageSize(Integer pageSize) {
	this.startRow=(pageNo-1)*pageSize;
	this.pageSize = pageSize;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getIsDisplay() {
	return isDisplay;
}
public void setIsDisplay(Integer isDisplay) {
	this.isDisplay = isDisplay;
}
  
}
