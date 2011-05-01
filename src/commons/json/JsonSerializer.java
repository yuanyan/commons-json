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

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * 序列化
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public class JsonSerializer {
	/**
	 * 序列化输出载体
	 */
	private final Appendable writer;

	/**
	 * 是否序列化Null值
	 */
	private final boolean serializeNulls;

	/**
	 * 是否忽略序列化对象的超类
	 */
	private final boolean ignoreHierarchy;

	/**
	 * 映射为JsonString的Java类型
	 */
	private Class<?>[] stringTypes = { String.class, char.class,
			Character.class, Enum.class, java.util.Date.class,
			java.sql.Date.class };
	/**
	 * 映射为JsonNumber的Java类型
	 */
	private Class<?>[] numberTypes = { int.class, long.class, short.class,
			float.class, double.class, byte.class, Integer.class, Long.class,
			Short.class, Float.class, Double.class, Byte.class };
	/**
	 * 映射为JsonArray的Java类型
	 */
	private Class<?>[] arrayTypes = { Iterable.class };

	/**
	 * 映射为JsonObject的Java类型
	 */
	private Class<?>[] objectTypes = { Map.class };

	/**
	 * 映射为JsonNull的Java类型
	 */
	private Class<?>[] nullTypes = {};

	/**
	 * 映射为JsonBoolean的Java类型
	 */
	private Class<?>[] booleanTypes = {};

	/**
	 * 序列化器
	 * 
	 * @param writer
	 * @param serializeNulls
	 */
	public JsonSerializer(Appendable writer, boolean serializeNulls) {
		this.writer = writer;
		this.serializeNulls = serializeNulls;
		this.ignoreHierarchy = true;
	}

	/**
	 * 序列化映射类型注册
	 * 
	 * @param src
	 * @param target
	 * 
	 * @return
	 */
	void typeMappingRegister(Class<?> src, Class<? extends JsonAware> target) {

		if (target == JsonString.class) {

		} else if (target == JsonArray.class) {

		} else if (target == JsonObject.class) {

		} else if (target == JsonBoolean.class) {

		} else if (target == JsonNull.class) {

		}

	}

	/**
	 * 类型映射控制
	 * 
	 * @param src
	 * @return
	 */
	JsonAware typeMapping(Object src) {

		if (isMapJsonNull(src)) {
			return jsonNullMapping(src);

		} else if (isMapJsonBoolean(src)) {
			return jsonBooleanMapping(src);

		} else if (isMapJsonString(src)) {
			return jsonStringMapping(src);

		} else if (isMapJsonNumber(src)) {
			return jsonNumberMapping(src);

		} else if (isMapJsonArray(src)) {
			return jsonArrayMapping(src);
		}

		return jsonObjectMapping(src);
	}

	/**
	 * 对象映射
	 * 
	 * @param src
	 * @return
	 */
	JsonAware jsonObjectMapping(Object src) {

		if (src == null) {
			return serializeNulls ? JsonNull.getInstance() : null;
		}

		JsonObject target = new JsonObject();

		if (isMapJsonObject(src)) {

			for (Map.Entry entry : (Set<Map.Entry>) ((Map) src).entrySet()) {
				Object value = entry.getValue();

				JsonAware valueElement;
				if (value == null) {
					valueElement = JsonNull.getInstance();
				} else {
					valueElement = typeMapping(value);
				}
				target.add(String.valueOf(entry.getKey()), valueElement);
			}

			return target;
		}

		Field[] fields = Reflector.getFields(src);

		for (Field field : fields) {
			Object value = null;
			String property = field.getName();

			field.getType();

			try {
				value = Reflector.getFieldValue(field, src, true);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			target.add(property, typeMapping(value));
		}

		return target;

	}

	/**
	 * 数组映射
	 * 
	 * @param src
	 * @return
	 */
	JsonAware jsonArrayMapping(Object src) {
		if (src == null) {
			return serializeNulls ? JsonNull.getInstance() : null;
		}

		JsonArray target = new JsonArray();

		// 目标对象为数组时
		if (src.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(src); i++) {
				Object element = Array.get(src, i);
				if (element == null) {
					target.add(JsonNull.getInstance());
				} else {
					JsonAware jsonElement = typeMapping(element);
					target.add(jsonElement);
				}
			}
			return target;
		}
		// 非数组类型时
		for (Object element : (Iterable) src) {
			if (element == null) {
				target.add(JsonNull.getInstance());
			} else {
				JsonAware jsonElement = typeMapping(element);
				target.add(jsonElement);
			}
		}

		return target;

	}

	/**
	 * 数字类型映射
	 * 
	 * @param src
	 * @param target
	 * @return
	 */
	JsonAware jsonNumberMapping(Object src) {
		if (src == null) {
			return serializeNulls ? JsonNull.getInstance() : null;
		}

		JsonNumber target = new JsonNumber(src);

		return target;

	}

	/**
	 * 字符类型映射
	 * 
	 * @param src
	 * @return
	 */
	JsonAware jsonStringMapping(Object src) {
		if (src == null) {
			return serializeNulls ? JsonNull.getInstance() : null;
		}

		JsonString target = new JsonString(src);

		return target;

	}

	/**
	 * 布尔类型映射
	 * 
	 * @param src
	 * @return
	 */
	JsonAware jsonBooleanMapping(Object src) {

		if (src == null) {
			return serializeNulls ? JsonNull.getInstance() : null;
		}

		JsonBoolean target = ((Boolean) src) ? JsonBoolean.getTrueInstance()
				: JsonBoolean.getFalseInstance();

		return target;

	}

	/**
	 * Null值映射
	 * 
	 * @param src
	 * @return
	 */
	JsonAware jsonNullMapping(Object src) {

		if (src == null) {
			return serializeNulls ? JsonNull.getInstance() : null;
		}

		return JsonNull.getInstance();

	}

	/**
	 * 是否匹配JsonString映射
	 * 
	 * @param src
	 * @return
	 */
	public boolean isMapJsonString(Object src) {
		if (src instanceof String) {
			return true;
		}

		Class<?> srcType = src.getClass();
		for (Class<?> stringType : stringTypes) {
			if (stringType.isAssignableFrom(srcType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否匹配JsonNumber映射
	 * 
	 * @param src
	 * @return
	 */
	public boolean isMapJsonNumber(Object src) {

		Class<?> srcType = src.getClass();
		for (Class<?> numberType : numberTypes) {
			if (numberType.isAssignableFrom(srcType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否匹配JsonBoolean映射
	 * 
	 * @param src
	 * @return
	 */
	public boolean isMapJsonBoolean(Object src) {
		if (src instanceof Boolean) {
			return true;
		}

		Class<?> srcType = src.getClass();
		for (Class<?> booleanType : booleanTypes) {
			if (booleanType.isAssignableFrom(srcType)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 是否匹配JsonNull映射
	 * 
	 * @param src
	 * @return
	 */
	public boolean isMapJsonNull(Object src) {
		if (src == null) {
			return true;
		}
		
		Class<?> srcType = src.getClass();
		for (Class<?> nullType : nullTypes) {
			if (nullType.isAssignableFrom(srcType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否匹配JsonArray映射
	 * 
	 * @param src
	 * @return
	 */
	public boolean isMapJsonArray(Object src) {
		if (src.getClass().isArray()) {
			return true;
		}

		Class<?> srcType = src.getClass();
		for (Class<?> arrayType : arrayTypes) {
			if (arrayType.isAssignableFrom(srcType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否匹配JsonObject映射
	 * 
	 * @param src
	 * @return
	 */
	public boolean isMapJsonObject(Object src) {

		Class<?> srcType = src.getClass();
		for (Class<?> objectType : objectTypes) {
			if (objectType.isAssignableFrom(srcType)) {
				return true;
			}
		}

		/*
		 * if (src instanceof Class) { return true; }
		 */

		return false;
	}

	/**
	 * 对象序列化
	 * 
	 * @param src
	 */
	public void serialize(Object src) {

		try {
			typeMapping(src).toString(writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
