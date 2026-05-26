-- PostgreSQL database dump
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Безопасный сброс всех таблиц (CASCADE автоматически удаляет связанные FK)
DROP TABLE IF EXISTS public.vacancy_moderation CASCADE;
DROP TABLE IF EXISTS public.applications CASCADE;
DROP TABLE IF EXISTS public.company_reviews CASCADE;
DROP TABLE IF EXISTS public.vacancy_specialties CASCADE;
DROP TABLE IF EXISTS public.favorites CASCADE;
DROP TABLE IF EXISTS public.notifications CASCADE;
DROP TABLE IF EXISTS public.vacancies CASCADE;
DROP TABLE IF EXISTS public.resumes CASCADE;
DROP TABLE IF EXISTS public.student_profile CASCADE;
DROP TABLE IF EXISTS public.employer_profile CASCADE;
DROP TABLE IF EXISTS public.files CASCADE;
DROP TABLE IF EXISTS public.guide CASCADE;
DROP TABLE IF EXISTS public.refresh_token CASCADE;
DROP TABLE IF EXISTS public.specialties CASCADE;
DROP TABLE IF EXISTS public.user_role CASCADE;
DROP TABLE IF EXISTS public.roles CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

SET default_tablespace = '';
SET default_table_access_method = heap;

-- 1. Базовые таблицы (без FK)
CREATE TABLE public.users (
                              id UUID PRIMARY KEY,
                              email VARCHAR(50) NOT NULL,
                              password_hash VARCHAR(255) NOT NULL,
                              is_active BOOLEAN NOT NULL DEFAULT true,
                              last_login_at TIMESTAMP WITH TIME ZONE,
                              created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                              deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE public.roles (
                              id UUID PRIMARY KEY,
                              name VARCHAR(50) NOT NULL UNIQUE,
                              CONSTRAINT roles_name_check CHECK (name IN ('ROLE_STUDENT', 'ROLE_EMPLOYER', 'ROLE_ADMIN'))
);

CREATE TABLE public.user_role (
                                  user_id UUID NOT NULL,
                                  role_id UUID NOT NULL,
                                  PRIMARY KEY (user_id, role_id)
);

CREATE TABLE public.files (
                              id UUID PRIMARY KEY,
                              file_name VARCHAR(255) NOT NULL,
                              mime_type VARCHAR(255) NOT NULL,
                              file_size BIGINT NOT NULL,
                              file_type VARCHAR(50) NOT NULL,
                              file_data BYTEA NOT NULL,
                              owner_id UUID NOT NULL,
                              created_at TIMESTAMP NOT NULL
);

CREATE TABLE public.student_profile (
                                        user_id UUID PRIMARY KEY,
                                        full_name VARCHAR(255),
                                        graduation_year INTEGER,
                                        avatar_file_id UUID
);

CREATE TABLE public.employer_profile (
                                         user_id UUID PRIMARY KEY,
                                         company_name VARCHAR(160),
                                         description VARCHAR(5000),
                                         website_link VARCHAR(255),
                                         logo_file_id UUID
);

CREATE TABLE public.resumes (
                                id UUID PRIMARY KEY,
                                title VARCHAR(255) NOT NULL,
                                content TEXT,
                                created_at DATE,
                                updated_at DATE,
                                student_id UUID NOT NULL UNIQUE,
                                file_id UUID NOT NULL
);

CREATE TABLE public.specialties (
                                    id UUID PRIMARY KEY,
                                    code VARCHAR(255) UNIQUE,
                                    is_active BOOLEAN NOT NULL,
                                    name VARCHAR(255)
);

CREATE TABLE public.vacancies (
                                  id UUID PRIMARY KEY,
                                  employer_id UUID,
                                  title VARCHAR(255),
                                  description VARCHAR(5000) NOT NULL,
                                  city VARCHAR(255),
                                  start_date DATE,
                                  end_date DATE,
                                  status VARCHAR(255),
                                  created_at DATE,
                                  updated_at DATE,
                                  deleted_at DATE,
                                  CONSTRAINT vacancies_status_check CHECK (status IN ('PENDING', 'REVIEWED', 'ACCEPTED', 'REJECTED'))
);

CREATE TABLE public.vacancy_specialties (
                                            vacancy_id UUID NOT NULL,
                                            specialty_id UUID NOT NULL,
                                            PRIMARY KEY (vacancy_id, specialty_id)
);

CREATE TABLE public.applications (
                                     id UUID PRIMARY KEY,
                                     student_id UUID,
                                     vacancy_id UUID,
                                     resume_id UUID,
                                     cover_letter VARCHAR(255),
                                     status VARCHAR(255),
                                     created_at DATE
);

CREATE TABLE public.favorites (
                                  id UUID PRIMARY KEY,
                                  student_id UUID,
                                  vacancy_id UUID,
                                  created_at TIMESTAMP NOT NULL
);

CREATE TABLE public.company_reviews (
                                        id UUID PRIMARY KEY,
                                        student_id UUID,
                                        employer_id UUID,
                                        rating SMALLINT,
                                        comment VARCHAR(255),
                                        created_at DATE
);

CREATE TABLE public.notifications (
                                      id UUID PRIMARY KEY,
                                      user_id UUID,
                                      "mеssage_type" SMALLINT,
                                      payload JSONB,
                                      is_read BOOLEAN,
                                      created_at DATE,
                                      CONSTRAINT "notifications_mеssage_type_check" CHECK ("mеssage_type" BETWEEN 0 AND 1)
);

CREATE TABLE public.vacancy_moderation (
                                           id UUID PRIMARY KEY,
                                           vacancy_id UUID UNIQUE,
                                           admin_id UUID,
                                           decision VARCHAR(255),
                                           comment VARCHAR(255),
                                           created_at DATE
);

CREATE TABLE public.refresh_token (
                                      id UUID PRIMARY KEY,
                                      token VARCHAR(255),
                                      email VARCHAR(255),
                                      expires_at DATE,
                                      revoked BOOLEAN NOT NULL
);

CREATE TABLE public.guide (
                              id UUID PRIMARY KEY,
                              title VARCHAR(255) NOT NULL,
                              content VARCHAR(10000) NOT NULL,
                              created_at DATE NOT NULL
);

-- 2. Вставка данных
COPY public.applications (id, cover_letter, created_at, status, resume_id, student_id, vacancy_id) FROM stdin;
\.

COPY public.company_reviews (id, comment, created_at, rating, employer_id, student_id) FROM stdin;
\.

COPY public.employer_profile (user_id, company_name, description, website_link, logo_file_id) FROM stdin;
\.

COPY public.favorites (id, created_at, student_id, vacancy_id) FROM stdin;
\.

COPY public.files (id, created_at, file_name, file_size, mime_type, owner_id, file_data, file_type) FROM stdin;
\.

COPY public.guide (id, content, created_at, title) FROM stdin;
\.

COPY public.notifications (id, created_at, is_read, "mеssage_type", payload, user_id) FROM stdin;
\.

COPY public.refresh_token (id, email, expires_at, revoked, token) FROM stdin;
1659b68e-6b36-4483-9f7b-88da344fd654	student6@gmail.com	2026-04-26	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoic3R1ZGVudDZAZ21haWwuY29tIiwiaWF0IjoxNzc3MDYwOTY5LCJleHAiOjE3NzcyMzM3NjksImp0aSI6IjdmZjRhNGJhLWY3MjItNDBhMi1iMjJlLWE5YWZkNzVhNTQ0MiJ9.B0NBDypCm0WZp-TiNb30RsD6AicRWvrYCjImtrlf6u4
c879b917-bad4-41cc-936b-566f64bed407	admin@gmail.com	2026-04-26	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzc3MDYxMDA5LCJleHAiOjE3NzcyMzM4MDksImp0aSI6ImY2N2ZiMmQxLWUxZGUtNDdkYy04NjZlLWRmZGYyNGQ1NzU5YyJ9.zEZpSveKp_Te9EudScX0T_Fw94F7ifTeYVEjlKeysBs
768cac35-a968-49e3-ad64-f7203ffbb236	student@gmail.com	2026-04-28	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoic3R1ZGVudEBnbWFpbC5jb20iLCJpYXQiOjoxNzc3MjE4MzQxLCJleHAiOjE3NzczOTExNDEsImp0aSI6ImNmNWQ2Y2Y4LWFjZDktNDdkMS1iODExLTNlYjFkYWY4MjI1ZiJ9.iNUhq6JLrHlv9H_30hWoB4R-OWjioj9KlN5bUQzKFEU
cabf90b6-5934-412d-97dd-5de4c951f375	admin@gmail.com	2026-04-28	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzc3MjE4MzYyLCJleHAiOjE3NzczOTExNjIsImp0aSI6ImI5ZDI4YmFlLTkwODAtNDYyMS05ZTBlLTBiNWNkMGExYzgwMCJ9.emere90AXOmgbAN6TorYTe9UsfOihn-vvxlmhOpwNOQ
\.

COPY public.resumes (id, title, content, created_at, updated_at, student_id, file_id) FROM stdin;
\.

COPY public.roles (id, name) FROM stdin;
de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab	ROLE_STUDENT
90d617cd-3782-468a-96c9-e8aa668e98f5	ROLE_EMPLOYER
2d6aee68-3888-4fb0-8e67-6f1ac7757c61	ROLE_ADMIN
\.

COPY public.specialties (id, code, is_active, name) FROM stdin;
\.

COPY public.student_profile (user_id, full_name, graduation_year, avatar_file_id) FROM stdin;
\.

COPY public.user_role (user_id, role_id) FROM stdin;
45178324-8cdc-4823-a68a-1dad0a66a7a8	de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab
d42545f7-d2ec-4b22-a724-61afe60f5a70	de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab
d42545f7-d2ec-4b22-a724-61afe60f5a70	2d6aee68-3888-4fb0-8e67-6f1ac7757c61
0502c582-b2e3-49c0-9aa9-43c6c0497b3c	de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab
\.

COPY public.users (id, created_at, deleted_at, email, is_active, last_login_at, password_hash) FROM stdin;
45178324-8cdc-4823-a68a-1dad0a66a7a8	2026-04-24 20:02:49.291575+00	\N	student6@gmail.com	t	\N	$2a$10$y2ihvxBsXjX3NZzTnUhLIep5JK97tGHdgntpiZa4c30Eh/NZQpjXW
0502c582-b2e3-49c0-9aa9-43c6c0497b3c	2026-04-26 15:45:41.930496+00	\N	student@gmail.com	t	\N	$2a$10$yfiRLT12ncP0ONulu5dneeKEZUvNFwyyOzvYwKBy77PhzCpp8v/wu
d42545f7-d2ec-4b22-a724-61afe60f5a70	2026-04-24 20:03:29.771318+00	\N	admin@gmail.com	t	2026-04-26 15:46:02.272723+00	$2a$10$.S0J8K/Fo2zY5omF5UlY1.wm/tI6ZgYS3H5vO8qBvRHk.MkpvDPsm
\.

COPY public.vacancies (id, city, created_at, deleted_at, description, end_date, start_date, status, title, updated_at, employer_id) FROM stdin;
\.

COPY public.vacancy_moderation (id, comment, created_at, decision, admin_id, vacancy_id) FROM stdin;
\.

COPY public.vacancy_specialties (vacancy_id, specialty_id) FROM stdin;
\.

-- 1. Дополнительные пользователи
INSERT INTO public.users (id, email, password_hash, is_active, created_at) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'ivan.petrov@mail.ru', '$2a$10$dummyhash1234567890abcdefghijklmnopqrstuv', true, '2026-05-01 10:00:00+03'),
('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'maria.sidorova@mail.ru', '$2a$10$dummyhash1234567890abcdefghijklmnopqrstuv', true, '2026-05-02 11:30:00+03'),
('c3d4e5f6-a7b8-9012-cdef-123456789012', 'hr@techcorp.ru', '$2a$10$dummyhash1234567890abcdefghijklmnopqrstuv', true, '2026-04-15 09:00:00+03'),
('d4e5f6a7-b8c9-0123-def0-234567890123', 'jobs@innovate.io', '$2a$10$dummyhash1234567890abcdefghijklmnopqrstuv', true, '2026-04-20 14:20:00+03');

-- 2. Назначение ролей (студентам и работодателям)
INSERT INTO public.user_role (user_id, role_id) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab'), -- ROLE_STUDENT
('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab'), -- ROLE_STUDENT
('c3d4e5f6-a7b8-9012-cdef-123456789012', '90d617cd-3782-468a-96c9-e8aa668e98f5'), -- ROLE_EMPLOYER
('d4e5f6a7-b8c9-0123-def0-234567890123', '90d617cd-3782-468a-96c9-e8aa668e98f5'); -- ROLE_EMPLOYER

-- 3. Профили студентов
INSERT INTO public.student_profile (user_id, full_name, graduation_year) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Иван Петров', 2025),
('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'Мария Сидорова', 2026);

-- 4. Профили работодателей
INSERT INTO public.employer_profile (user_id, company_name, description, website_link) VALUES
('c3d4e5f6-a7b8-9012-cdef-123456789012', 'TechCorp', 'Крупный системный интегратор', 'https://techcorp.ru'),
('d4e5f6a7-b8c9-0123-def0-234567890123', 'Innovate IO', 'Стартап в сфере AI и Big Data', 'https://innovate.io');

-- 5. Файлы (резюме)
INSERT INTO public.files (id, file_name, mime_type, file_size, file_type, file_data, owner_id, created_at) VALUES
('44444444-4444-4444-4444-444444444444', 'resume_ivan.pdf', 'application/pdf', 152000, 'RESUME', E'\\x255044462D312E34', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '2026-05-01'),
('55555555-5555-5555-5555-555555555555', 'resume_maria.pdf', 'application/pdf', 189000, 'RESUME', E'\\x255044462D312E34', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '2026-05-02');

-- 6. Резюме
INSERT INTO public.resumes (id, title, content, created_at, updated_at, student_id, file_id) VALUES
('66666666-6666-6666-6666-666666666666', 'Junior Java Developer', 'Spring Boot, PostgreSQL, Git. Опыт стажировки 6 мес.', '2026-05-01', '2026-05-05', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '44444444-4444-4444-4444-444444444444'),
('77777777-7777-7777-7777-777777777777', 'Frontend React Developer', 'React, TypeScript, Redux, Figma. 2 пет-проекта.', '2026-05-02', '2026-05-10', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '55555555-5555-5555-5555-555555555555');

-- 7. Специальности
INSERT INTO public.specialties (id, code, is_active, name) VALUES
('e1e2e3e4-f5f6-7890-1111-222233334444', '09.03.01', true, 'Информатика и вычислительная техника'),
('f2f3f4f5-a6b7-8901-2222-333344445555', '02.03.02', true, 'Фундаментальная информатика и ИТ'),
('a3b4c5d6-e7f8-9012-3333-444455556666', '10.05.01', true, 'Компьютерная безопасность');

-- 8. Вакансии
INSERT INTO public.vacancies (id, employer_id, title, description, city, start_date, end_date, status, created_at) VALUES
('11111111-1111-1111-1111-111111111111', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'Junior Java Developer', 'Разработка микросервисов, код-ревью, участие в Agile.', 'Москва', '2026-06-01', '2026-12-31', 'ACCEPTED', '2026-05-01'),
('22222222-2222-2222-2222-222222222222', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'Frontend React', 'Верстка SPA, интеграция REST API, оптимизация.', 'Санкт-Петербург', '2026-07-01', '2027-01-01', 'PENDING', '2026-05-10'),
('33333333-3333-3333-3333-333333333333', 'd4e5f6a7-b8c9-0123-def0-234567890123', 'Data Scientist Intern', 'Анализ данных, построение ML-моделей, Python.', 'Казань', '2026-08-01', '2026-11-30', 'REVIEWED', '2026-05-15');

-- 9. Отклики на вакансии (Applications)
INSERT INTO public.applications (id, student_id, vacancy_id, resume_id, cover_letter, status, created_at) VALUES
('88888888-8888-8888-8888-888888888888', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '11111111-1111-1111-1111-111111111111', '66666666-6666-6666-6666-666666666666', 'Хочу развиваться в enterprise разработке', 'SENT', '2026-05-22'),
('99999999-9999-9999-9999-999999999999', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '22222222-2222-2222-2222-222222222222', '77777777-7777-7777-7777-777777777777', 'Ищу стажировку в крутой команде', 'REVIEWED', '2026-05-23');

-- 10. Избранное
INSERT INTO public.favorites (id, student_id, vacancy_id, created_at) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '33333333-3333-3333-3333-333333333333', '2026-05-24 12:00:00+03'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '11111111-1111-1111-1111-111111111111', '2026-05-24 15:30:00+03');

-- 11. Отзывы о компаниях
INSERT INTO public.company_reviews (id, student_id, employer_id, rating, comment, created_at) VALUES
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 5, 'Отличное место для старта карьеры, менторство топ!', '2026-05-25'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', 'd4e5f6a7-b8c9-0123-def0-234567890123', 4, 'Быстрые собеседования, но много задач в первый месяц.', '2026-05-25');

-- 3. Добавление FK и индексов (гарантирует порядок создания)
ALTER TABLE public.user_role ADD CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES public.users(id);
ALTER TABLE public.user_role ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES public.roles(id);

ALTER TABLE public.files ADD CONSTRAINT fk_files_owner FOREIGN KEY (owner_id) REFERENCES public.users(id);

ALTER TABLE public.student_profile ADD CONSTRAINT fk_student_profile_user FOREIGN KEY (user_id) REFERENCES public.users(id);
ALTER TABLE public.student_profile ADD CONSTRAINT fk_student_avatar FOREIGN KEY (avatar_file_id) REFERENCES public.files(id);

ALTER TABLE public.employer_profile ADD CONSTRAINT fk_employer_user FOREIGN KEY (user_id) REFERENCES public.users(id);
ALTER TABLE public.employer_profile ADD CONSTRAINT fk_employer_logo FOREIGN KEY (logo_file_id) REFERENCES public.files(id);

ALTER TABLE public.resumes ADD CONSTRAINT fk_resume_student FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);
ALTER TABLE public.resumes ADD CONSTRAINT fk_resume_file FOREIGN KEY (file_id) REFERENCES public.files(id);

