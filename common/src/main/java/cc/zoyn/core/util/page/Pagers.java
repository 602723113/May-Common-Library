package cc.zoyn.core.util.page;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 分页工具
 *
 * @author Zoyn
 */
public class Pagers {

    public static <T> ListPager<T> newListPager(int pageSize, List<T> list) {
        return new ListPager<>(list, pageSize);
    }

    public static <T> ListPager<T> newListPager(int pageSize, T... objects) {
        return newListPager(pageSize, Lists.newArrayList(objects));
    }

}
