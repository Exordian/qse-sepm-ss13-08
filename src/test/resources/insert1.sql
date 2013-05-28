INSERT INTO Curriculum (id,studyNumber,name,description,academicTitle,ectsChoice,ectsFree,ectsSoftskill) VALUES
(null,'033 533','Data Engineering & Statistics','Data Engineering und so weiter','Bachelor',10,20,30),
(null,'033 534','Medieninformatik und Visual Computing','Spiele Graphik und so weiter','Bachelor',10,20,30),
(null,'033 535','Medizinische Informatik','Medizinische Informatik und so weiter','Bachelor',10,20,30);

INSERT INTO Module (id,name,description,completeAll) VALUES
(null,'Algebra und Diskrete Mathematik','Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweistechniken und Sätze in den Teilgebieten Algebra (v.a. algebraische Strukturen und lineare Algebra) und Diskrete Mathematik (v.a Kombinatorik und Graphentheorie). Es setzt sich aus einem Vorlesungsteil und einem begleitenden Übungsteil zusammen, der der Vertiefung der Vorlesungsinhalte und der Entwicklung von Fertigkeiten zur Erstellung korrekter mathematischer Beweise und der mathematischen Modellierung und Analyse praktischer Problemstellungen dient.',TRUE),
(null,'Algorithmen und Datenstrukturen','Dieses Modul behandelt folgende Inhalte: Analyse von Algorithmen (asymptotisches Laufzeitverhalten, Omega, O- und Theta-Notation); fundamentale Datentypen und Datenstrukturen; Sortieren und Suchen; grundlegende Graphenalgorithmen; Problemlösungsstrategien und Optimierung mit exakten, approximativen und heuristischen Verfahren; randomisierte Algorithmen;grundlegende geometrische Algorithmen.',TRUE),
(null,'Freie Wahlfächer','Da kann man beliebige Sachen raussuchen',FALSE),
(null,'Studieneingangsgespräch','Muss am Anfang des Studiums gemacht werden',TRUE);

INSERT INTO MetaLVA (id,lvaNumber,name,semester,type,priority,ects,module) VALUES
(null,'104.265','Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik',2,0,0,4,0),
(null,'104.263','Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik',2,2,0,5,0),
(null,'186.813','Algorithmen und Datenstrukturen 1',0,1,0,6,1),
(null,'186.815','Algorithmen und Datenstrukturen 2',0,1,0,3,1),
(null,'187.329','Techniksoziologie und Technikpsychologie',2,1,0,3,2),
(null,'187.298','Forschungsmethoden',2,1,0,3,2),
(null,'280.239','EDV-Vertragsrecht',1,0,0,1.5,2),
(null,'181.208','Softskills für TechnikerInnen',2,1,0,3,2),
(null,'195.039','Studieneingangsgespräch',2,2,'0','0.2','3');

INSERT INTO Predecessor (predecessor,successor) VALUES
(8,2),
(8,3),
(8,0),
(8,1);

INSERT INTO LVA (id,metaLva,year,isWinterSemester,description,grade,inStudyProgress) VALUES
(null,0,2013,FALSE,'Hier steht dann die Beschreibung zu Algebra und Diskrete Mathematik für Wirtschaftsinformatik',0,FALSE),
(null,1,2013,FALSE,'Hier steht dann die Beschreibung zu Algebra und Diskrete Mathematik für Wirtschaftsinformatik',0,FALSE),
(null,2,2013,FALSE,'Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 1',0,FALSE),
(null,3,2013,FALSE,'Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 2',0,FALSE),
(null,4,2013,FALSE,'Hier steht dann die Beschreibung zu Technikpsychologie und Technikpsychologie',0,FALSE),
(null,5,2013,FALSE,'Hier steht dann die Beschreibung zu Forschungsmethoden',0,FALSE),
(null,7,2013,FALSE,'Hier steht dann die Beschreibung zu Softskills für TechnikerInnen',0,FALSE),
(null,8,2013,FALSE,'Hier steht dann die Beschreibung zu Studieneingangsgespräch',0,FALSE);

INSERT INTO InCurriculum (curriculum,module,obligatory) VALUES
(0,3,TRUE),
(0,1,TRUE),
(1,3,TRUE),
(1,1,TRUE),
(1,2,FALSE),
(2,3,TRUE);

