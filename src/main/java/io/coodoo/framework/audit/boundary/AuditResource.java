package io.coodoo.framework.audit.boundary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.coodoo.framework.audit.boundary.dto.AuditEventDTO;
import io.coodoo.framework.audit.entity.AuditEvent;
import io.coodoo.framework.listing.boundary.Listing;
import io.coodoo.framework.listing.boundary.ListingParameters;
import io.coodoo.framework.listing.boundary.ListingResult;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@Stateless
@Path("/audits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuditResource {

    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Path("/{entity}")
    public ListingResult<AuditEventDTO> getAuditEventsListing(@PathParam("entity") String entity, @BeanParam ListingParameters listingParameters) {

        listingParameters.addFilterAttributes("entity", entity);

        if (listingParameters.getSortAttribute() == null) {
            listingParameters.setSortAttribute("-id");
        }

        ListingResult<AuditEvent> listingResult = Listing.getListingResult(entityManager, AuditEvent.class, listingParameters);
        List<AuditEventDTO> result = listingResult.getResults().stream().map(AuditEventDTO::new).collect(Collectors.toList());

        return new ListingResult<>(result, listingResult.getMetadata());
    }

    @GET
    @Path("/{entity}/{entityId}")
    public ListingResult<AuditEventDTO> getAuditEventsListing(@PathParam("entity") String entity, @PathParam("entityId") Long entityId,
                    @BeanParam ListingParameters listingParameters) {

        listingParameters.addFilterAttributes("entityId", entityId.toString());

        return getAuditEventsListing(entity, listingParameters);
    }

}
