package com.lnt.training.jms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		Connection connection = DriverManager.getConnection("jdbc:db2://dashdb-txn-sbox-yp-dal09-11.services.dal.bluemix.net:50000/BLUDB", "bpk66620","542s2mh6slczn^kl");
		System.out.println("Connected");
		Statement statement = connection.createStatement();
		statement.execute("create table ahmed ( empid int , empname varchar(20))");
		System.out.println("Table created");
	}
}


