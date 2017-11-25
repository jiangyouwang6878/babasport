package cn.itcast.babasport.pojo.cart;

import java.io.Serializable;

import cn.itcast.babasport.pojo.product.Sku;
/**
 * 
 * @ClassName: BuyerItem
 * @Description: 定义购物项
 * @author 
 * @date 2017年11月20日 下午3:50:27
 *
 */
public class BuyerItem implements Serializable {
  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuyerItem other = (BuyerItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}
//商品信息
  private Sku sku;
  //商品数量
  private Integer amount;
  //是否有货
  private Boolean isHave;
public Sku getSku() {
	return sku;
}
public void setSku(Sku sku) {
	this.sku = sku;
}
public Integer getAmount() {
	return amount;
}
public void setAmount(Integer amount) {
	this.amount = amount;
}
public Boolean getIsHave() {
	return isHave;
}
public void setIsHave(Boolean isHave) {
	this.isHave = isHave;
}
  
}
