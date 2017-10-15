package hr.fer.zemris.nenr.fuzzy;

import java.util.NoSuchElementException;

public abstract class AbstractDomain implements Domain {

    public int indexOfElement(DomainElement element) {
        int i = 0;
        for (DomainElement domainElement : this) {
            if (domainElement.equals(element)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException();
    }

    public DomainElement elementForIndex(int index) {
        int i = 0;
        for (DomainElement element : this) {
            if (i == index) {
                return element;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    // static methods

    public Domain intRange(int first, int last) {
        return null;
    }

    public Domain combine(Domain first, Domain second) {
        return null;
    }
}
