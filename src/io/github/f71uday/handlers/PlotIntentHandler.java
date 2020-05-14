/*
     Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package io.github.f71uday.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import io.github.f71uday.OMDB.OpenDBAPIFetch;
import io.github.f71uday.utils.SkillUtils;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.omg.CORBA.OMGVMCID;

public class PlotIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("PlotIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        String movieName = "";
        final ResourceBundle messages = SkillUtils.getResourceBundle(handlerInput, "Messages");
        
        final Slot itemSlot = intentRequest.getIntent().getSlots().get("movie");
        if (itemSlot != null)
         {
            movieName = itemSlot.getValue().toLowerCase();
        }
        System.out.println(movieName);
        
        final String cardTitle = String.format(messages.getString("DISPLAY_CARD_TITLE"), messages.getString("SKILL_NAME"), movieName);
        final String plotKey = movieName.replaceAll(" ", "_");
        OpenDBAPIFetch openDBAPIFetch = new OpenDBAPIFetch();
        final String plot = openDBAPIFetch.fetchMovieDetails(movieName);
        if (plot != null && !plot.isEmpty()) {
            // uncomment the reprompt lines if you want to repeat the info
            // and prompt for a subsequent action
            // sessionAttributes.put("repromptSpeech", messages.getString("plot_REPEAT_MESSAGE"));
            final Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
            sessionAttributes.put("speakOutput", plot);
            return handlerInput.getResponseBuilder()
                    .withSimpleCard(cardTitle, plot)
                    .withSpeech(plot)
                    .build();
        }
        final String repromptSpeech = messages.getString("plot_NOT_FOUND_REPROMPT");
        String speakOutput = "";
        if (movieName != null && !movieName.isEmpty()) {
            speakOutput += String.format(messages.getString("plot_NOT_FOUND_WITH_ITEM_NAME"), movieName);
        } else {
            speakOutput += messages.getString("plot_NOT_FOUND_WITHOUT_ITEM_NAME");
        }
        speakOutput += repromptSpeech;
        return handlerInput.getResponseBuilder()
                .withSpeech(speakOutput)
                .withReprompt(repromptSpeech)
                .build();
    }
}
