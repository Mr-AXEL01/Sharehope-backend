
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            categoryName VARCHAR(255) NOT NULL,
                            description VARCHAR(1000)
);


CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(50)
);


CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       role VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE app_user_roles (
                                user_id BIGINT NOT NULL,
                                role_id BIGINT NOT NULL,
                                PRIMARY KEY (user_id, role_id),
                                CONSTRAINT fk_app_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
                                CONSTRAINT fk_app_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);


CREATE TABLE actions (
                         id BIGSERIAL PRIMARY KEY,
                         amount DOUBLE PRECISION,
                         description VARCHAR(1000),
                         createdAt TIMESTAMP,
                         category_id BIGINT NOT NULL,
                         CONSTRAINT fk_actions_category FOREIGN KEY (category_id) REFERENCES categories(id)
);


CREATE TABLE needs (
                       id BIGINT PRIMARY KEY,
                       needStatus VARCHAR(50),
                       CONSTRAINT fk_needs_action FOREIGN KEY (id) REFERENCES actions(id)
);


CREATE TABLE donations (
                           id BIGINT PRIMARY KEY,
                           donationStatus VARCHAR(50),
                           CONSTRAINT fk_donations_action FOREIGN KEY (id) REFERENCES actions(id)
);


CREATE TABLE articles (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          description VARCHAR(1000),
                          content TEXT,
                          createdAt TIMESTAMP,
                          author_id BIGINT,
                          CONSTRAINT fk_articles_author FOREIGN KEY (author_id) REFERENCES users(id)
);


CREATE TABLE attachments (
                             id BIGSERIAL PRIMARY KEY,
                             filePath VARCHAR(255) NOT NULL,
                             fileType VARCHAR(50),
                             uploadDate TIMESTAMP,
                             attachableId BIGINT,
                             attachableType VARCHAR(50)
);
