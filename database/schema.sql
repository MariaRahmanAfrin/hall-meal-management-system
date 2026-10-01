-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS hall_meal_db;
USE hall_meal_db;

-- 1. Users Table (Parent Table for Students & Admins)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'ADMIN') NOT NULL,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Weekly Menu Table
CREATE TABLE IF NOT EXISTS weekly_menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    day_name ENUM('Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') UNIQUE NOT NULL,
    breakfast_item VARCHAR(255) NOT NULL,
    lunch_item VARCHAR(255) NOT NULL,
    dinner_item VARCHAR(255) NOT NULL
);

-- 3. Meal Prices Table
CREATE TABLE IF NOT EXISTS meal_prices (
    id INT PRIMARY KEY DEFAULT 1,
    breakfast_price DECIMAL(10,2) NOT NULL DEFAULT 30.00,
    lunch_price DECIMAL(10,2) NOT NULL DEFAULT 70.00,
    dinner_price DECIMAL(10,2) NOT NULL DEFAULT 60.00
);

-- Initialize default prices (only if empty)
INSERT IGNORE INTO meal_prices (id, breakfast_price, lunch_price, dinner_price)
VALUES (1, 30.00, 70.00, 60.00);

-- 4. Meal Selections Table
CREATE TABLE IF NOT EXISTS meal_selections (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    selection_date DATE NOT NULL,
    breakfast BOOLEAN DEFAULT FALSE,
    lunch BOOLEAN DEFAULT FALSE,
    dinner BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_student_date (student_id, selection_date)
);

-- 5. Daily Expenses Table
CREATE TABLE IF NOT EXISTS daily_expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    expense_date DATE NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    cost DECIMAL(10,2) NOT NULL
);

-- Insert Default Weekly Menu Items
INSERT IGNORE INTO weekly_menu (day_name, breakfast_item, lunch_item, dinner_item) VALUES
('Saturday', 'Paratha, Milk Tea, Fried Egg (with chilli & onion)', 'Rice, Fried Okra, Mashed Potato, Dal, Lemon', 'Rice, Dal, Chicken Curry, Lemon'),
('Sunday', 'Roti, Fried Potato', 'Rice, Dal, Rui Fish with Papaya, Lemon', 'Rice, Dal, Rui Fish, Mixed Vegetables, Lemon'),
('Monday', 'Roti, Chicken Curry', 'Rice, Fried Egg, Red Amaranth / Any Vegetable, Dal, Lemon', 'Rice, Fried Shrimp (2 pcs), Fried Pumpkin (2 pcs), Lemon'),
('Tuesday', 'Bread (3 pcs), Poached Egg, Milk Tea', 'Pulao, Roast, Salad (1 piece meat), Lemon', 'Rice, Fried Eggplant (2 pcs), Papaya Curry, Lemon'),
('Wednesday', 'Roti, Mixed Vegetables (Ridge Gourd, Pumpkin, Potato, Papaya)', 'Potato Curry with Egg, Rice, Lemon', 'Pulao / Rice Khichuri, Chicken Bhuna, Salad, Lemon'),
('Thursday', 'Paratha, Butter Dal', 'Rice, Moong Dal, Pabda Fish Curry', 'Rice, Dal, Mashed Potato, Fried Egg'),
('Friday', 'Suji, Roti', 'Rice, Beef / Chicken, Dal, Salad, Lemon', 'Rice, Dal, Potato & Bean Fry');