package specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

@FunctionalInterface
public interface PathSpecification<T> {

    default Specification<T> atRoot() {
        return this::toPredicate;
    }

    default <S> Specification<S> atPath(final SetAttribute<S, T> pathAttribute) {
        return (root, query, cb) -> toPredicate(root.join(pathAttribute), query, cb);
    }

    default <S> Specification<S> atPath(final SingularAttribute<S, T> singularAttribute) {
        return (root, query, cb) -> toPredicate(root.join(singularAttribute), query, cb);
    }

    default <S> Specification<S> atPath(final ListAttribute<S, T> listAttribute) {
        return (root, query, cb) -> toPredicate(root.join(listAttribute), query, cb);
    }

    @Nullable
    Predicate toPredicate(Path<T> path, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
