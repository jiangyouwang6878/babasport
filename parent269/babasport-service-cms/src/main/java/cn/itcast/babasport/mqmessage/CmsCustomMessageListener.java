package cn.itcast.babasport.mqmessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

import cn.itcast.babasport.pojo.product.Color;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.service.cms.CmsService;
import cn.itcast.babasport.service.staticpage.StaticPageService;

public class CmsCustomMessageListener implements MessageListener {
	@Resource
    private CmsService cmsService;
	@Resource
	private StaticPageService staticPageService;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
   try {
	   //获取消息
    	ActiveMQTextMessage activeMQTextMessage=(ActiveMQTextMessage) message;
		String id=activeMQTextMessage.getText();
		System.out.println("cms--id:"+id);
		//消费信息-处理业务
		Map<String,Object> rootMap=new HashMap<String,Object>();
		Product product=cmsService.selectProductById(Long.parseLong(id));
		rootMap.put("product", product);//商品数据
		List<Sku> skus=cmsService.selectSkusByPIdAndStockMoreThanZero(product.getId());
		rootMap.put("skus", skus);//库存数据
		Set<Color> colors=new HashSet<Color>();
		for(Sku sku:skus){
			colors.add(sku.getColor());
		}
		rootMap.put("colors", colors);//颜色数据
		staticPageService.index(id, rootMap);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
