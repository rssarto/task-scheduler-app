--CREATE TABLE IF NOT EXISTS MODEL
--(
--    id UUID NOT NULL,
--    name varchar(255) NOT NULL,
--    PRIMARY KEY (id)
--);
--
CREATE TABLE public.execution_schedule
(
    id uuid NOT NULL,
    execution_type character varying(255) NOT NULL,
    next_execution_date timestamp without time zone NOT NULL,
    status character varying(255) NOT NULL,
    target_class character varying(255) COLLATE pg_catalog."default" NOT NULL,
    target_id uuid NOT NULL,
    CONSTRAINT execution_schedule_pkey PRIMARY KEY (id),
    CONSTRAINT uk_g3cuckighv6iiq4n9x6tff2e8 UNIQUE (target_id)
);

CREATE TABLE public.execution_history
(
    id uuid NOT NULL,
    duration bigint NOT NULL,
    status character varying(255) NOT NULL,
    execution_schedule_id uuid,
    executionDate timestamp without time zone NOT NULL,
    CONSTRAINT execution_history_pkey PRIMARY KEY (id),
    CONSTRAINT fkhtgv8263x6coiauu9k2y6pt90 FOREIGN KEY (execution_schedule_id)
        REFERENCES public.execution_schedule (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.watchlist
(
    id uuid NOT NULL,
    execution_interval integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    schedule_type character varying(255) COLLATE pg_catalog."default",
    execution_schedule_id uuid NOT NULL,
    CONSTRAINT watchlist_pkey PRIMARY KEY (id),
    CONSTRAINT uk_2xjfa699ind0t1cj5bol4ovgm UNIQUE (execution_schedule_id),
    CONSTRAINT fk9cx3e5oko9tp35uhvjx7r3pwx FOREIGN KEY (execution_schedule_id)
        REFERENCES public.execution_schedule (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);