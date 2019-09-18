package top.hjlinfo.base.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import top.hjlinfo.base.common.annotation.Query;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author sting
 * @date 2019-6-4 14:59:48
 */
@Slf4j
public class QueryHelp {

    public static <T, Q>QueryWrapper<T> buildWrapper(Q query) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String attributeName = isBlank(propName) ? field.getName() : propName;
                    Object val = field.get(query);
                    if (ObjectUtil.isEmpty(val)) {
                        continue;
                    }
                    switch (q.type()) {
                        case EQUAL:
                            wrapper.eq(attributeName, val);
                            break;
                        case GREATER_THAN:
                            wrapper.ge(attributeName, val);
                            break;
                        case LESS_THAN:
                            wrapper.le(attributeName, val);
                            break;
                        case LESS_THAN_NQ:
                            wrapper.lt(attributeName, val);
                            break;
                        case INNER_LIKE:
                            wrapper.like(attributeName, val);
                            break;
                        case LEFT_LIKE:
                            wrapper.likeLeft(attributeName, val);
                            break;
                        case RIGHT_LIKE:
                            wrapper.likeRight(attributeName, val);
                            break;
                        case IN:
                            wrapper.in(attributeName, val);
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return wrapper;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
