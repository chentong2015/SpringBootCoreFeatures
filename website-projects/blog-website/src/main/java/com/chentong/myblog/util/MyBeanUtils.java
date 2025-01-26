package com.chentong.myblog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类 获取所有的属性值为空的一组属性
 * 返回属性名称的数组
 */
public class MyBeanUtils {

    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for(PropertyDescriptor pd: propertyDescriptors){
            String propertyName =  pd.getName();
            if(beanWrapper.getPropertyValue(propertyName) == null){
                nullPropertyNames.add(propertyName);
            }
        }
        // Define the array with the size of nullPropertyNames
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

}
