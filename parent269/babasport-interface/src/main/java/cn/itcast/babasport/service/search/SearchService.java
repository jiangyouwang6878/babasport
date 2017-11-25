package cn.itcast.babasport.service.search;

import java.util.List;

import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;

public interface SearchService {
	/**
	 * @throws Exception 
	 * 
	* @Title: selectProductsForPortal
	* @Description: 根据关键字检索
	* @param @param keyWord
	* @param @param pageNO
	* @param @return    
	* @return Pagination    
	* @throws
	 */
   public Pagination selectProductsForPortal(String keyWord,Long brandId,String price,Integer pageNO) throws Exception;
   /**
    * 
   * @Title: selectBrandsFromRedis
   * @Description: 检索页面获取品牌信息
   * @param @return    
   * @return List<Brand>    
   * @throws
    */
   public List<Brand> selectBrandsFromRedis();
   /**
    * 
   * @Title: selectBrandNameByIdFromRedis
   * @Description: 从redis获取名称
   * @param @param brandId
   * @param @return    
   * @return String    
   * @throws
    */
   public String selectBrandNameByIdFromRedis(Long brandId);
   /**
 * @throws Exception 
    * 
   * @Title: insertProductToSolr
   * @Description: 将商品信息保存到索引库中
   * @param @param id    
   * @return void    
   * @throws
    */
   public void insertProductToSolr(Long id) throws Exception;
}