INSERT INTO Track(id,lva,name,description,start,stop) VALUES
(null,1,'Übung machen','qertbe fg','2013-04-05 11:01:00.0','2013-04-05 20:00:00.0'),
(null,1,'Übung machen','- küp34i ü0poekas','2013-04-06 12:02:00.0','2013-04-06 21:00:00.0'),
(null,1,'Übung','Beispiele 23, 25, 27, 31, 45','2013-04-07 13:03:00.0','2013-04-07 17:00:00.0'),
(null,2,'Lernen','','2013-04-08 14:04:00.0','2013-04-08 14:50:45.0'),
(null,2,null,'asdfasdf','2013-04-09 15:05:00.0','2013-04-09 17:00:00.0'),
(null,2,'Programmieraufgabe','','2013-04-10 16:06:00.0','2013-04-10 23:00:00.0'),
(null,2,'Studenvorbereitung',null,'2013-04-11 17:07:00.0','2013-04-11 19:00:31.0'),
(null,2,'Gruppenlernen','','2013-04-12 18:08:00.0','2013-04-12 19:00:00.0'),
(null,2,'Übung machen',null,'2013-04-13 19:09:00.0','2013-04-13 23:00:00.0'),
(null,3,null,'','2013-04-14 20:10:00.0','2013-04-14 22:00:23.0'),
(null,3,'Übung machen','','2013-04-15 18:11:00.0','2013-04-15 20:00:00.0'),
(null,3,'','','2013-04-16 14:12:00.0','2013-04-16 23:00:00.0'),
(null,3,'Übung machen','asdf','2013-04-17 23:13:00.0','2013-04-18 05:00:00.0'),
(null,4,'Übung machen','asd sadf asdf sdf ','2013-04-18 10:14:00.0','2013-04-18 11:00:56.0'),
(null,4,'Präsentation vorbereiten','','2013-04-19 11:15:00.0','2013-04-19 23:00:00.0'),
(null,4,'','','2013-04-20 20:16:00.0','2013-04-20 21:00:23.0');

INSERT INTO TODO (id,lva,name,description) VALUES
(null,1,'Skript Kaufen','Favoritenbstraße, Stiege 3, 3. Stock Zimmer H1234'),
(null,null,'ÖB-Beitrag einzahlen','Kontonummer 12341234, Bankleitzahl 12341234, Betrag 17,50 €'),
(null,null,'Ausgeborge Bücher zurückbringen','enliehen bis 24.05.2013'),
(null,0,'Prof. fragen was eine Dterminante ist','');

INSERT INTO Tag (name) VALUES
('Freizeit'),
('Arbeit'),
('Feiertage'),
('Schwimmen'),
('Schlagzeug');

INSERT INTO Date (id,tag,name,description,isIntersectable,start,stop) VALUES
(null,null,null,null,FALSE,'2013-04-16 01:13:00.0','2013-04-16 15:13:00.0'),
(null,'Arbeit',' ',null,FALSE,'2013-05-02 09:00:00.0','2013-05-02 17:45:00.0'),
(null,'Feiertage','Staatsfeiertag','',TRUE,'2013-05-01 00:00:00.0','2013-05-01 23:59:59.9999'),
(null,'Schwimmen','Stadionbad','',TRUE,'2013-04-18 17:00:00.0','2013-04-18 20:00:00.0'),
(null,'Schwimmen','Stadionbad','Tauchkurs!!',FALSE,'2013-04-25 17:00:00.0','2013-04-25 20:00:00.0'),
(null,'Schwimmen','Stadionbad','',TRUE,'2013-05-02 17:00:00.0','2013-05-02 20:00:00.0'),
(null,null,'Schlagzeug','',FALSE,'2013-03-17 20:30:00.0','2013-03-17 23:00:00.0'),
(null,null,'Schlagzeug','',FALSE,'2013-03-18 20:30:00.0','2013-03-18 23:00:00.0'),
(null,'Feiertage','Florian','',TRUE,'2013-05-04 00:00:00.0','2013-05-04 00:00:00.0'),
(null,null,'irgendwas war da ...','',FALSE,'2013-06-17 09:00:00.0','2013-06-17 22:00:00.0'),
(null,'Arbeit','Arbeit',null,FALSE,'2013-06-03 08:30:00.0','2013-06-03 18:00:00.0'),
(null,'Arbeit','Arbeit',null,FALSE,'2013-06-04 08:30:00.0','2013-06-04 18:00:00.0'),
(null,'Arbeit','Arbeit',null,FALSE,'2013-06-05 08:30:00.0','2013-06-05 18:00:00.0'),
(null,'Arbeit','Arbeit',null,FALSE,'2013-06-05 08:30:00.0','2013-06-06 18:00:00.0'),
(null,'Arbeit','Arbeit',null,FALSE,'2013-06-05 08:30:00.0','2013-06-07 18:00:00.0');