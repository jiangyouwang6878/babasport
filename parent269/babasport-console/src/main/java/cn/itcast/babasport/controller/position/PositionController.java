package cn.itcast.babasport.controller.position;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.ad.Position;
import cn.itcast.babasport.service.ad.AdService;

@Controller
@RequestMapping("/position")
public class PositionController {
	@Resource
	private AdService adService;
	/**
	 * 
	* @Title: tree
	* @Description: 根据父节点获取对应子节点 
	* @param @param root
	* @param @param model
	* @param @return    
	* @return String    
	* @throws
	 */
	@RequestMapping("/tree.do")
	public String tree(String root,Model model){
		List<Position> list=null;
		if("source".equals(root)){
			list=adService.selectPosByParentId(0L);
		}else{
			list=adService.selectPosByParentId(Long.parseLong(root));
		}
		model.addAttribute("list", list);
		return "position/tree";
	}
    
}
