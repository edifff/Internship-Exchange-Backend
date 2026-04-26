--
-- PostgreSQL database dump
--

\restrict peAQtSFNkffcyd11PBHnOFNY01bVy1o3bqQ85d2ax86VHJaI1QT668CStKFVhEX

-- Dumped from database version 15.17 (Debian 15.17-1.pgdg13+1)
-- Dumped by pg_dump version 15.17 (Debian 15.17-1.pgdg13+1)

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

ALTER TABLE IF EXISTS ONLY public.user_role DROP CONSTRAINT IF EXISTS fkt7e7djp752sqn6w22i6ocqy6q;
ALTER TABLE IF EXISTS ONLY public.vacancy_specialties DROP CONSTRAINT IF EXISTS fkrib825tpg4rv1vdkl07o15ffa;
ALTER TABLE IF EXISTS ONLY public.applications DROP CONSTRAINT IF EXISTS fkq2axowr8ewvl0ncquhtq8od8j;
ALTER TABLE IF EXISTS ONLY public.company_reviews DROP CONSTRAINT IF EXISTS fknvrjqxe02esvjw5r0alae58at;
ALTER TABLE IF EXISTS ONLY public.files DROP CONSTRAINT IF EXISTS fkndbd0r86rsovslrthjrgl960x;
ALTER TABLE IF EXISTS ONLY public.employer_profile DROP CONSTRAINT IF EXISTS fkm4bswyvitxwjye3uwgs45tue4;
ALTER TABLE IF EXISTS ONLY public.files DROP CONSTRAINT IF EXISTS fkkns2bg3o1ii7m8rjqvequuu0h;
ALTER TABLE IF EXISTS ONLY public.user_role DROP CONSTRAINT IF EXISTS fkj345gk1bovqvfame88rcx7yyx;
ALTER TABLE IF EXISTS ONLY public.company_reviews DROP CONSTRAINT IF EXISTS fki8j096fywp18p9ljidhgriqj1;
ALTER TABLE IF EXISTS ONLY public.student_profile DROP CONSTRAINT IF EXISTS fkh6555c9k0gv0yddac6llslk3t;
ALTER TABLE IF EXISTS ONLY public.employer_profile DROP CONSTRAINT IF EXISTS fkgtg25lwq63c2ye2nq2k5wepda;
ALTER TABLE IF EXISTS ONLY public.vacancy_specialties DROP CONSTRAINT IF EXISTS fkean5cugw18skfkqogxx32gaou;
ALTER TABLE IF EXISTS ONLY public.vacancy_moderation DROP CONSTRAINT IF EXISTS fkd6nsg1b4onfir06kt0tgumdlm;
ALTER TABLE IF EXISTS ONLY public.applications DROP CONSTRAINT IF EXISTS fkbhnfs5wwy14wdm2f1wam8qkhf;
ALTER TABLE IF EXISTS ONLY public.notifications DROP CONSTRAINT IF EXISTS fk9y21adhxn0ayjhfocscqox7bh;
ALTER TABLE IF EXISTS ONLY public.applications DROP CONSTRAINT IF EXISTS fk94iy6oijc6sl7apkp6cfutcxf;
ALTER TABLE IF EXISTS ONLY public.favorites DROP CONSTRAINT IF EXISTS fk76n3v0v6w4lw3egbx2hr0xxpq;
ALTER TABLE IF EXISTS ONLY public.vacancies DROP CONSTRAINT IF EXISTS fk4lwg9jmmgy0a25q3iaie0wvfd;
ALTER TABLE IF EXISTS ONLY public.vacancy_moderation DROP CONSTRAINT IF EXISTS fk3i4if3ot67nahtk0i36tbyxfb;
ALTER TABLE IF EXISTS ONLY public.student_profile DROP CONSTRAINT IF EXISTS fk365nmvykqt2lwfskcd2p8hy2q;
ALTER TABLE IF EXISTS ONLY public.resumes DROP CONSTRAINT IF EXISTS fk2ae98pqgb982e1kdj4lvctash;
ALTER TABLE IF EXISTS ONLY public.favorites DROP CONSTRAINT IF EXISTS fk29j3ltqq5s8huxo1iwiqmwjli;
ALTER TABLE IF EXISTS ONLY public.vacancy_specialties DROP CONSTRAINT IF EXISTS vacancy_specialties_pkey;
ALTER TABLE IF EXISTS ONLY public.vacancy_moderation DROP CONSTRAINT IF EXISTS vacancy_moderation_pkey;
ALTER TABLE IF EXISTS ONLY public.vacancies DROP CONSTRAINT IF EXISTS vacancies_pkey;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS users_pkey;
ALTER TABLE IF EXISTS ONLY public.user_role DROP CONSTRAINT IF EXISTS user_role_pkey;
ALTER TABLE IF EXISTS ONLY public.resumes DROP CONSTRAINT IF EXISTS ukeodtnboldmimesu533jnl3ehi;
ALTER TABLE IF EXISTS ONLY public.specialties DROP CONSTRAINT IF EXISTS uk8hq46e4ylgbhk67ke0u9y52i3;
ALTER TABLE IF EXISTS ONLY public.vacancy_moderation DROP CONSTRAINT IF EXISTS uk7qkx41p2i03amvswrvbfa5b9n;
ALTER TABLE IF EXISTS ONLY public.student_profile DROP CONSTRAINT IF EXISTS student_profile_pkey;
ALTER TABLE IF EXISTS ONLY public.specialties DROP CONSTRAINT IF EXISTS specialties_pkey;
ALTER TABLE IF EXISTS ONLY public.roles DROP CONSTRAINT IF EXISTS roles_pkey;
ALTER TABLE IF EXISTS ONLY public.resumes DROP CONSTRAINT IF EXISTS resumes_pkey;
ALTER TABLE IF EXISTS ONLY public.refresh_token DROP CONSTRAINT IF EXISTS refresh_token_pkey;
ALTER TABLE IF EXISTS ONLY public.notifications DROP CONSTRAINT IF EXISTS notifications_pkey;
ALTER TABLE IF EXISTS ONLY public.roles DROP CONSTRAINT IF EXISTS idxofx66keruapi6vyqpv6f2or37;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS idx6dotkott2kjsp8vw4d0m25fb7;
ALTER TABLE IF EXISTS ONLY public.guide DROP CONSTRAINT IF EXISTS guide_pkey;
ALTER TABLE IF EXISTS ONLY public.files DROP CONSTRAINT IF EXISTS files_pkey;
ALTER TABLE IF EXISTS ONLY public.favorites DROP CONSTRAINT IF EXISTS favorites_pkey;
ALTER TABLE IF EXISTS ONLY public.employer_profile DROP CONSTRAINT IF EXISTS employer_profile_pkey;
ALTER TABLE IF EXISTS ONLY public.company_reviews DROP CONSTRAINT IF EXISTS company_reviews_pkey;
ALTER TABLE IF EXISTS ONLY public.applications DROP CONSTRAINT IF EXISTS applications_pkey;
DROP TABLE IF EXISTS public.vacancy_specialties;
DROP TABLE IF EXISTS public.vacancy_moderation;
DROP TABLE IF EXISTS public.vacancies;
DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS public.user_role;
DROP TABLE IF EXISTS public.student_profile;
DROP TABLE IF EXISTS public.specialties;
DROP TABLE IF EXISTS public.roles;
DROP TABLE IF EXISTS public.resumes;
DROP TABLE IF EXISTS public.refresh_token;
DROP TABLE IF EXISTS public.notifications;
DROP TABLE IF EXISTS public.guide;
DROP TABLE IF EXISTS public.files;
DROP TABLE IF EXISTS public.favorites;
DROP TABLE IF EXISTS public.employer_profile;
DROP TABLE IF EXISTS public.company_reviews;
DROP TABLE IF EXISTS public.applications;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: applications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.applications (
    id uuid NOT NULL,
    cover_letter character varying(255),
    created_at date,
    status character varying(255),
    resume_id uuid,
    student_id uuid,
    vacancy_id uuid
);


