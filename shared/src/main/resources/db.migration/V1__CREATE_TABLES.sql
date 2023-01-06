CREATE TABLE admin
(
    id              VARCHAR(255) NOT NULL,
    email           VARCHAR(255),
    phone           VARCHAR(255),
    full_name       VARCHAR(255),
    gender          VARCHAR(255) NOT NULL,
    file_id         VARCHAR(255),
    birth_date      date,
    activity_status VARCHAR(255) NOT NULL,
    enable_status   VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (id)
);

CREATE TABLE admin_authority
(
    id        VARCHAR(255) NOT NULL,
    authority VARCHAR(255) NOT NULL,
    admin_id  VARCHAR(255),
    CONSTRAINT pk_admin_authority PRIMARY KEY (id)
);

CREATE TABLE analytics_counter
(
    id                     VARCHAR(50) NOT NULL,
    supplier_logins        INTEGER     NOT NULL,
    client_logins          INTEGER     NOT NULL,
    total_logins           INTEGER     NOT NULL,
    suppliers_created      INTEGER     NOT NULL,
    clients_created        INTEGER     NOT NULL,
    total_created          INTEGER     NOT NULL,
    transactions_completed INTEGER     NOT NULL,
    created_at             date        NOT NULL,
    CONSTRAINT pk_analytics_counter PRIMARY KEY (id)
);

CREATE TABLE appointment
(
    id                   VARCHAR(50)  NOT NULL,
    supplier_id          VARCHAR(255) NOT NULL,
    client_id            VARCHAR(255) NOT NULL,
    reservation_date     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    old_reservation_date TIMESTAMP WITHOUT TIME ZONE,
    type                 VARCHAR(255) NOT NULL,
    description          TEXT         NOT NULL,
    status               VARCHAR(255) NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    payment              INTEGER,
    receipt_id           VARCHAR(255),
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

CREATE TABLE article
(
    id            VARCHAR(50)  NOT NULL,
    title         VARCHAR(255) NOT NULL,
    priority      INTEGER      NOT NULL,
    description   TEXT         NOT NULL,
    profession_id VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_article PRIMARY KEY (id)
);

CREATE TABLE article_files
(
    article_id VARCHAR(50)  NOT NULL,
    files_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_articlefiles PRIMARY KEY (article_id, files_id)
);

CREATE TABLE bank_account
(
    id              VARCHAR(255) NOT NULL,
    card_number     VARCHAR(255),
    company_name    VARCHAR(255),
    company_address VARCHAR(255),
    bin             VARCHAR(255),
    kbe             VARCHAR(255),
    bik             VARCHAR(255),
    iik             VARCHAR(255),
    CONSTRAINT pk_bank_account PRIMARY KEY (id)
);

CREATE TABLE bundle
(
    id             VARCHAR(50)  NOT NULL,
    title          VARCHAR(255) NOT NULL,
    first_benefit  INTEGER      NOT NULL,
    second_benefit INTEGER      NOT NULL,
    price          FLOAT        NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_bundle PRIMARY KEY (id)
);

CREATE TABLE calendar
(
    id                 VARCHAR(255) NOT NULL,
    first_day_of_month date,
    working_days       DATE[],
    supplier_id        VARCHAR(255) NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_calendar PRIMARY KEY (id)
);

CREATE TABLE certificate
(
    id             VARCHAR(255) NOT NULL,
    title          VARCHAR(255),
    issue_date     date,
    specialization VARCHAR(255),
    file_id        VARCHAR(255),
    supplier_id    VARCHAR(255),
    CONSTRAINT pk_certificate PRIMARY KEY (id)
);

CREATE TABLE chat
(
    id                             VARCHAR(255) NOT NULL,
    client_id                      VARCHAR(255),
    supplier_id                    VARCHAR(255),
    archive_status                 VARCHAR(255) NOT NULL,
    client_unread_messages_count   INTEGER      NOT NULL,
    supplier_unread_messages_count INTEGER      NOT NULL,
    created_at                     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at                     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chat PRIMARY KEY (id)
);

CREATE TABLE chat_support
(
    id          VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255),
    client_id   VARCHAR(255),
    authority   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chat_support PRIMARY KEY (id)
);

