CREATE SCHEMA order_process;

--drop table order_process.ORDER_PROC_DETAIL;
--drop table order_process.ORDER_PROC;
--drop table order_process.MESSAGE_RELAY;

CREATE TABLE order_process.ORDER_PROC(
	idOrder int IDENTITY(1,1) NOT NULL,
	name varchar(255) NOT NULL,
	value NUMERIC(8,2) NOT NULL,
	CONSTRAINT ORDER_pk PRIMARY KEY (idOrder)
);

CREATE TABLE order_process.ORDER_PROC_DETAIL(
	idOrderDetail int IDENTITY(1,1) NOT NULL,
	item varchar(255) NOT NULL,
	qtd NUMERIC(8,2) NOT NULL,
	value NUMERIC(8,2) NOT NULL,
	idOrder int NOT NULL
);
ALTER TABLE order_process.ORDER_PROC_DETAIL ADD CONSTRAINT ORDER_DETAIL_fk FOREIGN KEY (idOrder) REFERENCES order_process.ORDER_PROC(idOrder);

CREATE TABLE order_process.MESSAGE_RELAY(
	idMessage int IDENTITY(1,1) NOT NULL,
	idSaga VARCHAR(100) NOT NULL,
	name varchar(255) NOT NULL,
	time DATETIME NOT NULL,
	data TEXT NOT NULL,
	status VARCHAR(100) NOT NULL,
	saga VARCHAR(255) NOT NULL,
	CONSTRAINT MESSAGE_RELAY_pk PRIMARY KEY (idMessage)
);