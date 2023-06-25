package com.numble.reservation.persistence.entity.reservation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationEntity is a Querydsl query type for ReservationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationEntity extends EntityPathBase<ReservationEntity> {

    private static final long serialVersionUID = 1816206687L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationEntity reservationEntity = new QReservationEntity("reservationEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<java.math.BigDecimal> normalPrice = createNumber("normalPrice", java.math.BigDecimal.class);

    public final com.numble.reservation.persistence.entity.common.QPaymentInfoVo paymentInfoVo;

    public final NumberPath<Long> performanceId = createNumber("performanceId", Long.class);

    public final NumberPath<Long> reservationId = createNumber("reservationId", Long.class);

    public final ListPath<ReservationSeatEntity, QReservationSeatEntity> reservationSeats = this.<ReservationSeatEntity, QReservationSeatEntity>createList("reservationSeats", ReservationSeatEntity.class, QReservationSeatEntity.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> totalPrice = createNumber("totalPrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> vipPrice = createNumber("vipPrice", java.math.BigDecimal.class);

    public QReservationEntity(String variable) {
        this(ReservationEntity.class, forVariable(variable), INITS);
    }

    public QReservationEntity(Path<? extends ReservationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationEntity(PathMetadata metadata, PathInits inits) {
        this(ReservationEntity.class, metadata, inits);
    }

    public QReservationEntity(Class<? extends ReservationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.paymentInfoVo = inits.isInitialized("paymentInfoVo") ? new com.numble.reservation.persistence.entity.common.QPaymentInfoVo(forProperty("paymentInfoVo")) : null;
    }

}

