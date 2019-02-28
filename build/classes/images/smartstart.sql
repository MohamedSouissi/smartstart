-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 25 fév. 2019 à 17:27
-- Version du serveur :  5.7.19
-- Version de PHP :  7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `smartstart`
--

-- --------------------------------------------------------

--
-- Structure de la table `application`
--

DROP TABLE IF EXISTS `application`;
CREATE TABLE IF NOT EXISTS `application` (
  `id_application` int(11) NOT NULL AUTO_INCREMENT,
  `id_opportunity` int(11) NOT NULL,
  `id_freelancer` int(11) NOT NULL,
  `state` varchar(30) NOT NULL,
  PRIMARY KEY (`id_application`),
  KEY `id_opportunity` (`id_opportunity`),
  KEY `id_freelancer` (`id_freelancer`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `application`
--

INSERT INTO `application` (`id_application`, `id_opportunity`, `id_freelancer`, `state`) VALUES
(27, 113, 4, 'Proposal');

-- --------------------------------------------------------

--
-- Structure de la table `certif_tests`
--

DROP TABLE IF EXISTS `certif_tests`;
CREATE TABLE IF NOT EXISTS `certif_tests` (
  `id_certif` int(11) NOT NULL AUTO_INCREMENT,
  `name_certif` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `id_freelancer` int(11) NOT NULL,
  PRIMARY KEY (`id_certif`),
  KEY `id_freelancer` (`id_freelancer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `complaints`
--

DROP TABLE IF EXISTS `complaints`;
CREATE TABLE IF NOT EXISTS `complaints` (
  `id_complaint` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `mail_user` varchar(50) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `content` longtext NOT NULL,
  `added_date` date NOT NULL,
  `id_opp` int(11) NOT NULL,
  PRIMARY KEY (`id_complaint`),
  KEY `id_user` (`id_user`),
  KEY `id_opp` (`id_opp`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `complaints`
--

INSERT INTO `complaints` (`id_complaint`, `id_user`, `mail_user`, `subject`, `content`, `added_date`, `id_opp`) VALUES
(1, 1, 'man', 'Faulty Opportunity', 'lol', '2019-02-25', 113),
(2, 1, 'Beslm', 'Faulty Opportunity', 'lol', '2019-02-25', 114),
(5, 2, 'Mansouri111', 'Faulty Opportunity', 'lol ena howa mansouri', '2019-02-25', 113),
(7, 2, 'Mm', 'VERBAL ABUSE', 'mamma', '2019-02-25', 113),
(8, 2, 'maa', 'FAKE ENTREPRISE', 'll', '2019-02-25', 117),
(9, 2, 'bessbessbobi', 'VERBAL ABUSE', 'ena howa fl 9dim', '2019-02-20', 128);

-- --------------------------------------------------------

--
-- Structure de la table `contract`
--

DROP TABLE IF EXISTS `contract`;
CREATE TABLE IF NOT EXISTS `contract` (
  `id_contract` int(11) NOT NULL AUTO_INCREMENT,
  `payment_method` varchar(30) NOT NULL,
  `Start_date` date NOT NULL,
  `finish_date` date NOT NULL,
  `sum` float NOT NULL,
  `id_application` int(11) DEFAULT NULL,
  `prio` int(11) NOT NULL,
  PRIMARY KEY (`id_contract`),
  KEY `id_application` (`id_application`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `contract`
--

INSERT INTO `contract` (`id_contract`, `payment_method`, `Start_date`, `finish_date`, `sum`, `id_application`, `prio`) VALUES
(2, 'cheque', '2018-10-05', '2018-10-05', 0, 27, 1);

-- --------------------------------------------------------

--
-- Structure de la table `education`
--

DROP TABLE IF EXISTS `education`;
CREATE TABLE IF NOT EXISTS `education` (
  `id_edu` int(11) NOT NULL AUTO_INCREMENT,
  `edu_name` varchar(100) NOT NULL,
  `id_freelancer` int(11) NOT NULL,
  PRIMARY KEY (`id_edu`),
  KEY `id_freelancer` (`id_freelancer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE IF NOT EXISTS `feedback` (
  `id_feedback` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `Rating` float NOT NULL,
  `comment` varchar(100) NOT NULL,
  `added_date` date NOT NULL,
  `id_application` int(11) NOT NULL,
  PRIMARY KEY (`id_feedback`),
  KEY `id_user` (`id_user`),
  KEY `id_application` (`id_application`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `fos_user`
--

DROP TABLE IF EXISTS `fos_user`;
CREATE TABLE IF NOT EXISTS `fos_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Birth_date` date DEFAULT NULL,
  `Bio` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Register_Date` date DEFAULT NULL,
  `Earnings` float DEFAULT NULL,
  `Expenses` float DEFAULT NULL,
  `Budget` float DEFAULT NULL,
  `field_company` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_957A647992FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_957A6479A0D96FBF` (`email_canonical`),
  UNIQUE KEY `UNIQ_957A6479C05FB297` (`confirmation_token`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `fos_user`
--

INSERT INTO `fos_user` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `name`, `last_name`, `Birth_date`, `Bio`, `location`, `Register_Date`, `Earnings`, `Expenses`, `Budget`, `field_company`) VALUES
(1, 'Mohamed', 'mohamed', 'adelmounir.achir@esprit.tn', 'adelmounir.achir@esprit.tn', 1, 'salt', 'mounir', '2019-02-20 00:00:00', 'yes', '2019-02-21 00:00:00', 'freelancer', 'Mohamed', 'Amine', '2019-02-21', 'freelancer', NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'mani', 'bW91bmly', 'mounirachir96@gmail.com', 'bW91bmlyYWNoaXI5NkBnbWFpbC5jb20=', 1, 'salt', 'mounir', '1970-01-01 00:00:00', '3ia0X37uDh', '1970-01-01 00:00:00', 'entreprise', 'Mouni', 'Achi', '3919-03-18', 'Bio', 'tunis,tunis,tunis', '2019-02-17', 0, 0, 0, ''),
(4, 'Khalil', 'khalil', 'acmounir@live.fr', 'acmounir@live.fr', 0, NULL, 'khalil', NULL, NULL, NULL, 'entreprise', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 'Met', 'MjYwOA==', 'met@gmail.com', 'bWV0QGdtYWlsLmNvbQ==', 1, 'salt', '2608', '1970-01-01 00:00:00', 'Wdx6yZ0TjQ', '1970-01-01 00:00:00', 'freelancer', 'met', 'met', '3919-03-20', 'Bio', 'tunis,tunis,tunis', '2019-02-23', 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Structure de la table `has_skill`
--

DROP TABLE IF EXISTS `has_skill`;
CREATE TABLE IF NOT EXISTS `has_skill` (
  `id_freelancer` int(11) NOT NULL,
  `id_skill` int(11) NOT NULL,
  KEY `id_skill` (`id_skill`),
  KEY `id_freelancer` (`id_freelancer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `has_skill`
--

INSERT INTO `has_skill` (`id_freelancer`, `id_skill`) VALUES
(1, 2),
(1, 7),
(1, 7);

-- --------------------------------------------------------

--
-- Structure de la table `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
  `id_message` int(11) NOT NULL AUTO_INCREMENT,
  `message_from` int(11) NOT NULL,
  `message_to` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `attachement` int(11) DEFAULT NULL,
  `date_message` date NOT NULL,
  `viewed` int(11) NOT NULL,
  PRIMARY KEY (`id_message`),
  KEY `message_from` (`message_from`),
  KEY `message_to` (`message_to`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `needed_skills`
--

DROP TABLE IF EXISTS `needed_skills`;
CREATE TABLE IF NOT EXISTS `needed_skills` (
  `id_opp` int(11) NOT NULL,
  `id_skill` int(11) NOT NULL,
  KEY `id_opp` (`id_opp`),
  KEY `id_skill` (`id_skill`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `needed_skills`
--

INSERT INTO `needed_skills` (`id_opp`, `id_skill`) VALUES
(117, 2),
(121, 2),
(128, 2);

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id_notif` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `content` varchar(200) NOT NULL,
  `id_declancheur` int(11) NOT NULL,
  `dateDeclanched` date NOT NULL,
  `dateViwed` date DEFAULT NULL,
  PRIMARY KEY (`id_notif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `opportunity`
--

DROP TABLE IF EXISTS `opportunity`;
CREATE TABLE IF NOT EXISTS `opportunity` (
  `id_opp` int(11) NOT NULL AUTO_INCREMENT,
  `job_title` varchar(50) NOT NULL,
  `job_category` varchar(50) NOT NULL,
  `job_description` varchar(50) NOT NULL,
  `Budget` float NOT NULL,
  `job_Draft` int(11) NOT NULL,
  `job_Duration` varchar(50) NOT NULL,
  `Expiry_date` date NOT NULL,
  `added_date` date NOT NULL,
  `id_entreprise` int(11) NOT NULL,
  PRIMARY KEY (`id_opp`),
  KEY `id_entreprise` (`id_entreprise`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `opportunity`
--

INSERT INTO `opportunity` (`id_opp`, `job_title`, `job_category`, `job_description`, `Budget`, `job_Draft`, `job_Duration`, `Expiry_date`, `added_date`, `id_entreprise`) VALUES
(113, 'l', 'l', 'l', 36, 0, '0 years 0 Months 1 Days', '2019-02-24', '2019-02-23', 2),
(114, 'l', 'l', 'l', 3, 0, '0 years 0 Months 1 Days', '2019-02-24', '2019-02-23', 2),
(117, 'MOUNIR', 'ACHIR', 'KHALIL', 250, 0, '0 years 0 Months 1 Days', '2019-02-24', '2019-02-23', 2),
(121, 'M', 'M', 'M', 250, 0, '0 years 0 Months 1 Days', '2019-02-24', '2019-02-23', 4),
(128, 'M', 'M', 'M', 250, 0, '0 years 0 Months 1 Days', '2019-02-25', '2019-02-24', 2);

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id_question` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT NULL,
  `question` varchar(50) NOT NULL,
  `description` varchar(256) NOT NULL,
  `added_date` date NOT NULL,
  `answered` date DEFAULT NULL,
  `subject` varchar(256) NOT NULL,
  `Vues` int(11) NOT NULL,
  PRIMARY KEY (`id_question`),
  KEY `id_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `reply`
--

DROP TABLE IF EXISTS `reply`;
CREATE TABLE IF NOT EXISTS `reply` (
  `id_rep` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `date_reply` date NOT NULL,
  `id_question` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `likes` int(11) NOT NULL,
  `dislikes` int(11) NOT NULL,
  PRIMARY KEY (`id_rep`),
  KEY `id_user` (`id_user`),
  KEY `id_question` (`id_question`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `skills`
--

DROP TABLE IF EXISTS `skills`;
CREATE TABLE IF NOT EXISTS `skills` (
  `id_skill` int(11) NOT NULL AUTO_INCREMENT,
  `name_skill` varchar(60) NOT NULL,
  PRIMARY KEY (`id_skill`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `skills`
--

INSERT INTO `skills` (`id_skill`, `name_skill`) VALUES
(1, 'PHP'),
(2, 'JAVA'),
(3, 'SYMFONY'),
(4, 'PHOTOSHOP'),
(5, 'ILLUSTRATOR'),
(6, 'CSS'),
(7, 'JAVASCRIPT'),
(8, 'HTML'),
(9, 'ANGULAR'),
(10, 'C'),
(11, 'c++'),
(12, 'c#');

-- --------------------------------------------------------

--
-- Structure de la table `suggestedopps`
--

DROP TABLE IF EXISTS `suggestedopps`;
CREATE TABLE IF NOT EXISTS `suggestedopps` (
  `freelancerId` int(11) NOT NULL,
  `opportunityId` int(11) NOT NULL,
  KEY `freelancerId` (`freelancerId`),
  KEY `opportunityId` (`opportunityId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `suggestedopps`
--

INSERT INTO `suggestedopps` (`freelancerId`, `opportunityId`) VALUES
(1, 117),
(1, 121);

-- --------------------------------------------------------

--
-- Structure de la table `views`
--

DROP TABLE IF EXISTS `views`;
CREATE TABLE IF NOT EXISTS `views` (
  `id_view` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `view_identification` int(11) NOT NULL,
  `dateView` date NOT NULL,
  PRIMARY KEY (`id_view`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `application`
--
ALTER TABLE `application`
  ADD CONSTRAINT `application_ibfk_1` FOREIGN KEY (`id_freelancer`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `application_ibfk_2` FOREIGN KEY (`id_opportunity`) REFERENCES `opportunity` (`id_opp`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `certif_tests`
--
ALTER TABLE `certif_tests`
  ADD CONSTRAINT `certif_tests_ibfk_1` FOREIGN KEY (`id_freelancer`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `complaints`
--
ALTER TABLE `complaints`
  ADD CONSTRAINT `complaints_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `complaints_ibfk_2` FOREIGN KEY (`id_opp`) REFERENCES `opportunity` (`id_opp`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `contract`
--
ALTER TABLE `contract`
  ADD CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`id_application`) REFERENCES `application` (`id_application`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `education`
--
ALTER TABLE `education`
  ADD CONSTRAINT `education_ibfk_1` FOREIGN KEY (`id_freelancer`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`id_application`) REFERENCES `application` (`id_application`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `has_skill`
--
ALTER TABLE `has_skill`
  ADD CONSTRAINT `has_skill_ibfk_1` FOREIGN KEY (`id_skill`) REFERENCES `skills` (`id_skill`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `has_skill_ibfk_2` FOREIGN KEY (`id_freelancer`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`message_from`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`message_to`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `needed_skills`
--
ALTER TABLE `needed_skills`
  ADD CONSTRAINT `needed_skills_ibfk_1` FOREIGN KEY (`id_opp`) REFERENCES `opportunity` (`id_opp`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `needed_skills_ibfk_2` FOREIGN KEY (`id_skill`) REFERENCES `skills` (`id_skill`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `opportunity`
--
ALTER TABLE `opportunity`
  ADD CONSTRAINT `opportunity_ibfk_1` FOREIGN KEY (`id_entreprise`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `reply`
--
ALTER TABLE `reply`
  ADD CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id_question`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `suggestedopps`
--
ALTER TABLE `suggestedopps`
  ADD CONSTRAINT `suggestedopps_ibfk_1` FOREIGN KEY (`freelancerId`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `suggestedopps_ibfk_2` FOREIGN KEY (`opportunityId`) REFERENCES `opportunity` (`id_opp`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
