package com.drs.drs_enhanced;

import com.drs.drs_enhanced.model.*;
import com.drs.drs_enhanced.service.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());) {
            Object input;
            while ((input = in.readObject()) != null) {

                if (input instanceof Request) {
                    Request request = (Request) input;
                    String action = request.getAction();
                    Object data = request.getData();
                    User user;
                    String region;
                    Long deptId;
                    Incident incident;
                    Supply supply;
                    Object response;
                    Shelter shelter;
                    Alert alert;
                    Notification notification;
                    boolean responseBoolean;

                    switch (action) {
                        case "login":
                            user = (User) data;
                            System.out.println("Login attempt: " + user.getEmail());
                            response = UserService.login(user.getEmail(),
                                    user.getPassword());
                            if (response instanceof User) {
                                System.out.println("Login Success!");
                            } else {
                                System.out.println("Login Failed!");
                            }
                            out.writeObject(response);
                            break;

                        case "register":
                            user = (User) data;
                            System.out.println("Register attempt: " + user.getEmail());
                            responseBoolean = UserService.createUser(user);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Login Success!");
                            } else {
                                System.out.println("Login Failed!");
                            }
                            break;

                        case "sendHelp":
                            incident = (Incident) data;
                            System.out.println("Send Help attempt: " + incident.getIncidentType());
                            responseBoolean = IncidentService.createIncidentReport(incident);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Login Success!");
                            } else {
                                System.out.println("Login Failed!");
                            }
                            break;

                        case "getUnassignedIncidents":
                            System.out.println("Get Unassigned Incident attempt");
                            response = IncidentService.getUnassignedIncidents();
                            out.writeObject(response);
                            break;

                        case "getAllDepartments":
                            System.out.println("Get All Departments attempt");
                            response = UserService.getAllDepartments();
                            out.writeObject(response);
                            break;

                        case "assignTeamToIncident":
                            incident = (Incident) data;
                            responseBoolean = IncidentService.assignTeamToIncident(incident);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Assignment Success!");
                            } else {
                                System.out.println("Assignment Failed!");
                            }
                            break;

                        case "getIncidentsForDepartment":
                            deptId = (Long) data;
                            List<Incident> incidents = IncidentService.getIncidentsForDepartment(deptId);
                            out.writeObject(incidents);
                            break;
                            
                        case "getSuppliesForDepartment":
                            deptId = (Long) data;
                            List<Supply> supplies = UserService.getSuppliesForDepartment(deptId);
                            out.writeObject(supplies);
                            break;

                        case "addSupply":
                            supply = (Supply) data;
                            System.out.println("Add Supply attempt: " + supply.getName());
                            responseBoolean = SupplyService.createSupply(supply);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Supply Adding Success!");
                            } else {
                                System.out.println("Supply Adding Failed!");
                            }
                            break;

                        case "getAllSupplies":
                            System.out.println("Get All Supplies attempt");
                            response = SupplyService.getAllSupplies();
                            out.writeObject(response);
                            break;

                        case "assignSupplyToTeam":
                            Map<String, Long> assignData = (Map<String, Long>) data;
                            responseBoolean = UserService.assignSupplyToDepartment(
                                    assignData.get("deptId"),
                                    assignData.get("supplyId")
                            );
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Assignment Success!");
                            } else {
                                System.out.println("Assignment Failed!");
                            }
                            break;

                        case "addShelter":
                            shelter = (Shelter) data;
                            System.out.println("Add Shelter attempt: " + shelter.getShelterDetail());
                            responseBoolean = ShelterService.createShelter(shelter);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Shelter Adding Success!");
                            } else {
                                System.out.println("Shelter Adding Failed!");
                            }
                            break;

                        case "getAllShelters":
                            region = (String) data;
                            System.out.println("Get All Shelters attempt" + region);
                            response = ShelterService.getAllShelters(region);
                            out.writeObject(response);
                            break;

                        case "addNotification":
                            notification = (Notification) data;
                            System.out.println("Add Notification attempt: " + notification.getNotificationDetail());
                            responseBoolean = NotificationService.createNotification(notification);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Notification Adding Success!");
                            } else {
                                System.out.println("Notification Adding Failed!");
                            }
                            break;

                        case "getAllNotifications":
                            System.out.println("Get All Notification attempt");
                            response = NotificationService.getAllNotifications();
                            out.writeObject(response);
                            break;

                        case "addAlert":
                            alert = (Alert) data;
                            System.out.println("Add Alert attempt: " + alert);
                            responseBoolean = AlertService.createAlert(alert);
                            out.writeObject(responseBoolean);
                            if (responseBoolean) {
                                System.out.println("Alert Adding Success!");
                            } else {
                                System.out.println("Alert Adding Failed!");
                            }
                            break;

                        case "regionAlert":
                            region = (String) data;
                            System.out.println("Region Alert Attempt: " + region);
                            responseBoolean = AlertService.getAllAlertByRegion(region);
                            if (responseBoolean) {
                                System.out.println("Alert to region success");
                            } else {
                                System.out.println("Alert to region Failed!");
                            }
                            out.writeObject(responseBoolean);
                            break;

                        case "getAllAlerts":
                            System.out.println("Get All Alerts attempt");
                            response = AlertService.getAllAlerts();
                            out.writeObject(response);
                            break;

                        case "deleteAlert":
                            alert = (Alert) data;
                            System.out.println("Delete Alert attempt");
                            response = AlertService.deleteAlert(alert);
                            out.writeObject(response);
                            break;

                        default:
                            out.writeObject("Unknown action: " + action);
                    }

                    out.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected or error: " + e.getMessage());
        }
    }

}
