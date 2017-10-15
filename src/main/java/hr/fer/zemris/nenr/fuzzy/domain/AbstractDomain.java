package hr.fer.zemris.nenr.fuzzy.domain;

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
            i++;
        }
        throw new IndexOutOfBoundsException();
    }

    // static methods

    public static Domain intRange(int first, int last) {
        return new SimpleDomain(first, last);
    }

    public static Domain combine(Domain first, Domain second) {
        SimpleDomain[] domains = new SimpleDomain[first.getNumberOfComponents() + second.getNumberOfComponents()];
        int j = 0;
        for (int i = 0, end = first.getNumberOfComponents(); i < end; i++) {
            domains[j] = (SimpleDomain) first.getComponent(i);
            j++;
        }
        for (int i = 0, end = second.getNumberOfComponents(); i < end; i++) {
            domains[j] = (SimpleDomain) second.getComponent(i);
            j++;
        }
        return new CompositeDomain(domains);
    }
}
