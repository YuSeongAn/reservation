package com.numble.reservation.persistence.entity.performance;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerformanceEntity is a Querydsl query type for PerformanceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerformanceEntity extends EntityPathBase<PerformanceEntity> {

    private static final long serialVersionUID = 213256743L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerformanceEntity performanceEntity = new QPerformanceEntity("performanceEntity");

    public final NumberPath<Long> businessMemberId = createNumber("businessMemberId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> endAt = createDateTime("endAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> lastModifiedBy = createNumber("lastModifiedBy", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<java.math.BigDecimal> normalPrice = createNumber("normalPrice", java.math.BigDecimal.class);

    public final NumberPath<Long> performanceId = createNumber("performanceId", Long.class);

    public final NumberPath<Integer> remainCapacity = createNumber("remainCapacity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> startAt = createDateTime("startAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> totalCapacity = createNumber("totalCapacity", Integer.class);

    public final com.numble.reservation.persistence.entity.venue.QVenueEntity venue;

    public final NumberPath<java.math.BigDecimal> vipPrice = createNumber("vipPrice", java.math.BigDecimal.class);

    public QPerformanceEntity(String variable) {
        this(PerformanceEntity.class, forVariable(variable), INITS);
    }

    public QPerformanceEntity(Path<? extends PerformanceEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPerformanceEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPerformanceEntity(PathMetadata metadata, PathInits inits) {
        this(PerformanceEntity.class, metadata, inits);
    }

    public QPerformanceEntity(Class<? extends PerformanceEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.venue = inits.isInitialized("venue") ? new com.numble.reservation.persistence.entity.venue.QVenueEntity(forProperty("venue")) : null;
    }

}

