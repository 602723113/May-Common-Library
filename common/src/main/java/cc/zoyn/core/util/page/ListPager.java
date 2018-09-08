package cc.zoyn.core.util.page;

import java.util.List;

/**
 * 列表分页工具类
 *
 * @author Zoyn
 * @since 2018/8/27
 */
public class ListPager<T> {

    /**
     * 放入的列表
     */
    private List<T> list;
    /**
     * 每页显示的个数
     */
    private int pageSize;
    /**
     * 所有的元素们
     */
    private int totalCount;

    /**
     * 列表分页器
     *
     * @param list     列表
     * @param pageSize 每页显示的个数
     */
    public ListPager(List<T> list, int pageSize) {
        this.list = list;
        this.pageSize = pageSize;
        this.totalCount = list.size();
    }

    /**
     * 获取分页内容
     *
     * @param page 页数
     * @return {@link List}
     */
    public List<T> getPage(int page) {
        if (page <= 0) {
            return getPage(1);
        }

        List<T> subList;
        if (pageSize >= totalCount) {
            subList = list;
        } else {
            int fromIndex = Math.min(pageSize * (page - 1), totalCount);
            int endIndex = Math.min(pageSize * page, totalCount);

            subList = list.subList(fromIndex, endIndex);
        }
        return subList;
    }

    public List<T> getList() {
        return list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ListPager setList(List<T> list) {
        this.list = list;
        this.totalCount = list.size();
        return this;
    }

    public ListPager setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
