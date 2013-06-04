package org.siemac.metamac.common.metadata.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataBaseAsserts;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDoMocks;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDtoMocks;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.common.test.utils.MetamacAsserts.MapperEnum;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

public class ExternalItemsDto2DoMapperTest {

    @Rule
    public ExpectedException       thrown               = ExpectedException.none();

    private Dto2DoMapper           dto2DoMapper         = new Dto2DoMapperImpl();

    protected ConfigurationService configurationService = new ConfigurationServiceMockImpl();
    ExternalItemRepository         repository           = Mockito.mock(ExternalItemRepository.class);

    private static final String    URN_01               = "lorem:ipsum:externalItem:mock:01";
    private static final String    METADATA_NAME        = "LOREM_IPSUM";

    @Before
    public void setConfigurationToMapper() throws Exception {
        setFieldToBaseMapper("configurationService", configurationService);
        setFieldToMapper("externalItemRepository", repository);
    }

    @After
    public void validateMockitoUsage() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testExternalItemDtoToEntityNullDtoAndNullDo() throws Exception {
        // NULL, NULL
        testExternalItemDtoToEntity(null, null);
    }

    @Test
    public void testExternalItemDtoToEntityExistsDtoAndNullDo() throws Exception {
        // EXISTS, NULL
        ExternalItemDto externalItemDto = CommonMetadataDtoMocks.mockExternalItemDtoComplete(URN_01, TypeExternalArtefactsEnum.AGENCY);
        testExternalItemDtoToEntity(externalItemDto, null);
    }

    @Test
    public void testExternalItemDtoToEntityNullDtoAndExistsDo() throws Exception {
        // NULL, EXISTS
        ExternalItem externalItem = CommonMetadataDoMocks.mockAgencyExternalItem();
        testExternalItemDtoToEntity(null, externalItem);
        Mockito.verify(repository).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoToEntityExistsDtoAndExistsDo() throws Exception {
        // EXISTS, EXISTS
        ExternalItemDto externalItemDto = MetamacMocks.mockExternalItemDtoComplete(URN_01, TypeExternalArtefactsEnum.AGENCY);
        ExternalItem externalItem = CommonMetadataDoMocks.mockAgencyExternalItem();
        testExternalItemDtoToEntity(externalItemDto, externalItem);
    }

    private void setFieldToBaseMapper(String fieldName, ConfigurationService fieldValue) throws Exception {
        Field field = dto2DoMapper.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(dto2DoMapper, fieldValue);
    }
    
    private void setFieldToMapper(String fieldName, Object fieldValue) throws Exception {
        Field field = dto2DoMapper.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(dto2DoMapper, fieldValue);
    }

    @SuppressWarnings("rawtypes")
    private Method getVisibleExternalItemDtoToEntityMethod() throws Exception {
        Class[] parameterTypes = new Class[3];
        parameterTypes[0] = ExternalItemDto.class;
        parameterTypes[1] = ExternalItem.class;
        parameterTypes[2] = String.class;
        Method externalItemDtoToEntityMethod = dto2DoMapper.getClass().getDeclaredMethod("externalItemDtoToDo", parameterTypes);
        externalItemDtoToEntityMethod.setAccessible(true);
        return externalItemDtoToEntityMethod;
    }

    private void testExternalItemDtoToEntity(ExternalItemDto externalItemDto, ExternalItem externalItem) throws Exception {
        Method externalItemDtoToEntityMethod = getVisibleExternalItemDtoToEntityMethod();

        Object[] parameters = new Object[3];
        parameters[0] = externalItemDto;
        parameters[1] = externalItem;
        parameters[2] = METADATA_NAME;

        ExternalItem result = (ExternalItem) externalItemDtoToEntityMethod.invoke(dto2DoMapper, parameters);
        CommonMetadataBaseAsserts.assertEqualsExternalItem(result, externalItemDto, MapperEnum.DTO2DO);
    }
}