CREATE TABLE client
(
    id                 VARCHAR(255) NOT NULL,
    email              VARCHAR(255),
    phone              VARCHAR(255),
    full_name          VARCHAR(255),
    gender             VARCHAR(255) NOT NULL,
    birth_date         date,
    avatar             VARCHAR(255),
    session_count      INTEGER      NOT NULL,
    expenses           FLOAT        NOT NULL,
    activity_status    VARCHAR(255) NOT NULL,
    enable_status      VARCHAR(255) NOT NULL,
    file_id            VARCHAR(255),
    os_type            VARCHAR(255),
    notify             BOOLEAN      NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    registration_token VARCHAR(255),
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE education
(
    id               VARCHAR(255) NOT NULL,
    institution_name VARCHAR(255),
    year             INTEGER,
    faculty          VARCHAR(255),
    file_id          VARCHAR(255),
    supplier_id      VARCHAR(255),
    CONSTRAINT pk_education PRIMARY KEY (id)
);

CREATE TABLE faq
(
    id          VARCHAR(50)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    authority   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_faq PRIMARY KEY (id)
);

CREATE TABLE favorite_supplier
(
    id                   VARCHAR(255) NOT NULL,
    favorite_supplier_id VARCHAR(255),
    client_id            VARCHAR(255),
    CONSTRAINT pk_favorite_supplier PRIMARY KEY (id)
);

CREATE TABLE feedback
(
    id          VARCHAR(255) NOT NULL,
    grade       FLOAT        NOT NULL,
    description VARCHAR(255),
    status      VARCHAR(255) NOT NULL,
    client_id   VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_feedback PRIMARY KEY (id)
);

CREATE TABLE file
(
    id         VARCHAR(255) NOT NULL,
    directory  VARCHAR(255) NOT NULL,
    format     VARCHAR(255) NOT NULL,
    url        VARCHAR(255) NOT NULL,
    priority   INTEGER      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_file PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id          VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255),
    client_id   VARCHAR(255),
    chat_id     VARCHAR(255),
    content     VARCHAR(255),
    status      VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

CREATE TABLE notification
(
    id                   VARCHAR(255) NOT NULL,
    senders_name         VARCHAR(255) NOT NULL,
    recipient_type       VARCHAR(255) NOT NULL,
    status               VARCHAR(255) NOT NULL,
    appointment_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE passport_photo
(
    passport_files_id VARCHAR(255) NOT NULL,
    supplier_id       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_passport_photo PRIMARY KEY (passport_files_id, supplier_id)
);

CREATE TABLE policy
(
    id          VARCHAR(50)  NOT NULL,
    url         VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_policy PRIMARY KEY (id)
);

CREATE TABLE profession
(
    id          VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    priority    INTEGER      NOT NULL,
    file_id     VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_profession PRIMARY KEY (id)
);

CREATE TABLE promocode
(
    id                  VARCHAR(255) NOT NULL,
    code                VARCHAR(255) NOT NULL,
    description         VARCHAR(255),
    discount_type       VARCHAR(255) NOT NULL,
    discount_amount     INTEGER,
    discount_percentage DOUBLE PRECISION,
    activation_limit    INTEGER      NOT NULL,
    activated_amount    INTEGER      NOT NULL,
    total_attempts      INTEGER      NOT NULL,
    validity_from       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    validity_to         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status              VARCHAR(255) NOT NULL,
    admin_id            VARCHAR(255),
    supplier_id         VARCHAR(255),
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_promocode PRIMARY KEY (id)
);

CREATE TABLE promocode_client_activated
(
    id           VARCHAR(255) NOT NULL,
    promocode_id VARCHAR(255),
    client_id    VARCHAR(255),
    CONSTRAINT pk_promocode_client_activated PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id              VARCHAR(255) NOT NULL,
    monday          VARCHAR(255),
    monday_break    VARCHAR(255),
    tuesday         VARCHAR(255),
    tuesday_break   VARCHAR(255),
    wednesday       VARCHAR(255),
    wednesday_break VARCHAR(255),
    thursday        VARCHAR(255),
    thursday_break  VARCHAR(255),
    friday          VARCHAR(255),
    friday_break    VARCHAR(255),
    saturday        VARCHAR(255),
    saturday_break  VARCHAR(255),
    sunday          VARCHAR(255),
    sunday_break    VARCHAR(255),
    CONSTRAINT pk_schedule PRIMARY KEY (id)
);

CREATE TABLE social_media
(
    id        VARCHAR(255) NOT NULL,
    linkedin  VARCHAR(255),
    facebook  VARCHAR(255),
    instagram VARCHAR(255),
    youtube   VARCHAR(255),
    tiktok    VARCHAR(255),
    telegram  VARCHAR(255),
    twitter   VARCHAR(255),
    vk        VARCHAR(255),
    CONSTRAINT pk_social_media PRIMARY KEY (id)
);

CREATE TABLE speciality
(
    id            VARCHAR(255) NOT NULL,
    title         VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    priority      INTEGER      NOT NULL,
    profession_id VARCHAR(255),
    file_id       VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_speciality PRIMARY KEY (id)
);

CREATE TABLE stories
(
    id           VARCHAR(50) NOT NULL,
    title        VARCHAR(255),
    priority     INTEGER     NOT NULL,
    html_content TEXT        NOT NULL,
    article_id   VARCHAR(50),
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_stories PRIMARY KEY (id)
);

CREATE TABLE stories_files
(
    files_id   VARCHAR(255) NOT NULL,
    stories_id VARCHAR(50)  NOT NULL,
    CONSTRAINT pk_storiesfiles PRIMARY KEY (files_id, stories_id)
);

CREATE TABLE stories_supplier
(
    stories_id  VARCHAR(50)  NOT NULL,
    supplier_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_stories_supplier PRIMARY KEY (stories_id, supplier_id)
);

CREATE TABLE supplier
(
    id                 VARCHAR(255) NOT NULL,
    email              VARCHAR(255),
    phone              VARCHAR(255),
    first_name         VARCHAR(255),
    surname            VARCHAR(255),
    patronymic         VARCHAR(255),
    birth_date         date,
    social_media       VARCHAR(255),
    bank_account       VARCHAR(255),
    avatar             VARCHAR(255),
    enable_status      VARCHAR(255) NOT NULL,
    activity_status    VARCHAR(255) NOT NULL,
    status             VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    rating             FLOAT        NOT NULL,
    feedback_count     INTEGER      NOT NULL,
    client_size        INTEGER      NOT NULL,
    session_count      INTEGER      NOT NULL,
    total_earnings     FLOAT        NOT NULL,
    profession_id      VARCHAR(255),
    experience         date,
    about              VARCHAR(255),
    video_file_id      VARCHAR(255),
    timezone           VARCHAR(255),
    specialization_id  VARCHAR(255),
    chat_per_hour      INTEGER,
    chat_per_minute    INTEGER,
    audio_per_hour     INTEGER,
    audio_per_minute   INTEGER,
    video_per_hour     INTEGER,
    video_per_minute   INTEGER,
    schedule_id        VARCHAR(255),
    gender             VARCHAR(255) NOT NULL,
    file_id            VARCHAR(255),
    os_type            VARCHAR(255),
    notify             BOOLEAN      NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    registration_token VARCHAR(255),
    CONSTRAINT pk_supplier PRIMARY KEY (id)
);

CREATE TABLE supplier_files
(
    files_id    VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_supplier_files PRIMARY KEY (files_id, supplier_id)
);

CREATE TABLE tutorial
(
    id          VARCHAR(50)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    priority    INTEGER      NOT NULL,
    file_id     VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    authority   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tutorial PRIMARY KEY (id)
);

CREATE TABLE widget
(
    id                        VARCHAR(255) NOT NULL,
    header                    VARCHAR(255) NOT NULL,
    text                      VARCHAR(255),
    background_color          INTEGER      NOT NULL,
    gradient_background_color BOOLEAN      NOT NULL,
    text_color                INTEGER      NOT NULL,
    action_url                VARCHAR(255) NOT NULL,
    authority                 VARCHAR(255) NOT NULL,
    CONSTRAINT pk_widget PRIMARY KEY (id)
);

ALTER TABLE article_files
    ADD CONSTRAINT uc_article_files_files UNIQUE (files_id);

ALTER TABLE article
    ADD CONSTRAINT uc_article_title UNIQUE (title);

ALTER TABLE bundle
    ADD CONSTRAINT uc_bundle_title UNIQUE (title);

ALTER TABLE faq
    ADD CONSTRAINT uc_faq_title UNIQUE (title);

ALTER TABLE passport_photo
    ADD CONSTRAINT uc_passport_photo_passportfiles UNIQUE (passport_files_id);

ALTER TABLE profession
    ADD CONSTRAINT uc_profession_title UNIQUE (title);

ALTER TABLE promocode
    ADD CONSTRAINT uc_promocode_code UNIQUE (code);

ALTER TABLE speciality
    ADD CONSTRAINT uc_speciality_title UNIQUE (title);

ALTER TABLE stories_files
    ADD CONSTRAINT uc_stories_files_files UNIQUE (files_id);

ALTER TABLE supplier_files
    ADD CONSTRAINT uc_supplier_files_files UNIQUE (files_id);

ALTER TABLE tutorial
    ADD CONSTRAINT uc_tutorial_title UNIQUE (title);

ALTER TABLE admin_authority
    ADD CONSTRAINT FK_ADMIN_AUTHORITY_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES admin (id);

ALTER TABLE admin
    ADD CONSTRAINT FK_ADMIN_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_RECEIPT FOREIGN KEY (receipt_id) REFERENCES file (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE article
    ADD CONSTRAINT FK_ARTICLE_ON_PROFESSION FOREIGN KEY (profession_id) REFERENCES profession (id);

ALTER TABLE calendar
    ADD CONSTRAINT FK_CALENDAR_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE certificate
    ADD CONSTRAINT FK_CERTIFICATE_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE certificate
    ADD CONSTRAINT FK_CERTIFICATE_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE chat
    ADD CONSTRAINT FK_CHAT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE chat
    ADD CONSTRAINT FK_CHAT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE chat_support
    ADD CONSTRAINT FK_CHAT_SUPPORT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE chat_support
    ADD CONSTRAINT FK_CHAT_SUPPORT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE client
    ADD CONSTRAINT FK_CLIENT_ON_AVATAR FOREIGN KEY (avatar) REFERENCES file (id);

ALTER TABLE client
    ADD CONSTRAINT FK_CLIENT_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE education
    ADD CONSTRAINT FK_EDUCATION_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE education
    ADD CONSTRAINT FK_EDUCATION_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE favorite_supplier
    ADD CONSTRAINT FK_FAVORITE_SUPPLIER_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE favorite_supplier
    ADD CONSTRAINT FK_FAVORITE_SUPPLIER_ON_FAVORITE_SUPPLIER FOREIGN KEY (favorite_supplier_id) REFERENCES supplier (id);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_CHAT FOREIGN KEY (chat_id) REFERENCES chat (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE profession
    ADD CONSTRAINT FK_PROFESSION_ON_FILEID FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE promocode_client_activated
    ADD CONSTRAINT FK_PROMOCODE_CLIENT_ACTIVATED_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE promocode_client_activated
    ADD CONSTRAINT FK_PROMOCODE_CLIENT_ACTIVATED_ON_PROMOCODE FOREIGN KEY (promocode_id) REFERENCES promocode (id);

ALTER TABLE promocode
    ADD CONSTRAINT FK_PROMOCODE_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES admin (id);

ALTER TABLE promocode
    ADD CONSTRAINT FK_PROMOCODE_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE speciality
    ADD CONSTRAINT FK_SPECIALITY_ON_FILEID FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE speciality
    ADD CONSTRAINT FK_SPECIALITY_ON_PROFESSION FOREIGN KEY (profession_id) REFERENCES profession (id);

ALTER TABLE stories
    ADD CONSTRAINT FK_STORIES_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_AVATAR FOREIGN KEY (avatar) REFERENCES file (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_BANK_ACCOUNT FOREIGN KEY (bank_account) REFERENCES bank_account (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_PROFESSION FOREIGN KEY (profession_id) REFERENCES profession (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES schedule (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_SOCIAL_MEDIA FOREIGN KEY (social_media) REFERENCES social_media (id);

ALTER TABLE tutorial
    ADD CONSTRAINT FK_TUTORIAL_ON_FILEID FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE article_files
    ADD CONSTRAINT fk_artfil_on_article_entity FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_files
    ADD CONSTRAINT fk_artfil_on_file_entity FOREIGN KEY (files_id) REFERENCES file (id);

ALTER TABLE passport_photo
    ADD CONSTRAINT fk_paspho_on_file_entity FOREIGN KEY (passport_files_id) REFERENCES file (id);

ALTER TABLE passport_photo
    ADD CONSTRAINT fk_paspho_on_supplier_entity FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE stories_files
    ADD CONSTRAINT fk_stofil_on_file_entity FOREIGN KEY (files_id) REFERENCES file (id);

ALTER TABLE stories_files
    ADD CONSTRAINT fk_stofil_on_stories_entity FOREIGN KEY (stories_id) REFERENCES stories (id);

ALTER TABLE stories_supplier
    ADD CONSTRAINT fk_stosup_on_stories_entity FOREIGN KEY (stories_id) REFERENCES stories (id);

ALTER TABLE stories_supplier
    ADD CONSTRAINT fk_stosup_on_supplier_entity FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE supplier_files
    ADD CONSTRAINT fk_supfil_on_file_entity FOREIGN KEY (files_id) REFERENCES file (id);

ALTER TABLE supplier_files
    ADD CONSTRAINT fk_supfil_on_supplier_entity FOREIGN KEY (supplier_id) REFERENCES supplier (id);