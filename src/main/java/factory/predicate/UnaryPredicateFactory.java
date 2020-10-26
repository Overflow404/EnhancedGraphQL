package factory.predicate;

import context.UnaryPredicateContext;
import predicate.unary.NotUnaryPredicate;
import predicate.unary.UnaryPredicate;

import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.List;

public class UnaryPredicateFactory {

    private final List<UnaryPredicate> predicates = Collections.singletonList(
            new NotUnaryPredicate()
    );

    private UnaryPredicateFactory() {

    }

    public static UnaryPredicateFactory getInstance() {
        return new UnaryPredicateFactory();
    }

    public Predicate newPredicate(UnaryPredicateContext context) {
        for (UnaryPredicate unaryPredicate : predicates) {
            if (unaryPredicate.support(context.getOperand())) {
                return unaryPredicate.create(context);
            }
        }

        throw new IllegalArgumentException("No predicate found with name " + context.getOperand() + "!");
    }
}
