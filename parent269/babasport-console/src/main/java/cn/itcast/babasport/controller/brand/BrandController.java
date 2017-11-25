package cn.itcast.babasport.controller.brand;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;
import cn.itcast.babasport.service.brand.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandController {
	
    @Resource
	private BrandService brandService;
    
	@RequestMapping("/list.do")
	public String list(String name,@RequestParam(defaultValue="1") Integer isDisplay, Integer pageNo,Model model){
//		List<Brand> brands=brandService.selectBrandsNoPage(name, isDisplay);
//		model.addAttribute("brands", brands);
		
		Pagination pagination=brandService.selectBrandsHavePage(name, isDisplay, pageNo);
		model.addAttribute("pagination", pagination);
		//查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		model.addAttribute("pageNo", pageNo);
		return "brand/list";
	}
	//去更新页面
	@RequestMapping("/edit.do")
	public String edit(Long id,Model model){
		Brand brand=brandService.selectBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	//更新品牌
	@RequestMapping("/update.do")
	@Transactional
	public String update(Brand brand){
		brandService.updateBrand(brand);
		return "redirect:list.do";
	}
	
	//去添加页面
	@RequestMapping("/add.do")
	public String add(){
		
		return "brand/add";
	}
	
	//添加品牌
	@RequestMapping("/save.do")
	@Transactional
	public String save(Brand brand){
		brandService.saveBrand(brand);
		return "redirect:list.do";
	}
	
	//批量删除
	@Transactional
	@RequestMapping("/deleteBatchBrand.do")
	public String deleteBatchBrand(Long[] ids){
		brandService.deleteBatchBrand(ids);
		return "forward:list.do";
	}
	
	//单个删除
	@Transactional
	@RequestMapping("/deleteBrandById.do")
	public String deleteBrandById(Long id){
		brandService.deleteBrandById(id);
		return "forward:list.do";
	}
}
