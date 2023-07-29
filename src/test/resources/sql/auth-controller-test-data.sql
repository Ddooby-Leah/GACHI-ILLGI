insert into users (email, password, nickname, activated, sex, is_oauth_user, created_at, last_modified_at, created_by,
                   last_modified_by)
values ('ddooby1@kakaoenterprise.com', '$2a$12$px/zH2/5EHDKlO7.Y012ReHnpfhEcfSf6cLoi.rkYBIkia.CU66DG', 'ddooby',
        'PENDING', '남자', true, NOW(), NOW(), 'ddooby', 'ddooby'),
       ('ddooby2@kakaoenterprise.com', '$2a$12$px/zH2/5EHDKlO7.Y012ReHnpfhEcfSf6cLoi.rkYBIkia.CU66DG', 'ddooby',
        'ACTIVATED', '남자', true, NOW(), NOW(), 'ddooby', 'ddooby')
;

