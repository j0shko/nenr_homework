package hr.fer.zemris.nenr.fuzzy.demo;

import hr.fer.zemris.nenr.fuzzy.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.Domain;
import hr.fer.zemris.nenr.fuzzy.DomainElement;

public class DomainDemo {

    public static void main(String[] args) {
        Domain d1 = AbstractDomain.intRange(0, 5);
        d1.print("Elementi domene d1:");

        Domain d2 = AbstractDomain.intRange(0, 3);
        d2.print("Elementi domene d2:");

        Domain d3 = AbstractDomain.combine(d1, d2);
        d3.print("Elementi domene d3:");

        System.out.println(d3.elementForIndex(0));
        System.out.println(d3.elementForIndex(5));
        System.out.println(d3.elementForIndex(14));
        System.out.println(d3.indexOfElement(DomainElement.of(4, 1)));
    }
}
