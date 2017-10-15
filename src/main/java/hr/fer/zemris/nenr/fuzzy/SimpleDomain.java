package hr.fer.zemris.nenr.fuzzy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleDomain extends AbstractDomain {

    private int first;
    private int last;

    public SimpleDomain(int first, int last) {
        this.first = first;
        this.last = last;
    }

    public int getCardinality() {
        return last - first;
    }

    public Domain getComponent(int componentIndex) {
        return this;
    }

    public int getNumberOfComponents() {
        return 1;
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {
            int next = first;

            public boolean hasNext() {
                return next < last;
            }

            public DomainElement next() {
                if (next >= last) throw new NoSuchElementException();
                DomainElement element = DomainElement.of(next);
                next++;
                return element;
            }
        };
    }
}
