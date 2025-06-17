package com.drs.drs_enhanced.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-17T14:57:53", comments="EclipseLink-4.0.5.v20241223-ra96b873527f305f932543045c8679bb1de8d3a43")
@StaticMetamodel(Shelter.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Shelter_ { 

    public static volatile SingularAttribute<Shelter, Long> id;
    public static volatile SingularAttribute<Shelter, String> region;
    public static volatile SingularAttribute<Shelter, String> shelterDetail;

}