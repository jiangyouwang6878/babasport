package cn.itcast.babasport.service.brand;

import java.util.List;

import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;

public interface BrandService {
  /**
   * 查询品牌管理列表不分页
   */
	public List<Brand> selectBrandsNoPage(String name,Integer isDisplay);
  
  /**
   * 查询品牌管理列表分页
   */
	public Pagination selectBrandsHavePage(String name,Integer isDisplay,Integer pageNo);
	
  /**
    * 根据主键查询
	*/
	public Brand selectBrandById(Long id);
	
	
  /**
   * 品牌更新	
   */
	public void updateBrand(Brand brand);
	
  /**
   * 品牌添加	
   */
	public void saveBrand(Brand brand);
	
  /**
	* 批量删除	
   */
	public void deleteBatchBrand(Long[] ids);
	
  /**
	* 单个删除	
	*/
	public void deleteBrandById(Long id);	
}
