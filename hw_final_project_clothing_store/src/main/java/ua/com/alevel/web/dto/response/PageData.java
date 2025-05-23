package ua.com.alevel.web.dto.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageData<RES extends ResponseDto> {

    private int currentPage;
    private int pageSize;
    private int totalPageSize;
    private long itemsSize;
    private List<RES> items;
    private final int[] pageSizeItems;
    private boolean showFirst;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showLast;
    private String sort;
    private String order;
    private int currentShowFromEntries;
    private int currentShowToEntries;

    public PageData() {
        this.currentPage = 0;
        this.pageSizeItems = new int[]{5, 10, 25, 50, 100};
        this.pageSize = this.pageSizeItems[0];
        this.totalPageSize = 0;
        this.itemsSize = 0;
        this.items = new ArrayList<>();
        this.showFirst = false;
        this.showPrevious = false;
        this.showNext = false;
        this.showLast = false;
    }

    public void initPaginationState() {
        if (pageSize < itemsSize) {
            if (itemsSize % pageSize == 0) {
                this.totalPageSize = (int) itemsSize / pageSize;
            } else {
                this.totalPageSize = (int) itemsSize / pageSize + 1;
            }
            this.showFirst = currentPage != 1;
            this.showPrevious = currentPage - 1 != 0;
            this.showLast = currentPage != totalPageSize;
            this.showNext = currentPage != totalPageSize;
        }
        currentShowFromEntries = ((currentPage - 1) * pageSize) + 1;
        currentShowToEntries = ((currentPage - 1) * pageSize) + items.size();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public long getItemsSize() {
        return itemsSize;
    }

    public void setItemsSize(long itemsSize) {
        this.itemsSize = itemsSize;
    }

    public List<RES> getItems() {
        return items;
    }

    public void setItems(List<RES> items) {
        this.items = items;
    }

    public int[] getPageSizeItems() {
        return pageSizeItems;
    }

    public boolean isShowFirst() {
        return showFirst;
    }

    public void setShowFirst(boolean showFirst) {
        this.showFirst = showFirst;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowLast() {
        return showLast;
    }

    public void setShowLast(boolean showLast) {
        this.showLast = showLast;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getCurrentShowFromEntries() {
        return currentShowFromEntries;
    }

    public void setCurrentShowFromEntries(int currentShowFromEntries) {
        this.currentShowFromEntries = currentShowFromEntries;
    }

    public int getCurrentShowToEntries() {
        return currentShowToEntries;
    }

    public void setCurrentShowToEntries(int currentShowToEntries) {
        this.currentShowToEntries = currentShowToEntries;
    }
}