ALTER TABLE public.applications OWNER TO postgres;

--
-- Name: company_reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.company_reviews (
    id uuid NOT NULL,
    comment character varying(255),
    created_at date,
    rating smallint,
    employer_id uuid,
    student_id uuid
);


ALTER TABLE public.company_reviews OWNER TO postgres;

--
-- Name: employer_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employer_profile (
    user_id uuid NOT NULL,
    company_name character varying(160),
    description character varying(5000),
    website_link character varying(255),
    logo_file_id uuid
);


ALTER TABLE public.employer_profile OWNER TO postgres;

--
-- Name: favorites; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.favorites (
    id uuid NOT NULL,
    created_at timestamp(6) with time zone,
    student_id uuid,
    vacancy_id uuid
);


ALTER TABLE public.favorites OWNER TO postgres;

--
-- Name: files; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.files (
    id uuid NOT NULL,
    created_at date,
    file_key character varying(255),
    file_name character varying(255),
    file_size integer,
    file_url character varying(255),
    mime_type character varying(255),
    owner_id uuid,
    resume_id uuid
);


ALTER TABLE public.files OWNER TO postgres;

--
-- Name: guide; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guide (
    id uuid NOT NULL,
    content character varying(10000) NOT NULL,
    created_at date NOT NULL,
    title character varying(255) NOT NULL
);


