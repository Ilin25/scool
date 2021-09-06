CREATE TABLE groups
(
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(50)
);

CREATE TABLE students
(
    student_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    group_id INTEGER REFERENCES groups(group_id)
);

CREATE TABLE courses
(
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(50),
    course_description VARCHAR(50)
);

CREATE TABLE students_courses
(
    student_id INTEGER REFERENCES students(student_id),
    course_id INTEGER REFERENCES courses(course_id)
);