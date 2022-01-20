package io.micronaut.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.live.views.SubscriberDetail;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/subscriber")
class SubscriberUpdateController {

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/update")
    HttpResponse<?> update(@NonNull @Body @NotNull @Valid SubscriberDetail subscriberDetail) {
        return HttpResponse.seeOther(UriBuilder.of("/subscriber")
                .path(subscriberDetail.getId())
                .build());
    }
}
