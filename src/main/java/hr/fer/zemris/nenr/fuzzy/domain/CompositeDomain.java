package hr.fer.zemris.nenr.fuzzy.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
        int compCount = getNumberOfComponents();
        int[] valuesFirst = new int[compCount];
        int[] valuesLast = new int[compCount];

        for (int i = 0; i < compCount; i++) {
            valuesFirst[i] = components[i].getFirst();
            valuesLast[i] = components[i].getLast() - 1;
        }


        return new Iterator<>() { //java 9 feature yay
            int[] next = valuesFirst;
            boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public DomainElement next() {
                DomainElement element = new DomainElement(next);
                hasNext = !Arrays.equals(next, valuesLast);
                if (!hasNext) return element;

                for (int i = compCount - 1; i >= 0; i--) {
                    if (next[i] + 1 >= components[i].getLast()) {
                        next[i] = components[i].getFirst();
                    } else {
                        next[i]++;
                        return element;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }
}
