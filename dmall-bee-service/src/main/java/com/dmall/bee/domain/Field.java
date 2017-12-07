package com.dmall.bee.domain;

import com.dmall.bee.util.VelocityTools;

import java.util.Arrays;
import java.util.List;

/**
 * 字段信息
 * 
 */
public class Field {

	private String name;

	private int length;

	private boolean isNullAble;

	private Class<?> type;

	private String jdbcType;

	private String remark;

	private boolean isPrimaryKey;

	public String getName() {
		return name;
	}

	public String getFieldName() {
		return VelocityTools.getVarName(name);
	}

	public String getMethodPrefix() {
		return type == Boolean.TYPE ? "is" : "get";
	}

	public String getTypeName() {
		if (type == Integer.TYPE) {
			return Integer.class.getSimpleName();
		} else if (type == Boolean.TYPE) {
			return Boolean.class.getSimpleName();
		} else if (type == Long.TYPE) {
			return Long.class.getSimpleName();
		} else if (type == Float.TYPE) {
			return Float.class.getSimpleName();
		} else if (type == Double.TYPE) {
			return Double.class.getSimpleName();
		}
		return type.getSimpleName();
	}

	public String getMethodName() {
		return VelocityTools.getClassName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullAble() {
		return isNullAble;
	}

	public void setNullAble(boolean isNullAble) {
		this.isNullAble = isNullAble;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getRemark() {
		if (remark == null || "".equals(remark)) {
			remark = getFieldName();
		}
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	
	public boolean isSkip(){
	    List<String> list = Arrays.asList("id,feature,created,modified,yn,startIndex,endIndex,orderField,orderFieldType".split(","));
	    return list.contains(getFieldName());
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer("名称：");
		str.append(name).append(",类型:").append(type);
		str.append(",长度:").append(length);
		str.append(",为空性:").append(isNullAble);
		str.append(",主键:").append(isPrimaryKey);
		str.append(",备注:").append(remark);
		return str.toString();
	}
}
