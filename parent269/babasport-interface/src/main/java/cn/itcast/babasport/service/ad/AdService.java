package cn.itcast.babasport.service.ad;

import java.util.List;

import cn.itcast.babasport.pojo.ad.Advertising;
import cn.itcast.babasport.pojo.ad.Position;

public interface AdService {
  /**
   * 
  * @Title: selectPosByParentId
  * @Description: 根据父节点获取对应子节点
  * @param @param parentId
  * @param @return    
  * @return List<Position>    
  * @throws
   */
  public List<Position> selectPosByParentId(Long parentId);
  /**
   * 
  * @Title: selectAdsByPosId
  * @Description: 查询广告位下的列表信息
  * @param @param positionId
  * @param @return    
  * @return List<Advertising>    
  * @throws
   */
  public List<Advertising> selectAdsByPosId(Long positionId);
  /**
   * 
  * @Title: insertAd
  * @Description: 添加大广告
  * @param @param advertising    
  * @return void    
  * @throws
   */
  public void insertAd(Advertising advertising);
  /**
   * 
  * @Title: selectAdsByPosIdForPortal
  * @Description: 前台系统轮播大广告数据
  * @param @param positionId
  * @param @return    
  * @return String    
  * @throws
   */
  public String selectAdsByPosIdForPortal(Long positionId);
}
