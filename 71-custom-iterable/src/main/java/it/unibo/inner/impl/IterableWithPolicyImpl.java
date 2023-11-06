package it.unibo.inner.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import it.unibo.inner.api.IterableWithPolicy;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final T[] myarray;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] array) {
        this(
                array,
                new Predicate<T>() {
                    public boolean test(final T obj) {
                        return true;
                    }
                });
    }

    public IterableWithPolicyImpl(final T[] array, final Predicate<T> filter) {
        this.myarray = Arrays.copyOf(array, array.length);
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    private class ArrayIterator implements Iterator<T> {

        private int index;

        public ArrayIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            while (this.index < IterableWithPolicyImpl.this.myarray.length
                    && !IterableWithPolicyImpl.this.filter.test(IterableWithPolicyImpl.this.myarray[index])) {
                this.index++;
            }
            return this.index < IterableWithPolicyImpl.this.myarray.length;
        }

        @Override
        public T next() {
            if (index < IterableWithPolicyImpl.this.myarray.length) {
                return IterableWithPolicyImpl.this.myarray[index++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
