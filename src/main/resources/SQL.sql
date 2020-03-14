CREATE TABLE `db_middleware`.`red_record`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `red_packet` VARCHAR(255) NOT NULL COMMENT '红包全局唯一标识',
  `total` INT(11) NOT NULL COMMENT '人数',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '总金额（单位：分）',
  `is_active` TINYINT(4) DEFAULT 1,
  `create_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci
COMMENT='发红包记录表';

CREATE TABLE `db_middleware`.`red_detail`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `record_id` INT(11) NOT NULL COMMENT '红包记录ID',
  `amount` DECIMAL(8,2) NOT NULL COMMENT '金额（单位：分）',
  `is_active` TINYINT(4) DEFAULT 1,
  `create_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci
COMMENT='红白明细金额';

CREATE TABLE `db_middleware`.`red_rob_record`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户账号',
  `red_packet` VARCHAR(255) NOT NULL COMMENT '红包标识串',
  `rob_time` DATETIME NOT NULL COMMENT '时间',
  `is_active` TINYINT(4) DEFAULT 1,
  PRIMARY KEY (`id`)
)
COMMENT='抢红包记录表';
