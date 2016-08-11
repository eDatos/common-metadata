package org.siemac.metamac.common.metadata.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataBaseAsserts;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDoMocks;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.common.test.utils.MetamacAsserts.MapperEnum;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;

public class ExternalItemsDo2DtoMapperTest {

    private Do2DtoMapper         do2DtoMapper         = new Do2DtoMapperImpl();

    private ConfigurationService configurationService = new ConfigurationServiceMockImpl();

    @Before
    public void setConfigurationToMapper() throws Exception {
        setFieldToBaseMapper("configurationService", configurationService);
    }

    @Test
    public void testExternalItemDoToDtoWithNullParameter() throws Exception {
        testExternalItemDoToDto(null);
    }

    @Test
    public void testExternalItemDoToDtoWithExistsParameter() throws Exception {
        testExternalItemDoToDto(CommonMetadataDoMocks.mockAgencyExternalItem());
    }

    private void setFieldToBaseMapper(String fieldName, ConfigurationService fieldValue) throws Exception {
        Field field = do2DtoMapper.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(do2DtoMapper, fieldValue);
    }

    @SuppressWarnings("rawtypes")
    private Method getVisibleExternalItemDoToDtoMethod() throws Exception {
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = ExternalItem.class;
        Method externalItemDoToDtoMethod = do2DtoMapper.getClass().getDeclaredMethod("externalItemToDto", parameterTypes);
        externalItemDoToDtoMethod.setAccessible(true);
        return externalItemDoToDtoMethod;
    }

    private void testExternalItemDoToDto(ExternalItem externalItem) throws Exception {
        Method externalItemDtoToEntityMethod = getVisibleExternalItemDoToDtoMethod();

        Object[] parameters = new Object[1];
        parameters[0] = externalItem;

        ExternalItemDto result = (ExternalItemDto) externalItemDtoToEntityMethod.invoke(do2DtoMapper, parameters);
        CommonMetadataBaseAsserts.assertEqualsExternalItem(externalItem, result, MapperEnum.DO2DTO);
    }
}
