package cn.itcast.babasport.service.cms;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
@Service("cmsService")
public class CmsServiceImpl implements CmsService {
	@Resource
    private ProductMapper productMapper;
	@Resource
    private SkuMapper skuMapper;
	@Resource
	private ColorMapper colorMapper;
	/**
	 * 获取商品信息
	 */
	@Override
	public Product selectProductById(Long id) {
		// TODO Auto-generated method stub
		Product product=productMapper.selectByPrimaryKey(id);
		return product;
	}
    /*
     * (非 Javadoc) 
    * <p>Title: selectSkusByPIdAndStockMoreThanZero</p> 
    * <p>Description: 获取库存信息</p> 
    * @param pid
    * @return 
    * @see cn.itcast.babasport.service.cms.CmsService#selectSkusByPIdAndStockMoreThanZero(java.lang.Long)
     */
	@Override
	public List<Sku> selectSkusByPIdAndStockMoreThanZero(Long pid) {
		// TODO Auto-generated method stub
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(pid);
		skuQuery.createCriteria().andStockGreaterThan(0);
		List<Sku> skus=skuMapper.selectByExample(skuQuery);
		//填充颜色
		for (Sku sku : skus) {
			sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		}
		return skus;
	}

}
