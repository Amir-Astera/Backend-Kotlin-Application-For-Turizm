CREATE TABLE app_user
(
    id                   VARCHAR(255) NOT NULL,
    user_name            VARCHAR(255),
    email                VARCHAR(255),
    phone                VARCHAR(255),
    gender               VARCHAR(255) NOT NULL,
    client_id            VARCHAR(255),
    supplier_id          VARCHAR(255),
    file_id              VARCHAR(255),
    birth_date           date,
    phone_type           VARCHAR(255),
    os_type              VARCHAR(255),
    app_type             VARCHAR(255),
    have_unread_messages BOOLEAN,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE appointment
(
    id               VARCHAR(50)  NOT NULL,
    supplier_id      VARCHAR(255) NOT NULL,
    client_id        VARCHAR(255) NOT NULL,
    reservation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    type             VARCHAR(255) NOT NULL,
    description      TEXT         NOT NULL,
    status           VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

CREATE TABLE article
(
    id          VARCHAR(50)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    priority    INTEGER      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_article PRIMARY KEY (id)
);

CREATE TABLE article_files
(
    article_id VARCHAR(50)  NOT NULL,
    files_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_articlefiles PRIMARY KEY (article_id, files_id)
);

CREATE TABLE authority
(
    id          VARCHAR(255) NOT NULL,
    code        VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_authority PRIMARY KEY (id)
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

CREATE TABLE client
(
    id            VARCHAR(255) NOT NULL,
    session_count INTEGER      NOT NULL,
    expenses      FLOAT        NOT NULL,
    avatar        VARCHAR(255),
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE education
(
    id               VARCHAR(255) NOT NULL,
    institution_name VARCHAR(255),
    faculty          VARCHAR(255),
    specialization   VARCHAR(255),
    year             INTEGER,
    CONSTRAINT pk_education PRIMARY KEY (id)
);

CREATE TABLE faq
(
    id          VARCHAR(50)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_faq PRIMARY KEY (id)
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
    id                     VARCHAR(255) NOT NULL,
    title                  VARCHAR(255) NOT NULL,
    type                   VARCHAR(255) NOT NULL,
    enable_status          VARCHAR(255) NOT NULL,
    activity_status        VARCHAR(255) NOT NULL,
    discount               FLOAT        NOT NULL,
    validity_from          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    validity_to            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_activation_count  INTEGER      NOT NULL,
    total_activation_count INTEGER      NOT NULL,
    supplier_id            VARCHAR(255),
    created_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_promocode PRIMARY KEY (id)
);

CREATE TABLE promocode_professions
(
    professions_id VARCHAR(255) NOT NULL,
    promocode_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_promocode_professions PRIMARY KEY (professions_id, promocode_id)
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

CREATE TABLE speciality
(
    id            VARCHAR(255) NOT NULL,
    title         VARCHAR(255) NOT NULL,
    description   VARCHAR(255),
    priority      INTEGER      NOT NULL,
    file_id       VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    profession_id VARCHAR(255),
    CONSTRAINT pk_speciality PRIMARY KEY (id)
);

CREATE TABLE supplier
(
    id                VARCHAR(255) NOT NULL,
    enable_status     VARCHAR(255) NOT NULL,
    activity_status   VARCHAR(255) NOT NULL,
    status            VARCHAR(255) NOT NULL,
    description       VARCHAR(255),
    rating            FLOAT        NOT NULL,
    client_size       INTEGER      NOT NULL,
    session_count     INTEGER      NOT NULL,
    total_earnings    FLOAT        NOT NULL,
    profession_id     VARCHAR(255),
    experience        INTEGER,
    photo_file_id     VARCHAR(255),
    about             VARCHAR(255),
    video_file_id     VARCHAR(255),
    timezone          VARCHAR(255),
    specialization_id VARCHAR(255),
    chat_per_hour     INTEGER,
    chat_per_minute   INTEGER,
    audio_per_hour    INTEGER,
    audio_per_minute  INTEGER,
    video_per_hour    INTEGER,
    video_per_minute  INTEGER,
    schedule_id       VARCHAR(255),
    education_id      VARCHAR(255),
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
    CONSTRAINT pk_tutorial PRIMARY KEY (id)
);

CREATE TABLE user_authority
(
    id              VARCHAR(255) NOT NULL,
    authority       VARCHAR(255) NOT NULL,
    enable_status   VARCHAR(255) NOT NULL,
    activity_status VARCHAR(255) NOT NULL,
    app_user_id     VARCHAR(255),
    CONSTRAINT pk_user_authority PRIMARY KEY (id)
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
    position                  INTEGER      NOT NULL,
    CONSTRAINT pk_widget PRIMARY KEY (id)
);

ALTER TABLE article_files
    ADD CONSTRAINT uc_article_files_files UNIQUE (files_id);

ALTER TABLE article
    ADD CONSTRAINT uc_article_title UNIQUE (title);

ALTER TABLE authority
    ADD CONSTRAINT uc_authority_code UNIQUE (code);

ALTER TABLE authority
    ADD CONSTRAINT uc_authority_name UNIQUE (name);

ALTER TABLE bundle
    ADD CONSTRAINT uc_bundle_title UNIQUE (title);

ALTER TABLE faq
    ADD CONSTRAINT uc_faq_title UNIQUE (title);

ALTER TABLE profession
    ADD CONSTRAINT uc_profession_title UNIQUE (title);

ALTER TABLE promocode_professions
    ADD CONSTRAINT uc_promocode_professions_professions UNIQUE (professions_id);

ALTER TABLE promocode
    ADD CONSTRAINT uc_promocode_title UNIQUE (title);

ALTER TABLE speciality
    ADD CONSTRAINT uc_speciality_title UNIQUE (title);

ALTER TABLE supplier_files
    ADD CONSTRAINT uc_supplier_files_files UNIQUE (files_id);

ALTER TABLE tutorial
    ADD CONSTRAINT uc_tutorial_title UNIQUE (title);

ALTER TABLE widget
    ADD CONSTRAINT uc_widget_position UNIQUE (position);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE client
    ADD CONSTRAINT FK_CLIENT_ON_AVATAR FOREIGN KEY (avatar) REFERENCES file (id);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_CLIENT FOREIGN KEY (client_id) REFERENCES app_user (id);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE profession
    ADD CONSTRAINT FK_PROFESSION_ON_FILEID FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE promocode
    ADD CONSTRAINT FK_PROMOCODE_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE speciality
    ADD CONSTRAINT FK_SPECIALITY_ON_FILEID FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE speciality
    ADD CONSTRAINT FK_SPECIALITY_ON_PROFESSION FOREIGN KEY (profession_id) REFERENCES profession (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_EDUCATION FOREIGN KEY (education_id) REFERENCES education (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_PHOTO_FILE FOREIGN KEY (photo_file_id) REFERENCES file (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_PROFESSION FOREIGN KEY (profession_id) REFERENCES profession (id);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES schedule (id);

ALTER TABLE tutorial
    ADD CONSTRAINT FK_TUTORIAL_ON_FILEID FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE user_authority
    ADD CONSTRAINT FK_USER_AUTHORITY_ON_APP_USER FOREIGN KEY (app_user_id) REFERENCES app_user (id);

ALTER TABLE article_files
    ADD CONSTRAINT fk_artfil_on_article_entity FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_files
    ADD CONSTRAINT fk_artfil_on_file_entity FOREIGN KEY (files_id) REFERENCES file (id);

ALTER TABLE promocode_professions
    ADD CONSTRAINT fk_propro_on_profession_entity FOREIGN KEY (professions_id) REFERENCES profession (id);

ALTER TABLE promocode_professions
    ADD CONSTRAINT fk_propro_on_promocode_entity FOREIGN KEY (promocode_id) REFERENCES promocode (id);

ALTER TABLE supplier_files
    ADD CONSTRAINT fk_supfil_on_file_entity FOREIGN KEY (files_id) REFERENCES file (id);

ALTER TABLE supplier_files
    ADD CONSTRAINT fk_supfil_on_supplier_entity FOREIGN KEY (supplier_id) REFERENCES supplier (id);