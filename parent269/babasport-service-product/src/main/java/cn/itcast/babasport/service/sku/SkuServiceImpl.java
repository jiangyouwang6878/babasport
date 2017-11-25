package cn.itcast.babasport.service.sku;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
@Service("skuService")
public class SkuServiceImpl implements SkuService {
	@Resource
    private SkuMapper skuMapper;
	@Resource
	private ColorMapper colorMapper;
	@Override
	public List<Sku> selectSkusByProductId(Long productId) {
		// TODO Auto-generated method stub
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus=skuMapper.selectByExample(skuQuery);
		for (Sku sku : skus) {
			Color color=colorMapper.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
			
		}
		return skus;
	}
	/**
	 * 更新库存
	 */
	@Transactional
	@Override
	public void updateSku(Sku sku) {
		// TODO Auto-generated method stub
		skuMapper.updateByPrimaryKeySelective(sku);
	}

}
