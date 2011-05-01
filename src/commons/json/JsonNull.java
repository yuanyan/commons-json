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
 * 表示Json {@code null} 的抽象类.
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public final class JsonNull implements JsonAware {
	public static final JsonNull JSONNULL = new JsonNull();

	/**
	 * 
	 * @return {@link JsonNull}实例
	 */
	static JsonNull getInstance() {
		return JSONNULL;
	}

	/**
	 * 创建一个{@link JsonNull}对象.
	 */
	public JsonNull() {} 

	/**
	 * 序列化输出规则定义
	 * 
	 * @param sb
	 *            Appendable
	 */
	public void toString(Appendable sb) throws IOException {
		sb.append("null");
	}

	@Override
	public int hashCode() {
		return JsonNull.class.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof JsonNull;
	}

}
