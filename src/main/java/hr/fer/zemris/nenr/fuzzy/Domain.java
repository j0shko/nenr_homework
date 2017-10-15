package hr.fer.zemris.nenr.fuzzy;

public interface Domain extends Iterable<DomainElement> {
    int getCardinality();

    Domain getComponent(int componentIndex);

    int getNumberOfComponents();

    int indexOfElement(DomainElement element);

    DomainElement elementForIndex(int index);
}
