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

/**
 * Json Boolean 封装类
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public final class JsonBoolean implements JsonAware {

	private static final JsonBoolean TRUE = new JsonBoolean(true);
	private static final JsonBoolean FALSE = new JsonBoolean(false);

	private Object value;

	/**
	 * 获得JsonBoolean(true)实例
	 * 
	 * @return JsonBoolean(true)
	 */
	static JsonBoolean getTrueInstance() {
		return TRUE;
	}

	/**
	 * 获得JsonBoolean(false)实例
	 * 
	 * @return JsonBoolean(false)
	 */
	static JsonBoolean getFalseInstance() {
		return FALSE;
	}

	/**
	 * 实例化Json布尔类型对象
	 * 
	 * @param bool
	 *            布尔值
	 */
	public JsonBoolean(Boolean bool) {
		setValue(bool);
	}

	/**
	 * 实例化Json布尔类型对象
	 * 
	 * @param string
	 *            
	 */
	public JsonBoolean(String string) {
		setValue(string);
	}

	/**
	 * 实例化Json布尔类型对象
	 * @param obj
	 *            
	 */
	public JsonBoolean(Object obj) {
		setValue(obj);
	}

	private void setValue(Object obj) {
		this.value = obj;
	}


	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getAsBooleanWrapper() {
		return (Boolean) value;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean getAsBoolean() {
		
		return getAsBooleanWrapper().booleanValue();
	}

	/**
	 * 
	 * @return String
	 */
	public String getAsString() {
			return getAsBooleanWrapper().toString();
	}

	/**
	 * 序列化输出规则定义
	 * 
	 * @param sb
	 *            Appendable
	 */
	public void toString(Appendable sb) throws IOException {

		sb.append(value.toString());

	}

	@Override
	public int hashCode() {
		if (value == null) {
			return 31;
		}
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		JsonBoolean other = (JsonBoolean) obj;
		if (value == null) {
			return other.value == null;
		}

		return value.equals(other.value);
	}

}
