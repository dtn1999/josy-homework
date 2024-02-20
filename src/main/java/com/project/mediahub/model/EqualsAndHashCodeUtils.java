package com.project.mediahub.model;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * This class provides utility methods for the equals and hashCode of entity classes.
 */
@UtilityClass
public class EqualsAndHashCodeUtils {

    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> boolean checkEquality(T ref, Object object) {
        if (ref == object) return true;
        if (object == null) return false;
        T other = (T) object;
        return ref.getId() != null && Objects.equals(ref.getId(), other.getId());
    }

    public <T extends BaseEntity> int getHashCode(T ref) {
        return ref instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : ref.getClass().hashCode();
    }

}
