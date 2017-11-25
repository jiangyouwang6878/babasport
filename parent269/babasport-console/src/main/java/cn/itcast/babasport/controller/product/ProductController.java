package cn.itcast.babasport.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.service.brand.BrandService;
import cn.itcast.babasport.service.color.ColorService;
import cn.itcast.babasport.service.product.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Resource
	private BrandService brandService;
	@Resource
	private ProductService productService;
	@Resource
	private ColorService colorService;
     //查询商品的列表页面
	@RequestMapping("/list.do")
	public String list(String name,Long brandId,Integer pageNo,Boolean isShow,Model model){
		//初始化品牌列表
		List<Brand> brands=brandService.selectBrandsNoPage(null, 1);
		model.addAttribute("brands", brands);
		//商品列表信息
		Pagination pagination=productService.selectProductsHavePage(name, brandId, isShow, pageNo);
		model.addAttribute("pagination", pagination);
		//数据回显
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("isShow", isShow);
		return "product/list";
	}
	
	//去添加页面
	@RequestMapping("/toAdd.do")
	public String toAdd(Model model){
		//初始化品牌列表
	    List<Brand> brands=brandService.selectBrandsNoPage(null, 1);
		model.addAttribute("brands", brands);
		
		//初始化颜色信息
		List<Color> colors=colorService.selectColorsAndParentIdNotZero();
		model.addAttribute("colors", colors);
		return "product/add";
	}
	
	//添加商品
	@RequestMapping("/save.do")
	public String  save(Product product){
		productService.insertProduct(product);
		return "redirect:list.do";
	}
	@RequestMapping("/isShow.do")
	public String isShow(Long[] ids) throws Exception{
		productService.isShow(ids);
		return "redirect:list.do";
	}
}
