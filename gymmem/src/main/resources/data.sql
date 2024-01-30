-- DROP SCHEMA gym_schema;

CREATE SCHEMA gym_schema;

-- gym_schema.otp definition

-- Drop table

-- DROP TABLE gym_schema.otp;

CREATE TABLE gym_schema.otp (
	id varchar(255) NOT NULL,
	created_at timestamptz(6) NULL,
	expired_at timestamptz(6) NOT NULL,
	"key" varchar(255) NULL,
	"type" varchar(255) NOT NULL,
	updated_at timestamptz(6) NULL,
	value varchar(255) NULL,
	CONSTRAINT otp_pkey PRIMARY KEY (id),
	CONSTRAINT otp_type_check CHECK (((type)::text = ANY ((ARRAY['VERIFY_EMAIL'::character varying, 'PAYMENT'::character varying, 'RESET_PASSWORD'::character varying])::text[]))),
	CONSTRAINT uk_mmjjkyqjxrbhgs2l18f5anga1 UNIQUE (key)
);


-- gym_schema.product definition

-- Drop table

-- DROP TABLE gym_schema.product;

CREATE TABLE gym_schema.product (
	id varchar(255) NOT NULL,
	created_at timestamptz(6) NULL,
	description varchar(255) NULL,
	minutes_of_duration int2 NULL,
	price numeric(38, 2) NOT NULL,
	schedule_days_of_week varchar(14) NULL,
	times_of_meeting int4 NULL,
	updated_at timestamptz(6) NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id)
);


-- gym_schema.transaction_history definition

-- Drop table

-- DROP TABLE gym_schema.transaction_history;

CREATE TABLE gym_schema.transaction_history (
	id varchar(255) NOT NULL,
	amount numeric(38, 2) NOT NULL,
	credit_cart_id varchar(255) NOT NULL,
	status varchar(255) NULL,
	subscription_id varchar(255) NOT NULL,
	user_id varchar(255) NOT NULL,
	CONSTRAINT transaction_history_pkey PRIMARY KEY (id),
	CONSTRAINT transaction_history_status_check CHECK (((status)::text = ANY ((ARRAY['PROCESSING'::character varying, 'PAID'::character varying, 'FAILED'::character varying])::text[])))
);


-- gym_schema."user" definition

-- Drop table

-- DROP TABLE gym_schema."user";

CREATE TABLE gym_schema."user" (
	id varchar(255) NOT NULL,
	created_at timestamptz(6) NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NOT NULL,
	phone_number varchar(255) NULL,
	status varchar(255) NOT NULL,
	updated_at timestamptz(6) NULL,
	CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email),
	CONSTRAINT user_pkey PRIMARY KEY (id),
	CONSTRAINT user_status_check CHECK (((status)::text = ANY ((ARRAY['VERIFIED'::character varying, 'UNVERIFIED'::character varying])::text[])))
);


-- gym_schema.credit_card definition

-- Drop table

-- DROP TABLE gym_schema.credit_card;

CREATE TABLE gym_schema.credit_card (
	id varchar(255) NOT NULL,
	created_at timestamptz(6) NULL,
	"encrypted" varchar(255) NULL,
	updated_at timestamptz(6) NULL,
	user_id varchar(255) NULL,
	CONSTRAINT credit_card_pkey PRIMARY KEY (id),
	CONSTRAINT fkh4wi9724muee2kp2c4ku1yia2 FOREIGN KEY (user_id) REFERENCES gym_schema."user"(id)
);


-- gym_schema."subscription" definition

-- Drop table

-- DROP TABLE gym_schema."subscription";

CREATE TABLE gym_schema."subscription" (
	id varchar(255) NOT NULL,
	amount numeric(38, 2) NOT NULL,
	created_at timestamptz(6) NULL,
	remaining_times_of_meeting int4 NULL,
	status varchar(255) NULL,
	updated_at timestamptz(6) NULL,
	user_id varchar(255) NOT NULL,
	product_id varchar(255) NOT NULL,
	CONSTRAINT subscription_pkey PRIMARY KEY (id),
	CONSTRAINT subscription_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'ACTIVE'::character varying, 'INACTIVE'::character varying])::text[]))),
	CONSTRAINT ukoyqlmqkxsbr5nwyfyoow9d0o3 UNIQUE (user_id, product_id),
	CONSTRAINT fkg40gg635cui0m07vh433dri4x FOREIGN KEY (product_id) REFERENCES gym_schema.product(id)
);


-- gym_schema."token" definition

-- Drop table

-- DROP TABLE gym_schema."token";

CREATE TABLE gym_schema."token" (
	id varchar(255) NOT NULL,
	created_at timestamptz(6) NULL,
	encrypted_access_token text NULL,
	refresh_token text NULL,
	updated_at timestamptz(6) NULL,
	user_id varchar(255) NOT NULL,
	CONSTRAINT token_pkey PRIMARY KEY (id),
	CONSTRAINT uk_15gxur5anhs9nlx62ef6cx3sk UNIQUE (refresh_token),
	CONSTRAINT uk_g7im3j7f0g31yhl6qco2iboy5 UNIQUE (user_id),
	CONSTRAINT fke32ek7ixanakfqsdaokm4q9y2 FOREIGN KEY (user_id) REFERENCES gym_schema."user"(id)
);

INSERT INTO gym_schema.product
(id, created_at, description, minutes_of_duration, price, schedule_days_of_week, times_of_meeting, updated_at)
VALUES('da44debc-c72c-4b77-ac4b-6424bed81f17', '2024-01-29 02:25:47.770', 'Overweight', 60, 3000000.00, '1,2,3', 10, NULL);
INSERT INTO gym_schema.product
(id, created_at, description, minutes_of_duration, price, schedule_days_of_week, times_of_meeting, updated_at)
VALUES('cb55debc-c72c-4b77-ac4b-6424bed81f11', '2024-01-29 02:26:31.673', 'Gainmass', 40, 2000000.00, '3,4,5', 8, NULL);

