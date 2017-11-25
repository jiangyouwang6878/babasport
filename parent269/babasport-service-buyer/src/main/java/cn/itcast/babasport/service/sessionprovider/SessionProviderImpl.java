package cn.itcast.babasport.service.sessionprovider;

import javax.annotation.Resource;

import redis.clients.jedis.Jedis;

public class SessionProviderImpl implements SessionProvider {
	private Integer expUser=60;
	
	public void setExpUser(Integer expUser) {
		this.expUser = expUser;
	}

	@Resource
    private Jedis jedis;
	/*
	 * (非 Javadoc) 
	 * <p>Title: setAttributeForUserName</p> 
	 * <p>Description:把用户信息存到redis中 </p> 
	 * @param key
	 * @param name 
	 * @see cn.itcast.babasport.service.sessionprovider.SessionProvider#setAttributeForUserName(java.lang.String, java.lang.String)
	 */
	@Override
	public void setAttributeForUserName(String key, String name) {
		// TODO Auto-generated method stub
      jedis.set("USER_SESSION:"+key, name);
      //设置过期时间
      jedis.expire("USER_SESSION:"+key, 60*expUser);
	}
    /*
     * (非 Javadoc) 
     * <p>Title: getAttributeForUserName</p> 
     * <p>Description: 从redis数据库中取出用户信息</p> 
     * @param key
     * @return 
     * @see cn.itcast.babasport.service.sessionprovider.SessionProvider#getAttributeForUserName(java.lang.String)
     */
	@Override
	public String getAttributeForUserName(String key) {
		// TODO Auto-generated method stub
		String username=jedis.get("USER_SESSION:"+key);
		if(username!=null){
			 //设置过期时间
		    jedis.expire("USER_SESSION:"+key, 60*expUser);
			return username;
		}
		return null;
	}

}