ALTER TABLE public.guide OWNER TO postgres;

--
-- Name: notifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notifications (
    id uuid NOT NULL,
    created_at date,
    is_read boolean,
    "mеssage_type" smallint,
    payload jsonb,
    user_id uuid,
    CONSTRAINT "notifications_mеssage_type_check" CHECK ((("mеssage_type" >= 0) AND ("mеssage_type" <= 1)))
);


ALTER TABLE public.notifications OWNER TO postgres;

--
-- Name: refresh_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refresh_token (
    id uuid NOT NULL,
    email character varying(255),
    expires_at date,
    revoked boolean NOT NULL,
    token character varying(255)
);


ALTER TABLE public.refresh_token OWNER TO postgres;

--
-- Name: resumes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resumes (
    id uuid NOT NULL,
    content text,
    created_at date,
    titel character varying(255),
    updated_at date,
    student_id uuid
);


ALTER TABLE public.resumes OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id uuid NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT roles_name_check CHECK (((name)::text = ANY ((ARRAY['ROLE_STUDENT'::character varying, 'ROLE_EMPLOYER'::character varying, 'ROLE_ADMIN'::character varying])::text[])))
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: specialties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.specialties (
    id uuid NOT NULL,
    code character varying(255),
    is_active boolean NOT NULL,
    name character varying(255)
);


ALTER TABLE public.specialties OWNER TO postgres;

--
-- Name: student_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student_profile (
    user_id uuid NOT NULL,
    full_name character varying(255),
    graduation_year integer,
    avatar_file_id uuid
);


ALTER TABLE public.student_profile OWNER TO postgres;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_at timestamp(6) with time zone,
    deleted_at timestamp(6) with time zone,
    email character varying(50) NOT NULL,
    is_active boolean NOT NULL,
    last_login_at timestamp(6) with time zone,
    password_hash character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: vacancies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vacancies (
    id uuid NOT NULL,
    city character varying(255),
    created_at date,
    deleted_at date,
    description character varying(5000) NOT NULL,
    end_date date,
    start_date date,
    status character varying(255),
    title character varying(255),
    updated_at date,
    employer_id uuid,
    CONSTRAINT vacancies_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'REVIEWED'::character varying, 'ACCEPTED'::character varying, 'REJECTED'::character varying])::text[])))
);


ALTER TABLE public.vacancies OWNER TO postgres;

--
-- Name: vacancy_moderation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vacancy_moderation (
    id uuid NOT NULL,
    comment character varying(255),
    created_at date,
    decision character varying(255),
    admin_id uuid,
    vacancy_id uuid
);


ALTER TABLE public.vacancy_moderation OWNER TO postgres;

--
-- Name: vacancy_specialties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vacancy_specialties (
    vacancy_id uuid NOT NULL,
    specialty_id uuid NOT NULL
);


ALTER TABLE public.vacancy_specialties OWNER TO postgres;

--
-- Data for Name: applications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.applications (id, cover_letter, created_at, status, resume_id, student_id, vacancy_id) FROM stdin;
\.


