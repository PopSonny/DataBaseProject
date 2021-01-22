CREATE SCHEMA IF NOT EXISTS `proiectbd4`;
USE `proiectbd4` ;

SET SQL_SAFE_UPDATES = 0;#ptr update/delete safe option 

#tabelele din proiect sunt:
#clasamentechipe
#clasamentindividual
#echipa
#invitatieechipa
#invitatieindividual
#istoricechipe
#istoricindividual
#jocechipa
#jocindividual
#jucator
#tipdejoc

#tabel ptr echipa aici o sa stocam echipele create de useri, 
#fiecare echipa avand un nume unic, o parola ptr ca alti jucatori sa intre in ea
#si data incrierii
create table echipa
(
id_echipa int primary key auto_increment,
nume varchar(30) not null,
passwordE varchar(30) not null,
data_inscrierii date not null
);

#tabel ptr tipurile de joc care o sa fie folosite in aplicatie
#doar administratorul o sa poate creeze, sa dea update sau sa stearga din acest tabel
create table tipDeJoc
(
numeJoc varchar(30) primary key
);

#unul dintre tabelele principale ale acestui proiect este tabelul jucator
#fiecare jucator are un nickname unic, o parola, data nasterii si data inregistratrii in aplicatie
#de asemenea fiecare jucator are un camp pentru echipa,
#acesta poate opta sa intre intr-o echipa sau nu 
create table jucator
(
id_jucator int primary key auto_increment,
nume varchar(30) not null,
passwordUser varchar(30) not null,
data_inscrierii date not null,
#check (data_inscriereii <= (select current_date())),
data_nasterii date not null,
#check (data_nasterii <= (select current_date())),
id_echipa int default NULL,
#daca cumva echipa este stearsa jucatorul o sa paraseasca echipa automat
#si o sa devina din nou fara echipa
foreign key(id_echipa) references echipa(id_echipa)
on delete set null
);

#tabelul ptr jocindividual este de asemenea un tabel important in aplicatia de fata
#in el avem tipul de joc sah, table etc, id-urile jucatorului 1 si jucatorului 2
#numar de partide o sa fie numarul maxim de partide care se pot juca
#numar partide jucate este numarul de partide jucate deja
#data de inceput si de sfarsit o sa le pun automat
#scor1 si scor2 arata punctajul de pana acuma a fiecarui jucator
#invigatorul arata id-ul jucatorului castigator
create table jocIndividual
(
idJocIndividual int primary key auto_increment,
tipJoc varchar(30) not null,
jucator1 int not null,
jucator2 int not null,
check (jucator1 != jucator2),
nrPartide int not null,
check (nrPartide <=100),
nrPartideJucate int not null,
check (nrPartideJucate <=100),
dataInceputJoc date not null,
dataSfarsitJoc date default null,
check (dataInceputJoc <= dataSfarsitJoc),
scorJucator1 int not null,
scorJucator2 int not null,
check (scorJucator1 != scorJucator2),
invingator int default null,
#daca cumva unul dintre campurile din tabelul tata
#o sa fie sterse fiecare informatie despre acestia
#o sa fie stersa din aplicatie, acest lucru este 
#posibil doar administratorului
foreign key(tipJoc) references tipDeJoc(numeJoc)
on delete cascade,
foreign key (jucator1) references jucator (id_jucator)
on delete cascade,
foreign key (jucator2) references jucator (id_jucator)
on delete cascade,
foreign key (invingator) references jucator (id_jucator)
on delete cascade
);

#tabelul ptr joc de echipa este in mare parte la fel cu cel de 
#joc individual doar ca echipele joaca intre ele
create table jocEchipa
(
idJocEchipa int primary key auto_increment,
tipJoc varchar(30) not null,
echipa1 int not null,
echipa2 int not null,
check (echipa1 != echipa2),
nrPartide int not null,
check (nrPartide <=100),
nrPartideJucate int not null,
check (nrPartideJucate <=100),
dataInceputJoc date not null,
dataSfarsitJoc date default null,
check (dataInceputJoc <= dataSfarsitJoc),
scorEchipa1 int not null,
scorEchipa2 int not null,
check (scorEchipa1 != scorEchipa2),
invingator int default null,
#daca cumva unul dintre campurile din tabelul tata
#o sa fie sterse fiecare informatie despre acestia
#o sa fie stersa din aplicatie, acest lucru este 
#posibil doar administratorului
foreign key(tipJoc) references tipDeJoc(numeJoc)
on delete cascade,
foreign key (echipa1) references echipa (id_echipa)
on delete cascade,
foreign key (echipa2) references echipa (id_echipa)
on delete cascade,
foreign key (invingator) references echipa (id_echipa)
on delete cascade
);

