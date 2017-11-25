package cn.itcast.test;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import cn.itcast.babasport.pojo.ad.Advertising;

public class TestConverObjectToJson {
	@Test
   public void testObjectMapper() throws Exception{
	   ObjectMapper objectMapper = new ObjectMapper();
	   objectMapper.setSerializationInclusion(Include.NON_NULL);
	   Advertising advertising = new Advertising();
	   advertising.setPicture("/image/123.jpg");
	   advertising.setUrl("www.baidu.com");
	   advertising.setHeight(700);
	   advertising.setWidth(300);
	   StringWriter w=new StringWriter();
	   objectMapper.writeValue(w, advertising);
	   System.out.println(w.toString());;
   }
}
