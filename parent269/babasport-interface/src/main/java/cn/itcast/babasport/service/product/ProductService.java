package cn.itcast.babasport.service.product;

import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Product;

public interface ProductService {
   //商品的分页查询
   public Pagination selectProductsHavePage(String name,Long brandId,Boolean isShow,Integer pageNo);
   /**
    * 
   * @Title: insertProduct
   * @Description: 商品添加
   * @param @param product    
   * @return void    
   * @throws
    */
   public void insertProduct(Product product);
   /**
 * @throws Exception 
    * 
   * @Title: isShow
   * @Description: 商品上架
   * @param @param ids    
   * @return void    
   * @throws
    */
   public void isShow(Long[] ids) throws Exception;
   
   
  
}
