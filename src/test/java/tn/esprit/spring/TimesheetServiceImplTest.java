package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.config.LoggingAspect;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {

	private static final Logger l = LogManager.getLogger(LoggingAspect.class);

	@Autowired
	ITimesheetService timesheetService;

	@Autowired
	IEntrepriseService entrepriseService;

	@Autowired
	IEmployeService employerSer;

	@Autowired
	MissionRepository missionR;

	@Autowired
	TimesheetRepository timeR;

	@Autowired
	DepartementRepository depR;

	private static int idMission;
	private static int idDepartement;
	private static int idEmployer;

	@Before
	public void add() {

		l.info("------initialisation------");

		if (idDepartement == 0) {

			idDepartement = entrepriseService.ajouterDepartement(new Departement("departement informatique"));

		}

		if (idEmployer == 0) {
			idEmployer = employerSer
					.ajouterEmploye(new Employe("zouari", "oussema", "oussema.zouari@esprit.tn", true, Role.INGENIEUR));
		}

		if (idMission == 0) {

			idMission = timesheetService
					.ajouterMission(new Mission("Mission de developpement", "Developpement module RH"));

		}

	}

	
	@Test
	public void TestAjouterMission() {

		l.info("-------->TestAjouterMission()");

		idMission = timesheetService.ajouterMission(new Mission("Mission de developpement", "Developpement module RH"));

		assertThat(idMission).isGreaterThan(0);

	}

	@Test
	public void TestaffecterMissionADepartement() {

		l.info("-------->Affecter mission : " + idMission + " a departement " + idDepartement);

		timesheetService.affecterMissionADepartement(idMission, idDepartement);

		assertThat(missionR.findById(idMission).get().getDepartement().getId()).isEqualTo(idDepartement);

	}

	@Test
	public void TestAjouterTimeSheet() {

		try {

			l.info("-------->ajouter timeSheet de mission : " + idMission + " avec employer " + idEmployer);

			
			String dateDString = "2021-10-07";
			String dateFString = "2021-10-30";

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date dateD = formatter.parse(dateDString);
			Date dateF = formatter.parse(dateFString);

			timesheetService.ajouterTimesheet(idMission, idEmployer, dateD, dateF);

			assertThat(timeR.findBytimesheetPK(new TimesheetPK(idMission, idEmployer, dateD, dateF))).isNotNull();

		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void TestfindAllMissionByEmployeJPQL() {

		l.info("size findAllMissionByEmployeJPQL() --->"
				+ timesheetService.findAllMissionByEmployeJPQL(idEmployer).size());
		assertThat(timesheetService.findAllMissionByEmployeJPQL(idEmployer).size()).isGreaterThan(0);

	}

}