-- Insert Categories
INSERT INTO categories (name, description, created_at, updated_at) VALUES
('Fiction', 'Fictional literature and novels', NOW(), NOW()),
('Science', 'Scientific books and research', NOW(), NOW()),
('Technology', 'Technology and programming books', NOW(), NOW()),
('History', 'Historical books and biographies', NOW(), NOW()),
('Philosophy', 'Philosophy and ethics books', NOW(), NOW());

-- Insert Users
INSERT INTO users (email, first_name, last_name, password, role, active, created_at, updated_at) VALUES
('admin@library.com', 'Admin', 'User', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN', true, NOW(), NOW()),
('librarian@library.com', 'John', 'Librarian', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'LIBRARIAN', true, NOW(), NOW()),
('user@library.com', 'Jane', 'User', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'USER', true, NOW(), NOW());

-- Insert Books
INSERT INTO books (title, author, isbn, description, total_copies, available_copies, category_id, created_at, updated_at) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 'A classic American novel', 5, 5, 1, NOW(), NOW()),
('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 'A gripping tale of racial injustice', 3, 3, 1, NOW(), NOW()),
('1984', 'George Orwell', '978-0-452-28423-4', 'A dystopian social science fiction novel', 4, 4, 1, NOW(), NOW()),
('Clean Code', 'Robert C. Martin', '978-0-13-235088-4', 'A handbook of agile software craftsmanship', 2, 2, 3, NOW(), NOW()),
('The Origin of Species', 'Charles Darwin', '978-0-14-043205-1', 'On the origin of species by means of natural selection', 2, 2, 2, NOW(), NOW());
