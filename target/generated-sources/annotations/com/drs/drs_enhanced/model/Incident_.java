package com.drs.drs_enhanced.model;

import com.drs.drs_enhanced.model.Department;
import com.drs.drs_enhanced.model.User;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-16T14:23:07", comments="EclipseLink-4.0.5.v20241223-ra96b873527f305f932543045c8679bb1de8d3a43")
@StaticMetamodel(Incident.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Incident_ { 

    public static volatile SingularAttribute<Incident, Integer> priorityLevel;
    public static volatile SingularAttribute<Incident, String> incidentType;
    public static volatile SingularAttribute<Incident, String> description;
    public static volatile SingularAttribute<Incident, Long> id;
    public static volatile SingularAttribute<Incident, User> user;
    public static volatile SingularAttribute<Incident, Department> assignedDepartment;

}