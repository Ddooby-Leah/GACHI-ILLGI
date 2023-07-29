insert into users (email, password, nickname, activated, sex, is_oauth_user, created_at, last_modified_at, created_by,
                   last_modified_by)
values ('ddooby.doobob@kakaoenterprise.com', '$2a$12$px/zH2/5EHDKlO7.Y012ReHnpfhEcfSf6cLoi.rkYBIkia.CU66DG', 'admin',
        'ACTIVATED', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('ddooby.doobob1@kakaoenterprise.com', '$2a$12$px/zH2/5EHDKlO7.Y012ReHnpfhEcfSf6cLoi.rkYBIkia.CU66DG', 'user',
        'ACTIVATED', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('followee11@abc.com', '$2a$12$px/zH2/5EHDKlO7.Y012ReHnpfhEcfSf6cLoi.rkYBIkia.CU66DG', 'sss', 'ACTIVATED',
        '남자',
        false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('follower11@abc.com', '$2a$12$px/zH2/5EHDKlO7.Y012ReHnpfhEcfSf6cLoi.rkYBIkia.CU66DG', 'ddd', 'ACTIVATED',
        '남자',
        false, NOW(), NOW(), 'ddooby', 'ddooby');

insert into follow (follower_id, followee_id, created_at, created_by)
values ((select user_id from users where email = 'follower11@abc.com'),
        (select user_id from users where email = 'followee11@abc.com'), now(), 'ddooby'),
       ((select user_id from users where email = 'ddooby.doobob1@kakaoenterprise.com'),
        (select user_id from users where email = 'followee11@abc.com'), now(), 'ddooby')
;

insert into user_authority(user_id, authority_id, created_at, created_by)
values ((select user_id from users where email = 'ddooby.doobob@kakaoenterprise.com'), 2, now(), 'ddooby'),
       ((select user_id from users where email = 'ddooby.doobob@kakaoenterprise.com'), 3, now(), 'ddooby');
