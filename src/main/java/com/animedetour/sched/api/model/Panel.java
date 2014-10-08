/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.model;

import com.animedetour.sched.api.deserialization.PanelDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Panel / Event
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class Panel
{
    /**
     * Globally Unique ID for the panel
     */
    private String id;

    /**
     * A key specific to this panel
     */
    private String eventKey;

    /**
     * The Name of the panel
     */
    private String name;

    /**
     * The start time of the panel
     */
    private DateTime start;

    /**
     * End time of the panel
     */
    private DateTime end;

    /**
     * The Type specified for the event. E.g. 'Programming' or 'Room Parties'
     */
    private String eventType;

    /**
     * An optional subtype for the event. E.g. 'Panel' or 'Game'
     */
    private String eventSubType;

    /**
     * The detailed description of the event panel
     */
    private String description;

    /**
     * The name of the venue location where the panel is located
     */
    private String venue;

    /**
     * A unique Identifier for the venue location where the panel is located
     */
    private String venueId;

    /**
     * A list of official speakers running the event
     */
    private List<Speaker> speakers;

    /**
     * @return Globally Unique ID for the panel
     */
    final public String getId()
    {
        return id;
    }

    /**
     * @param id Globally Unique ID for the panel
     */
    @JsonProperty("id")
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return A key specific to this panel
     */
    final public String getEventKey()
    {
        return eventKey;
    }

    /**
     * @param eventKey A key specific to this panel
     */
    @JsonProperty("event_key")
    public void setEventKey(String eventKey)
    {
        this.eventKey = eventKey;
    }

    /**
     * @return The Name of the panel
     */
    final public String getName()
    {
        return name;
    }

    /**
     * @param name The Name of the panel
     */
    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get event Start as Joda DateTime object
     *
     * @return The start time of the panel
     */
    final public DateTime getStartDateTime()
    {
        return start;
    }

    /**
     * Set event Start as Joda DateTime object
     *
     * @param start The start time of the panel
     */
    @JsonProperty("event_start")
    @JsonDeserialize(using = PanelDateDeserializer.class)
    public void setStartDateTime(DateTime start)
    {
        this.start = start;
    }

    /**
     * Get event end as Joda DateTime object
     *
     * @return The end time of the panel
     */
    final public DateTime getEndDateTime()
    {
        return end;
    }

    /**
     * Set event end as Joda DateTime object
     *
     * @param end The end time of the panel
     */
    @JsonProperty("event_end")
    @JsonDeserialize(using = PanelDateDeserializer.class)
    public void setEndDateTime(DateTime end)
    {
        this.end = end;
    }

    /**
     * @return The Type specified for the event. E.g. 'Programming' or 'Room Parties'
     */
    final public String getEventType()
    {
        return eventType;
    }

    /**
     * @param eventType The Type specified for the event. E.g. 'Programming' or 'Room Parties'
     */
    @JsonProperty("event_type")
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    /**
     * @return An optional subtype for the event. E.g. 'Panel' or 'Game'
     */
    final public String getEventSubType()
    {
        return eventSubType;
    }

    /**
     * @param eventSubType An optional subtype for the event. E.g. 'Panel' or 'Game'
     */
    @JsonProperty("event_subtype")
    public void setEventSubType(String eventSubType)
    {
        this.eventSubType = eventSubType;
    }

    /**
     * @return The detailed description of the event panel
     */
    final public String getDescription()
    {
        return description;
    }

    /**
     * @param description The detailed description of the event panel
     */
    @JsonProperty("description")
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return The name of the venue location where the panel is located
     */
    final public String getVenue()
    {
        return venue;
    }

    /**
     * @param venue The name of the venue location where the panel is located
     */
    @JsonProperty("venue")
    public void setVenue(String venue)
    {
        this.venue = venue;
    }

    /**
     * @return A unique Identifier for the venue location where the panel is located
     */
    final public String getVenueId()
    {
        return venueId;
    }

    /**
     * @param venueId A unique Identifier for the venue location where the panel is located
     */
    @JsonProperty("venue_id")
    public void setVenueId(String venueId)
    {
        this.venueId = venueId;
    }

    /**
     * @return A list of official speakers running the event
     */
    final public List<Speaker> getSpeakers()
    {
        return speakers;
    }

    /**
     * @param speakers A list of official speakers running the event
     */
    @JsonProperty("speakers")
    public void setSpeakers(List<Speaker> speakers)
    {
        this.speakers = speakers;
    }
}
