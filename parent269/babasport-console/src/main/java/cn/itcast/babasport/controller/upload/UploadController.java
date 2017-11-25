package cn.itcast.babasport.controller.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.itcast.babasport.service.upload.UploadService;
import cn.itcast.babasport.utils.constants.BbsConstants;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@Resource
	private UploadService uploadService;
   //品牌管理图片上传
	@RequestMapping("/uploadPic.do")
	public void uploadPic(MultipartFile pic,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(pic!=null && pic.getSize()>0){
//			String fileName=pic.getOriginalFilename();
//			String uuid=UUID.randomUUID().toString().replace("-", "");
//			
//			String suffix=FilenameUtils.getExtension(fileName);
//			String newName=uuid+"."+suffix;
//			//附件上传
//			String realPath=request.getServletContext().getRealPath("");
//			String pathName="\\upload\\"+newName;
//			String path=realPath+pathName;
//			File file=new File(path);
//			pic.transferTo(file);
			String path=uploadService.uploadPicToFastDFS(pic.getBytes(), pic.getOriginalFilename());
			String allUrl=BbsConstants.IMG_URL+path;
		    //2.图片回显
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("allUrl", allUrl);
			jsonObject.put("imgUrl", path);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObject.toString());
		}
	}
	
	//上传商品图片
	@RequestMapping("/uploadPics.do")
	@ResponseBody
	public List<String> uploadPics(@RequestParam MultipartFile[] pics) throws Exception{
		List<String> urls=new ArrayList<String>();
		for (MultipartFile pic : pics) {
			String path=uploadService.uploadPicToFastDFS(pic.getBytes(), pic.getOriginalFilename());
			String allUrl=BbsConstants.IMG_URL+path;
			urls.add(allUrl);
		}
		return urls;
	}
	
	
	//通过文字编辑器上传图片
	@RequestMapping("/uploadFck.do")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MultipartRequest  multipartRequest=(MultipartRequest) request;
		//获取附件
		Map<String, MultipartFile> fileMap=multipartRequest.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet=fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			//附件上传
			MultipartFile pic=entry.getValue();
			String path=uploadService.uploadPicToFastDFS(pic.getBytes(), pic.getOriginalFilename());
			String url=BbsConstants.IMG_URL+path;
			
			  //2.图片回显
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("url", url);
			jsonObject.put("error", 0);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObject.toString());
		}
	}
}
