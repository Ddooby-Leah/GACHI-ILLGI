-- insert into users (username, password, nickname, activated) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
-- insert into users (username, password, nickname, activated) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);

insert into authority (authority_name, created_at, last_modified_at, created_by, last_modified_by) values ('ROLE_GUEST', NOW(), NOW(), 'ddooby', 'ddooby');
insert into authority (authority_name, created_at, last_modified_at, created_by, last_modified_by) values ('ROLE_USER', NOW(), NOW(), 'ddooby', 'ddooby');
insert into authority (authority_name, created_at, last_modified_at, created_by, last_modified_by) values ('ROLE_ADMIN', NOW(), NOW(), 'ddooby', 'ddooby');

-- insert into user_authority (user_id, authority_name) values (1, 'ROLE_USER');
-- insert into user_authority (user_id, authority_name) values (1, 'ROLE_ADMIN');
-- insert into user_authority (user_id, authority_name) values (2, 'ROLE_USER');