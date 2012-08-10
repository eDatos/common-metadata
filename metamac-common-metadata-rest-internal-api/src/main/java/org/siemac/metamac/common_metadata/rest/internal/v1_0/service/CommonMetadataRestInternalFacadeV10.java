package org.siemac.metamac.common_metadata.rest.internal.v1_0.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configurations;

@Path("v1.0")
public interface CommonMetadataRestInternalFacadeV10 {

    /**
     * Find configurations
     * 
     * @param query Clause to filter results by metadata. Accepts AND/OR clauses (see @LogicalOperator) and operators as eq, like... (see @ComparisonOperator)
     * @param orderBy Clause to order the results by metadata
     * @param limit Maximum number of results per page
     * @param offset Position of first result
     * @return List of configurations
     */
    @GET
    @Produces("application/xml")
    @Path("configurations")
    Configurations findConfigurations(@QueryParam("query") String query, @QueryParam("orderBy") String orderBy);

    /**
     * Retrieve configuration by id
     * 
     * @param id Id
     * @return Configuration
     */
    @GET
    @Produces("application/xml")
    @Path("configurations/{id}")
    Configuration retrieveConfigurationById(@PathParam("id") String id);
}