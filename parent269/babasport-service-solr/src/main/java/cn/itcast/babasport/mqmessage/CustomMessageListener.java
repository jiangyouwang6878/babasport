package cn.itcast.babasport.mqmessage;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

import cn.itcast.babasport.service.search.SearchService;

/**
 * 
 * @ClassName: CustomMessageListener
 * @Company: http://www.itcast.cn/
 * @Description: 自定义消息监听器
 * @author 阮文 
 * @date 2017年11月15日 下午12:20:56
 */
public class CustomMessageListener implements MessageListener {
	
	@Resource
	private SearchService searchService;

	/*
	 * (non-Javadoc)
	 * <p>Title: onMessage</p> 
	 * <p>Description: 商品信息保存到索引库中</p> 
	 * @param arg0 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			String id = activeMQTextMessage.getText(); // 取出消息
			System.out.println("solr---id:"+id);
			// 消费消息
			searchService.insertProductToSolr(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

}