#cu acest tabel o sa lucram pentru a arata care este istoricul
#jocurilor individuale, in el o sa avem id-urile celor doi jucatori,
#id-ul castigatorului
create table istoricIndividual
(
idIstoricIndividual int primary key auto_increment,
jucator1 int not null,
jucator2 int not null,
check (jucator1 != jucator2),
idJoc int not null,
invingator int default null,
#daca cumva unul dintre campurile din tabelul tata
#o sa fie sterse fiecare informatie despre acestia
#o sa fie stersa din aplicatie, acest lucru este 
#posibil doar administratorului
foreign key (jucator1) references jucator (id_jucator)
on delete cascade,
foreign key (jucator2) references jucator (id_jucator)
on delete cascade,
foreign key (idJoc) references jocIndividual (idJocIndividual)
on delete cascade,
foreign key (invingator) references jucator (id_jucator)
on delete cascade
);

#acest tabel este asemanator cu cel din fata lui
#doar ca este pentru echipe
create table istoricEchipe
(
idIstoricEchipa int primary key auto_increment,
echipa1 int not null,
echipa2 int not null,
check (echipa1 != echipa2),
idJoc int not null,
invingator int default null,
#daca cumva unul dintre campurile din tabelul tata
#o sa fie sterse fiecare informatie despre acestia
#o sa fie stersa din aplicatie, acest lucru este 
#posibil doar administratorului
foreign key (echipa1) references echipa (id_echipa)
on delete cascade,
foreign key (echipa2) references echipa (id_echipa)
on delete cascade,
foreign key (idJoc) references jocEchipa (idJocEchipa)
on delete cascade,
foreign key (invingator) references echipa (id_echipa)
on delete cascade
);

#tabel pentru a clasa echipele 
#ele o sa fie clasate dupa numarul de castiguri
create table clasamentEchipe
(
idEchipaC int primary key auto_increment,
numeEchipa varchar(30) not null,
jocuriCastigate int default 0,
#daca echipa este stearsa din baza de date 
#aceasta trebuie sa fie stearsa si din clasament
foreign key (idEchipaC) references echipa (id_echipa)
on delete cascade
);

#tabel pentru clasarea jucatorilor 
#la fel ei o sa fie clasati dupa numarul de invingeri
create table clasamentIndividual
(
idJucatorC int primary key auto_increment,
numeJucator varchar(30) not null,
jocuriCastigate int default 0, 
#daca jucatorul este sters din baza de date 
#aceasta trebuie sa fie sters si din clasament
foreign key (idJucatorC) references jucator (id_jucator)
on delete cascade
);

#tabel pentru organizarea invitatilor care o sa fie trimise de
#un jucator spre alt jucator, un jucator isi alege oponentul si 
#ce tip de joc doreste sa joace, iar adversarul o sa accepte din 
#interfata lui, in tabel avem urmatoarele campuri: 
#idJucator1 si idJucator2 care sunt id-urile celor 2 jucatori care 
#o sa joace jocul, numele celor doi si tipul de joc ales de primul jucator
create table invitatieIndividual
(
idInvitatie int primary key auto_increment,
idJucator1 int not null,
idJucator2 int not null,
check (idJucator1 != idJucator2),
numeJucator1 varchar(30) not null,
numeJucator2 varchar(30) not null,
check (numeJucator1 != numeJucator2),
tipJoc varchar(30) not null,
#daca cumva unul dintre campurile din tabelul tata
#o sa fie sterse fiecare informatie despre acestia
#o sa fie stersa din aplicatie, acest lucru este 
#posibil doar administratorului
foreign key(tipJoc) references tipDeJoc(numeJoc)
on delete cascade,
foreign key(idJucator1) references jucator (id_jucator)
on delete cascade,
foreign key(idJucator2) references jucator (id_jucator)
on delete cascade
);

#acest tabel este in mare parte la fel ca cel dinainte
#doar ptr echipe
create table invitatieEchipa
(
idInvitatie int primary key auto_increment,
idEchipa1 int not null,
idEchipa2 int not null,
check (idEchipa1 != idEchipa2),
numeEchipa1 varchar(30) not null,
numeEchipa2 varchar(30) not null,
check (numeEchipa1 != numeEchipa2),
tipJoc varchar(30) not null,
#daca cumva unul dintre campurile din tabelul tata
#o sa fie sterse fiecare informatie despre acestia
#o sa fie stersa din aplicatie, acest lucru este 
#posibil doar administratorului
foreign key(tipJoc) references tipDeJoc(numeJoc)
on delete cascade,
foreign key(idEchipa1) references echipa (id_echipa)
on delete cascade,
foreign key(idEchipa2) references echipa (id_echipa)
on delete cascade
);

################################################################################
################################################################################
################################################################################

#proceduri

