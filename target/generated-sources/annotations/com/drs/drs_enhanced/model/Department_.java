package com.drs.drs_enhanced.model;

import com.drs.drs_enhanced.model.Supply;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-15T16:43:02", comments="EclipseLink-4.0.5.v20241223-ra96b873527f305f932543045c8679bb1de8d3a43")
@StaticMetamodel(Department.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Department_ extends User_ {

    public static volatile SingularAttribute<Department, String> departmentName;
    public static volatile ListAttribute<Department, Supply> supplies;

}