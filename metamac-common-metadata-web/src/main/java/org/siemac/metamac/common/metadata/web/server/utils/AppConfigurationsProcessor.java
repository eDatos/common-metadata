package org.siemac.metamac.common.metadata.web.server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.enums.AppConfPropertyValueType;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.dto.ExternalItemDataConfigurationDto;
import org.siemac.metamac.common.metadata.web.shared.utils.AppConfigurationsUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.MetamacCollectionUtils;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public class AppConfigurationsProcessor {

    private static SrmRestInternalFacade srmRestInternalFacade;

    public static DataConfigurationDto processExternalItemConfiguration(DataConfigurationDto configuration) throws MetamacWebException {
        return processExternalItemsConfigurations(Arrays.asList(configuration)).get(0);
    }

    public static List<DataConfigurationDto> processExternalItemsConfigurations(List<DataConfigurationDto> configurations) throws MetamacWebException {
        Map<TypeExternalArtefactsEnum, List<String>> externalItemsClassification = classifyExternalItemsConfigurations(configurations);

        Map<String, ExternalItemDto> items = retrieveExternalItems(externalItemsClassification);

        List<DataConfigurationDto> result = replaceDataConfigurationsWithExternalItems(configurations, items);

        return result;
    }

    private static List<DataConfigurationDto> replaceDataConfigurationsWithExternalItems(List<DataConfigurationDto> configurations, Map<String, ExternalItemDto> items) {
        List<DataConfigurationDto> result = new ArrayList<DataConfigurationDto>();

        for (DataConfigurationDto dataConfDto : configurations) {
            AppConfPropertyValueType type = AppConfigurationsUtils.guessPropertyEditionValueType(dataConfDto.getConfigurationKey(), dataConfDto.getConfigurationValue());
            DataConfigurationDto newDataConf = null;
            switch (type) {
                case EXTERNAL_ITEM_CODE:
                case EXTERNAL_ITEM_CODELIST:
                case EXTERNAL_ITEM_CONCEPT:
                    String urn = dataConfDto.getConfigurationValue();
                    ExternalItemDto externalItem = urn != null ? items.get(urn) : null;
                    newDataConf = new ExternalItemDataConfigurationDto(dataConfDto, externalItem);
                    break;
                default:
                    newDataConf = dataConfDto;
            }
            result.add(newDataConf);
        }
        return result;
    }

    private static Map<String, ExternalItemDto> retrieveExternalItems(Map<TypeExternalArtefactsEnum, List<String>> externalItemsClassification) throws MetamacWebException {
        Map<String, ExternalItemDto> items = new HashMap<String, ExternalItemDto>();
        for (TypeExternalArtefactsEnum type : externalItemsClassification.keySet()) {
            switch (type) {
                case CODELIST:
                    items.putAll(retrieveCodelists(externalItemsClassification.get(type)));
                    break;
                case CONCEPT:
                    items.putAll(retrieveConcepts(externalItemsClassification.get(type)));
                    break;
                case CODE:
                    items.putAll(retrieveCodes(externalItemsClassification.get(type)));
                    break;
            }
        }
        return items;
    }

    private static Map<String, ExternalItemDto> retrieveCodelists(List<String> urns) throws MetamacWebException {
        SrmExternalResourceRestCriteria criteria = new SrmExternalResourceRestCriteria();
        criteria.setUrns(urns);

        ExternalItemsResult codes = getSrmRestInternalFacade().findCodelists(ServiceContextHolder.getCurrentServiceContext(), criteria, 0, Integer.MAX_VALUE);
        return buildExternalItemsMap(codes.getExternalItemDtos());
    }

    private static Map<String, ExternalItemDto> retrieveCodes(List<String> urns) throws MetamacWebException {
        SrmItemRestCriteria itemWebCriteria = new SrmItemRestCriteria();
        itemWebCriteria.setUrns(urns);

        ExternalItemsResult codes = getSrmRestInternalFacade().findCodes(ServiceContextHolder.getCurrentServiceContext(), itemWebCriteria, 0, Integer.MAX_VALUE);
        return buildExternalItemsMap(codes.getExternalItemDtos());
    }

    private static Map<String, ExternalItemDto> retrieveConcepts(List<String> urns) throws MetamacWebException {
        SrmItemRestCriteria itemWebCriteria = new SrmItemRestCriteria();
        itemWebCriteria.setUrns(urns);

        ExternalItemsResult codes = getSrmRestInternalFacade().findConcepts(ServiceContextHolder.getCurrentServiceContext(), itemWebCriteria, 0, Integer.MAX_VALUE);
        return buildExternalItemsMap(codes.getExternalItemDtos());
    }

    private static Map<String, ExternalItemDto> buildExternalItemsMap(List<ExternalItemDto> dtos) {
        Map<String, ExternalItemDto> map = new HashMap<String, ExternalItemDto>();
        for (ExternalItemDto dto : dtos) {
            map.put(dto.getUrn(), dto);
        }
        return map;
    }

    protected static Map<TypeExternalArtefactsEnum, List<String>> classifyExternalItemsConfigurations(List<DataConfigurationDto> configurations) {
        Map<TypeExternalArtefactsEnum, List<String>> externalItemBags = new HashMap<TypeExternalArtefactsEnum, List<String>>();
        for (int i = 0; i < configurations.size(); i++) {
            DataConfigurationDto dataConfDto = configurations.get(i);
            AppConfPropertyValueType propertyType = AppConfigurationsUtils.guessPropertyEditionValueType(dataConfDto.getConfigurationKey(), dataConfDto.getConfigurationValue());
            String propertyValue = dataConfDto.getConfigurationValue();
            switch (propertyType) {
                case EXTERNAL_ITEM_CODE:
                    MetamacCollectionUtils.putInMultiMapArrayList(externalItemBags, TypeExternalArtefactsEnum.CODE, propertyValue);
                    break;
                case EXTERNAL_ITEM_CODELIST:
                    MetamacCollectionUtils.putInMultiMapArrayList(externalItemBags, TypeExternalArtefactsEnum.CODELIST, propertyValue);
                    break;
                case EXTERNAL_ITEM_CONCEPT:
                    MetamacCollectionUtils.putInMultiMapArrayList(externalItemBags, TypeExternalArtefactsEnum.CONCEPT, propertyValue);
                    break;
            }
        }
        return externalItemBags;
    }

    public static SrmRestInternalFacade getSrmRestInternalFacade() {
        if (srmRestInternalFacade == null) {
            srmRestInternalFacade = ApplicationContextProvider.getApplicationContext().getBean(SrmRestInternalFacade.class);
        }
        return srmRestInternalFacade;
    }
}