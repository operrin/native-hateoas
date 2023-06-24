package org.miage.intervenantservice.boundary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class IntervenantRouter {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(IntervenantHandler intervenantHandler) {
        return route(GET("/intervenants").and(accept(MediaType.APPLICATION_JSON)),
                intervenantHandler::getAllIntervenants)
                .andRoute(GET("/intervenants/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        intervenantHandler::getIntervenantById);
    }
}
