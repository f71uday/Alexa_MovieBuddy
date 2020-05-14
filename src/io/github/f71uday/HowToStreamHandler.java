/*
     Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package io.github.f71uday;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

import io.github.f71uday.handlers.ErrorHandler;
import io.github.f71uday.handlers.ExitIntentHandler;
import io.github.f71uday.handlers.HelpIntentHandler;
import io.github.f71uday.handlers.LaunchHandler;
import io.github.f71uday.handlers.PlotIntentHandler;
import io.github.f71uday.handlers.RepeatIntentHandler;
import io.github.f71uday.handlers.SessionEndedHandler;

public class HowToStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new LaunchHandler(),
                        new HelpIntentHandler(),
                        new PlotIntentHandler(),
                        new RepeatIntentHandler(),
                        new ExitIntentHandler(),
                        new ErrorHandler(),
                        new SessionEndedHandler()
                )
                // Add your skill id below
                // .withSkillId("")
                .build();
    }

    public HowToStreamHandler() {
        super(getSkill());
    }
}