--
-- Data for Name: company_reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.company_reviews (id, comment, created_at, rating, employer_id, student_id) FROM stdin;
\.


--
-- Data for Name: employer_profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employer_profile (user_id, company_name, description, website_link, logo_file_id) FROM stdin;
\.


--
-- Data for Name: favorites; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.favorites (id, created_at, student_id, vacancy_id) FROM stdin;
\.


--
-- Data for Name: files; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.files (id, created_at, file_key, file_name, file_size, file_url, mime_type, owner_id, resume_id) FROM stdin;
\.


--
-- Data for Name: guide; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.guide (id, content, created_at, title) FROM stdin;
\.


--
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notifications (id, created_at, is_read, "mеssage_type", payload, user_id) FROM stdin;
\.


--
-- Data for Name: refresh_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refresh_token (id, email, expires_at, revoked, token) FROM stdin;
1659b68e-6b36-4483-9f7b-88da344fd654	student6@gmail.com	2026-04-26	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoic3R1ZGVudDZAZ21haWwuY29tIiwiaWF0IjoxNzc3MDYwOTY5LCJleHAiOjE3NzcyMzM3NjksImp0aSI6IjdmZjRhNGJhLWY3MjItNDBhMi1iMjJlLWE5YWZkNzVhNTQ0MiJ9.B0NBDypCm0WZp-TiNb30RsD6AicRWvrYCjImtrlf6u4
c879b917-bad4-41cc-936b-566f64bed407	admin@gmail.com	2026-04-26	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzc3MDYxMDA5LCJleHAiOjE3NzcyMzM4MDksImp0aSI6ImY2N2ZiMmQxLWUxZGUtNDdkYy04NjZlLWRmZGYyNGQ1NzU5YyJ9.zEZpSveKp_Te9EudScX0T_Fw94F7ifTeYVEjlKeysBs
768cac35-a968-49e3-ad64-f7203ffbb236	student@gmail.com	2026-04-28	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoic3R1ZGVudEBnbWFpbC5jb20iLCJpYXQiOjE3NzcyMTgzNDEsImV4cCI6MTc3NzM5MTE0MSwianRpIjoiY2Y1ZDZjZjgtYWNkOS00N2QxLWI4MTEtM2ViMWRhZjgyMjVmIn0.iNUhq6JLrHlv9H_30hWoB4R-OWjioj9KlN5bUQzKFEU
cabf90b6-5934-412d-97dd-5de4c951f375	admin@gmail.com	2026-04-28	f	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJlZGlmZmYuY29tIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzc3MjE4MzYyLCJleHAiOjE3NzczOTExNjIsImp0aSI6ImI5ZDI4YmFlLTkwODAtNDYyMS05ZTBlLTBiNWNkMGExYzgwMCJ9.emere90AXOmgbAN6TorYTe9UsfOihn-vvxlmhOpwNOQ
\.


--
-- Data for Name: resumes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resumes (id, content, created_at, titel, updated_at, student_id) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab	ROLE_STUDENT
90d617cd-3782-468a-96c9-e8aa668e98f5	ROLE_EMPLOYER
2d6aee68-3888-4fb0-8e67-6f1ac7757c61	ROLE_ADMIN
\.


--
-- Data for Name: specialties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.specialties (id, code, is_active, name) FROM stdin;
\.


