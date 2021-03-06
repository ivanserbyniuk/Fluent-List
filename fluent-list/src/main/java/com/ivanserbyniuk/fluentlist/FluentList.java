package com.ivanserbyniuk.fluentlist;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * The type Fluent list.
 * @param <T> the type parameter
 */
public class FluentList<T>
        implements List<T> {

    private final List<T> list;

    private FluentList(List<T> list) {
        this.list = list;
    }

    /**
     * Create FluentList from List.
     * @param <T> the type parameter
     * @param list the list
     * @return the fluent list
     */
    public static <T> FluentList<T> from(List<T> list) {
        return new FluentList<>(list);
    }

    /**
     * Create FluentList from Array.
     * @param <T> the type parameter
     * @param array the array
     * @return the fluent list
     */
    public static <T> FluentList<T> from(T... array) {
        return new FluentList<>(new ArrayList<>(Arrays.asList(array)));
    }

    /**
     * Create FluentList from Set.
     * @param <T> the type parameter
     * @param set the set
     * @return the fluent list
     */
    public static <T> FluentList<T> from(Set<T> set) {
        FluentList<T> fluentList = new FluentList<>(new ArrayList<T>());
        fluentList.addAll(set);
        return fluentList;
    }

    /**
     * Create FluentList from Map.
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the fluent list with entry[key, value]
     */
    public static <K, V> FluentList<Map.Entry<K, V>> from(Map<K, V> map) {
        return from(map.entrySet());
    }

    public static FluentList<Integer> intRange(int startInclusive, int endExclusive) {
        ArrayList<Integer> arrayList = new ArrayList<>(endExclusive);
        if (startInclusive >= endExclusive) {
            return from(Collections.<Integer>emptyList());
        } else {
            int lastIndex = startInclusive + endExclusive;
            for (int i = startInclusive; i <= lastIndex; i++) {
                arrayList.add(i);
            }
        }
        return from(arrayList);

    }

    /**
     * Transform list items and return new list.
     * @param <R> the type parameter
     * @param transform the transform
     * @return fluent list
     */
    public <R> FluentList<R> map(final ListUtils.TransformFunc<T, R> transform) {
        return from(ListUtils.map(list, transform));
    }

    /**
     * Filter list by predicate.
     * @param predicate the predicate
     * @return the fluent list
     */
    public FluentList<T> filter(final ListUtils.Predicate<T> predicate) {
        return from(ListUtils.filter(list, predicate));
    }

    /**
     * Return first item of list
     */
    public T firstOrNull() {
        return list.get(0);
    }

    /**
     * Return last item of list
     */
    public T lastOrNull() {
        return list.get(list.size() - 1);
    }

    /**
     * Find item by predicate.
     * @param predicate the predicate
     * @return the first item by the predicate.
     */
    public T firstOrNull(final ListUtils.Predicate<T> predicate) {
        return ListUtils.firstOrNull(list, predicate);
    }

    /**
     * Sort list by predicate.
     * @param <R> the type parameter
     * @param transform the transform
     * @return the fluent list
     */
    @NonNull
    public <R extends Comparable<R>> FluentList<T> sortedBy(final ListUtils.TransformFunc<T, R> transform) {
        return from(ListUtils.sortedBy(list, transform));
    }

    /**
     * Iterate each element.
     * @param consumer the consumer
     */
    public void forEachItem(ListUtils.ConsumerFunc<T> consumer) {
        ListUtils.forEach(list, consumer);
    }

    /**
     * Iterate each element with index.
     * @param consumer the consumer
     */
    public void forEachIndexes(ListUtils.ConsumerFunc2<Integer, T> consumer) {
        ListUtils.forEachIndexes(list, consumer);
    }

    /**
     * Distinct fluent list.
     * Users
     * @return a list containing only distinct elements from the given list
     */
    public FluentList<T> distinct() {
        return from(toSet());
    }

    /**
     * Distinct fluent list by keySelector.
     * @param <K> the type parameter
     * @param keySelector the key selector
     * @return the fluent list
     */
    public <K> FluentList distinct(final ListUtils.TransformFunc<T, K> keySelector) {
        HashSet<K> set = new HashSet<>();
        List<T> list = new ArrayList<>();
        for (T item : this) {
            K key = keySelector.apply(item);
            if (set.add(key)) {
                list.add(item);
            }
        }
        return from(list);
    }

    /**
     * Is at least one element matches the given [predicate].
     * @param predicate the predicate
     * @return `true` if at least one element matches the given [predicate]
     */
    public boolean any(final ListUtils.Predicate<T> predicate) {
        return ListUtils.any(list, predicate);
    }

    /**
     * Is all elements match the given [predicate].
     * @param predicate the predicate
     * @return `true` if all elements match the given [predicate]
     */
    public boolean all(final ListUtils.Predicate<T> predicate) {
        return ListUtils.all(list, predicate);
    }

    /**
     * Is no elements match the given [predicate].
     * @param predicate the predicate
     * @return true if no elements match the given [predicate]
     */
    public boolean non(final ListUtils.Predicate<T> predicate) {
        return ListUtils.non(list, predicate);
    }

    /**
     * Returns the number of elements matching the given [predicate].
     */
    public int count(final ListUtils.Predicate<T> predicate) {
        return ListUtils.count(list, predicate);
    }

    /**
     * Gets a single list of all elements yielded from results of [transform] function being invoked on each element of
     * original collection..
     * @param <R> the type parameter
     * @param transformer the transformer
     * @return fluent list
     */
    public <R> FluentList<R> flatMap(final ListUtils.TransformFunc<T, List<R>> transformer) {
        return from(ListUtils.flatMap(list, transformer));
    }

    /**
     * Groups elements of the original collection by the key returned by the given [kepluySelector] function
     * applied to each element and returns a map where each group key is associated with a list of corresponding
     * elements.
     * @param <K> the type parameter
     * @param keySelector the transform
     * @return the grouped map
     */
    public <K> Map<K, List<T>> groupBy(final ListUtils.TransformFunc<T, K> keySelector) {
        return ListUtils.groupBy(list, keySelector);
    }

    /**
     * Provide reduce operation with lists, such as finding min or max value in the list, sum or multiply all items.
     * @param reducer the reducer
     * @return the reduced value
     */
    public T reduce(final ListUtils.BiOperationFunc<T, T> reducer) {
        return ListUtils.reduce(list, reducer);
    }

    /**
     * Returns the first element yielding the smallest value of the given function or throw exception .
     */
    public <R extends Comparable<R>> T minBy(final ListUtils.TransformFunc<T, R> transform) {
        return ListUtils.reduce(list, new ListUtils.BiOperationFunc<T, T>() {
            @Override
            public T apply(T first, T second) {
                return transform.apply(first).compareTo(transform.apply(second)) == -1 ? first : second;
            }
        });
    }

    /**
     * Returns the first element yielding the largest value of the given function or trow exception` if there are no
     * elements.
     */
    public <R extends Comparable<R>> T maxBy(final ListUtils.TransformFunc<T, R> transform) {
        return ListUtils.reduce(list, new ListUtils.BiOperationFunc<T, T>() {
            @Override
            public T apply(T first, T second) {
                return transform.apply(first).compareTo(transform.apply(second)) == 1 ? first : second;
            }
        });
    }

    /**
     * Returns the sum of all values produced by [transform] function applied to each element in the collection.
     */
    public Integer sumBy(final ListUtils.TransformFunc<T, Integer> transform) {
        List<Integer> intList = new ArrayList<>(list.size());
        for (T item : list) {
            intList.add(transform.apply(item));
        }
        return ListUtils.reduce(intList, new ListUtils.BiOperationFunc<Integer, Integer>() {
            @Override
            public Integer apply(Integer first, Integer second) {
                return first + second;
            }
        });
    }

    /**
     * Creates a string from all the elements separated using [separator].
     * @param <R> the type parameter
     * @param separator the separator
     * @param transform the transform
     * @return the string
     */
    public <R> String joinToStringBy(String separator, final ListUtils.TransformFunc<T, R> transform) {
        return ListUtils.joinToStringBy(list, separator, transform);
    }

    /**
     * Creates a string from all the elements separated using ", " as separator.
     * @param <R> the type parameter
     * @param transform the transform
     * @return the string
     */
    public <R> String joinToStringBy(final ListUtils.TransformFunc<T, R> transform) {
        return ListUtils.joinToStringBy(list, transform);
    }

    public FluentList<T> reversed() {
        ArrayList<T> arrayList = new ArrayList<>(this.size());
        arrayList.addAll(this);
        Collections.reverse(arrayList);
        return from(arrayList);
    }

    /**
     * CreateeSet form current list.
     * @return the Set
     */
    public Set<T> toSet() {
        return new HashSet<>(this);
    }

    /**
     * To array t [ ].
     * @param tClass the t class
     * @return the t [ ]
     */
    public T[] toTypedArray(Class<T[]> tClass) {
        T[] array = tClass.cast(Array.newInstance(tClass.getComponentType(), size()));
        return toArray(array);
    }

    public FluentList<T> plus(T item) {
        ArrayList<T> arrayList = new ArrayList<>(list.size() + 1);
        arrayList.addAll(this);
        arrayList.add(item);
        return from(arrayList);
    }

    public FluentList<T> plus(Collection<T> iterable) {
        ArrayList<T> arrayList = new ArrayList<>(this.size() + iterable.size());
        arrayList.addAll(iterable);
        return from(arrayList);
    }

    /**
     * Create immutable list.
     */
    public List<T> toImmutableList() {
        return new AbstractList<T>() {

            private UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Immutable list");

            @Override
            public T get(int index) {
                return list.get(index);
            }

            @Override
            public int size() {
                return list.size();
            }

            @Override
            public boolean add(T t) {
                throw unsupportedOperationException;
            }

            @Override
            public boolean addAll(Collection<? extends T> c) {
                throw unsupportedOperationException;
            }

            @Override
            public void clear() {
                throw unsupportedOperationException;
            }
        };
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
