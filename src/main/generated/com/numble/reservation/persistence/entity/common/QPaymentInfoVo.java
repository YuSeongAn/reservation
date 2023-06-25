package com.numble.reservation.persistence.entity.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentInfoVo is a Querydsl query type for PaymentInfoVo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPaymentInfoVo extends BeanPath<PaymentInfoVo> {

    private static final long serialVersionUID = -1191277000L;

    public static final QPaymentInfoVo paymentInfoVo = new QPaymentInfoVo("paymentInfoVo");

    public final StringPath cardCVC = createString("cardCVC");

    public final DateTimePath<java.time.LocalDateTime> cardExpiredAt = createDateTime("cardExpiredAt", java.time.LocalDateTime.class);

    public final StringPath cardNumber = createString("cardNumber");

    public final EnumPath<com.numble.reservation.code.PaymentMethodType> paymentMethodType = createEnum("paymentMethodType", com.numble.reservation.code.PaymentMethodType.class);

    public QPaymentInfoVo(String variable) {
        super(PaymentInfoVo.class, forVariable(variable));
    }

    public QPaymentInfoVo(Path<? extends PaymentInfoVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentInfoVo(PathMetadata metadata) {
        super(PaymentInfoVo.class, metadata);
    }

}