--
-- Data for Name: student_profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student_profile (user_id, full_name, graduation_year, avatar_file_id) FROM stdin;
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role (user_id, role_id) FROM stdin;
45178324-8cdc-4823-a68a-1dad0a66a7a8	de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab
d42545f7-d2ec-4b22-a724-61afe60f5a70	de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab
d42545f7-d2ec-4b22-a724-61afe60f5a70	2d6aee68-3888-4fb0-8e67-6f1ac7757c61
0502c582-b2e3-49c0-9aa9-43c6c0497b3c	de3c03f6-ae4c-42ca-9f2b-76bc8ca9bfab
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, deleted_at, email, is_active, last_login_at, password_hash) FROM stdin;
45178324-8cdc-4823-a68a-1dad0a66a7a8	2026-04-24 20:02:49.291575+00	\N	student6@gmail.com	t	\N	$2a$10$y2ihvxBsXjX3NZzTnUhLIep5JK97tGHdgntpiZa4c30Eh/NZQpjXW
0502c582-b2e3-49c0-9aa9-43c6c0497b3c	2026-04-26 15:45:41.930496+00	\N	student@gmail.com	t	\N	$2a$10$yfiRLT12ncP0ONulu5dneeKEZUvNFwyyOzvYwKBy77PhzCpp8v/wu
d42545f7-d2ec-4b22-a724-61afe60f5a70	2026-04-24 20:03:29.771318+00	\N	admin@gmail.com	t	2026-04-26 15:46:02.272723+00	$2a$10$.S0J8K/Fo2zY5omF5UlY1.wm/tI6ZgYS3H5vO8qBvRHk.MkpvDPsm
\.


--
-- Data for Name: vacancies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vacancies (id, city, created_at, deleted_at, description, end_date, start_date, status, title, updated_at, employer_id) FROM stdin;
\.


--
-- Data for Name: vacancy_moderation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vacancy_moderation (id, comment, created_at, decision, admin_id, vacancy_id) FROM stdin;
\.


--
-- Data for Name: vacancy_specialties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vacancy_specialties (vacancy_id, specialty_id) FROM stdin;
\.


--
-- Name: applications applications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT applications_pkey PRIMARY KEY (id);


--
-- Name: company_reviews company_reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company_reviews
    ADD CONSTRAINT company_reviews_pkey PRIMARY KEY (id);


--
-- Name: employer_profile employer_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employer_profile
    ADD CONSTRAINT employer_profile_pkey PRIMARY KEY (user_id);


--
-- Name: favorites favorites_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT favorites_pkey PRIMARY KEY (id);


--
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (id);


--
-- Name: guide guide_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guide
    ADD CONSTRAINT guide_pkey PRIMARY KEY (id);


--
-- Name: users idx6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT idx6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: roles idxofx66keruapi6vyqpv6f2or37; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT idxofx66keruapi6vyqpv6f2or37 UNIQUE (name);


--
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- Name: refresh_token refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);


--
-- Name: resumes resumes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resumes
    ADD CONSTRAINT resumes_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: specialties specialties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialties
    ADD CONSTRAINT specialties_pkey PRIMARY KEY (id);


--
-- Name: student_profile student_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student_profile
    ADD CONSTRAINT student_profile_pkey PRIMARY KEY (user_id);


--
-- Name: vacancy_moderation uk7qkx41p2i03amvswrvbfa5b9n; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_moderation
    ADD CONSTRAINT uk7qkx41p2i03amvswrvbfa5b9n UNIQUE (vacancy_id);


--
-- Name: specialties uk8hq46e4ylgbhk67ke0u9y52i3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialties
    ADD CONSTRAINT uk8hq46e4ylgbhk67ke0u9y52i3 UNIQUE (code);


--
-- Name: resumes ukeodtnboldmimesu533jnl3ehi; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resumes
    ADD CONSTRAINT ukeodtnboldmimesu533jnl3ehi UNIQUE (student_id);


--
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vacancies vacancies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancies
    ADD CONSTRAINT vacancies_pkey PRIMARY KEY (id);


--
-- Name: vacancy_moderation vacancy_moderation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_moderation
    ADD CONSTRAINT vacancy_moderation_pkey PRIMARY KEY (id);


--
-- Name: vacancy_specialties vacancy_specialties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_specialties
    ADD CONSTRAINT vacancy_specialties_pkey PRIMARY KEY (vacancy_id, specialty_id);


--
-- Name: favorites fk29j3ltqq5s8huxo1iwiqmwjli; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fk29j3ltqq5s8huxo1iwiqmwjli FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);


--
-- Name: resumes fk2ae98pqgb982e1kdj4lvctash; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resumes
    ADD CONSTRAINT fk2ae98pqgb982e1kdj4lvctash FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);


--
-- Name: student_profile fk365nmvykqt2lwfskcd2p8hy2q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student_profile
    ADD CONSTRAINT fk365nmvykqt2lwfskcd2p8hy2q FOREIGN KEY (avatar_file_id) REFERENCES public.files(id);


