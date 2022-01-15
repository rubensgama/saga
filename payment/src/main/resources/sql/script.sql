CREATE SCHEMA "payment";

CREATE TABLE payment.PAYMENT(
	idPayment int IDENTITY(1,1) NOT NULL,
	idOrder int NOT NULL,
	status char(1) NOT NULL,
	value NUMERIC(8,2) NOT NULL,
	CONSTRAINT PAYMENT_pk PRIMARY KEY (idPayment)
);

CREATE TABLE payment.MESSAGE_RELAY(
	idMessage int IDENTITY(1,1) NOT NULL,
	idSaga VARCHAR(100) NOT NULL,
	name varchar(255) NOT NULL,
	time DATETIME NOT NULL,
	data TEXT NOT NULL,
	status VARCHAR(100) NOT NULL,
	saga VARCHAR(255) NOT NULL,
	CONSTRAINT MESSAGE_RELAY_pk PRIMARY KEY (idMessage)
);