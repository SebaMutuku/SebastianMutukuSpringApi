CREATE TABLE PROJECT
(
    project_id  INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);


CREATE TABLE TASK
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      ENUM('TO_DO', 'IN_PROGRESS', 'DONE') NOT NULL,
    due_date    DATE,
    projectId   INT,
    CONSTRAINT fk_project FOREIGN KEY (projectId) REFERENCES PROJECT (project_id)
);