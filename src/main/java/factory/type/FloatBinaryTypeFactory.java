package factory.type;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;
import predicate.binary.decimal.*;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public class FloatBinaryTypeFactory implements BinaryFactory {
    private final List<BinaryPredicate> predicates = Arrays.asList(
            new EqBinaryPredicate(),
            new GtBinaryPredicate(),
            new GteBinaryPredicate(),
            new LtBinaryPredicate(),
            new LteBinaryPredicate()
    );

    @Override
    public boolean support(Object aType) {
        try {
            Float.parseFloat(aType.toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
