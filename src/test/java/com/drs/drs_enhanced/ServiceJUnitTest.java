package com.drs.drs_enhanced;

import com.drs.drs_enhanced.model.*;
import com.drs.drs_enhanced.service.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceJUnitTest {
    
    static PublicUser testUser;
    static Department testDepartment;
    static Supply testSupply;
    static Incident testIncident;
    static Alert testAlert;
    static Notification testNotification;
    static Shelter testShelter;
    static String region;
    
    public ServiceJUnitTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        testUser = new PublicUser("Test User", "testuser@gmail.com", 
                "userpass", "North", "test address");

        testDepartment = new Department("Test Department", null, "Test Department", 
                "testdept@gmail.com", "deptpass", "NA");

        testSupply = new Supply("Test Supply", null);
        
        testIncident = new Incident("Test Incident", 
                "Test Incident Description", 1, 
                null, null);
        
        region = "Test Region";
        testAlert = new Alert(region);
        
        testNotification = new Notification("Test Notification");
        
        testShelter = new Shelter("Test Shelter", region);
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    @Order(1)
    void testCreateUser_success() {
        boolean result = UserService.createUser(testUser);
        assertTrue(result, "User should be created successfully");
    }

    @Test
    @Order(2)
    void testCreateUser_duplicateEmail() {
        boolean result = UserService.createUser(testUser);
        assertFalse(result, "Creating user with existing email should fail");
    }
    
    @Test
    @Order(3)
    void testLoginSuccess() {
        User user = UserService.login(testUser.getEmail(), testUser.getPassword());
        assertNotNull(user);
    }

    @Test
    @Order(4)
    void testLoginFailure_invalidPassword() {
        User user = UserService.login(testUser.getEmail(), "wrongpass");
        assertNull(user);
    }

    @Test
    @Order(3)
    void testLoginFailure_invalidEmail() {
        User user = UserService.login("invalid@example.com", testUser.getPassword());
        assertNull(user);
    }
    
    @Test
    @Order(4)
    void testCreateDepartment_success() {
        assertTrue(UserService.createUser(testDepartment));
    }
    
    @Test
    @Order(5)
    void testCreateDepartment_duplicateEmail() {
        assertFalse(UserService.createUser(testDepartment));
    }

    @Test
    @Order(6)
    void testGetAllDepartments() {
        List<Department> depts = UserService.getAllDepartments();
        assertFalse(depts.isEmpty());
        
        // To use for other test cases
        for (Department d: depts) {
            if (d.getDepartmentName().equalsIgnoreCase(testDepartment.getDepartmentName())){
                testDepartment = d;
                break;
            }
        }
    }

    @Test
    @Order(7)
    void testCreateSupply_success() {
        assertTrue(SupplyService.createSupply(testSupply));
    }
    
    @Test
    @Order(8)
    void testCreateSupply_duplicateName() {
        assertFalse(SupplyService.createSupply(testSupply));
    }
    
    @Test
    @Order(9)
    void testGetAllSupplies() {
        List<Supply> supplies = SupplyService.getAllSupplies();
        assertFalse(supplies.isEmpty());
        
        // To use for other test cases
        for (Supply s: supplies) {
            if (s.getName().equalsIgnoreCase(testSupply.getName())){
                testSupply = s;
                break;
            }
        }
    }
    
    @Test
    @Order(10)
    void testAssignSupplyToDepartment_success() {
        boolean result = UserService.assignSupplyToDepartment(testDepartment.getUserId(), testSupply.getId());
        assertTrue(result);
    }

    @Test
    @Order(11)
    void testAssignSupplyToDepartment_invalid() {
        boolean result = UserService.assignSupplyToDepartment(999L, 999L);
        assertFalse(result);
    }
    
    @Test
    @Order(12)
    void testAssignSupplyToDepartment_existing() {
        boolean result = UserService.assignSupplyToDepartment(testDepartment.getUserId(), testSupply.getId());
        assertFalse(result);
    }

    @Test
    @Order(13)
    void testGetSuppliesForDepartment() {
        List<Supply> supplies = UserService.getSuppliesForDepartment(testDepartment.getUserId());
        assertNotNull(supplies);
    }

    @Test
    @Order(14)
    void testGetSuppliesForDepartment_invalid() {
        List<Supply> supplies = UserService.getSuppliesForDepartment(999L);
        assertNotNull(supplies);
        assertTrue(supplies.isEmpty());
    }

    @Test
    @Order(15)
    void testCreateIncident() {
        testIncident.setUser(testUser);
        assertTrue(IncidentService.createIncidentReport(testIncident));
    }

    @Test
    @Order(16)
    void testGetUnassignedIncidents() {
        List<Incident> list = IncidentService.getUnassignedIncidents();
        assertNotNull(list);
        
        boolean test = false;
        
        // To use for other test cases
        for (Incident i : list) {
            if (i.getIncidentType().equalsIgnoreCase(testIncident.getIncidentType())) {
                test = true;
                testIncident = i;
                return;
            }
        }
        assertTrue(test);
    }

    @Test
    @Order(17)
    void testAssignTeamToIncident_success() {
        testIncident.setAssignedDepartment(testDepartment);
        assertTrue(IncidentService.assignTeamToIncident(testIncident));
    }
    
    @Test
    @Order(18)
    void testAssignTeamToIncident_invalid() {
        Incident dummy = new Incident();
        dummy.setAssignedDepartment(testDepartment);
        dummy.setId(999L);
        assertFalse(IncidentService.assignTeamToIncident(dummy));
    }

    @Test
    @Order(19)
    void testGetIncidentsForDepartment() {
        List<Incident> incidents = IncidentService.getIncidentsForDepartment(testDepartment.getUserId());
        assertNotNull(incidents);
        
        // To use for other test cases
        for (Incident i : incidents) {
            if (i.getIncidentType().equalsIgnoreCase(testIncident.getIncidentType())) {
                testIncident = i;
                return;
            }
        }
    }

    @Test
    @Order(20)
    void testGetIncidentsForDepartment_invalid() {
        List<Incident> incidents = IncidentService.getIncidentsForDepartment(999L);
        assertNotNull(incidents);
        assertTrue(incidents.isEmpty());
    }

    @Test
    @Order(21)
    void testMarkIncidentComplete() {
        assertTrue(IncidentService.markIncidentComplete(testIncident.getId()));
    }

    @Test
    @Order(22)
    void testMarkIncidentComplete_invalid() {
        assertFalse(IncidentService.markIncidentComplete(999L));
    }

    @Test
    @Order(23)
    void testCreateAlert() {
        assertTrue(AlertService.createAlert(testAlert));
    }

    @Test
    @Order(24)
    void testGetAllAlertByRegion_exists() {
        assertTrue(AlertService.getAllAlertByRegion(region));
    }

    @Test
    @Order(25)
    void testGetAllAlertByRegion_notExists() {
        assertFalse(AlertService.getAllAlertByRegion("Unknown"));
    }
    
    @Test
    @Order(26)
    void testGetAllAlerts() {
        List<Alert> alerts = AlertService.getAllAlerts();
        assertFalse(alerts.isEmpty());
        
        // To use for other test cases
        for (Alert a: alerts) {
            if (a.getAlertRegion().equalsIgnoreCase(testAlert.getAlertRegion())){
                testAlert = a;
                break;
            }
        }
    }

    @Test
    @Order(27)
    void testDeleteAlert() {
        assertTrue(AlertService.deleteAlert(testAlert));
    }

    @Test
    @Order(28)
    void testDeleteAlert_invalid() {
        Alert dummy = new Alert();
        dummy.setId(999L);
        assertFalse(AlertService.deleteAlert(dummy));
    }

    @Test
    @Order(29)
    void testCreateNotification() {
        assertTrue(NotificationService.createNotification(testNotification));
    }

    @Test
    @Order(30)
    void testGetAllNotifications() {
        List<Notification> list = NotificationService.getAllNotifications();
        assertNotNull(list);
    }

    @Test
    @Order(31)
    void testCreateShelter() {
        assertTrue(ShelterService.createShelter(testShelter));
    }

    @Test
    @Order(32)
    void testGetAllShelters() {
        List<Shelter> shelters = ShelterService.getAllShelters(region);
        assertNotNull(shelters);
    }

    @Test
    @Order(33)
    void testGetAllShelters_invalidRegion() {
        List<Shelter> shelters = ShelterService.getAllShelters("Unknown");
        assertTrue(shelters == null || shelters.isEmpty());
    }
    
}
