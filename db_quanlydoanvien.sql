-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 16, 2023 at 03:24 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_quanlydoanvien`
--

-- --------------------------------------------------------

--
-- Table structure for table `chidoans`
--

CREATE TABLE `chidoans` (
  `id` bigint(20) NOT NULL,
  `ten_chi_doan` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `danhgias`
--

CREATE TABLE `danhgias` (
  `id` bigint(20) NOT NULL,
  `ho_ten` varchar(255) DEFAULT NULL,
  `ma_doan_vien` varchar(255) DEFAULT NULL,
  `nam_hoc` varchar(255) DEFAULT NULL,
  `nguoi_danh_gia` varchar(255) DEFAULT NULL,
  `noi_dung` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `doanviens`
--

CREATE TABLE `doanviens` (
  `id` bigint(20) NOT NULL,
  `cccd` varchar(255) DEFAULT NULL,
  `chuc_vu` varchar(255) DEFAULT NULL,
  `dan_toc` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ho_ten` varchar(255) DEFAULT NULL,
  `id_chi_doan` bigint(20) DEFAULT NULL,
  `ma_doan_vien` varchar(255) DEFAULT NULL,
  `mat_khau` varchar(255) DEFAULT NULL,
  `ngay_sinh` varchar(255) DEFAULT NULL,
  `ngay_vao_doan` varchar(255) DEFAULT NULL,
  `que_quan` varchar(255) DEFAULT NULL,
  `re_mat_khau` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  `ten_chi_doan` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES
(1, 'admin@sis.hust.edu.vn', 'admin', 'admin', '$2a$10$L6Y4QDDzda8MwcRSr3coAOTW1JvEN9na56Zmh0hLPnX5yU30HPHrC', 'ADMIN');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chidoans`
--
ALTER TABLE `chidoans`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_moas00b7va0uvja0esra47jtf` (`ten_chi_doan`) USING HASH;

--
-- Indexes for table `danhgias`
--
ALTER TABLE `danhgias`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `doanviens`
--
ALTER TABLE `doanviens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6o4k0p44y65aeaj02xhqq4biy` (`email`) USING HASH,
  ADD UNIQUE KEY `UK_c9x0ncfj70sagcprnpcf4qc3y` (`ma_doan_vien`) USING HASH,
  ADD KEY `id_chi_doan` (`id_chi_doan`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`) USING HASH;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chidoans`
--
ALTER TABLE `chidoans`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `danhgias`
--
ALTER TABLE `danhgias`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `doanviens`
--
ALTER TABLE `doanviens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;