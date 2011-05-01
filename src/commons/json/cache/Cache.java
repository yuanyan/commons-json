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

/**
 * 
 * @author yuanyan.cao
 * 
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> {
	private CacheProvider<K, V> provider;
	//Lazy Initialization Mode
	private static Cache instance = null;

	private Cache(CacheProvider<K, V> provider) {
		this.provider = provider;
	}

	/**
	 * 获取缓存实例
	 * @param provider
	 * @return Cache
	 */
	public static Cache getInstance(CacheProvider provider) {
		//Double-check Locking
		if (null == instance) {
			synchronized (Cache.class) {
				if (null == instance) {
					instance = new Cache(provider);
				}
			}
		}
		
		return instance;
	}

	/**
	 * 清空
	 */
	public void clear() {
		provider.clear();
	}

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		return provider.get(key);
	}

	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 */
	public void add(K key, V value) {

		provider.add(key, value);
	}

	/**
	 * 移除元素，并返回
	 * 
	 * @param key
	 * @return
	 */
	public V remove(K key) {

		return provider.remove(key);
	}
	
	public boolean contains(K key){
		
		return provider.contains(key);
	}

	/**
	 * 当前缓存容量
	 * 
	 * @return
	 */
	public int size() {
		return provider.size();
	}

}
