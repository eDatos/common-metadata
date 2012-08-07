package org.siemac.metamac.common.metadata.rest.internal.v1_0.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;

public interface CommonMetadataRestInternalFacadeV10 {

    // TODO
    // @GET
    // @Produces({"application/xml", "application/json" })
    // @Path("configurations")
    // ResourcesNoPagedResult findConfigurations(@QueryParam("query") String query, @QueryParam("orderBy") String orderBy);

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("configurations/{id}")
    Configuration retrieveConfigurationById(@PathParam("id") String id);
}