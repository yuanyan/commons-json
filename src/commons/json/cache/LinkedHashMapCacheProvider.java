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
package commons.json.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An LRU(lasted recently used) Cache Provider based on LinkedHashMap
 * 
 * @author yuanyan.cao
 * 
 * @param <K>
 * @param <V>
 */
public final class LinkedHashMapCacheProvider<K, V> extends LinkedHashMap<K, V> implements
		CacheProvider<K, V> {
	private static final long serialVersionUID = 1L;
	/**
	 * 缓存最大容量
	 */
	private final int maxCapacity;

	/**
	 * 实例化默认最大容量为100的HashMapProvider
	 */
	public LinkedHashMapCacheProvider() {
		this(100);
	}

	/**
	 * 实例化
	 * 
	 * @param maxCapacity
	 *            最大容量
	 */
	public LinkedHashMapCacheProvider(int maxCapacity) {
		super(maxCapacity, 0.7F, true);
		this.maxCapacity = maxCapacity;
	}

	@Override
	public void add(K key, V value) {
		super.put(key, value);
	}

	@Override
	public V get(Object key) {
		return super.get(key);
	}

	@Override
	public V remove(Object key) {

		return super.remove(key);
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
		return size() > maxCapacity;
	}

	/**
	 * 
	 */
	@Override
	public boolean contains(K key) {
		
		return super.containsKey(key);
	}

}
