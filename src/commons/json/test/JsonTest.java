package commons.json.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import commons.json.Json;
import commons.json.JsonSerializer;
import commons.json.cache.LinkedHashMapCacheProvider;


import junit.framework.TestCase;

public class JsonTest extends TestCase {

	public void testSerialize() {

		Person person = new Person("cyy", 123);// object
		
		Map map = new HashMap();
		map.put("1", person);
		map.put("2", person);
		
		long start=System.currentTimeMillis();

		//Json.setCacheProvider(new LinkedHashMapCacheProvider());
		for(int i=0;i<1000;i++)  Json.serialize(map);
		
		long time=System.currentTimeMillis()-start;
		
		System.out.println(time);
		
		}

}

enum COLOR {
	GREEN, RED
}

class Person {
	public String name;
	public int age;
	public Person lover;
	public COLOR myColor = COLOR.RED;

	Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

}