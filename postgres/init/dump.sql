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