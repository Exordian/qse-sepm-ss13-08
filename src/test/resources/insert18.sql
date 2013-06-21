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
(null,2,2013,FALSE,'Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 1',0,TRUE),
(null,3,2013,FALSE,'Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 2',0,TRUE),
(null,7,2013,FALSE,'Hier steht dann die Beschreibung zu Softskills für TechnikerInnen',0,FALSE);

INSERT INTO InCurriculum (curriculum,module,obligatory) VALUES
(0,3,TRUE),
(0,1,TRUE),
(1,3,TRUE),
(1,1,TRUE),
(1,2,FALSE),
(2,3,TRUE);

INSERT INTO LVADate (id,lva,name,description,type,room,result,start,stop,attendanceRequired,wasAttendant) VALUES
(null,0,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-06 09:15:00.0','2013-03-06 10:45:00.0',FALSE,TRUE),
(null,0,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-13 09:15:00.0','2013-03-13 10:45:00.0',FALSE,TRUE),
(null,0,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-20 09:15:00.0','2013-03-20 10:45:00.0',FALSE,TRUE),
(null,0,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-27 09:15:00.0','2013-04-27 10:45:00.0',FALSE,TRUE),
(null,0,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2014-04-10 09:15:00.0','2014-04-10 10:45:00.0',FALSE,TRUE),
(null,0,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2014-04-17 09:15:00.0','2014-04-17 10:45:00.0',FALSE,TRUE),

(null,1,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2014-05-22 09:15:00.0','2014-05-22 10:45:00.0',FALSE,TRUE),
(null,1,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-11-29 09:15:00.0','2013-11-29 10:45:00.0',FALSE,FALSE),
(null,1,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-11-05 09:15:00.0','2013-11-05 10:45:00.0',FALSE,FALSE),
(null,1,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-11-12 09:15:00.0','2013-11-12 10:45:00.0',FALSE,FALSE),
(null,1,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2014-06-19 09:15:00.0','2014-06-19 10:45:00.0',FALSE,TRUE),
(null,1,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-11-26 09:15:00.0','2013-11-26 10:45:00.0',FALSE,FALSE),

(null,2,'VU - Softskills für TechnikerInnen','Vorbesprechung für Softskills für Technikerinnen',0,'EI 8 Pötzl HS',null,'2013-03-07 18:00:00.0','2013-03-07 19:00:00.0',FALSE,TRUE),
(null,2,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-14 18:00:00.0','2013-03-14 20:00:00.0',FALSE,TRUE),
(null,2,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-21 18:00:00.0','2013-03-21 20:00:00.0',FALSE,TRUE),
(null,2,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-28 18:00:00.0','2013-03-28 20:00:00.0',FALSE,TRUE),
(null,2,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-04 18:00:00.0','2013-03-04 20:00:00.0',FALSE,FALSE),
(null,2,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-11 18:00:00.0','2013-03-11 20:00:00.0',FALSE,TRUE),
(null,2,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-18 18:00:00.0','2013-03-18 20:00:00.0',FALSE,TRUE);