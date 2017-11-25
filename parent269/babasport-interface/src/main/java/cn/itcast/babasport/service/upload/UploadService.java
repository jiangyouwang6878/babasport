package cn.itcast.babasport.service.upload;

public interface UploadService {
	//上传附件到FastDFS
	public String uploadPicToFastDFS(byte[] file_buff,String fileName)throws Exception;
}
