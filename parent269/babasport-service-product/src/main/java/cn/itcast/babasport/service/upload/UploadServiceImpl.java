package cn.itcast.babasport.service.upload;

import org.springframework.stereotype.Service;

import cn.itcast.babasport.utils.fdfs.FastDFSUtils;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadPicToFastDFS(byte[] file_buff, String fileName) throws Exception {
		// TODO Auto-generated method stub
		String path=FastDFSUtils.uploadPicToFastDFS(file_buff, fileName);
		return path;
	}

}
