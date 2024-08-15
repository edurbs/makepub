--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-14 23:41:28

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 227 (class 1259 OID 17660)
-- Name: markup; Type: TABLE; Schema: public; Owner: makepub
--

CREATE TABLE public.markup (
    id character varying(255) NOT NULL,
    html_start text,
    html_end text,
    is_paragraph boolean NOT NULL,
    is_footnote_symbol boolean NOT NULL,
    is_footnote_text boolean NOT NULL,
    version integer NOT NULL,
    created_by character varying(255),
    created_date timestamp with time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp with time zone,
    description character varying(255),
    is_question boolean NOT NULL
);


ALTER TABLE public.markup OWNER TO makepub;

--
-- TOC entry 4869 (class 0 OID 17660)
-- Dependencies: 227
-- Data for Name: markup; Type: TABLE DATA; Schema: public; Owner: makepub
--

COPY public.markup (id, html_start, html_end, is_paragraph, is_footnote_symbol, is_footnote_text, version, created_by, created_date, last_modified_by, last_modified_date, description, is_question) FROM stdin;
]	</body> </html>	\N	t	f	f	2	admin	2024-07-20 15:12:27.221-03	admin	2024-07-20 15:23:20.27-03	Fim do documento	f
[	<?xml version="1.0" encoding="utf-8"?> <!DOCTYPE html> <html dir="ltr" class="dir-ltr ml-E ms-ROMAN" xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops" xml:lang="en"> <head> <meta charset="utf-8" /> <link rel="stylesheet" href="../Styles/sgc-nav.css" type="text/css" /> <script>     function saveAnnotation(textAreaId) {       var annotation = document.getElementById(textAreaId).value;       localStorage.setItem(textAreaId, annotation);     }     window.onload = function() {       // identificar parágrafos       var textAreas = document.getElementsByTagName('textarea');       for (var i = 0; i &lt; textAreas.length; i++) {         var textAreaId = textAreas[i].id;         var savedAnnotation = localStorage.getItem(textAreaId);         if (savedAnnotation) {           document.getElementById(textAreaId).value = savedAnnotation;         }         document.getElementById(textAreaId).addEventListener('input', function() {           saveAnnotation(this.id);         });       }     };   </script> </head> <body dir="ltr" xml:lang="en" class="jwac dir-ltr ml-E ms-ROMAN docClass-40 pub-w docId-2024404">	\N	t	f	f	3	admin	2024-07-20 15:12:08.843-03	admin	2024-08-14 23:13:59.217-03	Início do documento	f
%	<h1 class="du-color--coolGray-700 du-margin-top--8" id="p3" data-pid="3"><strong>	</strong></h1>	t	f	f	2	admin	2024-07-20 11:56:29.827-03	admin	2024-08-14 23:20:14.153-03	Título principal	f
$	<div id="tt7" class="du-color--textSubdued du-margin-vertical--8"> <p id="p4" data-pid="4" class="themeScrp"><em>	</em></p></div>	t	f	f	3	admin	2024-07-20 11:57:08.933-03	admin	2024-08-14 23:21:55.4-03	Texto temático	f
{	<div id="tt9" class="du-color--textSubdued du-borderStyle-inlineStart--solid du-borderWidth--2 du-borderColor--orange-700 du-margin-vertical--8 du-margin-children--0 du-padding-inlineStart--4 du-padding-vertical--1">	\N	t	f	f	7	admin	2024-07-20 11:59:38.541-03	admin	2024-08-14 23:28:11.408-03	Início do objetivo	f
}	</div>	\N	t	f	f	5	admin	2024-07-20 11:59:58.344-03	admin	2024-08-14 23:28:32.75-03	Fim do objetivo	f
=	<p class="qu">	</p>	t	f	f	4	admin	2024-07-20 11:34:34.476-03	admin	2024-08-14 23:29:45.166-03	Pergunta	t
@	<h2 class="du-color--coolGray-700 du-textAlign--center">	</h2>	t	f	f	3	admin	2024-07-20 11:34:10.631-03	admin	2024-08-14 23:33:13.994-03	Subtítulo	f
#	<div id="tt4" class="du-color--textSubdued du-margin-vertical--8"> <p class="pubRefs">	</p> </div>	t	f	f	4	admin	2024-07-20 13:25:32.514-03	admin	2024-08-14 23:33:40.754-03	Título cântico	f
_	<i>	</i>	f	f	f	2	admin	2024-07-20 11:31:54.715-03	admin	2024-07-20 11:47:09.962-03	Itálico	f
~	<b>	</b>	f	f	f	2	admin	2024-07-20 11:32:13.968-03	admin	2024-07-20 11:47:17.276-03	Negrito	f
*	<b><sup><a epub:type="noteref" href="#{idFootNote}">*</a></sup></b>	\N	f	t	f	2	admin	2024-07-20 11:32:36.48-03	admin	2024-07-20 11:47:38.692-03	Símbolo da nota de rodapé	f
§	<p>	</p>	t	f	f	3	admin	2024-07-20 11:35:40.11-03	admin	2024-07-20 11:51:14.346-03	Parágrafo normal	f
£	<aside id="{idFootNote}" epub:type="footnote"><p>	</p></aside>	t	f	t	2	admin	2024-07-20 11:33:05.368-03	admin	2024-07-20 11:51:42.369-03	Texto da nota de rodapé	f
!	<div id="tt2" class="dc-bleedToArticleEdge dc-paddingToContentEdge du-bgColor--orange-700 du-padding-vertical--2"> <p class="contextTtl du-color--white"><strong>	</strong></p> </div>	t	f	f	4	admin	2024-07-20 15:08:20.905-03	admin	2024-08-14 23:34:26.921-03	Número do estudo	f
¬	<hr></hr>	\N	t	f	f	1	admin	2024-07-20 12:39:47.368-03	\N	2024-07-20 12:39:47.368-03	linha divisória	f
|	<figure><figcaption class="figcaption"><p>	</p></figcaption> </figure>	t	f	f	2	admin	2024-07-20 13:22:21.876-03	admin	2024-08-14 23:37:07.847-03	Descrição da imagem ou texto destacado	f
\.


--
-- TOC entry 4723 (class 2606 OID 17668)
-- Name: markup idx_markup_unq; Type: CONSTRAINT; Schema: public; Owner: makepub
--

ALTER TABLE ONLY public.markup
    ADD CONSTRAINT idx_markup_unq UNIQUE (id);


--
-- TOC entry 4725 (class 2606 OID 17666)
-- Name: markup pk_markup; Type: CONSTRAINT; Schema: public; Owner: makepub
--

ALTER TABLE ONLY public.markup
    ADD CONSTRAINT pk_markup PRIMARY KEY (id);


-- Completed on 2024-08-14 23:41:28

--
-- PostgreSQL database dump complete
--

