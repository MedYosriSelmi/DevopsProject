package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlTimesheet {

	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;


	 
	/**
	 * this method add mission
	 * http://localhost:8081/SpringMVC/servlet/ajouterMission
	 * {"id":4,"name":"mamission", "description":"c ma mission"}
	 * @param mission
	 * @return
	 */

	@PostMapping("/ajouterMission")
	@ResponseBody
	public int ajouterMission(@RequestBody Mission mission) {
		itimesheetservice.ajouterMission(mission);
		return mission.getId();
	}

	/**
	 * this method affect mission in department
	 * http://localhost:8081/SpringMVC/servlet/affecterMissionADepartement/4/4
	 * 
	 * @param missionId
	 * @param depId
	 */
	@PutMapping(value = "/affecterMissionADepartement/{idmission}/{iddept}")
	public void affecterMissionADepartement(@PathVariable("idmission") int missionId,
			@PathVariable("iddept") int depId) {
		itimesheetservice.affecterMissionADepartement(missionId, depId);

	}


	/**
	 * this method add time sheet
	 * http://localhost:8081/SpringMVC/servlet/ajouterTimesheet
	 * {"missionId":1,"employeId":2,"dateDebut":"2020-03-01","dateFin":"2021-03-01"}
	 * 
	 * @param missionId
	 * @param employeId
	 * @param dateDebut
	 * @param dateFin
	 */


	@PostMapping("/ajouterTimesheet/idmission/idemp/dated/datef")
	@ResponseBody
	public void ajouterTimesheet(@PathVariable("idmission") int missionId, @PathVariable("idemp") int employeId,
			@PathVariable("dated") Date dateDebut, @PathVariable("datef") Date dateFin) {
		itimesheetservice.ajouterTimesheet(missionId, employeId, dateDebut, dateFin);

	}

	/**
	 * this method valid time sheet
	 * http://localhost:8081/SpringMVC/servlet/affecterMissionADepartement/4/4
	 * 
	 * @param missionId
	 * @param employeId
	 * @param dateDebut
	 * @param dateFin
	 * @param validateurId
	 */
	@PutMapping(value = "/validerTimesheet/{idmission}/{iddept}")
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		itimesheetservice.validerTimesheet(missionId, employeId, dateDebut, dateFin, validateurId);

	}

	/**
	 * this method for find a list of mission for a employ
	 * http://localhost:8081/SpringMVC/servlet/findAllMissionByEmployeJPQL/1
	 * 
	 * @param employeId
	 * @return a list of mission
	 */
	@GetMapping(value = "findAllMissionByEmployeJPQL/{idemp}")
	@ResponseBody
	public List<Mission> findAllMissionByEmployeJPQL(@PathVariable("idemp") int employeId) {

		return itimesheetservice.findAllMissionByEmployeJPQL(employeId);
	}

	/**
	 * this method for get all employ by mission
	 * http://localhost:8081/SpringMVC/servlet/getAllEmployeByMission/1
	 * 
	 * @param missionId
	 * @return list of employ
	 */
	@GetMapping(value = "getAllEmployeByMission/{idmission}")
	@ResponseBody
	public List<Employe> getAllEmployeByMission(@PathVariable("idmission") int missionId) {

		return itimesheetservice.getAllEmployeByMission(missionId);
	}
}
