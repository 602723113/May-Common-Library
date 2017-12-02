package cc.zoyn.core.util.reflect;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Easy to reflect
 *
 * @author Zoyn
 * @since 2017-12-02
 */
public final class ReflectionUtils {

    // Prevent accidental construction
    private ReflectionUtils() {
    }

    /**
     * get field objects by using field names
     *
     * @param clazz     classObj
     * @param fieldName field's name
     * @return {@link Field}
     * @see #hasField(Class, String)
     */
    public static Field getFieldByFieldName(Class<?> clazz, String fieldName) {
        Field field = null;
        if (hasField(clazz, fieldName)) {
            try {
                field = clazz.getField(fieldName);
            } catch (NoSuchFieldException ignored) {
            }
        }
        return field;
    }

    /**
     * get a Value field
     *
     * @param obj       object
     * @param fieldName field's name
     * @return {@link Object}
     * @throws IllegalAccessException If the field is accessible
     * @see #getFieldByFieldName(Class, String)
     */
    public static Object getValueByFieldName(Object obj, String fieldName) throws IllegalAccessException {
        Field field = getFieldByFieldName(obj.getClass(), fieldName);
        Object value = null;

        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * set a object's Value field
     *
     * @param obj       object
     * @param fieldName field's name
     * @param value     the value to be set
     * @throws NoSuchFieldException If the field is missing
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    /**
     * get a class's constructor
     *
     * @param clazz          classObj
     * @param parameterTypes parameters
     * @return {@link Constructor}
     * @throws NoSuchMethodException If the constructor with the specified parameter types cannot be found
     * @see #hasConstructor(Class, Class[])
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor constructor = null;
        if (hasConstructor(clazz, parameterTypes)) {
            constructor = clazz.getConstructor(parameterTypes);
        }
        return constructor;
    }

    /**
     * Constructing an object with a constructor
     *
     * @param constructor the Constructor
     * @param arguments   the constructor's arguments
     * @return {@link Object}
     * @throws IllegalAccessException    If the desired constructor cannot be accessed due to certain circumstances
     * @throws InvocationTargetException If the desired constructor cannot be invoked
     * @throws InstantiationException    If you cannot create an instance of the target class due to certain circumstances
     */
    public static Object instantiateObject(Constructor<?> constructor, Object... arguments) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return Validate.notNull(constructor).newInstance(arguments);
    }

    /**
     * get a method in a class
     *
     * @param clazz          classObj
     * @param methodName     method's name
     * @param parameterTypes the method's arguments
     * @return {@link Method}
     * @throws NoSuchMethodException If the method with the specified parameter types cannot be found
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        if (hasMethod(clazz, methodName, parameterTypes)) {
            return clazz.getMethod(methodName, parameterTypes);
        }
        return null;
    }

    /**
     * method of invocation of object
     *
     * @param method    methodObj
     * @param object    objects that need invoke
     * @param arguments the method's arguments
     * @return {@link Object}
     * @throws InvocationTargetException If the desired method cannot be invoked
     * @throws IllegalAccessException    If the desired method cannot be accessed due to certain circumstances
     */
    public static Object invokeMethod(Method method, Object object, Object... arguments) throws InvocationTargetException, IllegalAccessException {
        return Validate.notNull(method).invoke(object, arguments);
    }

    /**
     * check a class has a specified field
     *
     * @param clazz     classObj
     * @param fieldName field's name
     * @return true -> yes, false -> no
     */
    public static boolean hasField(Class<?> clazz, String fieldName) {
        boolean has;

        try {
            Validate.notNull(clazz).getField(fieldName);
            has = true;
        } catch (NoSuchFieldException e) {
            has = false;
        }
        return has;
    }

    /**
     * check a class has a specified field
     *
     * @param clazz  classObj
     * @param filter filter obj
     * @return true -> yes, false -> no
     */
    public static boolean hasField(Class<?> clazz, FieldFilter filter) {
        Validate.notNull(filter);

        boolean has = false;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (filter.accept(field)) {
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * check a class has a specified constructor
     *
     * @param clazz          classObj
     * @param parameterTypes the constructor with the specified parameter types
     * @return true -> yes, false -> no
     */
    public static boolean hasConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        boolean has;
        try {
            Validate.notNull(clazz).getConstructor(parameterTypes);
            has = true;
        } catch (NoSuchMethodException e) {
            has = false;
        }
        return has;
    }

    /**
     * check a class has a specified constructor
     *
     * @param clazz  classObj
     * @param filter filter obj
     * @return true -> yes, false -> no
     */
    public static boolean hasConstructor(Class<?> clazz, ConstructorFilter filter) {
        Validate.notNull(filter);

        boolean has = false;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            if (filter.accept(constructor)) {
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * check a class has a specified method
     *
     * @param clazz          classObj
     * @param methodName     method's name
     * @param parameterTypes the method with the specified parameter types
     * @return true -> yes, false -> no
     */
    public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        boolean has;
        try {
            Validate.notNull(clazz).getMethod(methodName, parameterTypes);
            has = true;
        } catch (NoSuchMethodException e) {
            has = false;
        }
        return has;
    }

    /**
     * check a class has a specified method
     *
     * @param clazz  classObj
     * @param filter filter obj
     * @return true -> yes, false -> no
     */
    public static boolean hasMethod(Class<?> clazz, MethodFilter filter) {
        Validate.notNull(filter);

        boolean has = false;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (filter.accept(method)) {
                has = true;
                break;
            }
        }
        return has;
    }

}
