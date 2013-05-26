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

INSERT INTO LVADate (id,lva,name,description,type,room,result,start,stop,attendanceRequired,wasAttendant) VALUES
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-03-04 12:15:00.0','2013-03-04 13:45:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-03-11 12:15:00.0','2013-03-11 13:45:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-03-18 12:15:00.0','2013-03-18 13:45:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-04-08 12:15:00.0','2013-04-08 13:45:00.0',FALSE,FALSE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-04-15 12:15:00.0','2013-04-15 13:45:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-04-22 12:15:00.0','2013-04-22 13:45:00.0',FALSE,FALSE),

(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-03-06 08:00:00.0','2013-03-06 10:00:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-03-13 08:00:00.0','2013-03-13 10:00:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-03-20 08:00:00.0','2013-03-20 10:00:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-04-10 08:00:00.0','2013-04-10 10:00:00.0',FALSE,FALSE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-04-17 08:00:00.0','2013-04-17 10:00:00.0',FALSE,TRUE),
(null,0,'VO - Algebra','blablabla über Mathe - VO',0,'HS 17 Friedrich Hartmann',null,'2013-04-24 08:00:00.0','2013-04-24 10:00:00.0',FALSE,TRUE),

(null,0,'Prüfung - Algebra','blablabla über Mathe - Prüfung',2,'EI 7 Hörsaal',null,'2013-06-28 12:00:00.0','2013-06-28 14:30:00.0',TRUE,null),
(null,0,'Prüfung - Algebra','blablabla über Mathe - Prüfung',2,'EI 7 Hörsaal',null,'2013-07-02 10:00:00.0','2013-07-02 12:30:00.0',TRUE,null),

(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-03-11 08:30:00.0','2013-03-11 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-03-18 08:30:00.0','2013-03-18 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-03-25 08:30:00.0','2013-03-25 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',75,'2013-04-01 08:30:00.0','2013-04-01 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-04-08 08:30:00.0','2013-04-08 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',100,'2013-04-15 08:30:00.0','2013-04-15 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-04-22 08:30:00.0','2013-04-22 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-04-29 08:30:00.0','2013-04-29 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-05-06 08:30:00.0','2013-05-06 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-05-13 08:30:00.0','2013-05-13 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-05-20 08:30:00.0','2013-05-20 10:00:00.0',TRUE,TRUE),
(null,1,'UE - Algebra G1','blablabla über Mathe - UE',1,'EI 8 Pötzl HS',null,'2013-05-27 08:30:00.0','2013-05-27 10:00:00.0',TRUE,TRUE),

(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-06 09:15:00.0','2013-03-06 10:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-13 09:15:00.0','2013-03-13 10:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-20 09:15:00.0','2013-03-20 10:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-27 09:15:00.0','2013-04-27 10:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-10 09:15:00.0','2013-04-10 10:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-17 09:15:00.0','2013-04-17 10:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-24 09:15:00.0','2013-04-24 10:45:00.0',FALSE,null),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-05-08 09:15:00.0','2013-05-08 10:45:00.0',FALSE,null),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-05-15 09:15:00.0','2013-05-15 10:45:00.0',FALSE,TRUE),

(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-07 11:15:00.0','2013-03-17 12:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-03-14 11:15:00.0','2013-03-14 12:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'EI 7 Hörsaal',null,'2013-03-21 11:15:00.0','2013-03-21 12:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-11 11:15:00.0','2013-04-11 12:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-18 11:15:00.0','2013-04-18 12:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-04-25 11:15:00.0','2013-04-25 12:45:00.0',FALSE,TRUE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-05-02 11:15:00.0','2013-05-02 12:45:00.0',FALSE,FALSE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-05-09 11:15:00.0','2013-05-09 12:45:00.0',FALSE,FALSE),
(null,2,'VO - Algodat1','blablabla über Algodat1 - VO',0,'Kuppelsaal',null,'2013-05-16 11:15:00.0','2013-05-16 12:45:00.0',FALSE,TRUE),

(null,2,'Prüfung - Algodat1','blablabla über Algodat1 - Prüfung',2,null,null,'2013-04-25 18:00:00.0','2013-04-25 20:00:00.0',TRUE,null),
(null,2,'Prüfung - Algodat1','blablabla über Algodat1 - Prüfung',2,null,null,'2013-06-06 18:00:00.0','2013-06-06 20:00:00.0',TRUE,null),

(null,2,'UE - Algodat1 Gruppe 04','blablabla über Algodat1 - UE',1,'Seminarraum 186',null,'2013-03-18 11:00:00.0','2013-03-18 11:55:00.0',TRUE,null),
(null,2,'UE - Algodat1 Gruppe 04','blablabla über Algodat1 - UE',1,'Seminarraum 186',null,'2013-04-15 11:00:00.0','2013-04-15 11:55:00.0',TRUE,null),
(null,2,'UE - Algodat1 Gruppe 04','blablabla über Algodat1 - UE',1,'Seminarraum 186',null,'2013-04-29 11:00:00.0','2013-04-29 11:55:00.0',TRUE,null),
(null,2,'UE - Algodat1 Gruppe 04','blablabla über Algodat1 - UE',1,'Seminarraum 186',null,'2013-05-27 11:00:00.0','2013-05-27 11:55:00.0',TRUE,null),

(null,2,'Algodat1: Deadline 1. Beispielblatt','blablabla über Algodat1 - Beispielblatt',3,null,null,'2013-04-09 00:00:00.0','2013-04-09 00:00:00.0',null,null),
(null,2,'Algodat1: Deadline Programmieraufgabe','blablabla über Algodat1 - Programmieraufgabe',3,null,null,'2013-05-13 15:00:00.0','2013-05-13 15:00:00.0',null,null),
(null,2,'Algodat1: Deadline 2. Beispielblatt','blablabla über Algodat1 - Beispielblatt',3,null,null,'2013-05-21 00:00:00.0','2013-05-21 00:00:00.0',null,null),

(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-05-22 09:15:00.0','2013-05-22 10:45:00.0',FALSE,TRUE),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-05-29 09:15:00.0','2013-05-29 10:45:00.0',FALSE,FALSE),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-05 09:15:00.0','2013-06-05 10:45:00.0',FALSE,FALSE),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-12 09:15:00.0','2013-06-12 10:45:00.0',FALSE,FALSE),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-19 09:15:00.0','2013-06-19 10:45:00.0',FALSE,TRUE),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-26 09:15:00.0','2013-06-26 10:45:00.0',FALSE,FALSE),

