package com.numble.reservation.persistence.entity.venue;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVenueSeatEntity is a Querydsl query type for VenueSeatEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVenueSeatEntity extends EntityPathBase<VenueSeatEntity> {

    private static final long serialVersionUID = 487590602L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVenueSeatEntity venueSeatEntity = new QVenueSeatEntity("venueSeatEntity");

    public final NumberPath<Long> seatId = createNumber("seatId", Long.class);

    public final StringPath seatNumber = createString("seatNumber");

    public final EnumPath<com.numble.reservation.code.VenueSeatType> seatType = createEnum("seatType", com.numble.reservation.code.VenueSeatType.class);

    public final QVenueEntity venue;

    public QVenueSeatEntity(String variable) {
        this(VenueSeatEntity.class, forVariable(variable), INITS);
    }

    public QVenueSeatEntity(Path<? extends VenueSeatEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVenueSeatEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVenueSeatEntity(PathMetadata metadata, PathInits inits) {
        this(VenueSeatEntity.class, metadata, inits);
    }

    public QVenueSeatEntity(Class<? extends VenueSeatEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.venue = inits.isInitialized("venue") ? new QVenueEntity(forProperty("venue")) : null;
    }

}

