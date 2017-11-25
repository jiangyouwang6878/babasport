package cn.itcast.babasport.service.color;

import java.util.List;

import cn.itcast.babasport.pojo.product.Color;

public interface ColorService {
	/**
	 * 
	* @Title: selectColorsAndParentIdNotZero
	* @Description: 颜色信息
	* @param @return    
	* @return List<Color>    
	* @throws
	 */
   public List<Color> selectColorsAndParentIdNotZero();
}
