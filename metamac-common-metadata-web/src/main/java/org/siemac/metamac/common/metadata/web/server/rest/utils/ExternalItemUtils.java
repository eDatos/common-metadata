package org.siemac.metamac.common.metadata.web.server.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ListBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agencies;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AgencySchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

public class ExternalItemUtils extends org.siemac.metamac.web.common.client.utils.ExternalItemUtils {

    //
    // ORGANISATIONS
    //

    // Agency Schemes

    public static ExternalItemsResult getAgencySchemesAsExternalItemsResult(AgencySchemes agencySchemes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(agencySchemes);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(agencySchemes.getAgencySchemes()));
        return result;
    }

    // Agencies

    public static ExternalItemsResult getAgenciesAsExternalItemsResult(Agencies agencies) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(agencies);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals(agencies.getAgencies()));
        return result;
    }

    //
    // COMMON METHODS
    //

    private static ExternalItemsResult getListBaseAsExternalItemsResult(ListBase listBase) {
        ExternalItemsResult result = new ExternalItemsResult();
        if (listBase != null) {
            result.setFirstResult(listBase.getOffset() != null ? listBase.getOffset().intValue() : 0);
            result.setTotalResults(listBase.getOffset() != null ? listBase.getTotal().intValue() : 0);
        }
        return result;
    }

    private static ExternalItemDto getExternalItemDtoFromSrmResourceInternal(ResourceInternal resourceInternal) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resourceInternal.getId());
        externalItemDto.setCodeNested(resourceInternal.getNestedId());
        externalItemDto.setUri(resourceInternal.getSelfLink().getHref());
        externalItemDto.setUrn(resourceInternal.getUrnSiemac());
        externalItemDto.setUrnProvider(resourceInternal.getUrn());
        externalItemDto.setType(TypeExternalArtefactsEnum.fromValue(resourceInternal.getKind()));
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resourceInternal.getName()));
        externalItemDto.setManagementAppUrl(resourceInternal.getManagementAppLink());
        return externalItemDto;
    }

    private static List<ExternalItemDto> getExternalItemDtosFromResourceInternals(List<ResourceInternal> resources) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(resources.size());
        for (ResourceInternal resource : resources) {
            externalItemDtos.add(getExternalItemDtoFromSrmResourceInternal(resource));
        }
        return externalItemDtos;
    }

    private static List<ExternalItemDto> getExternalItemDtosFromItemResourceInternals(List<ItemResourceInternal> resources) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(resources.size());
        for (ResourceInternal resource : resources) {
            externalItemDtos.add(getExternalItemDtoFromSrmResourceInternal(resource));
        }
        return externalItemDtos;
    }
}
