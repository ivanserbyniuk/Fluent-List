package com.ivanserbyniuk.fluentlist;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ivan on 2/12/18.
 */

public class FluentList<T>
        implements List<T> {
    private final List<T> list;

    private FluentList(List<T> list) {
        this.list = list;
    }

    public static <T> FluentList<T> from(List<T> list) {
        return new FluentList<>(list);
    }

    public static <T> FluentList<T> from(T[] array) {
        return new FluentList<>(new ArrayList<>(Arrays.asList(array)));
    }

    public static <T> FluentList<T> from(Set<T> set) {
        FluentList<T> fluentList = new FluentList<>(new ArrayList<T>());
        fluentList.addAll(set);
        return fluentList;
    }

    public static <K, V> FluentList<Map.Entry<K, V>> from(Map<K, V> map) {
        return from(map.entrySet());
    }

    public <R> FluentList<R> map(final ListUtils.TransformFunc<T, R> transform) {
        return from(ListUtils.map(list, transform));
    }

    public FluentList<T> filter(final ListUtils.Predicate<T> predicate) {
        return from(ListUtils.filter(list, predicate));
    }

    public T firstOrNull(final ListUtils.Predicate<T> predicate) {
        return ListUtils.firstOrNull(list, predicate);
    }

    @NonNull
    public <R extends Comparable<R>> FluentList<T> sortedBy(final ListUtils.TransformFunc<T, R> transform) {
        return from(ListUtils.sortedBy(list, transform));
    }

    public void forEachItem(ListUtils.ConsumerFunc<T> consumer) {
        ListUtils.forEach(list, consumer);
    }

    public void forEachIndexes(ListUtils.ConsumerFunc2<Integer, T> consumer) {
        ListUtils.forEachIndexes(list, consumer);
    }

    public boolean any(final ListUtils.Predicate<T> predicate) {
        return ListUtils.any(list, predicate);
    }

    public boolean all(final ListUtils.Predicate<T> predicate) {
        return ListUtils.all(list, predicate);
    }

    public boolean non(final ListUtils.Predicate<T> predicate) {
        return ListUtils.non(list, predicate);
    }

    public <R> FluentList<R> flatMap(final ListUtils.TransformFunc<T, List<R>> transformer) {
        return from(ListUtils.flatMap(list, transformer));
    }

    public <K> Map<K, List<T>> groupBy(final ListUtils.TransformFunc<T, K> keySelector) {
        return ListUtils.groupBy(list, keySelector);
    }

    public T reduce(final ListUtils.BiOperationFunc<T, T> reducer) {
        return ListUtils.reduce(list, reducer);
    }

    public <R> String joinToStringBy(String separator, final ListUtils.TransformFunc<T, R> transform) {
        return ListUtils.joinToStringBy(list, separator, transform);
    }

    public <R> String joinToStringBy(final ListUtils.TransformFunc<T, R> transform) {
        return ListUtils.joinToStringBy(list, transform);
    }

    public Set<T> toSet() {
        return new HashSet<>(this);
    }

    public T[] toTypedArray(Class<T[]> tClass) {
        T[] array = tClass.cast(Array.newInstance(tClass.getComponentType(), size()));
        return toArray(array);
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
