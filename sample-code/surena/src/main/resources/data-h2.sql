-- insert into "user" (id,first_name,last_name, username, password) values (1,'nilu123','niluueeee','bita','bitasddd');
-- insert into user (id,first_name,last_name, username, password) values (2,'nilu321','niloooeee','bittta','bitahhhh');

 create table user_tbl (
       id varchar(36) not null,
        create_date timestamp,
        modified_date timestamp,
        external_id varchar(36) not null,
        first_name varchar(36) not null,
        last_name varchar(36) not null,
        password varchar(36) not null,
        username varchar(36) not null,
        primary key (id)
    )
     create index idx_bufn on user_tbl (first_name)
     create index idx_buln on user_tbl (last_name)
     create index idx_buu on user_tbl (username)


    alter table user_tbl
       add constraint UK_l6vubdu2loq0e13fdpow3tg7f unique (password)


    alter table user_tbl
       add constraint UK_xkjl2orevvtyrqqshcot355j unique (username)

-- INSERT INTO user_tbl (id,external_id,first_name,last_name, username, password) VALUES (1,'sdkfjsldkjsldjslkjdsljdksjdlkjsdkjsldkjf','nilu123','niluueeee','bitkjhgfdgh5656a','bitasddd');
