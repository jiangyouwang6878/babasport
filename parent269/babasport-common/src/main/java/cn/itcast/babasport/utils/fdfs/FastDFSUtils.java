package cn.itcast.babasport.utils.fdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FastDFSUtils {
  //将附件上传到fastDFS上
	public static String uploadPicToFastDFS(byte[] file_buff,String fileName) throws Exception{
		//1.加载FastDFS配置文件
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		//2.初始化FastDFS配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		//3.创建跟踪服务器客户端
		TrackerClient trackerClient=new TrackerClient();
		//4.通过跟踪服务器客户端获取服务端
		TrackerServer trackerServer= trackerClient.getConnection();
		//5.根据跟踪服务器创建存储服务器客户端
		StorageClient1 storageClient1=new StorageClient1(trackerServer, null);
		//6.附件上传，返回附件的路径
		String file_ext_name=FilenameUtils.getExtension(fileName);
		//path不是完整路径
		String path=storageClient1.upload_appender_file1(file_buff, file_ext_name, null);
		return path;
	}
}
