package br.com.caina.todolist.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    
    //
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertiesName(source));
    }

    //PARA PEGAR PROPRIEDADES COM VALORES NULOS... passando objeto id nulo, etc...
    //PODE SER USADO NA BUSCA PARA VALIDAR OS RETORNOS...
    //USADO NOS PUT...
    //... VER DOC ...
    private static String[] getNullPropertiesName(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
