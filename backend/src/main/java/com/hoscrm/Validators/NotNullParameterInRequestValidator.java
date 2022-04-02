package com.hoscrm.Validators;

import com.hoscrm.Exceptions.NotNullParameterAbsentException;
import com.hoscrm.annotations.ReceiveNotNull;

import java.lang.reflect.Field;

public class NotNullParameterInRequestValidator {
    public static void validate(Object object) throws NotNullParameterAbsentException{
        for(Field f: object.getClass().getDeclaredFields()){
            boolean accessible = f.canAccess(object);
            f.setAccessible(true);
            try {
                if(f.getAnnotation(ReceiveNotNull.class) != null && f.get(object)== null){
                    f.setAccessible(accessible);
                    throw new NotNullParameterAbsentException("Mandatory parameter is missing:  " + f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
