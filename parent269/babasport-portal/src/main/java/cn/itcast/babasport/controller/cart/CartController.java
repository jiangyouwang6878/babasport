package cn.itcast.babasport.controller.cart;

import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.babasport.pojo.cart.BuyerCart;
import cn.itcast.babasport.pojo.cart.BuyerItem;
import cn.itcast.babasport.pojo.order.Order;
import cn.itcast.babasport.pojo.product.Sku;
import cn.itcast.babasport.service.buyer.BuyerService;
import cn.itcast.babasport.service.order.OrderService;
import cn.itcast.babasport.service.sessionprovider.SessionProvider;
import cn.itcast.babasport.utils.token.TokenUtils;
/**
 * 
 * @ClassName: CartController
 * @Description: 购物车
 * @author 
 * @date 2017年11月20日 下午4:20:15
 *
 */
@Controller
public class CartController {
	@Resource
	private BuyerService buyerService;
	@Resource
	private OrderService orderService;
	@Resource
	private SessionProvider sessionProvider;
	/**
	 * 
	 * @Title: buyerCart
	 * @Description: 添加购物车
	 * @param @param skuId
	 * @param @param amount
	 * @param @param model
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/shopping/buyerCart")
    public String buyerCart(Long skuId,Integer amount,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuyerCart buyerCart=null;
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		Cookie[] cookies=request.getCookies();
		for (Cookie cookie : cookies) {
			if("BUYER_CART".equals(cookie.getName())){
				String content=cookie.getValue();
				buyerCart=om.readValue(content, BuyerCart.class);
				break;
			}
		}
		
		if(buyerCart==null){
			buyerCart=new BuyerCart();
		}
		
		
		BuyerItem buyerItem = new BuyerItem();
		buyerItem.setAmount(amount);
		//Sku sku=buyerService.selectSkuById(skuId);
		Sku sku=new Sku();
		sku.setId(skuId);
		buyerItem.setSku(sku);
		buyerCart.addBuyItem(buyerItem);
		
		String username=sessionProvider.getAttributeForUserName(TokenUtils.getCSESSIONID(request, response));
		if(username!=null){
			if(buyerCart!=null){
				buyerService.insertBuyerCartToRedis(username, buyerCart);
				Cookie cookie=new Cookie("BUYER_CART",null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}else{
			
			StringWriter w=new StringWriter();
			om.writeValue(w, buyerCart);
			Cookie cookie=new Cookie("BUYER_CART",w.toString());
			cookie.setPath("/");
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
		}
		//jsp页面回显
		/*List<BuyerItem> buyerItems=buyerCart.getBuyerItems();
		if(buyerItems!=null && buyerItems.size()>0){
			
			for (BuyerItem item : buyerItems) {
				item.setSku(buyerService.selectSkuById(item.getSku().getId()));
			}
			model.addAttribute("buyerCart", buyerCart);
		}*/
    	return "redirect:/shopping/toCart";
    }
	/**
	 * 
	 * @Title: toCart
	 * @Description: 查询购物车
	 * @param @param model
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/shopping/toCart")
	public String toCart(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//未登录从cookie中查询
		BuyerCart buyerCart=null;
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0){
			
			for (Cookie cookie : cookies) {
				if("BUYER_CART".equals(cookie.getName())){
					String content=cookie.getValue();
					buyerCart=om.readValue(content, BuyerCart.class);
					break;
				}
			}
		}
		
		//已登录，从redis查询
		String username=sessionProvider.getAttributeForUserName(TokenUtils.getCSESSIONID(request, response));
		if(username!=null){
			if(buyerCart!=null){
				List<BuyerItem> buyItems=buyerCart.getBuyerItems();
				if(buyItems!=null && buyItems.size()>0){
					
					buyerService.insertBuyerCartToRedis(username, buyerCart);
					Cookie cookie=new Cookie("BUYER_CART",null);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			buyerCart=buyerService.getBuyerCartFromRedis(username);
		}
		//jsp页面回显
		if(buyerCart!=null){			
			List<BuyerItem> buyerItems=buyerCart.getBuyerItems();
			if(buyerItems!=null && buyerItems.size()>0){
				
				for (BuyerItem item : buyerItems) {
					item.setSku(buyerService.selectSkuById(item.getSku().getId()));
				}
				model.addAttribute("buyerCart", buyerCart);
			}
		}
		return "cart";
		
	}
	/**
	 * 
	 * @Title: trueBuy
	 * @Description: 去结算
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/buyer/trueBuy")
	public String trueBuy(HttpServletRequest request,HttpServletResponse response,Model model){
		String username=sessionProvider.getAttributeForUserName(TokenUtils.getCSESSIONID(request, response));
		BuyerCart buyerCart=buyerService.getBuyerCartFromRedis(username);
		List<BuyerItem> buyerItems=buyerCart.getBuyerItems();
		boolean flag=false;
		if(buyerItems!=null && buyerItems.size()>0){
			for (BuyerItem buyerItem : buyerItems) {
				Long id=buyerItem.getSku().getId();
				Sku sku=buyerService.selectSkuById(id);
				int stock=sku.getStock();
				buyerItem.setSku(sku);
				int amount=buyerItem.getAmount();
				if(amount>stock){
					buyerItem.setIsHave(false);
					flag=true;
				}
			}
			if(flag){
				model.addAttribute("buyerCart", buyerCart);
				return "cart";
			}
		}else{
			return "redirect:/shopping/toCart";
		}
		return "order";
	}
	/**
	 * 
	 * @Title: submitOrder
	 * @Description: 提交订单
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/buyer/submitOrder")
	public String submitOrder(Order order,HttpServletRequest request,HttpServletResponse response){
		String username=sessionProvider.getAttributeForUserName(TokenUtils.getCSESSIONID(request, response));
		orderService.insertOrder(username, order);
		return "success";
	}
}
