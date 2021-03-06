/*
 * Copyright (c) 2009-2011 Lockheed Martin Corporation
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
package org.eurekastreams.web.client.ui.common.stream.filters.list;

import org.eurekastreams.server.domain.stream.Stream;
import org.eurekastreams.server.domain.stream.StreamFilter;
import org.eurekastreams.web.client.events.ChangeShowStreamRecipientEvent;
import org.eurekastreams.web.client.events.HideNotificationEvent;
import org.eurekastreams.web.client.events.Observer;
import org.eurekastreams.web.client.events.StreamRequestEvent;
import org.eurekastreams.web.client.events.SwitchedToActivityDetailViewEvent;
import org.eurekastreams.web.client.events.SwitchedToCustomStreamEvent;
import org.eurekastreams.web.client.events.UpdateHistoryEvent;
import org.eurekastreams.web.client.history.CreateUrlRequest;
import org.eurekastreams.web.client.ui.Session;
import org.eurekastreams.web.client.ui.common.dialog.Dialog;
import org.eurekastreams.web.client.ui.common.stream.filters.FilterPanel;
import org.eurekastreams.web.client.ui.common.stream.renderers.ShowRecipient;
import org.eurekastreams.web.client.ui.pages.master.StaticResourceBundle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

/**
 * Represents a single list item.
 *
 */
public class CustomStreamPanel extends Composite implements FilterPanel
{

    /**
     * The view associated with it.
     */
    private Stream stream;

    /**
     * Label Container.
     */
    private final FlowPanel labelContainer;

    /**
     * Label.
     */
    private final Label label;

    /**
     * The move handle.
     */
    private final Label moveHandle;

    /**
     * Read only.
     */
    Boolean readOnly = false;

    /**
     * Default constructor.
     *
     * @param inStream
     *            the view.
     */
    public CustomStreamPanel(final Stream inStream)
    {
        FocusPanel container = new FocusPanel();
        container.addStyleName(StaticResourceBundle.INSTANCE.coreCss().filter());

        FlowPanel panel = new FlowPanel();

        labelContainer = new FlowPanel();
        labelContainer.addStyleName(StaticResourceBundle.INSTANCE.coreCss().filterLabel());
        label = new Label(inStream.getName());
        labelContainer.add(label);
        stream = inStream;

        panel.addStyleName(StaticResourceBundle.INSTANCE.coreCss().streamListItem());
        readOnly = stream.getReadOnly();

        container.addClickHandler(new ClickHandler()
        {
            public void onClick(final ClickEvent event)
            {
                updateHistory();
            }
        });

        panel.add(labelContainer);

        moveHandle = new Label("move");
        moveHandle.addStyleName(StaticResourceBundle.INSTANCE.coreCss().moveHandle());
        panel.add(moveHandle);

        InlineLabel seperator = new InlineLabel();
        seperator.addStyleName(StaticResourceBundle.INSTANCE.coreCss().filterSeperator());
        panel.add(seperator);

        seperator.addClickHandler(new ClickHandler()
        {
            public void onClick(final ClickEvent event)
            {
                updateHistory();
            }
        });

        if (!readOnly)
        {
            Anchor editButton = new Anchor("edit");
            editButton.addClickHandler(new ClickHandler()
            {
                public void onClick(final ClickEvent event)
                {
                    Session.getInstance().getEventBus().notifyObservers(new HideNotificationEvent());
                    Dialog.showCentered(new CustomStreamDialogContent(stream));
                    event.stopPropagation();
                }
            });
            editButton.addStyleName(StaticResourceBundle.INSTANCE.coreCss().editButton());
            panel.add(editButton);

        }

        Session.getInstance().getEventBus().addObserver(SwitchedToActivityDetailViewEvent.class,
                new Observer<SwitchedToActivityDetailViewEvent>()
                {
                    public void update(final SwitchedToActivityDetailViewEvent arg1)
                    {
                        unActivate();
                    }
                });

        Session.getInstance().getEventBus().addObserver(StreamRequestEvent.class, new Observer<StreamRequestEvent>()
        {
            public void update(final StreamRequestEvent arg1)
            {
                unActivate();
            }
        });

        container.add(panel);
        initWidget(container);
    }

    /**
     * Set view.
     *
     * @param inStream
     *            the view.
     */
    public void setFilter(final StreamFilter inStream)
    {
        stream = (Stream) inStream;
        label.setText(stream.getName());
    }

    /**
     * Get item id.
     *
     * @return the item id.
     */
    public Long getItemId()
    {
        return stream.getId();
    }

    /**
     * Get the mode handle.
     *
     * @return the move handle.
     */
    public Label getMoveHandle()
    {
        return moveHandle;
    }

    /**
     * Activates the view item.
     */
    public void activate()
    {
        Session.getInstance().getEventBus().notifyObservers(
                new StreamRequestEvent(stream.getName(), stream.getId(), stream.getRequest()));

        this.addStyleName(StaticResourceBundle.INSTANCE.coreCss().active());

        Session.getInstance().getEventBus().notifyObservers(new SwitchedToCustomStreamEvent());
        Session.getInstance().getEventBus().notifyObservers(new ChangeShowStreamRecipientEvent(ShowRecipient.YES));
    }

    /**
     * Unactivates the view item.
     */
    public void unActivate()
    {
        this.removeStyleName(StaticResourceBundle.INSTANCE.coreCss().active());
    }

    /**
     * Returns the filter.
     *
     * @return the filter.
     */
    public StreamFilter getFilter()
    {
        return stream;
    }

    /**
     * Updates the history.
     */
    public void updateHistory()
    {
        Session.getInstance().getEventBus().notifyObservers(
                new UpdateHistoryEvent(new CreateUrlRequest("streamId", String.valueOf(stream.getId()), true)));
    }
}
