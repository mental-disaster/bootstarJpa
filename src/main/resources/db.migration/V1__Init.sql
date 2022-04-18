CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `auth` VARCHAR(16) NOT NULL DEFAULT 'USER')
    ;

CREATE TABLE IF NOT EXISTS `post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `author_id` INT NOT NULL,
    `caption` BLOB DEFAULT '',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_post_user`
    FOREIGN KEY (`author_id`)
        REFERENCES `user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION)
    ;

CREATE TABLE IF NOT EXISTS `image` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `post_id` INT NOT NULL,
    `name` BLOB,
    `path` BLOB,
    `original_name` BLOB,
    `original_size` BIGINT,
    CONSTRAINT `fk_image_post1`
    FOREIGN KEY (`post_id`)
        REFERENCES `post` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION)
    ;