#una dintre cele mai importante proceduri, ea introduce in baza
#de date jucatori, ca si parametri avem numele, data nasteri si parola
delimiter //
create procedure introducereJucator(nume varchar(30), data_n date, pass varchar(30))
begin
#verificam ca nu exista nickname in tabel
set @exista =null;
select jucator.nume
into @exista
from jucator
where jucator.nume=nume;
if @exista is null then
#daca nu este o sa inseram in tabela
insert into jucator(nume,passwordUser,data_inscrierii,data_nasterii,id_echipa) values(nume,pass,current_date(),data_n,null);
#ca sa ne scutim de lucru in plus o sa adaugam si in clasament jucatorul
#si o sa ii setam numarul de invingeri la 0
insert into clasamentindividual(numeJucator,jocuriCastigate) values(nume,0);
end if;
end//
delimiter ;

#procedura asemanatoare cu cea de dinainte, doar ca pentru echipe
#diferenta este ca aici o echipa are nevoie de o parola
delimiter //
create procedure introducereEchipa(nume varchar(30), pass varchar(30))
begin
#verificam ca nu exista nickname de echipa in tabel
set @exista=null;
select echipa.nume
into @exista
from echipa
where echipa.nume=nume;
if @exista is null then
#daca nu este inseram echipa
insert into echipa(nume,passwordE,data_inscrierii) values(nume,pass,current_date());
#la fel ca sa nu ne complicam adaugam si la clasament echipa
#cu un numar de victorii egale cu 0
insert into clasamentechipe(numeEchipa,jocuriCastigate) values(nume,0);
end if;
end//
delimiter ;

#aceasta procedura o sa o folosim atunci cand un utilizator doreste
#sa intre intr-o echipa, el trebuie sa specifice numele echipei 
#si sa aiba parola de intrare in echipa
delimiter //
create procedure joinEchipa(nume varchar(30),pass varchar(30), idJucator int)
begin
#cu un aux cautam id-ul echipei
set @aux=null;
select id_echipa
into @aux
from echipa
where nume=echipa.nume and pass=echipa.passwordE;
#daca aux nu este null, inseamna ca echipa exista 
#si parola specificata este buna
if @aux is not null then
#updatam jucatorului campul cu id_echipa cu aux
update jucator
set jucator.id_echipa=@aux 
where idJucator =jucator.id_jucator;
end if;
end//
delimiter ;

#procedura pentru invitatia la joc individual
#jucatorul trebuie sa specifice numele jucatorului 
#cu care doreste sa se joace si de asemenea si tipul de joc
delimiter //
create procedure invitatieJocIndividual(idJucatorCurent int, numeJucatorCurent varchar(30), numeJucatorInvitat varchar(30), tipJoc varchar(30))
begin
#cautam id_ul jucatorului invitat
set @aux=null;
select id_jucator
into @aux
from jucator
where numeJucatorInvitat=jucator.nume;
#cautam tipul jocului 
set @aux2=null;
select numeJoc
into @aux2
from tipdejoc
where tipdejoc.numeJoc=tipJoc;
#daca exista jucator cu numele specificat
if @aux is not null then
	#daca avem jucatorul 
	if @aux2 is not null then
	#daca avem acel tip de joc
    #doar atunci inseram
	insert into invitatieindividual(idJucator1,idJucator2,numeJucator1,numeJucator2,tipJoc) values(idJucatorCurent,@aux,numeJucatorCurent,numeJucatorInvitat,tipJoc);
	end if;
end if;

end//
delimiter ;

#procedura de adaugare a unei invitati ptr echipa
#asemanatoare cu cea de invitatie individual
delimiter //
create procedure invitatieJocEchipa(idEchipaCurenta int, numeEchipaCurenta varchar(30), numeEchipaInvitata varchar(30), tipJoc varchar(30))
begin
set @aux=null;
select id_echipa
into @aux
from echipa
where numeEchipaInvitata=echipa.nume;
#cautam tipul jocului 
set @aux2=null;
select numeJoc
into @aux2
from tipdejoc
where tipdejoc.numeJoc=tipJoc;
if @aux is not null then
	#daca exista echipa
	if @aux2 is not null then
	#daca exista tipul de joc dace nu este inseram
	insert into invitatieechipa(idEchipa1,idEchipa2,numeEchipa1,numeEchipa2,tipJoc) values(idEchipaCurenta,@aux,numeEchipaCurenta,numeEchipaInvitata,tipJoc);
    end if;
end if;

end//
delimiter ;

#procedura pentru vizualizarea invitatilor individuale
delimiter //
create procedure vedereinvitatiiIndividual(idJucator int)
begin
select *
from invitatieindividual
where invitatieindividual.idJucator2=idJucator;
end//
delimiter ;

