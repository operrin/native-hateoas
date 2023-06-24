package org.miage.intervenantservice.boundary;

import org.miage.intervenantservice.control.IntervenantService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;

@Component
public class IntervenantHandler {

    private final IntervenantService is;

    IntervenantHandler(IntervenantService is) {
        this.is = is;
    }

    public Mono<ServerResponse> getAllIntervenants(ServerRequest request) {
        var uriStr = request.uri().toString();
        var collection = Mono.just(Link.of(uriStr).withRel("collection"));
        var response = this.is.getIntervenants()
                .flatMap(item -> Mono.just(Link.of(uriStr + "/" + item.getId()).withRel("self"))
                        .map(sl -> EntityModel.of(item, sl)))
                .collectList()
                .flatMap(items -> collection.map(selfLink -> CollectionModel.of(items, selfLink)));
        var bodyType = ParameterizedTypeReference.forType(RepresentationModel.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, bodyType);
    }
    
    public Mono<ServerResponse> getIntervenantById(ServerRequest request) {
        var collectionLink = Link.of(deleteIdInUri(request.uri().toString(), request.pathVariable("id")))
                .withRel("collection");
        var selfLink = Link.of(request.uri().toString()).withSelfRel();
        return this.is.getIntervenant(request.pathVariable("id"))
                .flatMap(r -> this.is.getIntervenant(request.pathVariable("id"))
                        .map(item -> EntityModel.of(item, selfLink, collectionLink)))
                .flatMap(r -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(r))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private String deleteIdInUri(String uri, String id) {
        if (uri.endsWith("/" + id)) {
            return uri.substring(0, uri.length() - id.length() - 1);
        } else {
            return uri;
        }
    }

}
