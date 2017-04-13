package com.dmall.bee.domain;

import com.dmall.bee.util.VelocityTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表信息
 */
public class Table {

    private String name;

    private String remark;

    private List<Field> fields;

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        if (remark == null || "".equals(remark)) {
            remark = getName();
        }
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Field> getFields() {
        if (fields == null) {
            fields = new ArrayList<Field>();
        }
        return fields;
    }

    public String getClassName() {
        return VelocityTools.getClassName(getName());
    }

    public String getVarName() {
        return VelocityTools.getVarName(getName());
    }

    /**
     * 获取表主键,默认第一个字段或者第一个主键.暂不支持复合主键
     * @return
     */
    public Field getPrimaryField() {
        Field field = null;
        List<Field> list = getFields();
        for (Field f : list) {
            if (f.isPrimaryKey()) {
                field = f;
                break;
            }
        }
        if (field == null && list.size() > 0) {
            field = list.get(0);// 默认第一个为主键
        }
        return field;
    }

    /**
     * 获取导入列表
     * @return
     */
    public Set<Class<?>> getImports() {
        Set<Class<?>> ts = new HashSet<Class<?>>();
        for (Field field : getFields()) {
            Class<?> type = field.getType();
            if (field.isSkip() || ("java.lang."+type.getSimpleName()).equals(type.getName())) {
                continue;
            }
            ts.add(type);
        }
        return ts;
    }

    public String getColumns() {
        StringBuffer str = new StringBuffer();
        List<Field> list = getFields();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i).getName());
            if (list.size() != i + 1) {
                str.append(",");
            }
        }
        return str.toString();
    }

    public String getInsertColumns() {
        StringBuffer str = new StringBuffer();
        List<Field> list = getFields();
        for (int i = 0; i < list.size(); i++) {
            str.append("#{").append(list.get(i).getFieldName()).append("}");
            if (list.size() != i + 1) {
                str.append(",");
            }
        }
        return str.toString();
    }

    public String getColumnsAS() {
        StringBuffer str = new StringBuffer();
        List<Field> list = getFields();
        for (int i = 0; i < list.size(); i++) {
            Field f = list.get(i);
            str.append(f.getName());
            if (!f.getName().equals(f.getFieldName())) {
                str.append(" AS ").append(f.getFieldName());
            }
            if (list.size() != i + 1) {
                str.append(",");
            }
        }

        return str.toString();
    }

    public void addField(Field field) {
        getFields().add(field);
    }

    public String toString() {
        StringBuffer str = new StringBuffer("表名：");
        str.append(getName()).append(",备注:").append(remark);
        for (Field field : getFields()) {
            str.append("\t").append(field.toString()).append("\r\n");
        }
        return str.toString();
    }

}
