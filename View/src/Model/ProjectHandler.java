package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import application.*;
import db.*;

public class ProjectHandler implements Serializable {
	private Map<String, Company> companies = new HashMap<String, Company>();
	private Map<String, ProjectOwner> projOwner = new HashMap<String, ProjectOwner>();
	private Map<String, Project> proj = new HashMap<String, Project>();
	private HashMap<String, Student> stud = new HashMap<String, Student>();
	private Map<String, StudentInfo> studInfo = new HashMap<String, StudentInfo>();
	private HashMap<String, String> studPref = new HashMap<String, String>();
	private HashMap<String, Integer> projRatings = new HashMap<String, Integer>();
	private HashMap<String, Integer> shortlistedProjects;
	private Map<String, Team> teams = new HashMap<String, Team>();
	private Map<String, Double> skill_Short = new HashMap<String, Double>();
	private Map<String, Double> av_comp_level = new HashMap<String, Double>();
	private SampleController control = null;
	private Team teams1;
	private Team teams2;
	private Team team_edit;
	private Team team_delete;
	private static DecimalFormat df = new DecimalFormat("0.00");

	public ProjectHandler() throws ClassNotFoundException, IOException {
		// TODO Auto-generated constructor stub
		readCompanyFile();
		readOwnersFile();
		readStudentsFile();
		readProjectFile();
		readStudInfo();
		readStudPref();
		readteams();
	}
	
	public ProjectHandler(String test) throws ClassNotFoundException, IOException {
		//A separate parameterized constructor for Junit as it is not reading teams
		System.out.println("For "+test+" Testing purposes");
		readCompanyFile();
		readOwnersFile();
		readStudentsFile();
		readProjectFile();
		readStudInfo();
		readStudPref();
	}

	//For contolling the GUI
	public void setController(SampleController control) {
		this.control = control;
	}

