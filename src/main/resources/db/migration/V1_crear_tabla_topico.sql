CREATE TABLE Topico (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        título VARCHAR(100) NOT NULL,
                        mensaje VARCHAR(500) NOT NULL,
                        fechaDeCreación TIMESTAMP NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        autor VARCHAR(100) NOT NULL,
                        curso VARCHAR(100) NOT NULL
);