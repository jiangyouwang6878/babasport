package cn.itcast.babasport.service.sessionprovider;

public interface SessionProvider {
  /**
   * 
   * @Title: setAttributeForUserName
   * @Description: 将用户名保存到redis
   * @param @param key
   * @param @param name    
   * @return void    
   * @throws
   */
  public void setAttributeForUserName(String key,String name);
  /**
   * 
   * @Title: getAttributeForUserName
   * @Description: 从redis取出用户名
   * @param @param key
   * @param @return    
   * @return String    
   * @throws
   */
  public String getAttributeForUserName(String key);
}
