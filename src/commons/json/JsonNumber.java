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
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Json Number 类型
 * 
 * @author yuanyan.cao@gmail.com
 * @version 1.0
 */
public final class JsonNumber implements JsonAware {

	private static final BigInteger INTEGER_MAX = BigInteger
			.valueOf(Integer.MAX_VALUE);
	private static final BigInteger LONG_MAX = BigInteger
			.valueOf(Long.MAX_VALUE);

	private Object value;


	public JsonNumber(Number number) {
		setValue(number);
	}


	public JsonNumber(String string) {
		setValue(string);
	}


	JsonNumber(Object number) {
		setValue(number);
	}

	private void setValue(Object number) {
		if(!(number instanceof Number))
			throw new JsonException("非法参数:"+number);;
		this.value = number;
	}


	public Number getAsNumber() {
		return (Number) value;
	}

	
	public String getAsString() {

			return getAsNumber().toString();
	}


	public double getAsDouble() {

			return getAsNumber().doubleValue();

	}

	public float getAsFloat() {
			return getAsNumber().floatValue();

	}


	public long getAsLong() {
			return getAsNumber().longValue();
	}


	public short getAsShort() {
			return getAsNumber().shortValue();
	}


	public int getAsInt() {

			return getAsNumber().intValue();

	}

	public byte getAsByte() {
			return getAsNumber().byteValue();
	}
	

	public BigDecimal getAsBigDecimal() {
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else {
			return new BigDecimal(value.toString());
		}
	}


	public BigInteger getAsBigInteger() {
		if (value instanceof BigInteger) {
			return (BigInteger) value;
		} else {
			return new BigInteger(value.toString());
		}
	}

	/**
	 * convenience method to get this element as an Object.
	 * 
	 * @return get this element as an Object that can be converted to a suitable
	 *         value.
	 */
	Object getAsObject() {
		if (value instanceof BigInteger) {
			BigInteger big = (BigInteger) value;
			if (big.compareTo(INTEGER_MAX) < 0) {
				return big.intValue();
			} else if (big.compareTo(LONG_MAX) < 0) {
				return big.longValue();
			}
		}
		// No need to convert to float or double since those lose precision
		return value;
	}
	
	public void toString(Appendable sb) throws IOException {
		sb.append(value.toString());
	}

	@Override
	public int hashCode() {
		if (value == null) {
			return 31;
		}
		// Using recommended hashing algorithm from Effective Java for longs and
		// doubles
		if (isIntegral(this)) {
			long value = getAsNumber().longValue();
			return (int) (value ^ (value >>> 32));
		}
		if (isFloatingPoint(this)) {
			long value = Double.doubleToLongBits(getAsNumber().doubleValue());
			return (int) (value ^ (value >>> 32));
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
		JsonNumber other = (JsonNumber) obj;
		if (value == null) {
			return other.value == null;
		}
		if (isIntegral(this) && isIntegral(other)) {
			return getAsNumber().longValue() == other.getAsNumber().longValue();
		}
		if (isFloatingPoint(this) && isFloatingPoint(other)) {
			return getAsNumber().doubleValue() == other.getAsNumber()
					.doubleValue();
		}
		return value.equals(other.value);
	}

	/**
	 * Returns true if the specified number is an integral type (Long, Integer,
	 * Short, Byte, BigInteger)
	 */
	private static boolean isIntegral(JsonNumber jsonNumber) {
		if (jsonNumber.value instanceof Number) {
			Number number = (Number) jsonNumber.value;
			return number instanceof BigInteger || number instanceof Long
					|| number instanceof Integer || number instanceof Short
					|| number instanceof Byte;
		}
		return false;
	}

	/**
	 * Returns true if the specified number is a floating point type
	 * (BigDecimal, double, float)
	 */
	private static boolean isFloatingPoint(JsonNumber jsonNumber) {
		if (jsonNumber.value instanceof Number) {
			Number number = (Number) jsonNumber.value;
			return number instanceof BigDecimal || number instanceof Double
					|| number instanceof Float;
		}
		return false;
	}
}
