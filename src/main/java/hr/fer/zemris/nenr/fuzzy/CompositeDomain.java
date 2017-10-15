package hr.fer.zemris.nenr.fuzzy;

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
    public Iterator<DomainElement> iterator() {
        int compCount = getNumberOfComponents();
        int[] valuesFirst = new int[compCount];
        int[] valuesLast = new int[compCount];

        for (int i = 0; i < compCount; i++) {
            valuesFirst[i] = components[i].getFirst();
            valuesLast[i] = components[i].getLast();
        }

        return new Iterator<>() {
            int[] next = valuesFirst;

            @Override
            public boolean hasNext() {
                return !Arrays.equals(next, valuesLast);
            }

            @Override
            public DomainElement next() {
                DomainElement element = new DomainElement(next);

                for (int i = compCount; i >= 0; i--) {
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