#procedura pentru a vizualiza invitatile echipei
#aici trebuie trebuie sa verificam daca utilizatorul curent face parte dintr-o echipa 
#daca da poate sa vizualizeze invitatile si sa accepte
delimiter //
create procedure vedereinvitatiiEchipe(idEchipa int, idJucator int)
begin

set @aux=null;
select id_echipa
into @aux
from jucator
where idJucator=id_jucator;
#verificam daca face parte dintr-o echipa
if @aux is not null then
#daca da aratam invitatiile
select *
from invitatieechipa
where invitatieechipa.idEchipa2=idEchipa;
end if;
end//
delimiter ;

#procedura pentru generarea jocului individual
#la apasarea unui buton se va generea un joc cu parametri dati
#si totodata o sa stergem din tabela invitatieindividual meciul
delimiter //
create procedure generareJocIndividual(idJucator1 int, idJucator2 int, scorJucator1 int, scorJucator2 int, nr_partide int,nr_partideJucate int, invingator int, tipjoc varchar(30))
begin
#toti parametrii vin din java inainte de call de procedura asta
insert into jocindividual(tipJoc,jucator1,jucator2,nrPartide,nrPartideJucate,dataInceputJoc,dataSfarsitJoc,scorJucator1,scorJucator2,invingator) 
values(tipjoc,idJucator1,idJucator2,nr_partide,nr_partideJucate,current_date(),current_date(),scorJucator1,scorJucator2,invingator);
#delete from invitatii
delete from invitatieindividual where idJucator1=invitatieindividual.idJucator1 and idJucator2=invitatieindividual.idJucator2;
end//
delimiter ;


#refuz transformam in back la interfata jocul este de tip campionat
#deci fiecare invitatie trebuie acc dar se poate alege oricare

#procedura pentru generarea jocului pe echipe
#este asemanatoare cu procedura de la individuale
#doar ca este pe echipe
delimiter //
create procedure generareJocEchipa(idEchipa1 int, idEchipa2 int, scorEchipa1 int, scorEchipa2 int, nr_partide int,nr_partideJucate int, invingator int, tipjoc varchar(30))
begin
#toti parametrii vin din java inainte de call de procedura asta
insert into jocechipa(tipJoc,echipa1,echipa2,nrPartide,nrPartideJucate,dataInceputJoc,dataSfarsitJoc,scorEchipa1,scorEchipa2,invingator) 
values(tipjoc,idEchipa1,idEchipa2,nr_partide,nr_partideJucate,current_date(),current_date(),scorEchipa1,scorEchipa2,invingator);
#delete from invitatii
delete from invitatieechipa where idEchipa1=invitatieechipa.idEchipa1 and idEchipa2=invitatieechipa.idEchipa2;
end//
delimiter ;

#procedura ptr clasament Individual
delimiter //
create procedure clasamentIndividual()
begin
SELECT * FROM proiectbd4.clasamentindividual 
#sortam dupa jocurile castigate
order by jocuriCastigate desc;
end//
delimiter ;

#procedura ptr clasament Echipe
delimiter //
create procedure clasamentEchipe()
begin
SELECT * FROM proiectbd4.clasamentechipe
#sortam dupa jocurile castigate
order by jocuriCastigate desc;
end//
delimiter ;

#procedura ptr istoric individual
delimiter //
create procedure istoricIndividual()
begin
SELECT * FROM proiectbd4.jocindividual
#sortam dupa finalizarea meciului
order by dataSfarsitJoc desc;
end//
delimiter ;

#procedura ptr istoric echipe
delimiter //
create procedure istoricEchipe()
begin
SELECT * FROM proiectbd4.jocechipa
#sortam dupa finalizarea meciului
order by dataSfarsitJoc desc;
end//
delimiter ;

#procedura ptr crearea unui nou tip de joc
#doar administratorul poate face acest lucru
delimiter //
create procedure creareTipJocNou(nume varchar(30))
begin
insert into tipdejoc values(nume); 
end //
delimiter ;

################################################################################
################################################################################
################################################################################

#triggere

#trigger ptr inserare in istoricul individual
#de fiecare daca cand un joc individual este inregistrat
#in tabelul ptr istoric o sa inseram acel joc
delimiter //
create trigger istoricindividualTrigger
after insert
on jocindividual
for each row
begin
#efectiv inseram in tabelul cu istoric datele noi
insert into istoricindividual(jucator1,jucator2,idJoc,invingator)
values(new.jucator1,new.jucator2,new.idJocIndividual,new.invingator);
end //
delimiter ;

