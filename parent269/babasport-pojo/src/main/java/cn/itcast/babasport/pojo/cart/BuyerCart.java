package cn.itcast.babasport.pojo.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 
 * @ClassName: BuyerCart
 * @Description:定义购物车
 * @author 
 * @date 2017年11月20日 下午3:55:08
 *
 */
public class BuyerCart implements Serializable {
   private List<BuyerItem> buyerItems=new ArrayList<BuyerItem>();//购物项集

public List<BuyerItem> getBuyerItems() {
	return buyerItems;
}

public void setBuyerItem(List<BuyerItem> buyerItems) {
	this.buyerItems = buyerItems;
}
/**
 * 
 * @Title: addBuyItem
 * @Description:定义装车方法 
 * @param @param buyerItem    
 * @return void    
 * @throws
 */
public void addBuyItem(BuyerItem buyerItem){
	if(buyerItems.contains(buyerItem)){
		for (BuyerItem item : buyerItems) {
			if(item.equals(buyerItem)){
				//合并商品数量
				item.setAmount(item.getAmount()+buyerItem.getAmount());
			}
		}
	}else{		
		buyerItems.add(buyerItem);
	}
}

  //商品金额
@JsonIgnore
public Float getProductPrice(){
	Float productPrice=0f;
	for (BuyerItem buyerItem: buyerItems) {
		productPrice+=buyerItem.getAmount()*buyerItem.getSku().getPrice();
	}
	return productPrice;
}
// 商品运费
@JsonIgnore
public Float getFee(){
	Float fee=0f;
	if(getProductPrice()<99){
		fee=9f;
	}
	return fee;
}

//商品总金额
@JsonIgnore
public Float getTotalPrice(){
	return getFee()+getProductPrice();
}
//商品数量
@JsonIgnore
public Integer getProductAmount(){
	Integer productAmount=0;
	for (BuyerItem buyerItem : buyerItems) {
		productAmount+=buyerItem.getAmount();
	}
   return productAmount;
}
}
