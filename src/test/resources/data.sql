/* business member */
insert into business_member(businessMemberId, email, password, businessLicense, businessMemberType, createdAt,lastModifiedAt)
values (1, 'venue@admin.com', 'password', '111-1111-1111', 'VENUE_ADMIN', now(), now());

insert into business_member(businessMemberId, email, password, businessLicense, businessMemberType, createdAt,lastModifiedAt)
values (2, 'performance@admin.com', 'password', '222-222-2222', 'PERFORMANCE_ADMIN', now(), now());

/* member */
insert into member(memberId, email, password, createdAt, lastModifiedAt)
values (1, 'member1@member.com', 'password1', now(), now());

insert into member(memberId, email, password, createdAt, lastModifiedAt)
values (2, 'member2@member.com', 'password2', now(), now());


/* venue */
insert into venue(venueId, name, businessMemberId, venueType, runningStartedAt, runningEndedAt, createdAt, createdBy, lastModifiedAt, lastModifiedBy)
values(1, 'seoul museum', 1, 'FIXED_TYPE', '09:00:00', '20:00:00', now(), 1, now(), 1);

/* venue seat */
insert into venue_seat(seatId, venueId, seatNumber, seatType )
values(1, 1, 'A1', 'NORMAL');

insert into venue_seat(seatId, venueId, seatNumber, seatType )
values(2, 1, 'A2', 'VIP');

insert into venue_seat(seatId, venueId, seatNumber, seatType )
values(3, 1, 'A3', 'VIP');

insert into venue_seat(seatId, venueId, seatNumber, seatType )
values(4, 1, 'B1', 'NORMAL');

insert into venue_seat(seatId, venueId, seatNumber, seatType )
values(5, 1, 'B2', 'NORMAL');

insert into venue_seat(seatId, venueId, seatNumber, seatType )
values(6, 1, 'B3', 'NORMAL');

/* performance */
insert into performance(performanceId, venueId, businessMemberId, name, totalCapacity, remainCapacity, startAt, endAt, normalPrice, vipPrice, createdAt, createdBy, lastModifiedBy, lastModifiedAt)
values(1, 1, 2, 'music performance', 6, 6, '2023-08-18 10:00:00', '2023-08-18 13:00:00', 5000, 10000, now(), 2, 2, now());

insert into performance(performanceId, venueId, businessMemberId, name, totalCapacity, remainCapacity, startAt, endAt, normalPrice, vipPrice, createdAt, createdBy, lastModifiedBy, lastModifiedAt)
values(2, 1, 2, 'music performance', 6, 6, '2023-08-18 10:00:00', '2023-08-18 13:00:00', 5000, 10000, now(), 2, 2, now());


/* reservation */
insert into reservation(reservationId, memberId, performanceId, paymentMethodType, cardNumber, cardExpiredAt, cardCVC, totalPrice, normalPrice, vipPrice, createdAt, lastModifiedAt)
values(1, 1, 1, 'CREDIT_CARD', '1111-1111-1111-1111', '2027-01-01 00:00:00', '111', 15000, 5000, 10000, now(), now());

insert into reservation(reservationId, memberId, performanceId, paymentMethodType, cardNumber, cardExpiredAt, cardCVC, totalPrice, normalPrice, vipPrice, createdAt, lastModifiedAt)
values(2, 2, 1, 'CREDIT_CARD', '2222-2222-2222-2222', '2024-12-01 00:00:00', '222', 5000, 5000, 10000, now(), now());


/* reservation seat */
insert into reservation_seat(reservationSeatId, reservationId, venueSeatId, seatType)
values(1, 1, 1, 'NORMAL');

insert into reservation_seat(reservationSeatId, reservationId, venueSeatId, seatType)
values(2, 1, 2, 'VIP');

insert into reservation_seat(reservationSeatId, reservationId, venueSeatId, seatType)
values(3, 2, 5, 'NORMAL')
