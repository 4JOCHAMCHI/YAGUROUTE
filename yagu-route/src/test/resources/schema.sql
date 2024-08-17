DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS gameseat;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS teamrank;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS seat;
DROP TABLE IF EXISTS stadium;

CREATE TABLE stadium (
                         stadium_id INT PRIMARY KEY AUTO_INCREMENT,
                         stadium_name VARCHAR(255) NOT NULL,
                         capacity INT NOT NULL,
                         location VARCHAR(255) NOT NULL,
                         seat_price INT NOT NULL
);

CREATE TABLE seat (
                      seat_id INT PRIMARY KEY AUTO_INCREMENT,
                      stadium_id INT NOT NULL,
                      seat_col INT NOT NULL,
                      seat_row INT NOT NULL ,
                      seat_num INT NOT NULL,
                      seat_status BOOLEAN DEFAULT 1 NOT NULL,
                      CONSTRAINT fk_stadium_id_seat FOREIGN KEY (`stadium_id`) REFERENCES `stadium`(`stadium_id`)
);

CREATE TABLE `team` (
                        `team_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        `team_name` VARCHAR(50) NOT NULL,
                        `logo` VARCHAR(255) NOT NULL,
                        `stadium_id` INT NOT NULL,
                        CONSTRAINT fk_stadium_id_team FOREIGN KEY (`stadium_id`) REFERENCES `stadium`(`stadium_id`)
);

CREATE TABLE `teamrank` (
                            `team_rank_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            `team_rank` INT NOT NULL,
                            `team_id` INT NOT NULL,
                            `games_behind` DECIMAL(4,2) NOT NULL,
                            CONSTRAINT fk_team_id FOREIGN KEY (`team_id`) REFERENCES `team`(`team_id`)
);

CREATE TABLE `game` (
                        `game_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        `game_date` DATE NOT NULL,
                        `game_time` TIME NOT NULL,
                        `home_team_id` INT NOT NULL,
                        `away_team_id` INT NOT NULL,
                        `sellable` ENUM('S','E','C') NOT NULL,
                        CONSTRAINT fk_home_team_id FOREIGN KEY (`home_team_id`) REFERENCES `team`(`team_id`),
                        CONSTRAINT fk_away_team_id FOREIGN KEY (`away_team_id`) REFERENCES `team`(`team_id`)
);

CREATE TABLE `gameseat` (
                            `game_seat_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            `seat_id` INT NOT NULL,
                            `game_id` INT NOT NULL,
                            `game_seat_price` INT NOT NULL,
                            `occupied` BOOLEAN DEFAULT 0 NOT NULL,
                            CONSTRAINT fk_seat_id FOREIGN KEY (`seat_id`) REFERENCES `seat`(`seat_id`),
                            CONSTRAINT fk_game_id FOREIGN KEY (`game_id`) REFERENCES `game`(`game_id`)
);

CREATE TABLE `member` (
                          `member_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          `member_name` VARCHAR(20) NOT NULL,
                          `member_email` VARCHAR(255) NOT NULL,
                          `member_password` VARCHAR(255) NOT NULL,
                          `member_phone` VARCHAR(15) NOT NULL
);

CREATE TABLE `ticket` (
                          `ticket_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          `member_id` INT NOT NULL,
                          `ticket_price` INT NOT NULL,
                          `game_seat_id` INT NOT NULL,
                          `ticket_date` TIMESTAMP NOT NULL,
                          CONSTRAINT fk_member_id FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`),
                          CONSTRAINT fk_game_seat_id FOREIGN KEY (`game_seat_id`) REFERENCES `gameseat`(`game_seat_id`)
);