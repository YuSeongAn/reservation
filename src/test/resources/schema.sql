CREATE TABLE IF NOT EXISTS member
(
    memberId       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email          VARCHAR(256) NOT NULL,
    password       VARCHAR(73)  NOT NULL,
    createdAt      DATETIME     NOT NULL,
    lastModifiedAt DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS business_member
(
    businessMemberId   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email              VARCHAR(256) NOT NULL,
    password           VARCHAR(73)  NOT NULL,
    businessLicense    VARCHAR(45)  NOT NULL,
    businessMemberType VARCHAR(64)  NOT NULL,
    createdAt          DATETIME     NOT NULL,
    lastModifiedAt     DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS venue
(
    venueId          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(128) NOT NULL,
    businessMemberId BIGINT       NOT NULL,
    venueType        VARCHAR(56)  NOT NULL,
    runningStartedAt TIME         NOT NULL,
    runningEndedAt   TIME         NOT NULL,
    createdAt        DATETIME     NOT NULL,
    createdBy        BIGINT       NOT NULL,
    lastModifiedAt   DATETIME     NOT NULL,
    lastModifiedBy   BIGINT       NOT NULL,
    FOREIGN KEY (businessMemberId) REFERENCES business_member (businessMemberId)
);

CREATE TABLE IF NOT EXISTS performance
(
    performanceId    BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    venueId          BIGINT         NOT NULL,
    businessMemberId BIGINT         NOT NULL,
    name             VARCHAR(128)   NOT NULL,
    totalCapacity    INT            NOT NULL,
    remainCapacity   INT            NOT NULL,
    startAt          DATETIME       NOT NULL,
    endAt            DATETIME       NOT NULL,
    normalPrice      DECIMAL(12, 0) NOT NULL,
    vipPrice         DECIMAL(12, 0) NOT NULL,
    createdAt        DATETIME       NOT NULL,
    createdBy        BIGINT         NOT NULL,
    lastModifiedBy   BIGINT         NOT NULL,
    lastModifiedAt   DATETIME       NOT NULL,
    FOREIGN KEY (`venueId`) REFERENCES venue (venueId),
    FOREIGN KEY (`businessMemberId`) REFERENCES business_member (businessMemberId)
);

CREATE TABLE IF NOT EXISTS reservation
(
    reservationId     BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    memberId          BIGINT         NOT NULL,
    performanceId     BIGINT         NOT NULL,
    paymentMethodType VARCHAR(64)    NOT NULL,
    cardNumber        VARCHAR(64)    NOT NULL,
    cardExpiredAt     DATETIME       NOT NULL,
    cardCVC           VARCHAR(16)    NOT NULL,
    totalPrice        DECIMAL(12, 0) NOT NULL,
    normalPrice       DECIMAL(12, 0) NOT NULL,
    vipPrice          DECIMAL(12, 0) NOT NULL,
    createdAt         DATETIME       NOT NULL,
    lastModifiedAt    DATETIME       NOT NULL,
    FOREIGN KEY (memberId) REFERENCES member (memberId),
    FOREIGN KEY (performanceId) REFERENCES performance (performanceId)
);

CREATE TABLE IF NOT EXISTS venue_seat
(
    seatId     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    venueId    BIGINT      NOT NULL,
    seatNumber VARCHAR(26) NOT NULL,
    seatType   VARCHAR(26) NOT NULL,
    FOREIGN KEY (`venueId`) REFERENCES venue (venueId)
);

CREATE TABLE IF NOT EXISTS reservation_seat
(
    reservationSeatId BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reservationId     BIGINT      NOT NULL,
    venueSeatId       BIGINT      NOT NULL,
    seatType          VARCHAR(26) NOT NULL,
    FOREIGN KEY (reservationId) REFERENCES reservation (reservationId),
    FOREIGN KEY (venueSeatId) REFERENCES venue_seat (seatId)
);
