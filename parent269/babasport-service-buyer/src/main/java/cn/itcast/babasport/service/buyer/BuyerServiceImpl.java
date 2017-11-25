package cn.itcast.babasport.service.buyer;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.mapper.user.BuyerMapper;
import cn.itcast.babasport.pojo.cart.BuyerCart;
import cn.itcast.babasport.pojo.cart.BuyerItem;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.user.Buyer;
import cn.itcast.babasport.pojo.user.BuyerQuery;
import redis.clients.jedis.Jedis;
@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {
	@Resource
	private BuyerMapper buyerMapper;
	@Resource
	private SkuMapper skuMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private ColorMapper colorMapper;
	@Resource
	private Jedis jedis;
    /*
     * (非 Javadoc) 
     * <p>Title: selectBuyerByUserName</p> 
     * <p>Description: 通过用户名获取用户信息</p> 
     * @param username
     * @return 
     * @see cn.itcast.babasport.service.buyer.BuyerService#selectBuyerByUserName(java.lang.String)
     */
	@Override
	public Buyer selectBuyerByUserName(String username) {
		// TODO Auto-generated method stub
		BuyerQuery buyerQuery = new BuyerQuery();
		buyerQuery.createCriteria().andUsernameEqualTo(username);
		List<Buyer> list=buyerMapper.selectByExample(buyerQuery);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/*
	 * (非 Javadoc) 
	 * <p>Title: selectSkuById</p> 
	 * <p>Description:根据主键获取库存信息 </p> 
	 * @param id
	 * @return 
	 * @see cn.itcast.babasport.service.buyer.BuyerService#selectSkuById(java.lang.Long)
	 */
	@Override
	public Sku selectSkuById(Long id) {
		// TODO Auto-generated method stub
		Sku sku=skuMapper.selectByPrimaryKey(id);
		sku.setProduct(productMapper.selectByPrimaryKey(sku.getProductId()));
		sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		return sku;
	}
	/*
	 * (非 Javadoc) 
	 * <p>Title: insertBuyerCartToRedis</p> 
	 * <p>Description: 把购物车插入到redis</p> 
	 * @param username
	 * @param buyerCart 
	 * @see cn.itcast.babasport.service.buyer.BuyerService#insertBuyerCartToRedis(java.lang.String, cn.itcast.babasport.pojo.cart.BuyerCart)
	 */
	@Override
	public void insertBuyerCartToRedis(String username, BuyerCart buyerCart) {
		// TODO Auto-generated method stub
		List<BuyerItem> buyerItems=buyerCart.getBuyerItems();
		for (BuyerItem buyerItem : buyerItems) {
			Long skuId=buyerItem.getSku().getId();
			Integer amount=buyerItem.getAmount();
			if(jedis.hexists("BUYER_CART:"+username, String.valueOf(skuId))){
				jedis.hincrBy("BUYER_CART:"+username,String.valueOf(skuId), amount);
			}else{				
				jedis.hset("BUYER_CART:"+username, String.valueOf(skuId), String.valueOf(amount));
			}
		}
	}
	/*
	 * (非 Javadoc) 
	 * <p>Title: getBuyerCartFromRedis</p> 
	 * <p>Description:从redis中取出购物车 </p> 
	 * @param username
	 * @return 
	 * @see cn.itcast.babasport.service.buyer.BuyerService#getBuyerCartFromRedis(java.lang.String)
	 */
	@Override
	public BuyerCart getBuyerCartFromRedis(String username) {
		// TODO Auto-generated method stub
		Map<String,String> map=jedis.hgetAll("BUYER_CART:"+username);
		Set<Entry<String, String>>  entrySet=map.entrySet();
		BuyerCart buyerCart = new BuyerCart();
		for (Entry<String, String> entry : entrySet) {
			BuyerItem buyerItem = new BuyerItem();
			Sku sku = new Sku();
			sku.setId(Long.parseLong(entry.getKey()));
			buyerItem.setSku(sku);
			buyerItem.setAmount(Integer.parseInt(entry.getValue()));
			buyerCart.addBuyItem(buyerItem);
		}
		return buyerCart;
	}

}
