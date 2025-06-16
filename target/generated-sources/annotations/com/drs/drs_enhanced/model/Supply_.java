package com.drs.drs_enhanced.model;

import com.drs.drs_enhanced.model.Department;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-16T14:55:25", comments="EclipseLink-4.0.5.v20241223-ra96b873527f305f932543045c8679bb1de8d3a43")
@StaticMetamodel(Supply.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Supply_ { 

    public static volatile SingularAttribute<Supply, Integer> quantity;
    public static volatile SingularAttribute<Supply, String> name;
    public static volatile SingularAttribute<Supply, Long> id;
    public static volatile ListAttribute<Supply, Department> departments;

}