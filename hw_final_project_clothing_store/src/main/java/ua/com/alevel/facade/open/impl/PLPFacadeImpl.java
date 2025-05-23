package ua.com.alevel.facade.open.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.exception.BadRequestException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
//import ua.com.alevel.service.elastic.ElasticClothesSearchService;
import ua.com.alevel.service.open.PLPService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PLPFacadeImpl implements PLPFacade {

    private final PLPService plpService;
//    private final ElasticClothesSearchService elasticClothesSearchService;

    public PLPFacadeImpl(PLPService plpService) {
        this.plpService = plpService;
//        this.elasticClothesSearchService = elasticClothesSearchService;
    }

    @Override
    public PageData findAll(WebRequest request) {
        Map<String, Object> queryMap = new HashMap<>();
        if (request.getParameterMap().get(WebUtil.BRAND_PARAM) != null) {
            String[] params = request.getParameterMap().get(WebUtil.BRAND_PARAM);
            if (StringUtils.isBlank(params[0])) {
                throw new BadRequestException("bad request");
            }
            Long publisherId = Long.parseLong(params[0]);
            queryMap.put(WebUtil.BRAND_PARAM, publisherId);
        }
        if (request.getParameterMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            String[] params = request.getParameterMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
            if (StringUtils.isBlank(params[0])) {
                throw new BadRequestException("bad request");
            }
            String searchClothes = params[0];
            queryMap.put(WebUtil.SEARCH_CLOTHES_PARAM, searchClothes);
        }
        if (request.getParameterMap().get(WebUtil.COLOR_PARAM) != null) {
            String[] params = request.getParameterMap().get(WebUtil.COLOR_PARAM);
            if (StringUtils.isBlank(params[0])) {
                throw new BadRequestException("bad request");
            }
            String color = params[0];
            queryMap.put(WebUtil.COLOR_PARAM, color);
        }
        if (request.getParameterMap().get(WebUtil.CLOTHES_SIZE_PARAM) != null) {
            String[] params = request.getParameterMap().get(WebUtil.CLOTHES_SIZE_PARAM);
            if (StringUtils.isBlank(params[0])) {
                throw new BadRequestException("bad request");
            }
            String color = params[0];
            queryMap.put(WebUtil.CLOTHES_SIZE_PARAM, color);
        }
        if (request.getParameterMap().get(WebUtil.SEX_PARAM) != null) {
            String[] params = request.getParameterMap().get(WebUtil.SEX_PARAM);
            if (StringUtils.isBlank(params[0])) {
                throw new BadRequestException("bad request");
            }
            String searchClothes = params[0];
            queryMap.put(WebUtil.SEX_PARAM, searchClothes);
        }
        if (request.getParameterMap().get(WebUtil.TYPE_PARAM) != null) {
            String[] params = request.getParameterMap().get(WebUtil.TYPE_PARAM);
            if (StringUtils.isBlank(params[0])) {
                throw new BadRequestException("bad request");
            }
            String searchClothes = params[0];
            queryMap.put(WebUtil.TYPE_PARAM, searchClothes);
        }

        DataTableRequest dataTableRequest = WebUtil.generatePLPDataTableRequestByWebRequest(request);
        dataTableRequest.setRequestParamMap(queryMap);
        DataTableResponse<Clothes> tableResponse = plpService.findAll(dataTableRequest);
        List<ClothesPLPDto> clothes = tableResponse.getItems().stream().
                map(ClothesPLPDto::new).
                collect(Collectors.toList());

        PageData<ClothesPLPDto> pageData = (PageData<ClothesPLPDto>) WebUtil.initPageData(tableResponse);
        pageData.setItems(clothes);
        return pageData;
    }

    @Override
    public ClothesResponseDto findById(Long id) {
        return new ClothesResponseDto(plpService.findById(id));
    }

    @Override
    public List<String> searchClothesNames(String query) {
//        return elasticClothesSearchService.searchClothesNames(query);
        return null;
    }

    @Override
    public Map<Long, String> findAllBrands() {
        return plpService.findAllBrands();
    }

    @Override
    public Map<Long, String> findAllColors() {
        return plpService.findAllColors();
    }

    @Override
    public Map<Long, String> findAllTypes() {
        return plpService.findAllTypes();
    }

    @Override
    public Map<Long, String> findAllSizes() {
        return plpService.findAllSizes();
    }
}