#trigger ptr clasamentindividual
#ideea aici este de fiecare daca cand un joc este realizat
#cautam id-ul castigatorului si dupa ce il gasim(suntem siguri
#ca exista deoarece fiecare jucator este deja in clasament)
#o sa punem un +1 la nr de jocuri castigate
#merge
delimiter //
create trigger clasamentindividualTrigger
after insert
on jocindividual
for each row
begin
#updatam cu un +1 nr de jocuri castigate de invingator
update clasamentindividual set jocuriCastigate=jocuriCastigate+1 where new.invingator=idJucatorC;
end //
delimiter ;

#trigger ptr introducere in istoricEchipe
#acesta este asemanator cu triggerul ptr
#introducere istoricIndividual
delimiter //
create trigger istoricEchipaTrigger
after insert
on jocechipa
for each row
begin
#introducem efectiv noile date in tabelul istoricechipe
insert into istoricechipe(echipa1,echipa2,idJoc,invingator)
values(new.echipa1,new.echipa2,new.idJocEchipa,new.invingator);
end //
delimiter ;

#trigger ptr clasamentul de echipe
#la fel ca pentru cel individual
#adunam efectiv un +1 la echipa castigatoare
delimiter //
create trigger clasamentEchipeTrigger
after insert
on jocechipa
for each row
begin
update clasamentechipe set jocuriCastigate=jocuriCastigate+1 where new.invingator=idEchipaC;
end //
delimiter ;

#trigger ptr stergere invitatie individuala
#dupa ce un utilizator a acceptat invitatia
#aceasta trebuie sa se stearga din tabel
#si sa nu mai apara din nou in lista cu invitatii a utilizatorului curent
delimiter //
create trigger stergereInvitatieIndividual
after insert
on istoricindividual
for each row
begin
#cautam id-urile celor 2 jucatori care joaca si stergem randul acela din invitatii
delete from invitatieindividual where new.jucator1=invitatieindividual.idJucator1 and new.jucator2=invitatieindividual.idJucator2;
end //
delimiter ;

#trigger ptr stergere invitatie echipa
#acelasi procedeu ca cel de dinainte de la individual
#cautam dupa id-uri si stergem invitatia
delimiter //
create trigger stergereInvitatieEchipa
after insert
on istoricechipe
for each row
begin
delete from invitatieechipa where new.echipa1=invitatieechipa.idEchipa1 and new.echipa2=invitatieechipa.idEchipa2;
end //
delimiter ;


################################################################################
################################################################################
################################################################################

#securizare de date

DROP USER IF EXISTS 'jucator'@'localhost';
Create user 'jucator'@'localhost' identified by 'player';

Grant select,insert,update  on proiectbd4.clasamentechipe to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.clasamentindividual to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.echipa to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.invitatieechipa to 'jucator'@'localhost'; 

Grant select,insert,update  on proiectbd4.invitatieindividual to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.istoricechipe to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.istoricindividual to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.jocechipa to 'jucator'@'localhost';
 
Grant select,insert,update  on proiectbd4.jocindividual to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.jucator to 'jucator'@'localhost'; 
Grant select,insert,update  on proiectbd4.tipdejoc to 'jucator'@'localhost'; 


SHOW GRANTS FOR 'jucator'@'localhost';

################################################################################
################################################################################
################################################################################

#popularea bazei

#creare tipuri de joc
call proiectbd4.creareTipJocNou('sah');
call proiectbd4.creareTipJocNou('table');
call proiectbd4.creareTipJocNou('remi');

SELECT * FROM proiectbd4.tipdejoc;

#populare cu jucatori
call proiectbd4.introducereJucator('Jucator1', '1999-11-11','1234');
call proiectbd4.introducereJucator('Jucator2', '2000-11-11','1234');
call proiectbd4.introducereJucator('Jucator3', '2001-11-11','1234');
call proiectbd4.introducereJucator('Jucator4', '2002-11-11','1234');
call proiectbd4.introducereJucator('Jucator5', '2003-11-11','1234');
call proiectbd4.introducereJucator('Jucator6', '2004-11-11','1234');
call proiectbd4.introducereJucator('Jucator7', '2005-11-11','1234');
call proiectbd4.introducereJucator('Jucator8', '2006-11-11','1234');
call proiectbd4.introducereJucator('Jucator9', '2007-11-11','1234');
call proiectbd4.introducereJucator('Jucator10', '2008-11-11','1234');
call proiectbd4.introducereJucator('Jucator11', '2009-11-11','1234');
call proiectbd4.introducereJucator('Jucator12', '2010-11-11','1234');
call proiectbd4.introducereJucator('Jucator13', '2011-11-11','1234');
call proiectbd4.introducereJucator('Jucator14', '2012-11-11','1234');
call proiectbd4.introducereJucator('Jucator15', '2013-11-11','1234');
call proiectbd4.introducereJucator('Jucator16', '2014-11-11','1234');
call proiectbd4.introducereJucator('Jucator17', '2015-11-11','1234');
call proiectbd4.introducereJucator('Jucator18', '2016-11-11','1234');
call proiectbd4.introducereJucator('Jucator19', '2017-11-11','1234');
call proiectbd4.introducereJucator('Jucator20', '2018-11-11','1234');
call proiectbd4.introducereJucator('Jucator21', '2019-11-11','1234');
call proiectbd4.introducereJucator('Jucator22', '2020-11-11','1234');
call proiectbd4.introducereJucator('Jucator23', '2021-11-11','1234');
call proiectbd4.introducereJucator('Jucator24', '2022-11-11','1234');
call proiectbd4.introducereJucator('Jucator25', '2023-11-11','1234');

