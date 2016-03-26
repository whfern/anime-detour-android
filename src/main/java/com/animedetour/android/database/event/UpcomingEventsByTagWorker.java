/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event;

import com.animedetour.android.model.Event;
import com.animedetour.android.model.transformer.Transformer;
import com.animedetour.api.sched.ScheduleEndpoint;
import com.animedetour.api.sched.model.ApiEvent;
import com.inkapplications.groundcontrol.Worker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import org.joda.time.DateTime;
import rx.Subscriber;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks up a single event by a specified tag criteria after synchronizing with
 * the remote API.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class UpcomingEventsByTagWorker implements Worker<Event>
{
    final private Dao<Event, String> localAccess;
    final private ScheduleEndpoint remoteAccess;
    final private TagCriteria criteria;
    final private Transformer<ApiEvent, Event> transformer;

    public UpcomingEventsByTagWorker(
        Dao<Event, String> localAccess,
        ScheduleEndpoint remoteAccess,
        Transformer<ApiEvent, Event> transformer,
        TagCriteria tag
    ) {
        this.localAccess = localAccess;
        this.remoteAccess = remoteAccess;
        this.criteria = tag;
        this.transformer = transformer;
    }

    @Override
    public void call(Subscriber<? super Event> subscriber)
    {
        try {
            this.lookup(subscriber);
        } catch (Exception e) {
            subscriber.onError(e);
        }

        subscriber.onCompleted();
    }

    /**
     * Looks up local data, syncs the repository, then does a new lookup.
     */
    private void lookup(Subscriber<? super Event> subscriber) throws SQLException
    {
        Event currentEvents = this.lookupLocal();
        subscriber.onNext(currentEvents);

        List<ApiEvent> apiEvents = this.remoteAccess.getSchedule();
        List<Event> events = this.transformer.bulkTransform(apiEvents);
        this.saveLocal(events);

        Event newEvent = this.lookupLocal();
        subscriber.onNext(newEvent);
    }

    /**
     * Get an upcoming event by tag and position.
     *
     * Searches for events containing the specified tag, orders them by their
     * start time excluding events that have already started, and returns a
     * single event of the specified position.
     *
     * @return The upcoming event
     */
    @Override
    public Event lookupLocal() throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        Where<Event, String> where = builder.where();
        where.like("tags", "%" + this.criteria.getSearch() + "%");
        where.and();
        where.gt("start", new DateTime());
        builder.orderBy("start", true);
        builder.offset(this.criteria.getOrdinal() - 1);
        builder.limit(1L);
        PreparedQuery<Event> prepared = builder.prepare();

        Event result = this.localAccess.queryForFirst(prepared);

        return result;
    }

    public void saveLocal(List<Event> events) throws SQLException
    {
        for (Event event : events) {
            this.localAccess.createOrUpdate(event);
        }
    }
}
