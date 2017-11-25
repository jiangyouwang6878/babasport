package cn.itcast.babasport.service.color;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.ColorMapper;
import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.ColorQuery;
import cn.itcast.babasport.pojo.product.ColorQuery.Criteria;
@Service("colorService")
public class ColorServiceImpl implements ColorService {
	@Resource
	private ColorMapper colorMapper;
    /**
     * 颜色信息
     */
	@Override
	public List<Color> selectColorsAndParentIdNotZero() {
		// TODO Auto-generated method stub
		ColorQuery colorQuery = new ColorQuery();
		Criteria criteria=colorQuery.createCriteria().andParentIdNotEqualTo(0L);
		List<Color> lists=colorMapper.selectByExample(colorQuery);
		return lists;
	}

}
