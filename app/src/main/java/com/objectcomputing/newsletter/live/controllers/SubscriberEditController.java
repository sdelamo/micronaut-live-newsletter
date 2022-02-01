package com.objectcomputing.newsletter.live.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import com.objectcomputing.newsletter.i18n.Messages;
import com.objectcomputing.newsletter.live.Subscriber;
import com.objectcomputing.newsletter.live.model.Alert;
import com.objectcomputing.newsletter.live.model.AlertPage;
import com.objectcomputing.newsletter.live.services.SubscriberShowService;
import com.objectcomputing.newsletter.live.views.HtmlPage;
import com.objectcomputing.newsletter.live.views.SubscriberDetail;
import com.objectcomputing.newsletter.live.views.SubscriberDetailPage;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collections;
import java.util.Optional;

@Controller("/subscriber")
class SubscriberEditController {
    private final SubscriberShowService subscriberShowService;

    SubscriberEditController(SubscriberShowService subscriberShowService) {
        this.subscriberShowService = subscriberShowService;
    }

    @Operation(operationId = "subscriber-edit",
            summary = "renders an HTML FORM to edit subscriber details",
            description = "renders an HTML FORM to edit subscriber details"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML FORM to edit subscriber details")
    @ExecuteOn(TaskExecutors.IO)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.TEXT_HTML)
    @Get("/{id}/edit")
    @View("subscriberedit")
    HtmlPage edit(@PathVariable String id,
                  @NonNull Messages messages) {
        String title = messages.get("subscriberEdit.title", "Edit Subscriber");

        Optional<SubscriberDetail> subscriberOptional = subscriberShowService.findById(id);
        if (!subscriberOptional.isPresent()) {
            String exceptionMessage = messages.get("subscriber.notFoundById", Collections.singletonList(id), "subscriber not found by id" + id);
            throw new HttpStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
        }
        SubscriberDetail subscriber = subscriberOptional.get();
        return new SubscriberDetailPage(title, subscriber);
    }
}