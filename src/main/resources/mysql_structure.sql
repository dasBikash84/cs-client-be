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

  CREATE TABLE `rest_activity_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_header` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `exception_class_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `method_signature` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remote_host` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `request_method` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `requesturl` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `time_taken_ms` int(11) NOT NULL,
  `user_agent_header` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18436 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `error_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stack_trace` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception_class` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_ip_address` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;