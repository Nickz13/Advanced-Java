package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Assignment {

	public static void main(String args[]) throws IOException, ClassNotFoundException {
		// For input from keyboard
		Scanner input = new Scanner(System.in);
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		// Handler object containing all the methods
		ProjectHandler proj = new ProjectHandler();
		// Menu
		while (true) {

			System.out.println("****Menu****");
			System.out.println("A. Add company");
			System.out.println("B. Add Project owner");
			System.out.println("C. Add Project");
			System.out.println("D. Capture Student Personalities");
			System.out.println("E. Add Student Preferences");
			System.out.println("F. Shortlist Projects");
			System.out.println("G. Form Teams");
			if (proj.getTeamSize() == 5)
				System.out.println("H. Display Team-Fitness Metric");
			System.out.println("I. Quit");

			String ch = input.next().toUpperCase();

			switch (ch) {

			case "A":
				try {
					System.out.println("Enter the company ID");
					String cid = input.next();
					boolean loop1 = true;
					do {
						// To check uniqueness of id
						if (cid.matches("^[c]\\w{1,29}$")) {
							if (proj.checkComp(cid)) {
								System.out.println("Please Enter a unique company id");
								cid = input.next();
							} else {
								loop1 = false;
							}
						} else {
							System.out.println("Enter company id in the format c*");
							cid = input.next();
						}
					} while (loop1);

					System.out.println("Enter the company name");
					String cname = stdin.readLine();

					System.out.println("Enter ABN number");
					long abn = input.nextLong();
					loop1 = true;
					do {
						if (Long.toString(abn).length() == 11) {
							if (proj.checkABN(abn)) {
								System.out.println("Enter a unique abn");
								abn = input.nextLong();
							} else
								loop1 = false;
						} else {
							System.out.println("Enter ABN correctly");
							abn = input.nextLong();
						}
					} while (loop1);

					System.out.println("Enter the company URl");
					String curl = input.next();

					System.out.println("Enter the company address");
					String cadd = stdin.readLine();

					// Calling method to add companies
					proj.addCompany(cid, new Company(cid, cname, abn, curl, cadd));
				} catch (InputMismatchException e) {
					System.out.println("Only numbers accepted");
					e.printStackTrace();
				}
				break;

			case "B":
				System.out.println("Enter first name");
				String fname = input.next();

				System.out.println("Enter surname");
				String sname = input.next();

				System.out.println("Enter Project Owner ID");

				boolean loop = true;

				String pid = input.next();
				// To check uniqueness of ownerid
				do {
					if (pid.matches("^[own]\\w{1,7}$")) {
						if (proj.checkOwner(pid)) {
							System.out.println("Please Enter a unique project owner id");
							pid = input.next();
						} else {
							loop = false;
						}
					} else {
						System.out.println("Enter the owner id in own* format");
						pid = input.next();
					}
				} while (loop);

				System.out.println("Enter your role");
				String role = input.next();

				System.out.println("Enter your email address");
				String email = input.next();

				System.out.println("Enter company ID to which you belong");
				String eid = input.next();

				boolean loop1 = true;
				// Checking company id exists or not
				do {
					if (!proj.checkComp(eid)) {
						System.out.println("Company doesnot exist!!");
						System.out.println("Please enter a valid id");
						eid = input.next();
					} else {
						loop1 = false;
					}
				} while (loop1);

				// calling method to add owner
				proj.addProjectOwner(pid, new ProjectOwner(fname, sname, pid, role, email, eid));
				break;

			case "C":
				System.out.println("Enter title of the Project");
				String title = stdin.readLine();

				System.out.println("Enter Project ID");
				String projID = input.next();

				boolean projID_loop = true;
				// To check uniqueness of project id
				do {
					if (projID.matches("^[Pr]\\w{1,8}$")) {
						if (proj.checkProjID(projID)) {
							System.out.println("Please enter a unique ID");
							projID = input.next();
						} else
							projID_loop = false;
					} else {
						System.out.println("Enter id in the Pr format");
						projID = input.next();
					}
				} while (projID_loop);

				System.out.println("Enter Description of the Project");
				String desc = stdin.readLine();

				System.out.println("Enter Project Owner ID");
				String ownID = input.next();

				boolean own_loop = true;
				// Checking existence of owner id
				do {
					if (!proj.checkOwner(ownID)) {
						System.out.println("Owner doesnot exist!!");
						System.out.println("Please enter a valid id");
						ownID = input.next();
					} else {
						own_loop = false;
					}
				} while (own_loop);

				System.out.println("Ranking in Programming and Software Engineering");
				int p = input.nextInt();

				System.out.println("Ranking in Network and Security");
				int n = input.nextInt();

				System.out.println("Ranking in Analytics and Big Data");
				int a = input.nextInt();

				System.out.println("Ranking in Web & Mobile applications");
				int w = input.nextInt();

				// Calling method to add projects
				proj.addProject(projID, new Project(title, desc, projID, ownID, p, n, a, w));
				break;

			case "D":
				try {
					System.out.println("List of students available");
					// Getting number of students
					int n_stud = proj.checkStudents();

					// Available students
					System.out.println(proj.stud.keySet());

					// Menu to capture student personalities
					boolean flag1 = false;
					while (flag1 == false && n_stud > 0) {
						System.out.println("1.Capture Student Personalities");
						System.out.println("2.Exit");
						// So that this menu terminates when all the students are covered
						n_stud--;
						int choice_d = input.nextInt();

						switch (choice_d) {
						case 1:
							System.out.println("Enter student id");
							String sid = input.next().toUpperCase();
							boolean stu_loop = true;
							do {
								if (proj.checkID(sid)) {
									stu_loop = false;
								} else {
									System.out.println("Student not available");
									System.out.println("Enter from the above set");
									sid = input.next().toUpperCase();
								}
							} while (stu_loop);

							System.out.println("Enter the personalityType assigned");
							String personalityType = input.next().toUpperCase();

							System.out.println("Enter any students you dont want to work with");
							String studs = stdin.readLine().toUpperCase();

							// Calling this method which adds the above info
							if (proj.addStudentInfo(sid, personalityType, studs)) {
								System.out.println("Information added");
							} else {
								System.out.println("ID not found");
							}
							break;
						case 2:
							flag1 = true;
							break;
						}
					}
					// Calling the method which adds the info to file
					if (proj.addStudInfoFile()) {
						System.out.println("StudentInfo File created!!");
					} else {
						System.out.println("personalityTypes not balanced");
					}
				} catch (Exception e) {
					System.out.println("Error occured");
					e.printStackTrace();
				}
				break;

			case "E":
				try {
					System.out.println("Add Student Preferences");

					// Menu to add student preferences
					boolean flag = false;
					while (flag == false) {

						System.out.println("1. Enter the student preferences");
						System.out.println("2. Exit this option");

						int choice = input.nextInt();

						switch (choice) {
						case 1:
							System.out.println("Enter your Student id");
							String stid = input.next().toUpperCase();

							boolean student_loop = true;
							// Uniqueness of id's
							do {
								if (proj.checkID(stid)) {
									student_loop = false;
								} else {
									System.out.println("Student not available");
									stid = input.next().toUpperCase();
								}
							} while (student_loop);
							boolean pr1_loop = true;
							boolean pr2_loop = true;
							boolean pr3_loop = true;
							boolean pr4_loop = true;
							System.out.println("Enter the Rank 4 project");
							String pr1 = input.next();
							do {
								if (proj.checkProjID(pr1))
									pr1_loop = false;
								else {
									System.out.println("Id doesnot exist");
									pr1 = input.next();
								}
							} while (pr1_loop);
							System.out.println("Enter the rank 3 project");
							String pr2 = input.next();
							do {
								if (proj.checkProjID(pr2))
									pr2_loop = false;
								else {
									System.out.println("Id doesnot exist");
									pr2 = input.next();
								}
							} while (pr2_loop);
							System.out.println("Enter the rank 2 project");
							String pr3 = input.next();
							do {
								if (proj.checkProjID(pr3))
									pr3_loop = false;
								else {
									System.out.println("Id doesnot exist");
									pr3 = input.next();
								}
							} while (pr3_loop);
							System.out.println("Enter the rank 1 project");
							String pr4 = input.next();
							do {
								if (proj.checkProjID(pr4))
									pr4_loop = false;
								else {
									System.out.println("Id doesnot exist");
									pr4 = input.next();
								}
							} while (pr4_loop);

							String pref = pr1 + " " + "4" + " " + pr2 + " " + "3" + " " + pr3 + " " + "2" + " " + pr4
									+ " " + "1";
							proj.addStudentPref(stid, pref);
							break;
						case 2:
							flag = true;
							break;
						default:
							System.out.println("Wrong choice");
							break;
						}

					}
					// Method which writes to the file
					proj.writeStudentPref();
					System.out.println("Written to the file preferences.txt");
				} catch (Exception e) {
					System.out.println("Error occured");
					e.printStackTrace();
				}
				break;

			case "F":
				try {
					// Method which reads preferences from the file
					proj.readPref();
					System.out.println(proj.shortlistedProjects.entrySet());
					System.out.println("Shortlisted Projects updated to projects.txt");
				} catch (Exception e) {
					System.out.println("Error occured");
					e.printStackTrace();
				}
				break;

			case "G":
				System.out.println("Enter the Project ID and Student ID's");
				proj.getUpdatedProjects();
				proj.getUpdatedStudents();
				try {
					String prID = stdin.readLine();
					String sID = stdin.readLine();
					proj.formTeam(new Team(prID, sID));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;

			case "H":
				if (proj.getTeamSize() == 5) {
					System.out.println("Team Fitness Metrics");
					try {
						proj.teamMetrics();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;
			case "I":
				System.exit(0);
				break;

			default:
				System.out.println("Wrong choice entered");
				break;
			}

		}
	}
}