package com.numble.reservation.persistence.entity.venue;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVenueEntity is a Querydsl query type for VenueEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVenueEntity extends EntityPathBase<VenueEntity> {

    private static final long serialVersionUID = 278083941L;

    public static final QVenueEntity venueEntity = new QVenueEntity("venueEntity");

    public final NumberPath<Long> businessMemberId = createNumber("businessMemberId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> lastModifiedBy = createNumber("lastModifiedBy", Long.class);

    public final StringPath name = createString("name");

    public final TimePath<java.time.LocalTime> runningEndedAt = createTime("runningEndedAt", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> runningStartedAt = createTime("runningStartedAt", java.time.LocalTime.class);

    public final NumberPath<Long> venueId = createNumber("venueId", Long.class);

    public final ListPath<VenueSeatEntity, QVenueSeatEntity> venueSeats = this.<VenueSeatEntity, QVenueSeatEntity>createList("venueSeats", VenueSeatEntity.class, QVenueSeatEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.numble.reservation.code.VenueType> venueType = createEnum("venueType", com.numble.reservation.code.VenueType.class);

    public QVenueEntity(String variable) {
        super(VenueEntity.class, forVariable(variable));
    }

    public QVenueEntity(Path<? extends VenueEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVenueEntity(PathMetadata metadata) {
        super(VenueEntity.class, metadata);
    }

}

