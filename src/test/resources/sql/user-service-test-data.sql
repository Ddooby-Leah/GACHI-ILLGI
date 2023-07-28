insert into users (email, password, nickname, activated, sex, is_oauth_user, created_at, last_modified_at, created_by,
                   last_modified_by)
values ('ddooby@kakaoenterprise.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin',
        'PENDING', '남자', false, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('ddooby1@kakaoenterprise.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'ddooby',
        'PENDING', '남자', true, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('ddooby2@kakaoenterprise.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'ddooby',
        'ACTIVATED', '남자', true, NOW(), NOW(), 'ddooby', 'ddooby')
;