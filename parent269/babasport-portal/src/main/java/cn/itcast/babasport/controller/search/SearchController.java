package cn.itcast.babasport.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.ad.AdvertisingQuery;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.service.ad.AdService;
import cn.itcast.babasport.service.cms.CmsService;
import cn.itcast.babasport.service.search.SearchService;
/**
 * 前台系统检索
* @ClassName: SearchController
* @Description: TODO
* @author 
* @date 2017年11月15日 下午2:09:40
*
 */
@Controller
public class SearchController {
	@Resource
    private SearchService searchService;
	@Resource
	private CmsService cmsService;
	@Resource
	private AdService adService;
	@RequestMapping("/")
	public String index(Model model){
		String ads=adService.selectAdsByPosIdForPortal(89L);
		model.addAttribute("ads",ads);
		return "index";
	}
	/**
	 * 
	* @Title: search
	* @Description: 搜索页
	* @param @param keyword
	* @param @param brandId
	* @param @param price
	* @param @param pageNo
	* @param @param model
	* @param @return
	* @param @throws Exception    
	* @return String    
	* @throws
	 */
	@RequestMapping("/Search")
	public String search(String keyword,Long brandId,String price,Integer pageNo,Model model) throws Exception{
		List<Brand> brands=searchService.selectBrandsFromRedis();
		model.addAttribute("brands", brands);
		Pagination pagination=searchService.selectProductsForPortal(keyword, brandId,price,pageNo);
		model.addAttribute("pagination", pagination);
		
		//检索条件回显
		model.addAttribute("keyword", keyword);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		
		//筛选条件回显
		Map<String,String> map=new HashMap<String,String>();
		if(brandId!=null){
			map.put("品牌", searchService.selectBrandNameByIdFromRedis(brandId));
		}
		
		if(price!=null && !"".equals(price)){
			String[] prices=price.split("-");
			if(prices.length==2){
				map.put("价格", price);
			}else{
				map.put("价格", price+"以上");
			}
		}
		model.addAttribute("map", map);
		return "search";
	}
	@RequestMapping("/product/detail")
	public String detail(Long id,Model model){
		//商品信息
		Product product=cmsService.selectProductById(id);
		model.addAttribute("product", product);
		//库存信息
		List<Sku> skus=cmsService.selectSkusByPIdAndStockMoreThanZero(id);
		model.addAttribute("skus", skus);
		//颜色信息
		Set<Color> colors=new HashSet<>();
		for(Sku sku:skus){
			colors.add(sku.getColor());
		}
		model.addAttribute("colors", colors);
		return "product";
	}
}
