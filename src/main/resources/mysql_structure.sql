CREATE TABLE `user`
(
    `user_id`             varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
    `password`            varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
    `first_name`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `last_name`           varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `is_customer_manager` bit(1)                                  NOT NULL DEFAULT b'0',
    `is_end_user`         bit(1)                                  NOT NULL DEFAULT b'1',
    `registered_to_cs`    bit(1)                                  NOT NULL DEFAULT b'0',
    `enabled`             bit(1)                                  NOT NULL DEFAULT b'1',
    `expired`             bit(1)                                  NOT NULL DEFAULT b'0',
    `locked`              bit(1)                                  NOT NULL DEFAULT b'0',
    `credentials_expired` bit(1)                                  NOT NULL DEFAULT b'0',
    `modified`            datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created`             datetime                                         DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;