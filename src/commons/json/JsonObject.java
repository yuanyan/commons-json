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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Json Object 封装类
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public final class JsonObject implements JsonAware {

	private final LinkedHashMap<String, JsonAware> members;


	public JsonObject() {
		members = new LinkedHashMap<String, JsonAware>();
	}

	/**
	 * 添加属性
	 * @param property
	 * @param value
	 */
	public void add(String property, JsonAware value) {
		if(property == null && "".equals(property.trim()))
			throw new JsonException("非法参数:"+property);

		if (value == null) {
			value = JsonNull.getInstance();
		}
		members.put(property, value);
	}

	/**
	 * 删除属性
	 * @param property
	 * @return
	 */
	public JsonAware remove(String property) {
		return members.remove(property);
	}


	public Set<Map.Entry<String, JsonAware>> entrySet() {
		return members.entrySet();
	}


	public boolean has(String memberName) {
		return members.containsKey(memberName);
	}


	public JsonAware get(String memberName) {
		if (members.containsKey(memberName)) {
			JsonAware member = members.get(memberName);
			return member == null ? JsonNull.getInstance() : member;
		} else {
			return null;
		}
	}


	public JsonNumber getAsJsonNumber(String memberName) {
		return (JsonNumber) members.get(memberName);
	}


	public JsonString getAsJsonString(String memberName) {
		return (JsonString) members.get(memberName);
	}


	public JsonBoolean getAsJsonBoolean(String memberName) {
		return (JsonBoolean) members.get(memberName);
	}


	public JsonArray getAsJsonArray(String memberName) {
		return (JsonArray) members.get(memberName);
	}


	public JsonObject getAsJsonObject(String memberName) {
		return (JsonObject) members.get(memberName);
	}

	public void toString(Appendable sb) throws IOException {
		sb.append('{');
		boolean first = true;
		for (Map.Entry<String, JsonAware> entry : members.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append('\"');
			sb.append(entry.getKey());
			sb.append("\":");
			entry.getValue().toString(sb);
		}
		sb.append('}');
	}
}
