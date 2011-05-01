/*
 * Copyright (C) 2010 Yuanyan Cao<yuanyan.cao@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons.json;

import commons.json.cache.Cache;
import commons.json.cache.CacheProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


/**
 * Json 
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public class Json {
	/**
	 * 缓存
	 */
	private static Cache cache=null;
	/**
	 * 是否开启缓存，默认为false
	 */
	private static boolean cacheEnable=false;
	
	/**
	 * 设置CacheProvider后即自动开启缓存
	 * @param provider
	 */
	public static void setCacheProvider(CacheProvider provider){
		cache=Cache.getInstance(provider);
		cacheEnable=true;
	}
	
	/**
	 * 是否已经缓存
	 * @param object
	 * @return
	 */
	private static boolean hasCached(Object object) {			
		return cache.contains(object);
	}

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return 序列化字符串
	 */
	public static String serialize(Object object) {
		if(cacheEnable&&hasCached(object)) 
			return (String) cache.get(object);
		
		StringBuffer sb = new StringBuffer();
		JsonSerializer json = new JsonSerializer(sb, false);
		json.serialize(object);
		String str=sb.toString();
		
		if(cacheEnable) 
			cache.add(object,str);
		
		return str;
		
		
	}

	/**
	 * 序列化
	 * 
	 * @param writer
	 * @param object
	 */
	public static void serialize(Appendable writer, Object object) {

			JsonSerializer json = new JsonSerializer(writer, false);
			json.serialize(object);
	}
	
	
	

	/**
	 * 反序列化
	 * 
	 * @param json
	 * @return 反序列化后对象
	 * @throws JsonException
	 */
	public static Object deserialize(String json) throws JsonException {
		if(cacheEnable&&hasCached(json)) 
			return  cache.get(json);
		
		JsonDeserializer reader = new JsonDeserializer();
		Object obj=reader.deserialize(json);
		
		if(cacheEnable) 
			cache.add(json,obj);
		
		return obj;
		
		
	}

	/**
	 * 反序列化
	 * 
	 * @param reader
	 * @return 反序列化后对象
	 * @throws JsonException
	 *             when IOException happens
	 */
	public static Object deserialize(Reader reader) throws JsonException {
		// read content
		BufferedReader bufferReader = new BufferedReader(reader);
		String line = null;
		StringBuilder buffer = new StringBuilder();

		try {
			while ((line = bufferReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			throw new JsonException(e);
		}

		return deserialize(buffer.toString());
	}

}
