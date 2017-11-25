package cn.itcast.babasport.service.order;

import cn.itcast.babasport.pojo.order.Order;

public interface OrderService {
   /**
    * 
    * @Title: insertOrder
    * @Description: 生成订单
    * @param @param username
    * @param @param order    
    * @return void    
    * @throws
    */
   public void insertOrder(String username,Order order);
}
