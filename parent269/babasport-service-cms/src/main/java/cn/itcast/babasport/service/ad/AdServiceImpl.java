package cn.itcast.babasport.service.ad;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.mapper.ad.AdvertisingMapper;
import cn.itcast.babasport.mapper.ad.PositionMapper;
import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.pojo.ad.AdvertisingQuery;
import cn.itcast.babasport.pojo.ad.Position;
import cn.itcast.babasport.pojo.ad.PositionQuery;
import cn.itcast.babasport.utils.json.JsonUtils;
import redis.clients.jedis.Jedis;
@Service("adService")
public class AdServiceImpl implements AdService {
	@Resource
	private Jedis jedis;
	@Resource
	private PositionMapper positionMapper;
	@Resource
	private AdvertisingMapper advertisingMapper;
    /*
     * (非 Javadoc) 
    * <p>Title: selectPosByParentId</p> 
    * <p>Description:根据父节点获取对应子节点 </p> 
    * @param parentId
    * @return 
    * @see cn.itcast.babasport.service.ad.AdService#selectPosByParentId(java.lang.Long)
     */
	@Override
	public List<Position> selectPosByParentId(Long parentId) {
		// TODO Auto-generated method stub
		PositionQuery positionQuery = new PositionQuery();
		positionQuery.createCriteria().andParentIdEqualTo(parentId);
		List<Position> list=positionMapper.selectByExample(positionQuery);
		return list;
	}
	@Override
	public List<Advertising> selectAdsByPosId(Long positionId) {
		// TODO Auto-generated method stub
		AdvertisingQuery advertisingQuery = new AdvertisingQuery();
		advertisingQuery.createCriteria().andPositionIdEqualTo(positionId);
		List<Advertising> ads=advertisingMapper.selectByExample(advertisingQuery);
		for (Advertising ad : ads) {
			ad.setPosition(positionMapper.selectByPrimaryKey(ad.getPositionId()));
		}
		return ads;
	}
	@Override
	public void insertAd(Advertising advertising) {
		// TODO Auto-generated method stub
		advertisingMapper.insertSelective(advertising);
	}
	/*
	 * (非 Javadoc) 
	* <p>Title: selectAdsByPosIdForPortal</p> 
	* <p>Description:前台系统轮播大广告数据 </p> 
	* @param positionId
	* @return 
	* @see cn.itcast.babasport.service.ad.AdService#selectAdsByPosIdForPortal(java.lang.Long)
	 */
	@Override
	public String selectAdsByPosIdForPortal(Long positionId) {
		String ads=jedis.get("ads");
		if(ads==null){
		// TODO Auto-generated method stub
		AdvertisingQuery advertisingQuery = new AdvertisingQuery();
		advertisingQuery.createCriteria().andPositionIdEqualTo(positionId);
		List<Advertising> list=advertisingMapper.selectByExample(advertisingQuery);
		ads=JsonUtils.parseObjectToJson(list);
		jedis.set("ads", ads);
		}
		return ads;
	}

}
