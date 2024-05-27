-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 28-Maio-2024 às 00:51
-- Versão do servidor: 10.4.28-MariaDB
-- versão do PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `cm`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `locals`
--

CREATE TABLE `locals` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `location` varchar(250) DEFAULT NULL,
  `description` varchar(500) NOT NULL,
  `ref` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `locals_img`
--

CREATE TABLE `locals_img` (
  `id` int(11) NOT NULL,
  `local_id` int(11) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `desc_img` varchar(150) NOT NULL,
  `data_inserted` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `trips`
--

CREATE TABLE `trips` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(150) NOT NULL,
  `imageUrl` varchar(150) NOT NULL,
  `description` text DEFAULT NULL,
  `date_created` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `date` date NOT NULL,
  `end_of_the_trip` date NOT NULL,
  `classification` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `trips`
--

INSERT INTO `trips` (`id`, `name`, `username`, `imageUrl`, `description`, `date_created`, `date`, `end_of_the_trip`, `classification`) VALUES
(1, 'Brasil', 'Fabio Porchat', 'https://humanidades.com/wp-content/uploads/2017/04/brasil-1-e1566159470787.jpg', 'Brasil Brasil Brasil Brasil Brasil Brasil ', '2024-05-27 22:40:26', '2024-05-14', '2024-05-29', 1),
(2, 'Portugal', 'CR7', 'https://i0.wp.com/www.eurodicas.com.br/wp-content/uploads/2018/12/regioes-de-portugal.jpg?fit=1360%2C907&ssl=1', 'Portugal Portugal Portugal Portugal Portugal Portugal Portugal Portugal', '2024-05-27 22:40:35', '2024-05-27', '2024-05-27', 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `trips_locals`
--

CREATE TABLE `trips_locals` (
  `id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `local_id` int(11) NOT NULL,
  `nr_order` int(11) NOT NULL,
  `date_finish` date NOT NULL,
  `classification` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `cellphone` varchar(50) NOT NULL,
  `profile_img` varchar(150) NOT NULL,
  `user_type` int(11) NOT NULL,
  `username` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `cellphone`, `profile_img`, `user_type`, `username`, `password`) VALUES
(7, 'Nuno', 'nunomansilhas@gmail.com', '12345678', '12345678', 1, 'nunomansilhas', '12345678'),
(8, 'Valter', 'valter@gmail.com', '12345678', '12345678', 1, 'valter', '12345678');

-- --------------------------------------------------------

--
-- Estrutura da tabela `users_trips`
--

CREATE TABLE `users_trips` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `main_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `locals`
--
ALTER TABLE `locals`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `locals_img`
--
ALTER TABLE `locals_img`
  ADD PRIMARY KEY (`id`),
  ADD KEY `local_id` (`local_id`);

--
-- Índices para tabela `trips`
--
ALTER TABLE `trips`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `trips_locals`
--
ALTER TABLE `trips_locals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `trip_id` (`trip_id`),
  ADD KEY `local_id` (`local_id`);

--
-- Índices para tabela `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índices para tabela `users_trips`
--
ALTER TABLE `users_trips`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `locals`
--
ALTER TABLE `locals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `locals_img`
--
ALTER TABLE `locals_img`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `trips`
--
ALTER TABLE `trips`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `trips_locals`
--
ALTER TABLE `trips_locals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `users_trips`
--
ALTER TABLE `users_trips`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `locals_img`
--
ALTER TABLE `locals_img`
  ADD CONSTRAINT `locals_img_ibfk_1` FOREIGN KEY (`local_id`) REFERENCES `locals` (`id`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `trips_locals`
--
ALTER TABLE `trips_locals`
  ADD CONSTRAINT `trips_locals_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `trips_locals_ibfk_2` FOREIGN KEY (`local_id`) REFERENCES `locals` (`id`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `users_trips`
--
ALTER TABLE `users_trips`
  ADD CONSTRAINT `users_trips_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `users_trips_ibfk_2` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
