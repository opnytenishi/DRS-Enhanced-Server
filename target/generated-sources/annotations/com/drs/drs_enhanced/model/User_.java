package com.drs.drs_enhanced.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-17T14:57:53", comments="EclipseLink-4.0.5.v20241223-ra96b873527f305f932543045c8679bb1de8d3a43")
@StaticMetamodel(User.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class User_ { 

    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> region;
    public static volatile SingularAttribute<User, Long> userId;
    public static volatile SingularAttribute<User, String> email;

}