	// Adds companies info to the file and hashmap
	public void addCompany(String id, Company c) throws IOException {
		companies.put(id, c);
		//This set of code serializes the object
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("companies.dat"));
		out.writeObject(companies);
		out.close();
		//This writes to the file
		FileWriter f = new FileWriter(new File("companies.txt"));
		try {
			for (Map.Entry mapElement : companies.entrySet()) {
				Company value = (Company) mapElement.getValue();
				f.append(value.getCid() + " " + value.getCname() + " " + value.getAbn() + " " + value.getCurl() + " "
						+ value.getCadd() + "\n");
			}
			System.out.println("Successfully wrote to the file companies.txt");
		} catch (Exception e) {
			System.out.println("Error occured");
			e.printStackTrace();
		} finally {
			f.close();
		}
	}

	// Adds the project owner information to hashmap and file
	public void addProjectOwner(String id, ProjectOwner p) throws IOException {
		projOwner.put(id, p);
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("owners.dat"));
		out.writeObject(projOwner);
		out.close();
		
		FileWriter f1 = new FileWriter(new File("owners.txt"));
		try {
			for (Map.Entry mapElement : projOwner.entrySet()) {
				ProjectOwner value = (ProjectOwner) mapElement.getValue();
				f1.append(value.getFname() + " " + value.getSname() + " " + value.getPid() + " " + value.getRole() + " "
						+ value.getEmail() + " " + value.getEid() + "\n");
			}
			System.out.println("Successfully wrote to the file owners.txt");
		} catch (Exception e) {
			System.out.println("Error occured");
			e.printStackTrace();
		} finally {
			f1.close();
		}
	}

	// Add projects info to hashmap and file
	public void addProject(String id, Project pro) throws IOException {
		proj.put(id, pro);
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("projects.dat"));
		out.writeObject(proj);
		out.close();
		
		FileWriter f1 = new FileWriter(new File("projects.txt"));
		try {
			for (Map.Entry mapElement : proj.entrySet()) {
				Project value = (Project) mapElement.getValue();
				f1.append(value.getTitle() + "\n" + value.getPid() + "\n" + value.getDesc() + "\n" + value.getOid()
						+ "\n" + value.getSkill() + "\n");
			}
			System.out.println("Successfully wrote to the file projects.txt");
		} catch (Exception e) {
			System.out.println("Error occured");
			e.printStackTrace();
		} finally {
			f1.close();
		}
	}

	// Adds students info if student is present
	public boolean addStudentInfo(String sid, String personalityType, String studs) {
		if (stud.containsKey(sid)) {
			String score = stud.get(sid).getScore();
			studInfo.put(sid, new StudentInfo(sid, score, personalityType, studs));
			System.out.println(studInfo.get(sid).getStudents());
			return true;
		} else {
			return false;
		}
	}

	// Adding student info to the file
	public boolean addStudInfoFile() throws IOException {
		try {
			// Checking for the balanced personalityTypes
			if (checkpersonalityType()) {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("studentInfo.dat"));
				out.writeObject(studInfo);
				out.close();
				FileWriter f1 = new FileWriter(new File("studentInfo.txt"));
				for (Map.Entry mapEle : studInfo.entrySet()) {
					StudentInfo value = (StudentInfo) mapEle.getValue();
					f1.append(value.getSid() + " " + value.getScore() + value.getpersonalityType() + " "
							+ value.getStudents() + "\n");
				}
				f1.close();
				return true;
			} else
				return false;
		} catch (Exception e) {
			throw e;
		}
	}

	public void addStudentPref(String sid, String pref) throws FileNotFoundException, IOException {
		studPref.put(sid, pref);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("preferences.dat"));
		out.writeObject(studPref);
		out.close();
	}

	// Writing student preferences to the file
	public void writeStudentPref() throws IOException {
		try {
			FileWriter f1 = new FileWriter(new File("preferences.txt"));
			for (Map.Entry mapEle : studPref.entrySet()) {
				String value = (String) mapEle.getValue();
				String key = (String) mapEle.getKey();
				f1.append(key + "\n" + value + "\n");
			}
			f1.close();
		} catch (Exception e) {
			throw e;
		}
	}

	// Reading info from file
	public void readPref() throws Exception {
		Scanner sc = new Scanner(new File("preferences.txt"));
		sc.useDelimiter("\n");
		while (sc.hasNext()) {
			sc.next();
			String line = sc.next();
			String[] words = line.split("\\s");
			for (int i = 0; i < 7; i += 2) {
				String pro = words[i];
				projRatings.putIfAbsent(pro, 0);
				projRatings.put(pro, projRatings.get(pro) + Integer.parseInt(words[i + 1]));
			}
		}
		try {
			shortlistProjects();
		} catch (Exception e) {
			throw e;
		}
	}

	public Map<String, Project> getProj() {
		return proj;
	}

	public Map<String, StudentInfo> getStudInfo() {
		return studInfo;
	}

	public ArrayList<String> getPref(String sid) {
		String words[] = studPref.get(sid).split(" ");
		ArrayList<String> pref = new ArrayList<String>();
		for (int i = 0; i < 3; i += 2) {
			pref.add(words[i]);
		}
		return pref;
	}

	//Return percentage of students getting preference in a team
	public HashMap<String, Double> percentage() {

		HashMap<String, Double> percen = new HashMap<String, Double>();
		for (Map.Entry mapElement : teams.entrySet()) {
			double success = 0;
			double fail = 0;
			double ratio = 0;
			String key = (String) mapElement.getKey();
			String[] value = ((Team) mapElement.getValue()).getStudentIDs().split(" ");
			for (String students : value) {
				ArrayList<String> pref = getPref(students);
				if (pref.contains(key))
					success += 1;
				else
					fail += 1;
			}
			ratio = (success) / 4;
			percen.put(key, ratio * 100);
		}
		return percen;
	}

	//Standard Deviation in %getting preferences
	public String percentageDeviation() {
		double sum = 0, stdDeviation = 0;
		Map<String, Double> percen = percentage();
		for (Map.Entry mapElement : percen.entrySet()) {
			sum = sum + (Double) mapElement.getValue() / 100;
		}
		double mean = sum / percen.size();
		for (Map.Entry mapElement : percen.entrySet()) {
			stdDeviation += Math.pow((Double) mapElement.getValue() / 100 - mean, 2);
		}
		double dev = Math.sqrt(stdDeviation / percen.size());
		return df.format(dev);
	}

	//Standard deviation for skill gaps in a team
	public String shortfallDeviation() {
		double sum = 0, stdDev = 0;
		for (Double num : skill_Short.values()) {
			sum += num;
		}
		double mean = sum / skill_Short.size();
		for (Double num : skill_Short.values()) {
			stdDev += Math.pow(num - mean, 2);
		}
		return df.format(Math.sqrt(stdDev / skill_Short.size()));
	}

	public String competencyDeviation() {
		double sum = 0, stdDev = 0;
		for (Double num : getAv_comp_level().values()) {
			sum += num;
		}
		double mean = sum / getAv_comp_level().size();
		for (Double num : getAv_comp_level().values()) {
			stdDev += Math.pow(num - mean, 2);
		}
		return df.format(Math.sqrt(stdDev / getAv_comp_level().size()));
	}

	// Method which sorts the values
	public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		Collections.reverse(list);
		// put data from sorted list to hashmap
		int count = 0;
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			if (count < 5) {
				temp.put(aa.getKey(), aa.getValue());
				count++;
			} else {
				break;
			}
		}
		return temp;
	}

	// Method which shortlists projects and add to the file
	public void shortlistProjects() throws IOException {
		try {
			shortlistedProjects = sortByValue(projRatings);

			FileWriter file = new FileWriter(new File("projects.txt"));
			for (Map.Entry mapElement : shortlistedProjects.entrySet()) {
				Project value = (Project) proj.get(mapElement.getKey());
				file.append(value.getTitle() + "\n" + value.getPid() + "\n" + value.getDesc() + "\n" + value.getOid()
						+ "\n" + value.getSkill() + "\n");
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("projects.dat"));
			out.writeObject(proj);
			out.close();
			file.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	//Specifically for Junit as this doesn't serialize the object
	public void jUnitformTeam(Team team) throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		checkTeamProjID(team.getProjectID());
		studentTeams(team.getStudentIDs());
		checkDuplicates(team);
		checkConflict(team.getStudentIDs());
		checkLeader(team);
		checkImbalance(team);
		teams.put(team.getProjectID(), team);
	}

	// Forming teams and checking for several exceptions
	public void formTeam(Team team) throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		checkTeamProjID(team.getProjectID());
		studentTeams(team.getStudentIDs());
		checkDuplicates(team);
		checkConflict(team.getStudentIDs());
		checkLeader(team);
		checkImbalance(team);
		teams.put(team.getProjectID(), team);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("teams.dat"));
		out.writeObject(teams);
		out.close();
	}

	//This method is to show the team metrics
	public void teamMetrics() {

		for (Map.Entry mapElement : teams.entrySet()) {
			double p = 0;
			double a = 0;
			double n = 0;
			double w = 0;
			double skill_shortfall = 0;
			String[] stIDs = ((Team) mapElement.getValue()).getStudentIDs().split(" ");
			for (String st : stIDs) {
				for (Map.Entry mapEle : studInfo.entrySet()) {
					if (st.equalsIgnoreCase((String) mapEle.getKey())) {
						StudentInfo value = (StudentInfo) mapEle.getValue();
						String[] scores = value.getScore().split(" ");
						for (int i = 0; i < scores.length; i++) {
							// System.out.println(scores[i]);
							if (scores[i].equals("P")) {
								p = p + Integer.parseInt(scores[i + 1]);
							} else if (scores[i].equals("A")) {
								a = a + Integer.parseInt(scores[i + 1]);
							} else if (scores[i].equals("N")) {
								n = n + Integer.parseInt(scores[i + 1]);
							} else if (scores[i].equals("W")) {
								w = w + Integer.parseInt(scores[i + 1]);
							}
						}
					}
				}
			}
			System.out.println("Average skill competency of " + mapElement.getKey());
			System.out.println(
					p / stIDs.length + " " + a / stIDs.length + " " + n / stIDs.length + " " + w / stIDs.length);
			System.out.println("Average Team skills" + ((p + a + n + w) / 16));
			av_comp_level.put((String) mapElement.getKey(), ((p + a + n + w) / 16));
			if (proj.get(mapElement.getKey()).getP() > p / stIDs.length) {
				skill_shortfall = skill_shortfall + proj.get(mapElement.getKey()).getP() - (p / stIDs.length);
			}
			if (proj.get(mapElement.getKey()).getN() > n / stIDs.length) {
				skill_shortfall = skill_shortfall + proj.get(mapElement.getKey()).getN() - (n / stIDs.length);
			}
			if (proj.get(mapElement.getKey()).getA() > a / stIDs.length) {
				skill_shortfall = skill_shortfall + proj.get(mapElement.getKey()).getA() - (a / stIDs.length);
			}
			if (proj.get(mapElement.getKey()).getW() > w / stIDs.length) {
				skill_shortfall = skill_shortfall + proj.get(mapElement.getKey()).getW() - (w / stIDs.length);
			}
			skill_Short.put((String) mapElement.getKey(), skill_shortfall);
		}
		getSkillShortfall();
		System.out.println(
				"Percentage of students getting their first and second preferences " + percentage().values() + "%");
		System.out.println("\nStandard deviation preferences = " + percentageDeviation());
		System.out.println("\nStandard deviation Skill shortfall = " + shortfallDeviation());
	}

	//Swap functionality where first the teams in which there is updation are removed then the new updated teams are added
	public void swapTeam(Map<String, String> selection)
			throws IOException, NotValidIDException, InvalidMemberException, StudentConflictException,
			RepeatedMemberException, NoLeaderException, PersonalityImbalanceException, ClassNotFoundException {
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		for (Map.Entry mapElement : selection.entrySet()) {
			String key = (String) mapElement.getKey();
			String value = (String) mapElement.getValue();
			values.add(value);
			keys.add(key);
		}
		String[] students1 = teams.get(keys.get(0)).getStudentIDs().split(" ");
		String[] students2 = teams.get(keys.get(1)).getStudentIDs().split(" ");
		String team1 = "";
		String team2 = "";
		for (int i = 0; i < students1.length; i++) {
			if (students1[i].equals(values.get(0))) {
				students1[i] = values.get(1);
			}
			team1 += students1[i] + " ";
		}
		for (int i = 0; i < students2.length; i++) {
			if (students2[i].equals(values.get(1))) {
				students2[i] = values.get(0);
			}
			team2 += students2[i] + " ";
		}
		System.out.println("team1= " + team1 + "team2= " + team2);
		teams1 = teams.remove(keys.get(0));
		teams2 = teams.remove(keys.get(1));
		formTeam(new Team(keys.get(0), team1));
		formTeam(new Team(keys.get(1), team2));
		//Updating the database tables
		UpdateTable m = new UpdateTable();
		m.updateTeam(teams.get(keys.get(0)));
		m.updateTeam(teams.get(keys.get(1)));
		//Updating the GUI
		if (control != null)
			control.update();
	}

	//Removing the specific student id from a team
	public void removeID(Map<String, String> selection)
			throws IOException, NotValidIDException, InvalidMemberException, StudentConflictException,
			RepeatedMemberException, NoLeaderException, PersonalityImbalanceException, ClassNotFoundException {
		String key = null;
		String value = null;
		for (Map.Entry mapElement : selection.entrySet()) {
			key = (String) mapElement.getKey();
			value = (String) mapElement.getValue();
		}

		//Modifying the student string i.e. removing that specific student id from the team
		String[] orig = teams.get(key).getStudentIDs().split(" ");
		String students = "";
		for (String st : orig) {
			if (!st.equals(value)) {
				students += st + " ";
			}
		}
		team_edit = teams.remove(key);
		formTeam(new Team(key, students));
		//Updating the db
		UpdateTable m = new UpdateTable();
		m.updateTeam(teams.get(key));
		//Updating GUI
		if (control != null)
			control.update();
	}

	public void setTeams(Map<String, Team> teams) throws ClassNotFoundException, IOException {
		this.teams = teams;
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("teams.dat"));
		out.writeObject(teams);
		out.close();
		if (control != null)
			control.update();
	}

	//Adding the ID to the team
	public void addID(String empty, String stud)
			throws NotValidIDException, InvalidMemberException, StudentConflictException, RepeatedMemberException,
			NoLeaderException, PersonalityImbalanceException, IOException {
		String studs[] = teams.get(empty).getStudentIDs().split(" ");
		String stu = "";
		for (String st : studs) {
			stu += st + " ";
		}
		stu += stud;
		team_delete = teams.remove(empty);
		formTeam(new Team(empty, stu));
		UpdateTable m = new UpdateTable();
		m.updateTeam(teams.get(empty));
		if (control != null)
			control.update();
	}
	
	//Several getters from now on
	public Team getTeam_delete() {
		return team_delete;
	}

	public Team getTeam_edit() {
		return team_edit;
	}

	public Team getTeams1() {
		return teams1;
	}

	public Team getTeams2() {
		return teams2;
	}

	public void getSkillShortfall() {
		for (Map.Entry mapElement : skill_Short.entrySet()) {
			String key = (String) mapElement.getKey();
			Double value = (Double) mapElement.getValue();
			System.out.println("Skill shortfall for " + key + "is " + value);
		}
	}

	public Map<String, Double> getSkill_Short() {
		return skill_Short;
	}

	public void getUpdatedProjects() {
		ArrayList<String> projects = new ArrayList<String>();
		projects.addAll(proj.keySet());
		projects.removeAll(teams.keySet());
		System.out.println(projects.toString());
	}
	
	public HashMap<String, Student> getStud() {
		return stud;
	}

	public void getUpdatedStudents() {
		ArrayList<String> students = new ArrayList<String>();
		ArrayList<String> allstud = new ArrayList<String>();
		for (Map.Entry mapElement : teams.entrySet()) {
			String[] value = ((Team) mapElement.getValue()).getStudentIDs().split(" ");
			for (String val : value) {
				students.add(val);
			}
		}
		for (String s : studInfo.keySet()) {
			allstud.add(s);
		}
		allstud.removeAll(students);
		System.out.println(allstud.toString());
	}
	
	public HashMap<String, Integer> getShortlistedProjects() {
		return shortlistedProjects;
	}

	public Map<String, Double> getAv_comp_level() {
		return av_comp_level;
	}

	// Checking owner id by reading from the owners file and hashmap
	public boolean checkOwner(String id) {

		if (projOwner.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}

	// Checking if the company exists or not
	public boolean checkComp(String id) {
		if (companies.containsKey(id))
			return true;
		else
			return false;
	}

	//Checking the uniqueness of ABN
	public boolean checkABN(long abn) {
		boolean flag = false;
		for (Map.Entry mapEle : companies.entrySet()) {
			if (((Company) mapEle.getValue()).getAbn() == abn)
				flag = true;
		}
		return flag;
	}

	// Return number of students
	public int checkStudents() {
		return stud.size();
	}

	//Checking uniqueness of ID
	public boolean checkID(String id) {
		if (stud.containsKey(id))
			return true;
		else
			return false;
	}

	// Checking projects id present or not
	public boolean checkProjID(String id) throws FileNotFoundException {
		if (proj.containsKey(id))
			return true;
		else
			return false;
	}

	//Checking leader present in a team or not
	public void checkLeader(Team team) throws NoLeaderException {
		String students[] = team.getStudentIDs().split(" ");
		boolean flag = false;
		for (String student : students) {
			if (studInfo.get(student).getpersonalityType().equals("A"))
				flag = true;
		}
		if (flag == false)
			throw new NoLeaderException();
	}

	public void checkDuplicates(Team team) throws RepeatedMemberException {
		String students[] = team.getStudentIDs().split(" ");
		for (int i = 1; i < students.length; i++) {
			int j = 0;
			for (j = 0; j < i; j++)
				if (students[i].equals(students[j]))
					throw new RepeatedMemberException();
		}
	}

	public void studentTeams(String sID) throws NotValidIDException, InvalidMemberException, StudentConflictException {
		String words[] = sID.split(" ");
		if (words.length == 4 || words.length == 3) {
			for (String sid : words) {
				if (!checkID(sid)) {
					throw new NotValidIDException();
				} else {
					for (Map.Entry mapEle : teams.entrySet()) {
						String students[] = ((Team) mapEle.getValue()).getStudentIDs().split(" ");
						for (String stud : students) {
							if (stud.equals(sid))
								throw new InvalidMemberException();
						}

					}
				}
			}
		} else
			throw new NotValidIDException();
	}

	public void checkConflict(String sid) throws StudentConflictException {
		String words[] = sid.split(" ");
		ArrayList<String> students = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();
		for (String id : words) {
			ids.add(id);
			if (studInfo.get(id).getStudents() != null) {
				String temp[] = studInfo.get(id).getStudents().split(" ");
				for (String t : temp) {
					students.add(t);
				}
			}
		}
		for (String m : ids) {
			if (students.contains(m))
				throw new StudentConflictException();
		}

	}

	public void checkTeamProjID(String id) throws NotValidIDException {
		if (!proj.containsKey(id))
			throw new NotValidIDException();
	}

	public int getTeamSize() {
		return teams.size();
	}

	public boolean checkpersonalityType() {
		int factor = stud.size() / 4;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		for (Map.Entry mapElement : studInfo.entrySet()) {
			StudentInfo value = (StudentInfo) mapElement.getValue();
			if (value.getpersonalityType().equals("A")) {
				a++;
			} else if (value.getpersonalityType().equals("B")) {
				b++;
			} else if (value.getpersonalityType().equals("C")) {
				c++;
			} else if (value.getpersonalityType().equals("D")) {
				d++;
			}
		}
		if (a > factor || b > factor || c > factor || d > factor) {
			return false;
		} else {
			return true;
		}
	}

	public void checkImbalance(Team team) throws PersonalityImbalanceException {
		String[] students = team.getStudentIDs().split(" ");
		HashSet<String> personality = new HashSet<String>();
		for (String student : students) {
			personality.add(studInfo.get(student).getpersonalityType());
		}
		if (personality.size() < 3)
			throw new PersonalityImbalanceException();
	}

	// All Reads
	public void readCompanyFile() throws IOException, ClassNotFoundException {
		//Reading from a file using streams
//		if (new File("companies.txt").exists()) {
//
//			Stream<String> rows = Files.lines(Paths.get("companies.txt"));
//			companies = rows.map(x -> x.split(" ")).collect(
//					Collectors.toMap(x -> x[0], x -> new Company(x[0], x[1], Long.parseLong(x[2]), x[3], x[4])));
//			rows.close();
//		}
//		
//		ObjectOutputStream out = new ObjectOutputStream
//        (new FileOutputStream("companies.dat"));
//		out.writeObject(companies);
//		out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("companies.dat"));
		companies = (HashMap<String, Company>) in.readObject();
		in.close();
		System.out.println(companies.keySet());
	}

	public void readOwnersFile() throws IOException, ClassNotFoundException {
		//Reading from files using streams
//		if (new File("owners.txt").exists()) {
//
//			Stream<String> rows = Files.lines(Paths.get("owners.txt"));
//			projOwner = rows.map(x -> x.split(" "))
//					.collect(Collectors.toMap(x -> x[2], x -> new ProjectOwner(x[0], x[1], x[2], x[3], x[4], x[5])));
//			rows.close();
//		}
//		ObjectOutputStream out = new ObjectOutputStream
//        (new FileOutputStream("owners.dat"));
//		out.writeObject(projOwner);
//		out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("owners.dat"));
		projOwner = (Map<String, ProjectOwner>) in.readObject();
		in.close();
		System.out.println(projOwner.keySet());
	}

	// Adds students info from file to the hashmap
	public void readStudentsFile() throws IOException, ClassNotFoundException {
//			try {
//				Scanner sc = new Scanner(new File("students.txt"));
//				while (sc.hasNextLine()) {
//					String sid = sc.next();
//					String score = "";
//					int i = 0;
//					while (i < 8) {
//						i++;
//						score = score.concat(sc.next() + " ");
//					}
//					stud.put(sid, new Student(sid, score));
//				}
//			} catch (FileNotFoundException e) {
//				throw e;
//			}
//			ObjectOutputStream out = new ObjectOutputStream
//	                (new FileOutputStream("students.dat"));
//			out.writeObject(stud);
//		    out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"));
		stud = (HashMap<String, Student>) in.readObject();
		in.close();
		System.out.println(stud.keySet());
	}

	public void readStudPref() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("preferences.dat"));
		studPref = (HashMap<String, String>) in.readObject();
		in.close();
		System.out.println(studPref.keySet());
	}

	public void readProjectFile() throws IOException, ClassNotFoundException {
//			if (new File("projects.txt").exists()) {
//				Scanner sc = new Scanner(new File("projects.txt"));
//				sc.useDelimiter("\n");
//				while (sc.hasNext()) {
//					String title = sc.next();
//					String projID = sc.next();
//					String desc = sc.next();
//					String ownID = sc.next();
//					String line = sc.next();
//					String[] words = line.split("\\s");
//					int p = Integer.parseInt(words[1]);
//					int n = Integer.parseInt(words[3]);
//					int a = Integer.parseInt(words[5]);
//					int w = Integer.parseInt(words[7]);
//					proj.put(projID, new Project(title, desc, projID, ownID, p, n, a, w));
//				}
//			}
//		ObjectOutputStream out = new ObjectOutputStream
//        (new FileOutputStream("projects.dat"));
//		out.writeObject(proj);
//		out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("projects.dat"));
		proj = (Map<String, Project>) in.readObject();
		in.close();
		System.out.println(proj.keySet());
	}

	public void readStudInfo() throws IOException, ClassNotFoundException {
//			if (new File("studentInfo.txt").exists()) {
//			Scanner sc = new Scanner(new File("studentInfo.txt"));
//
//			while (sc.hasNext()) {
//
//				sc.useDelimiter("\n");
//				String line = sc.next();
//				String[] words = line.split("\\s");
//
//				if (words.length == 10) {
//					String sid = words[0];
//					String score = words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5] + " "
//							+ words[6] + " " + words[7] + " " + words[8];
//					String personality = words[9];
//					String students = null;
//					studInfo.put(sid, new StudentInfo(sid, score, personality, students));
//				}
//
//				if (words.length == 11) {
//					String sid = words[0];
//					String score = words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5] + " "
//							+ words[6] + " " + words[7] + " " + words[8];
//					String personality = words[9];
//					String students = words[10];
//					studInfo.put(sid, new StudentInfo(sid, score, personality, students));
//				}
//
//				if (words.length == 12) {
//					String sid = words[0];
//					String score = words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5] + " "
//							+ words[6] + " " + words[7] + " " + words[8];
//					String personality = words[9];
//					String students = words[10] + " " + words[11];
//					studInfo.put(sid, new StudentInfo(sid, score, personality, students));
//				}
//			}
//			}
//			ObjectOutputStream out = new ObjectOutputStream
//            (new FileOutputStream("studentInfo.dat"));
//			out.writeObject(studInfo);
//			out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("studentInfo.dat"));
		studInfo = (Map<String, StudentInfo>) in.readObject();
		in.close();
		System.out.println(studInfo.keySet());
	}

	public void readteams() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("teams.dat"));
		teams = (Map<String, Team>) in.readObject();
		in.close();
		System.out.println(teams.keySet());
	}

	public Map<String, Team> getTeams() {
		return teams;
	}
}