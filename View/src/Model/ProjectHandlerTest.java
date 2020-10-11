package Model;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ProjectHandlerTest extends ProjectHandler {

	public ProjectHandlerTest() throws IOException, ClassNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Test(expected = NoLeaderException.class)
	public void testCheckLeader() throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		formTeam(new Team("Pr1", "S2 S3 S7 S6"));
	}

	@Test(expected = RepeatedMemberException.class)
	public void testCheckDuplicates() throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		formTeam(new Team("Pr1", "S1 S1 S7 S8"));
	}

	@Test(expected = InvalidMemberException.class)
	public void testStudentTeams() throws NotValidIDException, InvalidMemberException, StudentConflictException,
			IOException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		formTeam(new Team("Pr1", "S1 S8 S2 S3"));
		formTeam(new Team("Pr2", "S5 S8 S4 S6"));
	}

	@Test(expected = StudentConflictException.class)
	public void testCheckConflict() throws StudentConflictException {
		checkConflict("S1 S2 S3 S4");
		fail("conflict");
	}

	@Test(expected = NotValidIDException.class)
	public void testCheckTeamProjID() throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		formTeam(new Team("Pr0", "S1 S7 S8 S6"));
	}

	@Test(expected = PersonalityImbalanceException.class)
	public void testcheckPersonalityImbalance() throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		formTeam(new Team("Pr1", "S1 S5 S8 S4"));
	}

	@Test
	public void testpercentage() throws IOException, NotValidIDException, InvalidMemberException,
			StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
//		formTeam(new Team("Pr1", "S12 S8 S7 S5"));
//		formTeam(new Team("Pr2", "S1 S18 S19 S16"));
//		formTeam(new Team("Pr3", "S3 S11 S14 S17"));
//		formTeam(new Team("Pr4", "S13 S4 S6 S20"));
//		formTeam(new Team("Pr5", "S9 S10 S15 S2"));
		assertEquals("0.16", percentageDeviation());
	}
	
	@Test
	public void testshortfallDeviation() throws IOException, NotValidIDException, InvalidMemberException, StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
//		formTeam(new Team("Pr1", "S12 S8 S7 S5"));
//		formTeam(new Team("Pr2", "S1 S18 S19 S16"));
//		formTeam(new Team("Pr3", "S3 S11 S14 S17"));
//		formTeam(new Team("Pr4", "S13 S4 S6 S20"));
//		formTeam(new Team("Pr5", "S9 S10 S15 S2"));
		teamMetrics();
		assertEquals("0.37", shortfallDeviation());
	}

}

