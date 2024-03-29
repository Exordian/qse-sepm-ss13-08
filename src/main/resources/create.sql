DROP TABLE IF EXISTS Date;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS TODO;
DROP TABLE IF EXISTS Track;
DROP TABLE IF EXISTS LVADate;
DROP TABLE IF EXISTS LVA;
DROP TABLE IF EXISTS InCurriculum;
DROP TABLE IF EXISTS Predecessor;
DROP TABLE IF EXISTS MetaLVA;
DROP TABLE IF EXISTS Module;
DROP TABLE IF EXISTS Curriculum;
DROP TABLE IF EXISTS pendingRegistrations;

CREATE TABLE Curriculum (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  studyNumber VARCHAR(20) NOT NULL,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(500),
  academicTitle VARCHAR(30) NOT NULL,
  ectsChoice INTEGER NOT NULL,
  ectsFree INTEGER NOT NULL,
  ectsSoftSkill INTEGER NOT NULL,
  UNIQUE(id, studyNumber)
);

CREATE TABLE Module (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(5000),
  completeAll BOOLEAN NOT NULL,
  UNIQUE(name)
);

CREATE TABLE MetaLVA (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  lvaNumber VARCHAR(30) NOT NULL,
  name VARCHAR(200) NOT NULL,
  semester INTEGER,
  type INTEGER NOT NULL,
  priority FLOAT DEFAULT 0 NOT NULL,
  ects FLOAT NOT NULL,
  module INTEGER REFERENCES Module (id),
  minTimeEstimate INTEGER,
  maxTimeEstimate INTEGER,
  UNIQUE (lvaNumber)
);

CREATE TABLE Predecessor (
  predecessor INTEGER FOREIGN KEY REFERENCES MetaLVA (id),
  successor INTEGER FOREIGN KEY REFERENCES MetaLVA (id),
  PRIMARY KEY (predecessor, successor)
);

CREATE TABLE InCurriculum (
  curriculum INTEGER FOREIGN KEY REFERENCES Curriculum (id),
  module INTEGER FOREIGN KEY REFERENCES Module (id),
  obligatory BOOLEAN NOT NULL,
  PRIMARY KEY (curriculum, module)
);

CREATE TABLE LVA (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0) PRIMARY KEY,
  metaLva INTEGER NOT NULL FOREIGN KEY REFERENCES MetaLVA (id),
  year INTEGER NOT NULL,
  isWinterSemester BOOLEAN NOT NULL,
  description LONGVARCHAR,
  goals LONGVARCHAR,
  content LONGVARCHAR,
  additionalInfo1 LONGVARCHAR,
  additionalInfo2 LONGVARCHAR,
  institute LONGVARCHAR,
  language LONGVARCHAR,
  performanceRecord LONGVARCHAR,
  grade INTEGER,
  inStudyProgress BOOLEAN DEFAULT FALSE NOT NULL,
  UNIQUE (metaLva, year, isWinterSemester)
);

CREATE TABLE LVADate (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0) PRIMARY KEY,
  lva INTEGER NOT NULL FOREIGN KEY REFERENCES LVA (id),
  name VARCHAR(200),
  description VARCHAR(500),
  type INTEGER,
  room VARCHAR(200),
  result INTEGER DEFAULT 0,
  start TIMESTAMP NOT NULL,
  stop TIMESTAMP NOT NULL,
  attendanceRequired BOOLEAN DEFAULT TRUE,
  wasAttendant BOOLEAN DEFAULT TRUE
);

CREATE TABLE Track (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0),
  lva INTEGER FOREIGN KEY REFERENCES LVA (id),
  PRIMARY KEY (id, lva),
  name VARCHAR(200),
  description VARCHAR(500),
  start TIMESTAMP NOT NULL,
  stop TIMESTAMP NOT NULL
);

CREATE TABLE TODO (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0) PRIMARY KEY,
  lva INTEGER FOREIGN KEY REFERENCES LVA (id),
  name VARCHAR(200),
  description VARCHAR(500),
  done BOOLEAN DEFAULT FALSE
);

CREATE TABLE Tag (
  name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Date (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0) PRIMARY KEY,
  tag VARCHAR(50) FOREIGN KEY REFERENCES Tag (name),
  name VARCHAR(2000),
  description VARCHAR(2000),
  isIntersectable BOOLEAN,
  start TIMESTAMP NOT NULL,
  stop TIMESTAMP NOT NULL
);

CREATE TABLE pendingRegistrations (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0) PRIMARY KEY,
  lva VARCHAR(50),
  name VARCHAR(200),
  mode VARCHAR(50),
  start TIMESTAMP NOT NULL,
  stop TIMESTAMP NOT NULL
);
