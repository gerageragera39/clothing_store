package ua.com.alevel.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.ResponseDto;

public final class WebUtil {

    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final String SORT_PARAM = "sort";
    private static final String ORDER_PARAM = "order";
    public static final String DEFAULT_SORT_PARAM_VALUE = "created";
    public static final String DEFAULT_ORDER_PARAM_VALUE = "desc";
    public static final int DEFAULT_PAGE_PARAM_VALUE = 1;
    public static final int DEFAULT_SIZE_PARAM_VALUE = 10;
    public static final int PLP_DEFAULT_SIZE_PARAM_VALUE = 8;
    public static final String BRAND_PARAM = "brand";
    public static final String SEX_PARAM = "sex";
    public static final String TYPE_PARAM = "type";
    public static final String SEARCH_CLOTHES_PARAM = "search_clothes";
    public static final String COLOR_PARAM = "color";
    public static final String CLOTHES_SIZE_PARAM = "clothes_size";

    private WebUtil() { }

    public static DataTableRequest generateDataTableRequestByWebRequest(WebRequest request) {
        DataTableRequest dataTableRequest = new DataTableRequest();
        int page = StringUtils.isBlank(request.getParameter(PAGE_PARAM))
                ? DEFAULT_PAGE_PARAM_VALUE
                : Integer.parseInt(request.getParameter(PAGE_PARAM));
        int size = StringUtils.isBlank(request.getParameter(SIZE_PARAM))
                ? DEFAULT_SIZE_PARAM_VALUE
                : Integer.parseInt(request.getParameter(SIZE_PARAM));
        String sort = StringUtils.isBlank(request.getParameter(SORT_PARAM))
                ? DEFAULT_SORT_PARAM_VALUE
                : request.getParameter(SORT_PARAM);
        String order = StringUtils.isBlank(request.getParameter(ORDER_PARAM))
                ? DEFAULT_ORDER_PARAM_VALUE
                : request.getParameter(ORDER_PARAM);
        dataTableRequest.setOrder(order);
        dataTableRequest.setSort(sort);
        dataTableRequest.setSize(size);
        dataTableRequest.setPage(page);
        return dataTableRequest;
    }

    public static DataTableRequest generatePLPDataTableRequestByWebRequest(WebRequest request) {
        DataTableRequest dataTableRequest = new DataTableRequest();
        int page = StringUtils.isBlank(request.getParameter(PAGE_PARAM))
                ? DEFAULT_PAGE_PARAM_VALUE
                : Integer.parseInt(request.getParameter(PAGE_PARAM));
        int size = StringUtils.isBlank(request.getParameter(SIZE_PARAM))
                ? PLP_DEFAULT_SIZE_PARAM_VALUE
                : Integer.parseInt(request.getParameter(SIZE_PARAM));
        String sort = StringUtils.isBlank(request.getParameter(SORT_PARAM))
                ? DEFAULT_SORT_PARAM_VALUE
                : request.getParameter(SORT_PARAM);
        String order = StringUtils.isBlank(request.getParameter(ORDER_PARAM))
                ? DEFAULT_ORDER_PARAM_VALUE
                : request.getParameter(ORDER_PARAM);
        dataTableRequest.setOrder(order);
        dataTableRequest.setSort(sort);
        dataTableRequest.setSize(size);
        dataTableRequest.setPage(page);
        return dataTableRequest;
    }

    public static PageData<? extends ResponseDto> initPageData(
            DataTableResponse<? extends BaseEntity> tableResponse) {
        PageData<? extends ResponseDto> pageData = new PageData<>();
        pageData.setCurrentPage(tableResponse.getCurrentPage());
        pageData.setPageSize(tableResponse.getPageSize());
        pageData.setOrder(tableResponse.getOrder());
        pageData.setSort(tableResponse.getSort());
//        pageData.setItemsSize(tableResponse.getItemsSize());
        pageData.setItemsSize(tableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    public static void initDataTableResponse(DataTableRequest request, DataTableResponse<? extends BaseEntity> dataTableResponse, long count) {
        dataTableResponse.setItemsSize(count);
        dataTableResponse.setCurrentPage(request.getPage());
        dataTableResponse.setItemsSize(request.getSize());
        dataTableResponse.setOrder(request.getOrder());
        dataTableResponse.setSort(request.getSort());
    }
}
