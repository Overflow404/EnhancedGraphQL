package factory.type;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;
import predicate.binary.bool.IsBinaryPredicate;

import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.List;

public class BooleanBinaryTypeFactory implements BinaryFactory {
    private final List<BinaryPredicate> predicates = Collections.singletonList(
            new IsBinaryPredicate()
    );

    @Override
    public boolean support(Object aType) {
        return aType.toString().equalsIgnoreCase("TRUE") ||
                aType.toString().equalsIgnoreCase("FALSE");
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        for (BinaryPredicate binaryPredicate : predicates) {
            if (binaryPredicate.support(context.getOperand())) {
                return binaryPredicate.create(context);
            }
        }

        throw new IllegalArgumentException("No predicate found with name " + context.getOperand() + "!");
    }
}
