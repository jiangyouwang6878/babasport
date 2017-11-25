package cn.itcast.babasport.utils.converter;

import org.springframework.core.convert.converter.Converter;
//自定义类型转换器
public class CustomParamsConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		// TODO Auto-generated method stub
		if(source!=null && !"".equals(source)){
			source=source.trim();
			if(!"".equals(source)){
				return source;
			}
		}
		return null;
	}

}
