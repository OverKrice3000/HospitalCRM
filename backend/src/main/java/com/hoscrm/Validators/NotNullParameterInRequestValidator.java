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
                ReceiveNotNull rnnAnn = f.getAnnotation(ReceiveNotNull.class);
                if(rnnAnn != null){
                    if(f.get(object)== null){
                        f.setAccessible(accessible);
                        throw new NotNullParameterAbsentException("Mandatory parameter is missing: " + f.getName());
                    }
                    else if(rnnAnn.deepValidation()){
                        try{
                            validate(f.get(object));
                        } catch(NotNullParameterAbsentException e){
                            throw new NotNullParameterAbsentException("Deep validation failed for " + f.getName() + ": " + e.getMessage());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
