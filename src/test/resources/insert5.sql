INSERT INTO Curriculum (id,studyNumber,name,description,academicTitle,ectsChoice,ectsFree,ectsSoftskill) VALUES
(null,'033 533','Data Engineering & Statistics','Data Engineering und so weiter','Bachelor',10,20,30),
(null,'033 534','Medieninformatik und Visual Computing','Spiele Graphik und so weiter','Bachelor',10,20,30),
(null,'033 535','Medizinische Informatik','Medizinische Informatik und so weiter','Bachelor',10,20,30);

INSERT INTO Module (id,name,description,completeAll) VALUES
(null,'Algebra und Diskrete Mathematik','Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweistechniken und Sätze in den Teilgebieten Algebra (v.a. algebraische Strukturen und lineare Algebra) und Diskrete Mathematik (v.a Kombinatorik und Graphentheorie). Es setzt sich aus einem Vorlesungsteil und einem begleitenden Übungsteil zusammen, der der Vertiefung der Vorlesungsinhalte und der Entwicklung von Fertigkeiten zur Erstellung korrekter mathematischer Beweise und der mathematischen Modellierung und Analyse praktischer Problemstellungen dient.',TRUE),
(null,'Algorithmen und Datenstrukturen','Dieses Modul behandelt folgende Inhalte: Analyse von Algorithmen (asymptotisches Laufzeitverhalten, Omega, O- und Theta-Notation); fundamentale Datentypen und Datenstrukturen; Sortieren und Suchen; grundlegende Graphenalgorithmen; Problemlösungsstrategien und Optimierung mit exakten, approximativen und heuristischen Verfahren; randomisierte Algorithmen;grundlegende geometrische Algorithmen.',TRUE),
(null,'Freie Wahlfächer','Da kann man beliebige Sachen raussuchen',FALSE),
(null,'Studieneingangsgespräch','Muss am Anfang des Studiums gemacht werden',TRUE);

INSERT INTO InCurriculum (curriculum,module,obligatory) VALUES
(0,3,TRUE),
(0,1,TRUE),
(1,3,TRUE),
(1,1,TRUE),
(1,2,FALSE),
(2,3,TRUE);