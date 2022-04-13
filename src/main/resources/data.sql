-- USERS
INSERT INTO Users (id, name, email, password)
VALUES (100, 'Shannon', 'shannon@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2') ON CONFLICT DO NOTHING ;

INSERT INTO Users (id, name, email, password)
VALUES (101, 'David', 'david@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2') ON CONFLICT DO NOTHING ;

INSERT INTO Users (id, name, email, password)
VALUES (102, 'Kyle', 'kyle@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2') ON CONFLICT DO NOTHING ;


-- ROLES
INSERT INTO role (id, role)
VALUES (1, 'ADMIN')  ON CONFLICT DO NOTHING ;

INSERT INTO role (id, role)
VALUES (2, 'USER')  ON CONFLICT DO NOTHING ;


-- USERS_ROLES
INSERT INTO users_roles (user_id, roles_id)
VALUES (100, 1) ON CONFLICT DO NOTHING ;

INSERT INTO users_roles (user_id, roles_id)
VALUES (101, 2) ON CONFLICT DO NOTHING ;

INSERT INTO users_roles (user_id, roles_id)
VALUES (102, 2) ON CONFLICT DO NOTHING ;


-- POSTS
INSERT INTO Post (id, title, content, author_id)
VALUES (200, 'Open Mic Night', 'Open Mic & Karaoke night in the Student Lounge', 101)
ON CONFLICT DO NOTHING ;

INSERT INTO Post (id, title, content, author_id)
VALUES (201, 'Jobot is Hiring', 'Entry Level Engineering Manager Position', 101)
ON CONFLICT DO NOTHING ;

INSERT INTO Post (id, title, content, author_id)
VALUES (202, 'The New Pixel 6', 'The All New Pixel 6 is Here!', 102)
ON CONFLICT DO NOTHING ;


-- COMMENTS
INSERT INTO Comment (id, name, post_id)
VALUES (300, 'I disagree', 201)
ON CONFLICT DO NOTHING ;

INSERT INTO Comment (id, name, post_id)
VALUES (301, 'What did I just read?', 201)
ON CONFLICT DO NOTHING ;

INSERT INTO Comment (id, name, post_id)
VALUES (302, 'First!', 201)
ON CONFLICT DO NOTHING ;

INSERT INTO Comment (id, name, post_id)
VALUES (303, 'Try this link', 202)
ON CONFLICT DO NOTHING ;

INSERT INTO Comment (id, name, post_id)
VALUES (304, 'This works', 202)
ON CONFLICT DO NOTHING ;

INSERT INTO Comment (id, name, post_id)
VALUES (305, 'I was kidding obviously!', 201)
ON CONFLICT DO NOTHING ;