#ptr aceeasi data
call proiectbd4.introducereJucator('Jucator26', '2021-11-11','1234');
call proiectbd4.introducereJucator('Jucator27', '2022-11-11','1234');
call proiectbd4.introducereJucator('Jucator28', '2023-11-11','1234');

SELECT * FROM proiectbd4.jucator;

#populare cu echipe
call proiectbd4.introducereEchipa('Echipa1', '1234');
call proiectbd4.introducereEchipa('Echipa2', '1234');
call proiectbd4.introducereEchipa('Echipa3', '1234');
call proiectbd4.introducereEchipa('Echipa4', '1234');
call proiectbd4.introducereEchipa('Echipa5', '1234');
call proiectbd4.introducereEchipa('Echipa6FaraJucatori', '1234');

select * from echipa;


#introducerea unor jucatori in echipe fiecare echipa o sa aiba 4 jucatori si ultimi 5 nu o sa fie in echipe
#pentru simplitate si verificare parolele o sa fie toate 1234
#demonstram la predare cum se adauga un jcator cu alta parola
call proiectbd4.joinEchipa('Echipa1', '1234', 1);
call proiectbd4.joinEchipa('Echipa1', '1234', 2);
call proiectbd4.joinEchipa('Echipa1', '1234', 3);
call proiectbd4.joinEchipa('Echipa1', '1234', 4);

call proiectbd4.joinEchipa('Echipa2', '1234', 5);
call proiectbd4.joinEchipa('Echipa2', '1234', 6);
call proiectbd4.joinEchipa('Echipa2', '1234', 7);
call proiectbd4.joinEchipa('Echipa2', '1234', 8);

call proiectbd4.joinEchipa('Echipa3', '1234', 9);
call proiectbd4.joinEchipa('Echipa3', '1234', 10);
call proiectbd4.joinEchipa('Echipa3', '1234', 11);
call proiectbd4.joinEchipa('Echipa3', '1234', 12);

call proiectbd4.joinEchipa('Echipa4', '1234', 13);
call proiectbd4.joinEchipa('Echipa4', '1234', 14);
call proiectbd4.joinEchipa('Echipa4', '1234', 15);
call proiectbd4.joinEchipa('Echipa4', '1234', 16);

call proiectbd4.joinEchipa('Echipa5', '1234', 17);
call proiectbd4.joinEchipa('Echipa5', '1234', 18);
call proiectbd4.joinEchipa('Echipa5', '1234', 19);
call proiectbd4.joinEchipa('Echipa5', '1234', 20);
#ptr echipa cu cei mai multi jucatori
call proiectbd4.joinEchipa('Echipa5', '1234', 21);

#afisam din nou jucatori dar acuma ei fac parte din echipe

select id_jucator,nume, id_echipa from jucator;

#invitatii jocuri individuale
#primul trimite la al doilea
#primul parametru este id-ul primului jucator



call proiectbd4.invitatieJocIndividual(1, 'Jucator1', 'Jucator2', 'sah');
call proiectbd4.invitatieJocIndividual(1, 'Jucator1', 'Jucator3', 'sah');
call proiectbd4.invitatieJocIndividual(1, 'Jucator1', 'Jucator4', 'sah');
call proiectbd4.invitatieJocIndividual(1, 'Jucator1', 'Jucator5', 'sah');
call proiectbd4.invitatieJocIndividual(1, 'Jucator1', 'Jucator6', 'sah');

#ptr cel mai bun jucator
call proiectbd4.generareJocIndividual(1, 2, 36, 20, 9, 6, 1, 'sah');
call proiectbd4.generareJocIndividual(1, 3, 35, 20, 8, 6, 1, 'sah');
call proiectbd4.generareJocIndividual(1, 4, 35, 20, 8, 6, 1, 'sah');
call proiectbd4.generareJocIndividual(1, 5, 35, 20, 8, 6, 1, 'sah');
call proiectbd4.generareJocIndividual(1, 6, 35, 20, 8, 6, 1, 'sah');


