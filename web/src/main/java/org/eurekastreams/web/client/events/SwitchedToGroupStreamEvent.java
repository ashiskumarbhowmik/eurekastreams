/*
 * Copyright (c) 2010 Lockheed Martin Corporation
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
package org.eurekastreams.web.client.events;

import org.eurekastreams.server.domain.stream.GroupStreamDTO;

/**
 * Gets fired when the view has changed to the group stream.
 * 
 */
public class SwitchedToGroupStreamEvent
{
    /**
     * Gets an instance of the event.
     * 
     * @return the event.
     */
    public static SwitchedToGroupStreamEvent getEvent()
    {
        return new SwitchedToGroupStreamEvent(null);
    }

    /**
     * The new view.
     */
    private GroupStreamDTO view;

    /**
     * Default constructor.
     * 
     * @param inView
     *            the new view.
     */
    public SwitchedToGroupStreamEvent(final GroupStreamDTO inView)
    {
        view = inView;
    }

    /**
     * Returns the view.
     * 
     * @return the view.
     */
    public GroupStreamDTO getView()
    {
        return view;
    }
}