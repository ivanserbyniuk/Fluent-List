package com.ivanserbyniuk.fluentlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Helper class for common operation with lists.
 */
@SuppressWarnings("Convert2streamapi")
public class ListUtils {

    private ListUtils() {
    }

    /**
     * Find item by predicate.
     * @param <T> the type parameter
     * @param values the values
     * @param predicate the predicate
     * @return the first item by the predicate.
     */
    @Nullable
    public static <T> T firstOrNull(@NonNull final List<T> values, final Predicate<T> predicate) {
        for (T value : values) {
            if (predicate.test(value)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Transform list items and return new list.
     * @param <T> the type parameter
     * @param <R> the type parameter
     * @param list the list
     * @param transform the transform
     * @return the list
     */
    @NonNull
    public static <T, R> List<R> map(@NonNull final List<T> list, final TransformFunc<T, R> transform) {
        List<R> resultList = new ArrayList<>(list.size());
        for (T item : list) {
            resultList.add(transform.apply(item));
        }
        return resultList;
    }

    /**
     * Filter list by predicate.
     * @param <T> the type parameter
     * @param list the list
     * @param predicate the predicate
     * @return the list
     */
    @NonNull
    public static <T> List<T> filter(@NonNull final List<T> list, final Predicate<T> predicate) {
        List<T> resultList = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    /**
     * Sort list by predicate.
     * @param <T> the type parameter
     * @param <R> the type parameter
     * @param list the list
     * @param transform the transform
     * @return the list
     */
    @NonNull
    public static <T, R extends Comparable<R>> List<T> sortedBy(@NonNull final List<T> list,
                                                                final TransformFunc<T, R> transform) {
        List<T> sortedList = new ArrayList<>(list);
        Collections.sort(sortedList, new Comparator<T>() {
            @Override
            public int compare(T first, T second) {
                R one = transform.apply(first);
                R two = transform.apply(second);
                return one.compareTo(two);
            }
        });
        return list;
    }

    /**
     * Iterate each element.
     * @param <T> the type parameter
     * @param list the list
     * @param consumer the consumer
     */
    public static <T> void forEach(@NonNull final List<T> list, ConsumerFunc<T> consumer) {
        for (T item : list) {
            consumer.apply(item);
        }
    }

    /**
     * Iterate each element with index.
     * @param <T> the type parameter
     * @param list the list
     * @param consumer the consumer
     */
    public static <T> void forEachIndexes(@NonNull final List<T> list, ConsumerFunc2<Integer, T> consumer) {
        for (int i = 0; i < list.size(); i++) {
            consumer.apply(i, list.get(i));
        }
    }

    /**
     * Join to string by property.
     * @param <T> the type parameter
     * @param <R> the type parameter
     * @param list the list
     * @param separator the separator
     * @param transform the transform
     * @return the string
     */
    public static <T, R> String joinToStringBy(@NonNull final List<T> list, String separator, final TransformFunc<T,
            R> transform) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(transform.apply(list.get(i)));
            if (i < list.size() - 1) {
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Join to string by property. Separator by default is ", "
     * @param <T> the type parameter
     * @param <R> the type parameter
     * @param list the list
     * @param transform the transform
     * @return the string
     */
    public static <T, R> String joinToStringBy(@NonNull final List<T> list, final TransformFunc<T, R> transform) {
        return joinToStringBy(list, ", ", transform);
    }

    /**
     * Is all item of list suppress the predicate.
     * @param <T> the type parameter
     * @param list the list
     * @param predicate the predicate
     * @return true is all items of the list suppress the predicate
     */
    public static <T> boolean all(@NonNull final List<T> list, final Predicate<T> predicate) {
        for (T item : list) {
            if (!predicate.test(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is any item of list suppress the predicate.
     * @param <T> the type parameter
     * @param list the list
     * @param predicate the predicate
     * @return true if someone item of the list suppress the predicate
     */
    public static <T> boolean any(@NonNull final List<T> list, final Predicate<T> predicate) {
        for (T item : list) {
            if (predicate.test(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is there ane no items of list which suppress the predicate.
     * @param <T> the type parameter
     * @param list the list
     * @param predicate the predicate
     * @return true if there are no items of the list suppress the predicate
     */
    public static <T> boolean non(@NonNull final List<T> list, final Predicate<T> predicate) {
        return !any(list, predicate);
    }

    /**
     * Returns the number of elements matching the given [predicate].
     */
    public static <T> int count(@NonNull final List<T> list, final Predicate<T> predicate) {
        int count = 0;
        for (T item : list) {
            if (predicate.test(item)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get flat list.
     * @param <T> the type parameter
     * @param <R> the type parameter
     * @param list the list
     * @param transformer the transformer
     * @return flat list
     */
    @NonNull
    public static <T, R> List<R> flatMap(final List<T> list, final TransformFunc<T, List<R>> transformer) {
        ArrayList<R> resultList = new ArrayList<>();
        for (T item : list) {
            resultList.addAll(transformer.apply(item));
        }
        return resultList;
    }

    /**
     * Return HashMap that contains an ArrayList of items of list keyed by a specified {@code keySelector} function.}
     * @param <T> the type parameter
     * @param <K> the type parameter
     * @param list the list
     * @param keySelector the transform
     * @return the group map
     */
    @NonNull
    public static <T, K> Map<K, List<T>> groupBy(final List<T> list, final TransformFunc<T, K> keySelector) {
        HashMap<K, List<T>> listMap = new HashMap<>();
        for (T item : list) {
            K key = keySelector.apply(item);
            if (!listMap.containsKey(key)) {
                listMap.put(key, new ArrayList<T>());
            }
            listMap.get(key).add(item);
        }
        return listMap;
    }

    /**
     * Provide reduce operation with lists, such as finding min or max value in the list, sum or multiply all items.
     * @param <T> the type parameter
     * @param values the values
     * @param reducer the reducer
     * @return the reduced value
     */
    @NonNull
    public static <T> T reduce(@NonNull final List<T> values, final BiOperationFunc<T, T> reducer) {
        Iterator<T> iterator = values.iterator();
        if (!iterator.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        T accumulator = iterator.next();
        while (iterator.hasNext()) {
            accumulator = reducer.apply(accumulator, iterator.next());
        }
        return accumulator;
    }

    /**
     * Binary operation function interface.
     * @param <T> the type parameter
     * @param <R> the type parameter
     */
    public interface BiOperationFunc<T, R> {

        /**
         * Apply r.
         * @param first the first
         * @param second the second
         * @return the r
         */
        R apply(T first, T second);
    }

    /**
     * Represents an operation that accepts a single input argument and returns no result.
     * This is a functional interface and can therefore be used as the assignment target for a lambda expression or
     * method
     * reference.
     * @param <T> the type parameter
     */
    @FunctionalInterface
    public interface ConsumerFunc<T> {

        /**
         * Performs operation on the given argument.
         * @param value the value
         */
        void apply(T value);

    }

    /**
     * Represents an operation that accepts a single input argument and returns no result.
     * This is a functional interface and can therefore be used as the assignment target for a lambda expression or
     * method
     * reference.
     * @param <T> the type parameter
     * @param <T2> the type parameter
     */
    @FunctionalInterface
    public interface ConsumerFunc2<T, T2> {

        /**
         * Performs operation on the given argument.
         * @param value the value
         * @param value2 the value 2
         */
        void apply(T value, T2 value2);

    }

    /**
     * This is a functional interface and can therefore be used as the assignment target for a lambda expression or
     * method reference.
     * @param <T> the type parameter
     */
    @FunctionalInterface
    public interface Predicate<T> {
        /**
         * Evaluates this predicate on the given argument.
         * @param value the value
         * @return the boolean
         */
        boolean test(T value);
    }

    /**
     * This is a functional interface and can therefore be used as the assignment target for a lambda expression or
     * method reference.
     * @param <T> the type parameter
     * @param <R> the type parameter
     */
    public interface TransformFunc<T, R> {
        /**
         * Applies this function to the given arguments..
         * @param value the value
         * @return the boolean
         */
        R apply(T value);
    }

}