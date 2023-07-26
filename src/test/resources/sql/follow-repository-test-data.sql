insert into users (email, password, nickname, activated, sex, is_oauth_user, created_at, last_modified_at, created_by,
                   last_modified_by)
values ('ddooby.doobob@kakaoenterprise.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin',
        'ACTIVATED', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby');
insert into users (email, password, nickname, activated, sex, is_oauth_user, created_at, last_modified_at, created_by,
                   last_modified_by)
values ('ddooby.doobob1@kakaoenterprise.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user',
        'ACTIVATED', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby');

insert into follow (follower_id, followee_id, created_at, created_by)
values ((select user_id from users where email = 'ddooby.doobob@kakaoenterprise.com'),
        (select user_id from users where email = 'ddooby.doobob1@kakaoenterprise.com'),
        now(),
        'ddooby');