call proiectbd4.invitatieJocEchipa(2, 'Echipa2', 'Echipa1', 'sah');
call proiectbd4.invitatieJocEchipa(3, 'Echipa3', 'Echipa1', 'sah');
call proiectbd4.invitatieJocEchipa(4, 'Echipa4', 'Echipa1', 'sah');
call proiectbd4.invitatieJocEchipa(5, 'Echipa5', 'Echipa1', 'sah');

#ptr cea mai buna echipa
call proiectbd4.generareJocEchipa(2, 1, 31, 40, 57, 89, 1, 'sah');
call proiectbd4.generareJocEchipa(3, 1, 32, 40, 55, 82, 1, 'sah');
call proiectbd4.generareJocEchipa(4, 1, 33, 40, 55, 82, 1, 'sah');
call proiectbd4.generareJocEchipa(5, 1, 34, 40, 55, 82, 1, 'sah');


#ptr cele mai multe invitatii primite
call proiectbd4.invitatieJocIndividual(4, 'Jucator4', 'Jucator1', 'sah');
call proiectbd4.invitatieJocIndividual(5, 'Jucator5', 'Jucator1', 'sah');
call proiectbd4.invitatieJocIndividual(6, 'Jucator6', 'Jucator1', 'sah');

#ptr cel mai popular tip de joc la categoria echipe
call proiectbd4.invitatieJocEchipa(2, 'Echipa2', 'Echipa1', 'table');
call proiectbd4.invitatieJocEchipa(3, 'Echipa3', 'Echipa2', 'table');
call proiectbd4.invitatieJocEchipa(4, 'Echipa4', 'Echipa3', 'table');
call proiectbd4.invitatieJocEchipa(5, 'Echipa5', 'Echipa4', 'table');


#ptr jucatorul care are cele mai multe castiguri si nu face parte dintr-o echipa
call proiectbd4.invitatieJocIndividual(1, 'Jucator1', 'Jucator28', 'sah');
call proiectbd4.generareJocIndividual(1, 28, 35, 20, 8, 6, 28, 'sah');

#ptr vedere nr de jocuri

call proiectbd4.invitatieJocIndividual(24, 'Jucator24', 'Jucator26', 'table');
call proiectbd4.invitatieJocIndividual(25, 'Jucator25', 'Jucator22', 'remi');

################################################################################
################################################################################
################################################################################

#interogari
#1 numele jucatorului care are cele mai multe castiguri
#2 numele Echipei care are cele mai multe castiguri
#3 numele jucatorilor care fac dintr-o echipa 
#4 numele jucatorilor care fac dintr-o echipa care a castigat cel putin 1 meci
#5 jucatori care nu fac parte dintr-o echipa
#6 echipele care nu au jucatori
#7. echipa cu cei mai multi jucatori
#8 cati jucatori sunt in fiecare echipa
#9 numarul de jucatori si de echipe din aplicatie
#10. jucatori care s-au nascut intre DATA
#11. nr de jocuri per total din aplicatie
#12 cel mai in varsta jucator
#13 jucatorul si echipa care s-au inregistrat ultimii
#14 datele de nastere cu cei mai multi jucatori
#15 cel mai mare nrDePartide de la individuale si cel mai mare nrDePartide de la echipe pe un meci
#16 jucatorul cu cele mai multe invitatii primite
#17 cel mai popular tip de joc de la invitatii echipe
#18 nr de invitatii din aplicatie
#19 nr de partide jucate individual si echipe
#20 jucatorul care are cele mai multe meciuri castigate dar nu face parte din nicio echipa


#1 numele jucatorului care are cele mai multe castiguri
select numeJucator , jocuriCastigate 
from clasamentindividual
order by jocuriCastigate desc
limit 1;

#2 numele Echipei care are cele mai multe castiguri
select numeEchipa , jocuriCastigate 
from clasamentechipe
order by jocuriCastigate desc
limit 1;

#3 numele jucatorilor care fac dintr-o echipa 
select j.nume as numeJucator, e.nume as numeEchipa
from jucator j
join echipa e on j.id_echipa=e.id_echipa;

#4 numele jucatorilor care fac dintr-o echipa care a castigat cel putin 1 meci
select j.nume as numeJucator, e.numeEchipa as numeEchipa
from clasamentechipe e
join jucator j on e.idEchipaC=j.id_echipa and e.jocuriCastigate>0;

#5 jucatori care nu fac parte dintr-o echipa
select nume 
from jucator 
where id_echipa is null;

#6 echipele care nu au jucatori
select e.nume as numeEchipa
from echipa e
where e.id_echipa not in(select id_echipa from jucator where id_echipa);

#7. echipa cu cei mai multi jucatori
select e.nume as numeEchipa,count(j.id_echipa) as nrJucatori
from jucator j
join echipa e on e.id_echipa=j.id_echipa
group by(j.id_echipa)
order by count(j.id_echipa) desc
limit 1;

