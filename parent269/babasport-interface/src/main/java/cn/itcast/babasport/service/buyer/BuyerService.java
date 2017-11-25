package cn.itcast.babasport.service.buyer;

import cn.itcast.babasport.pojo.cart.BuyerCart;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.user.Buyer;

public interface BuyerService {
 /**
  * 
  * @Title: selectBuyerByUserName
  * @Description: 通过用户名获取用户信息
  * @param @param username
  * @param @return    
  * @return Buyer    
  * @throws
  */
 public Buyer selectBuyerByUserName(String username);
 /**
  * 
  * @Title: selectSkuById
  * @Description: 根据主键获取库存信息
  * @param @param id
  * @param @return    
  * @return Sku    
  * @throws
  */
 public Sku selectSkuById(Long id);
 /**
  * 
  * @Title: insertBuyerCartToRedis
  * @Description: 把购物车保存到redis中
  * @param @param username
  * @param @param buyerCart    
  * @return void    
  * @throws
  */
 
 public void insertBuyerCartToRedis(String username,BuyerCart buyerCart);
 /**
  * 
  * @Title: getBuyerCartFromRedis
  * @Description: 从redis中取出购物车
  * @param @param username
  * @param @return    
  * @return BuyerCart    
  * @throws
  */
 public BuyerCart getBuyerCartFromRedis(String username);
}
