INSERT INTO public.users(
	user_id, password, role, user_email, user_name,created_by, created_date, updated_by, updated_date)
	VALUES (1, '$2a$12$PgnnDcfyWLeoSMOTrqywNOx6QG8wRnGf0GMxqubBbnbAEWu2rWxsa', 'ADMIN', 'satya@gmail.com', 'satya',1,now(),1,now());