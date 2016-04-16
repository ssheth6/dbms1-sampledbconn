package com.dbms1.assign1;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class sdap
{
	public static void main (String[] args)
	{
		String usr = args[0];
		String pwd = args[1];
		String url = "jdbc:postgresql://localhost:5432/postgres";
		//load driver
		try
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Successfully loaded the driver!");
		}
		catch (Exception e)
		{
			System.out.println("Failed to load the driver!");
			e.printStackTrace();
		}
		//connect server
		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Successfully connected to the server!");
			//get query result in ResultSet rs
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("select * from sales2");
			//ResultSet rs1 = rs;
			ArrayList<String[]> max = new ArrayList<String[]>();
			ArrayList<String[]> min = new ArrayList<String[]>();
			String[] temp = new String[5];
			int i=1,rowcount=0,j=0,k=0;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			while(i<=8)
			{
				
				rs.absolute(rowcount);
				while(rs.next())
				{
					Date date;
					rowcount=rs.getRow();
					//System.out.println("From rs:" + " " +rs.getString("cust") + " " +rs.getString("prod") + " " +rs.getString("quant"));
					//rows.add(new String [] {rs.getString("cust"), rs.getString("prod"), rs.getString("month") + "/" + rs.getString("day") + "/" + rs.getString("year"), rs.getString("quant"), rs.getString("state")});
					temp[0] = rs.getString("cust");
					temp[1] = rs.getString("prod");
					try {
						date = (Date) sdf.parse(rs.getString("month") + "/" + rs.getString("day") + "/" + rs.getString("year"));
						temp[2] = sdf.format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					temp[3] = rs.getString("quant");
					temp[4] = rs.getString("state");
					rs.beforeFirst();
					while(rs.next())
					{
						//System.out.println("From rs1:" + " " +rs.getString("cust") + " " +rs.getString("prod") + " " +rs.getString("quant"));
						//l=0;
						
							if(max.size() == 0 && min.size() == 0)
							{
								max.add(new String [] {temp[0],temp[1],temp[2],temp[3],temp[4]});
								min.add(new String [] {temp[0],temp[1],temp[2],temp[3],temp[4]});
							}
							else
							{
								for(j=0; j < max.size(); j++)
								{
									if(temp[0].equals(max.get(j)[0]) && temp[1].equals(max.get(j)[1]) && (Integer.parseInt(temp[3]) > Integer.parseInt(max.get(j)[3])))
									{
										System.out.println("Greater");
									}
									else if(temp[0].equals(max.get(j)[0]) && temp[1].equals(max.get(j)[1]) && (Integer.parseInt(temp[3]) < Integer.parseInt(max.get(j)[3])))
									{
										System.out.println("Lesser");
									}
									else
									{
										max.add(new String [] {temp[0],temp[1],temp[2],temp[3],temp[4]});
									}
								}
							}
							
							/*if(Integer.parseInt(temp[3]) > Integer.parseInt(rs.getString("quant")))
							{
								//System.out.println("Greater");
								max.add(new String [] {temp[0],temp[1],temp[2],temp[3],temp[4]});
							}
							else if(Integer.parseInt(temp[3]) < Integer.parseInt(rs.getString("quant")))
							{
								//System.out.println("Lesser");
								min.add(new String [] {temp[0],temp[1],temp[2],temp[3],temp[4]});
							}
							else if(Integer.parseInt(temp[3]) == Integer.parseInt(rs.getString("quant")))
							{
								//System.out.println("Equal");
							}*/
						
					}
				
				}
				System.out.println("\t \t");
				i++;
			}	
			for(j=0; j < max.size(); j++)
			{
				for(k=0; k < 5; k++)
				{
					System.out.println("From max:" +max.get(j)[k]);
				}
				System.out.println("\t");
			}
			for(j=0; j < min.size(); j++)
			{
				for(k=0; k < 5; k++)
				{
					System.out.println("From min:" +min.get(j)[k]);
				}
				System.out.println("\t");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}















