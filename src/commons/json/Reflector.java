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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;


/**
 * 反射器
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
final class Reflector {
	/**
	 * 访问权限修饰字段
	 */
	private static final int ACCESS_MODIFIER = Modifier.PUBLIC | Modifier.PROTECTED
			| Modifier.PRIVATE;

	/**
	 * 获取目标对象的所有字段
	 * 
	 * @param src
	 * @return
	 */
	public static Field[] getFields(Object src) {
		return src.getClass().getFields();
	}


	/**
	 * 获取字段值
	 * @param field
	 * @param target
	 * @param forceAccess 
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Object getFieldValue(Field field, Object target,
			boolean forceAccess) throws IllegalAccessException {
		if (field == null) {
			throw new IllegalArgumentException("字段名不能为空");
		}
		if (forceAccess && !field.isAccessible()) {
			field.setAccessible(true);
		} else {
			setAccessibleWorkaround(field);
		}
		return field.get(target);
	}

	/**
	 * 访问权限设置
	 * @param o
	 */
	static void setAccessibleWorkaround(AccessibleObject o) {
		if (o == null || o.isAccessible()) {
			return;
		}
		Member m = (Member) o;
		if (Modifier.isPublic(m.getModifiers())
				&& isPackageAccess(m.getDeclaringClass().getModifiers())) {
			try {
				o.setAccessible(true);
			} catch (SecurityException e) {}
		}
	}

	
	static boolean isPackageAccess(int modifiers) {
		return (modifiers & ACCESS_MODIFIER) == 0;
	}


	static boolean isAccessible(Member m) {
		return m != null && Modifier.isPublic(m.getModifiers());
	}

}
