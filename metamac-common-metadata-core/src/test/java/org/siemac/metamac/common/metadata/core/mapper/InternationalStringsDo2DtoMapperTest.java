package org.siemac.metamac.common.metadata.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataBaseAsserts;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDoMocks;
import org.siemac.metamac.common.test.MetamacBaseTest;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests.DataBaseProvider;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.springframework.beans.factory.annotation.Value;

public class InternationalStringsDo2DtoMapperTest extends MetamacBaseTest {

    @Value("${metamac.common_metadata.db.provider}")
    private String                     databaseProvider;

    private final Do2DtoMapper         do2DtoMapper         = new Do2DtoMapperImpl();

    private final ConfigurationService configurationService = new ConfigurationServiceMockImpl();

    @Before
    public void setConfigurationToMapper() throws Exception {
        setFieldToBaseMapper("configurationService", configurationService);
    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Test
    public void testInternationalStringDo2DtoWithNullParameter() throws Exception {
        testInternationalStringDoToDto(null);
    }

    @Test
    public void testInternationalStringDo2DtoWithExistsParameter() throws Exception {
        testInternationalStringDoToDto(CommonMetadataDoMocks.mockInternationalString());
    }

    private void setFieldToBaseMapper(String fieldName, ConfigurationService fieldValue) throws Exception {
        Field field = do2DtoMapper.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(do2DtoMapper, fieldValue);
    }

    @SuppressWarnings("rawtypes")
    private Method getVisibleInternationalStringDoToDtoMethod() throws Exception {
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = InternationalString.class;
        Method internationalStringDoToDtoMethod = do2DtoMapper.getClass().getDeclaredMethod("internationalStringToDto", parameterTypes);
        internationalStringDoToDtoMethod.setAccessible(true);
        return internationalStringDoToDtoMethod;
    }

    private void testInternationalStringDoToDto(InternationalString internationalString) throws Exception {
        Method internationalStringDtoToEntityMethod = getVisibleInternationalStringDoToDtoMethod();

        Object[] parameters = new Object[1];
        parameters[0] = internationalString;

        InternationalStringDto result = (InternationalStringDto) internationalStringDtoToEntityMethod.invoke(do2DtoMapper, parameters);
        CommonMetadataBaseAsserts.assertEqualsInternationalString(internationalString, result);
    }

    @Override
    protected DataBaseProvider getDatabaseProvider() {
        return DataBaseProvider.valueOf(databaseProvider);
    }
}
