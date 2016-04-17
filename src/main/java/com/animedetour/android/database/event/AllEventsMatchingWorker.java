/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015-2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event;

import com.animedetour.android.model.Event;
import com.animedetour.android.model.MetaData;
import com.animedetour.android.model.transformer.Transformer;
import com.animedetour.api.sched.ScheduleEndpoint;
import com.animedetour.api.sched.model.ApiEvent;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import monolog.Monolog;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks up a list of all locally stored events that match a search query from
 * the user.
 *
 * Events are matched *roughly* by name, and will match if the exact query
 * matches the event category, tags or hosts, to allow for filtering.
 *
 * Events will be ordered by start time, then name.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class AllEventsMatchingWorker extends SyncEventsWorker
{
    final private Dao<Event, String> localAccess;
    final private String criteria;

    public AllEventsMatchingWorker(
        Dao<Event, String> localAccess,
        Dao<MetaData, Integer> metaData,
        ScheduleEndpoint remoteAccess,
        Transformer<ApiEvent, Event> eventTransformer,
        Monolog logger,
        String criteria
    ) {
        super(localAccess, metaData, remoteAccess, eventTransformer, logger);

        this.localAccess = localAccess;
        this.criteria = criteria;
    }

    @Override
    public List<Event> lookupLocal() throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        builder.orderBy("start", true);
        builder.orderBy("name", true);

        String criteria = this.criteria.trim();

        builder.where().like("name", new SelectArg("%" + criteria + "%"))
            .or().eq("category", new SelectArg(criteria))
            .or().like("tags", new SelectArg("%" + criteria + "%"))
            .or().like("hosts", new SelectArg("%" + criteria + "%"))
            .or().like("room", new SelectArg("%" + criteria + "%"));

        PreparedQuery<Event> query = builder.prepare();
        List<Event> result = this.localAccess.query(query);

        return result;
    }
}
