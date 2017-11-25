package cn.itcast.babasport.controller.ad;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.service.ad.AdService;
@RequestMapping("/ad")
@Controller
public class AdController {
  @Resource
  private AdService adService;
  /**
   * 
  * @Title: list
  * @Description: 根据位置查询广告列表
  * @param @param id
  * @param @param model
  * @param @return    
  * @return String    
  * @throws
   */
  @RequestMapping("/list.do")
  public String list(Long root,Model model){
	  List<Advertising> ads=adService.selectAdsByPosId(root);
	  model.addAttribute("ads", ads);
	  model.addAttribute("positionId", root);
	  return "ad/list";
  }
  
  /**
   * 
  * @Title: add
  * @Description: 跳转到添加页面
  * @param @param positionId
  * @param @param model
  * @param @return    
  * @return String    
  * @throws
   */
  @RequestMapping("/add.do")
  public String add(Long positionId,Model model){
	  model.addAttribute("positionId", positionId);
	  return "ad/add";
  }
  /**
   * 
  * @Title: save
  * @Description: 添加大广告
  * @param @param advertising
  * @param @return    
  * @return String    
  * @throws
   */
  @RequestMapping("/save.do")
  public  String save(Advertising advertising){
	  adService.insertAd(advertising);
	  return "redirect:list.do?root="+advertising.getPositionId();
  }
}
