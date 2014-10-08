/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Panel Date Deserializer
 *
 * This deserializes the format of the panel start and end dates from their
 * `yyyy-mm-dd HH:mm` format into a Joda DateTime object
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class PanelDateDeserializer extends JsonDeserializer<ReadableInstant>
{
    @Override
    public ReadableInstant deserialize(JsonParser parser, DeserializationContext context)
        throws IOException, JsonProcessingException
    {
        JsonToken token = parser.getCurrentToken();

        if (token != JsonToken.VALUE_STRING) {
            throw context.mappingException("Expected string value for date");
        }

        String value = parser.getText().trim();

        if (value.length() == 0) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm");
        return formatter.parseDateTime(value);
    }
}
