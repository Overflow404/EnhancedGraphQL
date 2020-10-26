package context;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

@Getter
@Builder(toBuilder = true)
public class UnaryPredicateContext {
    private final String operand;

    private final Predicate predicate;
    private final CriteriaBuilder criteriaBuilder;
}
