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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Json Array 封装类
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public final class JsonArray implements JsonAware, Iterable<JsonAware> {
	private final List<JsonAware> elements;

	/**
	 * 实例化Json数组元素对象
	 */
	public JsonArray() {
		elements = new ArrayList<JsonAware>();
	}

	/**
	 * 添加Json元素
	 * 
	 * @param element Json元素
	 *         
	 */
	public void add(JsonAware element) {
		if (element == null) {
			element = JsonNull.getInstance();
		}
		elements.add(element);
	}


	public void addAll(JsonArray array) {
		elements.addAll(array.elements);
	}


	void reverse() {
		Collections.reverse(elements);
	}


	public int size() {
		return elements.size();
	}


	public Iterator<JsonAware> iterator() {
		return elements.iterator();
	}


	public JsonAware get(int i) {
		return elements.get(i);
	}

	public void toString(Appendable sb) throws IOException{
		sb.append('[');
		boolean first = true;
		for (JsonAware element : elements) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			element.toString(sb);
		}
		sb.append(']');
	}

}
