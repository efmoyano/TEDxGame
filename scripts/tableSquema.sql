-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-10-2014 a las 03:23:37
-- Versión del servidor: 5.6.17
-- Versión de PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `tedxgame`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getQuestion`()
    NO SQL
SELECT * FROM question ORDER BY RAND() LIMIT 3$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertQuestion`(IN `question` VARCHAR(150), IN `answer1` VARCHAR(50), IN `answer2` VARCHAR(50), IN `answer3` VARCHAR(50), IN `answer4` VARCHAR(50), IN `difficulty` INT, IN `correct` INT)
    NO SQL
INSERT INTO `tedxgame`.`question` (`id`, `question`, `answer1`, `answer2`, `answer3`, `answer4`, `difficulty`, `correct`) VALUES (NULL, question , answer1, answer2, answer3, answer4, difficulty, correct)$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(150) NOT NULL,
  `answer1` varchar(50) NOT NULL,
  `answer2` varchar(50) NOT NULL,
  `answer3` varchar(50) NOT NULL,
  `answer4` varchar(50) NOT NULL,
  `difficulty` int(1) NOT NULL,
  `correct` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Truncar tablas antes de insertar `question`
--

TRUNCATE TABLE `question`;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
