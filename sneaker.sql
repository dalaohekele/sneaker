/*
 Navicat Premium Data Transfer

 Source Server         : goproject
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost
 Source Database       : sneaker

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : utf-8

 Date: 09/02/2018 16:35:36 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tt_order_address`
-- ----------------------------
DROP TABLE IF EXISTS `tt_order_address`;
CREATE TABLE `tt_order_address` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `buyer_name` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人名称',
  `user_id` int(12) NOT NULL COMMENT '用户id',
  `adress` varchar(128) NOT NULL COMMENT '收货地址',
  `phone` varchar(16) NOT NULL COMMENT '号码',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- ----------------------------
--  Table structure for `tt_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `tt_order_detail`;
CREATE TABLE `tt_order_detail` (
  `detail_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `product_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `product_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '当前价格,单位分',
  `product_quantity` int(11) NOT NULL COMMENT '数量',
  `product_icon` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '小图',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `tt_order_master`
-- ----------------------------
DROP TABLE IF EXISTS `tt_order_master`;
CREATE TABLE `tt_order_master` (
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `buyer_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '买家id',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `tt_product_category`
-- ----------------------------
DROP TABLE IF EXISTS `tt_product_category`;
CREATE TABLE `tt_product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uqe_category_type` (`category_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `tt_product_img`
-- ----------------------------
DROP TABLE IF EXISTS `tt_product_img`;
CREATE TABLE `tt_product_img` (
  `img_id` int(16) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(128) COLLATE utf8_bin NOT NULL,
  `img_desc` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '图片描述',
  `product_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`img_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `tt_product_info`
-- ----------------------------
DROP TABLE IF EXISTS `tt_product_info`;
CREATE TABLE `tt_product_info` (
  `product_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `product_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '小图',
  `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
  `product_show` int(2) DEFAULT '0',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `tt_user`
-- ----------------------------
DROP TABLE IF EXISTS `tt_user`;
CREATE TABLE `tt_user` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `user_name` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '昵称',
  `password` varchar(32) COLLATE utf8_bin DEFAULT '' COMMENT '密码',
  `role` tinyint(1) NOT NULL COMMENT '1买家 2管理员',
  `wx_openid` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'wxopenid',
  `head_image` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '头像',
  `login_count` int(11) DEFAULT NULL COMMENT '登陆次数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
