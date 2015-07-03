package com.quantium.web.bean.view;

import com.quantium.web.bean.Url;

import java.util.ArrayList;

/**
 * Author FREEMAN
 * Created 18.11.14
 */
public class Pagination {
    private int count       = 0;
    private int perPage     = 10;
    private int pageOffset  = 2;
    private int page        = 1;

    private Url url;
    private String pName;

    // - Calculated data
    private int pageCount   = 0;
    private int offset      = 0;

    private int first, left, right, last;

    private ArrayList<Integer> pageList;

    public Pagination(Url url, String pName, int count, int perPage, int pageOffset) {
        this.url        = url;
        this.pName      = pName;
        this.count      = count;
        this.pageOffset = pageOffset;

        if (perPage > 0)
            this.perPage    = perPage;

        count();
    }
    public Pagination(Url url, String pName, int count, int perPage) {
        this.url        = url;
        this.pName      = pName;
        this.count      = count;

        if (perPage > 0)
            this.perPage    = perPage;

        count();
    }
    public Pagination(Url url, String pName, int count) {
        this.url        = url;
        this.pName      = pName;
        this.count      = count;

        count();
    }
    public Pagination(Url url, String pName) {
        this.url        = url;
        this.pName      = pName;

        count();
    }

    public Pagination count() {
        String tmp;
        int start, end;

        if (url != null && (tmp = url.getSearch("per_" + pName)) != null)
            perPage     = Integer.parseInt(tmp);

        if (perPage < 0) {
            perPage     = Math.abs(perPage);
            count       = perPage;
        } else if (perPage == 0)
            perPage     = count;

        if (count == 0)
            pageCount       = 0;
        else
            pageCount       = (int) Math.ceil((float) count / perPage);

        if (pageCount > 1) {
            this.page       = 1;
            if (url != null && (tmp = url.getSearch(pName)) != null)
                page        = Integer.parseInt(tmp);

            if (pageCount < page) {
                page            = pageCount;
                url.setSearch(pName, String.valueOf(page));
            } else if (page <= 0) {
                page            = 1;
                url.setSearch(pName, String.valueOf(page));
            }

            offset      = (page * perPage) - perPage;

            left = right = page;

            if (page > 1) {
                left--;

                if (page < pageCount)
                    right++;
            } else
                right++;

            start   = (page - pageOffset <= 3) ? 1 : page - pageOffset;
            end     = (page + pageOffset < pageCount - 2) ? page + pageOffset : pageCount;

            this.pageList   = new ArrayList<Integer>();

            if (start > 3) {
                pageList.add(1);
                pageList.add(-1);
            }

            for (; start <= end; start++)
                pageList.add(start);

            if (end < pageCount) {
                pageList.add(-1);
                pageList.add(pageCount);
            }
        } else
            offset      = 0;

        return this;
    }

    /**
     * @return first page at set of all pages
     */
    public int getFirst() {
        return first;
    }

    /**
     * @return last page at set of all pages
     */
    public int getLast() {
        return last;
    }

    /**
     * @return previous page at set of all pages
     */
    public int getLeft() {
        return left;
    }

    /**
     * @return next page at set of all pages
     */
    public int getRight() {
        return right;
    }

    /**
     * @return get current page
     */
    public int getPage() {
        return page;
    }

    /**
     * @return offset of items for limits in database
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return get offset from current page
     */
    public int getPageOffset() {
        return pageOffset;
    }

    /**
     * @return count of items of request
     */
    public int getCount() {
        return count;
    }

    /**
     * @return count items on single page
     */
    public int getPerPage() {
        return perPage;
    }

    /**
     * @return count pages
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @return get page set
     */
    public ArrayList<Integer> getPageList() {
        return pageList;
    }

    /**
     * @param page page number
     * @return generate url with page parameter
     */
    public String getUrl(int page) {
        if (url == null)
            return null;

        return url.toString(pName, String.valueOf(page));
    }
}
