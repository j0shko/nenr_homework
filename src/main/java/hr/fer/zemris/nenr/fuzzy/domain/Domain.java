package hr.fer.zemris.nenr.fuzzy.domain;

public interface Domain extends Iterable<DomainElement> {
    int getCardinality();

    Domain getComponent(int componentIndex);

    int getNumberOfComponents();

    int indexOfElement(DomainElement element);

    DomainElement elementForIndex(int index);

    default void print(String headingText) {
        if (headingText != null) {
            System.out.println(headingText);
        }

        for (DomainElement e : this) {
            System.out.println("Element domene: " + e);
        }

        System.out.println("Kardinalitet domene: " + getCardinality());
        System.out.println();
    }
}
