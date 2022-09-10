package com.lks.blog.blog_project.entity;

/**
 * 封装底部分页相关的信息
 */
public class Page {

    // 当前的页码
    private int current = 1;
    // 显示上限
    private int limit = 10;
    // 数据总数（用于计算总页数）
    private int rows;
    // 查询路径（用来复用分页链接）
    private String path;

    /**
     * 获取当前页的起始行
     * @return 当前页码第一条帖子的行号
     */
    public int getOffset() {
        return (current - 1) * limit;
    }

    /**
     * 获取总的页数，防止显示错误
     * @return
     */
    public int getTotal() {
        if (rows % limit == 0) {
            return rows % limit;
        } else {
            return rows % limit + 1;
        }
    }

    /**
     * 获取分页栏显示起始页码
     * @return
     */
    public int getFrom() {
        int from = current - 2;
        return Math.max(from, 1);
    }

    /**
     * 获取分页栏显示最后页码
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return Math.min(to, total);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
