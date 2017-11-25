package cn.itcast.babasport.mapper.product;

import java.util.List;

import cn.itcast.babasport.pojo.product.Brand;
import cn.itcast.babasport.pojo.product.BrandQuery;

public interface BrandMapper {
  /**
   * 查询品牌列表不分页
   */
	public List<Brand> selectBrandsNoPage(BrandQuery brandQuery);
	
  /**
   * 查询品牌列表分页
   */
	public List<Brand> selectBrandsHavePage(BrandQuery brandQuery);
  
  /**
   * 查询品牌列表总条数
   */
	public int selectBrandCount(BrandQuery brandQuery);
	
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