--
-- Name: vacancy_moderation fk3i4if3ot67nahtk0i36tbyxfb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_moderation
    ADD CONSTRAINT fk3i4if3ot67nahtk0i36tbyxfb FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);


--
-- Name: vacancies fk4lwg9jmmgy0a25q3iaie0wvfd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancies
    ADD CONSTRAINT fk4lwg9jmmgy0a25q3iaie0wvfd FOREIGN KEY (employer_id) REFERENCES public.employer_profile(user_id);


--
-- Name: favorites fk76n3v0v6w4lw3egbx2hr0xxpq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fk76n3v0v6w4lw3egbx2hr0xxpq FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);


--
-- Name: applications fk94iy6oijc6sl7apkp6cfutcxf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fk94iy6oijc6sl7apkp6cfutcxf FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);


--
-- Name: notifications fk9y21adhxn0ayjhfocscqox7bh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT fk9y21adhxn0ayjhfocscqox7bh FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: applications fkbhnfs5wwy14wdm2f1wam8qkhf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fkbhnfs5wwy14wdm2f1wam8qkhf FOREIGN KEY (resume_id) REFERENCES public.resumes(id);


--
-- Name: vacancy_moderation fkd6nsg1b4onfir06kt0tgumdlm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_moderation
    ADD CONSTRAINT fkd6nsg1b4onfir06kt0tgumdlm FOREIGN KEY (admin_id) REFERENCES public.users(id);


--
-- Name: vacancy_specialties fkean5cugw18skfkqogxx32gaou; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_specialties
    ADD CONSTRAINT fkean5cugw18skfkqogxx32gaou FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);


--
-- Name: employer_profile fkgtg25lwq63c2ye2nq2k5wepda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employer_profile
    ADD CONSTRAINT fkgtg25lwq63c2ye2nq2k5wepda FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: student_profile fkh6555c9k0gv0yddac6llslk3t; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student_profile
    ADD CONSTRAINT fkh6555c9k0gv0yddac6llslk3t FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: company_reviews fki8j096fywp18p9ljidhgriqj1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company_reviews
    ADD CONSTRAINT fki8j096fywp18p9ljidhgriqj1 FOREIGN KEY (student_id) REFERENCES public.student_profile(user_id);


--
-- Name: user_role fkj345gk1bovqvfame88rcx7yyx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: files fkkns2bg3o1ii7m8rjqvequuu0h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT fkkns2bg3o1ii7m8rjqvequuu0h FOREIGN KEY (resume_id) REFERENCES public.resumes(id);


--
-- Name: employer_profile fkm4bswyvitxwjye3uwgs45tue4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employer_profile
    ADD CONSTRAINT fkm4bswyvitxwjye3uwgs45tue4 FOREIGN KEY (logo_file_id) REFERENCES public.files(id);


--
-- Name: files fkndbd0r86rsovslrthjrgl960x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT fkndbd0r86rsovslrthjrgl960x FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- Name: company_reviews fknvrjqxe02esvjw5r0alae58at; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company_reviews
    ADD CONSTRAINT fknvrjqxe02esvjw5r0alae58at FOREIGN KEY (employer_id) REFERENCES public.employer_profile(user_id);


--
-- Name: applications fkq2axowr8ewvl0ncquhtq8od8j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.applications
    ADD CONSTRAINT fkq2axowr8ewvl0ncquhtq8od8j FOREIGN KEY (vacancy_id) REFERENCES public.vacancies(id);


--
-- Name: vacancy_specialties fkrib825tpg4rv1vdkl07o15ffa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy_specialties
    ADD CONSTRAINT fkrib825tpg4rv1vdkl07o15ffa FOREIGN KEY (specialty_id) REFERENCES public.specialties(id);


--
-- Name: user_role fkt7e7djp752sqn6w22i6ocqy6q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkt7e7djp752sqn6w22i6ocqy6q FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- PostgreSQL database dump complete
--

\unrestrict peAQtSFNkffcyd11PBHnOFNY01bVy1o3bqQ85d2ax86VHJaI1QT668CStKFVhEX

