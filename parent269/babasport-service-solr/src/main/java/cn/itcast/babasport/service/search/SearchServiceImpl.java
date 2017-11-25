package cn.itcast.babasport.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.product.ProductMapper;
import cn.itcast.babasport.mapper.product.SkuMapper;
import cn.itcast.babasport.pojo.page.Pagination;
import cn.itcast.babasport.pojo.product.Brand;
import cn.itcast.babasport.pojo.product.Product;
import cn.itcast.babasport.pojo.product.ProductQuery;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;
@Service("searchService")
public class SearchServiceImpl implements SearchService {
    /**
     * 根据关键字检索
     */
	@Resource
	private SolrServer solrServer;
	@Resource
	private SkuMapper skuMapper;
	@Resource
	private Jedis jedis;
	@Resource
	private ProductMapper productMapper;
	@Override
	public Pagination selectProductsForPortal(String keyWord,Long brandId,String price, Integer pageNo) throws Exception {
		// 创建solrQuery并封装查询条件
		SolrQuery solrQuery = new SolrQuery();
		ProductQuery productQuery = new ProductQuery();
		productQuery.setPageSize(Pagination.cpn(pageNo));
		productQuery.setPageSize(8);
		StringBuilder params = new StringBuilder();
		//根据关键字检索
		if(keyWord!=null && !"".equals(keyWord)){
			solrQuery.setQuery("name_ik:"+keyWord);
			params.append("keyWord=").append(keyWord);
		}
		//品牌顾虑
		if(brandId !=null){
			solrQuery.setQuery("brandId:"+brandId);
			params.append("&brandId=").append(brandId);
		}
		
		//价格过滤
		if(price!=null && !"".equals(price)){
			String[] prices=price.split("-");
			if(prices.length==2){
				solrQuery.addFilterQuery("price:["+prices[0]+" TO "+prices[1] +"]");
			}else{
				solrQuery.addFilterQuery("price:["+price+" TO *]");
			}
			params.append("&price=").append(price);
		}
		//关键字高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		solrQuery.setHighlightSimplePre("<font color='red' >");
		solrQuery.setHighlightSimplePost("</font >");
		
		//根据价格排序
		solrQuery.setSort("price", ORDER.asc);
		
		//结果分页
		solrQuery.setStart(productQuery.getStartRow());
		solrQuery.setRows(productQuery.getPageSize());
		
		//根据条件进行查询
		QueryResponse  queryResponse=solrServer.query(solrQuery);
		SolrDocumentList  results=queryResponse.getResults();//普通结果集
		//总条数
		int totalCount=(int) results.getNumFound();
		//高亮结果集
		Map<String, Map<String, List<String>>> h1 = queryResponse.getHighlighting();
		
		//处理结果集
		List<Product> list=new ArrayList<Product>();
		for(SolrDocument solrDocument: results){
			Product product=new Product();
			String id=solrDocument.get("id").toString();
			String imgUrl=solrDocument.get("url").toString();
			String price1=solrDocument.get("price").toString();
			String brandId1=solrDocument.get("brandId").toString();
			List<String> names=h1.get(id).get("name_ik");
			if(names!=null && names.size()>0){
				product.setName(names.get(0));
			}else{
				product.setName(solrDocument.get("name_ik").toString());
			}
			
			product.setBrandId(Long.parseLong(brandId1));
			product.setImgUrl(imgUrl);
			product.setPrice(price1);
			product.setId(Long.parseLong(id));
			list.add(product);
		}
		
		//创建分页对象，并填充数据
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, list);
		
		//构建分页工具类
		String url="/Search";
		pagination.pageView(url, params.toString());
		return pagination;
	}
	/*
	 * (非 Javadoc) 
	* <p>Title: selectBrandsFromRedis</p> 
	* <p>Description:检索页面获取品牌信息 </p> 
	* @return 
	* @see cn.itcast.babasport.service.search.SearchService#selectBrandsFromRedis()
	 */
	@Override
	public List<Brand> selectBrandsFromRedis() {
		// TODO Auto-generated method stub
		Map<String, String>  map=jedis.hgetAll("brand");
		Set<Entry<String, String>> entrySet=map.entrySet();
		List<Brand> brands=new ArrayList<Brand>();
		
		for (Entry<String, String> entry : entrySet) {
			Brand brand = new Brand();
			brand.setId(Long.parseLong(entry.getKey()));
			brand.setName(entry.getValue());
			brands.add(brand);
		}
		return brands;
	}
	/*
	 * (非 Javadoc) 
	* <p>Title: selectBrandNameByIdFromRedis</p> 
	* <p>Description: 从redis获取品牌</p> 
	* @param brandId
	* @return 
	* @see cn.itcast.babasport.service.search.SearchService#selectBrandNameByIdFromRedis(java.lang.Long)
	 */
	@Override
	public String selectBrandNameByIdFromRedis(Long brandId) {
		// TODO Auto-generated method stub
		String name=jedis.hget("brand", String.valueOf(brandId));
		return name;
	}
	/*
	 * (非 Javadoc) 
	* <p>Title: insertProductToSolr</p> 
	* <p>Description:保存商品信息到索引库中 </p> 
	* @param id 
	* @see cn.itcast.babasport.service.search.SearchService#insertProductToSolr(java.lang.Long)
	 */
	@Override
	public void insertProductToSolr(Long id) throws Exception {
		// TODO Auto-generated method stub
		SolrInputDocument doc = new SolrInputDocument();
		Product goods=productMapper.selectByPrimaryKey(id);
		doc.addField("id", id);
		doc.addField("name_ik", goods.getName());
		doc.addField("brandId", goods.getBrandId());
		doc.addField("url", goods.getImgUrl());
		
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.setFields("price");
		skuQuery.createCriteria().andProductIdEqualTo(id);
		skuQuery.setOrderByClause("price asc");
		skuQuery.setPageNo(1);
		skuQuery.setPageSize(1);
		List<Sku> skus=skuMapper.selectByExample(skuQuery);
		doc.addField("price", skus.get(0).getPrice());
		solrServer.add(doc);
		
		solrServer.commit();
	}

}
