package me.may.core.util;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 空,非空判断工具
 *
 * @author May_Speed
 */
public class IsNullUtils {

    private static boolean flag; //记录判断结果

    /**
     * 判断空
     * 举例
     * null:true
     * new Object():false
     * 0:true
     * List.size() = 0:true
     *
     * @param object 对象
     * @return true[是]/false[不是]
     */
    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        }
        return detect(object, true);
    }

    /**
     * 判断非空
     * <p>
     * 举例
     * null:false
     * new Object():true
     * 0.0001:true
     * List.size() = 0:false
     *
     * @param object 对象
     * @return true[是]/false[不是]
     */
    public static boolean notNull(Object object) {
        if (object == null) {
            return false;
        }
        return detect(object, false);
    }

    private static boolean detect(Object object, boolean isDetectNull) {
        if (object instanceof Boolean) {
            return (boolean) object;
        } else if (object instanceof String) {
            if (((String) object).equals("")) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else if (object instanceof Integer) {
            if (((Integer) object) == 0) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else if (object instanceof Double) {
            if (((Double) object) == 0) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else if (object instanceof Float) {
            if (((Float) object) == 0) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else if (object instanceof List<?>) {
            if (((List<?>) object).size() < 1) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else if (object instanceof Map<?, ?>) {
            if (((Map<?, ?>) object).isEmpty()) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else if (object instanceof File) {
            if (((File) object).exists()) {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            } else {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            }
        } else if (object instanceof Object[]) {
            if (((Object[]) object).length < 1) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        } else {
            if (object == null) {
                if (isDetectNull)
                    flag = true;
                else
                    flag = false;
            } else {
                if (isDetectNull)
                    flag = false;
                else
                    flag = true;
            }
        }
        return flag;
    }
}