#8 cati jucatori sunt in fiecare echipa
select e.nume as numeEchipa,count(j.id_echipa) 
from jucator j
join echipa e on e.id_echipa=j.id_echipa
group by(j.id_echipa)
order by count(j.id_echipa) desc;

#9 numarul de jucatori si de echipe din aplicatie
select (select count(j.id_jucator) from jucator j) as numarJucatori,
(select count(e.id_echipa) from echipa e) as numarEchipe;

#10. jucatori care s-au nascut intre DATA
select j.nume as numeJucator
from jucator j
where j.data_nasterii>='2011-11-11' and j.data_nasterii<='2017-11-11';

#11. nr de jocuri per total din aplicatie
select (select count(i.idIstoricIndividual) from istoricindividual i) as nrDeJocuriIndividuale,
(select count(e.idIstoricEchipa) from istoricechipe e) as nrDeJocuriEchipe;

#12 cel mai in varsta jucator
select nume from jucator 
order by data_nasterii asc
limit 1;

#13 jucatorul si echipa care s-au inregistrat ultimii
select (select i.nume from jucator i where i.id_jucator in(select max(i.id_jucator) from jucator i)) as UltimulJucatorInregistrat,
(select e.nume from echipa e where e.id_echipa in(select max(e.id_echipa) from echipa e)) as UltimaEchipaInregistrata;

#14 data sau datele de nastere cu cei mai multi jucatori
select j.data_nasterii, count(*) as nrTotal
from jucator j
group by j.data_nasterii
having nrTotal >1
order by nrTotal desc;

#15 cel mai mare nrDePartide de la individuale si cel mai mare nrDePartide de la echipe pe un meci
select (select i.nrPartide from jocindividual i where i.nrPartide in(select max(i.nrPartide) from jocindividual i)) as MaxNrPartideIndividual,
(select e.nrPartide from jocechipa e where e.nrPartide in(select max(e.nrPartide) from jocechipa e)) as MaxNrPartideEchipa;

#16 jucatorul cu cele mai multe invitatii primite
select j.numeJucator2 as numeJucator, count(idJucator2) as invitatii
from invitatieindividual j
group by j.idJucator2
order by count(idJucator2) desc
limit 1;

#17 cel mai popular tip de joc de la invitatii echipe
select e.tipJoc, count(tipJoc) as JocPopular
from invitatieechipa e
group by e.tipJoc
order by count(tipJoc) desc
limit 1;

#18 nr de invitatii din aplicatie
select (select count(idInvitatie)
from invitatieechipa)+(select count(idInvitatie)
from invitatieindividual) as nrDeInvitatii;

#19 nr de partide jucate individual si echipe
select (select sum(nrPartideJucate) from jocindividual) as partideIndividual,
(select sum(nrPartideJucate) from jocechipa) as partideEchipa;

#20 jucatorul care are cele mai multe meciuri castigate dar nu face parte din nicio echipa
select c.numeJucator , c.jocuriCastigate 
from clasamentindividual c
join jucator j on j.id_jucator=c.idJucatorC and j.id_echipa is null
order by c.jocuriCastigate desc
limit 1;


################################################################################
################################################################################
################################################################################


#vederi
#1 numele jucatorilor din baza de date in ordinea datei de nastere
#2 tipurile de joc si cate invitatii sunt ptr fiecare
#3 echipele care contin minim un jucator
#4 echipele care nu au jucat niciun meci


#1 numele jucatorilor din baza de date in ordinea datei de nastere
create view jucatoriData as
select j.nume as numeJucator, j.data_nasterii as dataNasterii
from jucator j
order by j.data_nasterii asc;

SELECT * FROM proiectbd4.jucatoridata;


#2 tipurile de joc si cate invitatii sunt ptr fiecare
create view tipdejocPlusNumar as
select t.numeJoc, count(j.tipJoc) as NrJocuri
from tipdejoc t
join invitatieindividual j on j.tipJoc=t.numeJoc
group by t.numeJoc;

SELECT * FROM proiectbd4.tipdejocplusnumar;

#3 echipele care contin minim un jucator
create view echipeCuJucatori as
select e.nume as numeEchipa
from echipa e
where e.id_echipa in(select id_echipa from jucator where id_echipa);

SELECT * FROM proiectbd4.echipecujucatori;

#4 echipele care nu au jucat niciun meci
create view echipefarameci as
select distinct e.nume
from echipa e
join istoricechipe i on e.id_echipa not in(select f.echipa1 from istoricechipe f) and e.id_echipa not in(select g.echipa2 from istoricechipe g); 

SELECT * FROM proiectbd4.echipefarameci;

