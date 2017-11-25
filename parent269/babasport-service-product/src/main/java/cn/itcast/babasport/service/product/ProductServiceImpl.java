package cn.itcast.babasport.service.product;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.ProductQuery;
import cn.itcast.babasport.pojo.product.ProductQuery.Criteria;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;
@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Resource
	private ProductMapper productMapper;
	@Resource
	private SolrServer solrServer;
	@Resource
	private Jedis jedis;
	@Resource
	private SkuMapper skuMapper;
	@Resource
	private JmsTemplate jmsTemplate;
    //查询商品的分页，带条件查询
	@Override
	public Pagination selectProductsHavePage(String name, Long brandId, Boolean isShow, Integer pageNo) {
		// TODO Auto-generated method stub
		ProductQuery productQuery = new ProductQuery();
		Criteria criteria=productQuery.createCriteria();
		StringBuilder params=new StringBuilder();
		if(name!=null && !"".equals(name)){
			criteria.andNameLike("%"+name+"%");
			params.append("name=").append(name);
		}
		
		if(brandId !=null){
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		
		if(isShow!=null){
			criteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		}else{
			criteria.andIsShowEqualTo(false);
			params.append("&isShow=").append(false);
		}
		
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(5);
		productQuery.setOrderByClause("id desc");
		
		List<Product> list=productMapper.selectByExample(productQuery);
		int totalCount=productMapper.countByExample(productQuery);
		Pagination pagination=new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, list);
		
		String url="/product/list.do";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	/**
	 * 商品添加
	 */
	@Override
	@Transactional
	public void insertProduct(Product product) {
		// TODO Auto-generated method stub
		//通过redis生成商品id
		Long id=jedis.incr("pno");
		product.setId(id);
	    //保存商品
		product.setIsShow(false);//默认下架
		product.setCreateTime(new Date());//创建时间
		productMapper.insertSelective(product);
		
		//初始化商品信息
		String[] colors=product.getColors().split(",");
		String[] sizes=product.getSizes().split(",");
		for (String size : sizes) {
			for(String color:colors){
				Sku sku=new Sku();
				//sku.setProductId(product.getId());
				sku.setProductId(id);
				sku.setColorId(Long.parseLong(color));
				sku.setSize(size);
				sku.setMarketPrice(0f);
				sku.setPrice(0f);
				sku.setDeliveFee(0f);
				sku.setStock(0);
				sku.setUpperLimit(0);
				sku.setCreateTime(new Date());
				skuMapper.insertSelective(sku);
				
			}
		}
	}
	
	/**
	 * 商品上架
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public void isShow(Long[] ids) throws Exception {
		// TODO Auto-generated method stub
		Product product=new Product();
		product.setIsShow(true);
		for (final Long id : ids) {
			product.setId(id);
			productMapper.updateByPrimaryKeySelective(product);
			//将商品id发送到ActiveMQ中
			jmsTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					// TODO Auto-generated method stub
					TextMessage textMessage=session.createTextMessage(String.valueOf(id));
					return textMessage;
				}
			});
			//将商品保存到solr中
//			Product good=productMapper.selectByPrimaryKey(id);
//			SolrInputDocument doc=new SolrInputDocument();
//			doc.addField("id", id);
//			doc.addField("name_ik", good.getName());
//			doc.addField("url", good.getImgUrl());
//			doc.addField("brandId", good.getBrandId());
//			//查询商品最低价格
//			SkuQuery skuQuery = new SkuQuery();
//			skuQuery.createCriteria().andProductIdEqualTo(id);
//			skuQuery.setFields("price");
//			skuQuery.setOrderByClause("price asc");
//			skuQuery.setPageNo(1);
//			skuQuery.setPageSize(1);
//			Sku sku=skuMapper.selectByExample(skuQuery).get(0);
//			doc.addField("price", sku.getPrice());
//		    solrServer.add(doc);
//		    solrServer.commit();
		}
	}
	

}
