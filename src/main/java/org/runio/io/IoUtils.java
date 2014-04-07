package org.runio.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.runio.domain.ActivityPair;
import org.runio.garmin.activity.GarminActivitySummary;
import org.runio.runkeeper.activity.RunKeeperActivity;

public class IoUtils {

    public static final ActivityIntervalExtractor<GarminActivitySummary> GARMIN_ACTIVITY_INTERVAL_EXTRACTOR = new ActivityIntervalExtractor<GarminActivitySummary>() {
        @Override
        public Interval extractInterval(GarminActivitySummary activity) {
            LocalDateTime startTime = LocalDateTime.parse(activity.getActivitySummary().getBeginTimestamp().getValue(), GarminActivitySummary.GARMIN_TIME_FORMAT);
            LocalDateTime endTime = LocalDateTime.parse(activity.getActivitySummary().getEndTimestamp().getValue(), GarminActivitySummary.GARMIN_TIME_FORMAT);
            return new Interval(startTime.toDateTime(), endTime.toDateTime());
        }
    };

    public static final ActivityIntervalExtractor<RunKeeperActivity> RUNKEEPER_ACTIVITY_INTERVAL_EXTRACTOR = new ActivityIntervalExtractor<RunKeeperActivity>() {
        @Override
        public Interval extractInterval(RunKeeperActivity activity) {
            LocalDateTime startTime = activity.getStartTime();
            LocalDateTime endTime = startTime.plusSeconds(activity.getTotalDurationInSeconds().intValue());
            return new Interval(startTime.toDateTime(), endTime.toDateTime());
        }
    };

    private static <T> Iterator<T> createSortedIterator(List<T> activities, final ActivityIntervalExtractor<T> intervalExctractor) {
        NavigableSet<T> result = new TreeSet<>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return intervalExctractor.extractInterval(o1).getStart().compareTo(intervalExctractor.extractInterval(o2).getStart());
            }
        });
        result.addAll(activities);
        return result.iterator();
    }

    public static <T, S> List<ActivityPair<T, S>> findIntersectingActivities(
            List<T> leftHandActivities, List<S> rightHandActivities,
            final ActivityIntervalExtractor<T> leftIntervalExtractor, final ActivityIntervalExtractor<S> rightIntervalExtractor) {

        Iterator<T> leftHandSortedIterator = createSortedIterator(leftHandActivities, leftIntervalExtractor);
        Iterator<S> rightHandSortedIterator = createSortedIterator(rightHandActivities, rightIntervalExtractor);

        List<ActivityPair<T, S>> result = new ArrayList<>();

        while (leftHandSortedIterator.hasNext() && rightHandSortedIterator.hasNext()) {
            T left = leftHandSortedIterator.next();
            S right = rightHandSortedIterator.next();
            Interval leftInterval = leftIntervalExtractor.extractInterval(left);
            Interval rightInterval = rightIntervalExtractor.extractInterval(right);
            while (leftInterval.isBefore(rightInterval) && leftHandSortedIterator.hasNext()) {
                left = leftHandSortedIterator.next();
                leftInterval = leftIntervalExtractor.extractInterval(left);
            }
            while (rightInterval.isBefore(leftInterval) && rightHandSortedIterator.hasNext()) {
                right = rightHandSortedIterator.next();
                rightInterval = rightIntervalExtractor.extractInterval(right);
            }
            if (leftInterval.overlaps(rightInterval)) {
                result.add(new ActivityPair<T, S>(left, right));
            }
        }

        return Collections.unmodifiableList(result);
    }


    static interface ActivityIntervalExtractor<T> {
        Interval extractInterval(T activity);
    }
}
