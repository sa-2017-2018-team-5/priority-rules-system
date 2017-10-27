package fr.polytech.al.five.exceptions;

import javax.xml.ws.WebFault;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebFault(name = "NotAuthorizedCar")
public class NotAuthorizedCar extends Exception {
}
