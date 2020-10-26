package factory.predicate;


import context.CompoundPredicateContext;
import predicate.compound.AndCompoundPredicate;
import predicate.compound.CompoundPredicate;
import predicate.compound.OrCompoundPredicate;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public class CompoundPredicateFactory {

    private final List<CompoundPredicate> predicates = Arrays.asList(
            new AndCompoundPredicate(),
            new OrCompoundPredicate()
    );

    private CompoundPredicateFactory() {

    }

    public static CompoundPredicateFactory getInstance() {
        return new CompoundPredicateFactory();
    }

    public Predicate newPredicate(CompoundPredicateContext context) {
        for (CompoundPredicate compoundPredicate : this.predicates) {
            if (compoundPredicate.support(context.getOperand())) {
                return compoundPredicate.create(context);
            }
        }

        throw new IllegalArgumentException("No predicate found with name " + context.getOperand() + "!");
    }

}
