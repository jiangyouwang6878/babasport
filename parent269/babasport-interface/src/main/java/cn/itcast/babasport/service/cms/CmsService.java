package cn.itcast.babasport.service.cms;

import java.util.List;

import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;

public interface CmsService {
	/**
	 * 
	* @Title: selectProductById
	* @Description: 根据商品id
	* @param @param id
	* @param @return    
	* @return Product    
	* @throws
	 */
   public Product selectProductById(Long id);
   /**
    * 
   * @Title: selectSkusByPIdAndStockMoreThanZero
   * @Description: 获取商品 对应的库存信息
   * @param @param pid
   * @param @return    
   * @return List<Sku>    
   * @throws
    */
   public List<Sku> selectSkusByPIdAndStockMoreThanZero(Long pid);
   
}
