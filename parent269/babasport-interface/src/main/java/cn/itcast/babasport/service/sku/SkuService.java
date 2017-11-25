package cn.itcast.babasport.service.sku;

import java.util.List;

import cn.itcast.babasport.pojo.product.Sku;

public interface SkuService {
    /**
     * 
    * @Title: selectSkusByProductId
    * @Description: 查询商品对应的库存信息
    * @param @param productId
    * @param @return    
    * @return List<Sku>    
    * @throws
     */
	
	public List<Sku> selectSkusByProductId(Long productId);
	
	/**
	 * 
	* @Title: updateSku
	* @Description: 更新库存
	* @param @param sku    
	* @return void    
	* @throws
	 */
	public void updateSku(Sku sku);
}
