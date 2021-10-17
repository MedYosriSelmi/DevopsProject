package tn.esprit.spring;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartementServiceImplTest {

		@Autowired
		IEmployeService employeS;
		@Autowired
		 IEntrepriseService entrepriseS;
		@Autowired
		DepartementRepository departementRerpository;
		
		private Employe employe;
		private Departement departement;
		
		@Test
		public void ajouterDepartementTest() {
			Departement depTest = new Departement("production");
			int idDepartement=entrepriseS.ajouterDepartement(depTest);
			Assert.assertTrue(departementRerpository.findById(idDepartement).get().getId() > 0);
			entrepriseS.deleteDepartementById(idDepartement);
			
		}
		
		
}
