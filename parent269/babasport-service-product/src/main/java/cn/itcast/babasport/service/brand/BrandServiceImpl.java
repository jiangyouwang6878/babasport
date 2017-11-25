package cn.itcast.babasport.service.brand;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.BrandMapper;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;
import cn.itcast.babasport.pojo.product.BrandQuery;
import redis.clients.jedis.Jedis;

@Service("brandService")
public class BrandServiceImpl implements BrandService {
	@Resource
    private BrandMapper brandMapper;
	@Resource
	private Jedis jedis;
	//列表查询
	@Override
	public List<Brand> selectBrandsNoPage(String name, Integer isDisplay) {
		// TODO Auto-generated method stub
		BrandQuery brandQuery = new BrandQuery();
		if(name !=null && !"".equals(name)){
			brandQuery.setName(name);
		}
		
		if(isDisplay !=null){
			brandQuery.setIsDisplay(isDisplay);
		}
		List<Brand> brands=brandMapper.selectBrandsNoPage(brandQuery);
		return brands;
	}
	//分页查询
	@Override
	public Pagination selectBrandsHavePage(String name,Integer isDisplay,Integer pageNo) {
		// TODO Auto-generated method stub
		BrandQuery brandQuery=new BrandQuery();
		StringBuilder params = new StringBuilder();
		if(name !=null && !"".equals(name)){
			brandQuery.setName(name);
			params.append("name=").append(name);
		}
		
		if(isDisplay !=null){
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}
		
		brandQuery.setPageSize(4);
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		
		List<Brand> brands=brandMapper.selectBrandsHavePage(brandQuery);
		int totalCount=brandMapper.selectBrandCount(brandQuery);
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(), totalCount,brands);
		
		String url="/brand/list.do";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	//品牌查询
	@Override
	public Brand selectBrandById(Long id) {
		// TODO Auto-generated method stub
		Brand brand=brandMapper.selectBrandById(id);
		return brand;
	}
	//品牌更新
	@Override
	public void updateBrand(Brand brand) {
		// TODO Auto-generated method stub
		brandMapper.updateBrand(brand);
		String id = String.valueOf(brand.getId());
		jedis.hset("brand", id, brand.getName());
	}
	//品牌添加
	@Override
	public void saveBrand(Brand brand) {
		// TODO Auto-generated method stub
		brandMapper.saveBrand(brand);
		jedis.hset("brand", String.valueOf(brand.getId()), brand.getName());
	}
	@Override
	public void deleteBatchBrand(Long[] ids) {
		// TODO Auto-generated method stub
		brandMapper.deleteBatchBrand(ids);
	}
	@Override
	public void deleteBrandById(Long id) {
		// TODO Auto-generated method stub
		brandMapper.deleteBrandById(id);
	}

}
