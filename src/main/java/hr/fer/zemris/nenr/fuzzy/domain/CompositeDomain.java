package hr.fer.zemris.nenr.fuzzy.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CompositeDomain extends AbstractDomain {

    private SimpleDomain[] components;

    public CompositeDomain(SimpleDomain[] components) {
        this.components = components;
    }

    @Override
    public int getCardinality() {
        return Arrays.stream(components).map(SimpleDomain::getCardinality).reduce(1, (prod, x) -> x * prod);
    }

    @Override
    public Domain getComponent(int componentIndex) {
        return components[componentIndex];
    }

    @Override
    public int getNumberOfComponents() {
        return components.length;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        int[] values = new int[components.length];
        int j = index;
        for (int i = components.length - 1; i > 0; i--) {
            int currentComponentIndex = j % components[i].getCardinality();
            values[i] = components[i].elementForIndex(currentComponentIndex).getComponentValue(0);
            j = (j - currentComponentIndex) / components[i].getCardinality();
        }
        values[0] = components[0].elementForIndex(j).getComponentValue(0);
        return new DomainElement(values);
    }

    @Override
    public int indexOfElement(DomainElement element) {
        int index = 0;
        for (int i = 0; i < components.length - 1; i++) {
            index += components[i].indexOfElement(DomainElement.of(element.getComponentValue(i)));
            index *= components[i + 1].getCardinality();
        }
        index += components[components.length - 1].indexOfElement(
                DomainElement.of(element.getComponentValue(element.getNumberOfComponents() - 1))
        );
        return index;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new CompositeIterator();
    }

    private class CompositeIterator implements Iterator<DomainElement> {

        private List<Iterator<DomainElement>> iterators;
        private boolean init = false;
        private int[] last;

        CompositeIterator() {
            iterators = Arrays.stream(components).map(SimpleDomain::iterator).collect(Collectors.toList());
        }

        @Override
        public boolean hasNext() {
            return iterators.stream().map(Iterator::hasNext).reduce(false, (acc, it) -> acc || it);
        }

        @Override
        public DomainElement next() {
            if (!init) {
                init = true;
                last = new int[components.length];
                for (int i = 0; i < components.length; i++) {
                    last[i] = iterators.get(i).next().getComponentValue(0);
                }
                return new DomainElement(last);
            }

            for (int i = iterators.size() - 1; i >= 0; i--) {
                Iterator<DomainElement> iterator = iterators.get(i);
                if (!iterators.get(i).hasNext()) {
                    Iterator<DomainElement> newIterator = components[i].iterator();
                    iterators.set(i, newIterator);
                    last[i] = newIterator.next().getComponentValue(0);
                } else {
                    last[i] = iterator.next().getComponentValue(0);
                    return new DomainElement(last);
                }
            }
            throw new NoSuchElementException();
        }
    }
}
