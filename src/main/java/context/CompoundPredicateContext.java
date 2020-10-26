package context;

import lombok.Builder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Builder(toBuilder = true)
public class CompoundPredicateContext {
    private final String operand;

    private final List<Predicate> predicates;
    private final CriteriaBuilder criteriaBuilder;

    public List<Predicate> getPredicates() {
        return new ArrayList<>(predicates);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public String getOperand() {
        return operand;
    }
}
