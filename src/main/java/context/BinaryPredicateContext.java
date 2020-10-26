package context;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

@Getter
@Builder(toBuilder = true)
public class BinaryPredicateContext {
    private final String operand;
    private final String leftOperand;
    private final Object rightOperand;

    private final CriteriaBuilder criteriaBuilder;
    private final Path<?> path;
}
