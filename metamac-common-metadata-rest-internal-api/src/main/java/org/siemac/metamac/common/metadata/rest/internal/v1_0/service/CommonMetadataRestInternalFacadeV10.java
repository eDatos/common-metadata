package org.siemac.metamac.common.metadata.rest.internal.v1_0.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configurations;

public interface CommonMetadataRestInternalFacadeV10 {

    @GET
    @Produces("application/xml")
    @Path("configurations")
    Configurations findConfigurations(@QueryParam("query") String query, @QueryParam("orderBy") String orderBy);

    @GET
    @Produces("application/xml")
    @Path("configurations/{id}")
    Configuration retrieveConfigurationById(@PathParam("id") String id);
}