(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-05-22 11:15:00.0','2013-05-22 12:45:00.0',FALSE,null),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-05-30 11:15:00.0','2013-05-30 12:45:00.0',FALSE,null),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-06 11:15:00.0','2013-06-06 12:45:00.0',FALSE,null),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-13 11:15:00.0','2013-06-13 12:45:00.0',FALSE,null),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-20 11:15:00.0','2013-06-20 12:45:00.0',FALSE,TRUE),
(null,3,'VO - Algodat2','blablabla über Algodat2 - VO',0,'Kuppelsaal',null,'2013-06-27 11:15:00.0','2013-06-27 12:45:00.0',FALSE,TRUE),

(null,2,'Prüfung - Algodat1','blablabla über Algodat1 - Prüfung',2,null,null,'2013-06-27 18:00:00.0','2013-06-27 21:00:00.0',TRUE,null),

(null,3,'UE - Algodat2 Gruppe 08','blablabla über Algodat2 - UE',1,'FH HS 4',null,'2013-06-10 17:00:00.0','2013-06-10 17:55:00.0',TRUE,null),
(null,3,'UE - Algodat2 Gruppe 08','blablabla über Algodat2 - UE',1,'FH HS 4',null,'2013-06-24 17:00:00.0','2013-06-24 17:55:00.0',TRUE,null),

(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-03-05 14:00:00.0','2013-03-05 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-03-12 14:00:00.0','2013-03-12 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-03-19 14:00:00.0','2013-03-19 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-04-09 14:00:00.0','2013-04-09 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-04-16 14:00:00.0','2013-04-16 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-04-23 14:00:00.0','2013-04-23 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-04-30 14:00:00.0','2013-04-30 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-05-07 14:00:00.0','2013-05-07 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-05-14 14:00:00.0','2013-05-14 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-05-28 14:00:00.0','2013-05-28 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-06-04 14:00:00.0','2013-06-04 18:00:00.0',TRUE,null),
(null,4,'VO - Techniksoziologie und Technikpsychologie','blablabla über Techniksoziologie und Technikpsychologie',0,'Seminarraum 187/2',null,'2013-06-11 14:00:00.0','2013-06-11 18:00:00.0',TRUE,null),

(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-03-05 13:30:00.0','2013-03-05 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-03-12 13:30:00.0','2013-03-12 19:00:00.0',FALSE,null),

(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-03-19 13:30:00.0','2013-03-19 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-03-26 13:30:00.0','2013-03-26 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-04-09 13:30:00.0','2013-04-09 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-04-16 13:30:00.0','2013-04-16 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-04-23 13:30:00.0','2013-04-23 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-04-30 13:30:00.0','2013-04-30 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-05-07 13:30:00.0','2013-05-07 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-05-14 13:30:00.0','2013-05-14 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-05-21 13:30:00.0','2013-05-21 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-05-28 13:30:00.0','2013-05-28 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-06-04 13:30:00.0','2013-06-04 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-06-11 13:30:00.0','2013-06-11 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-06-28 13:30:00.0','2013-06-28 19:00:00.0',FALSE,null),
(null,5,'VU - Forschungsmethoden','blablabla über Forschugnsmethoden',0,'Seminarraum 187/2',null,'2013-06-25 13:30:00.0','2013-06-25 19:00:00.0',FALSE,null),

(null,6,'VU - Softskills für TechnikerInnen','Vorbesprechung für Softskills für Technikerinnen',0,'EI 8 Pötzl HS',null,'2013-03-07 18:00:00.0','2013-03-07 19:00:00.0',FALSE,TRUE),
(null,6,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-14 18:00:00.0','2013-03-14 20:00:00.0',FALSE,TRUE),
(null,6,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-21 18:00:00.0','2013-03-21 20:00:00.0',FALSE,TRUE),
(null,6,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-28 18:00:00.0','2013-03-28 20:00:00.0',FALSE,TRUE),
(null,6,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-04 18:00:00.0','2013-03-04 20:00:00.0',FALSE,FALSE),
(null,6,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-11 18:00:00.0','2013-03-11 20:00:00.0',FALSE,TRUE),
(null,6,'VU - Softskills für TechnikerInnen','blablabla über Softskills für TechnikerInnen',0,'EI 8 Pötzl HS',null,'2013-03-18 18:00:00.0','2013-03-18 20:00:00.0',FALSE,TRUE),

(null,7,'Studieneingangsgespräch','da MUSS ich hingehen ... nicht vergessen!!!!!!!!!',1,'muss ich noch nachschauen',null,'2013-02-14 18:00:00.0','2013-02-14 19:00:00.0',TRUE,TRUE);

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