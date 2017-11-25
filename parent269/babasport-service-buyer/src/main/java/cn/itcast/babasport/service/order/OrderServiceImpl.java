package cn.itcast.babasport.service.order;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.order.DetailMapper;
import cn.itcast.babasport.mapper.order.OrderMapper;
import cn.itcast.babasport.pojo.cart.BuyerCart;
import cn.itcast.babasport.pojo.cart.BuyerItem;
import cn.itcast.babasport.pojo.order.Detail;
import cn.itcast.babasport.pojo.order.Order;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.service.buyer.BuyerService;
import redis.clients.jedis.Jedis;
@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Resource
	private Jedis jedis;
	@Resource
	private BuyerService buyerService;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private DetailMapper detailMapper;
    /*
     * (非 Javadoc) 
     * <p>Title: insertOrder</p> 
     * <p>Description:生成订单信息 </p> 
     * @param username
     * @param order 
     * @see cn.itcast.babasport.service.order.OrderService#insertOrder(java.lang.String, cn.itcast.babasport.pojo.order.Order)
     */
	@Override
	public void insertOrder(String username, Order order) {
		// TODO Auto-generated method stub
       Long oid=jedis.incr("oid");
       order.setId(oid);
       BuyerCart buyerCart=buyerService.getBuyerCartFromRedis(username);
       List<BuyerItem> buyerItems=buyerCart.getBuyerItems();
       for (BuyerItem buyerItem : buyerItems) {
		buyerItem.setSku(buyerService.selectSkuById(buyerItem.getSku().getId()));
	}
       order.setDeliverFee(buyerCart.getFee());
       order.setOrderPrice(buyerCart.getProductPrice());
       order.setTotalPrice(buyerCart.getTotalPrice());
       
       if(order.getPaymentWay()==1){
    	   order.setIsPaiy(1);
       }
       
       order.setOrderState(0);
       order.setCreateDate(new Date());
       order.setBuyerId(1L);
       
       orderMapper.insertSelective(order);
       
       //生成订单明细表
       for (BuyerItem buyerItem : buyerItems) {
		Detail detail = new Detail();
		Sku sku=buyerItem.getSku();
		detail.setOrderId(oid);
		detail.setPrice(sku.getPrice());
		detail.setProductId(sku.getProductId());
		detail.setProductName(sku.getProduct().getName());
		detail.setSize(sku.getSize());
		detail.setColor(sku.getColor().getName());
		detail.setAmount(buyerItem.getAmount());
		detailMapper.insertSelective(detail);
	}
       //删除购物车
       jedis.del("BUYER_CART:"+username);
	}

}
