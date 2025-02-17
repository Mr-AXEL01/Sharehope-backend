package net.axel.sharehope.validation.validator;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.axel.sharehope.validation.Exists;
import org.springframework.stereotype.Component;

@Component
public class ExistsValidator implements ConstraintValidator<Exists, Object> {

    private final EntityManager entityManager;
    private Class<?> entityClass;
    private String fieldName;

    public ExistsValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void initialize(Exists constraintAnnotation) {
        entityClass = constraintAnnotation.entityClass();
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext Context) {
        if (value == null) return true;

        final String queryStr = String.format(
                "SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                entityClass.getSimpleName(), fieldName
        );

        Long count = entityManager.createQuery(queryStr, Long.class)
                .setParameter("value", value)
                .getSingleResult();

        return count > 0;
    }
}
