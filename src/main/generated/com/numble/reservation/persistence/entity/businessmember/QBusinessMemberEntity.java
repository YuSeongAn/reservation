package com.numble.reservation.persistence.entity.businessmember;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessMemberEntity is a Querydsl query type for BusinessMemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessMemberEntity extends EntityPathBase<BusinessMemberEntity> {

    private static final long serialVersionUID = 1753114883L;

    public static final QBusinessMemberEntity businessMemberEntity = new QBusinessMemberEntity("businessMemberEntity");

    public final StringPath businessLicense = createString("businessLicense");

    public final NumberPath<Long> businessMemberId = createNumber("businessMemberId", Long.class);

    public final EnumPath<com.numble.reservation.code.BusinessMemberType> businessMemberType = createEnum("businessMemberType", com.numble.reservation.code.BusinessMemberType.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public QBusinessMemberEntity(String variable) {
        super(BusinessMemberEntity.class, forVariable(variable));
    }

    public QBusinessMemberEntity(Path<? extends BusinessMemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessMemberEntity(PathMetadata metadata) {
        super(BusinessMemberEntity.class, metadata);
    }

}