ALTER TABLE public.vacancies ADD CONSTRAINT fk_vacancies_employer FOREIGN KEY (employer_id) REFERENCES public.employer_profile(user_id);

ALTER TABLE public.vacancy_specialties ADD CONSTRAINT fk_vs_vacancy FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);
ALTER TABLE public.vacancy_specialties ADD CONSTRAINT fk_vs_specialty FOREIGN KEY (specialty_id) REFERENCES public.specialties(id);

ALTER TABLE public.applications ADD CONSTRAINT fk_app_student FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);
ALTER TABLE public.applications ADD CONSTRAINT fk_app_vacancy FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);
ALTER TABLE public.applications ADD CONSTRAINT fk_app_resume FOREIGN KEY (resume_id) REFERENCES public.resumes(id);

ALTER TABLE public.favorites ADD CONSTRAINT fk_fav_student FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);
ALTER TABLE public.favorites ADD CONSTRAINT fk_fav_vacancy FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);

ALTER TABLE public.company_reviews ADD CONSTRAINT fk_review_student FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);
ALTER TABLE public.company_reviews ADD CONSTRAINT fk_review_employer FOREIGN KEY (employer_id) REFERENCES public.employer_profile(user_id);

ALTER TABLE public.notifications ADD CONSTRAINT fk_notif_user FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE public.vacancy_moderation ADD CONSTRAINT fk_mod_vacancy FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);
ALTER TABLE public.vacancy_moderation ADD CONSTRAINT fk_mod_admin FOREIGN KEY (admin_id) REFERENCES public.users(id);

-- Индексы для производительности
CREATE INDEX idx_files_owner ON public.files(owner_id);
CREATE INDEX idx_files_type ON public.files(file_type);
CREATE INDEX idx_vacancies_employer ON public.vacancies(employer_id);
CREATE INDEX idx_applications_vacancy ON public.applications(vacancy_id);

-- Готово
