INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`) VALUES ('fanAdmin', '1', '', '3', '', 'sys@sys', TRUE, '', 'sys', '', '', 'sys');
INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`, `points`) VALUES ('registered', '2', 'Mihajla Pupina 3', '0', 'default-avatar.jpg', 'stefkic.jr@gmail.com', TRUE, 'Pera', 'pera', 'Peric', '123', 'pera', '1');
INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`, `points`) VALUES ('registered', '3', 'Dvorska 7', '0', 'default-avatar.jpg', 'stefkic.jr@gmail.com', TRUE, 'Nemanja', 'nemanja', 'Ivetic', '456', 'nemanja', '1');
INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`, `points`) VALUES ('registered', '4', 'Igmanska 73', '0', 'default-avatar.jpg', 'stefkic.jr@gmail.com', TRUE, 'Igor', 'a', 'Jovanovic', '789', 'a', '1');
INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`, `points`) VALUES ('registered', '9999', 'dummy', '0', 'default-avatar.jpg', 'dummy@gmail.com', FALSE, 'dummy', 'dummy', 'dummy', '7789', 'dummy', '1');

INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`) VALUES ('fanAdmin', '6', 'Prizrenska 34', '2', 'default-avatar.jpg', 'brankodragicevic', TRUE, 'Branko', 'branko', 'Dragicevic', '741', 'branko');
INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`) VALUES ('fanAdmin', '7', 'Tolstojeva19', '2', 'default-avatar.jpg', 'jovanapavlovic@gmail.com', TRUE, 'Jovana', 'j', 'Pavlovic', '586', 'j');

INSERT INTO `facility` (`type`, `id`, `address`, `description`, `name`) VALUES ('cinema', '1', 'Centar ', 'Moderan bioskop', 'Cineplex');
INSERT INTO `facility` (`type`, `id`, `address`, `description`, `name`) VALUES ('theater', '2', 'Crveni Trg 5', 'Moderno pozoriste', 'Arena');


INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`, `facility_id`) VALUES ('catAdmin', '5', 'Sremska 43', '1', 'default-avatar.jpg', 'jelanaradic@gmail.com', TRUE, 'Jelana', 'jelena', 'Radic', '853', 'jelena', '1');
INSERT INTO `user` (`type`, `id`, `address`, `authorities`, `avatar`, `email`, `first_login`, `name`, `password`, `surname`, `telephone`, `username`, `facility_id`) VALUES ('catAdmin', '8', 'Rankoviceva ', '1', 'default-avatar.jpg', 'milosradovic@gmail.com', TRUE, 'Milos', 'm', 'Radovic', '274', 'm', '2');

INSERT INTO `viewing_rooms` (`id`, `name`, `facility_id`) VALUES ('1', 'G7', '1');
INSERT INTO `viewing_rooms` (`id`, `name`, `facility_id`) VALUES ('2', 'G8', '1');
INSERT INTO `viewing_rooms` (`id`, `name`, `facility_id`) VALUES ('3', 'F1', '2');
INSERT INTO `viewing_rooms` (`id`, `name`, `facility_id`) VALUES ('4', 'F2', '2');

INSERT INTO `points_scale` (`id`, `facility_id`) VALUES ('1', '1');
INSERT INTO `points_scale` (`id`, `facility_id`) VALUES ('2', '2');

INSERT INTO `user_category` (`id`, `discount`, `name`, `points`, `points_scale_id`) VALUES ('1', '30.00', '0', '30', '1');
INSERT INTO `user_category` (`id`, `discount`, `name`, `points`, `points_scale_id`) VALUES ('2', '20.00', '1', '20', '1');
INSERT INTO `user_category` (`id`, `discount`, `name`, `points`, `points_scale_id`) VALUES ('3', '10.00', '2', '10', '1');
INSERT INTO `user_category` (`id`, `discount`, `name`, `points`, `points_scale_id`) VALUES ('4', '30.00', '0', '30', '2');
INSERT INTO `user_category` (`id`, `discount`, `name`, `points`, `points_scale_id`) VALUES ('5', '20.00', '1', '20', '2');
INSERT INTO `user_category` (`id`, `discount`, `name`, `points`, `points_scale_id`) VALUES ('6', '10.00', '2', '10', '2');

INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('1', '1', '1', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('2', '1', '2', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('3', '1', '3', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('4', '1', '4', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('5', '2', '1', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('6', '2', '2', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('7', '2', '3', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('8', '2', '4', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('9', '3', '1', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('10', '3', '2', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('11', '3', '3', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('12', '3', '4', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('13', '4', '1', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('14', '4', '2', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('15', '4', '3', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('16', '4', '4', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('17', '5', '1', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('18', '5', '2', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('19', '5', '3', '2', '1');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('20', '5', '4', '2', '1');

INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('21', '1', '1', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('22', '1', '2', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('23', '1', '3', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('24', '1', '4', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('25', '2', '1', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('26', '2', '2', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('27', '2', '3', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('28', '2', '4', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('29', '3', '1', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('30', '3', '2', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('31', '3', '3', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('32', '3', '4', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('33', '4', '1', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('34', '4', '2', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('35', '4', '3', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('36', '4', '4', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('37', '5', '1', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('38', '5', '2', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('39', '5', '3', '2', '2');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('40', '5', '4', '2', '2');


INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('41', '1', '1', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('42', '1', '2', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('43', '1', '3', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('44', '1', '4', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('45', '2', '1', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('46', '2', '2', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('47', '2', '3', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('48', '2', '4', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('49', '3', '1', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('50', '3', '2', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('51', '3', '3', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('52', '3', '4', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('53', '4', '1', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('54', '4', '2', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('55', '4', '3', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('56', '4', '4', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('57', '5', '1', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('58', '5', '2', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('59', '5', '3', '2', '3');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('60', '5', '4', '2', '3');


INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('61', '1', '1', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('62', '1', '2', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('63', '1', '3', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('64', '1', '4', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('65', '2', '1', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('66', '2', '2', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('67', '2', '3', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('68', '2', '4', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('69', '3', '1', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('70', '3', '2', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('71', '3', '3', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('72', '3', '4', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('73', '4', '1', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('74', '4', '2', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('75', '4', '3', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('76', '4', '4', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('77', '5', '1', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('78', '5', '2', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('79', '5', '3', '2', '4');
INSERT INTO `seat` (`id`, `seat_column`, `seat_row`, `segment`, `viewing_room_id`) VALUES ('80', '5', '4', '2', '4');

INSERT INTO `props` (`id`, `active`, `description`, `image`, `reserved`, `facility_id`) VALUES ('1', TRUE, 'Batman Key chains', 'img-1.jpg', TRUE, '1');
INSERT INTO `props` (`id`, `active`, `description`, `image`, `reserved`, `facility_id`) VALUES ('2', TRUE, 'Spider Key chains', 'img-2.jpg', TRUE, '2');
INSERT INTO `props` (`id`, `active`, `description`, `image`, `reserved`, `facility_id`) VALUES ('3', TRUE, 'Carnage figure', 'img-3.jpg', FALSE, '1');
INSERT INTO `props` (`id`, `active`, `description`, `image`, `reserved`, `facility_id`) VALUES ('4', TRUE, 'Carnage statue', 'img-4.jpg', FALSE, '2');

INSERT INTO `props_reservation` (`id`, `quantity`, `props_id`, `registered_user_id`) VALUES ('1', '3', '1', '2');
INSERT INTO `props_reservation` (`id`, `quantity`, `props_id`, `registered_user_id`) VALUES ('2', '2', '2', '2');

INSERT INTO `ad` (`id`, `deadline`, `description`, `image`, `name`, `state`, `version`, `owner_id`) VALUES ('1', '2018-06-30 12:14:00', '35', 'no-image-found.jpg', 'Keys', '0', '2', '4');
INSERT INTO `ad` (`id`, `deadline`, `description`, `image`, `name`, `state`, `version`, `owner_id`) VALUES ('2', '2018-06-30 12:14:00', '70', 'no-image-found.jpg', 'Statue', '0', '2', '3');

INSERT INTO `bid` (`id`, `date`, `offer`, `state`, `ad_id`, `user_id`) VALUES ('1', '2018-06-18 03:27:11', '38', '0', '1', '2');
INSERT INTO `bid` (`id`, `date`, `offer`, `state`, `ad_id`, `user_id`) VALUES ('2', '2018-06-18 03:29:17', '40', '0', '1', '3');
INSERT INTO `bid` (`id`, `date`, `offer`, `state`, `ad_id`, `user_id`) VALUES ('3', '2018-06-18 03:31:16', '81', '0', '2', '2');
INSERT INTO `bid` (`id`, `date`, `offer`, `state`, `ad_id`, `user_id`) VALUES ('4', '2018-06-18 03:33:24', '85', '0', '2', '4');

INSERT INTO `projection` (`id`, `date`, `description`, `director`, `duration`, `genre`, `name`, `picture`, `price`, `facility_id`, `viewing_room_id`) VALUES ('1', '2018-06-21 13:10:00', 'Opis1', 'Reziser', '90', 'Akcija', 'Akcioni film', 'https://cdn.psychologytoday.com/sites/default/files/styles/image-article_inline_full/public/field_blog_entry_images/2017-06/movie_projector.jpg?itok=zD-doWcz', '100', '1', '1');

INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('1', '0', '0', '1', '0', '0', '1', '2', '1', '1');
INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('2', '0', '0', '1', '0', '0', '1', '2', '1', '3');
INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('3', '0', '0', '1', '0', '0', '1', '2', '1', '4');
INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('4', '0', '0', '1', '0', '0', '1', '2', '1', '7');
INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('5', '0', '0', '1', '0', '0', '2', '3', '1', '8');
INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('6', '0', '0', '1', '0', '0', '2', '3', '1', '9');
INSERT INTO `ticket` (`id`, `discount`, `fast_reservation`, `seat_status`, `taken`, `version`, `facility_id`, `owner_id`, `projection_id`, `seat_id`) VALUES ('7', '0', '0', '1', '0', '0', '2', '4', '1', '13');



