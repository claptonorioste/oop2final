/*
SQLyog Enterprise v10.42 
MySQL - 5.5.5-10.1.37-MariaDB : Database - elearning
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`elearning` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `elearning`;

/*Table structure for table `tblinstructorcert` */

DROP TABLE IF EXISTS `tblinstructorcert`;

CREATE TABLE `tblinstructorcert` (
  `inscertid` int(11) NOT NULL AUTO_INCREMENT,
  `certid` int(11) DEFAULT NULL,
  `dateacquired` date DEFAULT NULL,
  PRIMARY KEY (`inscertid`),
  KEY `certid` (`certid`),
  CONSTRAINT `tblinstructorcert_ibfk_1` FOREIGN KEY (`certid`) REFERENCES `tblutilcertification` (`certid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcert` */

/*Table structure for table `tblinstructorcorsectestquestion` */

DROP TABLE IF EXISTS `tblinstructorcorsectestquestion`;

CREATE TABLE `tblinstructorcorsectestquestion` (
  `inscorsectestquestid` int(11) NOT NULL AUTO_INCREMENT,
  `inscorsectestid` int(11) DEFAULT NULL,
  `questionno` int(11) DEFAULT NULL,
  `question` varchar(125) DEFAULT NULL,
  `answer` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`inscorsectestquestid`),
  KEY `inscorsectestid` (`inscorsectestid`),
  CONSTRAINT `tblinstructorcorsectestquestion_ibfk_1` FOREIGN KEY (`inscorsectestid`) REFERENCES `tblinstructorcoursesectiontest` (`inscorsectestid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcorsectestquestion` */

/*Table structure for table `tblinstructorcortestquestion` */

DROP TABLE IF EXISTS `tblinstructorcortestquestion`;

CREATE TABLE `tblinstructorcortestquestion` (
  `inscortestquestid` int(11) NOT NULL AUTO_INCREMENT,
  `inscoursetestid` int(11) DEFAULT NULL,
  `questionno` int(11) DEFAULT NULL,
  `question` varchar(125) DEFAULT NULL,
  `answer` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`inscortestquestid`),
  KEY `inscoursetestid` (`inscoursetestid`),
  CONSTRAINT `tblinstructorcortestquestion_ibfk_1` FOREIGN KEY (`inscoursetestid`) REFERENCES `tblinstructorcoursetest` (`inscoursetestid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcortestquestion` */

/*Table structure for table `tblinstructorcourse` */

DROP TABLE IF EXISTS `tblinstructorcourse`;

CREATE TABLE `tblinstructorcourse` (
  `inscourseid` int(11) NOT NULL AUTO_INCREMENT,
  `instructorid` int(11) DEFAULT NULL,
  `courseid` int(11) DEFAULT NULL,
  `datecreated` date DEFAULT NULL,
  `datepublished` date DEFAULT NULL,
  `courseprice` decimal(20,2) DEFAULT '0.00',
  PRIMARY KEY (`inscourseid`),
  KEY `courseid` (`courseid`),
  KEY `instructorid` (`instructorid`),
  CONSTRAINT `tblinstructorcourse_ibfk_1` FOREIGN KEY (`courseid`) REFERENCES `tblutilcourse` (`courseid`),
  CONSTRAINT `tblinstructorcourse_ibfk_2` FOREIGN KEY (`instructorid`) REFERENCES `tblinstructorinfo` (`instructorid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcourse` */

/*Table structure for table `tblinstructorcoursedesc` */

DROP TABLE IF EXISTS `tblinstructorcoursedesc`;

CREATE TABLE `tblinstructorcoursedesc` (
  `inscoursedescid` int(11) NOT NULL AUTO_INCREMENT,
  `orderno` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(125) DEFAULT NULL,
  `inscourseid` int(11) DEFAULT NULL,
  PRIMARY KEY (`inscoursedescid`),
  KEY `inscourseid` (`inscourseid`),
  CONSTRAINT `tblinstructorcoursedesc_ibfk_1` FOREIGN KEY (`inscourseid`) REFERENCES `tblinstructorcourse` (`inscourseid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursedesc` */

/*Table structure for table `tblinstructorcoursediscount` */

DROP TABLE IF EXISTS `tblinstructorcoursediscount`;

CREATE TABLE `tblinstructorcoursediscount` (
  `inscoursedescid` int(11) NOT NULL AUTO_INCREMENT,
  `datefrom` date DEFAULT NULL,
  `dateto` date DEFAULT NULL,
  `inscourseid` int(11) DEFAULT NULL,
  PRIMARY KEY (`inscoursedescid`),
  KEY `inscourseid` (`inscourseid`),
  CONSTRAINT `tblinstructorcoursediscount_ibfk_1` FOREIGN KEY (`inscourseid`) REFERENCES `tblinstructorcourse` (`inscourseid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursediscount` */

/*Table structure for table `tblinstructorcoursesched` */

DROP TABLE IF EXISTS `tblinstructorcoursesched`;

CREATE TABLE `tblinstructorcoursesched` (
  `inscourseschedid` int(11) NOT NULL AUTO_INCREMENT,
  `datefrom` date DEFAULT NULL,
  `dateto` date DEFAULT NULL,
  PRIMARY KEY (`inscourseschedid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursesched` */

/*Table structure for table `tblinstructorcoursesection` */

DROP TABLE IF EXISTS `tblinstructorcoursesection`;

CREATE TABLE `tblinstructorcoursesection` (
  `inscoursesecid` int(11) NOT NULL AUTO_INCREMENT,
  `inscourseid` int(11) DEFAULT NULL,
  `sectionno` int(11) DEFAULT NULL,
  `sectiontitle` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`inscoursesecid`),
  KEY `inscourseid` (`inscourseid`),
  CONSTRAINT `tblinstructorcoursesection_ibfk_1` FOREIGN KEY (`inscourseid`) REFERENCES `tblinstructorcourse` (`inscourseid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursesection` */

/*Table structure for table `tblinstructorcoursesectioncontent` */

DROP TABLE IF EXISTS `tblinstructorcoursesectioncontent`;

CREATE TABLE `tblinstructorcoursesectioncontent` (
  `inscoursesecconid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(75) DEFAULT NULL,
  `contenttypeid` int(11) DEFAULT NULL,
  `contentlocation` varchar(125) DEFAULT NULL,
  `titleorderno` int(11) DEFAULT NULL,
  PRIMARY KEY (`inscoursesecconid`),
  KEY `contenttypeid` (`contenttypeid`),
  CONSTRAINT `tblinstructorcoursesectioncontent_ibfk_1` FOREIGN KEY (`contenttypeid`) REFERENCES `tblutilcontenttype` (`contenttypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursesectioncontent` */

/*Table structure for table `tblinstructorcoursesectiontest` */

DROP TABLE IF EXISTS `tblinstructorcoursesectiontest`;

CREATE TABLE `tblinstructorcoursesectiontest` (
  `inscorsectestid` int(11) NOT NULL AUTO_INCREMENT,
  `inscoursesecid` int(11) DEFAULT NULL,
  `testtypeid` int(11) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  PRIMARY KEY (`inscorsectestid`),
  KEY `inscoursesecid` (`inscoursesecid`),
  KEY `testtypeid` (`testtypeid`),
  CONSTRAINT `tblinstructorcoursesectiontest_ibfk_1` FOREIGN KEY (`inscoursesecid`) REFERENCES `tblinstructorcoursesection` (`inscoursesecid`),
  CONSTRAINT `tblinstructorcoursesectiontest_ibfk_2` FOREIGN KEY (`testtypeid`) REFERENCES `tblutiltesttype` (`testtypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursesectiontest` */

/*Table structure for table `tblinstructorcoursetest` */

DROP TABLE IF EXISTS `tblinstructorcoursetest`;

CREATE TABLE `tblinstructorcoursetest` (
  `inscoursetestid` int(11) NOT NULL AUTO_INCREMENT,
  `inscourseid` int(11) DEFAULT NULL,
  `testtypeid` int(11) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  PRIMARY KEY (`inscoursetestid`),
  KEY `inscourseid` (`inscourseid`),
  KEY `testtypeid` (`testtypeid`),
  CONSTRAINT `tblinstructorcoursetest_ibfk_1` FOREIGN KEY (`inscourseid`) REFERENCES `tblinstructorcourse` (`inscourseid`),
  CONSTRAINT `tblinstructorcoursetest_ibfk_2` FOREIGN KEY (`testtypeid`) REFERENCES `tblutiltesttype` (`testtypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorcoursetest` */

/*Table structure for table `tblinstructordegree` */

DROP TABLE IF EXISTS `tblinstructordegree`;

CREATE TABLE `tblinstructordegree` (
  `insdegreeid` int(11) NOT NULL AUTO_INCREMENT,
  `degreeid` int(11) DEFAULT NULL,
  `yeargraduated` int(11) DEFAULT NULL,
  `schoolid` int(11) DEFAULT NULL,
  PRIMARY KEY (`insdegreeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructordegree` */

/*Table structure for table `tblinstructorinfo` */

DROP TABLE IF EXISTS `tblinstructorinfo`;

CREATE TABLE `tblinstructorinfo` (
  `instructorid` int(11) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(75) DEFAULT NULL,
  `firstname` varchar(75) DEFAULT NULL,
  `middlename` varchar(75) DEFAULT NULL,
  `bday` date DEFAULT NULL,
  `contactno` varchar(75) DEFAULT NULL,
  `address` varchar(125) DEFAULT NULL,
  `emailadd` varchar(75) DEFAULT NULL,
  `countryid` int(11) DEFAULT NULL,
  `useracctid` int(11) DEFAULT NULL,
  PRIMARY KEY (`instructorid`),
  KEY `countryid` (`countryid`),
  KEY `useracctid` (`useracctid`),
  CONSTRAINT `tblinstructorinfo_ibfk_1` FOREIGN KEY (`countryid`) REFERENCES `tblutilcountry` (`countryid`),
  CONSTRAINT `tblinstructorinfo_ibfk_2` FOREIGN KEY (`useracctid`) REFERENCES `tblsecuseracct` (`useracctid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblinstructorinfo` */

/*Table structure for table `tblsecgroup` */

DROP TABLE IF EXISTS `tblsecgroup`;

CREATE TABLE `tblsecgroup` (
  `groupid` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(75) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '1-Active,0-Inactive',
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tblsecgroup` */

insert  into `tblsecgroup`(`groupid`,`groupname`,`status`) values (1,'Admin',1),(2,'Student',1),(3,'Instructor',1);

/*Table structure for table `tblsecgrouppriv` */

DROP TABLE IF EXISTS `tblsecgrouppriv`;

CREATE TABLE `tblsecgrouppriv` (
  `grpprivid` int(11) NOT NULL AUTO_INCREMENT,
  `groupid` int(11) DEFAULT NULL,
  `privid` int(11) DEFAULT NULL,
  `moduleid` int(11) DEFAULT NULL,
  `dateadded` datetime DEFAULT NULL,
  PRIMARY KEY (`grpprivid`),
  KEY `groupid` (`groupid`),
  KEY `fkprivid` (`privid`),
  KEY `moduleid` (`moduleid`),
  CONSTRAINT `fkprivid` FOREIGN KEY (`privid`) REFERENCES `tblsecprivileges` (`privid`),
  CONSTRAINT `tblsecgrouppriv_ibfk_1` FOREIGN KEY (`groupid`) REFERENCES `tblsecgroup` (`groupid`),
  CONSTRAINT `tblsecgrouppriv_ibfk_2` FOREIGN KEY (`moduleid`) REFERENCES `tblsecmodules` (`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblsecgrouppriv` */

/*Table structure for table `tblsecgroupusers` */

DROP TABLE IF EXISTS `tblsecgroupusers`;

CREATE TABLE `tblsecgroupusers` (
  `grpuserid` int(11) NOT NULL AUTO_INCREMENT,
  `useracctid` int(11) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  `dateadded` datetime DEFAULT NULL,
  PRIMARY KEY (`grpuserid`),
  KEY `fkuseracctid` (`useracctid`),
  KEY `fkgroupid` (`groupid`),
  CONSTRAINT `fkgroupid` FOREIGN KEY (`groupid`) REFERENCES `tblsecgroup` (`groupid`),
  CONSTRAINT `fkuseracctid` FOREIGN KEY (`useracctid`) REFERENCES `tblsecuseracct` (`useracctid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblsecgroupusers` */

/*Table structure for table `tblsecmodules` */

DROP TABLE IF EXISTS `tblsecmodules`;

CREATE TABLE `tblsecmodules` (
  `moduleid` int(11) NOT NULL AUTO_INCREMENT,
  `modulename` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblsecmodules` */

/*Table structure for table `tblsecprivileges` */

DROP TABLE IF EXISTS `tblsecprivileges`;

CREATE TABLE `tblsecprivileges` (
  `privid` int(11) NOT NULL AUTO_INCREMENT,
  `privilegename` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`privid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `tblsecprivileges` */

insert  into `tblsecprivileges`(`privid`,`privilegename`) values (1,'Add'),(2,'Edit'),(3,'Delete'),(4,'Search'),(5,'Download'),(6,'Print');

/*Table structure for table `tblsecuseracct` */

DROP TABLE IF EXISTS `tblsecuseracct`;

CREATE TABLE `tblsecuseracct` (
  `useracctid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1' COMMENT '1-Active, Inactive',
  PRIMARY KEY (`useracctid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblsecuseracct` */

/*Table structure for table `tblstudentcart` */

DROP TABLE IF EXISTS `tblstudentcart`;

CREATE TABLE `tblstudentcart` (
  `studcourseid` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` int(11) DEFAULT NULL,
  `inscourseid` int(11) DEFAULT NULL,
  `dateadded` date DEFAULT NULL,
  `amountprice` decimal(20,2) DEFAULT '0.00',
  `discount` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`studcourseid`),
  KEY `studentid` (`studentid`),
  KEY `inscourseid` (`inscourseid`),
  CONSTRAINT `tblstudentcart_ibfk_1` FOREIGN KEY (`studentid`) REFERENCES `tblstudentinfo` (`studentid`),
  CONSTRAINT `tblstudentcart_ibfk_2` FOREIGN KEY (`inscourseid`) REFERENCES `tblinstructorcourse` (`inscourseid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblstudentcart` */

/*Table structure for table `tblstudentcourse` */

DROP TABLE IF EXISTS `tblstudentcourse`;

CREATE TABLE `tblstudentcourse` (
  `studcourseid` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` int(11) DEFAULT NULL,
  `inscourseid` int(11) DEFAULT NULL,
  `datepurchased` date DEFAULT NULL,
  `paytypeid` int(11) DEFAULT NULL,
  `paidamount` decimal(20,2) DEFAULT '0.00',
  `discount` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`studcourseid`),
  KEY `studentid` (`studentid`),
  KEY `inscourseid` (`inscourseid`),
  KEY `paytypeid` (`paytypeid`),
  CONSTRAINT `tblstudentcourse_ibfk_1` FOREIGN KEY (`studentid`) REFERENCES `tblstudentinfo` (`studentid`),
  CONSTRAINT `tblstudentcourse_ibfk_2` FOREIGN KEY (`inscourseid`) REFERENCES `tblinstructorcourse` (`inscourseid`),
  CONSTRAINT `tblstudentcourse_ibfk_3` FOREIGN KEY (`paytypeid`) REFERENCES `tblutilpaymenttype` (`paytypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblstudentcourse` */

/*Table structure for table `tblstudentinfo` */

DROP TABLE IF EXISTS `tblstudentinfo`;

CREATE TABLE `tblstudentinfo` (
  `studentid` int(11) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(75) DEFAULT NULL,
  `firstname` varchar(75) DEFAULT NULL,
  `middlename` varchar(75) DEFAULT NULL,
  `bday` date DEFAULT NULL,
  `contactno` varchar(75) DEFAULT NULL,
  `address` varchar(125) DEFAULT NULL,
  `emailadd` varchar(75) DEFAULT NULL,
  `countryid` int(11) DEFAULT NULL,
  `useracctid` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentid`),
  KEY `countryid` (`countryid`),
  KEY `useracctid` (`useracctid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblstudentinfo` */

/*Table structure for table `tblutilcertification` */

DROP TABLE IF EXISTS `tblutilcertification`;

CREATE TABLE `tblutilcertification` (
  `certid` int(11) NOT NULL AUTO_INCREMENT,
  `certname` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`certid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutilcertification` */

/*Table structure for table `tblutilcontenttype` */

DROP TABLE IF EXISTS `tblutilcontenttype`;

CREATE TABLE `tblutilcontenttype` (
  `contenttypeid` int(11) NOT NULL AUTO_INCREMENT,
  `contenttypename` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`contenttypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tblutilcontenttype` */

insert  into `tblutilcontenttype`(`contenttypeid`,`contenttypename`) values (1,'Video'),(2,'Picture'),(3,'Link');

/*Table structure for table `tblutilcountry` */

DROP TABLE IF EXISTS `tblutilcountry`;

CREATE TABLE `tblutilcountry` (
  `countryid` int(11) NOT NULL AUTO_INCREMENT,
  `countrycode` varchar(10) DEFAULT NULL,
  `countryname` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`countryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutilcountry` */

/*Table structure for table `tblutilcourse` */

DROP TABLE IF EXISTS `tblutilcourse`;

CREATE TABLE `tblutilcourse` (
  `courseid` int(11) NOT NULL AUTO_INCREMENT,
  `coursecode` varchar(50) DEFAULT NULL,
  `coursename` varchar(100) DEFAULT NULL,
  `coursecatid` int(11) DEFAULT NULL,
  PRIMARY KEY (`courseid`),
  KEY `coursecatid` (`coursecatid`),
  CONSTRAINT `tblutilcourse_ibfk_1` FOREIGN KEY (`coursecatid`) REFERENCES `tblutilcoursecategory` (`coursecatid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutilcourse` */

/*Table structure for table `tblutilcoursecategory` */

DROP TABLE IF EXISTS `tblutilcoursecategory`;

CREATE TABLE `tblutilcoursecategory` (
  `coursecatid` int(11) NOT NULL AUTO_INCREMENT,
  `categoryname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`coursecatid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutilcoursecategory` */

/*Table structure for table `tblutildegree` */

DROP TABLE IF EXISTS `tblutildegree`;

CREATE TABLE `tblutildegree` (
  `degreeid` int(11) NOT NULL AUTO_INCREMENT,
  `degreename` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`degreeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutildegree` */

/*Table structure for table `tblutilpaymenttype` */

DROP TABLE IF EXISTS `tblutilpaymenttype`;

CREATE TABLE `tblutilpaymenttype` (
  `paytypeid` int(11) NOT NULL AUTO_INCREMENT,
  `paytypecode` varchar(50) DEFAULT NULL,
  `paytypename` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`paytypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tblutilpaymenttype` */

insert  into `tblutilpaymenttype`(`paytypeid`,`paytypecode`,`paytypename`) values (1,'Visa','Visa Card'),(2,'Mastercard','Mastercard'),(3,'Paypay','Paypal');

/*Table structure for table `tblutilschool` */

DROP TABLE IF EXISTS `tblutilschool`;

CREATE TABLE `tblutilschool` (
  `schoolid` int(11) NOT NULL AUTO_INCREMENT,
  `schoolcode` varchar(50) DEFAULT NULL,
  `schoolname` varchar(100) DEFAULT NULL,
  `schooladdress` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`schoolid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutilschool` */

/*Table structure for table `tblutiltesttype` */

DROP TABLE IF EXISTS `tblutiltesttype`;

CREATE TABLE `tblutiltesttype` (
  `testtypeid` int(11) NOT NULL AUTO_INCREMENT,
  `testtypename` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`testtypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tblutiltesttype` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
