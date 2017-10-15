package hr.fer.zemris.nenr.fuzzy;

import java.util.LinkedList;
import java.util.List;
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
        return new SimpleDomain(first, last);
    }

    public Domain combine(Domain first, Domain second) {
        List<SimpleDomain> domains = new LinkedList<>();
        for (int i = 0, end = first.getNumberOfComponents(); i < end; i++) {
            domains.add((SimpleDomain) first.getComponent(i));
        }
        for (int i = 0, end = second.getNumberOfComponents(); i < end; i++) {
            domains.add((SimpleDomain) second.getComponent(i));
        }
        return new CompositeDomain((SimpleDomain[]) domains.toArray());
    }
}
