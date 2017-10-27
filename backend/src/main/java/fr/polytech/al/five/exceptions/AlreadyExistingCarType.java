package fr.polytech.al.five.exceptions;

import javax.xml.ws.WebFault;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebFault(name = "AlreadyExistingCarType")
public class AlreadyExistingCarType extends Exception {
}
