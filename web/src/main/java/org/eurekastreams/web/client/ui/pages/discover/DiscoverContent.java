/*
 * Copyright (c) 2011 Lockheed Martin Corporation
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
package org.eurekastreams.web.client.ui.pages.discover;

import org.eurekastreams.server.domain.dto.StreamDTO;
import org.eurekastreams.server.domain.dto.StreamDiscoverListsDTO;
import org.eurekastreams.web.client.events.EventBus;
import org.eurekastreams.web.client.events.Observer;
import org.eurekastreams.web.client.events.data.GotStreamDiscoverListsDTOResponseEvent;
import org.eurekastreams.web.client.model.StreamsDiscoveryModel;
import org.eurekastreams.web.client.ui.common.pager.PagerComposite;
import org.eurekastreams.web.client.ui.pages.search.GlobalSearchComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Discover Page.
 */
public class DiscoverContent extends Composite
{
    /**
     * Binder for building UI.
     */
    private static LocalUiBinder binder = GWT.create(LocalUiBinder.class);

    /**
     * Number of streams to show in the "Most Active Streams" section.
     */
    private static final int MOST_ACTIVE_STREAMS_PAGE_SIZE = 9;

    /**
     * Number of featured streams to show.
     */
    private static final int FEATURED_STREAMS_PAGE_SIZE = 3;

    /**
     * CSS resource.
     */
    interface DiscoverStyle extends CssResource
    {

    }

    /**
     * Flow Panel ot contain the stream search box.
     */
    @UiField
    FlowPanel searchFlowPanel;

    /**
     * UI element for streams.
     */
    @UiField
    PagerComposite featuredStreamsComposite;

    /**
     * UI element for streams.
     */
    @UiField
    PagerComposite mostActiveStreamsComposite;

    /**
     * UI element for streams.
     */
    @UiField
    FlowPanel suggestedStreamsPanel;

    /**
     * UI element for streams.
     */
    @UiField
    FlowPanel mostViewedStreamsPanel;

    /**
     * UI element for streams.
     */
    @UiField
    FlowPanel mostFollowedStreamsPanel;

    /**
     * UI element for streams.
     */
    @UiField
    FlowPanel mostRecentStreamsPanel;

    /**
     * Default constructor.
     */
    public DiscoverContent()
    {
        initWidget(binder.createAndBindUi(this));

        EventBus.getInstance().addObserver(GotStreamDiscoverListsDTOResponseEvent.class,
                new Observer<GotStreamDiscoverListsDTOResponseEvent>()
                {
                    public void update(final GotStreamDiscoverListsDTOResponseEvent event)
                    {
                        EventBus.getInstance().removeObserver(GotStreamDiscoverListsDTOResponseEvent.class, this);
                        buildPage(event.getResponse());
                    }
                });

        StreamsDiscoveryModel.getInstance().fetch(null, true);
    }

    /**
     * Build the page.
     * 
     * @param inDiscoverLists
     *            the data to display
     */
    private void buildPage(final StreamDiscoverListsDTO inDiscoverLists)
    {
        if (inDiscoverLists == null)
        {
            return;
        }
        suggestedStreamsPanel.clear();
        mostViewedStreamsPanel.clear();
        mostFollowedStreamsPanel.clear();
        mostRecentStreamsPanel.clear();

        searchFlowPanel.add(new GlobalSearchComposite("e.g., Jane Doe, Cloud Computing, etc.."));

        // --------------------
        // FEATURED STREAMS
        // Note: the data needed for this list is built from the MostActiveStreamsModel, but is actually fetched and
        // stored in the StreamsDiscoveryModel cache, so this request won't hit the server
        featuredStreamsComposite.init(new FeaturedStreamsPagerUiStrategy(FEATURED_STREAMS_PAGE_SIZE));
        featuredStreamsComposite.load();

        // --------------------
        // MOST ACTIVE STREAMS
        // Note: the data needed for this list is built from the MostActiveStreamsModel, but is actually fetched and
        // stored in the StreamsDiscoveryModel cache, so this request won't hit the server
        mostActiveStreamsComposite.init(new MostActiveStreamsPagerUiStrategy(MOST_ACTIVE_STREAMS_PAGE_SIZE));
        mostActiveStreamsComposite.load();

        if (inDiscoverLists.getSuggestedStreams() != null)
        {
            for (StreamDTO stream : inDiscoverLists.getSuggestedStreams())
            {
                suggestedStreamsPanel.add(new DiscoverListItemPanel(stream,
                        DiscoverListItemPanel.ListItemType.MUTUAL_FOLLOWERS));
            }
        }
        if (inDiscoverLists.getMostViewedStreams() != null)
        {
            for (StreamDTO stream : inDiscoverLists.getMostViewedStreams())
            {
                mostViewedStreamsPanel.add(new DiscoverListItemPanel(stream,
                        DiscoverListItemPanel.ListItemType.DAILY_VIEWERS));
            }
        }
        if (inDiscoverLists.getMostFollowedStreams() != null)
        {
            for (StreamDTO stream : inDiscoverLists.getMostFollowedStreams())
            {
                mostFollowedStreamsPanel.add(new DiscoverListItemPanel(stream,
                        DiscoverListItemPanel.ListItemType.FOLLOWERS));
            }
        }
        if (inDiscoverLists.getMostRecentStreams() != null)
        {
            for (StreamDTO stream : inDiscoverLists.getMostRecentStreams())
            {
                mostRecentStreamsPanel.add(new DiscoverListItemPanel(stream,
                        DiscoverListItemPanel.ListItemType.TIME_AGO));
            }
        }
    }

    /**
     * Binder for building UI.
     */
    interface LocalUiBinder extends UiBinder<Widget, DiscoverContent>
    {
    }
}