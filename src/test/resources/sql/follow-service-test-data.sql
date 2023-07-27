insert into users (email, password, nickname, activated, sex, is_oauth_user, created_at, last_modified_at, created_by,
                   last_modified_by)
values ('ddooby.doobob@kakaoenterprise.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin',
        'ACTIVATED', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('ddooby.doobob1@kakaoenterprise.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user',
        'ACTIVATED', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('followee11@abc.com', '$2a$08$lDnHPz7eUkasdSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'sss', 'ACTIVATED',
        '남자',
        false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('follower11@abc.com', '$2a$08$UkVvwpULis18S19S5padsZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ddd', 'ACTIVATED',
        '남자',
        false, NOW(), NOW(), 'ddooby', 'ddooby');

insert into follow (follower_id, followee_id, created_at, created_by)
values ((select user_id from users where email = 'follower11@abc.com'),
        (select user_id from users where email = 'followee11@abc.com'), now(), 'ddooby'),
       ((select user_id from users where email = 'ddooby.doobob1@kakaoenterprise.com'),
        (select user_id from users where email = 'followee11@abc.com'), now(), 'ddooby')
;
