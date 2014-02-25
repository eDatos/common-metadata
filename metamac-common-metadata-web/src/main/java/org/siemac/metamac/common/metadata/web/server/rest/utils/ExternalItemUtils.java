package org.siemac.metamac.common.metadata.web.server.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ListBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agencies;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AgencySchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelists;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
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
        return getItemSchemesAsExternalItemsResult(agencySchemes, agencySchemes.getAgencySchemes());
    }

    // Codelist

    public static ExternalItemsResult getCodelistsAsExternalItemsResult(Codelists codelists) {
        return getItemSchemesAsExternalItemsResult(codelists, codelists.getCodelists());
    }

    // Concept schemes

    public static ExternalItemsResult getConceptSchemesAsExternalItemsResult(ConceptSchemes concepSchemes) {
        return getItemSchemesAsExternalItemsResult(concepSchemes, concepSchemes.getConceptSchemes());
    }

    // Agencies

    public static ExternalItemsResult getAgenciesAsExternalItemsResult(Agencies agencies) {
        return getItemsAsExternalItemsResult(agencies, agencies.getAgencies());
    }

    // Concepts

    public static ExternalItemsResult getConceptsAsExternalItemsResult(Concepts concepts) {
        return getItemsAsExternalItemsResult(concepts, concepts.getConcepts());
    }

    // Codes

    public static ExternalItemsResult getCodesAsExternalItemsResult(Codes codes) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(codes);
        result.setExternalItemDtos(getExternalItemDtosFromCodeResourceInternals(codes.getCodes()));
        return result;
    }

    //
    // COMMON METHODS
    //

    private static ExternalItemsResult getItemSchemesAsExternalItemsResult(ListBase listBase, List<ResourceInternal> items) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(listBase);
        result.setExternalItemDtos(getExternalItemDtosFromResourceInternals(items));
        return result;
    }

    private static ExternalItemsResult getItemsAsExternalItemsResult(ListBase listBase, List<ItemResourceInternal> items) {
        ExternalItemsResult result = getListBaseAsExternalItemsResult(listBase);
        result.setExternalItemDtos(getExternalItemDtosFromItemResourceInternals(items));
        return result;
    }

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
        externalItemDto.setUrn(resourceInternal.getUrn());
        externalItemDto.setUrnProvider(resourceInternal.getUrnProvider());
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

    private static List<ExternalItemDto> getExternalItemDtosFromCodeResourceInternals(List<CodeResourceInternal> resources) {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(resources.size());
        for (ResourceInternal resource : resources) {
            externalItemDtos.add(getExternalItemDtoFromSrmResourceInternal(resource));
        }
        return externalItemDtos;
    }
}
