package com.numble.reservation.persistence.entity.reservation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationSeatEntity is a Querydsl query type for ReservationSeatEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationSeatEntity extends EntityPathBase<ReservationSeatEntity> {

    private static final long serialVersionUID = 1725391300L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationSeatEntity reservationSeatEntity = new QReservationSeatEntity("reservationSeatEntity");

    public final QReservationEntity reservation;

    public final NumberPath<Long> reservationSeatId = createNumber("reservationSeatId", Long.class);

    public final EnumPath<com.numble.reservation.code.VenueSeatType> seatType = createEnum("seatType", com.numble.reservation.code.VenueSeatType.class);

    public final NumberPath<Long> venueSeatId = createNumber("venueSeatId", Long.class);

    public QReservationSeatEntity(String variable) {
        this(ReservationSeatEntity.class, forVariable(variable), INITS);
    }

    public QReservationSeatEntity(Path<? extends ReservationSeatEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationSeatEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationSeatEntity(PathMetadata metadata, PathInits inits) {
        this(ReservationSeatEntity.class, metadata, inits);
    }

    public QReservationSeatEntity(Class<? extends ReservationSeatEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new QReservationEntity(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

