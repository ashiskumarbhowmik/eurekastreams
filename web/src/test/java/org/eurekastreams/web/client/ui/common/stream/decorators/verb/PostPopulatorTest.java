/*
 * Copyright (c) 2009 Lockheed Martin Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eurekastreams.web.client.ui.common.stream.decorators.verb;

import junit.framework.Assert;

import org.junit.Test;
import org.eurekastreams.server.domain.stream.ActivityDTO;
import org.eurekastreams.server.domain.stream.ActivityVerb;

/**
 * Test the post populator.
 * 
 */
public class PostPopulatorTest
{
    /**
     * populate test.
     */
    @Test
    public final void populate()
    {
        PostPopulator sut = new PostPopulator();
        ActivityDTO activity = new ActivityDTO();
        sut.populate(activity);
        Assert.assertEquals(ActivityVerb.POST, activity.getVerb());
    }
}
